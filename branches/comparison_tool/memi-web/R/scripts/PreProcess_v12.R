# This code is composed of various fucntions that could be used for the pre-processing of data before
# using statistical functions of R on it.

# It seems needed to convert the tables to matrix containing only numerical values in order to use
# heatmap, prcomp, etc. That's why I convert abundance tables to matrix at the end of functions.

# Precedent abundance table creation method has been merged into 1 big method to generate abundance table ans save it. See parameters to customize.
# Can be used for GO annotation summaries (.csv files) and IPR summaries (.csv files)

# Console log
message(paste(Sys.time(),'[R - Message] Launched R script PreProcess_v12.R'))


CreateAbTable <- function(outputName, fileList, usedData = c('GO','GOslim','IPR','IPRcol'), sampleNames = c()) {
  # Create the abundance table needed for statistics from a file vector. If wanted, the hierarchy of
  # IPR can be handled (by collapsing: replacing IPR that are below a certain level by the parent IPR
  # of level 'collapseLevel' if there is any).  
  #
  # Args: 
  #   fileList: a vector containing the name of at least 2 GO annotation or InterPro matches (i5.tsv) files.
  #   usedData: a string indicating the type of data. Values: 'GO' or 'IPR'.
  #   sampleNames: a vector of sample names, should come form the java web app.
  #   outputName: name of the output file
  # 
  # Returns: The abundance table of GO/IPR for selected samples. Rows: Samples, Columns: IPR.
  
  tableList <- list()
  size <- length(fileList)
    
  # Error handling: if the number of files is incorrect (< 2), stop.
  if (size < 2) {
    stop("Incorrect number of files (at least 2 are needed).")
  }
  
  # Following the used data, we won't call the same function later. Need the correct name.
  if(usedData == 'GO' || usedData == 'GOslim'){
    singleTableFun <- 'NewGOTable'
  }
  else {
    singleTableFun <- 'NewIPRTable'
  }
  
  # Creation of all needed tables (single tables)
  for (i in 1:size) {
    tableList[[i]] <- do.call(singleTableFun, list(fileList[i]))
    # Rename properly the Columns
    setnames(tableList[[i]], colnames(tableList[[i]]), c(usedData,sampleNames[i])) 
  }
  
  # Merge ! (dirty but working)

  if (size < 10) {
      abTable <-   Reduce(function(x,y) {merge(x,y,by=colnames(tableList[[1]])[1],all=TRUE)}, tableList)
  }
  
  # If size is too large (10 samples or more), divide into chunks of five.
  if(size>=10) {
    mergedNumber = as.integer(size/5)
    subTableList <- list()
    for (j in 1:(mergedNumber)) {
      if(j<mergedNumber){
      subTableList[[j]] <- Reduce(function(x,y) {merge(x,y,by=colnames(tableList[[1]])[1],all=TRUE)}, tableList[(((j-1)*5)+1):((j)*5)])
      }
      else {
        subTableList[[j]] <- Reduce(function(x,y) {merge(x,y,by=colnames(tableList[[1]])[1],all=TRUE)}, tableList[(((j-1)*5)+1):length(tableList)])
      }
      # print(subTableList[[j]])
    }
    abTable <- Reduce(function(x,y) {merge(x,y,by=colnames(tableList[[1]])[1],all=TRUE)}, subTableList)
  }
  # Replace 'NA' values by 0.
  abTable[is.na(abTable)] <- 0

  # If used data is 'GOslim', re-order rows following the order of GOslimTable (table defined in launch script)
  # There is surely a much more legant way of doing this but this one seems cool. Doesn't work at the moment.
  #if (usedData=='GOslim') {
  #namesVector = sapply(sapply((abTable)[[1]],strsplit,split='\\|',USE.NAMES=FALSE),"[[", 1)
  #print(namesVector)
  #GOslimVector = GOslimTable$GO
  #rightOrder = sapply(namesVector, grep, x = GOslimVector, USE.NAMES=FALSE)
  #print(rightOrder)
  #abTable = abTable[c(rightOrder),,drop=FALSE]
  #namesVector = sapply(sapply((abTable)[[1]],strsplit,split='\\|',USE.NAMES=FALSE),"[[", 1)
  #print(namesVector)
  #}  

  # Make the table suitable for use (2d table + conversion to matrix)
  rownames(abTable) <- abTable[[1]]
  abTable[[1]] <- NULL 
  abTable <- data.matrix(abTable)
  
  # If the user wants to collapse (data is IPRcol), just do it. Level 1 by default but can be manually changed.
  if (usedData == 'IPRcol') {
    #rownames(abTable) <- ReplaceChildrenIPR(abTable, 1)
    # Use rowsum function (which performs column sums across rows) to merge and sum. It calls a C
    # function (pretty fast).
    #abTable <- rowsum(abTable, group = rownames(abTable))
  }

  # Transpose the matrix so it is an abundance table
  abTable <- t(abTable)
  
  # Save the matrix in a file with a custom filename
  # fileName = paste('abundance',usedData,Sys.Date(),sep='_')
  # write.table(abTable,paste('R/tables/',outputName,'.txt',sep=''),sep='\t',col.names=NA,quote=FALSE)
  return(abTable)
  } 

################# GO SLIM AND GO #################

NewGOTable <- function(goFile) {
  # Extracts the occurrence of each GO term in a sample from a complete GO annotation or a GO slim
  # annotation file and store this data as a table.  
  # 
  # Args: 
  #   goFile: a GO or GO slim annotation file (CSV).
  # 
  # Returns: 
  #   A table containing the abundance of GO terms for a single sample.
  
  # Read table Parameter 'stringsAsFactors=FALSE' seems to fasten the process.
  newTable <- fread(goFile, header = FALSE, sep = ",", stringsAsFactors = FALSE)
  
  # Melting first columns to keep ID, name and category
  newTable = data.table(GO = paste(newTable$V1,newTable$V2,newTable$V3,sep='|'), count = c(as.numeric(newTable$V4)))
  # Return the table
  return(newTable)
}

################# IPR IN SUMMARY CSV FILES #################

NewIPRTable <- function(iprSummaryFile) {
  # Extracts the occurrence of each IPR in a sample from an 'InterPro matches summary' file (CSV) and store
  # this data as a table.  
  # 
  # Args: 
  #   i5tsvFile: an InterPro matches file (CSV).
  # 
  # Returns: 
  #   A table containing the abundance of IPR for a single sample.
  
  newTable <- fread(iprSummaryFile, header = FALSE, sep = ",", stringsAsFactors = FALSE)
  # Melting first columns to keep ID, name and category
  newTable = data.table(IPR = paste(newTable$V1,newTable$V2,sep='|'), count = c(as.numeric(newTable$V3)))
  # Return the table
  return(newTable)
}
 
ReplaceChildrenIPR <- function(abTableIPR, level) {
  # Replace IPR that are below a certain level by the parent term (if any). This function has to be
  # used as a final step in the 'CreateAbTableIPR' function, before the table is transposed!  
  # 
  # Args:
  #   abTableIPR: a non-transposed-yet abundance table of IPR.  
  #   level: the level of replacement (min 1, max 5) 
  #
  # Returns: 
  #   newRows: a vector containing all IPR with replacements made when needed.
  
  # Collection of row names (table is not transposed yet)
  oldRows <- rownames(abTableIPR)
  hierarchyList <- CreateHierarchyList(level)
  fullChildrenString <- CreateHierarchyString(hierarchyList)
  # Search parents for all IPR of the abundance table
  newRows = sapply(oldRows, ReplaceIPR, childrenString = fullChildrenString, USE.NAMES=FALSE)
  return(newRows)
}

# Replace the IPR in parameter by its parent
ReplaceIPR = function(IPR,childrenString) {
  result = IPR
  if (grepl(paste0(':[^-]*',IPR), childrenString) == TRUE) {
    searchPattern <- paste("--IPR\\d+:[^:]*", IPR, sep = "")
    searchResult <- regmatches(childrenString, regexpr(searchPattern, childrenString))
    result <- regmatches(searchResult, regexpr("^IPR\\d{6}", searchResult))
  }
  return(result)
}

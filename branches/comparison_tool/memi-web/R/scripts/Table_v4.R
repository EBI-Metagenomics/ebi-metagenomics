# This script aim to return a big JSON array wrapped in some HTML code enabling jQuery DataTables.
# Nb: including of JS Libraries could also be done directly in the webapp. However, I prefer to
# return a full div containing the table, autonomous (no spaghetti code!)  Nb: Maybe it could be
# betterl to deal only with the data and to handle headers and HTML in an other place. This is a
# first attempt to get things working. TODO: Only return headers and data, and do the HTML externally
# because it is quite messy.

# Console log
message(paste(Sys.time(), "[R - Message] Launched R script Table_v4.R"))

# Returns a list composed of the sums of all matches for all samples, for each GO category.  Used to
# display percentage in the table
MaxForCategory <- function(abTable) {
  tableCatList <- list(abTable[, grepl("biological_process", colnames(abTable))], abTable[, grepl("cellular_component", 
    colnames(abTable))], abTable[, grepl("molecular_function", colnames(abTable))])
  maxList <- lapply(tableCatList, rowSums)
  return(maxList)
}

DataTableGen <- function(abTable) {
  # Nb: Original abundance have to be in absolute value.  TODO: Handle other data types
  
  # Import of needed JS / CSS (jQuery is already inside !)
  importJS <- "<script src=\"https://cdn.datatables.net/1.10.0/js/jquery.dataTables.js\"></script>"
  goLink <- "http://www.ebi.ac.uk/QuickGO/GTerm?id=" # Beginning of the URL to access quickGO webpage for a GO term. Could be replaced by anything else.
  
  # Steps Going from simple abundance table to the table we want to display.
  nbSamples <- nrow(abTable)
  nbObs <- ncol(abTable)
  maxOfAll <- MaxForCategory(abTable)
  recordGO <- c()
  recordName <- c()
  recordSample <- c()
  recordValue <- c()
  recordPc <- c()
  recordCat <- c()
  correctNames <- c("Biological process", "Cellular component", "Molecular function")
  for (i in 1:nbObs) {
    categoryIndice <- 0
    category <- strsplit(colnames(abTable)[i], split = "\\|")[[1]][3]
    if (category == "biological_process") 
      categoryIndice <- 1
    if (category == "cellular_component") 
      categoryIndice <- 2
    if (category == "molecular_function") 
      categoryIndice <- 3
    for (j in 1:nbSamples) {
      # GO names in a separate place ? Could be changed in the future
      recordName <- c(recordName, strsplit(colnames(abTable)[i], split = "\\|")[[1]][2])
      recordCat <- c(recordCat, correctNames[categoryIndice])  # Clean category name
      currentGO <- strsplit(colnames(abTable)[i], split = "\\|")[[1]][1]
      recordGO <- c(recordGO, paste0("<a href=\"",goLink, currentGO, 
        "\" target=\"_blank\" class=\"ext\">", currentGO, "</a>"))
      # Time to mess with percentages
      recordPc <- c(recordPc, round(((abTable[j, i]/maxOfAll[[categoryIndice]][j]) * 100), 2))
      recordSample <- c(recordSample, rownames(abTable)[j])
      recordValue <- c(recordValue, abTable[j, i])
    }
  }
  
  newTable <- data.table(recordName, recordCat, recordGO, recordSample, recordValue, recordPc)
  setnames(newTable, colnames(newTable), c("GO name", "GO category", "GO ID", "Sample ID", "Matches", 
    "%"))
  
  
  # Convert it to JSON
  tableJSON <- unlist(apply(newTable, 1, list), recursive = FALSE)
  tableJSON <- lapply(tableJSON, unname)
  tableJSON <- toJSON(tableJSON)
  
  
  
  # Headers to JSON, dirty style !
  headers <- paste("[ \n { \"title\": \"", colnames(newTable)[1], "\" },\n", "{ \"title\": \"", colnames(newTable)[2], 
    "\" },\n", "{ \"title\": \"", colnames(newTable)[3], "\" },\n", "{ \"title\": \"", colnames(newTable)[4], 
    "\" },\n", "{ \"title\": \"", colnames(newTable)[5], "\" },\n", "{ \"title\":  \"", colnames(newTable)[6], 
    " \" } \n ]", sep = "")
  
  # Add the needed HTML code around: header, etc.
  
  
  jQueryFuncBegin <- "$(document).ready(function() { $('#table-container').html('<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"display\" id=\"table\"></table>' ); \n $('#table').dataTable( { \"data\": dataSet, \"columns\": \n"
  jQueryFuncEnd <- ",\n \"order\": [[ 4, \"desc\" ]] } ); \n } );"
  
  
  fullHtmlCode <- paste(importJS, "<div id=\"table-container\"></div>", "<script>", "\n", "var dataSet = ", 
    tableJSON, "; \n", jQueryFuncBegin, headers, jQueryFuncEnd, "</script>", sep = "")
  
  # Return this text
  return(fullHtmlCode)
} 


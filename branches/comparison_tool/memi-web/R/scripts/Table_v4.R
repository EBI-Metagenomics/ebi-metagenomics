# This script is used to generate jQuery dataTables from an abundance table
# The aim is to return a big JSON array wrapped in some HTML/JS code to enable jQuery DataTables.
# Note: it could be better to deal only with the data in this place and to handle headers and other HTML directly in the result page from the webapp.  
# Note: insert of the dataTables jQuery plug-in has be done in the HTML code of the result page

# Console log
message(paste(Sys.time(), "[R - Message] Launched R script Table_v4.R"))


MaxForCategory <- function(abTable) {
  # Generates a list composed of the sums of all matches for all samples for each GO category.
  # Used to calculate and display percentage in the table. 
  #
  # Args:
  #   abTable: An abundance table for selected samples. Rows: Samples, Columns: observations. 
  #            This function is used on a general abundance table (all GO categories mixed). 
  #
  # Returns:
  #   A list composed of three elements (one for each category of GO terms), 
  #   each one containing the sum of all matches for all samples.

  tableCatList <- list(abTable[, grepl("biological_process", colnames(abTable))], abTable[, grepl("cellular_component", 
    colnames(abTable))], abTable[, grepl("molecular_function", colnames(abTable))])
  maxList <- lapply(tableCatList, rowSums)
  return(maxList)
}


DataTableGen <- function(abTable) {
  # Generates a table (jQuery dataTable object) from an abundance table and returns all needed
  # HTML code to display it on the result page.  
  #
  # Args:
  #   abTable: The general abundance table for selected samples (all GO categories mixed)
  #
  # Returns:
  #   Needed HTML code to insert the table on the result page.
  
  # Beginning of the URL to access quickGO webpage for a GO term. Could be replaced by anything else.
  goLink <- "http://www.ebi.ac.uk/QuickGO/GTerm?id=" 
  
  # Go from abundance table to jQuery dataTable.

  # Variables to store all information
  nbSamples <- nrow(abTable) # Number of samples
  nbObs <- ncol(abTable) # Number of terms
  maxOfAll <- MaxForCategory(abTable) # Max matches for every sample / GO category
  recordGO <- c() # GO identifiers
  recordName <- c() # GO names
  recordSample <- c() # Sample identifiers
  recordValue <- c() # Matches
  recordPc <- c() # Percentages 
  recordCat <- c() # GO categories
  # Correct names for each GO category
  correctNames <- c("Biological process", "Cellular component", "Molecular function")
  # Retrieves information from abundance table 
  for (i in 1:nbObs) {
    # Associates category of GO term and to an indice
    categoryIndice <- 0
    category <- strsplit(colnames(abTable)[i], split = "\\|")[[1]][3]
    if (category == "biological_process") 
      categoryIndice <- 1
    if (category == "cellular_component") 
      categoryIndice <- 2
    if (category == "molecular_function") 
      categoryIndice <- 3
    for (j in 1:nbSamples) {
      recordName <- c(recordName, strsplit(colnames(abTable)[i], split = "\\|")[[1]][2])
      recordCat <- c(recordCat, correctNames[categoryIndice])
      currentGO <- strsplit(colnames(abTable)[i], split = "\\|")[[1]][1] # Retrieves current GO
      # Creates a link to QuickGO with GO identifier
      recordGO <- c(recordGO, paste0("<a href=\"",goLink, currentGO, 
                    "\" target=\"_blank\" class=\"ext\">", currentGO, "</a>"))
      recordPc <- c(recordPc, round(((abTable[j, i]/maxOfAll[[categoryIndice]][j]) * 100), 2))
      recordSample <- c(recordSample, rownames(abTable)[j])
      recordValue <- c(recordValue, abTable[j, i])
    }
  }
  # Stores all retrieved information in a flat table
  newTable <- data.table(recordName, recordCat, recordGO, recordSample, recordValue, recordPc)
  # Changes column names by correct names as they will be displayed on the visualization
  setnames(newTable, colnames(newTable), 
           c("GO name", "GO category", "GO ID", "Sample ID", "Matches", "%"))
  
  
  # Convert the generated flat table to JSON object
  # Each row of the table -> list element
  tableJSON <- unlist(apply(newTable, 1, list), recursive = FALSE) 
  # Remove names of vectors of each list element before conversion to JSON object
  # Needed if we want the data to be edible by the dataTables plug-in.
  tableJSON <- lapply(tableJSON, unname)
  # Conversion to JSON object
  tableJSON <- toJSON(tableJSON)

  # Conversion of headers of the table to JSON. Made in a dirty way..
  headers <- paste("[ \n { \"title\": \"", colnames(newTable)[1], "\" },\n", 
                        "{ \"title\": \"", colnames(newTable)[2], "\" },\n", 
                        "{ \"title\": \"", colnames(newTable)[3], "\" },\n", 
                        "{ \"title\": \"", colnames(newTable)[4], "\" },\n", 
                        "{ \"title\": \"", colnames(newTable)[5], "\" },\n", 
                        "{ \"title\": \"", colnames(newTable)[6], " \" } \n ]", sep = "")
  
  # Last step is to add the needed HTML/JS code around
  # Beginning of the jQuery function enabling dataTables (and reading the headers)
  jQueryFuncBegin <- "$(document).ready(function() { $('#table-container').html('<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"display\" id=\"table\"></table>' ); \n $('#table').dataTable( { \"data\": dataSet, \"columns\": \n"
  # End of the jQuery function
  jQueryFuncEnd <- ",\n \"order\": [[ 4, \"desc\" ]] } ); \n } );"

  # Total HTML code is created (string object) and returned
  fullHtmlCode <- paste("<div id=\"table-container\"></div>", "<script>", "\n", "var dataSet = ", 
    tableJSON, "; \n", jQueryFuncBegin, headers, jQueryFuncEnd, "</script>", sep = "")
  return(fullHtmlCode)
} 


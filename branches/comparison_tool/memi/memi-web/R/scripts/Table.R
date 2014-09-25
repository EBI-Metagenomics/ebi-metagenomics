# This script aim to return a big JSON array wrapped in some HTML code enabling jQuery DataTables.
# Nb: including of JS Libraries could also be done directly in the webapp. However, I prefer to return a full div containing the table, autonomous (no spaghetti code!)
# Nb: Maybe it could be betterl to deal only with the data and to handle headers and HTML in an other place. This is a first attempt to get things working.
#TODO: Only return headers and data, and do the HTML externally because it is quite messy.

# Console log
message(paste(Sys.time(),'[R - Message] Launched R script Table.R'))

library(RJSONIO)
library(data.table)


DataTableGen = function(abTable){
# Nb: Original abundance have to be in absolute value.
#TODO: Handle percentages
#TODO: Handle other data types

# Import of needed JS / CSS (jQuery is already inside !)
importJS = c('<link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdn.datatables.net/1.10.0/css/jquery.dataTables.css\">','<script src=\"https://cdn.datatables.net/1.10.0/js/jquery.dataTables.js\"></script>')

# Steps
# Going from simple abundance table to the table we want to display.
  nbSamples = length(rownames(abTable))
  nbObs = length(colnames(abTable))
  maxOfAll = rowSums(abTable)
  recordGO=c()
  recordName= c()
  recordSample=c()
  recordValue=c()
  for(i in 1:nbObs){
    for(j in 1:nbSamples) {
      recordGO=c(recordGO,colnames(abTable)[i])
      recordSample=c(recordSample,rownames(abTable)[j])
      recordValue=c(recordValue,abTable[j,i])
    }
  }
  # GO names
  for(k in 1:length(recordGO)){
    index = grep(recordGO[k],GOslimTable$GO)
    recordName <- c(recordName,GOslimTable[index,2])
    
  }
  
  newTable=data.table(recordSample,recordName,recordGO,recordValue)
  setnames(newTable,colnames(newTable),c('Sample','GO name','GO ID','Matches'))


# Convert it to JSON
tableJSON = unlist(apply(newTable, 1, list), recursive = FALSE)
tableJSON = lapply(tableJSON,unname)
tableJSON = toJSON(tableJSON)



# Headers to JSON, dirty style !
headers = paste('[ \n { \"title\": \"',colnames(newTable)[1],'\" },\n','{ \"title\": \"',colnames(newTable)[2],'\" },\n','{ \"title\": \"',colnames(newTable)[3],'\" },\n','{ \"title\":  \"',colnames(newTable)[4],' \" } \n ]',sep='')

# Add the needed HTML code around: header, etc.


jQueryFuncBegin = "$(document).ready(function() { $('#table-container').html(\'<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"display\" id=\"table\"></table>\' ); \n $('#table').dataTable( { \"data\": dataSet, \"columns\": \n"
jQueryFuncEnd = "\n } ); \n } );"

fullHtmlCode = paste(importJS[1],importJS[2],'<div id=\"table-container\"></div>','<script>','\n','var dataSet = ',tableJSON,'; \n',jQueryFuncBegin,headers,jQueryFuncEnd,'</script>',sep='')

# Return this text
return(fullHtmlCode)

}


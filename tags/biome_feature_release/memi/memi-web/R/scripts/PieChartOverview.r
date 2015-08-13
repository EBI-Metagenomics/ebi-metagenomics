# Console log
#TODO : Pie charts instead of this crap
message(paste(Sys.time(),'[Message] Launched script PieChartOverview.r'))

# Create tables to retrieve names and type from a GO term
GOslimTable <- read.table("R/GOslim_names.csv", sep = ",", quote = "\"", 
                          col.names = c("GO", "function", "type"), colClasses = c("character", "character", "character"))

# A small operator that is quite useful
"%w/o%" <- function(x, y) x[!x %in% y] # ->  x without y 

# Function to divide abundance table for each category
divideAbTable = function(abTable,category) {
  categoryGo <- data.frame(GOslimTable[, 2][GOslimTable[3] == category], row.names = GOslimTable[, 
                                                                                                 1][GOslimTable[3] == category], stringsAsFactors = FALSE)
  # Fixing shitty column names
  colnames(categoryGo) <- c("name")
  abTableCategory <- abTable[, colnames(abTable) %in% c(row.names(categoryGo))]
  return(abTableCategory)
}

# Function to generate rCharts-compatible data for the 'Overview' tab
overallSummary = function(abTable) {
  abTable_bio <- divideAbTable(abTable,'biological_process')
  abTable_cell <- divideAbTable(abTable,'cellular_component')
  abTable_mol <- divideAbTable(abTable,'molecular_function')
  nbSamples = length(rownames(abTable_bio))
  
  matches = c(rowSums(abTable_bio),rowSums(abTable_cell),rowSums(abTable_mol))
  types = c(rep('Biological Process',nbSamples),rep('Cellular Component',nbSamples),rep('Molecular Function',nbSamples))
  samples = c(rep(rownames(abTable_bio),3))
  
  newTable=data.table(samples,types,matches)
  setnames(newTable,colnames(newTable),c('sample','GOtype','value'))
  return(newTable)
}

generateOverview = function(abTable) {
  overviewTable = overallSummary(abTable)
# Let's try to get a list of pie charts
  sampleList = unique(overviewTable$sample)
  chartList = list()
# To be continued...
# for(i in sampleList) {
a = hPlot(value~GOtype,data = overviewTable[overviewTable$sample==sampleList[1]], type = 'pie', title=sampleList[1]) # add group='GOtype' and swith 'pie' to 'bart' to activate barcharts again
    a$tooltip(shared=TRUE)
    a$colors(EMGcolors)
    # a$plotOptions(bar = list(stacking = "percent")) 
    a$chart(width = 400, height = 400)
    a$exporting(enabled = FALSE)
    options(rcharts.cdn = TRUE)
print(getwd())
# Save in R/Graphs/tmp then delete this crap
print('adfsdgfasdfsgsg')
}
# This is the proper HTML code containing everything needed (except style/JS but we define this in another place)
    return(chartList) 

}

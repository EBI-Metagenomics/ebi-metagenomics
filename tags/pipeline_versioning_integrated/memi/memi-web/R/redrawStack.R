# Note: THIS FEATURE IS STILL EXPERIMENTAL. I did not have time to write it properly (with good paths etc.). However, this works on my local machine and gives the logic to be able to redraw samples. To do so, here are the steps:
#    - Save the abundance table
#    - Get the ouput ID with JSTL on the result page so you can retrieve the table
#    - Do a R script reading the output and the new value to redraw the chart (here the threshold), loading needed packages and call the visualization script.
#    - Store the result as html file, read it and replace html of wanted div with ajax method

# Console log
message(paste(Sys.time(),'[R - Message] Redrawing stacked columns...'))

# Allow R to read command arguments
parameters <- commandArgs(TRUE)

# Small check to see number of arguments. Uncomment to use.
# print(length(parameters))

outputId <- parameters[1]
newThreshold <- as.numeric(parameters[2]) 
print(parameters)

abTable = read.table(paste0('R/tmpGraph/',outputId,'_table.tsv'),sep='\t',quote='',header=TRUE,row.names=1,check.names=FALSE)

##########################################
################ Variables ###############
##########################################

# Path separator, not the same depending on the OS running the script. Shouldn't be a problem since the scripts are running on a linux server.
pathSep = '/'
if(.Platform$OS.type != 'unix') pathSep = '\\'


# EMG website colors
EMGcolors <- c('#058DC7','#50B432','#ED561B','#EDEF00','#24CBE5','#64E572','#FF9655','#e396ff','#6AF9C4','#DABE88')

# A small operator that is quite useful
"%w/o%" <- function(x, y) x[!x %in% y] # ->  x without y

# A boolean to disable heatmap generation as it doesn't work on the server
commit = FALSE


##########################################
############# Shared methods #############
##########################################

# These methods are used in various visualization scripts. They are defined here. 
# - Divide abundance table into subtable for given GO category
# - Go from abundance table to flat table edible by rCharts
# - Conversion of abundance table values to percentages

# Divide abundance table for each category
DivideAbTable = function(abTable,category) {
  abTableCategory <- abTable[,grepl(category,colnames(abTable))]
  return(abTableCategory)
}

# Go from divided abundance table to edible data for rChart (flat table)
RchartsFix = function(abundanceTable){
  nbSamples = nrow(abundanceTable)
  nbObs = ncol(abundanceTable)
  maxOfAll = rowSums(abundanceTable)
  recordGO=c()
  recordName= c()
  recordSample=c()
  recordValue=c()
  for(i in 1:nbObs){
    for(j in 1:nbSamples) {
      recordGO = c(recordGO,strsplit(colnames(abundanceTable)[i],split='\\|')[[1]][1])
      # GO names in a separate place ? Could be changed in the future
      recordName <- c(recordName,strsplit(colnames(abundanceTable)[i],split='\\|')[[1]][2])
      recordSample = c(recordSample,rownames(abundanceTable)[j])
      recordValue=c(recordValue,abundanceTable[j,i])
    }
  }
  newTable=data.table(recordSample,recordGO,recordName,recordValue)
  setnames(newTable,colnames(newTable),c('sample','GO','GOname','value'))
  return(newTable)
}

# Convert abundance table values into percentages. Use it on divided abundance tables.
AbTablePercent = function(abundanceTable){
  pcAbTable = abundanceTable
  maxOfAll = rowSums(pcAbTable)
  for(i in 1:length(maxOfAll)) {
    pcAbTable[i,] = round(((pcAbTable[i,]/maxOfAll[i])*100),2)
  }
  return(pcAbTable)
}


usedPackages = c('methods', # methods: Slows up loading time but needed to use rCharts methods.
  'rCharts', # rCharts: JS visualizations using highcharts JS library
  'RJSONIO', # RJSONIO: Enable conversion of R data into JSON data
  'gplots', # gplots: Used for heatmaps (heatmap.2 function)
  'RColorBrewer', # RColorBrewer: Heatmap colors
  'data.table', # data.table: data.table objects
  'Cairo') # Cairo: enable creation of SVG graphical output on server
invisible(sapply(usedPackages, library, character.only=TRUE, USE.NAMES=FALSE, quietly=TRUE)) 
message(paste(Sys.time(),'[R - Message] R packages loaded successfully'))
source('R/scripts/GOslimStack_v6.R')

########################################
######### Stacked Bars Redraw ##########
########################################
# Use methods, save, grab useful data
stackedCols = c(CreateStackColForCategory(abTable, newThreshold,'biological_process'),
  CreateStackColForCategory(abTable, newThreshold, 'molecular_function'),
  CreateStackColForCategory(abTable, newThreshold, 'cellular_component'))
# Write final result file
write(stackedCols,paste('R/tmpGraph/',outputId,'_stack','.htm',sep=''))

message('Redrawing done.')

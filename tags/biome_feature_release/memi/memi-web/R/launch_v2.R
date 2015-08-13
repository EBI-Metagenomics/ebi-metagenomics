parameters <- commandArgs(TRUE)
print(length(parameters))
files <- (strsplit(parameters[1],split=',')[[1]])
print(files)
data <- parameters[2]
print(data)
keep <- as.logical(parameters[3])
print(keep)
name <- (parameters[4])
print(name)
level <- as.integer(parameters[5])
print(level)
library(methods)
source("R/preProcess.r")

a = CreateAbTable(files, data, keep, name, level)
print(a)
library(rCharts)

rChartsFixing = function(abundanceTable,percent=FALSE){
  nbSamples = length(rownames(abundanceTable))
  nbObs = length(colnames(abundanceTable))
  maxOfAll = rowSums(abundanceTable)
  recordStuff=c()
  recordName= c()
  recordSample=c()
  recordValue=c()
  for(i in 1:nbObs){
    for(j in 1:nbSamples) {
      recordStuff=c(recordStuff,as.character(colnames(abundanceTable)[i]))
      recordSample=c(recordSample,as.character(rownames(abundanceTable)[j]))
      if(percent) recordValue=c(recordValue,round(((abundanceTable[j,i]/maxOfAll[i])*100),3))
      else recordValue=c(recordValue,abundanceTable[j,i])
    }
  }

  newTable=data.table(recordSample,recordStuff,recordValue)
  setnames(newTable,colnames(newTable),c('sample','Stuff','value'))
  return(newTable)
}

# First try, GO slim + barcharts, one category
# TODO: Big-ass script to cope with all situations

# Load libraries
library(data.table)

# A small operator that is quite useful
"%w/o%" <- function(x, y) x[!x %in% y] # ->  x without y 

# Create tables to retrieve names and type from a GO term
GOslimTable <- read.table("R/GOslim_names.csv", sep = ",", quote = "\"", 
                          col.names = c("GO", "function", "type"), colClasses = c("character", "character", "character"))


# Function to divide abundance table for each category
divideAbTable = function(abTable,category) {
  categoryGo <- data.frame(GOslimTable[, 2][GOslimTable[3] == category], row.names = GOslimTable[, 
                                                                                                 1][GOslimTable[3] == category], stringsAsFactors = FALSE)
  # Fixing shitty column names
  colnames(categoryGo) <- c("name")
  abTableCategory <- abTable[, colnames(abTable) %in% c(row.names(categoryGo))]
  return(abTableCategory)
}

# Go from DIVIDED abundance table to edible data for rChart  
# TODO : Percentage stuff
rChartsFixingGO = function(abundanceTable,percent=FALSE){
  nbSamples = length(rownames(abundanceTable))
  nbObs = length(colnames(abundanceTable))
  maxOfAll = rowSums(abundanceTable)
  recordGO=c()
  recordName= c()
  recordSample=c()
  recordValue=c()
  for(i in 1:nbObs){
    for(j in 1:nbSamples) {
      recordGO=c(recordGO,colnames(abundanceTable)[i])
      recordSample=c(recordSample,rownames(abundanceTable)[j])
      if(percent) recordValue=c(recordValue,round(((abundanceTable[j,i]/maxOfAll[i])*100),3))
      else recordValue=c(recordValue,abundanceTable[j,i])
    }
  }
  # GO names ...?
  for(k in 1:length(recordGO)){
    index = grep(recordGO[k],GOslimTable$GO)
    recordName <- c(recordName,GOslimTable[index,2])
    
  }
  
  newTable=data.table(recordSample,recordGO,recordName,recordValue)
  setnames(newTable,colnames(newTable),c('sample','GO','GOname','value'))
  return(newTable)
}

abTableToPercentage = function(abundanceTable){
  percentAbTable = abundanceTable
  maxOfAll = rowSums(percentAbTable)
  for(i in 1:length(maxOfAll)) {
    percentAbTable[i,] = round(((percentAbTable[i,]/maxOfAll[i])*100),3)
  }
  
  return(percentAbTable)}

dataChart = rChartsFixingGO(divideAbTable(abTableToPercentage(a),'biological_process'))
print(dataChart)
width = (length(unique(dataChart$GO))*length(unique(dataChart$sample)))*10
options(RCHART_WIDTH = width)
print(width)
options(RCHART_HEIGHT = 700)
chart <- hPlot(value~GO,data = dataChart, group='sample', type='column',title='Number of matches by GO slim')
    chart$chart(type='column')
    chart$plotOptions(series=list(borderWidth=0,pointPadding=0,groupPadding=0))
    chart$xAxis(categories = c(unique(dataChart$GOname)),labels=list(rotation=-45,formatter = "#! function() {  if(this.value.length>20) return this.value.substr(0,20) + '...'; else return this.value ; } !#"),title=list(text='GO slim assiociated with biological process'))
    chart$yAxis(title=list(text='Matches'))
    chart$tooltip(formatter = "#! function() { var serie = this.series; var s = '<b>'+this.key+'</b><br>'; s+= this.x+'<br>'; s+= '<b>'+'<span style=\"color:'+serie.color+'\">'+serie.options.name+'</span>:'+'</b> '+this.y+' matches';  return s; } !#")
    chart$chart(zoomType = "x",spacingLeft=40)
chart$exporting(enabled = T)
chart$save(paste('R/Graphs/',name,'.htm',sep=''),cdn=TRUE)

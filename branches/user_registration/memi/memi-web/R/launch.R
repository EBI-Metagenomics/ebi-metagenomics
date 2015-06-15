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

dataChart = rChartsFixing(a)
print(dataChart)
width = (length(unique(dataChart$Stuff))*length(unique(dataChart$sample)))*7
options(RCHART_WIDTH = width)
print(width)
options(RCHART_HEIGHT = 700)
chart = hPlot(value~Stuff, data = dataChart, type = "column", group = "sample",title="TEST TITLE")
chart$exporting(enabled = T)
chart$save(paste('R/Graphs/',name,'.htm',sep=''),cdn=TRUE)

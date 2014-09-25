# Take parameters from command line
parameters <- commandArgs(TRUE)
print(length(parameters))
fileName <- (parameters[4])
print(fileName)

# rCharts + data.table libraries
library(rCharts)
library(data.table)

# Find abudnance table
aCoolTable = read.table(paste('R/tables/',fileName,'.txt',sep=''),sep='\t',quote='',header=TRUE,row.names=1)
print(aCoolTable)

# Test to process data and use rCharts to get a graph out of it
rChartsFixing = function(abundanceTable,percent=FALSE){
  nbSamples = length(rownames(abundanceTable))
  nbObs = length(colnames(abundanceTable))
  maxOfAll = rowSums(abundanceTable)
  recordIPR=c()
  recordSample=c()
  recordValue=c()
  for(i in 1:nbObs){
    for(j in 1:nbSamples) {
      recordIPR=c(recordIPR,colnames(abundanceTable)[i])
      recordSample=c(recordSample,rownames(abundanceTable)[j])
      if(percent) recordValue=c(recordValue,round(((abundanceTable[j,i]/maxOfAll[i])*100),3))
      else recordValue=c(recordValue,abundanceTable[j,i])
    }
  }

  newTable=data.table(recordSample,recordIPR,recordValue)
  setnames(newTable,colnames(newTable),c('sample','IPR','value'))
  return(newTable)
}

rChartsData = rChartsFixing(aCoolTable)

# Now, create and save the graph
graph = hPlot(value~sample,data = overviewTable, type = 'bar', title='Overview')
graph$exporting(enabled = T)

# Save
h1$save(paste('R/Graphs/',fileName,'.htm',sep=''),cdn=TRUE)

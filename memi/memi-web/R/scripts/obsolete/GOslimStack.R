# Create the right charts if user has chosen go slim and stacked barcharts

# Console log
message(paste(Sys.time(),'[R - Message] Launched R script GOslimStack.R'))

# A small operator that is quite useful
"%w/o%" <- function(x, y) x[!x %in% y] # ->  x without y 

# Load libraries
library(data.table)

# Go from DIVIDED abundance table to edible data for rChart  
rChartsFixing = function(abundanceTable,percent=FALSE){
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

rChartsStacked = function(abundanceTable,threshold){
  newTable = abundanceTable
  maxOfAll = rowSums(abundanceTable)
  for(i in 1:length(maxOfAll)) {
    newTable[i,] = round(((newTable[i,]/maxOfAll[i])*100),3)
  }
  
  newTable = rChartsFixing(newTable)
  # Threshold handling
  newTable$GO[newTable$value < threshold] = 'Other'
  newTable$GOname[newTable$value < threshold] = 'Other'
  newTable = aggregate(value~sample+GO+GOname,data=newTable,FUN=sum)
  
  # Bug with rCharts : Need to recreate the missing values (values that were under threshold in some sample(s) and not in others)
  GO = unique(newTable$GO)
  samples = unique(newTable$sample)
  for(j in GO){
    subTable = newTable[newTable$GO==j,]
    if((dim(subTable)[1])<length(samples)){
      addData = data.frame(samples%w/o%subTable$sample,j,subTable$GOname[1],0)
      colnames(addData)=c('sample','GO','GOname','value')
      newTable = rbind(newTable,addData)
    }
  }
  newTable = as.data.table(newTable)
  return(newTable)
}

# Final function. Yay.
CreateGraph = function(abundanceTable,threshold) {
  dataChart = rChartsStacked(abTable,threshold)
  chartWidth = 900+length(unique(dataChart$sample))*50
  chart <- hPlot(value~sample,data = dataChart[dataChart$GOname!='Other'], group='GOname',type = 'column',group.na = 'NA\'s',title = 'Most frequent GO slim by sample')
  chart$chart(width = chartWidth, height = 700)
  chart$series(data = dataChart$value[dataChart$GOname=='Other'], name = paste('other (less than ',as.character(threshold),'%)',sep=''),type='column', color = '#B9B9B9')
  chart$plotOptions(column = list(stacking = "percent")) 
  chart$xAxis(categories=c(unique(dataChart$sample)))
  chart$yAxis(title=list(text='Relative abundance (%)'))
  chart$legend(layout='vertical',align='right',verticalAlign='top',x=-10,y=100)
  chart$colors(EMGcolors)
  chart$exporting(enabled = T)
  return(chart)
}

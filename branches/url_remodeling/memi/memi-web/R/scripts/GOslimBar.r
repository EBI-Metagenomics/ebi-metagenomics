# Create the right charts if user has chosen go slim and barcharts

# Console log
message(paste(Sys.time(),'[Message] Launched script GOslimBar.r'))

# Load libraries
library(data.table)

# Create tables to retrieve names and type from a GO term
GOslimTable <- read.table("R/GOslim_names.csv", sep = ",", quote = "\"", 
                          col.names = c("GO", "function", "type"), colClasses = c("character", "character", "character"))

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

# Go from abundance table to edible data for rChart  
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

abTableToPercentage = function(abundanceTable){
  percentAbTable = abundanceTable
  maxOfAll = rowSums(percentAbTable)
  for(i in 1:length(maxOfAll)) {
    percentAbTable[i,] = round(((percentAbTable[i,]/maxOfAll[i])*100),1)
  }
  
  return(percentAbTable)
}

# Final function. Yay.
CreateGraphForCategory = function(abTable,category) {
  # Correct names according to category of GO Slim
  categoryIndice = 0
  if(category=='biological_process') categoryIndice = 1
  if(category=='cellular_component') categoryIndice = 2
  if(category=='molecular_function') categoryIndice = 3
  correctNames c('Biological process','Cellular component','Molecular function')

  # EMG website colors
  EMGcolors <- c('#058DC7','#50B432','#ED561B','#EDEF00','#24CBE5','#64E572','#FF9655','#FFF263','#6AF9C4','#DABE88')

  # Used data
  dataChart = rChartsFixing(abTableToPercentage(divideAbTable(abTable,category)))

  # Chart dimensions / options / customization
  chartHeight = (length(unique(dataChart$GO)))*25+(length(unique(dataChart$sample)))*5
  options(RCHART_WIDTH = 360)
  options(rcharts.cdn = TRUE)
  options(RCHART_HEIGHT = chartHeight)
  axisFormat = "#! function() {  if(this.value.length>20) return this.value.substr(0,20) + '...'; else return this.value ; } !#"
  tooltipFormat = "#! function() { var serie = this.series; var s = '<b>'+this.x+'</b><br>'; s+='<br>'; s+= '<b>'+'<span style=\"color:'+serie.color+'\">'+serie.options.name+'</span>:'+'</b> '+this.y+' %';  return s; } !#"
  # tooltipPosition = "#! function() { return { x: 10, y: 35 }; !#"

  # Actual chart creation
  chart <- hPlot(value~GO,data = dataChart, group='sample', type='bar',title= correctNames[categoryIndice])
  #chart$chart(type='column')
  chart$plotOptions(series=list(borderWidth=0,pointPadding=0,groupPadding=1))
  chart$xAxis(categories = c(unique(dataChart$GOname)),labels=list(rotation=0,formatter = axisFormat),title=list(text=paste('GO slim',category,sep=' ')))
  chart$yAxis(title=list(text='Matches'))
  chart$tooltip(formatter = tooltipFormat)
  chart$chart(zoomType = "x",spacingLeft=40)
  chart$colors(EMGcolors)
  chart$exporting(enabled = T)
  return(chart)
}

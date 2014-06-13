# Create the right charts if user has chosen go slim and barcharts

# Console log
message(paste(Sys.time(),'[R - Message] Launched R script GOslimBar_v2.R'))

# Load libraries
library(data.table)

# Function to divide abundance table for each category
divideAbTable = function(abTable,category) {
  abTableCategory <- abTable[,grepl(category,colnames(abTable))]
  return(abTableCategory)
}

# Go from divided abundance table to edible data for rChart  
rChartsFixing = function(abundanceTable,percent=FALSE){
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
      if(percent) recordValue=c(recordValue,round(((abundanceTable[j,i]/maxOfAll[i])*100),3))
      else recordValue=c(recordValue,abundanceTable[j,i])
    }
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
  correctNames = c('Biological process','Cellular component','Molecular function')

  # Used data
  dataChart = rChartsFixing(abTableToPercentage(divideAbTable(abTable,category)))
  
  # Chart dimensions / options / customization
  chartHeight = 750+(length(unique(dataChart$sample)))*50
  axisFormat = "#! function() {  if(this.value.length>20) return this.value.substr(0,20) + '...'; else return this.value ; } !#"
  tooltipFormat = "#! function() { var serie = this.series; var s = '<b>'+this.x+'</b><br>'; s+='<br>'; s+= '<b>'+'<span style=\"color:'+serie.color+'\">'+'\u25A0 '+serie.options.name+'</span>:'+'</b> '+this.y+' %';  return s; } !#"
  # tooltipPosition = "#! function() { return { x: 10, y: 35 }; !#"

  # Actual chart creation
  chart <- hPlot(value~GO,data = dataChart, group='sample', type='bar',title= correctNames[categoryIndice])
  # chart$chart(type='column')
  chart$plotOptions(series=list(borderWidth=0,pointPadding=0,groupPadding=0.1))
  chart$xAxis(categories = c(unique(dataChart$GOname)),labels=list(rotation=0,formatter = axisFormat), lineColor = '#C0C0C0', tickColor = '#C0C0C0')
  chart$yAxis(title=list(text='Match (%)'), endOnTick = FALSE, maxPadding = 0)
  chart$tooltip(backgroundColor = '#FFFFFF', formatter = tooltipFormat)
  chart$chart(zoomType = "x",spacingLeft=40, marginLeft=200,  width = 360, height = chartHeight)
  chart$colors(EMGcolors)
  # chart$legend(verticalAlign = 'top')
  # chart$exporting(enabled = T)
  return(chart)
}

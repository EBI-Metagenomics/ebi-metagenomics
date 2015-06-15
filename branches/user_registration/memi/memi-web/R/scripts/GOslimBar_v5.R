# Create the right charts if user has chosen go slim and barcharts

# Console log
message(paste(Sys.time(),'[R - Message] Launched R script GOslimBar_v2.R'))

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
CreateBarsForCategory = function(abTable,category) {
  # Correct names according to category of GO
  categoryIndice = 0
  if(category=='biological_process') categoryIndice = 1
  if(category=='cellular_component') categoryIndice = 2
  if(category=='molecular_function') categoryIndice = 3
  correctNames = c('Biological process','Cellular component','Molecular function')
  labelSize = c(260,168,230)

  # Used data
  dataChart = rChartsFixing(abTableToPercentage(divideAbTable(abTable,category)))
  
  # Chart dimensions / options / customization
  chartHeight = 750 +  200*length(unique(dataChart$sample))#*length(unique(dataChart$GO))#+3*length(unique(dataChart$sample))
  axisFormat = "#! function() {  if(this.value.length>20) return this.value.substr(0,20) + '...'; else return this.value ; } !#"
  tooltipFormat = "#! function() { var serie = this.series; var s = '<b>'+this.x+'</b><br>'; s+='<br>'; s+= '<b>'+'<span style=\"color:'+serie.color+'\">'+'\u25A0 '+serie.options.name+'</span>:'+'</b> '+this.y+' %';  return s; } !#"
  tooltipPosition = "#! function(boxWidth, boxHeight, point) {              // Set up the variables \n            var chart = this.chart, \n                plotLeft = chart.plotLeft, \n                plotTop = chart.plotTop, \n                plotWidth = chart.plotWidth, \n                plotHeight = chart.plotHeight, \n                distance = 5, \n                pointX = point.plotX, \n                pointY = point.plotY, \n                x = pointX + plotLeft + (chart.inverted ? distance : -boxWidth - distance), \n                y = pointY - boxHeight + plotTop + 15, \n                // 15 means the point is 15 pixels up from the bottom of the tooltip \n                alignedRight; \n            // It is too far to the left, adjust it \n            if (x < 7) { \n                x = plotLeft + pointX + distance; \n            } \n            // Test to see if the tooltip is too far to the right, \n            // if it is, move it back to be inside and then up to not cover the point. \n            if ((x + boxWidth) > (plotLeft + plotWidth)) { \n                x -= (x + boxWidth) - (plotLeft + plotWidth); \n                y = pointY - boxHeight + plotTop - distance; \n                alignedRight = true; \n            } \n            // If it is now above the plot area, align it to the top of the plot area \n            if (y < plotTop + 5) { \n                y = plotTop + 5; \n                // If the tooltip is still covering the point, move it below instead \n                if (alignedRight && pointY >= y && pointY <= (y + boxHeight)) { \n                    y = pointY + plotTop + distance; // below \n                } \n            } \n            // Now if the tooltip is below the chart, move it up. It's better to cover the \n            // point than to disappear outside the chart. #834. \n            if (y + boxHeight > plotTop + plotHeight) { \n                y = mathMax(plotTop, plotTop + plotHeight - boxHeight - distance); // below \n            } \n            return { \n                x: x+20, \n                y: y+10 \n            }; \n        } !#"

  # Actual chart creation
  chart <- hPlot(value~GO,data = dataChart, group='sample', type='bar')

  chart$title(text=correctNames[categoryIndice],floating=FALSE, style=list(fontSize=15, fontWeight='bold'))
  # chart$chart(type='column')
  chart$legend(enabled=FALSE) #itemStyle=list(fontSize='11px', fontWeight='regular', color='#606060', title = list(text='Sample list<br/><span style="font-size: 9px; color: #666; font-weight: normal">(Click to hide sample)</span>', style = list(fontStyle='regular')))
  chart$plotOptions(series=list(borderWidth=1,borderColor='#777777',pointPadding=0,groupPadding=0.1))
  chart$xAxis(categories = c(unique(dataChart$GOname)),labels=list(step = 1, rotation=0, style = list(fontSize = '11px', width = labelSize[categoryIndice]), formatter = axisFormat), lineColor = '#595959', tickColor = '')
  chart$yAxis(title=list(text='Match (%)'), endOnTick = FALSE, maxPadding = 0)
  chart$tooltip(backgroundColor = '#FFFFFF', positioner = tooltipPosition, backgroundColor = 'white', headerFormat = '{point.x}<br/>', pointFormat = '<span style="color:{series.color}">\u25A0</span> {series.name}: <b>{point.y} % matches</b><br/>',useHTML=TRUE) #, formatter = tooltipFormat
  chart$chart(zoomType = "x",spacingLeft=20,height = chartHeight, animation = FALSE)
  chart$colors(EMGcolors)
  chart$addParams(width=NULL, height = chartHeight)
  #chart$exporting(filename='custom-file-name')
  # chart$legend(verticalAlign = 'top')
  # chart$exporting(enabled = T)
  chartCode <- paste(capture.output(chart$print(paste0(category,'_bars'))),collapse = '\n')
  #chartCode <- paste(strsplit(chartCode,split='\n\"width\":.*400,')[[1]],collapse='')
  return(chartCode)
}

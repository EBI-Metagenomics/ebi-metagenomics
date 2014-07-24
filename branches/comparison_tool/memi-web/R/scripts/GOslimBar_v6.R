# Create the right charts if user has chosen go slim and barcharts

# Console log
message(paste(Sys.time(), "[R - Message] Launched R script GOslimBar_v6.R"))


CreateBarsForCategory <- function(abTable, category) {
  # Correct names according to category of GO
  categoryIndice <- 0
  if (category == "biological_process") 
    categoryIndice <- 1
  if (category == "cellular_component") 
    categoryIndice <- 2
  if (category == "molecular_function") 
    categoryIndice <- 3
  correctNames <- c("Biological process", "Cellular component", "Molecular function")
  labelSize <- c(200, 200, 200)
  
  # Used data
  dataChart <- RchartsFix(AbTablePercent(DivideAbTable(abTable, category)))
  
  # Chart dimensions / options / customization / 470 factor -> exactly 6px width for the bar in BP
  chartHeight <- 470 * length(unique(dataChart$sample))
  axisFormat <- "#! function() {  if(this.value.length>30) return this.value.substr(0,30) + '...'; else return this.value ; } !#"
  tooltipFormat <- "#! function() { var serie = this.series; var s = '<b>'+this.x+'</b><br>'; s+='<br>'; s+= '<b>'+'<span style=\"color:'+serie.color+'\">'+'■ '+serie.options.name+'</span>:'+'</b> '+this.y+' %';  return s; } !#"
  tooltipPosition <- "#! function(boxWidth, boxHeight, point) { \n  return { \n  x: point.plotX+120,\n    y: point.plotY +10};\n   } !#"
  # Actual chart creation
  chart <- hPlot(value ~ GO, data = dataChart, group = "sample", type = "bar")
  
  chart$title(text = correctNames[categoryIndice], floating = FALSE, margin= 0, style = list(fontSize = 13, fontWeight = "bold"))
  chart$legend(enabled = FALSE)
  chart$plotOptions(series = list(borderWidth = 1, borderColor = "#686868", pointPadding = 0, groupPadding = 0.1))
  chart$xAxis(categories = c(unique(dataChart$GOname)), labels = list(step = 1, rotation = 0, formatter = axisFormat, style = list(fontSize = "11px",
    width = labelSize[categoryIndice])), lineColor = "#595959", tickColor = "")
  chart$yAxis(opposite = TRUE, gridLineColor = "#e0e0e0", title = list(text = "Match (%)", style = list(color = "#a0a0a0")), endOnTick = TRUE, maxPadding = 0,  labels = list( y=-6,  style = list(color = "#a0a0a0")))
  chart$tooltip(backgroundColor = "white", headerFormat = "{point.x}<br/>",
    pointFormat = "<span style=\"color:{series.color}\">&#9632;</span> <span style=\"font-size:88%;\">{series.name}: <strong>{point.y} %</strong></span>",
    useHTML = TRUE)  #, formatter = tooltipFormat         , positioner = tooltipPosition
  chart$chart(zoomType = "x", spacingLeft = 20, height = chartHeight, animation = FALSE)
  chart$colors(EMGcolors)
  chart$addParams(width = NULL, height = chartHeight)
  # chart$exporting(filename='custom-file-name') chart$legend(verticalAlign = 'top')
  # chart$exporting(enabled = T)
  chartCode <- paste(capture.output(chart$print(paste0(category, "_bars"))), collapse = "\n")
  # chartCode <- paste(strsplit(chartCode,split='\n\'width\':.*400,')[[1]],collapse='')
  return(chartCode)
} 


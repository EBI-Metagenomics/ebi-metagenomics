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
  labelSize <- c(260, 168, 230)
  
  # Used data
  dataChart <- RchartsFix(AbTablePercent(DivideAbTable(abTable, category)))
  
  # Chart dimensions / options / customization / 470 factor -> exactly 6px width for the bar in BP
  chartHeight <- 470 * length(unique(dataChart$sample))
  axisFormat <- "#! function() {  if(this.value.length>20) return this.value.substr(0,20) + '...'; else return this.value ; } !#"
  tooltipFormat <- "#! function() { var serie = this.series; var s = '<b>'+this.x+'</b><br>'; s+='<br>'; s+= '<b>'+'<span style=\"color:'+serie.color+'\">'+'■ '+serie.options.name+'</span>:'+'</b> '+this.y+' %';  return s; } !#"
  tooltipPosition <- "#! function(boxWidth, boxHeight, point) {              // Set up the variables \n            var chart = this.chart, \n                plotLeft = chart.plotLeft, \n                plotTop = chart.plotTop, \n                plotWidth = chart.plotWidth, \n                plotHeight = chart.plotHeight, \n                distance = 5, \n                pointX = point.plotX, \n                pointY = point.plotY, \n                x = pointX + plotLeft + (chart.inverted ? distance : -boxWidth - distance), \n                y = pointY - boxHeight + plotTop + 15, \n                // 15 means the point is 15 pixels up from the bottom of the tooltip \n                alignedRight; \n            // It is too far to the left, adjust it \n            if (x < 7) { \n                x = plotLeft + pointX + distance; \n            } \n            // Test to see if the tooltip is too far to the right, \n            // if it is, move it back to be inside and then up to not cover the point. \n            if ((x + boxWidth) > (plotLeft + plotWidth)) { \n                x -= (x + boxWidth) - (plotLeft + plotWidth); \n                y = pointY - boxHeight + plotTop - distance; \n                alignedRight = true; \n            } \n            // If it is now above the plot area, align it to the top of the plot area \n            if (y < plotTop + 5) { \n                y = plotTop + 5; \n                // If the tooltip is still covering the point, move it below instead \n                if (alignedRight && pointY >= y && pointY <= (y + boxHeight)) { \n                    y = pointY + plotTop + distance; // below \n                } \n            } \n            // Now if the tooltip is below the chart, move it up. It's better to cover the \n            // point than to disappear outside the chart. #834. \n            if (y + boxHeight > plotTop + plotHeight) { \n                y = mathMax(plotTop, plotTop + plotHeight - boxHeight - distance); // below \n            } \n            return { \n                x: x+20, \n                y: y+10 \n            }; \n        } !#"
  # Actual chart creation
  chart <- hPlot(value ~ GO, data = dataChart, group = "sample", type = "bar")
  
  chart$title(text = correctNames[categoryIndice], floating = FALSE, style = list(fontSize = 13, fontWeight = "bold"))
  chart$legend(enabled = FALSE)
  chart$plotOptions(series = list(borderWidth = 1, borderColor = "#686868", pointPadding = 0, groupPadding = 0.1))
  chart$xAxis(categories = c(unique(dataChart$GOname)), labels = list(step = 1, rotation = 0, style = list(fontSize = "11px", 
    width = labelSize[categoryIndice]), formatter = axisFormat), lineColor = "#595959", tickColor = "")
  chart$yAxis(title = list(text = "Match (%)"), endOnTick = FALSE, maxPadding = 0)
  chart$tooltip(backgroundColor = "white", headerFormat = "{point.x}<br/>", 
    pointFormat = "<span style=\"color:{series.color}\">&#9632;</span> {series.name}: <strong>{point.y} %</strong><br/>",
    useHTML = TRUE)  #, formatter = tooltipFormat, positioner = tooltipPosition
  chart$chart(zoomType = "x", spacingLeft = 20, height = chartHeight, animation = FALSE)
  chart$colors(EMGcolors)
  chart$addParams(width = NULL, height = chartHeight)
  # chart$exporting(filename='custom-file-name') chart$legend(verticalAlign = 'top')
  # chart$exporting(enabled = T)
  chartCode <- paste(capture.output(chart$print(paste0(category, "_bars"))), collapse = "\n")
  # chartCode <- paste(strsplit(chartCode,split='\n\'width\':.*400,')[[1]],collapse='')
  return(chartCode)
} 


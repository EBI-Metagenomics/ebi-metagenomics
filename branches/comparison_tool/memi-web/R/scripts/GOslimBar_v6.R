# Console log
message(paste(Sys.time(), "[R - Message] Launched R script GOslimBar_v6.R"))


CreateBarsForCategory <- function(abTable, category) {
  # Creates barcharts for a category of GO terms given a general abundance table.
  #
  # Args:
  #   abTable: An abundance table for selected samples. Rows: Samples, Columns: observations. 
  #            The function has to be used with the GENERAL abundance table (all categories)  
  #   category: The GO category for which we want to generate barcharts.
  #
  # Returns:
  #   Needed HTML code to insert barcharts visualization for one category on the result page 
  #   (div and JS)

  # Correct names according to category of GO Slim
  # 'categoryIndice' gives an 'indice' depending on the current GO category.
  # Used to know which title has to be used for the current chart
  categoryIndice <- 0
  if (category == "biological_process") 
    categoryIndice <- 1
  if (category == "cellular_component") 
    categoryIndice <- 2
  if (category == "molecular_function") 
    categoryIndice <- 3
  correctNames <- c("Biological process", "Cellular component", "Molecular function")

  labelSize <- c(200, 200, 200) # Size of X axis labels for each chart (same order, size in px)
  
  # Used data for the chart. See the 'RchartsFix()' function in launch script for more details
  dataChart <- RchartsFix(AbTablePercent(DivideAbTable(abTable, category)))
  # Initially the data is ordered by GO term identifier,
  # the 'setkey()' function enables ordering of the data by GO name (alphabetical order) instead of GO identifier.
  setkey(dataChart,GOname) # THIS IS WHERE IT BUGS AS CAPITAL LETTERS FIRST IN ORDER  SEB

  ########################
  #### Chart creation ####
  ########################
  chart <- hPlot(value ~ GOname, # Match percentage for each GO term
                 data = dataChart, # ... from the 'dataChart' object
                 group = "sample", # Group the data by sample
                 type = "bar") # Chart type
  
  ########################
  #### Chart settings ####
  ########################
  # Chart dimensions: 470 factor -> exactly 6px width for the bar in BP
  chartHeight <- 470 * length(unique(dataChart$sample))
  # Formatter function for the X axis. If axis text is too long, replace the end by '...'
  axisFormat <- "#! function() {  if(this.value.length>30) return this.value.substr(0,30) + '...'; else return this.value ; } !#"
  # Formatter function for the tooltip. NOT USED. 
  tooltipFormat <- "#! function() { var serie = this.series; var s = '<b>'+this.x+'</b><br>'; s+='<br>'; s+= '<b>'+'<span style=\"color:'+serie.color+'\">'+'■ '+serie.options.name+'</span>:'+'</b> '+this.y+' %';  return s; } !#"
  # Modify tooltip position so it is always well placed. NOT WORKING PROPERLY.
  tooltipPosition <- "#! function(boxWidth, boxHeight, point) { \n  return { \n  x: point.plotX+120,\n    y: point.plotY +10};\n   } !#"

  chart$title(text = correctNames[categoryIndice], # Title text
              floating = FALSE, # Avoid overlapping of chart and title
              margin= 0, # No margin
              style = list(fontSize = 13, fontWeight = "bold")) # Title style
  chart$legend(enabled = FALSE) # Legend is enabled as another one is created in the result page
  chart$plotOptions(bar = list (dataLabels = list (enabled = FALSE, color = "#a0a0a0")), series = list(borderWidth = 1,
                                  borderColor = "#686868",
                                  pointPadding = 0, # Space between the bars of a same GO term
                                  groupPadding = 0.1)) # Space between two GO terms
  chart$xAxis(categories = c(unique(dataChart$GOname)), # Correct name on X axis
              labels = list(step = 1, # Every GO term displayed 
                            rotation = 0, # labels written horizontally
                            formatter = axisFormat, # Format of X axis label's text
                            style = list(fontSize = "11px", width = labelSize[categoryIndice])),
                                         lineColor = "#595959", tickColor = "") # Style for X axis
  chart$yAxis(opposite = TRUE, # Put percentage values / axis title on top of the chart
              gridLineColor = "#e0e0e0", # Color of vertical lines in the background
              title = list(text = "Match (%)", # Axis title
              style = list(color = "#a0a0a0")), 
              endOnTick = TRUE, 
              maxPadding = 0,  
              labels = list(y=-6, style = list(color = "#a0a0a0"))) # Label position and style
  chart$tooltip(backgroundColor = "white",
                headerFormat = "{point.x}<br/>", # Tootltip header
                pointFormat = "<span style=\"color:{series.color}\">&#9632;</span> <span style=\"font-size:88%;\">{series.name}: <strong>{point.y} %</strong></span>", # Tootltip content
                useHTML = TRUE)  # Uncomment to use: , formatter = tooltipFormat         , positioner = tooltipPosition
  chart$chart(zoomType = "x", # Enables zoom for X coordinates
              spacingLeft = 20, # Adds space on the edge of the graph
              height = chartHeight, # fixed height (calculated before)
              animation = FALSE) # Disables animation as it made the page laggy on my local machine
  chart$colors(EMGcolors) # Uses EMGcolors (defined in the 'launch' script)
  # Setting width parameter to NULL disable the default width given to charts
  # Dimensions can therefore be controlled with css and charts can be resizable.
  chart$addParams(width = NULL, height = chartHeight)
  #chart$exporting(enabled = T) # Uncomment to enable export

  #######################
  ###### HTML code ######
  #######################
  chartCode <- paste(capture.output(chart$print(paste0(category, "_bars"))), collapse = "\n")
  return(chartCode)
} 


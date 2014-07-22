# Console log
# message(paste(Sys.time(), "[R - Message] Launched R script GOslimStack_v5.R"))

FixRchartsStacked <- function(abundanceTable, threshold) {
  newTable <- abundanceTable
  maxOfAll <- rowSums(abundanceTable)
  for (i in 1:length(maxOfAll)) {
    newTable[i, ] <- round(((newTable[i, ]/maxOfAll[i]) * 100), 3)
  }
  newTable <- RchartsFix(newTable)
  # Threshold handling
  newTable$GO[newTable$value < threshold] <- "Other"
  newTable$GOname[newTable$value < threshold] <- "Other"
  newTable <- aggregate(value ~ sample + GO + GOname, data = newTable, FUN = sum)
  
  # Bug with rCharts : Need to recreate the missing values (values that were under threshold in some sample(s) and not in
  # others)
  GO <- unique(newTable$GO)
  samples <- unique(newTable$sample)
  for (j in GO) {
    subTable <- newTable[newTable$GO == j, ]
    if ((dim(subTable)[1]) < length(samples)) {
      addData <- data.frame(samples %w/o% subTable$sample, j, subTable$GOname[1], 0)
      colnames(addData) <- c("sample", "GO", "GOname", "value")
      newTable <- rbind(newTable, addData)
    }
  }
  newTable <- as.data.table(newTable)
  return(newTable)
}

# Final function. For one category, from general table.
CreateStackColForCategory <- function(abundanceTable, threshold, category) {
  
  # Correct names, h3 names and h3 id according to category of GO Slim
  categoryIndice <- 0
  if (category == "biological_process") 
    categoryIndice <- 1
  if (category == "cellular_component") 
    categoryIndice <- 2
  if (category == "molecular_function") 
    categoryIndice <- 3
  correctNames <- c("biological process", "cellular component", "molecular function")
  h3Names <- c("Biological process", "Cellular component", "Molecular function")
  h3Id <- c("stack_bio_title", "stack_cell_title", "stack_mol_title")
  
  dataChart <- FixRchartsStacked(DivideAbTable(abundanceTable, category), threshold)
  chartWidth <- 900 + length(unique(dataChart$sample)) * 50
  chart <- hPlot(value ~ sample, data = dataChart[dataChart$GOname != "Other"], group = "GOname", type = "column", group.na = "NA's", 
    title = paste0("Most frequent GO terms", " (", correctNames[categoryIndice], ")"))  # , subtitle = correctNames[categoryIndice])
  chart$chart(height = 444)
  chart$title(text = paste0("Most frequent GO terms", " (", correctNames[categoryIndice], ")"), floating = FALSE, style = list(fontSize = 13, fontWeight = "bold"))
  chart$series(data = dataChart$value[dataChart$GOname == "Other"], name = paste("other (less than ", as.character(threshold), 
    "%)", sep = ""), type = "column", color = "#B9B9B9")
  chart$plotOptions(column = list(stacking = "percent"))
  chart$xAxis(categories = c(unique(dataChart$sample)), lineColor = "#595959", tickColor = "")
  chart$yAxis(title = list(text = "Relative abundance (%)"))
  chart$legend(layout = "vertical", align = "right", verticalAlign = "top", x = 0, y = 26, width = 360, itemStyle = list(fontSize = "11px",
    fontWeight = "regular", color = "#606060"), title = list(text = "GO terms list<br/><span style=\"font-size: 9px; color: #666; font-weight: normal; font-style: italic;\">Click to hide</span>",
    style = list(fontStyle = "regular")))
  chart$tooltip(backgroundColor = "white", headerFormat = "{point.x}<br/>",
     pointFormat = "<span style=\"color:{series.color}\">&#9632;</span> {series.name}: <strong>{point.y} %</strong><br/>",
     useHTML = TRUE)  #, formatter = tooltipFormat, positioner = tooltipPosition
  chart$colors(EMGcolors)
  chart$addParams(width = NULL, height = NULL)
  chart$exporting(enabled = T)
  chartCode <- paste(capture.output(chart$print(paste0("stack_", category))), collapse = "\n")
  chartCode <- paste(paste0("<h4 id=\"", h3Id[categoryIndice], "\">", h3Names[categoryIndice], "</h3>"), chartCode, sep = "\n")
  return(chartCode)
}

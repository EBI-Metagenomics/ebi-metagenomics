# Console log
message(paste(Sys.time(), "[R - Message] Launched R script Overview_v6.R"))

# Function to generate rCharts-compatible data for the 'Overview' tab
OverallSummary <- function(abTable) {
  abTable_bio <- DivideAbTable(abTable, "biological_process")
  abTable_cell <- DivideAbTable(abTable, "cellular_component")
  abTable_mol <- DivideAbTable(abTable, "molecular_function")

  nbSamples <- length(rownames(abTable_bio))
  matches <- c(rowSums(abTable_bio), rowSums(abTable_cell), rowSums(abTable_mol))
  types <- c(rep("Biological Process", nbSamples), rep("Cellular Component", nbSamples), rep("Molecular Function", nbSamples))
  samples <- c(rep(rownames(abTable_bio), 3))

  newTable <- data.table(samples, types, matches)
  setnames(newTable, colnames(newTable), c("sample", "GOtype", "value"))
  return(newTable)
}

GenerateOverview <- function(abTable) {
  overviewTable <- OverallSummary(abTable)
  chartCode <- ""
  a <- hPlot(value ~ sample, data = overviewTable, type = "bar", group = "GOtype", title = "Overview of GO slim")
  a$tooltip(shared = TRUE)
  a$colors(EMGcolors)
  a$chart(width = 900, height = length(unique(overviewTable$sample)) * 120)
  a$exporting(enabled = TRUE)
  options(rcharts.cdn = TRUE)
  a$yAxis(title = list(text = "Match (absolute value)"))
  a$xAxis(title = list(text = "Sample"), categories = unique(overviewTable$sample))
  chartCode <- paste(capture.output(a$print("OverviewChart")), collapse = "\n")
  # This is the proper HTML code containing everything needed (except style/JS but we define this inside the Java web app)
  return(chartCode)
  
}

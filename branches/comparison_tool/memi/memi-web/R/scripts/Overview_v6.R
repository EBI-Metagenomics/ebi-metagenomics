# Console log
# Note: This script is not used anymore as this visualization was not providing really useful information. 
message(paste(Sys.time(), "[R - Message] Launched R script Overview_v6.R"))

OverallSummary <- function(abTable) {
  # Function that generates rCharts-compatible data for the 'Overview' tab (flat table)
  #
  # Args:
  #   abTable: An abundance table for selected samples. Rows: samples, Columns: observations. 
  # Returns:
  #   A table with the sum of matches for each category, for each sample

  # Get subtable for each category
  abTable_bio <- DivideAbTable(abTable, "biological_process")
  abTable_cell <- DivideAbTable(abTable, "cellular_component")
  abTable_mol <- DivideAbTable(abTable, "molecular_function")

  nbSamples <- length(rownames(abTable_bio)) # Number of samples
  matches <- c(rowSums(abTable_bio), rowSums(abTable_cell), rowSums(abTable_mol)) # Sum of all matches for each term of each category
  types <- c(rep("Biological Process", nbSamples), rep("Cellular Component", nbSamples), rep("Molecular Function", nbSamples)) # Repeating GO types (good number of rows)
  samples <- c(rep(rownames(abTable_bio), 3)) # Repeating sample identifiers (good number of rows)
  # Creation of the final table, containing all the information
  newTable <- data.table(samples, types, matches)
  setnames(newTable, colnames(newTable), c("sample", "GOtype", "value")) # Better column names
  return(newTable)
}


GenerateOverview <- function(abTable) {
  # Creates the Overview chart and returns needed HTML code to display it.
  #
  # Args:
  #   abTable: An abundance table for selected samples. Rows: Samples, Columns: observations. 
  #            The function has to be used with the GENERAL abundance table (all categories)  
  #
  # Returns:
  #   Needed HTML code to insert the overview chart on the result page 
  #   (div and JS)
  overviewTable <- OverallSummary(abTable) # Gets the correct data
  
  ########################
  #### Chart creation ####
  ########################
  a <- hPlot(value ~ sample, 
             data = overviewTable, 
             type = "bar", 
             group = "GOtype", 
             title = "Overview of GO slim")

  ########################
  #### Chart settings ####
  ########################
  a$tooltip(shared = TRUE)
  a$colors(EMGcolors)
  a$chart(width = 900, 
          height = length(unique(overviewTable$sample)) * 120)
  a$exporting(enabled = TRUE)
  options(rcharts.cdn = TRUE)
  a$yAxis(title = list(text = "Match (absolute value)"))
  a$xAxis(title = list(text = "Sample"), categories = unique(overviewTable$sample))
  chartCode <- paste(capture.output(a$print("OverviewChart")), collapse = "\n")
  # This is the proper HTML code containing everything needed (except style/JS but we define this inside the Java web app)
  return(chartCode)
  
}

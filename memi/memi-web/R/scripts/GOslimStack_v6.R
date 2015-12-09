# This script is used to generate stacked columns visualizations. 
# Note: From this version now, a GO term will be considered as 'other' only if it is present below a certain percentage in ALL samples.

# Console log
message(paste(Sys.time(), "[R - Message] Launched R script GOslimStack_v6.R"))


FixRchartsStacked <- function(abundanceTable, threshold) {
  # Function to make the data compliant with the stacked columns visualization. 
  # - Handles the threshold by converting all terms that are below it into 'Other'
  # - Uses the RchartsFix() function from the launch script (creation of flat table)
  #
  # Args:
  #   abundanceTable: An abundance table for selected samples. Rows: samples, Columns: observations. 
  #                   In this script, used tables are divided with the DivideAbTable() function (only one category of GO terms).  
  #   threshold: terms having a proportion of matches lower than the threshold for all samples 
  #              will be shown as 'Other' (replacement of name + GO identifier). 
  #
  # Returns:
  #   A flat table (rCharts-edible table) with terms below the threshold grouped as 'Other' 
  #   containing percentage of matches for each sample / GO and GO name and identifier. 
  #   This table is ready to be used as dataset for stacked columns visualization.

  newTable <- AbTablePercent(abundanceTable) # Converting matches to percent
  # Handling threshold by testing the abundance of each term among all samples 
  # Identifiers and names of terms that are below the threshold are stored.
  otherTerms = c()
  otherNames= c()
  for(i in colnames(newTable)) {
  currentTermMatches <- newTable[, i]
  if(! (FALSE %in% (currentTermMatches < threshold))) {
    otherTerms = c(otherTerms, strsplit(i,split='\\|')[[1]][1])
    otherNames = c(otherNames, strsplit(i,split='\\|')[[1]][2])
    # It could worth it to try to use regular expression instead of two vectors (if too slow)
    # It could also be interesting to try replacing by 'Other' directly in the ab. table.  
    }
  } 

  newTable <- RchartsFix(newTable) # Create a flat table edible by rCharts
  newTable$GO[newTable$GO %in% otherTerms] <- "Other" # Replace ID of 'Other' terms
  newTable$GOname[newTable$GOname %in% otherNames] <- "Other" # Replace name of 'Other' terms 
  # Create a new table from 'newTable' 
  #  - All duplicated 'sample+GO+GOname' are melted and the associated values are summed
  newTable <- aggregate(value ~ sample + GO + GOname, data = newTable, FUN = sum)
  newTable <- as.data.table(newTable) # Conversion to data.table object
  return(newTable)
}


CreateStackColForCategory <- function(abundanceTable, threshold, category) {
  # Creates a chart for a category of GO terms given a general abundance table and a threshold
  #
  # Args:
  #   abundanceTable: An abundance table for selected samples. Rows: Samples, Columns: observations. 
  #                   The function has to be used with the GENERAL abundance table (all categories)  
  #   threshold: A percentage for which all terms below it among all samples will be converted 
  #              to 'Other'
  #   category: The GO category for which we want to generate a stacked columns visualization.
  #
  # Returns:
  #   Needed HTML code to insert a stacked columns visualization on the result page 
  #   (h3 tags + div and JS)

  # Correct names, h3 names and h3 id according to category of GO Slim
  # 'categoryIndice' gives an 'indice' depending on the current GO category.
  # Used to know which title / h3 id / h3 text has to be used for the current chart
  categoryIndice <- 0
  if (category == "biological_process") 
    categoryIndice <- 1
  if (category == "cellular_component") 
    categoryIndice <- 2
  if (category == "molecular_function") 
    categoryIndice <- 3

  # Components of name for the title
  correctNames <- c("biological process", "cellular component", "molecular function")
  # Text for the h3 HTML tags
  h3Names <- c("Biological process", "Cellular component", "Molecular function")
  # Identifier for the h3 HTML tags
  h3Id <- c("stack_bio_title", "stack_cell_title", "stack_mol_title")

  # Used data for the chart. See the 'FixRchartsStacked()' function for more details
  dataChart <- FixRchartsStacked(DivideAbTable(abundanceTable, category), threshold)
  
  ########################
  #### Chart creation ####
  ########################
  chart <- hPlot(value ~ sample, # Matches for each sample 
                 data = dataChart[dataChart$GOname != "Other"], # Take all data but 'Other' terms
                 group = "GOname", # Group them by GO name
                 type = "column") # chart type
                 
  # Adding 'Other' terms in last
  chart$series(data = dataChart$value[dataChart$GOname == "Other"], # 'Other' data
               name = paste("other (less than ", as.character(threshold),"%)", sep = ""), # Series name
               type = "column", # Type of the series
               color = "#B9B9B9") # Color for this series

  ########################
  #### Chart settings ####
  ########################
 # chart$chart(height = 484)  Height of charting area
  #   Replace by: chart$chart(height = 444) when no vertical label
  chart$title(text = paste0("Most frequent GO terms", " (", correctNames[categoryIndice], ")"), # Title text
              floating = FALSE, # Avoid overlapping of chart and title
              style = list(fontSize = 13, fontWeight = "bold")) # Title style
  chart$plotOptions(column = list(stacking = "percent")) # Stacking the columns. Valid values are 'normal' or 'percent'.
  chart$xAxis(categories = c(unique(dataChart$sample)), # Display sample identifiers on x axis 
              lineColor = "#595959", # Color of the axis
              tickColor = "", # Needed to delete ticks
              labels = list (rotation =-90)) # Display labels vertically
  chart$yAxis(gridLineColor = "#e0e0e0", # Color of the horizontal background lines
              title = list(text = "Relative abundance (%)"), # Y axis title
              min = 0, max = 100) # Minimum and maximum values for this axis
  chart$legend(# layout = "vertical",  verticalAlign = "top", align = "right", Legend display options
               #x = 0, y = 20,  Legend position options
               # width = 280,Fixed legend width so the 3 charts are aligned
               itemStyle = list(fontSize = "11px", fontWeight = "regular", color = "#606060"), # Style of legend items
               title = list(text = "GO terms list<br/><span style=\"font-size: 9px; color: #666; font-weight: normal; font-style: italic;\">Click to hide</span>", # Text of legend title
                            style = list(fontStyle = "regular"))) # Style of legend title
  chart$tooltip(backgroundColor = "white", # Tooltip backgound color
                headerFormat = "{series.name}<br/>", # Tooltip header
                pointFormat = "<span style=\"color:{series.color}\">&#9632;</span> <span style=\"font-size:88%;\">{point.category}: <strong>{point.y} %</strong></span>", # Tooltip content
                useHTML = TRUE)
  chart$colors(EMGcolors) # Using EMG website colors (defined in the launch script)
  # Setting width / height parameters to NULL disable the default size given to charts
  # Dimensions can therefore be controlled with css. 
  chart$addParams(width = NULL, height = NULL)

  #######################
  ###### HTML code ######
  #######################
  # Final HTML code generation 
  # - Capturing needed HTML code for the chart. Container div id is like : stack_biological_process (stack_CATEGORY)
  # - Adding h3 HTML tags
  chartCode <- paste(capture.output(chart$print(paste0("stack_",category))), collapse = "\n") # 
  chartCode <- paste(paste0("<h3 id=\"", h3Id[categoryIndice], "\">", h3Names[categoryIndice], "</h3>"), chartCode, sep = "\n") # Adding an h3 HTML tag to display a title
  return(chartCode)
}

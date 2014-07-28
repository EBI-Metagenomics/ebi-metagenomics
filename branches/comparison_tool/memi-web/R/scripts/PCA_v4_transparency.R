# Console log
message(paste(Sys.time(), "[R - Message] Launched R script PCA_v4_transparency.R"))


PerformPCA <- function(abTable, category) {
  # Performs PCA for one abuundance table / GO category and reutrns a list containing the following:
  #       [[1]] - Category of GO terms
  #       [[2]] - Percentage of variance explained by each principal component
  #       [[3]] - Principal component values (used coordinates to draw the points on the plot)
  #
  # Args:
  #   abTable: An abundance table for selected samples. Rows: samples, Columns: observations. 
  #   category: The GO category for which we want to perform a PCA
  # Returns:
  #   A list object containing category of GO terms, percentage of variance explained by each PC,
  #   PC values.

  # Performing PCA with the 'prcomp()' function (see help page R for more details about parameters)
  PCAresult <- prcomp(DivideAbTable(abTable, category), rtx = TRUE, ncomp = 3, 
    scale = FALSE, center = FALSE)
  
  # Extracting percentage of variance explained by each PC + formatting obtained values
  varPercent <- ((PCAresult$sdev)^2) * 100/sum((PCAresult$sdev)^2)
  varPercent <- format(varPercent, scientific = FALSE)
  varPercent <- round(as.numeric(varPercent), 2)
  # Returns a list containing needed information to generate the charts
  return(list(catGO = category, vars = varPercent, PCs = as.data.frame(PCAresult$x)))
  
}


DrawPCAPlot <- function(outputPCA, comp1, comp2) {
  # Draws a PCA plot for the chosen principal components from the result of 'PerformPCA()' 
  # and returns HTML code to display it (output is a highcharts scatterplot)
  #
  # Args:
  #   outputPCA: results of PCA obtained from the 'PerformPCA()' function
  #   comp1, comp2: The two principal components we want to work with
  #
  # Returns:
  #   HTML code needed to display the PCA plot
 
  # Correct names according to category of GO
  categoryIndice <- 0
  if (outputPCA$catGO == "biological_process") 
    categoryIndice <- 1
  if (outputPCA$catGO == "cellular_component") 
    categoryIndice <- 2
  if (outputPCA$catGO == "molecular_function") 
    categoryIndice <- 3
  correctNames <- c("Biological process", "Cellular component", "Molecular function")
  # Minimum and maximum values for each axis, so the chart should not resize. 
  # This sometimes bug, a possible fix would be to over-estimate minimum and maximum.
  axisX <- list(minimum = min(outputPCA$PCs[,comp1]), maximum = max(outputPCA$PCs[,comp1]))
  axisY <- list(minimum = min(outputPCA$PCs[,comp2]), maximum = max(outputPCA$PCs[,comp2]))
  # Custom colors (WITHOUT YELLOW HUBERT)
  # These colors are not used anymore since we prefer to work with transparency.
  PCAcolors <- c("#058DC7", "#50B432", "#ED561B", "#e725a7", "#24CBE5", "#64E572", "#FF9655", 
                 "#708D91", "#6AF9C4", "#DABE88", "#ff4949")

  ########################
  #### Chart creation ####
  ########################  
  PCAplot <- rCharts::Highcharts$new() # New rCharts object
  PCAplot$chart(type = "scatter", # Type of chart
                zoomType = "xy")  # Enable zoom for both x and y coordinates
  # Time to use the data and draw the points
  dataList <- list()
  samples <- rownames(outputPCA$PCs) # Sample identifiers
  symbols <- c("circle", "square", "triangle", "triangle-down", "diamond") # List of default symbols
  # colorsAndShapes <- expand.grid(symbols, PCAcolors, stringsAsFactors = FALSE)  # data.frame containing all combinations of symbols and colors. Not used anymore as we prefer to work with transparency. 
  for (i in 1:nrow(outputPCA$PCs)) {
    # Draw a point for each sample
    PCAplot$series(list(list(name = rownames(outputPCA$PCs)[i], 
                             data = list(c(outputPCA$PCs[i, comp1], outputPCA$PCs[i, comp2])), 
                             color = "rgba(5, 141, 199, 0.70)", 
                             marker = list(symbol = "circle"))))
  }

  ########################
  #### Chart settings ####
  ########################
  PCAplot$xAxis(title = list(text = paste("Principal component", comp1, 
                                          paste0("(", outputPCA$vars[comp1], "% of variance)"))),
                gridLineWidth = 1, 
                min = axisX$minimum, max = axisX$maximum)
  PCAplot$yAxis(title = list(text = paste("Principal component", comp2, 
                                          paste0("(", outputPCA$vars[comp2], "% of variance)"))),
                gridLineWidth = 1, 
                min = axisY$minimum, max = axisY$maximum)
  PCAplot$title(text = "Principal Component Analysis")
  PCAplot$subtitle(text = correctNames[categoryIndice])
  PCAplot$tooltip(backgroundColor = "white", # Tooltip backgound color
                headerFormat = "<span style=\"color:{series.color}\">\u25CF</span> {series.name}", # Tooltip header
                pointFormat = "", # Tooltip content (contains nothing)
                useHTML = TRUE)

  PCAplot$legend(enabled = FALSE) # itemStyle = list(fontSize = "11px", 
    # fontWeight = "regular", color = "#606060"), title = list(text = "Sample list<br/><span style=\"font-size: 9px; color: #666; font-weight: normal; font-style: italic;\">Click to hide</span>",
    # style = list(fontStyle = "regular"))) # Uncomment if you want one legend for each chart
  PCAplot$colors(PCAcolors)
  # Setting width / height parameters to NULL disable the default size given to charts
  # Dimensions can therefore be controlled with css. 
  PCAplot$addParams(width = NULL, height = NULL)

  #######################
  ###### HTML code ######
  #######################
  # - Capturing needed HTML code for the chart. Container div id is like : CATEGORY_pca_PC1PC2
  chartCode <- paste(capture.output(PCAplot$print(paste0(outputPCA$catGO, "_pca", "_", comp1, comp2))), 
    collapse = "\n")
  return(chartCode)
}


PCApage <- function(abTable) {
  # Uses the 'DrawPCAPlot()' function to generate PCA plots for each GO category 
  # and returns needed HTML code to display all the plots.  
  #
  # Args:
  #   abTable: a general abundance table (mixed GO categories)
  #
  # Returns:
  #   HTML/JS code needed to display the three PCA plots

  plots <- c(DrawPCAPlot(PerformPCA(abTable, "biological_process"), 1, 2), 
             DrawPCAPlot(PerformPCA(abTable, "molecular_function"), 1, 2), 
             DrawPCAPlot(PerformPCA(abTable, "cellular_component"), 1, 2))
  return(plots)
}

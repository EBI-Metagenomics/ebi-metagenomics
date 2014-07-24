# Console log
message(paste(Sys.time(), "[R - Message] Launched R script PCA_v4.R"))

# roundUp <- function(x,to=10) {
#   if(x<0) {
#     return(-(to*(-x%/%to + as.logical(-x%%to))))
#   }
#   else {
#     return(to*(x%/%to + as.logical(x%%to)))
# }
# Returns a list containing the category of GO, the percentage of variance explained by each
# principal component and the values of PCs for each sample as a data.frame (everything needed to
# draw a pretty scatter plot)
PerformPCA <- function(abTable, category) {
  
  # Performing PCA
  PCAresult <- prcomp(DivideAbTable(abTable, category), rtx = TRUE, ncomp = 3, scale = FALSE, center = FALSE)
  
  # Extracting percentage of variance explained by each PC
  varPercent <- ((PCAresult$sdev)^2) * 100/sum((PCAresult$sdev)^2)
  varPercent <- format(varPercent, scientific = FALSE)
  varPercent <- round(as.numeric(varPercent), 2)
  return(list(catGO = category, vars = varPercent, PCs = as.data.frame(PCAresult$x)))
  
}

# Drawing a PCA plot for the chosen PCs
DrawPCAPlot <- function(outputPCA, comp1, comp2) {
  # Correct names according to category of GO
  categoryIndice <- 0
  if (outputPCA$catGO == "biological_process") 
    categoryIndice <- 1
  if (outputPCA$catGO == "cellular_component") 
    categoryIndice <- 2
  if (outputPCA$catGO == "molecular_function") 
    categoryIndice <- 3
  correctNames <- c("Biological process", "Cellular component", "Molecular function")
  
  # Custom colors (WITHOUT YELLOW HUBERT)
  PCAcolors <- c("#058DC7", "#50B432", "#ED561B", "#e725a7", "#24CBE5", "#64E572", "#FF9655", "#708D91", 
    "#6AF9C4", "#DABE88", "#ff4949")
  
  PCAplot <- rCharts::Highcharts$new()
  PCAplot$chart(type = "scatter", zoomType = "xy")  #,width=600,height=500)
  
  # Time to mess with the data
  dataList <- list()
  samples <- rownames(outputPCA$PCs)
  symbols <- c("circle", "square", "triangle", "triangle-down", "diamond")
  colorsAndShapes <- expand.grid(symbols, PCAcolors, stringsAsFactors = FALSE)  # data.frame containing all combinations of symbols and colors. 
  axisX <- list(minimum = min(outputPCA$PCs[,comp1]), maximum = max(outputPCA$PCs[,comp1]))
  axisY <- list(minimum = min(outputPCA$PCs[,comp2]), maximum = max(outputPCA$PCs[,comp2]))
  for (i in 1:nrow(outputPCA$PCs)) {
    PCAplot$series(list(list(name = rownames(outputPCA$PCs)[i], data = list(c(outputPCA$PCs[i, comp1], 
      outputPCA$PCs[i, comp2])), color = "rgba(5, 141, 199, 0.70)", marker = list(symbol = "circle"))))
  }
  
  # Good graphics are good. Good good good. Good.
  PCAplot$xAxis(title = list(text = paste("Principal component", comp1, paste0("(", outputPCA$vars[comp1], 
    "% of variance)"))), gridLineWidth = 1, min = axisX$minimum, max = axisX$maximum)
  PCAplot$yAxis(title = list(text = paste("Principal component", comp2, paste0("(", outputPCA$vars[comp2], 
    "% of variance)"))), gridLineWidth = 1, min = axisY$minimum, max = axisY$maximum)
  PCAplot$title(text = "Principal Component Analysis")
  PCAplot$subtitle(text = correctNames[categoryIndice])
  PCAplot$tooltip(backgroundColor = "white", # Tooltip backgound color
                headerFormat = "<span style=\"color:{series.color}\">\u25CF</span> {series.name}", # Tooltip header
                pointFormat = "", # Tooltip content
                useHTML = TRUE)

  PCAplot$legend(enabled = FALSE) # itemStyle = list(fontSize = "11px", 
    # fontWeight = "regular", color = "#606060"), title = list(text = "Sample list<br/><span style=\"font-size: 9px; color: #666; font-weight: normal; font-style: italic;\">Click to hide</span>",
    # style = list(fontStyle = "regular")))
  PCAplot$colors(PCAcolors)
  PCAplot$addParams(width = NULL, height = NULL)
  chartCode <- paste(capture.output(PCAplot$print(paste0(outputPCA$catGO, "_pca", "_", comp1, comp2))), 
    collapse = "\n")
  return(chartCode)
}



PCApage <- function(abTable) {
  plots <- c(DrawPCAPlot(PerformPCA(abTable, "biological_process"), 1, 2), DrawPCAPlot(PerformPCA(abTable, 
    "molecular_function"), 1, 2), DrawPCAPlot(PerformPCA(abTable, "cellular_component"), 1, 2))
  return(plots)
}

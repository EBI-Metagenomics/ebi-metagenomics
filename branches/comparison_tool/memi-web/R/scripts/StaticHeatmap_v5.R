# Console log Nb : This version contains a workaround that should enable to display correct png
# graphical output by generating a pdf and then convert it with ghostscript.  It is ugly-ish but
# allows to have at least something to display
message(paste(Sys.time(), "[R - Message] Launched R script StaticHeatmap_v5.R"))

abTableToFraction <- function(abundanceTable) {
  percentAbTable <- abundanceTable
  maxOfAll <- rowSums(percentAbTable)
  for (i in 1:length(maxOfAll)) {
    percentAbTable[i, ] <- round(((percentAbTable[i, ]/maxOfAll[i])), 5)
  }
  percentAbTable[is.nan(percentAbTable)] <- 0
  return(percentAbTable)
}

# Generate a heatmap from an abundance table
GenerateHeatmap <- function(generalAbTable, category, filePathAndName, hmParametersVector) {
  
  # Correct names according to category of GO Slim
  categoryIndice <- 0
  if (category == "biological_process") 
    categoryIndice <- 1
  if (category == "cellular_component") 
    categoryIndice <- 2
  if (category == "molecular_function") 
    categoryIndice <- 3
  correctNames <- c("Biological process", "Cellular component", "Molecular function")
  
  tmpPathAndName <- paste("src/main/webapp/img/comparison/", name, "_tmp.pdf", sep = "")
  abTable <- abTableToFraction(DivideAbTable(generalAbTable, category))
  # print(abTable) Changing colnames to have GO names only (instead of all) and cut them if they are
  # too long
  colnames(abTable) <- sapply(sapply(sapply(colnames(abTable), strsplit, split = "\\|", USE.NAMES = FALSE), 
    "[[", 2), substr, start = 0, stop = 34)
  maxMatches <- max(abTable)
  # print(maxMatches) Colors
  greenToRed <- colorRampPalette(c("green", "yellow", "red"))(n = 299)
  # if(is.nan(maxMatches)) maxMatches=1
  col_breaks <- c(seq(0, (0.3 * maxMatches), length = 100), seq((0.3 * maxMatches), (0.7 * maxMatches), 
    length = 100), seq((0.7 * maxMatches), maxMatches, length = 100))
  # Png device opening. Won't work on the cluster...
  pdf(tmpPathAndName, pointsize = 7)
  boolClust <- c(TRUE, TRUE)
  if (hmParametersVector[2] == "column") 
    boolClust <- c(FALSE, TRUE)
  if (hmParametersVector[2] == "row") 
    boolClust <- c(TRUE, FALSE)
  if (hmParametersVector[2] == "none") 
    boolClust <- c(FALSE, FALSE)
  heatmap.2(data.matrix(abTable), main = correctNames[categoryIndice], col = greenToRed, trace = "none", 
    density.info = "none", breaks = col_breaks, Rowv = boolClust[1], Colv = boolClust[2], key = as.logical(hmParametersVector[1]), 
    dendrogram = hmParametersVector[3], lwid = c(2, 8), lhei = c(2, 8), margins = c(12, 12))
  dev.off()
  
  # Convert to png and reduce it to desired size 
  # TODO: Don't have that blurry output ... Maybe worth it to try svg !
  conversionCommand <- paste("convert -density 300 -resize 800x800", tmpPathAndName, "-sharpen 0x1", 
    filePathAndName)
  system(conversionCommand)
  # Finally, delete the temporary pdf file
  file.remove(tmpPathAndName)
} 


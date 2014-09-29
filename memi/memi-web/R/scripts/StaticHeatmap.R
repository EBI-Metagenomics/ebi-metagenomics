# Console log
message(paste(Sys.time(),'[R - Message] Launched R script StaticHeatmap.R'))

# Loading needed library
library(gplots)
library(RColorBrewer)

abTableToFraction = function(abundanceTable){
  percentAbTable = abundanceTable
  maxOfAll = rowSums(percentAbTable)
  for(i in 1:length(maxOfAll)) {
    percentAbTable[i,] = round(((percentAbTable[i,]/maxOfAll[i])),5)
  }
  
  return(percentAbTable)
}

# Generate a heatmap from an abundance table
GenerateHeatmap = function(abTable,filePathAndName,hmParametersVector){
abTable = abTableToFraction(abTable)
maxMatches = max(abTable)
print(maxMatches)
# Colors
greenToRed <- colorRampPalette(c('#058DC7', "white", '#ED561B'))(n = 299)
col_breaks = c(seq(0,(0.3*maxMatches),length=100), seq((0.3*maxMatches),(0.7*maxMatches),length=100), seq((0.7*maxMatches),maxMatches,length=100))
# Png device opening
png(filePathAndName, width = 1000, height = 1000)
boolClust = c(TRUE,TRUE)
if(hmParametersVector[2]=='column') boolClust = c(FALSE,TRUE)
if(hmParametersVector[2]=='row') boolClust = c(TRUE,FALSE)
if(hmParametersVector[2]=='none') boolClust = c(FALSE,FALSE)
heatmap.2(data.matrix(abTable),main="Heatmap",col = greenToRed, trace = "none", density.info = "none", margins = c(10,15), breaks = col_breaks, Rowv = boolClust[1], Colv = boolClust[2], lwid = c(1,6), lhei = c(1,6), key = as.logical(hmParametersVector[1]), dendrogram = hmParametersVector[3])
dev.off()
}

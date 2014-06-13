# Console log
# Nb : This 2nd version contains a workaround that should enable to display correct png graphical output by generating a pdf and then convert it with ghostscript.
message(paste(Sys.time(),'[R - Message] Launched R script StaticHeatmap_v2.R'))

# Loading needed library
library(gplots)
library(RColorBrewer)

divideAbTable = function(abTable,category) {
  abTableCategory <- abTable[,grepl(category,colnames(abTable))]
  return(abTableCategory)
}

abTableToFraction = function(abundanceTable){
  percentAbTable = abundanceTable
  maxOfAll = rowSums(percentAbTable)
  for(i in 1:length(maxOfAll)) {
    percentAbTable[i,] = round(((percentAbTable[i,]/maxOfAll[i])),5)
  }
  percentAbTable[is.nan(percentAbTable)] <- 0
  return(percentAbTable)
}

# Generate a heatmap from an abundance table
GenerateHeatmap = function(generalAbTable,category,filePathAndName,hmParametersVector){
# tmpPathAndName = paste('src/main/webapp/img/comparison/',name,'_tmp.pdf',sep='')
abTable = abTableToFraction(divideAbTable(generalAbTable,category))
print(abTable)
# Changing colnames to have GO names only (instead of all)
colnames(abTable) = sapply(sapply(sapply(colnames(abTable),strsplit,split='\\|',USE.NAMES=FALSE),"[[", 2),substr,start=0,stop=35)
maxMatches = max(abTable)
# print(maxMatches)
# Colors
greenToRed <- colorRampPalette(c('#058DC7', "white", '#ED561B'))(n = 299)
if(is.nan(maxMatches)) maxMatches=1
col_breaks = c(seq(0,(0.3*maxMatches),length=100), seq((0.3*maxMatches),(0.7*maxMatches),length=100), seq((0.7*maxMatches),maxMatches,length=100))
# Png device opening. Won't work on the cluster...
png(filePathAndName, width = 1000, height = 1000)
boolClust = c(TRUE,TRUE)
if(hmParametersVector[2]=='column') boolClust = c(FALSE,TRUE)
if(hmParametersVector[2]=='row') boolClust = c(TRUE,FALSE)
if(hmParametersVector[2]=='none') boolClust = c(FALSE,FALSE)
heatmap.2(data.matrix(abTable),main=category,col = greenToRed, trace = "none", density.info = "none", margins = c(13,14), breaks = col_breaks, Rowv = boolClust[1], Colv = boolClust[2], lwid = c(1,6), lhei = c(1,6), key = as.logical(hmParametersVector[1]), dendrogram = hmParametersVector[3])
dev.off()

# Convert to png and reduce it to desired size
# conversionCommand = paste0('gs -dSAFTER -dNOPAUSE -dBATCH -sDEVICE=png16m -sOutputFile=',filePathAndName,' -r300 ',tmpPathAndName)
# reductionCommand = paste0('mogrify -resize 500 ',filePathAndName)
# system(conversionCommand)
# system(reductionCommand)
# Finally, delete the temporary pdf file
# file.remove(tmpPathAndName)
}

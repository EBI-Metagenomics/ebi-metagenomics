# Console log
message(paste(Sys.time(),'[R - Message] Launched R script PCA_v3.R'))

divideAbTable = function(abTable,category) {
  abTableCategory <- abTable[,grepl(category,colnames(abTable))]
  return(abTableCategory)
}


# Returns a list containing the category of GO, the percentage of variance explained by each principal component and the values of PCs for each sample as a data.frame (everything needed to draw a pretty scatter plot)
PerformPCA = function(abTable,category) {
  
  # Performing PCA
  PCAresult = prcomp(divideAbTable(abTable,category),rtx=TRUE,ncomp=3,scale=FALSE,center=FALSE)

  # Extracting percentage of variance explained by each PC
  varPercent<-((PCAresult$sdev)^2)*100/sum((PCAresult$sdev)^2)
  varPercent = format(varPercent, scientific = FALSE)
  varPercent = round(as.numeric(varPercent),2)
  return(list(catGO = category, vars=varPercent, PCs = as.data.frame(PCAresult$x)))

}

# Drawing a PCA plot for the chosen PCs
DrawPCAPlot = function (outputPCA,comp1,comp2){
  # Correct names according to category of GO
  categoryIndice = 0
  if(outputPCA$catGO == 'biological_process') categoryIndice = 1
  if(outputPCA$catGO == 'cellular_component') categoryIndice = 2
  if(outputPCA$catGO == 'molecular_function') categoryIndice = 3
  correctNames = c('Biological process','Cellular component','Molecular function')

  # Custom colors (WITHOUT YELLOW)
  PCAcolors <- c('#058DC7','#50B432','#ED561B','#e725a7','#24CBE5','#64E572','#FF9655','#708D91','#6AF9C4','#DABE88','#ff4949')

PCAplot <- rCharts::Highcharts$new()
PCAplot$chart(type='scatter')#,width=600,height=500)

# Time to mess with the data
dataList = list()
samples = rownames(outputPCA$PCs)
symbols = c("circle", "square", "triangle", "triangle-down", "diamond")
colorsAndShapes = expand.grid(symbols,PCAcolors,stringsAsFactors=FALSE) # data.frame containing all combinations of symbols and colors. 

for(i in 1:nrow(outputPCA$PCs)) {
PCAplot$series(list(list(name=rownames(outputPCA$PCs)[i],data = list(c(outputPCA$PCs[i,comp1],outputPCA$PCs[i,comp2])), color = colorsAndShapes[i,2], marker = list(symbol = colorsAndShapes[i,1]))))
}

# Good graphics are good. Good good good. Good.
PCAplot$xAxis(title=list(text=paste('Principal component',comp1, paste0('(', outputPCA$vars[comp1], '% of variance)'))),gridLineWidth=1)
PCAplot$yAxis(title=list(text=paste('Principal component',comp2, paste0('(', outputPCA$vars[comp2], '% of variance)'))),gridLineWidth=1)
PCAplot$title(text = 'Principal Component Analysis')
PCAplot$subtitle(text = correctNames[categoryIndice])
PCAplot$colors(PCAcolors)
PCAplot$addParams(width=NULL, height = NULL)
chartCode <- paste(capture.output(PCAplot$print(paste0(outputPCA$catGO,'_pca','_',comp1,comp2))),collapse = '\n')
  return(chartCode)
}



PCApage = function(abTable) {
plots = c(
DrawPCAPlot(PerformPCA(abTable,'biological_process'),1,2),
DrawPCAPlot(PerformPCA(abTable,'molecular_function'),1,2)
)
return(plots)
}

# o          +
#     o  +           +        +
# +        o     o       +        o
# -_-_-_-_-_-_-_,------,      o 
# _-_-_-_-_-_-_-|   /\_/\  
# -_-_-_-_-_-_-~|__( ^ .^)  +     +  
# _-_-_-_-_-_-_-""  ""      
# +      o         o   +       o
#     +         +
# o        o         o      o     +

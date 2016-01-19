# Console log
message(paste(Sys.time(),'[R - Message] Launched R script GOslimStack_v3.R'))

# A small operator that is quite useful
"%w/o%" <- function(x, y) x[!x %in% y] # ->  x without y 

# Load libraries
library(data.table)

divideAbTable = function(abTable,category) {
  abTableCategory <- abTable[,grepl(category,colnames(abTable))]
  return(abTableCategory)
}

# Go from divided abundance table to edible data for rChart  
rChartsFixing = function(abundanceTable,percent=FALSE){
  nbSamples = nrow(abundanceTable)
  nbObs = ncol(abundanceTable)
  maxOfAll = rowSums(abundanceTable)
  recordGO=c()
  recordName= c()
  recordSample=c()
  recordValue=c()
  for(i in 1:nbObs){
    for(j in 1:nbSamples) {
      recordGO = c(recordGO,strsplit(colnames(abundanceTable)[i],split='\\|')[[1]][1])
      # GO names in a separate place ? Could be changed in the future
      recordName <- c(recordName,strsplit(colnames(abundanceTable)[i],split='\\|')[[1]][2])
      recordSample = c(recordSample,rownames(abundanceTable)[j])
      if(percent) recordValue=c(recordValue,round(((abundanceTable[j,i]/maxOfAll[i])*100),3))
      else recordValue=c(recordValue,abundanceTable[j,i])
    }
  }
  
  newTable=data.table(recordSample,recordGO,recordName,recordValue)
  setnames(newTable,colnames(newTable),c('sample','GO','GOname','value'))
  return(newTable)
}

rChartsStacked = function(abundanceTable,threshold){
  newTable = abundanceTable
  maxOfAll = rowSums(abundanceTable)
  for(i in 1:length(maxOfAll)) {
    newTable[i,] = round(((newTable[i,]/maxOfAll[i])*100),3)
  }
  
  newTable = rChartsFixing(newTable)
  # Threshold handling
  newTable$GO[newTable$value < threshold] = 'Other'
  newTable$GOname[newTable$value < threshold] = 'Other'
  newTable = aggregate(value~sample+GO+GOname,data=newTable,FUN=sum)
  
  # Bug with rCharts : Need to recreate the missing values (values that were under threshold in some sample(s) and not in others)
  GO = unique(newTable$GO)
  samples = unique(newTable$sample)
  for(j in GO){
    subTable = newTable[newTable$GO==j,]
    if((dim(subTable)[1])<length(samples)){
      addData = data.frame(samples%w/o%subTable$sample,j,subTable$GOname[1],0)
      colnames(addData)=c('sample','GO','GOname','value')
      newTable = rbind(newTable,addData)
    }
  }
  newTable = as.data.table(newTable)
  return(newTable)
}

# Final function. Yay. For one category, from general table. Cool.
CreateStackColForCategory = function(abundanceTable,threshold,category) {

  # Correct names and h3 id according to category of GO Slim
  categoryIndice = 0
  if(category=='biological_process') categoryIndice = 1
  if(category=='cellular_component') categoryIndice = 2
  if(category=='molecular_function') categoryIndice = 3
  correctNames = c('biological process','cellular component','molecular function')
  h3Id = c('stack_bio_title','stack_cell_title','stack_mol_title')

  dataChart = rChartsStacked(divideAbTable(abundanceTable,category),threshold)
  chartWidth = 900+length(unique(dataChart$sample))*50
  chart <- hPlot(value~sample,data = dataChart[dataChart$GOname!='Other'], group='GOname',type = 'column',group.na = 'NA\'s',title = paste0('Most frequent GO terms',' (',correctNames[categoryIndice],')')) # , subtitle = correctNames[categoryIndice])
  chart$chart(height = 500)
  chart$series(data = dataChart$value[dataChart$GOname=='Other'], name = paste('other (less than ',as.character(threshold),'%)',sep=''),type='column', color = '#B9B9B9')
  chart$plotOptions(column = list(stacking = "percent")) 
  chart$xAxis(categories=c(unique(dataChart$sample)))
  chart$yAxis(title=list(text='Relative abundance (%)'))
  chart$legend(layout='vertical',align='right',verticalAlign='top',x=-10,y=100)
  chart$colors(EMGcolors)
  chart$exporting(enabled = T)
  chartCode <- paste(capture.output(chart$print(paste0('stack_',category))),collapse = '\n')
  chartCode <- paste(paste0('<h3 id=\"',h3Id[categoryIndice],'\">',correctNames[categoryIndice],'</h3>'),chartCode,sep='\n')
  return(chartCode)
}

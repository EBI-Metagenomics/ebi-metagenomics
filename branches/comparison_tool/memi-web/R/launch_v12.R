# Launch script. This script is called by the EMG Java web application. 
# It reads command line parameters and launchs external scripts for pre-processing (abundance table) and each visualization
# Each visualization is saved as an HTML file which can be read and displayed by the Java web application.

##### Console log #####
message(paste(Sys.time(),'[R - Message] Launched general R script launch.R'))


##########################################
######## Parameters retrieving ###########
##########################################

# Allow R to read command arguments
parameters <- commandArgs(TRUE)

# Small check to see number of arguments. Uncomment to use.
# print(length(parameters))

tempDir <- parameters[1] # Temporary directory to store graphs. To be set without slash ('/') at the end.
workDir <- parameters[2] # Working directory
imgDir <- parameters[3] # Tmp image directory, used for heatmaps
name <- parameters[4] # output files identifier
files <- (strsplit(parameters[5],split=',')[[1]]) # Creation of a vector of file names from the list in parameter
data <- parameters[6] # Used data
sampleNames <- (strsplit(parameters[7],split=',')[[1]]) # Sample names

# Advanced settings
stack <- as.numeric(parameters[8]) # Stacking threshold for stacked bars
hmParameters <- (strsplit(parameters[9],split=',')[[1]]) # Heatmap parameters

# Print arguments to see if everything is okay. Uncomment to use.
# print(parameters)


##########################################
##### Libraries/packages and sources #####
##########################################

# Define all names of used packages
usedPackages = c('methods', # methods: Slows up loading time but needed to use rCharts methods.
  'rCharts', # rCharts: JS visualizations using highcharts JS library
  'RJSONIO', # RJSONIO: Enable conversion of R data into JSON data
  'gplots', # gplots: Used for heatmaps (heatmap.2 function)
  'RColorBrewer', # RColorBrewer: Heatmap colors
  'data.table', # data.table: data.table objects
  'Cairo') # Cairo: enable creation of SVG graphical output on server

# Define all the file names of used R scripts
scriptList <- list(
  preProcess = 'PreProcess_v12.R',
  overview = 'Overview_v6.R',
  bars = 'GOslimBar_v6.R',
  stacked = 'GOslimStack_v6.R',
  heatmap = 'StaticHeatmap_v6.R',
  pca = 'PCA_v4_transparency.R',
  jsTable = 'Table_v4.R')


##########################################
############ Initial checks ##############
##########################################
# Perform checks to see if everything needed is present

# Packages check
message(paste(Sys.time(), "[R - Initial checks] Check if needed R packages are installed."))
availablePackages = rownames(installed.packages())
if(FALSE %in% (usedPackages %in% availablePackages)) {
  missingPackages <- usedPackages[! usedPackages %in% availablePackages]
  message(paste(Sys.time(), "[R - Error] Check over. Missing package(s):"), paste(missingPackages))
  stop()
  }
  message(paste(Sys.time(), "[R - Initial checks] Check over. All packages are installed."))

# Directories check
message(paste(Sys.time(), "[R - Initial checks] Check if needed directories exist."))
dirList = c(tempDir, imgDir, workDir)
if(FALSE %in% (file.exists(dirList))) {
  missingDir <- dirList[! file.exists(dirList)]
  message(paste(Sys.time(), "[R - Error] Check over.", paste(missingDir,sep=', '),'not found.'))
  stop()
  }
  message(paste(Sys.time(), "[R - Initial checks] Check over. All needed directories exist."))

# Loading all packages at once. The invisible() function remove the messages.
invisible(sapply(usedPackages, library, character.only=TRUE, USE.NAMES=FALSE, quietly=TRUE)) 
message(paste(Sys.time(),'[R - Message] R packages loaded successfully'))


##########################################
################ Variables ###############
##########################################

# Path separator, not the same depending on the OS running the script. Shouldn't be a problem since the scripts are running on a linux server.
pathSep = '/'
if(.Platform$OS.type != 'unix') pathSep = '\\'

scriptsDir = paste(workDir,'scripts',sep = pathSep) # Name of the directory containing the used R scripts
resourcesDir = paste(workDir,'resources', sep = pathSep) # Name of the directory containing various ressources (Hierarchy file, etc.)

# EMG website colors
EMGcolors <- c('#058DC7','#50B432','#ED561B','#EDEF00','#24CBE5','#64E572','#FF9655','#e396ff','#6AF9C4','#DABE88')

# A small operator that is quite useful
"%w/o%" <- function(x, y) x[!x %in% y] # ->  x without y

# A boolean to disable heatmap generation as it doesn't work on the server
commit = FALSE


##########################################
############# Shared methods #############
##########################################

# These methods are used in various visualization scripts. They are defined here. 
# - Divide abundance table into subtable for given GO category
# - Go from abundance table to flat table edible by rCharts
# - Conversion of abundance table values to percentages

# Divide abundance table for each category
DivideAbTable = function(abTable,category) {
  abTableCategory <- abTable[,grepl(category,colnames(abTable))]
  return(abTableCategory)
}

# Go from divided abundance table to edible data for rChart (flat table)
RchartsFix = function(abundanceTable){
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
      recordValue=c(recordValue,abundanceTable[j,i])
    }
  }
  newTable=data.table(recordSample,recordGO,recordName,recordValue)
  setnames(newTable,colnames(newTable),c('sample','GO','GOname','value'))
  return(newTable)
}

# Convert abundance table values into percentages. Use it on divided abundance tables.
AbTablePercent = function(abundanceTable){
  pcAbTable = abundanceTable
  maxOfAll = rowSums(pcAbTable)
  for(i in 1:length(maxOfAll)) {
    pcAbTable[i,] = round(((pcAbTable[i,]/maxOfAll[i])*100),2)
  }
  return(pcAbTable)
}


#########################################
############ Abundance table ############
#########################################

# Load pre-processing script
source(paste(scriptsDir,scriptList$preProcess,sep = pathSep))

# Import of required functions from 'IPRHierarchy.R' if handling collapsing of InterPro terms is needed.
# source(paste(scriptsDir,'IPRHierarchy.R',sep = pathSep))

# Launch abundance table creation script and print it for debugging.
abTable = CreateAbTable(name, files, data, sampleNames)

# print(abTable) # Uncomment line to print


if(data=='GO') {
  mostVarNames = names(sort(apply(abTable, 2, var),decreasing=TRUE))
  abTable = abTable [,c(mostVarNames[1:as.integer(parameters[9])])]
}


# Dealing with all visualizations

#########################################
############### Overview ################
#########################################

# Call needed methods
source(paste(scriptsDir,scriptList$overview,sep = pathSep))
# Use methods (generate, grab useful data)
overviewCode = GenerateOverview(abTable)
# Write final result file
write(overviewCode,paste(tempDir,pathSep,name,'_overview','.htm',sep=''))


#########################################
############### Barcharts ###############
#########################################

maxSampleNumber = 25

# Display error message and don't generate any barchart if too many samples have been selected. 
fullBarsCode <- paste('You selected more than', maxSampleNumber, 'samples, cannot display bar chart visualization.')

if(length(files) <= maxSampleNumber) {
source(paste(scriptsDir,scriptList$bars,sep = pathSep))
bioBars = CreateBarsForCategory(abTable,'biological_process')
molBars = CreateBarsForCategory(abTable,'molecular_function')
cellBars = CreateBarsForCategory(abTable,'cellular_component')

fullBarsCode <- c(bioBars, molBars, cellBars)
}

write(fullBarsCode,paste(tempDir,pathSep,name,'_bar','.htm',sep=''))



#########################################
############# Stacked Bars ##############
#########################################

# Call needed methods
source(paste(scriptsDir,scriptList$stacked,sep = pathSep))
# Use methods, save, grab useful data
stackedCols = c(CreateStackColForCategory(abTable,stack,'biological_process'),
  CreateStackColForCategory(abTable,stack,'molecular_function'),
  CreateStackColForCategory(abTable,stack,'cellular_component'))
# Write final result file
write(stackedCols,paste(tempDir,pathSep,name,'_stack','.htm',sep=''))


#########################################
########### Static Heatmap ##############
#########################################
# The link between path and URL of images is a bit tricky. If no image is displayed, it could be worth it to check the path for heatmaps.
hmCode = '<p><i>Generation of PNG heatmaps is not working on the server ...</i></p>'
imgExt = '.svg' # Extension of images
if(!commit) {
source(paste(scriptsDir,scriptList$heatmap,sep = pathSep))
bioHM <- GenerateHeatmap(abTable,'biological_process',imgDir, paste0('bio_', name, imgExt), hmParameters)
molHM <- GenerateHeatmap(abTable,'molecular_function',imgDir, paste0('mol_', name, imgExt), hmParameters)
cellHM <- GenerateHeatmap(abTable,'cellular_component',imgDir, paste0('cell_', name, imgExt), hmParameters)
 
hmCode = c(bioHM, molHM, cellHM)
}

write(hmCode,paste(tempDir,pathSep,name,'_hm','.htm',sep=''))


#########################################
################# PCA ###################
#########################################
source(paste(scriptsDir,scriptList$pca,sep = pathSep))
PCAcode = PCApage(abTable)
write(PCAcode,paste(tempDir,pathSep,name,'_pca','.htm',sep=''))


#########################################
################ Table ##################
#########################################
source(paste(scriptsDir,scriptList$jsTable,sep = pathSep))
tableCode = DataTableGen(abTable)
write(tableCode, paste(tempDir,pathSep,name,'_table','.htm',sep=''))

# Console log
message(paste(Sys.time(),'[R - Message] R scripts over.'))

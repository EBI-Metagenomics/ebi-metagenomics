# Launch script. Will create the abundance table and then generate each visualization
# All is saved in HTML files which are parsed and then displayed.
# Nb: Logic changed for parameters. Older versions won't work anymore.

# Console log
message(paste(Sys.time(),'[R - Message] Launched general R script launch.R'))

# Loading of needed libraries and sources
library(methods)
library(rCharts)

# A boolean to disable heatmap generation as it doesn't work on the server ...
commit = TRUE

# Allow R to read command arguments
parameters <- commandArgs(TRUE)

##########################################
######## Parameters retrieving ###########
##########################################

# Small check to see number of arguments. Uncomment to use.
# print(length(parameters))

tempDir <- parameters[1] # Temporary directory to store graphs. To be set without slash ('/') at the end.
workDir <- parameters[2] # Working directory
name <- parameters[3] # output file unique name
files <- (strsplit(parameters[4],split=',')[[1]]) # Creation of a vector of file names from the list in parameter
data <- parameters[5] # Chosen data
sampleNames <- (strsplit(parameters[6],split=',')[[1]]) # Sample names

# Advanced settings
stack <- as.numeric(parameters[7]) # Stacking threshold for stacked bars
hmParameters <- (strsplit(parameters[8],split=',')[[1]]) #Heatmap parameters

# Print arguments to see if everything is okay. Uncomment to use.
# print(parameters)

#########################################

# Path separator, not the same depending on the OS running the script. Shouldn't be a problem since the scripts are running on a linux server.
pathSep = '/'
if(.Platform$OS.type != 'unix') pathSep = '\\'

setwd(workDir) # Set working directory. Maybe not need as paths are always given as absolute paths now ?
scriptsDir = paste(workDir,'scripts',sep = pathSep) # Name of the directory containing the used R scripts
resourcesDir = paste(workDir,'resources', sep = pathSep) # Name of the directory containing various ressources (Hierarchy file, etc.)

# Define all the file names of used R scripts
scriptList <- list(
  preProcess = 'PreProcess_v11.R',
  overview = 'Overview_v5.R',
  bars = 'GOslimBar_v4.R',
  stacked = 'GOslimStack_v3.R',
  heatmap = 'StaticHeatmap_v3.R',
  pca = 'PCA.R',
  jsTable = 'Table_v3.R')

# EMG website colors
EMGcolors <- c('#058DC7','#50B432','#ED561B','#EDEF00','#24CBE5','#64E572','#FF9655','#FFF263','#6AF9C4','#DABE88')

# A small operator that is quite useful
"%w/o%" <- function(x, y) x[!x %in% y] # ->  x without y


# Create table to retrieve names and type from a GO slim term
GOslimTable <- read.table(paste(resourcesDir,'GOslim_names.csv',sep = pathSep), sep = ",", quote = "\"",
                          col.names = c("GO", "function", "type"), colClasses = c("character", "character", "character"))


# Wrapper for source() and paste() function to load R scripts with all the set values for directories and file names more easily.


# Load pre-processing script
source(paste(scriptsDir,scriptList$preProcess,sep = pathSep))


#########################################
########### Abundance table #############
#########################################

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
overviewCode = generateOverview(abTable)
# Write final result file
write(overviewCode,paste(tempDir,pathSep,name,'_overview','.htm',sep=''))


#########################################
############### Barcharts ###############
#########################################

fullBarsCode <- 'You selected more than 10 samples, cannot display bar chart visualization.'

if(length(files) <= 10) {
source(paste(scriptsDir,scriptList$bars,sep = pathSep))
bioBars = CreateBarsForCategory(abTable,'biological_process')
molBars = CreateBarsForCategory(abTable,'molecular_function')
cellBars = CreateBarsForCategory(abTable,'cellular_component')

fullBarsCode <- c(bioBars, molBars, cellBars)
}

write(fullBarsCode,paste(tempDir,pathSep,name,'_bar','.htm',sep=''))



#########################################
####### GO slim | Stacked Bars ##########
#########################################

# Call needed methods
source(paste(scriptsDir,scriptList$stacked,sep = pathSep))
# Use methods, save, grab useful data
stackedBars = c(CreateStackColForCategory(abTable,stack,'biological_process'),CreateStackColForCategory(abTable,stack,'cellular_component'),CreateStackColForCategory(abTable,stack,'molecular_function'))
# Write final result file
write(stackedBars,paste(tempDir,pathSep,name,'_stack','.htm',sep=''))

#########################################
########### Static Heatmap ##############
#########################################
hmCode = '<p><i>Generation of PNG heatmaps is not working on the server ...</i></p>'
if(!commit) {
source(paste(scriptsDir,scriptList$heatmap,sep = pathSep))
pathAndName = paste('src/main/webapp/img/comparison/',c('bio_','cell_','mol_'),name,'.png',sep='')
GenerateHeatmap(abTable,'biological_process',pathAndName[1],hmParameters)
GenerateHeatmap(abTable,'cellular_component',pathAndName[2],hmParameters)
GenerateHeatmap(abTable,'molecular_function',pathAndName[3],hmParameters)

# A bit tricky with the JSTL paths ... But display this anyway
hmCode = paste('<div id="hm_bio"><img src=\"','/metagenomics/img/comparison/',paste0('bio_',name),'.png\"></div>','<div id="hm_cell"><img src=\"','/metagenomics/img/comparison/',paste0('cell_',name),'.png\"></div>','<div id="hm_mol"><img src=\"','/metagenomics/img/comparison/',paste0('mol_',name),'.png\"></div>',sep='')
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
coucou = DataTableGen(abTable)
write(coucou,paste(tempDir,pathSep,name,'_table','.htm',sep=''))


# Delete table 
# file.remove(paste('R/tables/',name,'.txt',sep=''))

# Console log
message(paste(Sys.time(),'[R - Message] R scripts over.'))

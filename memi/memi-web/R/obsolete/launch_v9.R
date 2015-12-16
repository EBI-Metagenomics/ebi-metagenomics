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

# Load pre-processing script
source("R/scripts/PreProcess_v11.R")

# EMG website colors
EMGcolors <- c('#058DC7','#50B432','#ED561B','#EDEF00','#24CBE5','#64E572','#FF9655','#FFF263','#6AF9C4','#DABE88')

# A small operator that is quite useful
"%w/o%" <- function(x, y) x[!x %in% y] # ->  x without y 

# Create table to retrieve names and type from a GO slim term
GOslimTable <- read.table("R/resources/GOslim_names.csv", sep = ",", quote = "\"",
                          col.names = c("GO", "function", "type"), colClasses = c("character", "character", "character"))

# Small check to see number of arguments. Uncomment to use.
# print(length(parameters))

name <- parameters[1] # output file name
files <- (strsplit(parameters[2],split=',')[[1]]) # Creation of a vector of file names from the list in parameter
data <- parameters[3] # Chosen data
sampleNames <- (strsplit(parameters[4],split=',')[[1]]) # Sample names

# Advanced settings
stack <- as.numeric(parameters[5]) # Stacking threshold for stacked bars
hmParameters <- (strsplit(parameters[6],split=',')[[1]]) #Heatmap parameters


# Print arguments to see if everything is okay. Uncomment to use.
print(parameters)

# Launch abundance table creation script and print it for debugging.
abTable = CreateAbTable(name, files, data, sampleNames)
# print(abTable) # Uncomment line to print

# First try to deal with the full GO stuff ...
if(data=='GO') {
  mostVarNames = names(sort(apply(abTable, 2, var),decreasing=TRUE))
  abTable = abTable [,c(mostVarNames[1:as.integer(parameters[7])])]
}

# Dealing with all visualizations

#########################################
############### Overview ################
#########################################

# Call needed methods
source('R/scripts/Overview_v5.R')
# Use methods (generate, grab useful data)
graphCode = generateOverview(abTable)
# Write final result file
write(graphCode,paste('R/tmpGraph/',name,'_overview','.htm',sep=''))


#########################################
############### Barcharts ###############
#########################################

fullBarsCode <- 'You have more than 10 samples, cannot display bar chart visualization.'

if(length(files) <= 10) {
source('R/scripts/GOslimBar_v4.R')
bioBars = CreateBarsForCategory(abTable,'biological_process')
molBars = CreateBarsForCategory(abTable,'molecular_function')
cellBars = CreateBarsForCategory(abTable,'cellular_component')

fullBarsCode <- c(bioBars, molBars, cellBars)
}

write(fullBarsCode,paste('R/tmpGraph/',name,'_bar','.htm',sep=''))



#########################################
####### GO slim | Stacked Bars ##########
#########################################

# Call needed methods
source('R/scripts/GOslimStack_v3.R')
# Use methods, save, grab useful data
totalCode = c()
stackedBars = c(CreateStackColForCategory(abTable,stack,'biological_process'),CreateStackColForCategory(abTable,stack,'cellular_component'),CreateStackColForCategory(abTable,stack,'molecular_function'))
# Write final result file
write(stackedBars,paste('R/tmpGraph/',name,'_stack','.htm',sep=''))

#########################################
########### Static Heatmap ##############
#########################################
hmCode = '<p><i>Generation of PNG heatmaps is not working on the server ...</i></p>'
if(!commit) {
source('R/scripts/StaticHeatmap_v3.R')
pathAndName = paste('src/main/webapp/img/comparison/',c('bio_','cell_','mol_'),name,'.png',sep='')
GenerateHeatmap(abTable,'biological_process',pathAndName[1],hmParameters)
GenerateHeatmap(abTable,'cellular_component',pathAndName[2],hmParameters)
GenerateHeatmap(abTable,'molecular_function',pathAndName[3],hmParameters)

# A bit tricky with the JSTL paths ... But display this anyway
hmCode = paste('<div id="hm_bio"><img src=\"','/metagenomics/img/comparison/',paste0('bio_',name),'.png\"></div>','<div id="hm_cell"><img src=\"','/metagenomics/img/comparison/',paste0('cell_',name),'.png\"></div>','<div id="hm_mol"><img src=\"','/metagenomics/img/comparison/',paste0('mol_',name),'.png\"></div>',sep='')
}
write(hmCode,paste('R/tmpGraph/',name,'_hm','.htm',sep=''))

#########################################
################# PCA ###################
#########################################
source('R/scripts/PCA.R')
PCAcode = PCApage(abTable)
write(PCAcode,paste('R/tmpGraph/',name,'_pca','.htm',sep=''))

#########################################
################ Table ##################
#########################################
source('R/scripts/Table_v3.R')
coucou = DataTableGen(abTable)
write(coucou,paste('R/tmpGraph/',name,'_table','.htm',sep=''))


# Delete table 
# file.remove(paste('R/tables/',name,'.txt',sep=''))

# Console log
message(paste(Sys.time(),'[R - Message] R scripts over.'))

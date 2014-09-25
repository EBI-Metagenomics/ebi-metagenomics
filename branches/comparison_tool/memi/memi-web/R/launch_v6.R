# Laucnh script. Will create the abundance table and then choose  the correct script to execute, following data/visualization
# All is saved in HTML files which are parsed and then displayed.
# TODO: Find a place to store static images.

# Console log
message(paste(Sys.time(),'[R - Message] Launched general R script launch.R'))

# Loading of needed libraries and sources
library(methods)
library(rCharts)

# Allow R to read command arguments
parameters <- commandArgs(TRUE)

# Load pre-processing script
source("R/scripts/PreProcess.R")

# EMG website colors
EMGcolors <- c('#058DC7','#50B432','#ED561B','#EDEF00','#24CBE5','#64E572','#FF9655','#FFF263','#6AF9C4','#DABE88')

# Create table to retrieve names and type from a GO slim term
GOslimTable <- read.table("R/ressources/GOslim_names.csv", sep = ",", quote = "\"", 
                          col.names = c("GO", "function", "type"), colClasses = c("character", "character", "character"))

# Small check to see number of arguments. Uncomment to use.
# print(length(parameters))

files <- (strsplit(parameters[1],split=',')[[1]]) # Creation of a vector of file names from the list in parameter
data <- parameters[2] # Chosen data
keep <- as.logical(parameters[3]) # Keep names or not ?
name <- parameters[4] # output file name
level <- as.integer(parameters[5]) # Level of collapsing (if there is collapsing ...)
vis <- parameters[6] # Chosen visualization

# Advanced settings
stack <- as.numeric(parameters[7]) # Stacking threshold for stacked bars
hmParameters <- (strsplit(parameters[8],split=',')[[1]]) #Heatmap parameters


# Print arguments to see if everything is okay. Uncomment to use.
# print(parameters)

# Launch abundance table creation script and print it for debugging.
abTable = CreateAbTable(files, data, keep, name, level)
# print(abTable) # Uncomment line to print

# Dealing with all visualizations

#########################################
############### Overview ################
#########################################

# Call needed methods
source('R/scripts/PieChartOverview_v3.R')
# Use methods (generate, grab useful data)
graphCode = generateOverview(abTable)
# Write final result file
write(graphCode,paste('R/tmpGraph/',name,'_overview','.htm',sep=''))


#########################################
############### Barcharts ###############
#########################################

fullBarCode <- 'You have more than 10 samples, cannot display bar chart visualization.'

if(length(files) < 10) {
source('R/scripts/GOslimBar_v2.R')
bio = CreateGraphForCategory(abTable,'biological_process')
mol = CreateGraphForCategory(abTable,'molecular_function')
cell = CreateGraphForCategory(abTable,'cellular_component')

# Save
bio$save(paste('R/tmpGraph/',name,'_bio.htm',sep=''),cdn=TRUE)
mol$save(paste('R/tmpGraph/',name,'_mol.htm',sep=''),cdn=TRUE)
cell$save(paste('R/tmpGraph/',name,'_cell.htm',sep=''),cdn=TRUE)

# Grab some code
fileNames = c(paste('R/tmpGraph/',name,'_bio.htm',sep=''),
paste('R/tmpGraph/',name,'_mol.htm',sep=''),
paste('R/tmpGraph/',name,'_cell.htm',sep=''))
fileContent = c(paste(readLines(fileNames[1]),collapse='\n'),paste(readLines(fileNames[2]),collapse='\n'),paste(readLines(fileNames[3]),collapse='\n'))
grabbedStyle <- c() # regmatches(fileContent[1], regexpr('<script src=\'http://.+</style>', fileContent[1]))
grabbedBio <- regmatches(fileContent[1], regexpr('<div id=\'chart.+</script>', fileContent[1]))
grabbedMol <- regmatches(fileContent[2], regexpr('<div id=\'chart.+</script>', fileContent[2]))
grabbedCell <- regmatches(fileContent[3], regexpr('<div id=\'chart.+</script>', fileContent[3]))

# Remove the files to keep space
file.remove(fileNames)

# Store and write the final result. If too many samples, write an error message instead.
fullBarCode <- c(grabbedStyle,grabbedMol,grabbedBio,grabbedCell)
}

write(fullBarCode,paste('R/tmpGraph/',name,'_bar','.htm',sep=''))



#########################################
####### GO slim | Stacked Bars ##########
#########################################

# Call needed methods
source('R/scripts/GOslimStack.R')
# Use methods, save, grab useful data
stackedBar = CreateGraph(abTable,stack)
stackedBar$save(paste('R/tmpGraph/',name,'.htm',sep=''),cdn=TRUE) # save temporary file

# Grab some code
fileName = paste('R/tmpGraph/',name,'.htm',sep='')
fileContent = paste(readLines(fileName),collapse='\n')
grabbedStyle <- c()#regmatches(fileContent, regexpr('<script src=\'http://.+</style>', fileContent))
grabbedGraph <- regmatches(fileContent, regexpr('<div id=\'chart.+</script>',fileContent))

# Remove the files to keep space
file.remove(fileName)

# Write final result file
write(c(grabbedStyle,grabbedGraph),paste('R/tmpGraph/',name,'_stack','.htm',sep=''))

#########################################
########### Static Heatmap ##############
#########################################

source('R/scripts/StaticHeatmap.R')
pathAndName = paste('src/main/webapp/img/comparison/',name,'.png',sep='')
GenerateHeatmap(abTable,pathAndName,hmParameters)

# A bit tricky with the JSTL paths ... But display this anyway
write(paste('<img src=\"','/metagenomics/img/comparison/',name,'.png\">',sep=''),paste('R/tmpGraph/',name,'_hm','.htm',sep=''))

#########################################
################ Table ##################
#########################################
source('R/scripts/Table.R')
coucou = DataTableGen(abTable)
write(coucou,paste('R/tmpGraph/',name,'_table','.htm',sep=''))


# Delete table 
file.remove(paste('R/tables/',name,'.txt',sep=''))

# Console log
message(paste(Sys.time(),'[R - Message] R scripts over.'))

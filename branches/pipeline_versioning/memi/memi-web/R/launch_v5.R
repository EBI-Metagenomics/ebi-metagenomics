# Laucnh script. Will create the abundance table and then choose  the correct script to execute, following data/visualization
# All is saved in HTML files which are parsed then displayed.
# TODO: Find a place to store static images.
# TODO: Find the good place to store all the scripts

# Console log
message(paste(Sys.time(),'[Message] Launched script launch.R'))

# Loading of needed libraries and sources
library(methods)
library(rCharts)

source("R/preProcess.r")

# EMG website colors
EMGcolors <- c('#058DC7','#50B432','#ED561B','#EDEF00','#24CBE5','#64E572','#FF9655','#FFF263','#6AF9C4','#DABE88')

# Allow R to read command arguments
parameters <- commandArgs(TRUE)

# Small check to see number of arguments
print(length(parameters))

files <- (strsplit(parameters[1],split=',')[[1]]) # Creation of a vector of file names from the list in parameter
data <- parameters[2] # Chosen data
keep <- as.logical(parameters[3]) # Keep names or not ?
name <- parameters[4] # output file name
level <- as.integer(parameters[5]) # Level of collapsing (if there is collapsing ...)
vis <- parameters[6] # Chosen visualization

# Advanced settings
stack <- as.numeric(parameters[7]) # Stacking threshold for stacked bars
hmParameters <- (strsplit(parameters[8],split=',')[[1]])


# Print arguments to see if everything is okay
print(parameters)

# Launch abundance table creation script and print it for debugging
abTable = CreateAbTable(files, data, keep, name, level)
print(abTable)

#########################################
############### Overview ################
#########################################

# Call needed methods
source('R/vis/PieChartOverview_v2.r')
# Use methods, save, grab useful data
graph = generateOverview(abTable)
graph$save(paste('R/Graphs/tmp/',name,'.htm',sep=''),cdn=TRUE) # save temporary file

# Grab some code
fileName = paste('R/Graphs/tmp/',name,'.htm',sep='')
fileContent = paste(readLines(fileName),collapse='\n')
grabbedStyle <- c() # regmatches(fileContent, regexpr('<script src=\'http://.+</style>', fileContent))
grabbedGraph <- regmatches(fileContent, regexpr('<div id=\'chart.+</script>',fileContent))

# Remove the files to keep space
file.remove(fileName)

# Write final result file
write(c(grabbedStyle,grabbedGraph),paste('R/Graphs/',name,'_overview','.htm',sep=''))


#########################################
############### Barcharts ###############
#########################################

source('R/vis/GOslimBar_v2.r')
bio = CreateGraphForCategory(abTable,'biological_process')
mol = CreateGraphForCategory(abTable,'molecular_function')
cell = CreateGraphForCategory(abTable,'cellular_component')
message('OK. Now save and grep.')

# Save
mol$save(paste('R/Graphs/mol/',name,'.htm',sep=''),cdn=TRUE)
bio$save(paste('R/Graphs/bio/',name,'.htm',sep=''),cdn=TRUE)
cell$save(paste('R/Graphs/cell/',name,'.htm',sep=''),cdn=TRUE)

# Grab some code
fileNames = c(paste('R/Graphs/mol/',name,'.htm',sep=''),
paste('R/Graphs/bio/',name,'.htm',sep=''),
paste('R/Graphs/cell/',name,'.htm',sep=''))
fileContent = c(paste(readLines(fileNames[1]),collapse='\n'),paste(readLines(fileNames[2]),collapse='\n'),paste(readLines(fileNames[3]),collapse='\n'))
grabbedStyle <- c() # regmatches(fileContent[1], regexpr('<script src=\'http://.+</style>', fileContent[1]))
grabbedBio <- regmatches(fileContent[1], regexpr('<div id=\'chart.+</script>', fileContent[1]))
grabbedMol <- regmatches(fileContent[2], regexpr('<div id=\'chart.+</script>', fileContent[2]))
grabbedCell <- regmatches(fileContent[3], regexpr('<div id=\'chart.+</script>', fileContent[3]))

# Remove the files to keep space
file.remove(fileNames)

# Store and write the final result. If too many samples, write an error message instead.
fullBarCode <- c(grabbedStyle,grabbedMol,grabbedBio,grabbedCell)

if(length(files) > 10) fullBarCode <- 'TOO MANY SAMPLES! CAN\'T. HANDLE. SAMPLENESS.'

write(fullBarCode,paste('R/Graphs/',name,'_bar','.htm',sep=''))



#########################################
####### GO slim | Stacked Bars ##########
#########################################

# Call needed methods
source('R/vis/GOslimStack.r')
# Use methods, save, grab useful data
stackedBar = CreateGraph(abTable,stack)
stackedBar$save(paste('R/Graphs/tmp/',name,'.htm',sep=''),cdn=TRUE) # save temporary file

# Grab some code
fileName = paste('R/Graphs/tmp/',name,'.htm',sep='')
fileContent = paste(readLines(fileName),collapse='\n')
grabbedStyle <- c()#regmatches(fileContent, regexpr('<script src=\'http://.+</style>', fileContent))
grabbedGraph <- regmatches(fileContent, regexpr('<div id=\'chart.+</script>',fileContent))

# Remove the files to keep space
file.remove(fileName)

# Write final result file
write(c(grabbedStyle,grabbedGraph),paste('R/Graphs/',name,'_stack','.htm',sep=''))

#########################################
########### Static Heatmap ##############
#########################################

source('R/vis/StaticHeatmap.r')
pathAndName = paste('src/main/webapp/img/comparison/',name,'.png',sep='')
GenerateHeatmap(abTable,pathAndName,hmParameters)

# A bit tricky with the JSTL paths ... But display this anyway
write(paste('<img src=\"','/metagenomics/img/comparison/',name,'.png\">',sep=''),paste('R/Graphs/',name,'_hm','.htm',sep=''))

#########################################
################ Table ##################
#########################################
source('R/vis/Table.r')
coucou = DataTableGen(abTable)
write(coucou,paste('R/Graphs/',name,'_table','.htm',sep=''))



# Console log
message(paste(Sys.time(),'[Message] R scripts over.'))

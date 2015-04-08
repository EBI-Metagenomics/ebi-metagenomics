# Launch script. Will create the abundance table and then generate each visualization
# All is saved in HTML files which are parsed and then displayed.
# Nb: Logic changed for parameters. Older versions won't work anymore.

# Console log
message(paste(Sys.time(),'[R - Message] Launched general R script launch.R'))

# Loading of needed libraries and sources
library(methods)
library(rCharts)

# A boolean to disable heatmap generation as it doesn't work on the server ...
commit = FALSE

# Allow R to read command arguments
parameters <- commandArgs(TRUE)

# Load pre-processing script
source("R/scripts/PreProcess_v11.R")

# EMG website colors
EMGcolors <- c('#058DC7','#50B432','#ED561B','#EDEF00','#24CBE5','#64E572','#FF9655','#FFF263','#6AF9C4','#DABE88')

# A small operator that is quite useful
"%w/o%" <- function(x, y) x[!x %in% y] # ->  x without y 

# Create table to retrieve names and type from a GO slim term
GOslimTable <- read.table("R/ressources/GOslim_names.csv", sep = ",", quote = "\"", 
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
source('R/scripts/PieChartOverview_v4.R')
# Use methods (generate, grab useful data)
graphCode = generateOverview(abTable)
# Write final result file
write(graphCode,paste('R/tmpGraph/',name,'_overview','.htm',sep=''))


#########################################
############### Barcharts ###############
#########################################

fullBarCode <- 'You have more than 10 samples, cannot display bar chart visualization.'

if(length(files) <= 10) {
source('R/scripts/GOslimBar_v3.R')
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
source('R/scripts/GOslimStack_v2.R')
# Use methods, save, grab useful data
totalCode = c()
stackedBars = list(CreateGraph(abTable,stack,'biological_process'),CreateGraph(abTable,stack,'cellular_component'),CreateGraph(abTable,stack,'molecular_function'))
divHelper = c('<div id=\"stack_bio\">','<div id=\"stack_cell\">','<div id=\"stack_mol\">') # I'll explain this later. Lazy.
for(i in 1:3) {
stackedBars[[i]]$save(paste('R/tmpGraph/',name,'.htm',sep=''),cdn=TRUE) # save temporary file
# Grab some code
fileName = paste('R/tmpGraph/',name,'.htm',sep='')
fileContent = paste(readLines(fileName),collapse='\n')
grabbedStyle <- c()#regmatches(fileContent, regexpr('<script src=\'http://.+</style>', fileContent))
grabbedGraph <- regmatches(fileContent, regexpr('<div id=\'chart.+</script>',fileContent))
totalCode = c(totalCode,paste(divHelper[i],grabbedGraph,'</div>'))
# Remove the files to keep space
file.remove(fileName)
}
# Write final result file
write(c(totalCode),paste('R/tmpGraph/',name,'_stack','.htm',sep=''))

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
################ Table ##################
#########################################
source('R/scripts/Table_v2.R')
coucou = DataTableGen(abTable)
write(coucou,paste('R/tmpGraph/',name,'_table','.htm',sep=''))


# Delete table 
# file.remove(paste('R/tables/',name,'.txt',sep=''))

# Console log
message(paste(Sys.time(),'[R - Message] R scripts over.'))

# This script uses the heatmap.2() function from the 'gplots' R package to generate static heatmaps (SVG output).
# Note: This version generates a SVG output for heatmaps and uses a different logic than older versions. It has to be used with 'launch_v12.R' and won't work with previous versions. 

# Console log
message(paste(Sys.time(), "[R - Message] Launched R script StaticHeatmap_v6.R"))


AbTableToFraction <- function(abTable) {
  # Converts absolute values of an abundance table to 'fraction' (values comprised in 0 and 1). 
  #
  # Args:
  #   abTable: An abundance table for selected samples. Rows: Samples, Columns: observations. 
  #            In this script, this function is used on didvided abundance table (only one category of GO terms). 
  #
  # Returns:
  #   An abundance table with 0 to 1 values.

  fracAbTable <- abTable
  maxOfAll <- rowSums(fracAbTable) # Get the total number of matches for each sample (used to calculate the proportion of each term)
  for (i in 1:length(maxOfAll)) {
    fracAbTable[i, ] <- round(((fracAbTable[i, ]/maxOfAll[i])), 5)
  }
  # If any 'NA' value is generated during conversion, replace it by a zero
  fracAbTable[is.nan(fracAbTable)] <- 0
  return(fracAbTable)
}

ReplaceIfTooLong = function(string, maxChar, afterCut) {  
  # Checks the size of the string placed in parameter. If it is too long (more characters than 'maxChar'), 
  # it is cut and replaced by a substring followed by additional characters ('afterCut').
  # 
  # Args:
  #   string: A string that the function will replace if it is too long
  #   maxChar: An integer that sets the maximum length 
  #   afterCut: Additional character(s) added at the end of the string if it is cut. 
  #
  # Returns:
  #   A new string object, cut if it was too long.
  newString <- ifelse(nchar(string) >= maxChar, # Tests length of string 
    paste0(substr(x = string, start = 1, stop = (maxChar - nchar(afterCut))), afterCut), # Replace it if needed
    string) # Keeps the original string otherwise
  return(newString)
}

GenerateHeatmap <- function(generalAbTable, category, filePath, fileName, hmParametersVector) {
  # Generates a heatmap for the selected GO category from an abundance table and returns all needed
  # HTML code to display it on the result page.  
  #
  # Args:
  #   generalAbTable: The general abundance table for selected samples (all GO categories mixed) 
  #   category: The GO category for which we want to generate a heatmap
  #   filePath: The path for the heatmap image file
  #   fileName: The file name for the heatmap image file WITH EXTENSION
  #   hmParametersVector: a vector containing user-chosen parameters for the heatmaps (see launch script)
  #                       [1] - Color key display (boolean)
  #                       [2] - Hierarchical clustering (column, row or both - string) 
  #                       [3] - Draw dendrograms (column, row or both - string)
  #
  # Returns:
  #   Needed HTML code to insert a heatmap on the result page (h3 tags + div + img src)

  abTable <- AbTableToFraction(DivideAbTable(generalAbTable, category)) # Retrieves data for the heatmap

  # Gives an 'indice' depending on the current GO category.
  # Used to know which title / h3 id / h3 text has to be used for the current heatmap
  categoryIndice <- 0
  if (category == "biological_process") 
    categoryIndice <- 1
  if (category == "cellular_component") 
    categoryIndice <- 2
  if (category == "molecular_function") 
    categoryIndice <- 3

  correctNames <- c("Biological process", "Cellular component", "Molecular function") # Correct names of GO category
  h3Names <- c("Biological process", "Cellular component", "Molecular function") # Text for the h3 HTML tags
  h3Id <- c("hm_bio_title", "hm_cell_title", "hm_mol_title") # Identifier for the h3 HTML tags
  divId <- c("hm_bio","hm_cell","hm_mol") # Identifier for the heatmap divs
  hmUrl <- "/metagenomics/tmp/comparison/" # The URL path where heatmaps 
  

  # Changes column names to have GO names only (instead of all) and cut them if they are too long.
  # Modify 'maxChar' value if you want longer or shorter labels
  colnames(abTable) <- sapply(sapply(sapply(colnames(abTable), strsplit, split = "\\|", USE.NAMES = FALSE), 
    "[[", 2), 
    ReplaceIfTooLong, maxChar = 25, afterCut = '...')
  
  # Colors
  maxMatches <- max(abTable)
  greenToRed <- colorRampPalette(c("green", "yellow", "red"))(n = 299) # Number of color has to be shorter than the number of breaks
  colBreaks <- c(seq(0, (0.25 * maxMatches), length = 100), # First color range
                  seq((0.25 * maxMatches), (0.75 * maxMatches), length = 100), # Second color range
                  seq((0.75 * maxMatches), maxMatches, length = 100)) # Third color range

  # Interpret the information retrieved for hierarchical clustering (conversion into a vector of logical values)
  boolClust <- c(TRUE, TRUE)
  if (hmParametersVector[2] == "column") 
    boolClust <- c(FALSE, TRUE)
  if (hmParametersVector[2] == "row") 
    boolClust <- c(TRUE, FALSE)
  if (hmParametersVector[2] == "none") 
    boolClust <- c(FALSE, FALSE)

  # Change the font size of row if there are only a few samples (less than 4 currently)
  rowFontSize <- 1.2 
  if(nrow(abTable) < 4) rowFontSize <- 1.0
  if(nrow(abTable) > 100) rowFontSize <- 0.7


  # SVG device opening. Should work on the server. 
  # pathSep should be defined in the 'launch' script
  CairoSVG(file = paste(filePath, fileName, sep = pathSep), pointsize = 5)
  # Creation of the heatmap
  heatmap.2(data.matrix(abTable), 
            main = correctNames[categoryIndice], # Title displayed on the heatmap image (clean GO category currently)
            cexRow = rowFontSize,  # cexCol = 1, # Changes size of font for rows and columns respectively
            col = greenToRed, breaks = colBreaks, # Colors (green to red currently) and color breaks
            trace = "none", density.info = "none", # Disable display of the trace and density information (unreadable but enabled by default...)
            Rowv = boolClust[1], Colv = boolClust[2],  # Clustering control (boolean for rows and columns respectively)
            key = as.logical(hmParametersVector[1]), # Color key control (boolean, display or not)
            dendrogram = hmParametersVector[3], # Dendrogram control (valid values : 'row','column' or 'both')
            lwid = c(2, 8), lhei = c(2, 8), # Organization of the space on the image (2x2 matrix, see Confluence page for more details)
            margins = c(13, 13)) # Image margins (should be large enough to display labels)
  dev.off() # Close SVG device and save image

  # Read the newly created file and returns it : <svg> ... </svg> basically.
  h3Tag <- paste0("<h3 id=\"",h3Id[categoryIndice],"\">",h3Names[categoryIndice],"</h3>") # Creates the h3 HTML tag with correct name / id
  hmDiv <- paste(paste0("<div id=\"",divId[categoryIndice],"\">"), # Creates the heatmap div
                 paste0("<img src=\"",hmUrl,fileName,"\">"), # img src tag
                 "</div>", sep="\n") # End of div
  
  # Total code is created and returned 
  svgCode = c(h3Tag, '\n', hmDiv)
  return(svgCode)
} 


package uk.ac.ebi.interpro.metagenomics.memi.services;

import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadableFileDefinition;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.FileDefinitionId;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Returns the file definition for the requested export value.
 *
 * @author Maxim Scheremetjew
 */
public class ExportValueService {

    @Resource
    protected Map<String, DownloadableFileDefinition> fileDefinitionsMap;

    private final String[] exportRequestParamValues = new String[]{"biom",
            "taxa", "tree", "5SrRNA", "16SrRNA", "23SrRNA", "readsWithMatches",
            "readsWithoutMatches", "orfWithoutAnnotation", "cdsWithoutAnnotation", "otuTable",
            "hdf5Biom", "JSONBiom", "prunedTree", "maskedReads", "readsWithPredictedCDS",
            "predictedCDS"};

    public DownloadableFileDefinition findResultFileDefinition(final String exportValue) {
        DownloadableFileDefinition fileDefinition = null;
        if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[0])) {
            fileDefinition = fileDefinitionsMap.get(FileDefinitionId.OTUS_BIOM_FORMAT_FILE.name());
        } else if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[1])) {
            fileDefinition = fileDefinitionsMap.get(FileDefinitionId.TAX_ANALYSIS_TSV_FILE.name());
        }//if export value = tree
        else if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[2])) {
            fileDefinition = fileDefinitionsMap.get(FileDefinitionId.TAX_ANALYSIS_TREE_FILE.name());
        }//if export value = 5SrRNA
        else if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[3])) {
            fileDefinition = fileDefinitionsMap.get(FileDefinitionId.R_RNA_5S_FASTA_FILE.name());
        } //if export value = 16SrRNA
        else if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[4])) {
            fileDefinition = fileDefinitionsMap.get(FileDefinitionId.R_RNA_16S_FASTA_FILE.name());
        }//if export value = 23SrRNA
        else if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[5])) {
            fileDefinition = fileDefinitionsMap.get(FileDefinitionId.R_RNA_23S_FASTA_FILE.name());
        }//if export value = readsWithMatches
        else if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[6])) {
            fileDefinition = fileDefinitionsMap.get(FileDefinitionId.READS_WITH_MATCHES_FASTA_FILE.name());
        }//if export value = readsWithoutMatches
        else if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[7])) {
            fileDefinition = fileDefinitionsMap.get(FileDefinitionId.READS_WITHOUT_MATCHES_FASTA_FILE.name());
        }//
        else if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[8])) {
            fileDefinition = fileDefinitionsMap.get(FileDefinitionId.PREDICTED_ORF_WITHOUT_ANNOTATION_FILE.name());
        }//
        else if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[9])) {
            fileDefinition = fileDefinitionsMap.get(FileDefinitionId.PREDICTED_CDS_WITHOUT_ANNOTATION_FILE.name());
        }//
        else if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[10])) {
            fileDefinition = fileDefinitionsMap.get(FileDefinitionId.OTU_TABLE_FILE.name());
        }//
        else if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[11])) {
            fileDefinition = fileDefinitionsMap.get(FileDefinitionId.HDF5_BIOM_FILE.name());
        }//
        else if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[12])) {
            fileDefinition = fileDefinitionsMap.get(FileDefinitionId.JSON_BIOM_FILE.name());
        }//
        else if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[13])) {
            fileDefinition = fileDefinitionsMap.get(FileDefinitionId.PRUNED_TREE_FILE.name());
        }//
        else if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[14])) {
            fileDefinition = fileDefinitionsMap.get(FileDefinitionId.MASKED_FASTA.name());
        }//
        else if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[15])) {
            fileDefinition = fileDefinitionsMap.get(FileDefinitionId.READS_WITH_PREDICTED_CDS_FILE.name());
        }//
        else if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[16])) {
            fileDefinition = fileDefinitionsMap.get(FileDefinitionId.PREDICTED_CDS_FILE.name());
        } else {
            //leave as null
        }
        return fileDefinition;
    }
}

package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.results;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.ExperimentTypeE;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.ExperimentType;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileExistenceChecker;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.tabActivation.FunctionalAnalysisTab;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.tabActivation.TaxonomicAnalysisTab;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.AbstractResultViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.AbstractViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.UserManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: maxim
 * Date: 3/18/15
 * Time: 12:49 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractResultViewModelBuilder<E extends AbstractResultViewModel> extends AbstractViewModelBuilder<E> implements ViewModelBuilder<E> {

    private final static Log log = LogFactory.getLog(TaxonomicViewModelBuilder.class);

    private List<ResultFileDefinitionImpl> qualityControlFileDefinitions;

    private List<FunctionalAnalysisFileDefinition> functionalAnalysisFileDefinitions;

    private List<ResultFileDefinitionImpl> taxonomicAnalysisFileDefinitions;

    protected AnalysisJob analysisJob;

    protected MemiPropertyContainer propertyContainer;

    protected String pageTitle;

    protected List<Breadcrumb> breadcrumbs;


    protected AbstractResultViewModelBuilder(UserManager sessionMgr,
                                             EBISearchForm ebiSearchForm,
                                             String pageTitle,
                                             List<Breadcrumb> breadcrumbs,
                                             MemiPropertyContainer propertyContainer,
                                             List<ResultFileDefinitionImpl> qualityControlFileDefinitions,
                                             List<FunctionalAnalysisFileDefinition> functionalAnalysisFileDefinitions,
                                             List<ResultFileDefinitionImpl> taxonomicAnalysisFileDefinitions,
                                             AnalysisJob analysisJob) {
        super(sessionMgr, ebiSearchForm);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
        this.qualityControlFileDefinitions = qualityControlFileDefinitions;
        this.functionalAnalysisFileDefinitions = functionalAnalysisFileDefinitions;
        this.taxonomicAnalysisFileDefinitions = taxonomicAnalysisFileDefinitions;
        this.analysisJob = analysisJob;
    }

    protected Submitter getSessionSubmitter(UserManager sessionMgr) {
        if (sessionMgr != null && sessionMgr.getUserAuthentication() != null) {
            return sessionMgr.getUserAuthentication().getSubmitter();
        }
        return null;
    }

    protected AnalysisStatus getAnalysisStatus(boolean isAnalysisCompleted) {
        if (!isAnalysisCompleted) {
            return new AnalysisStatus(
                    new TaxonomicAnalysisTab(true, true, true, true),
                    true, true,
                    new FunctionalAnalysisTab(true, true, true));
        }
        //Set qualityControlTab value
        //If one of the quality control files does exist the tab gets activated
        boolean qualityControlTabDisabled = true;
        for (IResultFileDefinition fileDefinition : qualityControlFileDefinitions) {
            File fileObject = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, fileDefinition);
            boolean doesExist = FileExistenceChecker.checkFileExistence(fileObject);
            if (doesExist) {
                qualityControlTabDisabled = false;
                break;
            }
        }
        //Set abundance/stats Tab value
        //If one of the abundance files does NOT exist the tab gets deactivated
        boolean abundanceTabDisabled = checkForExistenceOfAbundanceFiles(analysisJob);
        //
        //Set functional analysis tab object
        boolean isInterProMatchSectionDisabled = true;
        boolean isGoSectionDisabled = true;
        boolean isSequenceFeatureSectionDisabled = true;
        //Only check for functional result files if it is not amplicon data
        //Amplicon analyses do not produce any sensible functional results, you might see artifact, which you do not want to render
        if (!isAmpliconData()) {
            for (FunctionalAnalysisFileDefinition fileDefinition : functionalAnalysisFileDefinitions) {
                File fileObject = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, fileDefinition);
                boolean doesExist = FileExistenceChecker.checkFileExistence(fileObject);
                if (doesExist) {
                    if (fileDefinition.getIdentifier().equalsIgnoreCase(FileDefinitionId.INTERPRO_MATCHES_SUMMARY_FILE.toString())) {
                        isInterProMatchSectionDisabled = false;
                    } else if (fileDefinition.getIdentifier().equalsIgnoreCase(FileDefinitionId.GO_COMPLETE_FILE.toString())) {
                        isGoSectionDisabled = false;
                    } else if (fileDefinition.getIdentifier().equalsIgnoreCase(FileDefinitionId.SEQUENCE_FEATURE_SUMMARY_FILE.toString())) {
                        isSequenceFeatureSectionDisabled = false;
                    }
                }
            }
        }
        //
        //Set taxonomic analysis tab object
        boolean isPieChartTabDisabled = true;
        boolean isBarChartTabDisabled = true;
        boolean isStackChartTabDisabled = true;
        boolean isKronaTabDisabled = true;
        for (ResultFileDefinitionImpl fileDefinition : taxonomicAnalysisFileDefinitions) {
            File fileObject = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, fileDefinition);
            boolean doesExist = FileExistenceChecker.checkFileExistence(fileObject);
            if (doesExist) {
                if (fileDefinition.getIdentifier().equalsIgnoreCase(FileDefinitionId.KRONA_HTML_FILE.toString())) {
                    isKronaTabDisabled = false;
                } else if (fileDefinition.getIdentifier().equalsIgnoreCase(FileDefinitionId.KRONA_HTML_FILE_SSU.toString())) {
                    isKronaTabDisabled = false;
                } else if (fileDefinition.getIdentifier().equalsIgnoreCase(FileDefinitionId.KRONA_HTML_FILE_LSU.toString())) {
                    isKronaTabDisabled = false;
                } else if (fileDefinition.getIdentifier().equalsIgnoreCase(FileDefinitionId.KINGDOM_COUNTS_FILE.toString())) {
                    isPieChartTabDisabled = false;
                    isBarChartTabDisabled = false;
                    isStackChartTabDisabled = false;
                }//From v4 on SSUs and LSUs have been introduced
                else if (fileDefinition.getIdentifier().equalsIgnoreCase(FileDefinitionId.KINGDOM_COUNTS_FILE_SSU.toString())) {
                    isPieChartTabDisabled = false;
                    isBarChartTabDisabled = false;
                    isStackChartTabDisabled = false;
                } else if (fileDefinition.getIdentifier().equalsIgnoreCase(FileDefinitionId.KINGDOM_COUNTS_FILE_LSU.toString())) {
                    isPieChartTabDisabled = false;
                    isBarChartTabDisabled = false;
                    isStackChartTabDisabled = false;
                }
            }
        }
        return new AnalysisStatus(
                new TaxonomicAnalysisTab(isPieChartTabDisabled, isBarChartTabDisabled, isStackChartTabDisabled, isKronaTabDisabled),
                qualityControlTabDisabled,
                abundanceTabDisabled,
                new FunctionalAnalysisTab(isInterProMatchSectionDisabled, isGoSectionDisabled, isSequenceFeatureSectionDisabled));
    }

    private boolean checkForExistenceOfAbundanceFiles(AnalysisJob analysisJob) {
        List<String> abundanceFiles = new ArrayList<String>();
        abundanceFiles.add("charts/tad-plots.svg");
        abundanceFiles.add("charts/fold-change.svg");
        final String resultDirectory = analysisJob.getResultDirectory();
        final String rootPath = propertyContainer.getPathToAnalysisDirectory();
        final String resultDirectoryAbsolute = rootPath + resultDirectory;
        for (String abundanceFile : abundanceFiles) {
            String filename = resultDirectoryAbsolute + File.separator + abundanceFile;
            File fileObject = new File(filename);
            boolean doesExist = FileExistenceChecker.checkFileExistence(fileObject);
            if (!doesExist) {
                // disable tab
                return true;
            }
        }
        // activate tab
        return false;
    }


    /**
     * Reads raw data from the specified file by using a CSV reader. Possible to specify different delimiters.
     */
    protected List<String[]> getRawData(final File file, final char delimiter) {
        List<String[]> rows = null;
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(file), delimiter);
        } catch (FileNotFoundException e) {
            log.warn("Could not find the following file " + file.getAbsolutePath(), e);
        }
        if (reader != null) {
            try {
                rows = reader.readAll();
            } catch (IOException e) {
                log.warn("Could not get rows from CSV file!", e);
            }
        }
        return rows;
    }

    protected boolean isAmpliconData() {
        //We only want to render functional results on the download section if the experiment type is not amplicon
        //Therefore there is no need to create the functional download model for amplicon studies
        //Also we only want to activate the functional tabs for all experiments except amplicon studies
        final ExperimentType experimentType = analysisJob.getExperimentType();
        return (experimentType.getExperimentType().equalsIgnoreCase(ExperimentTypeE.AMPLICON.getExperimentType()) ? true : false);
    }
}
package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.results;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileExistenceChecker;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.tabActivation.FunctionalAnalysisTab;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.tabActivation.TaxonomicAnalysisTab;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.AbstractResultViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.AbstractViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.io.*;
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


    protected AbstractResultViewModelBuilder(SessionManager sessionMgr,
                                             String pageTitle,
                                             List<Breadcrumb> breadcrumbs,
                                             MemiPropertyContainer propertyContainer,
                                             List<ResultFileDefinitionImpl> qualityControlFileDefinitions,
                                             List<FunctionalAnalysisFileDefinition> functionalAnalysisFileDefinitions,
                                             List<ResultFileDefinitionImpl> taxonomicAnalysisFileDefinitions,
                                             AnalysisJob analysisJob) {
        super(sessionMgr);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
        this.qualityControlFileDefinitions = qualityControlFileDefinitions;
        this.functionalAnalysisFileDefinitions = functionalAnalysisFileDefinitions;
        this.taxonomicAnalysisFileDefinitions = taxonomicAnalysisFileDefinitions;
        this.analysisJob = analysisJob;
    }

    protected Submitter getSessionSubmitter(SessionManager sessionMgr) {
        if (sessionMgr != null && sessionMgr.getSessionBean() != null) {
            return sessionMgr.getSessionBean().getSubmitter();
        }
        return null;
    }

    protected AnalysisStatus getAnalysisStatus(boolean isAnalysisCompleted) {
        if (!isAnalysisCompleted) {
            return new AnalysisStatus(
                    new TaxonomicAnalysisTab(true, true, true, true),
                    true,
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
        //
        //Set functional analysis tab object
        boolean isInterProMatchSectionDisabled = true;
        boolean isGoSectionDisabled = true;
        boolean isSequenceFeatureSectionDisabled = true;
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
                } else if (fileDefinition.getIdentifier().equalsIgnoreCase(FileDefinitionId.KINGDOM_COUNTS_FILE.toString())) {
                    isPieChartTabDisabled = false;
                    isBarChartTabDisabled = false;
                    isStackChartTabDisabled = false;
                }
            }
        }
        return new AnalysisStatus(
                new TaxonomicAnalysisTab(isPieChartTabDisabled, isBarChartTabDisabled, isStackChartTabDisabled, isKronaTabDisabled),
                qualityControlTabDisabled,
                new FunctionalAnalysisTab(isInterProMatchSectionDisabled, isGoSectionDisabled, isSequenceFeatureSectionDisabled));
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
}
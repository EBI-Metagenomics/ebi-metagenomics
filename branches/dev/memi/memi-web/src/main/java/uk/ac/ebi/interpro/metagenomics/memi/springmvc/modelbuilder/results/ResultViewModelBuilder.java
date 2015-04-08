package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.results;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.core.comparators.PublicationComparator;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSampleAnnotation;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.*;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileExistenceChecker;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.AnalysisStatus;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.tabActivation.FunctionalAnalysisTab;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.tabActivation.TaxonomicAnalysisTab;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.ResultViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.AbstractViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Model builder class for {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.ResultViewModel}.
 * <p/>
 * See {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder} for more information of how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class ResultViewModelBuilder extends AbstractResultViewModelBuilder<ResultViewModel> {

    private final static Log log = LogFactory.getLog(ResultViewModelBuilder.class);

    private String pageTitle;

    private List<Breadcrumb> breadcrumbs;

    private Sample sample;

    private Run run;

    private ResultViewModel.ExperimentType experimentType;

    private final List<String> archivedSequences;

    public ResultViewModelBuilder(SessionManager sessionMgr,
                                  Sample sample,
                                  Run run,
                                  String pageTitle,
                                  List<Breadcrumb> breadcrumbs,
                                  AnalysisJob analysisJob,
                                  List<String> archivedSequences,
                                  MemiPropertyContainer propertyContainer,
                                  ResultViewModel.ExperimentType experimentType,
                                  List<ResultFileDefinitionImpl> qualityControlFileDefinitions,
                                  List<FunctionalAnalysisFileDefinition> functionalAnalysisFileDefinitions,
                                  List<ResultFileDefinitionImpl> taxonomicAnalysisFileDefinitions) {
        super(sessionMgr, pageTitle, breadcrumbs, propertyContainer, qualityControlFileDefinitions, functionalAnalysisFileDefinitions, taxonomicAnalysisFileDefinitions, analysisJob);
        this.sample = sample;
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.archivedSequences = archivedSequences;
        this.experimentType = experimentType;
        this.run = run;
    }

    @Override
    public ResultViewModel getModel() {
        log.info("Building instance of " + ResultViewModel.class + "...");
        final Submitter submitter = getSessionSubmitter(sessionMgr);

        //Get analysis status
        AnalysisStatus analysisStatus = getAnalysisStatus((sample.getAnalysisCompleted() == null ? false : true));


        final ResultViewModel resultViewModel = new ResultViewModel(submitter,
                pageTitle,
                breadcrumbs,
                sample,
                run,
                analysisJob,
                analysisStatus,
                archivedSequences,
                propertyContainer,
                experimentType);
        return resultViewModel;
    }
}
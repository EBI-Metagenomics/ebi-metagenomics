package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.results;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.AnalysisStatus;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.ResultViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.UserManager;

import java.util.*;

/**
 * Model builder class for {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.ResultViewModel}.
 * <p>
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

    private final List<String> archivedSequences;

    public ResultViewModelBuilder(UserManager sessionMgr,
                                  EBISearchForm ebiSearchForm,
                                  Sample sample,
                                  Run run,
                                  String pageTitle,
                                  List<Breadcrumb> breadcrumbs,
                                  AnalysisJob analysisJob,
                                  List<String> archivedSequences,
                                  MemiPropertyContainer propertyContainer,
                                  List<ResultFileDefinitionImpl> qualityControlFileDefinitions,
                                  List<FunctionalAnalysisFileDefinition> functionalAnalysisFileDefinitions,
                                  List<ResultFileDefinitionImpl> taxonomicAnalysisFileDefinitions) {
        super(sessionMgr, ebiSearchForm, pageTitle, breadcrumbs, propertyContainer, qualityControlFileDefinitions,
                functionalAnalysisFileDefinitions, taxonomicAnalysisFileDefinitions, analysisJob);
        this.sample = sample;
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.archivedSequences = archivedSequences;
        this.run = run;
    }

    @Override
    public ResultViewModel getModel() {
        log.info("Building instance of " + ResultViewModel.class + "...");
        final Submitter submitter = getSessionSubmitter(sessionMgr);
        EBISearchForm ebiSearchForm = getEbiSearchForm();
        //Get analysis status
        AnalysisStatus analysisStatus = getAnalysisStatus((sample.getAnalysisCompleted() == null ? false : true));


        final ResultViewModel resultViewModel = new ResultViewModel(
                submitter,
                ebiSearchForm,
                pageTitle,
                breadcrumbs,
                sample,
                run,
                analysisJob,
                analysisStatus,
                archivedSequences,
                propertyContainer);
        return resultViewModel;
    }
}
package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.results;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.AnalysisStatus;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.FunctionalAnalysisFileDefinition;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.ResultFileDefinitionImpl;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.QualityCheckViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.UserManager;

import java.util.List;

/**
 * Model builder class for {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.QualityCheckViewModel}.
 * <p>
 * See {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder} for more information of how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
public class QualityCheckViewModelBuilder extends AbstractResultViewModelBuilder<QualityCheckViewModel> {

    private final static Log log = LogFactory.getLog(QualityCheckViewModelBuilder.class);

    public QualityCheckViewModelBuilder(UserManager sessionMgr,
                                        EBISearchForm ebiSearchForm,
                                        String pageTitle,
                                        List<Breadcrumb> breadcrumbs,
                                        MemiPropertyContainer propertyContainer,
                                        List<ResultFileDefinitionImpl> qualityControlFileDefinitions,
                                        List<FunctionalAnalysisFileDefinition> functionalAnalysisFileDefinitions,
                                        List<ResultFileDefinitionImpl> taxonomicAnalysisFileDefinitions,
                                        AnalysisJob analysisJob) {
        super(sessionMgr, ebiSearchForm, pageTitle, breadcrumbs, propertyContainer, qualityControlFileDefinitions,
                functionalAnalysisFileDefinitions, taxonomicAnalysisFileDefinitions, analysisJob);
    }

    public QualityCheckViewModel getModel() {
        log.debug("Building instance of " + QualityCheckViewModel.class + "...");
        //Get the sample object from the analysis job
        Sample sample = analysisJob.getSample();
        //Get analysis status
        AnalysisStatus analysisStatus = getAnalysisStatus((sample.getAnalysisCompleted() == null ? false : true));
        return new QualityCheckViewModel(getSessionSubmitter(sessionMgr), getEbiSearchForm(), pageTitle, breadcrumbs, propertyContainer, analysisJob.getSample(), analysisJob, analysisStatus);
    }
}
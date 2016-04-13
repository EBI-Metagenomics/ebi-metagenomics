package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results;

import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.AnalysisResult;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.AnalysisStatus;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.FunctionalAnalysisResult;

import java.util.List;

/**
 * Functional view model.
 *
 * @author Maxim Scheremetjew
 * @since 1.4-SNAPSHOT
 */
public class FunctionalViewModel extends AbstractResultViewModel {

    private Sample sample;

    private Run run;

    private AnalysisStatus analysisStatus;

    private AnalysisJob analysisJob;

    private FunctionalAnalysisResult functionalAnalysisResult;

    public FunctionalViewModel(Submitter submitter,
                               EBISearchForm ebiSearchForm,
                               String pageTitle,
                               List<Breadcrumb> breadcrumbs,
                               MemiPropertyContainer propertyContainer,
                               Sample sample,
                               Run run,
                               AnalysisJob analysisJob,
                               AnalysisStatus analysisStatus,
                               FunctionalAnalysisResult functionalAnalysisResult) {
        super(submitter, ebiSearchForm, pageTitle, breadcrumbs, propertyContainer);
        this.sample = sample;
        this.run = run;
        this.analysisJob = analysisJob;
        this.analysisStatus = analysisStatus;
        this.functionalAnalysisResult = functionalAnalysisResult;
    }

    public Sample getSample() {
        return sample;
    }

    public AnalysisStatus getAnalysisStatus() {
        return analysisStatus;
    }

    public AnalysisJob getAnalysisJob() {
        return analysisJob;
    }

    public Run getRun() {
        return run;
    }

    public FunctionalAnalysisResult getFunctionalAnalysisResult() {
        return functionalAnalysisResult;
    }
}
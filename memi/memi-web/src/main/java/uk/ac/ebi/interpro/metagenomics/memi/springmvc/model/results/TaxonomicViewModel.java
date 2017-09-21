package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results;

import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.AnalysisStatus;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.TaxonomyAnalysisResult;

import java.util.List;

/**
 * Functional view model.
 *
 * @author Maxim Scheremetjew
 * @since 1.4-SNAPSHOT
 */
public class TaxonomicViewModel extends AbstractResultViewModel {

    private Sample sample;

    private Run run;

    private AnalysisJob analysisJob;

    private AnalysisStatus analysisStatus;

    private TaxonomyAnalysisResult taxonomyAnalysisResultSSU;

    private TaxonomyAnalysisResult taxonomyAnalysisResultLSU;


    public TaxonomicViewModel(Submitter submitter,
                              EBISearchForm ebiSearchForm,
                              String pageTitle,
                              List<Breadcrumb> breadcrumbs,
                              MemiPropertyContainer propertyContainer,
                              Sample sample,
                              Run run,
                              AnalysisJob analysisJob,
                              AnalysisStatus analysisStatus,
                              TaxonomyAnalysisResult taxonomyAnalysisResultSSU,
                              TaxonomyAnalysisResult taxonomyAnalysisResultLSU) {
        super(submitter, ebiSearchForm, pageTitle, breadcrumbs, propertyContainer);
        this.sample = sample;
        this.run = run;
        this.analysisJob = analysisJob;
        this.analysisStatus = analysisStatus;
        this.taxonomyAnalysisResultSSU = taxonomyAnalysisResultSSU;
        this.taxonomyAnalysisResultLSU = taxonomyAnalysisResultLSU;
    }

    public Sample getSample() {
        return sample;
    }

    public Run getRun() {
        return run;
    }

    public AnalysisJob getAnalysisJob() {
        return analysisJob;
    }

    public AnalysisStatus getAnalysisStatus() {
        return analysisStatus;
    }

    public TaxonomyAnalysisResult getTaxonomyAnalysisResultSSU() {
        return taxonomyAnalysisResultSSU;
    }

    public TaxonomyAnalysisResult getTaxonomyAnalysisResultLSU() {
        return taxonomyAnalysisResultLSU;
    }
}
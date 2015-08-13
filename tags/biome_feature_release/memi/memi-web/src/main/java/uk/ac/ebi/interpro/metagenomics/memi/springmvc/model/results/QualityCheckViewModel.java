package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results;

import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.AnalysisStatus;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadSection;

import java.util.List;

/**
 * Quality check view model.
 *
 * @author Maxim Scheremetjew
 * @since 1.4-SNAPSHOT
 */
public class QualityCheckViewModel extends AbstractResultViewModel {

    private Sample sample;

    private AnalysisJob analysisJob;

    private AnalysisStatus analysisStatus;

    public QualityCheckViewModel(Submitter submitter,
                                 String pageTitle,
                                 List<Breadcrumb> breadcrumbs,
                                 MemiPropertyContainer propertyContainer,
                                 Sample sample,
                                 AnalysisJob analysisJob,
                                 AnalysisStatus analysisStatus) {
        super(submitter, pageTitle, breadcrumbs, propertyContainer);
        this.sample = sample;
        this.analysisJob = analysisJob;
        this.analysisStatus = analysisStatus;
    }

    public Sample getSample() {
        return sample;
    }

    public AnalysisJob getAnalysisJob() {
        return analysisJob;
    }

    public AnalysisStatus getAnalysisStatus() {
        return analysisStatus;
    }
}
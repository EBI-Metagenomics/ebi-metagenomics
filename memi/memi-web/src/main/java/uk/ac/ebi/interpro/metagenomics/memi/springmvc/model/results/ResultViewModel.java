package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results;

import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.AnalysisStatus;

import java.util.List;

/**
 *
 * Model to hold statistics and results related to the analysis of a sample.
 *
 * @author Maxim Scheremetjew, Phil Jones
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class ResultViewModel extends AbstractResultViewModel {

    private Sample sample;

    private Run run;

    private AnalysisJob analysisJob;

    private AnalysisStatus analysisStatus;

    private final List<String> archivedSequences;

    public ResultViewModel(Submitter submitter,
                           String pageTitle,
                           List<Breadcrumb> breadcrumbs,
                           Sample sample,
                           Run run,
                           AnalysisJob analysisJob,
                           AnalysisStatus analysisStatus,
                           List<String> archivedSequences,
                           MemiPropertyContainer propertyContainer) {
        super(submitter, pageTitle, breadcrumbs, propertyContainer);
        this.sample = sample;
        this.analysisJob = analysisJob;
        this.archivedSequences = archivedSequences;
        this.run = run;
        this.analysisStatus = analysisStatus;
    }

    public Sample getSample() {
        return sample;
    }

    public Run getRun() {
        return run;
    }

    public List<String> getArchivedSequences() {
        return archivedSequences;
    }

    public AnalysisJob getAnalysisJob() {
        return analysisJob;
    }

    public AnalysisStatus getAnalysisStatus() {
        return analysisStatus;
    }
}
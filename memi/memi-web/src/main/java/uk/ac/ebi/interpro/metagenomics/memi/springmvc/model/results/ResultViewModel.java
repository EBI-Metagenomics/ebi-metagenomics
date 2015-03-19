package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results;

import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSampleAnnotation;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Publication;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.*;

import java.util.Collections;
import java.util.List;

/**
 * Model to hold statistics related to the analysis of a sample.
 * <p/>
 * TODO - revisit before beta release.
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

    private ExperimentType experimentType;

    private final List<String> archivedSequences;

    public ResultViewModel(Submitter submitter,
                           String pageTitle,
                           List<Breadcrumb> breadcrumbs,
                           Sample sample,
                           Run run,
                           AnalysisJob analysisJob,
                           AnalysisStatus analysisStatus,
                           List<String> archivedSequences,
                           MemiPropertyContainer propertyContainer,
                           ExperimentType experimentType) {
        super(submitter, pageTitle, breadcrumbs, propertyContainer);
        this.sample = sample;
        this.analysisJob = analysisJob;
        this.archivedSequences = archivedSequences;
        this.experimentType = experimentType;
        //
        this.run = run;
        this.analysisStatus = analysisStatus;
    }

//    public ResultViewModel(Submitter submitter,
//                           String pageTitle,
//                           List<Breadcrumb> breadcrumbs,
//                           Sample sample,
//                           Run run,
//                           List<String> archivedSequences,
//                           MemiPropertyContainer propertyContainer,
//                           ExperimentType experimentType) {
//        this(submitter,
//                pageTitle,
//                breadcrumbs,
//                sample,
//                run,
//                null,
//                null,
//                archivedSequences,
//                propertyContainer,
//                experimentType);
//    }

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

    public ExperimentType getExperimentType() {
        return experimentType;
    }

    public enum ExperimentType {
        GENOMIC, TRANSCRIPTOMIC;

        public String getLowerCase() {
            return toString();
        }

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }
}
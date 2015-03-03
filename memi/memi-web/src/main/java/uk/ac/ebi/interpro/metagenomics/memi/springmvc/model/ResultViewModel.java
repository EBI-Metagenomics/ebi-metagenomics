package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSampleAnnotation;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Publication;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
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
public class ResultViewModel extends ViewModel {

    private Sample sample;

    private Run run;

    private AnalysisJob analysisJob;

    private ExperimentType experimentType;

    private DownloadSection downloadSection;

    private final List<String> archivedSequences;

    private final List<EmgSampleAnnotation> sampleAnnotations;

    private List<Publication> relatedLinks;

    private List<Publication> relatedPublications;

    /**
     * Indicates if the sample of this model is host-associated OR not
     */
    private final boolean isHostAssociated;

    private AnalysisResult analysisResult;

    public ResultViewModel(Submitter submitter,
                           String pageTitle,
                           List<Breadcrumb> breadcrumbs,
                           Sample sample,
                           Run run,
                           AnalysisJob analysisJob,
                           List<String> archivedSequences,
                           MemiPropertyContainer propertyContainer,
                           FunctionalAnalysisResult functionalAnalysisResult,
                           ExperimentType experimentType,
                           final DownloadSection downloadSection,
                           List<Publication> relatedLinks,
                           List<Publication> relatedPublications,
                           boolean isHostAssociated,
                           List<EmgSampleAnnotation> sampleAnnotations,
                           final AnalysisStatus analysisStatus) {
        super(submitter, pageTitle, breadcrumbs, propertyContainer);
        this.sample = sample;
        this.analysisJob = analysisJob;
        this.archivedSequences = archivedSequences;
        this.experimentType = experimentType;
        this.downloadSection = downloadSection;
        this.relatedLinks = relatedLinks;
        this.relatedPublications = relatedPublications;
        this.isHostAssociated = isHostAssociated;
        this.sampleAnnotations = sampleAnnotations;
        //
        this.analysisResult = new AnalysisResult(analysisStatus);
        this.analysisResult.setFunctionalAnalysisResult(functionalAnalysisResult);
        this.run = run;
    }

    public ResultViewModel(Submitter submitter,
                           String pageTitle,
                           List<Breadcrumb> breadcrumbs,
                           Sample sample,
                           Run run,
                           List<String> archivedSequences,
                           MemiPropertyContainer propertyContainer,
                           FunctionalAnalysisResult functionalAnalysisResult,
                           ExperimentType experimentType,
                           final DownloadSection downloadSection,
                           List<Publication> relatedLinks,
                           List<Publication> relatedPublications,
                           boolean isHostAssociated,
                           List<EmgSampleAnnotation> sampleAnnotations,
                           final AnalysisStatus analysisStatus) {
        this(submitter,
                pageTitle,
                breadcrumbs,
                sample,
                run,
                null,
                archivedSequences,
                propertyContainer,
                functionalAnalysisResult,
                experimentType,
                downloadSection,
                relatedLinks,
                relatedPublications,
                isHostAssociated,
                sampleAnnotations,
                analysisStatus);
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

    public List<BiologicalProcessGOTerm> getBiologicalProcessGOTerms() {
        return this.analysisResult.getFunctionalAnalysisResult().getGoTermSection().getBiologicalProcessGoTerms().getBiologicalProcessGOTermList();
    }

    /**
     * Sorted by number of hits.
     */
    public List<BiologicalProcessGOTerm> getSortedBiologicalProcessGOTerms() {
        List<BiologicalProcessGOTerm> result = this.analysisResult.getFunctionalAnalysisResult().getGoTermSection().getBiologicalProcessGoTerms().getBiologicalProcessGOTermList();
        Collections.sort(result, AbstractGOTerm.GoTermComparator);
        return result;
    }

    public List<MolecularFunctionGOTerm> getMolecularFunctionGOTerms() {
        return this.analysisResult.getFunctionalAnalysisResult().getGoTermSection().getMolecularFunctionGoTerms().getMolecularFunctionGOTermList();
    }

    /**
     * Sorted by number of hits.
     */
    public List<MolecularFunctionGOTerm> getSortedMolecularFunctionGOTerms() {
        List<MolecularFunctionGOTerm> result = this.analysisResult.getFunctionalAnalysisResult().getGoTermSection().getMolecularFunctionGoTerms().getMolecularFunctionGOTermList();
        Collections.sort(result, AbstractGOTerm.GoTermComparator);
        return result;
    }

    public List<CellularComponentGOTerm> getCellularComponentGOTerms() {
        return this.analysisResult.getFunctionalAnalysisResult().getGoTermSection().getCellularComponentGoTerms().getCellularComponentGOTermList();
    }

    /**
     * Sorted by number of hits.
     */
    public List<CellularComponentGOTerm> getSortedCellularComponentGOTerms() {
        List<CellularComponentGOTerm> result = this.analysisResult.getFunctionalAnalysisResult().getGoTermSection().getCellularComponentGoTerms().getCellularComponentGOTermList();
        Collections.sort(result, AbstractGOTerm.GoTermComparator);
        return result;
    }

    public AnalysisJob getAnalysisJob() {
        return analysisJob;
    }

    public FunctionalAnalysisResult getFunctionalAnalysisResult() {
        return this.analysisResult.getFunctionalAnalysisResult();
    }

    public ExperimentType getExperimentType() {
        return experimentType;
    }

    public DownloadSection getDownloadSection() {
        return downloadSection;
    }

    public List<EmgSampleAnnotation> getSampleAnnotations() {
        return sampleAnnotations;
    }

    public List<Publication> getRelatedLinks() {
        return relatedLinks;
    }

    public List<Publication> getRelatedPublications() {
        return relatedPublications;
    }

    public boolean isHostAssociated() {
        return isHostAssociated;
    }

    public TaxonomyAnalysisResult getTaxonomyAnalysisResult() {
        return this.analysisResult.getTaxonomyAnalysisResult();
    }

    public void setTaxonomyAnalysisResult(TaxonomyAnalysisResult taxonomyAnalysisResult) {
        this.analysisResult.setTaxonomyAnalysisResult(taxonomyAnalysisResult);
    }

    public AnalysisStatus getAnalysisStatus() {
        return this.analysisResult.getAnalysisStatus();
    }

    public AnalysisResult getAnalysisResult() {
        return analysisResult;
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
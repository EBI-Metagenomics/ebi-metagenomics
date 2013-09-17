package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSampleAnnotation;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Publication;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.*;

import java.util.*;

/**
 * Model to hold statistics related to the analysis of a sample.
 * <p/>
 * TODO - revisit before beta release.
 *
 * @author Maxim Scheremetjew, Phil Jones
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class SampleViewModel extends ViewModel {

    private Sample sample;

    /* This object contains a list of InterPro entries. Loaded from the MG pipeline produced file with the IPR extension
     (summary of InterPro matches).
     */
    private FunctionalAnalysisResult functionalAnalysisResult;

    /* An EmgFile object holds two attributes of the */
    private EmgFile emgFile;

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

    private TaxonomyAnalysisResult taxonomyAnalysisResult;

    private AnalysisStatus analysisStatus;

    public SampleViewModel(Submitter submitter,
                           String pageTitle,
                           List<Breadcrumb> breadcrumbs,
                           Sample sample,
                           EmgFile emgFile,
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
        this.functionalAnalysisResult = functionalAnalysisResult;
        this.emgFile = emgFile;
        this.archivedSequences = archivedSequences;
        this.experimentType = experimentType;
        this.downloadSection = downloadSection;
        this.relatedLinks = relatedLinks;
        this.relatedPublications = relatedPublications;
        this.isHostAssociated = isHostAssociated;
        this.sampleAnnotations = sampleAnnotations;
        this.analysisStatus = analysisStatus;
    }

    public SampleViewModel(Submitter submitter,
                           String pageTitle,
                           List<Breadcrumb> breadcrumbs,
                           Sample sample,
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

    public List<String> getArchivedSequences() {
        return archivedSequences;
    }

    public List<BiologicalProcessGOTerm> getBiologicalProcessGOTerms() {
        return functionalAnalysisResult.getGoTermSection().getBiologicalProcessGoTerms().getBiologicalProcessGOTermList();
    }

    /**
     * Sorted by number of hits.
     */
    public List<BiologicalProcessGOTerm> getSortedBiologicalProcessGOTerms() {
        List<BiologicalProcessGOTerm> result = functionalAnalysisResult.getGoTermSection().getBiologicalProcessGoTerms().getBiologicalProcessGOTermList();
        Collections.sort(result, AbstractGOTerm.GoTermComparator);
        return result;
    }

    public List<MolecularFunctionGOTerm> getMolecularFunctionGOTerms() {
        return functionalAnalysisResult.getGoTermSection().getMolecularFunctionGoTerms().getMolecularFunctionGOTermList();
    }

    /**
     * Sorted by number of hits.
     */
    public List<MolecularFunctionGOTerm> getSortedMolecularFunctionGOTerms() {
        List<MolecularFunctionGOTerm> result = functionalAnalysisResult.getGoTermSection().getMolecularFunctionGoTerms().getMolecularFunctionGOTermList();
        Collections.sort(result, AbstractGOTerm.GoTermComparator);
        return result;
    }

    public List<CellularComponentGOTerm> getCellularComponentGOTerms() {
        return functionalAnalysisResult.getGoTermSection().getCellularComponentGoTerms().getCellularComponentGOTermList();
    }

    /**
     * Sorted by number of hits.
     */
    public List<CellularComponentGOTerm> getSortedCellularComponentGOTerms() {
        List<CellularComponentGOTerm> result = functionalAnalysisResult.getGoTermSection().getCellularComponentGoTerms().getCellularComponentGOTermList();
        Collections.sort(result, AbstractGOTerm.GoTermComparator);
        return result;
    }

    public EmgFile getEmgFile() {
        return emgFile;
    }

    public FunctionalAnalysisResult getFunctionalAnalysisResult() {
        return functionalAnalysisResult;
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
        return taxonomyAnalysisResult;
    }

    public void setTaxonomyAnalysisResult(TaxonomyAnalysisResult taxonomyAnalysisResult) {
        this.taxonomyAnalysisResult = taxonomyAnalysisResult;
    }

    public AnalysisStatus getAnalysisStatus() {
        return analysisStatus;
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
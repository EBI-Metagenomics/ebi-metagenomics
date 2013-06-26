package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSampleAnnotation;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Publication;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadSection;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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
public class SampleViewModel extends ViewModel {

    private Sample sample;

    // URLs to use the Google chart Tool

    /* URL should display a bar chart about a statistical overview */
    private String barChartURL;

    /* URL should display a pie chart which includes GO annotations for biological process */
    private String pieChartBiologicalProcessURL;

    /* URL should display a pie chart which includes GO annotations for molecular function */
    private String pieChartMolecularFunctionURL;

    /* URL should display a pie chart which includes GO annotations for cellular component */
    private String pieChartCellularComponentURL;

    /* List of InterPro entries. Loaded from the MG pipeline produced file with the IPR extension
     (summary of InterPro matches).
     */
    private List<InterProEntry> interProEntries;

    private List<AbstractGOTerm> bioGOTerms;

    /* An EmgFile object holds two attributes of the */
    private EmgFile emgFile;

    private ExperimentType experimentType;

    private DownloadSection downloadSection;

    private final List<String> archivedSequences;

    private final List<EmgSampleAnnotation> sampleAnnotations;

    private final List<Publication> publications;

    /**
     * Indicates if the sample of this model is host-associated OR not
     */
    private final boolean isHostAssociated;

    private TaxonomyAnalysisResult taxonomyAnalysisResult;

    public SampleViewModel(Submitter submitter,
                           String pageTitle,
                           List<Breadcrumb> breadcrumbs,
                           Sample sample,
                           String barChartURL,
                           String pieChartBiologicalProcessURL,
                           String pieChartCellularComponentURL,
                           String pieChartMolecularFunctionURL,
                           List<AbstractGOTerm> bioGOTerms,
                           EmgFile emgFile,
                           List<String> archivedSequences,
                           MemiPropertyContainer propertyContainer,
                           List<InterProEntry> interProEntries,
                           ExperimentType experimentType,
                           final DownloadSection downloadSection,
                           List<Publication> publications,
                           boolean isHostAssociated,
                           List<EmgSampleAnnotation> sampleAnnotations) {
        super(submitter, pageTitle, breadcrumbs, propertyContainer);
        this.sample = sample;
        this.barChartURL = barChartURL;
        this.pieChartBiologicalProcessURL = pieChartBiologicalProcessURL;
        this.pieChartMolecularFunctionURL = pieChartMolecularFunctionURL;
        this.pieChartCellularComponentURL = pieChartCellularComponentURL;
        this.interProEntries = interProEntries;
        this.bioGOTerms = bioGOTerms;
        this.emgFile = emgFile;
        this.archivedSequences = archivedSequences;
        this.experimentType = experimentType;
        this.downloadSection = downloadSection;
        this.publications = publications;
        this.isHostAssociated = isHostAssociated;
        this.sampleAnnotations = sampleAnnotations;
    }

    public SampleViewModel(Submitter submitter,
                           String pageTitle,
                           List<Breadcrumb> breadcrumbs,
                           Sample sample,
                           List<String> archivedSequences,
                           MemiPropertyContainer propertyContainer,
                           List<InterProEntry> interProEntries,
                           ExperimentType experimentType,
                           final DownloadSection downloadSection,
                           List<Publication> publications,
                           boolean isHostAssociated,
                           List<EmgSampleAnnotation> sampleAnnotations) {
        this(submitter,
                pageTitle,
                breadcrumbs,
                sample,
                null,
                null,
                null,
                null,
                null,
                null,
                archivedSequences,
                propertyContainer,
                interProEntries,
                experimentType,
                downloadSection,
                publications,
                isHostAssociated,
                sampleAnnotations);
    }

    public Sample getSample() {
        return sample;
    }

    public String getBarChartURL() {
        return barChartURL;
    }

    public String getPieChartBiologicalProcessURL() {
        return pieChartBiologicalProcessURL;
    }

    public String getPieChartMolecularFunctionURL() {
        return pieChartMolecularFunctionURL;
    }

    public String getPieChartCellularComponentURL() {
        return pieChartCellularComponentURL;
    }

    public List<String> getArchivedSequences() {
        return archivedSequences;
    }

    public List<AbstractGOTerm> getBioGOTerms() {
        return bioGOTerms;
    }

    public EmgFile getEmgFile() {
        return emgFile;
    }

    public List<InterProEntry> getInterProEntries() {
        return interProEntries;
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

    public List<Publication> getPublications() {
        return publications;
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
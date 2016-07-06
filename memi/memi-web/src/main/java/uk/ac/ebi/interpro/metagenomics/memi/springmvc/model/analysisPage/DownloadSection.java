package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

/**
 * Object which is used to build the download section on the analysis page.
 *
 * @author Maxim Scheremetjew
 */
public class DownloadSection {
    private SequencesDownloadSection sequencesDownloadSection;

    private FunctionalDownloadSection functionalDownloadSection;

    private TaxonomyDownloadSection taxonomyDownloadSection;

    public DownloadSection(SequencesDownloadSection sequencesDownloadSection,
                           FunctionalDownloadSection functionalDownloadSection,
                           TaxonomyDownloadSection taxonomyDownloadSection) {
        this.sequencesDownloadSection = sequencesDownloadSection;
        this.functionalDownloadSection = functionalDownloadSection;
        this.taxonomyDownloadSection = taxonomyDownloadSection;
    }

    public SequencesDownloadSection getSequencesDownloadSection() {
        return sequencesDownloadSection;
    }

    public FunctionalDownloadSection getFunctionalDownloadSection() {
        return functionalDownloadSection;
    }

    public TaxonomyDownloadSection getTaxonomyDownloadSection() {
        return taxonomyDownloadSection;
    }
}
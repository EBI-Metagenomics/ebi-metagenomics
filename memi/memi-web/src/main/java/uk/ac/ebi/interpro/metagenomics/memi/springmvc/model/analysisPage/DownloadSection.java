package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

import java.util.List;

/**
 * Object which is used to build the download section on the analysis page.
 *
 * @author Maxim Scheremetjew
 */
public class DownloadSection {
    private SequencesDownloadSection sequencesDownloadSection;

    private FunctionalDownloadSection functionalDownloadSection;

    private List<DownloadLink> taxaAnalysisDownloadLinks;

    public DownloadSection(SequencesDownloadSection sequencesDownloadSection,
                           FunctionalDownloadSection functionalDownloadSection,
                           List<DownloadLink> taxaAnalysisDownloadLinks) {
        this.sequencesDownloadSection = sequencesDownloadSection;
        this.functionalDownloadSection = functionalDownloadSection;
        this.taxaAnalysisDownloadLinks = taxaAnalysisDownloadLinks;
    }

    public SequencesDownloadSection getSequencesDownloadSection() {
        return sequencesDownloadSection;
    }

    public List<DownloadLink> getTaxaAnalysisDownloadLinks() {
        return taxaAnalysisDownloadLinks;
    }

    public FunctionalDownloadSection getFunctionalDownloadSection() {
        return functionalDownloadSection;
    }
}
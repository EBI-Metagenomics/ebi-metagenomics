package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

import java.util.List;

/**
 * Object which is used to build the download section on the analysis page.
 *
 * @author Maxim Scheremetjew
 */
public class DownloadSection {
    private List<DownloadLink> seqDataDownloadLinks;

    private FunctionalDownloadSection functionalDownloadSection;

    private List<DownloadLink> taxaAnalysisDownloadLinks;

    public DownloadSection(List<DownloadLink> seqDataDownloadLinks,
                           FunctionalDownloadSection functionalDownloadSection,
                           List<DownloadLink> taxaAnalysisDownloadLinks) {
        this.seqDataDownloadLinks = seqDataDownloadLinks;
        this.functionalDownloadSection = functionalDownloadSection;
        this.taxaAnalysisDownloadLinks = taxaAnalysisDownloadLinks;
    }

    public List<DownloadLink> getSeqDataDownloadLinks() {
        return seqDataDownloadLinks;
    }

    public List<DownloadLink> getTaxaAnalysisDownloadLinks() {
        return taxaAnalysisDownloadLinks;
    }

    public FunctionalDownloadSection getFunctionalDownloadSection() {
        return functionalDownloadSection;
    }
}
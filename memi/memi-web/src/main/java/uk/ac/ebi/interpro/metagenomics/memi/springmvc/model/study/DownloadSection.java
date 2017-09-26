package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.study;

import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadLink;

import java.util.List;

/**
 * Object which is used to build the download section on the analysis page.
 *
 * @author Maxim Scheremetjew
 */
public class DownloadSection {

    private List<DownloadLink> funcAnalysisDownloadLinks;

    private List<DownloadLink> taxaAnalysisDownloadLinks;

    private List<DownloadLink> statsDownloadLinks;

    public DownloadSection(List<DownloadLink> funcAnalysisDownloadLinks,
                           List<DownloadLink> taxaAnalysisDownloadLinks,
                           List<DownloadLink> statsDownloadLinks) {
        this.funcAnalysisDownloadLinks = funcAnalysisDownloadLinks;
        this.taxaAnalysisDownloadLinks = taxaAnalysisDownloadLinks;
        this.statsDownloadLinks = statsDownloadLinks;
    }

    public List<DownloadLink> getFuncAnalysisDownloadLinks() {
        return funcAnalysisDownloadLinks;
    }

    public List<DownloadLink> getTaxaAnalysisDownloadLinks() {
        return taxaAnalysisDownloadLinks;
    }

    public List<DownloadLink> getStatsDownloadLinks() {
        return statsDownloadLinks;
    }
}
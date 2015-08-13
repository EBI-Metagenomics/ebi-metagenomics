package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

import java.util.List;

/**
 * Object which is used to build the download section on the analysis page
 *
 * @author Maxim Scheremetjew, EMBL-EBI
 */
public class FunctionalDownloadSection {

    private List<DownloadLink> interproscanDownloadLinks;

    private List<DownloadLink> otherDownloadLinks;

    public FunctionalDownloadSection(List<DownloadLink> interproscanDownloadLinks, List<DownloadLink> otherDownloadLinks) {
        this.interproscanDownloadLinks = interproscanDownloadLinks;
        this.otherDownloadLinks = otherDownloadLinks;
    }

    public List<DownloadLink> getInterproscanDownloadLinks() {
        return interproscanDownloadLinks;
    }

    public List<DownloadLink> getOtherDownloadLinks() {
        return otherDownloadLinks;
    }
}

package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Object which is used to build the download section on the analysis page
 *
 * @author Maxim Scheremetjew, EMBL-EBI
 */
public class SequencesDownloadSection {

    private List<DownloadLink> processedReadsLinks;

    private List<DownloadLink> readsWithPredictedCDSLinks;

    private List<DownloadLink> readsWithMatchesLinks;

    private List<DownloadLink> readsWithoutMatchesLinks;

    private List<DownloadLink> predictedCDSLinks;

    private List<DownloadLink> predictedORFWithoutAnnotationLinks;

    private List<DownloadLink> predicatedCDSWithoutAnnotationLinks;

    private List<DownloadLink> otherDownloadLinks;

    public SequencesDownloadSection() {
        this.otherDownloadLinks = new ArrayList<DownloadLink>();
        this.processedReadsLinks = new ArrayList<DownloadLink>();
        this.readsWithPredictedCDSLinks = new ArrayList<DownloadLink>();
        this.readsWithMatchesLinks = new ArrayList<DownloadLink>();
        this.readsWithoutMatchesLinks = new ArrayList<DownloadLink>();
        this.predictedCDSLinks = new ArrayList<DownloadLink>();
        this.predictedORFWithoutAnnotationLinks = new ArrayList<DownloadLink>();
        this.predicatedCDSWithoutAnnotationLinks = new ArrayList<DownloadLink>();
    }

    public List<DownloadLink> getProcessedReadsLinks() {
        return processedReadsLinks;
    }

    public void setProcessedReadsLinks(List<DownloadLink> processedReadsLinks) {
        this.processedReadsLinks = processedReadsLinks;
    }

    public List<DownloadLink> getReadsWithPredictedCDSLinks() {
        return readsWithPredictedCDSLinks;
    }

    public void setReadsWithPredictedCDSLinks(List<DownloadLink> readsWithPredictedCDSLinks) {
        this.readsWithPredictedCDSLinks = readsWithPredictedCDSLinks;
    }

    public List<DownloadLink> getReadsWithMatchesLinks() {
        return readsWithMatchesLinks;
    }

    public void setReadsWithMatchesLinks(List<DownloadLink> readsWithMatchesLinks) {
        this.readsWithMatchesLinks = readsWithMatchesLinks;
    }

    public List<DownloadLink> getReadsWithoutMatchesLinks() {
        return readsWithoutMatchesLinks;
    }

    public void setReadsWithoutMatchesLinks(List<DownloadLink> readsWithoutMatchesLinks) {
        this.readsWithoutMatchesLinks = readsWithoutMatchesLinks;
    }

    public List<DownloadLink> getPredictedCDSLinks() {
        return predictedCDSLinks;
    }

    public void setPredictedCDSLinks(List<DownloadLink> predictedCDSLinks) {
        this.predictedCDSLinks = predictedCDSLinks;
    }

    public List<DownloadLink> getPredictedORFWithoutAnnotationLinks() {
        return predictedORFWithoutAnnotationLinks;
    }

    public void setPredictedORFWithoutAnnotationLinks(List<DownloadLink> predictedORFWithoutAnnotationLinks) {
        this.predictedORFWithoutAnnotationLinks = predictedORFWithoutAnnotationLinks;
    }

    public List<DownloadLink> getPredicatedCDSWithoutAnnotationLinks() {
        return predicatedCDSWithoutAnnotationLinks;
    }

    public void setPredicatedCDSWithoutAnnotationLinks(List<DownloadLink> predicatedCDSWithoutAnnotationLinks) {
        this.predicatedCDSWithoutAnnotationLinks = predicatedCDSWithoutAnnotationLinks;
    }

    public List<DownloadLink> getOtherDownloadLinks() {
        return otherDownloadLinks;
    }

    public void addOtherDownloadLink(DownloadLink otherDownloadLink) {
        this.otherDownloadLinks.add(otherDownloadLink);
    }

    public void addOtherDownloadLinks(List<DownloadLink> otherDownloadLinks) {
        for (DownloadLink downloadLink : otherDownloadLinks) {
            this.otherDownloadLinks.add(downloadLink);
        }
    }

    public List<List<DownloadLink>> getListOfChunkedDownloadLinks() {
        List<List<DownloadLink>> result = new ArrayList<List<DownloadLink>>();
        result.add(getProcessedReadsLinks());
        result.add(getReadsWithPredictedCDSLinks());
        result.add(getReadsWithMatchesLinks());
        result.add(getReadsWithoutMatchesLinks());
        result.add(getPredictedCDSLinks());
        result.add(getPredictedORFWithoutAnnotationLinks());
        result.add(getPredicatedCDSWithoutAnnotationLinks());
        return result;
    }
}
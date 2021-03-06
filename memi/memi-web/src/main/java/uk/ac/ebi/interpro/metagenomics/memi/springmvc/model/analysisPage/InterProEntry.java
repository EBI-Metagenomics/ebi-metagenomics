package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

/**
 * Simple representation of an InterPro entry.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class InterProEntry {

    private String entryID;

    private String entryDescription;

    private int numOfEntryHits;

    public InterProEntry(String entryID, String entryDescription, int numOfEntryHits) {
        this.entryID = entryID;
        // replace all interpro entries with prime symbol in their name e.g.Primosomal protein N' where the ' is translated into \ in highchart
        this.entryDescription = entryDescription.replace("\\", "\\u2032");
        this.numOfEntryHits = numOfEntryHits;
    }

    public String getEntryID() {
        return entryID;
    }

    public void setEntryID(String entryID) {
        this.entryID = entryID;
    }

    public String getEntryDescription() {
        return entryDescription;
    }

    public void setEntryDescription(String entryDescription) {
        this.entryDescription = entryDescription;
    }

    public int getNumOfEntryHits() {
        return numOfEntryHits;
    }

    public void setNumOfEntryHits(int numOfEntryHits) {
        this.numOfEntryHits = numOfEntryHits;
    }
}

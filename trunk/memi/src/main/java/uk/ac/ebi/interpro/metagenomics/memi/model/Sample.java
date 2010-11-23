package uk.ac.ebi.interpro.metagenomics.memi.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a first version of a sample (just a simple version).
 * TODO: Ask for a detailed specification of a sample object
 * TODO: Implement the JUni test for this class
 * TODO: Add Hibernate annotations
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class Sample implements Serializable {

    private long sampleId;

    private String sampleName;

    private Date date;


    public long getSampleId() {
        return sampleId;
    }

    public void setSampleId(long sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
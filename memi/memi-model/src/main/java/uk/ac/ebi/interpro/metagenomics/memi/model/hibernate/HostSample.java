package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * TODO: Description
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Entity
public class HostSample extends Sample {

    @Column(name = "HOST_TAXON_ID")
    private long hostTaxonId;

    @Column(name = "HOST_SEX")
    private String hostSex;

    public String getHostSex() {
        return hostSex;
    }

    public void setHostSex(String hostSex) {
        this.hostSex = hostSex;
    }

    public long getHostTaxonId() {
        return hostTaxonId;
    }

    public void setHostTaxonId(long hostTaxonId) {
        this.hostTaxonId = hostTaxonId;
    }
}
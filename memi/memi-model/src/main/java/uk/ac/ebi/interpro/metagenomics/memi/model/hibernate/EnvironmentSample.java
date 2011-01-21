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
public class EnvironmentSample extends Sample {

    @Column(name = "LAT_LON")
    private String latLon;

    public String getLatLon() {
        return latLon;
    }

    public void setLatLon(String latLon) {
        this.latLon = latLon;
    }
}
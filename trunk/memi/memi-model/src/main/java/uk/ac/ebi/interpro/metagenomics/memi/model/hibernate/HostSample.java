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

    @Column(name = "PHENOTYPE")
    private String phenotype;

    /**
     * Host Id
     */
    @Column(name = "TAXONOMY_ID")
    private int taxonomyId;

    @Column(name = "HOST_SEX")
    private HostSex hostSex;

    public Class<? extends Sample> getClazz() {
        return this.getClass();
    }

    public SampleType getSampleType() {
        return Sample.SampleType.HOST_ASSOCIATED;
    }

    public enum HostSex {
        FEMALE, MALE;
    }

    public String getPhenotype() {
        return phenotype;
    }

    public void setPhenotype(String phenotype) {
        this.phenotype = phenotype;
    }

    public int getTaxonomyId() {
        return taxonomyId;
    }

    public void setTaxonomyId(int taxonomyId) {
        this.taxonomyId = taxonomyId;
    }

    public HostSex getHostSex() {
        return hostSex;
    }

    public void setHostSex(HostSex hostSex) {
        this.hostSex = hostSex;
    }
}
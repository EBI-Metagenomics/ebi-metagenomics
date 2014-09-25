package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import javax.persistence.Entity;

/**
 * TODO: Description
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Entity
public class UndefinedSample extends Sample {

    public Class<? extends Sample> getClazz() {
        return this.getClass();
    }

    public SampleType getSampleType() {
        return Sample.SampleType.UNDEFINED;
    }
}
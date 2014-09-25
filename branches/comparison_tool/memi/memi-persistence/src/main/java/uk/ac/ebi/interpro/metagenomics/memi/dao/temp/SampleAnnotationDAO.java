package uk.ac.ebi.interpro.metagenomics.memi.dao.temp;

import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSampleAnnotation;

import java.util.Collection;

/**
 * Represents interface for {@link EmgSampleAnnotation} DAO.
 * User: maxim
 * Date: 14.09.11
 * Time: 18:13
 * To change this template use File | Settings | File Templates.
 */
public interface SampleAnnotationDAO {
    public Collection<EmgSampleAnnotation> getSampleAnnotations(Long sampleId);
}

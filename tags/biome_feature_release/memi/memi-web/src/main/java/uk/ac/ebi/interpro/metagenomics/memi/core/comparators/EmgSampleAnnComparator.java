package uk.ac.ebi.interpro.metagenomics.memi.core.comparators;

import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSampleAnnotation;

import java.util.Comparator;

/**
 * Used to order sample annotation objects.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class EmgSampleAnnComparator implements Comparator<EmgSampleAnnotation> {

    public int compare(EmgSampleAnnotation ann1, EmgSampleAnnotation ann2) {
        String annotationName1 = ann1.getAnnotationName();
        String annotationName2 = ann2.getAnnotationName();
        if (annotationName1 != null && annotationName2 != null) {
            annotationName1 = annotationName1.trim().toLowerCase();
            annotationName2 = annotationName2.trim().toLowerCase();
            return annotationName1.compareTo(annotationName2);
        }
        return -1;
    }
}

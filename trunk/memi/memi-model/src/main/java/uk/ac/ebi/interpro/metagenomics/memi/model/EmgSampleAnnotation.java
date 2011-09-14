package uk.ac.ebi.interpro.metagenomics.memi.model;

import java.util.Map;

/**
 * Represents a sample annotation object.
 * <p/>
 * User: maxim
 * Date: 14.09.11
 * Time: 18:30
 * To change this template use File | Settings | File Templates.
 */
public class EmgSampleAnnotation {
    private String annotationName;

    private String annotationValue;

    /**
     * Unit of measurement. Shouldn't be null, if the value of an annotation is measured.
     */
    private String unit;

    public EmgSampleAnnotation(String annotationName, String annotationValue, String unit) {
        this.annotationName = annotationName;
        this.annotationValue = annotationValue;
        this.unit = unit;
    }

    public String getAnnotationName() {
        return annotationName;
    }

    public String getAnnotationValue() {
        return annotationValue;
    }

    public String getUnit() {
        return unit;
    }
}

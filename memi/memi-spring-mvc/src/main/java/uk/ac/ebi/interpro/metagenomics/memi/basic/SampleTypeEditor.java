package uk.ac.ebi.interpro.metagenomics.memi.basic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;

import java.beans.PropertyEditorSupport;

/**
 * Represents a customized property editor for {@link uk.ac.ebi.interpro.metagenomics.memi.model.EmgStudy.StudyType}.
 * Can used within initBinder methods, which are normally implemented within controller classes.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class SampleTypeEditor extends PropertyEditorSupport {
    private final static Log log = LogFactory.getLog(SampleTypeEditor.class);

    @Override
    public void setAsText(String text) {
        if (text != null && text.trim().length() > 0) {
            text = text.replace(" ", "_");
            text = text.toUpperCase();
        }

        Sample.SampleType type;

        try {
            type = Sample.SampleType.valueOf(text);
            setValue(type);
        } catch (Exception e) {
            log.warn("Could not find any study type value for name: " + text);
        }
    }

    @Override
    public String getAsText() {
        if (getValue() instanceof String) {
            return (String) getValue();
        }
        //make first char capital and the other lower case
        String result = ("" + getValue());
        result = result.substring(0, 1) + result.substring(1, result.length()).toLowerCase();
        return result;
    }
}

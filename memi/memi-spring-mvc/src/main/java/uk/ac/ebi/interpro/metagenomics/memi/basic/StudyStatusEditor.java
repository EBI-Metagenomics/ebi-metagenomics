package uk.ac.ebi.interpro.metagenomics.memi.basic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgStudy;

import java.beans.PropertyEditorSupport;

/**
 * Represents a customized property editor for {@link uk.ac.ebi.interpro.metagenomics.memi.model.EmgStudy.StudyType}.
 * Can used within initBinder methods, which are normally implemented within controller classes.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class StudyStatusEditor extends PropertyEditorSupport {
    private final Log log = LogFactory.getLog(StudyStatusEditor.class);

    @Override
    public void setAsText(String text) {
        if (text != null && text.trim().length() > 0) {
            text = text.replace(" ", "_");
            text = text.toUpperCase();
        }

        EmgStudy.StudyStatus status;

        try {
            status = EmgStudy.StudyStatus.valueOf(text);
            setValue(status);
        }
        catch (Exception e) {
            log.warn("Could not find any study status value for name: " + text);
        }
    }

    @Override
    public String getAsText() {
        if (getValue() instanceof String) {
            return (String) getValue();
        }
        return ("" + getValue());
    }
}

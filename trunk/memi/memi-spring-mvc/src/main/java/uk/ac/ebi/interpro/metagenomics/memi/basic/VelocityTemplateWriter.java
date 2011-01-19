package uk.ac.ebi.interpro.metagenomics.memi.basic;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.io.StringWriter;
import java.util.Map;

/**
 * Represents a method provider/utility class. This class has one method to build up Velocity velocity_templates.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class VelocityTemplateWriter {

    private VelocityTemplateWriter() {
    }

    /**
     * Creates a String representation of the specified Velocity template using the specified spring_model.
     *
     * @param templateLocation Relative path to the Velocity template file.
     * @param model            Possibility to provide a spring_model to the Velocity engine.
     */
    public static String createFileContent(VelocityEngine velocityEngine, String templateLocation, Map<String, Object> model) {
        StringWriter stringWriter = new StringWriter();
        VelocityEngineUtils.mergeTemplate(velocityEngine, templateLocation, model, stringWriter);
        return stringWriter.toString();
    }
}
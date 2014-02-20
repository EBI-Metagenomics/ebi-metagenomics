package uk.ac.ebi.interpro.metagenomics.memi.forms.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * Interface for submission release date custom validation
 * User: matthew
 * Date: 28-Mar-2011
 * Time: 11:12:55
 */
@Constraint(validatedBy = ReleaseDateValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ReleaseDate {
    String message() default "{form.submission.releaseDate.tooFarInFuture}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

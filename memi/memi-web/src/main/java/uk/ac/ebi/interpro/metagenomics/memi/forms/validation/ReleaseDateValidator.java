package uk.ac.ebi.interpro.metagenomics.memi.forms.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.Date;

/**
 * Check the project release date is not too far in the future
 * User: matthew
 * Date: 28-Mar-2011
 * Time: 11:31:36
 */
public class ReleaseDateValidator implements ConstraintValidator<ReleaseDate, Date> {

    public void initialize(ReleaseDate constraintAnnotation) {
        //nothing to do
    }

    /**
     * True if date is less than or equal to 2 years in the future, otherwise false
     * @param releaseDate Submission release date to validate
     * @param constraintContext Constraint context
     * @return True if valid, otherwise false
     */
    public boolean isValid(Date releaseDate, ConstraintValidatorContext constraintContext) {

        long now = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now);
        calendar.add(Calendar.YEAR, 2);
        long limit = calendar.getTimeInMillis();

        long releaseTimeInMillis = releaseDate.getTime();
        if (releaseTimeInMillis > limit) {
            return false;
        }

        return true;
    }

}

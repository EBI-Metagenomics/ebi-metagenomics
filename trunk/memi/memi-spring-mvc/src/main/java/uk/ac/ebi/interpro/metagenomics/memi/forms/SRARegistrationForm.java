package uk.ac.ebi.interpro.metagenomics.memi.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import uk.ac.ebi.interpro.metagenomics.memi.forms.validation.ReleaseDate;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Future;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Represents a form for SRA submission account registration.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class SRARegistrationForm {
    public final static String MODEL_ATTR_NAME = "sraRegistrationForm";

    /**
     * Submission title
     */
    @NotEmpty(message = "{form.submission.subTitle.notEmpty}")
    @Length(min = 3, max = 100, message = "{form.submission.subTitle.size}")
    private String subTitle;

    /**
     * Hold project private until date
     */
    @Temporal(TemporalType.DATE)
    @Future(message = "{form.submission.releaseDate.future}")
    @ReleaseDate(message = "{form.submission.releaseDate.tooFarInFuture}")
    private Date releaseDate;

    /**
     * Data description
     */
    //SUMMARY: <Please provide a summary of the data you will be submitting including platform, file format, read type (single/paired end), barcoded, demultiplexed, file numbers, total volume of data, release date etc. >
    @NotEmpty(message = "{form.submission.dataDesc.notEmpty}")
    @Length(min = 3, message = "{form.submission.dataDesc.size}")
    private String dataDesc;

    //LABORATORY NAME  : <Please provide the name of your laboratory/department within the above institute>
    @NotEmpty(message = "{form.sra.registration.department.notEmpty}")
    @Length(min = 3, max = 100, message = "{form.sra.registration.department.size}")
    private String department;

    //CENTRE DETAILS : <Please provide your Institute's full name and Country>
    //CENTRE ACRONYM : <Please provide the preferred acronym of your institute. This is used in the XMLs as a shorthand form of the centre details. We now provide a unique acronym per submission account so if your institute's acronym has already been used in another account, we will provide a similar one. Note that this acronym is only used as a controlled vocabulary in order to display the full centre details in the ENA browser.>
    @NotEmpty(message = "{form.sra.registration.institute.notEmpty}")
    @Length(min = 3, max = 100, message = "{form.sra.registration.institute.size}")
    private String institute;

    @NotEmpty(message = "{form.sra.registration.postalAddress.notEmpty}")
    @Length(min = 5, message = "{form.sra.registration.postalAddress.size}")
    private String postalAddress;

    @NotEmpty(message = "{form.sra.registration.postalCode.notEmpty}")
    @Length(min = 3, message = "{form.sra.registration.postalCode.size}")
    private String postalCode;

    @NotEmpty(message = "{form.sra.registration.country.notEmpty}")
    private String country;

    //EMAIL ADDRESS: <Please provide the email address(es) for the submission account owner(s). These are people who can amend account details and submit data to SRA>
    @NotEmpty(message = "{form.sra.registration.email.notEmpty}")
    @Email(message = "{form.sra.registration.email.regex}")
    private String email;

    @NotEmpty(message = "{form.sra.registration.firstName.notEmpty}")
    @Length(min = 3, max = 50, message = "{form.sra.registration.firstName.size}")
    private String firstName;

    //YOUR NAME : <Please provide your name>
    @NotEmpty(message = "{form.sra.registration.lastName.notEmpty}")
    @Length(min = 3, max = 50, message = "{form.sra.registration.lastName.size}")
    private String lastName;

    //DATA: Please confirm that the data submitted through this account is NOT sensitive, restricted-access or human-identifiable.
    private boolean isNotSensitiveData;

    private String leaveIt; // Secret HoneyPot field to detect robots - should be left blank by humans!


    public SRARegistrationForm() {
        long time = Calendar.getInstance().getTimeInMillis();
        //add time to 1 year in advance
        time = time + 1000L * 60L * 60L * 24L * 365L * 2L;
        this.releaseDate = new Date(time);
//        this.lastName="Scheremetjew";
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }

    public String getReleaseDate() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(releaseDate);
    }

    public void setReleaseDate(String releaseDate) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            this.releaseDate = df.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @AssertTrue(message = "{form.sra.registration.sensitiveData}")
    public boolean isNotSensitiveData() {
        return isNotSensitiveData;
    }

    public void setNotSensitiveData(boolean notSensitiveData) {
        isNotSensitiveData = notSensitiveData;
    }

    public String getLeaveIt() {
        return leaveIt;
    }

    public void setLeaveIt(String leaveIt) {
        this.leaveIt = leaveIt;
    }
}
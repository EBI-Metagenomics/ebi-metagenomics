package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.dao.apro.CountryDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Country;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.SRARegistrationModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.util.Collection;
import java.util.List;

/**
 * Model builder class for SRARegistrationModel. See {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder} for more information on how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class SRARegistrationViewModelBuilder extends AbstractViewModelBuilder<SRARegistrationModel> {

    private final static Log log = LogFactory.getLog(SRARegistrationViewModelBuilder.class);

    private String pageTitle;

    private List<Breadcrumb> breadcrumbs;

    private MemiPropertyContainer propertyContainer;

    private CountryDAO countryDAO;

    private Submitter submitter;


    public SRARegistrationViewModelBuilder(SessionManager sessionMgr, String pageTitle, List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer,
                                           final CountryDAO countryDAO, final Submitter submitter) {
        super(sessionMgr);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
        this.countryDAO = countryDAO;
        this.submitter = submitter;
    }

    @Override
    public SRARegistrationModel getModel() {
        log.info("Building instance of " + SRARegistrationModel.class + "...");
        Collection<Country> countries = countryDAO.getAllCountries();
//        preFillRegistrationForm(registrationForm);
        return new SRARegistrationModel(submitter, pageTitle, breadcrumbs, propertyContainer,
                countries);
    }

//    private void preFillRegistrationForm(SRARegistrationForm registrationForm) {
//        if (submitter != null) {
//            log.info("Pre fill the SRA registration form with submitter details.");
//            String email = submitter.getEmailAddress();
//            //trim email address
//            if (email != null)
//                email.trim();
//            registrationForm.setEmail(email);
//            //
//            String firstName = submitter.getFirstName();
//            //trim email address
//            if (firstName != null) {
//                firstName.trim();
//            }
//            //
//            registrationForm.setFirstName(firstName);
//            String lastName = submitter.getFirstName();
//            //trim last name
//            if (lastName != null) {
//                lastName.trim();
//            }
//            registrationForm.setLastName(lastName);
//            //
//            registrationForm.setDepartment(submitter.getAddress());
//            //
//            registrationForm.setCountry(submitter.getCountry());
//        } else {
//            log.info("No submitter attached to the session. Therefore no submitter details to pre fill the registration form.");
//        }
//    }
}
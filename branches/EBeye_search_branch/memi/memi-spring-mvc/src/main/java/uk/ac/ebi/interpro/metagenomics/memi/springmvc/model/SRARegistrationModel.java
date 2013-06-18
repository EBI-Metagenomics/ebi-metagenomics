package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.forms.SRARegistrationForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Country;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;

import java.util.Collection;
import java.util.List;

/**
 * Represents the model for the SRA registration form.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class SRARegistrationModel extends ViewModel {


    private Collection<Country> countries;

    public SRARegistrationModel(Submitter submitter, String pageTitle, List<Breadcrumb> breadcrumbs,
                                MemiPropertyContainer propertyContainer, Collection<Country> countries) {
        super(submitter, pageTitle, breadcrumbs, propertyContainer);
        this.countries = countries;
    }

    public Collection<Country> getCountries() {
        return countries;
    }

    public void setCountries(Collection<Country> countries) {
        this.countries = countries;
    }
}
package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.Collection;
import java.util.List;

/**
 * Represents the model for the initial compare page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class CompareViewModel extends ViewModel {

    private List<Study> filteredStudies;

    public CompareViewModel(Submitter submitter, String pageTitle, List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer, List<Study> filteredStudies) {
        super(submitter, pageTitle, breadcrumbs, propertyContainer);
        this.filteredStudies = filteredStudies;
    }

    public List<Study> getFilteredStudies() {
        return filteredStudies;
    }

    public void setFilteredStudies(List<Study> filteredStudies) {
        this.filteredStudies = filteredStudies;
    }
}
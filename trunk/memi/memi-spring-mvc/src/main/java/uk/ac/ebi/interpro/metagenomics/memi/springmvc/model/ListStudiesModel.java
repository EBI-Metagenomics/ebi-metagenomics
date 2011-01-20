package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.forms.FilterForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgStudy;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;

import java.util.List;

/**
 * Represents the model for the list studies page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class ListStudiesModel extends MGModel {

    private FilterForm filterForm;

    private List<EmgStudy> studies;

    public ListStudiesModel(Submitter submitter, List<EmgStudy> studies) {
        super(submitter);
        this.filterForm = new FilterForm();
        this.studies = studies;
    }

    public FilterForm getFilterForm() {
        return filterForm;
    }

    public void setFilterForm(FilterForm filterForm) {
        this.filterForm = filterForm;
    }

    public List<EmgStudy> getStudies() {
        return studies;
    }

    public void setStudies(List<EmgStudy> studies) {
        this.studies = studies;
    }
}

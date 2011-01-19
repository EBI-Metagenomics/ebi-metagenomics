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
public class ListStudiesPageModel extends MGModel {

    private FilterForm filterForm;

    public ListStudiesPageModel(Submitter submitter, List<EmgStudy> studies) {
        super(submitter, studies);
        this.filterForm = new FilterForm();
    }

    public FilterForm getFilterForm() {
        return filterForm;
    }

    public void setFilterForm(FilterForm filterForm) {
        this.filterForm = filterForm;
    }
}

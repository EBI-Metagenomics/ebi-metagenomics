package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects.CompareToolStudyVO;

import java.util.List;

/**
 * Represents the model for the initial compare page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class CompareViewModel extends ViewModel {

    private List<CompareToolStudyVO> filteredStudies;

    public CompareViewModel(Submitter submitter, EBISearchForm ebiSearchForm, String pageTitle, List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer, List<CompareToolStudyVO> filteredStudies) {
        super(submitter, ebiSearchForm, pageTitle, breadcrumbs, propertyContainer);
        this.filteredStudies = filteredStudies;
    }

    public List<CompareToolStudyVO> getFilteredStudies() {
        return filteredStudies;
    }

    public void setFilteredStudies(List<CompareToolStudyVO> filteredStudies) {
        this.filteredStudies = filteredStudies;
    }
}
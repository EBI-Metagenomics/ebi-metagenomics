package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results;

import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: maxim
 * Date: 3/18/15
 * Time: 12:46 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractResultViewModel extends ViewModel {

    public static final List<String> colorCodeList = new ArrayList<String>(10);

    public AbstractResultViewModel(Submitter submitter, EBISearchForm ebiSearchForm, String pageTitle, List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer) {
        super(submitter, ebiSearchForm, pageTitle, breadcrumbs, propertyContainer);
    }

    static {
        //init color code list
        colorCodeList.add("058dc7");
        colorCodeList.add("50b432");
        colorCodeList.add("ed561b");
        colorCodeList.add("fbe300");
        colorCodeList.add("24cbe5");
        colorCodeList.add("c49ecc");
        colorCodeList.add("ff9655");
        colorCodeList.add("708090");
        colorCodeList.add("6af9c4");
        colorCodeList.add("dabe88");
    }

    public List<String> getColorCodeList() {
        return colorCodeList;
    }
}

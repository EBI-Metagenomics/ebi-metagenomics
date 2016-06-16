package uk.ac.ebi.interpro.metagenomics.memi.forms;

import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch.EBISearchResults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maq on 18/03/2016.
 */
public class EBISearchForm implements Serializable {

    public final static String MODEL_ATTR_NAME = "ebiSearchForm";

    String searchText;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}

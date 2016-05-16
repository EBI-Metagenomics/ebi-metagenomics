package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maq on 22/03/2016.
 */
public class EBISearchFacet {
    String identifier;

    String label;

    List<EBISearchFacetValue> values = new ArrayList<EBISearchFacetValue>();

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<EBISearchFacetValue> getValues() {
        return values;
    }

    public void setValues(List<EBISearchFacetValue> values) {
        this.values = values;
    }
}

package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch;

/**
 * Created by maq on 22/03/2016.
 */
public class EBISearchFacetValue {

    public static final String FACET_SEPARATOR = "____";

    int count;

    String label;

    String value;

    String type;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabelAndCount() {
        return label + " (" + count + ")";
    }

    public String getFacetAndValue() {
        return type + FACET_SEPARATOR + value;
    }
}

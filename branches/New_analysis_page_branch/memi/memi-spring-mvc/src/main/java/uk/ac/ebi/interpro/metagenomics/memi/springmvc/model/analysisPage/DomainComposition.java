package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple model class, which is used to render the analysis page. The domain map contains the objects to render the domain composition pie and bar chart.
 * <p/>
 * Domain map: Superkingdom -> total number of unique OTUs (e.g. Bacteria -> 24, Archaea -> 2, Unassigned -> 50)
 *
 * @author Maxim Scheremetjew
 */
public class DomainComposition {
    /**
     * Domain map: Superkingdom -> total number of unique OTUs (e.g. Bacteria -> 24, Archaea -> 2, Unassigned -> 50)
     */
    private Map<String, Integer> domainMap;

    /* The value is used to set the colors option of a Google chart (E.g. 'colors':['#5f8694','#5f8694'] */
    private String colorCode;

    /* Maps superkingdoms to colors     */
    private final Map<String, String> colorCodeMap;

    public DomainComposition(final Map<String, Integer> domainMap) {
        this.domainMap = domainMap;
        //
        this.colorCodeMap = new HashMap<String, String>(3);
        this.colorCodeMap.put("bacteria", "'#5f8694'");
        this.colorCodeMap.put("archaea", "'#91d450'");
        this.colorCodeMap.put("unassigned", "'#535353'");
        //
        setColorCode();
    }

    public Map<String, Integer> getDomainMap() {
        return domainMap;
    }

    public String getColorCode() {
        return colorCode;
    }

    private void setColorCode() {
        StringBuilder result = new StringBuilder();
        for (String key : domainMap.keySet()) {
            if (result.length() > 0)
                result.append(",");
            if (key.equalsIgnoreCase("bacteria")) {
                result.append(colorCodeMap.get("bacteria"));
            } else if (key.equalsIgnoreCase("archaea")) {
                result.append(colorCodeMap.get("archaea"));
            } else {
                result.append(colorCodeMap.get("unassigned"));
            }

        }
        this.colorCode = result.toString();
    }
}
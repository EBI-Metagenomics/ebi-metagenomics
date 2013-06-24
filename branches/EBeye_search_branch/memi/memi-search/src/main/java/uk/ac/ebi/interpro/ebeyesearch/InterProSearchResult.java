package uk.ac.ebi.interpro.ebeyesearch;

/**
 * TODO
 *
 * @author Matthew Fraser, EMBL-EBI, InterPro
 * @version $Id: InterProSearchResult.java,v 1.1 2012/09/06 14:31:08 matthew Exp $
 * @since 1.0-SNAPSHOT
 */
public class InterProSearchResult {
    private final String id;
    private final String name;
    private final String type;
    private final String description;

    public InterProSearchResult(String id, String name, String type, String description) {
        this.id          = id;
        this.name        = name;
        this.type        = type;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

}

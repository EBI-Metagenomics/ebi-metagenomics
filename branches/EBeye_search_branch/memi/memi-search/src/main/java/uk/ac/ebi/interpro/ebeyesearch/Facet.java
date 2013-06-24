package uk.ac.ebi.interpro.ebeyesearch;

/**
 * A search facet.
 *
 * @author Matthew Fraser, EMBL-EBI, InterPro
 * @version $Id: Facet.java,v 1.1 2012/09/06 14:31:08 matthew Exp $
 * @since 1.0-SNAPSHOT
 */
public class Facet {
    private final String prefix;
    private final String id;
    private final String label;
    private final int     count;
    private final boolean isSelected;

    public Facet(String prefix, String id, String label, int count, boolean isSelected) {
        this.prefix     = prefix;
        this.id         = convertId(id);
        this.label      = convertLabel(label);
        this.count      = count;
        this.isSelected = isSelected;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getCount() {
        return count;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public static String convertId(String id) {
        if (id.equals("post-translational_modifications")) {
            return "ptm";
        }
        return id;
    }

    public static String convertLabel(String label) {
        if (label.equals("Post-translational Modifications")) {
            return "PTM";
        }
        if (label.endsWith("Site")) {
            return label.replace("Site", "site");
        }
        return label;
    }

    public static boolean isSite(Facet facet) {
        String id = facet.getId();
        return id.endsWith("site") || id.equals("ptm");
    }

}

package uk.ac.ebi.interpro.ebeyesearch;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author Matthew Fraser, EMBL-EBI, InterPro
 * @version $Id: RelatedSearchResult.java,v 1.1 2012/09/06 14:31:08 matthew Exp $
 * @since 1.0-SNAPSHOT
 */
public class RelatedSearchResult {
    private final String id;
    private final String name;
    private final int    count;

    // TODO: better as enum
    private static final Map<String, String> NAMES = new HashMap<String, String>();
    static {
        NAMES.put("genomes",                "Genomes");
        NAMES.put("nucleotideSequences",    "DNA"); // Nucleotide Sequences
        NAMES.put("proteinSequences",       "Proteins"); // Protein Sequences
        NAMES.put("macromolecularStructures", "3D Structures"); // Macromolecular Structures
        NAMES.put("smallMolecules",         "Small Molecules");
        NAMES.put("geneExpression",         "Gene Expression");
        NAMES.put("molecularInteractions",  "Molecular Interactions");
        NAMES.put("reactionsPathways",      "Reactions & Pathways");
        NAMES.put("proteinFamilies",        "Protein Families");
        NAMES.put("enzymes",                "Enzymes");
        NAMES.put("literature",             "Literature");
        NAMES.put("ontologies",             "Ontologies");
        NAMES.put("ebiweb",                 "EBI Website");
    }

    public RelatedSearchResult(String id, int count) {
        this.id     = id;
        this.name   = NAMES.containsKey(id) ? NAMES.get(id) : id;
        this.count  = count;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

}

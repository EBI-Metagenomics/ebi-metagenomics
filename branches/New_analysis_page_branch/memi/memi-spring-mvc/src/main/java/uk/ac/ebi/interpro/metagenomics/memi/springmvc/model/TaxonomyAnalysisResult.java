package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import java.util.HashSet;
import java.util.Set;

/**
 * As the name says it represents an object which holds taxonomy analysis results.
 *
 * @author Maxim Scheremetjew
 */
public class TaxonomyAnalysisResult extends AnalysisResult {

    private final Set<TaxonomyData> taxonomyDataSet = new HashSet<TaxonomyData>();

    public void addTaxonomyDataRow(TaxonomyData taxonomyData) {
        if (taxonomyData != null && taxonomyData.getPhylum() != null)
            taxonomyDataSet.add(taxonomyData);
    }

    public Set<TaxonomyData> getTaxonomyDataSet() {
        return taxonomyDataSet;
    }
}

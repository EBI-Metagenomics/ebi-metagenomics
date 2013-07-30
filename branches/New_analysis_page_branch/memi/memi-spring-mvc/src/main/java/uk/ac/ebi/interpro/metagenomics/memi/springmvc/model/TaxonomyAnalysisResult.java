package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import org.apache.commons.collections.map.HashedMap;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadLink;

import java.util.*;

/**
 * As the name says it represents an object which holds taxonomy analysis results.
 *
 * @author Maxim Scheremetjew
 */
public class TaxonomyAnalysisResult extends AnalysisResult {

    private List<TaxonomyData> taxonomyDataSet;

    private Map<String, Integer> domainMap;

    public List<TaxonomyData> getTaxonomyDataSet() {
        return taxonomyDataSet;
    }

    public Map<String, Integer> getDomainMap() {
        return domainMap;
    }

    public void setTaxonomyDataSet(List<TaxonomyData> taxonomyDataSet) {
        this.taxonomyDataSet = taxonomyDataSet;
        calculateDomainComposition();
    }

    /**
     * Super kingdom is the same as domain.
     * //TODO: Write JUnit test
     */
    private void calculateDomainComposition() {
        this.domainMap = new HashedMap();
        for (TaxonomyData taxonomyData : taxonomyDataSet) {
            String key = taxonomyData.getSuperKingdom();
            int value = taxonomyData.getNumberOfHits().intValue();
            if (domainMap.containsKey(key)) {
                value = value + domainMap.get(key).intValue();
            }
            domainMap.put(key, value);
        }
    }

    /**
     * Compares taxonomy data by 'numberOfHits' property. Descending order.
     */
    public static Comparator<TaxonomyData> TaxonomyDataComparator
            = new Comparator<TaxonomyData>() {
        public int compare(TaxonomyData o1, TaxonomyData o2) {
            return o2.getNumberOfHits() - o1.getNumberOfHits();
        }
    };
}

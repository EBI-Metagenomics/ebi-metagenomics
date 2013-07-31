package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DomainComposition;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents a simple model object which is used to render the sample analysis page, more specifically the taxonomic analysis tab.
 * The taxonomy data set is used to render the phylum table and charts.
 *
 *
 * @author Maxim Scheremetjew
 */
public class TaxonomyAnalysisResult extends AnalysisResult {

    private List<TaxonomyData> taxonomyDataSet;

    private DomainComposition domainComposition;

    private int uniqueUTUsTotalCount;

    public List<TaxonomyData> getTaxonomyDataSet() {
        return taxonomyDataSet;
    }

    public DomainComposition getDomainComposition() {
        return domainComposition;
    }

    public int getUniqueUTUsTotalCount() {
        return uniqueUTUsTotalCount;
    }

    private void setUniqueUTUsTotalCount(int uniqueUTUsTotalCount) {
        this.uniqueUTUsTotalCount = uniqueUTUsTotalCount;
    }

    private void setDomainComposition(DomainComposition domainComposition) {
        this.domainComposition = domainComposition;
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
        Map<String, Integer> domainMap = new TreeMap();
        int uniqueUTUsCounter = 0;
        for (TaxonomyData taxonomyData : taxonomyDataSet) {
            String key = taxonomyData.getSuperKingdom();
            int value = taxonomyData.getNumberOfHits().intValue();
            uniqueUTUsCounter += value;
            if (domainMap.containsKey(key)) {
                value = value + domainMap.get(key).intValue();
            }
            domainMap.put(key, value);
        }
        setDomainComposition(new DomainComposition(domainMap));
        setUniqueUTUsTotalCount(uniqueUTUsCounter);
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

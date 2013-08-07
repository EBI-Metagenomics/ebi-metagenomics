package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DomainComposition;

import java.math.BigDecimal;
import java.util.*;

/**
 * Represents a simple model object which is used to render the sample analysis page, more specifically the taxonomic analysis tab.
 * The taxonomy data set is used to render the phylum table and charts.
 *
 * @author Maxim Scheremetjew
 */
public class TaxonomyAnalysisResult extends AnalysisResult {

    private List<TaxonomyData> taxonomyDataSet;

    private DomainComposition domainComposition;

    private int uniqueUTUsTotalCount;

    private BigDecimal sliceVisibilityThreshold;

    public TaxonomyAnalysisResult() {
        this(new ArrayList<TaxonomyData>());
    }

    public TaxonomyAnalysisResult(List<TaxonomyData> taxonomyDataSet) {
        this.taxonomyDataSet = taxonomyDataSet;
        init();
    }

    public String getColorCodeForStackChart() {
        StringBuilder result = new StringBuilder("'#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#b2deff'");
        if (taxonomyDataSet.size() > 10) {
            for (int i = 0; i < taxonomyDataSet.size() - 10; i++) {
                result.append(",'#ccc'");
            }
        }
        return result.toString();
    }


    public List<TaxonomyData> getTaxonomyDataSet() {
        return taxonomyDataSet;
    }

    public DomainComposition getDomainComposition() {
        return domainComposition;
    }

    public int getUniqueUTUsTotalCount() {
        return uniqueUTUsTotalCount;
    }

    public BigDecimal getSliceVisibilityThreshold() {
        return sliceVisibilityThreshold;
    }

    private void setUniqueUTUsTotalCount(int uniqueUTUsTotalCount) {
        this.uniqueUTUsTotalCount = uniqueUTUsTotalCount;
    }

    private void setDomainComposition(DomainComposition domainComposition) {
        this.domainComposition = domainComposition;
    }

    private void setSliceVisibilityThreshold(BigDecimal sliceVisibilityThreshold) {
        this.sliceVisibilityThreshold = sliceVisibilityThreshold;
    }

    public String getColorCodeForPieChart() {
        StringBuilder sb = new StringBuilder("'#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#b2deff'");
        if (taxonomyDataSet.size() > 10) {
            for (int i = 0; i < taxonomyDataSet.size() - 10; i++) {
                sb.append(",'#ccc'");
            }
        }
        return sb.toString();
    }

    /**
     * Sets DomainComposition, UniqueUTUsTotalCount, color codes for taxonomy data as well as the sliceVisibilityThreshold.
     * Super kingdom is the equivalent of domain.
     * //TODO: Write JUnit test
     */
    private void init() {
        Enumeration<String> colors = getColors();
        //
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
            //set the color code at the same time
            if (colors.hasMoreElements()) {
                taxonomyData.setColorCode(colors.nextElement());
            } else {
                taxonomyData.setColorCode("ccc");
            }
        }
        setDomainComposition(new DomainComposition(domainMap));
        setUniqueUTUsTotalCount(uniqueUTUsCounter);
        if (uniqueUTUsCounter > 0) {
            BigDecimal sliceVisibilityThreshold = new BigDecimal(0f);
            if (taxonomyDataSet.size() > 10) {
                int numberOfHits = taxonomyDataSet.get(9).getNumberOfHits();
                sliceVisibilityThreshold = new BigDecimal((double) numberOfHits / (double) getUniqueUTUsTotalCount());
            }
            setSliceVisibilityThreshold(sliceVisibilityThreshold);
        }
    }

    private Enumeration<String> getColors() {
        Vector<String> colors = new Vector<String>(10);
        colors.add("058dc7");
        colors.add("50b432");
        colors.add("ed561b");
        colors.add("edef00");
        colors.add("24cbe5");
        colors.add("64e572");
        colors.add("ff9655");
        colors.add("fff263");
        colors.add("6af9c4");
        colors.add("b2deff");
        return colors.elements();
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

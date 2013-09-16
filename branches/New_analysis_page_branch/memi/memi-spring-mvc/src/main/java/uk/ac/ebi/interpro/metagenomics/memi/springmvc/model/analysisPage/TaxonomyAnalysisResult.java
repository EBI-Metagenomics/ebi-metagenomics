package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.AnalysisResult;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.TaxonomyData;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DomainComposition;

import java.util.*;

/**
 * Represents a simple model object which is used to render the sample analysis page, more specifically the taxonomic analysis tab.
 * The taxonomy data set is used to render the phylum table and phylum charts.
 *
 * @author Maxim Scheremetjew
 */
public class TaxonomyAnalysisResult extends AnalysisResult {

    private List<TaxonomyData> taxonomyDataSet;

    private DomainComposition domainComposition;

    private PhylumChartOptions phylumChartOptions;

    /**
     * Empty constructor to avoid NPE.
     */
    public TaxonomyAnalysisResult() {
        this(new ArrayList<TaxonomyData>());
        this.domainComposition = new DomainComposition(new HashMap<String, Integer>(0));
        this.phylumChartOptions = new PhylumChartOptions();
    }

    public TaxonomyAnalysisResult(List<TaxonomyData> taxonomyDataSet) {
        this.taxonomyDataSet = taxonomyDataSet;
        if (taxonomyDataSet.size() > 0)
            init();
    }


    public List<TaxonomyData> getTaxonomyDataSet() {
        return taxonomyDataSet;
    }

    public DomainComposition getDomainComposition() {
        return domainComposition;
    }

    private void setDomainComposition(DomainComposition domainComposition) {
        this.domainComposition = domainComposition;
    }

    /**
     * Used in JSP 'taxonomy analysis tab'.
     * <p/>
     * Used to set the colors option for the stack chart
     */
    public String getColorCodeForStackChart() {
        StringBuilder result = new StringBuilder("'#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#dabe88'");
        if (taxonomyDataSet.size() > 10) {
            for (int i = 0; i < taxonomyDataSet.size() - 10; i++) {
                result.append(",'#ccc'");
            }
        }
        return result.toString();
    }

    /**
     * Used in JSP 'taxonomy analysis tab'.
     * <p/>
     * Used to set the colors option for the pie chart.
     */
    public String getColorCodeForPieChart() {
        StringBuilder sb = new StringBuilder("'#058dc7', '#50b432', '#ed561b', '#edef00', '#24cbe5', '#64e572', '#ff9655', '#fff263', '#6af9c4', '#dabe88'");
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
        List<String> colors = getColors();
        //maps domains (Bacteria, Archaea, unassigned)
        Map<String, Integer> domainCompositionMap = new TreeMap();
        //Counts all unique UTU values
        int uniqueUTUsCounter = 0;
        //The next 3 variables are used for the colour code algorithm
        int previousNumberOfHits = 0;
        int nextNumberOfHits = 0;
        boolean colourCodeTrigger = false;

        for (int index = taxonomyDataSet.size() - 1; index >= 0; index--) {
            TaxonomyData taxonomyData = taxonomyDataSet.get(index);
            String superKingdom = taxonomyData.getSuperKingdom();
            int currentNumberOfHits = taxonomyData.getNumberOfHits().intValue();
            uniqueUTUsCounter += currentNumberOfHits;
            int domainValue = currentNumberOfHits;
            if (domainCompositionMap.containsKey(superKingdom)) {
                domainValue = currentNumberOfHits + domainCompositionMap.get(superKingdom).intValue();
            }
            domainCompositionMap.put(superKingdom, domainValue);
            //set the colour code at the same time

            if (index >= 1) {
                nextNumberOfHits = taxonomyDataSet.get(index - 1).getNumberOfHits().intValue();
            }
            colourCodeTrigger = setColourCodeForTaxonomyDataObject(taxonomyData, index, currentNumberOfHits, previousNumberOfHits, nextNumberOfHits, colourCodeTrigger, colors);
            previousNumberOfHits = currentNumberOfHits;
        }
        setDomainComposition(new DomainComposition(domainCompositionMap));
        setPhylumChartOptions(new PhylumChartOptions(uniqueUTUsCounter, taxonomyDataSet.size() - 1));
    }

    private boolean setColourCodeForTaxonomyDataObject(final TaxonomyData taxonomyData, final int index,
                                                       final int currentNumberOfHits, final int previousNumberOfHits, final int nextNumberOfHits,
                                                       final boolean colourCodeTrigger, final List<String> colors) {
        if (index > 9) {
            taxonomyData.setColorCode("ccc");
            return false;
        } else //i<=9
        {
            if (colourCodeTrigger) {
                taxonomyData.setColorCode(colors.get(index));
            }// 10th == 11th && 10th != 9th
            //Nr    Phylum 	Domain 	Unique OTUs
            //9 Euryarchaeota   Archaea 2
            //10    SAR406	Bacteria	1
            //11    Chloroflexi	Bacteria	1
            else if (currentNumberOfHits == previousNumberOfHits && currentNumberOfHits != nextNumberOfHits) {
                taxonomyData.setColorCode("ccc");
            }// 10th == 11th && 10th == 9th
            //Nr    Phylum 	Domain 	Unique OTUs
            //9 Euryarchaeota   Archaea 1
            //10    SAR406	Bacteria	1
            //11    Chloroflexi	Bacteria	1
            else if (currentNumberOfHits == previousNumberOfHits && currentNumberOfHits == nextNumberOfHits) {
                taxonomyData.setColorCode("ccc");
                return false;
            } else {
                taxonomyData.setColorCode(colors.get(index));
            }
        }
        return true;
    }

    private List<String> getColors() {
        List<String> colors = new ArrayList<String>(10);
        colors.add("058dc7");
        colors.add("50b432");
        colors.add("ed561b");
        colors.add("edef00");
        colors.add("24cbe5");
        colors.add("64e572");
        colors.add("ff9655");
        colors.add("fff263");
        colors.add("6af9c4");
        colors.add("dabe88");
        return colors;
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

    private class PhylumChartOptions {
        private float sliceVisibilityThresholdNumerator;

        /* Which is the same as uniqueUTUsTotalCount */
        private int sliceVisibilityThresholdDenominator;

        private PhylumChartOptions() {
        }

        private PhylumChartOptions(int sliceVisibilityThresholdDenominator, int indexOfLastTaxonomyDataSetEntry) {
            this.sliceVisibilityThresholdDenominator = sliceVisibilityThresholdDenominator;
            init(indexOfLastTaxonomyDataSetEntry);
        }

        private void init(int indexOfLastTaxonomyDataSetEntry) {
            if (taxonomyDataSet.size() > 10) {
                TaxonomyData taxonomyDataEntry10 = taxonomyDataSet.get(9);
                TaxonomyData taxonomyDataEntry11 = taxonomyDataSet.get(10);
                if (!taxonomyDataEntry10.getNumberOfHits().equals(taxonomyDataEntry11.getNumberOfHits())) {
                    this.sliceVisibilityThresholdNumerator = taxonomyDataEntry10.getNumberOfHits() - 0.5f;
                } else {
                    //If the number of hits is the same
                    this.sliceVisibilityThresholdNumerator = taxonomyDataEntry10.getNumberOfHits() + 0.5f;
                }
            } else {
                //If the size of taxonomy data set is less then 10
                this.sliceVisibilityThresholdNumerator = taxonomyDataSet.get(indexOfLastTaxonomyDataSetEntry).getNumberOfHits() - 0.5f;
            }
        }

        public float getSliceVisibilityThresholdNumerator() {
            return sliceVisibilityThresholdNumerator;
        }

        public int getSliceVisibilityThresholdDenominator() {
            return sliceVisibilityThresholdDenominator;
        }

    }

    public float getSliceVisibilityThresholdNumerator() {
        return getPhylumChartOptions().getSliceVisibilityThresholdNumerator();
    }

    public int getSliceVisibilityThresholdDenominator() {
        return getPhylumChartOptions().getSliceVisibilityThresholdDenominator();
    }

    public PhylumChartOptions getPhylumChartOptions() {
        return phylumChartOptions;
    }

    public void setPhylumChartOptions(PhylumChartOptions phylumChartOptions) {
        this.phylumChartOptions = phylumChartOptions;
    }
}
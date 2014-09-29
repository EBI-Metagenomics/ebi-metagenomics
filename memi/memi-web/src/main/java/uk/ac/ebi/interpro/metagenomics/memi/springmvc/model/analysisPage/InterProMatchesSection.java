package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Simple model class which is part of {@link FunctionalAnalysisResult}. Holds data to render the InterPro matches chart.
 *
 * @author Maxim Scheremetjew
 */
public class InterProMatchesSection {

    private List<InterProEntry> interProEntryList;

    private int totalReadsCount;

    private float sliceVisibilityThresholdValue;

    private List<String> colorCodeList;

    public InterProMatchesSection(List<InterProEntry> interProEntryList, int totalReadsCount) {
        this.interProEntryList = interProEntryList;
        this.totalReadsCount = totalReadsCount;
        init();
    }

    private void init() {
        //Set sliceVisibilityThresholdValue
        initSliceVisibilityThresholdValue();
        //Set color code list
        initColorCodeList();
    }

    public List<InterProEntry> getInterProEntryList() {
        return interProEntryList;
    }

    public int getTotalReadsCount() {
        return totalReadsCount;
    }

    public float getSliceVisibilityThresholdValue() {
        return sliceVisibilityThresholdValue;
    }

    public List<String> getColorCodeList() {
        return colorCodeList;
    }

    private void initSliceVisibilityThresholdValue() {
        int numberOfElements = interProEntryList.size();
        if (numberOfElements > 10) {
            int numberOfEntryHits10thElement = interProEntryList.get(9).getNumOfEntryHits();
            int numberOfEntryHits11thElement = interProEntryList.get(10).getNumOfEntryHits();

            if (numberOfEntryHits10thElement != numberOfEntryHits11thElement) {
                this.sliceVisibilityThresholdValue = (numberOfEntryHits10thElement - 0.5f) / totalReadsCount;
            } else {
                this.sliceVisibilityThresholdValue = (numberOfEntryHits10thElement + 0.5f) / totalReadsCount;
            }
        } else {
            if (numberOfElements > 0) {
                this.sliceVisibilityThresholdValue = (interProEntryList.get(numberOfElements - 1).getNumOfEntryHits() - 0.5f) / totalReadsCount;
            } else {
                this.sliceVisibilityThresholdValue = 0.0f;
            }
        }
    }

    /**
     * Set the color code for the InterPro matches tables (only for the first 10 entries). Setting the color code depends on the number
     * of matches between InterPro entries. For instance, if the number of matches between InterPro entries is different from entry to entry,
     * then just apply the DEFAULT color code. If that isn't the case, then you have to grey out InterPro entries (for instance if the 9th entry has the
     * same number of matches then the 10th entry)
     * <p/>
     * For example:
     * InterPro_ID  Number_of_matches
     * 1.   IPR017690	16
     * 2.   IPR016040	9
     * 3.   IPR013785	9
     * 4.   IPR013781	7
     * 5.   IPR014729	7
     * 6.   IPR013126	5
     * 7.   IPR000398	3
     * 8.   IPR023214	3
     * 9.   IPR013221	3
     * 10.  IPR023451	3
     * <p/>
     * Grey out all entries from 7.-10.,because they have the same number of matches.
     *
     * @return
     */
    private void initColorCodeList() {
        int numberOfElements = interProEntryList.size();
        if (numberOfElements > 10) {
            String[] colorCodeArray = new String[10];

            int previousNumberOfHits = 0;
            int nextNumberOfHits = 0;
            boolean colorTrigger = false;
            for (int index = 9; index >= 0; index--) {
                if (colorTrigger) {
                    colorCodeArray[index] = AnalysisResult.colorCodeList.get(index);
                } else {
                    int currentNumberOfHits = interProEntryList.get(index).getNumOfEntryHits();
                    if (index - 1 >= 0) {
                        previousNumberOfHits = interProEntryList.get(index - 1).getNumOfEntryHits();
                    }
                    if (index + 1 <= interProEntryList.size()) {
                        nextNumberOfHits = interProEntryList.get(index + 1).getNumOfEntryHits();
                    }
                    if (currentNumberOfHits == nextNumberOfHits || currentNumberOfHits == previousNumberOfHits) {
                        colorCodeArray[index] = "b9b9b9";
                    } else {
                        colorCodeArray[index] = AnalysisResult.colorCodeList.get(index);
                        colorTrigger = true;
                    }
                }
            }
            this.colorCodeList = Arrays.asList(colorCodeArray);
        } else {
            this.colorCodeList = new ArrayList<String>(AnalysisResult.colorCodeList);
        }
    }
}
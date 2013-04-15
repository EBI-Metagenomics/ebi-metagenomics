package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

/**
 * Represents an object which handles pagination in general.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class ViewPagination {
    private int startPosition;

    private int nextStartPos;

    boolean existNextStartPos;

    private int previousStartPos;

    boolean existPreviousStartPos;

    public int pageSize;

    private final int start = 0;

    private long totalItems;

    private String displayedItemRange;

    private int lastLinkPosition;


    public ViewPagination(int startPosition, int pageSize) {
        this(startPosition, 0, pageSize);
    }

    public ViewPagination(int startPosition, long totalItems, int pageSize) {
        this.startPosition = startPosition;
        this.totalItems = totalItems;
        this.pageSize = pageSize;
        setPreviousStartPos();
        setNextStartPosAndExistNextStartPos();
        setDisplayedItemRange();
        setLastPosition();
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getNextStartPos() {
        return nextStartPos;
    }

    /**
     * Example:
     * <p/>
     * startPosition=10
     * pageSize=10
     * totalItems=20
     * Then nextStartPos=10+10=20
     * If 20=>20
     * Then nextStartPos=10
     */
    public void setNextStartPosAndExistNextStartPos() {
        this.nextStartPos = startPosition + pageSize;
        if (nextStartPos >= totalItems) {
            this.nextStartPos = startPosition;
            this.existNextStartPos = false;
        } else {
            this.existNextStartPos = true;
        }
    }

    public void setPreviousStartPos() {
        this.previousStartPos = startPosition - pageSize;
        if (previousStartPos < start) {
            previousStartPos = start;
            existPreviousStartPos = false;
        } else {
            existPreviousStartPos = true;
        }
    }

    public int getPreviousStartPos() {
        return previousStartPos;
    }

    public int getStart() {
        return start;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public boolean isExistNextStartPos() {
        return existNextStartPos;
    }

    public void setExistNextStartPos(boolean existNextStartPos) {
        this.existNextStartPos = existNextStartPos;
    }

    public boolean isExistPreviousStartPos() {
        return existPreviousStartPos;
    }

    public void setExistPreviousStartPos(boolean existPreviousStartPos) {
        this.existPreviousStartPos = existPreviousStartPos;
    }

    public String getDisplayedItemRange() {
        return displayedItemRange;
    }

    public void setDisplayedItemRange() {
        StringBuilder result = new StringBuilder("" + (startPosition + 1) + " - ");
        if (isExistNextStartPos()) {
            result.append(nextStartPos);
        } else {
            result.append(totalItems);
        }
        this.displayedItemRange = result.toString();
    }

    public int getLastLinkPosition() {
        return lastLinkPosition;
    }

    public void setLastPosition() {
        //There are 2 cases to distinguish
        //Case 1: The modulo of totalItems and pageSize is NULL, e.g. if the sample size is 20. So you will have 2 pages (1-10 and 11-20) and the last link position (startPosition) should be 10
        //Case 2: The modulo of totalItems and pageSize is NOT NULL, e.g. if the sample size is 21. So you will have 3 pages (1-10 and 11-20 and 21-21 of 21) and the last link position (startPosition) should be 20.
        this.lastLinkPosition = (int) (totalItems % pageSize == 0 ? totalItems - pageSize : (totalItems - (totalItems % pageSize)));
    }
}
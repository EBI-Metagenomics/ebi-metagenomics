package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

/**
 * Simple POJO for holding GO match statistics
 *
 * @author Phil Jones
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class MatchStatistic implements Comparable<MatchStatistic> {

    private String accession;

    private String term;

    private int count;

    private int totalMatchCount;

    MatchStatistic(String[] parts, int totalMatchCount) {
        accession = parts[0];
        term = parts[1];
        count = Integer.parseInt(parts[2]);
        this.totalMatchCount = totalMatchCount;
    }

    public String getAccession() {
        return accession;
    }

    public String getTerm() {
        return term;
    }

    public int getCount() {
        return count;
    }

    public float getMatchPercentage() {
        return ((float) count) / ((float) totalMatchCount) * 100.0f;
    }

    /**
     * @param that the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object.
     * @throws ClassCastException if the specified object's type prevents it
     *                            from being compared to this object.
     */
    @Override
    public int compareTo(MatchStatistic that) {
        if (this == that || this.equals(that)) {
            return 0;
        }
        if (this.getMatchPercentage() != that.getMatchPercentage()) {
            return (this.getMatchPercentage() > that.getMatchPercentage()) ? -1 : 1;
        }
        return this.getAccession().compareTo(that.getAccession());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchStatistic statistic = (MatchStatistic) o;

        if (count != statistic.count) return false;
        if (!accession.equals(statistic.accession)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accession.hashCode();
        result = 31 * result + count;
        return result;
    }
}

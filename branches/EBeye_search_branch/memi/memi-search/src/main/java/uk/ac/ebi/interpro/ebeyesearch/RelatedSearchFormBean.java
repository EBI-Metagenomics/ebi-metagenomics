package uk.ac.ebi.interpro.ebeyesearch;

import java.util.List;

/**
 * A form bean class to hold necessary data for displaying the related results only.
 *
 * @author Matthew Fraser, EMBL-EBI, InterPro
 * @version $Id: RelatedSearchFormBean.java,v 1.2 2012/09/11 11:02:25 matthew Exp $
 * @since 1.0-SNAPSHOT
 */
public class RelatedSearchFormBean {
    private final String query;
    private final String queryWithoutFacets;
    private final List<RelatedSearchResult> results;
    private final String errorMessage;

    public RelatedSearchFormBean(String query, String queryWithoutFacets, List<RelatedSearchResult> results,
                                 String errorMessage) {
        this.query              = query;
        this.queryWithoutFacets = queryWithoutFacets;
        this.results = results;
        this.errorMessage       = errorMessage;
    }

    public String getQuery() {
        return query;
    }

    public String getQueryWithoutFacets() {
        return queryWithoutFacets;
    }

    public List<RelatedSearchResult> getResults() {
        return results;
    }

    public boolean getAreRelatedResultsNotEmpty() {
        return getResultsCount() > 0;
    }

    /**
     * Get the number of results (number available for display on this page only)
     * @return The count
     */
    public int getResultsCount() {
        if (results == null) {
            return 0;
        }
        return results.size();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}

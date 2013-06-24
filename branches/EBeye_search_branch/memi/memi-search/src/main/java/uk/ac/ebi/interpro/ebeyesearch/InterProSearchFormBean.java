package uk.ac.ebi.interpro.ebeyesearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A form bean class to hold necessary data for displaying an InterPro search results page.
 * Related results (non-InterPro) are not included, but can be retrieved separately.
 *
 * @author Matthew Fraser, EMBL-EBI, InterPro
 * @version $Id: InterProSearchFormBean.java,v 1.1 2012/09/06 14:31:08 matthew Exp $
 * @since 1.0-SNAPSHOT
 */
public class InterProSearchFormBean {
    private final String query; // The query as entered/read by the user (without escape chars added)
    private final String queryWithoutFacets; // The same as query, but with facets removed (without escape chars added)
    private final int count; // The number of results returned by the query, taking selected facets into account
    private final int countWithoutFacets; // The total number of results returned by the query (so ignoring facets)
    private final int currentPage; // The current page number in the search results (pagination)
    private final List<InterProSearchResult> results; // Data for the search results (for the current page only)
    private final List<LinkInfoBean> paginationLinks; // The pagination links to display for the current page
    private final List<Facet> facets; // The facets available, to allow filtering of search results
    private final List<Facet> siteFacets    = new ArrayList<Facet>();
    private final List<Facet> nonSiteFacets = new ArrayList<Facet>();
    private final boolean areAllSelected;
    private final String errorMessage; // NULL, unless an error was thrown whilst performing the search

    public InterProSearchFormBean(String query, String queryWithoutFacets, int count, int countWithoutFacets, int currentPage,
                                  List<InterProSearchResult> results, List<LinkInfoBean> paginationLinks, List<Facet> facets,
                                  String errorMessage) {
        this.query = query;
        this.queryWithoutFacets = queryWithoutFacets;
        this.count = count;
        this.countWithoutFacets = countWithoutFacets;
        this.currentPage = currentPage;
        this.results = results;
        this.paginationLinks = paginationLinks;
        this.errorMessage = errorMessage;

        // Init facets
        this.facets = facets;
        boolean isFacetSelected = false;
        for (Facet f : facets) {
            if (Facet.isSite(f)) {
                siteFacets.add(f);
            }
            else {
                nonSiteFacets.add(f);
            }
            if (f.getIsSelected()) {
                isFacetSelected = true;
            }
        }
        this.areAllSelected = !isFacetSelected;
        Collections.sort(siteFacets, new Comparator<Facet>() {
            public int compare(Facet f1, Facet f2) {
                return f1.getLabel().compareTo(f2.getLabel());
            }
        }
        );

    }

    public String getQuery() {
        return query;
    }

    public String getQueryWithoutFacets() {
        return queryWithoutFacets;
    }

    /**
     * Get the total number of results for this search (for selected facets only)
     * @return The count
     */
    public int getCount() {
        return count;
    }

    /**
     * Get the total number of results for this search (without taking facets into account)
     * @return The count
     */
    public int getCountWithoutFacets() {
        return countWithoutFacets;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public List<InterProSearchResult> getResults() {
        return results;
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

    public List<LinkInfoBean> getPaginationLinks() {
        return paginationLinks;
    }

    public List<Facet> getFacets() {
        return facets;
    }

    public List<Facet> getSiteFacets() {
        return siteFacets;
    }

    public List<Facet> getNonSiteFacets() {
        return nonSiteFacets;
    }

    public boolean getAreAllSelected() {
        return areAllSelected;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}

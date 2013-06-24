package uk.ac.ebi.interpro.ebeyesearch;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Matthew Fraser
 * @author Antony Quinn
 * @author Phil Jones
 */
public class ResultsPager {

    private static final Logger LOGGER = Logger.getLogger(ResultsPager.class.getName());

    private static final Pattern DOLLAR_START = Pattern.compile("\\$\\{start\\}");

    // Default number of numeric links to include in pagination, e.g. if 10 then we could get:
    // « First ‹ Previous 4 5 6 7 8 9 10 11 12 13 Next › Last »
    private static final int DEFAULT_NUM_NUMERIC_LINKS = 10;

    /**
     * Construct a list of pagination links based on the current page and other parameters.
     * This code is based on that provided by external services.
     *
     * @param urlPattern     Link URL pattern
     * @param resultIndex    First result to retrieve (zero indexed)
     * @param count          Total number of search results
     * @param resultsPerPage The number of results to show per page
     * @return List of link information objects
     */
    public List<LinkInfoBean> getLinks(final String urlPattern,
                                       final int resultIndex,
                                       final int count,
                                       final int resultsPerPage) {
        return getLinks(urlPattern, resultIndex, count, resultsPerPage, DEFAULT_NUM_NUMERIC_LINKS);
    }

    /**
     * Construct a list of pagination links based on the current page and other parameters.
     * This code is based on that provided by external services.
     *
     * @param urlPattern         Link URL pattern
     * @param resultIndex        First result to retrieve (zero indexed)
     * @param count              Total number of search results
     * @param resultsPerPage     The number of results to show per page
     * @param maxNumNumericLinks Maximum number of numeric links to show in the pagination, e.g. if 10 then could get:
     *                           « First ‹ Previous 4 5 6 7 8 9 10 11 12 13 Next › Last »
     * @return List of link information objects
     */
    public List<LinkInfoBean> getLinks(final String urlPattern,
                                       final int resultIndex,
                                       final int count,
                                       final int resultsPerPage,
                                       int maxNumNumericLinks) {

        if (maxNumNumericLinks < 0) {
            LOGGER.warn("Invalid maximum number of numeric pagintation links: " + maxNumNumericLinks +
                    ", using default instead: " + DEFAULT_NUM_NUMERIC_LINKS);
            maxNumNumericLinks = DEFAULT_NUM_NUMERIC_LINKS;
        }

        final int currentPage = getCurrentPageNumber(resultIndex, resultsPerPage);

        int numberOfPages = 0;
        if (resultsPerPage > 0) {
            numberOfPages = count / resultsPerPage;
            if (count % resultsPerPage > 0) {
                numberOfPages++;
            }
        }

        final List<LinkInfoBean> links = new ArrayList<LinkInfoBean>();
        if (numberOfPages <= 1) {
            return links;
        }
        LinkInfoBean bean;
        int startPage = 1;
        int endPage = numberOfPages;
        if (numberOfPages > maxNumNumericLinks) {
            // E.g. if maxNumNumericLinks=10 then startPage = currentPage - 4
            if (maxNumNumericLinks % 2 == 0) {
                // Even number
                startPage = currentPage - Math.round(maxNumNumericLinks / 2) + 1;
            }
            else {
                // Odd number
                startPage = currentPage - Math.round(maxNumNumericLinks / 2);
            }
            if (startPage <= 0) {
                startPage = 1;
            }
            // E.g. if maxNumNumericLinks=10 then endPage = startPage + 9
            endPage = startPage + maxNumNumericLinks - 1;
            if (endPage > numberOfPages) {
                endPage = numberOfPages;
            }
        }
        if (currentPage > 1) {
            bean = new LinkInfoBean();
            bean.setName("First");
            bean.setLink(DOLLAR_START.matcher(urlPattern).replaceAll("0"));
            bean.setDescription("Go to the first page");
            bean.setClassName("paging_first");
            links.add(bean);

            bean = new LinkInfoBean();
            bean.setName("Previous");
            bean.setLink(DOLLAR_START.matcher(urlPattern).replaceAll(Integer.toString(pageNumToStartIndex(currentPage, resultsPerPage) - resultsPerPage)));
            bean.setDescription("Go to the previous page");
            bean.setClassName("paging_previous");
            links.add(bean);
        }
        for (int i = startPage; i <= endPage; i++) {
            bean = new LinkInfoBean();
            bean.setName(Integer.toString(i));
            if (i != currentPage) {
                bean.setLink(DOLLAR_START.matcher(urlPattern).replaceAll(Integer.toString(pageNumToStartIndex(i, resultsPerPage))));
            }
            bean.setDescription("Go to page " + i);
            links.add(bean);
        }
        if (currentPage < numberOfPages) {
            bean = new LinkInfoBean();
            bean.setName("Next");
            bean.setLink(DOLLAR_START.matcher(urlPattern).replaceAll(Integer.toString(pageNumToStartIndex(currentPage, resultsPerPage) + resultsPerPage)));
            bean.setDescription("Go to the next page");
            bean.setClassName("paging_next");
            links.add(bean);

            bean = new LinkInfoBean();
            bean.setName("Last");
            bean.setLink(DOLLAR_START.matcher(urlPattern).replaceAll(Integer.toString(pageNumToStartIndex(numberOfPages, resultsPerPage))));
            bean.setDescription("Go to the last page");
            bean.setClassName("paging_last");
            links.add(bean);
        }
        return links;
    }

    public int getCurrentPageNumber(final int resultIndex, final int resultsPerPage) {
        return (resultIndex / resultsPerPage) + 1;
    }

    /**
     * Use the supplied page number and the number of search results shown per page to calculate the start index of
     * the result required.
     *
     * @param pageNum        Page number
     * @param resultsPerPage Number of results per page
     * @return Equivalent search result index
     */
    private int pageNumToStartIndex(final int pageNum, final int resultsPerPage) {
        return (pageNum - 1) * resultsPerPage;
    }
}

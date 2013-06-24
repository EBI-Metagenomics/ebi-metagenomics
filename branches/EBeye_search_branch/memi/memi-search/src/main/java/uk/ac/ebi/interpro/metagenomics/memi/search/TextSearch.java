package uk.ac.ebi.interpro.metagenomics.memi.search;

import uk.ac.ebi.interpro.ebeyesearch.*;
import uk.ac.ebi.webservices.axis1.EBeyeClient;
import uk.ac.ebi.webservices.axis1.stubs.ebeye.DomainResult;
import uk.ac.ebi.webservices.axis1.stubs.ebeye.FacetValue;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * EBI Search web service client.
 *
 * @author Antony Quinn
 * @version $Id: TextSearch.java,v 1.8 2013/03/04 13:57:49 maxim Exp $
 */
public final class TextSearch {

    // "Domain" in this sense means the name of the index in the EBI search engine
//    private static final String DOMAIN = "interpro";

    // Could configure in Spring
    private final EBeyeClient client;

    // This class appears to be thread safe, so no reason to build one for each instance of TextSearch.
    private static final ResultsPager PAGER = new ResultsPager();

    public TextSearch() {
        client = new EBeyeClient();
    }

    public TextSearch(String endPointUrl) {
        client = new EBeyeClient();
        client.setServiceEndPoint(endPointUrl); // E.g. "http://www.ebi.ac.uk/ebisearch/service.ebi"
    }

    public String getServiceEndPoint() {
        return client.getServiceEndPoint();
    }

//    public List<String> getFields(String domain) {
//        try {
//            return Arrays.asList(client.listFields(domain));
//        } catch (RemoteException e) {
//            throw new IllegalStateException(e);
//        } catch (ServiceException e) {
//            throw new IllegalStateException(e);
//        }
//    }

    // TODO: Add caching (very slow!!)

    /**
     * Get related results for this query.
     *
     * @param query The search query to use (as entered/viewed by the user, no escape characters required)
     * @return The list of related results
     */
    public List<RelatedSearchResult> getRelatedResults(final String query) {
        final String escapedQuery = escapeSpecialChars(query);
//        try {
//            List<RelatedSearchResult> relatedResults = new ArrayList<RelatedSearchResult>();
//            ArrayOfDomainResult a = null;
//            try {
//                DomainResult dr = client.getDetailledNumberOfResults("allebi", escapedQuery, false);
//                a = dr.getSubDomainsResults().getValue();
//            } catch (SOAPFaultException e) {
//                // TODO: Add logging
//                System.out.println("Could not get related results for " + escapedQuery + ": " + e);
//            }
//            if (a != null) {
//                for (DomainResult sd : a.getDomainResult()) {
//                    String id = sd.getDomainId().getValue();
//                    int count = sd.getNumberOfResults();
//                    if (count > 0) {
//                        // TODO: use enum to exclude
//                        if (!id.equals("ebiweb") && !id.equals("proteinFamilies") && !id.equals("ontologies")) {
//                            relatedResults.add(new RelatedSearchResult(id, sd.getNumberOfResults()));
//                        }
//                    }
//                }
//            }
//            return relatedResults;
//        } catch (RemoteException e) {
//            throw new IllegalStateException(e);
//        } catch (ServiceException e) {
//            throw new IllegalStateException(e);
//        }
        return null;
    }

    /**
     * Get facets for the queries.
     *
     * @param query              The query with facet selections taken into account (assumes escape characters have already been added)
     * @param queryWithoutFacets The query ignoring any facet selections (assumes escape characters have already been added)
     * @return The list of facets
     */
    private List<Facet> getFacets(final String query, final String queryWithoutFacets, final String domainIndexName) {

        /*
         * Example facet to currently selected values map:
         * dbname -> [pfam, tigrfam]
         * type -> [domain]
         */
        Map<String, Set<String>> facetToSelectedValuesMap = getFacetToValuesMap(query);
        boolean areAllSelected = facetToSelectedValuesMap.isEmpty();

        List<Facet> facets = new ArrayList<Facet>();
        try {
            uk.ac.ebi.webservices.axis1.stubs.ebeye.Facet[] f = client.getFacets(domainIndexName, queryWithoutFacets);
            for (uk.ac.ebi.webservices.axis1.stubs.ebeye.Facet facet : f) {
                String facetName = facet.getLabel().toLowerCase(); // E.g. "dbname" or "type"
                for (FacetValue v : facet.getFacetValues()) {
                    String label = Facet.convertLabel(v.getLabel());
                    String id = label.replaceAll(" ", "_").toLowerCase(); // E.g. "domain"
                    boolean selected = false;
                    if (!areAllSelected) {
                        // Is this ID in our set of known selected values (e.g. "[domain]") for this facet (e.g. "type")?
                        final Set<String> selectedValues = facetToSelectedValuesMap.get(facetName);
                        boolean isAllThisFacetSelected = selectedValues.isEmpty();
                        if (!isAllThisFacetSelected) {
                            selected = selectedValues.contains(id);
                        }
                    }
                    facets.add(new Facet(facetName + ":", id, label, v.getHitCount(), selected));
                }
            }
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        } catch (ServiceException e) {
            throw new IllegalStateException(e);
        }
        return facets;
    }

    /**
     * Extract text relevant to InterPro facets from the supplied query.
     * <p/>
     * Example query:
     * domain_source:interpro GO:0005524 kinase type:domain insulin dbname:(pfam OR tigrfam)
     * Returns a map:
     * dbname -> [pfam, tigrfam]
     * type -> [domain]
     *
     * @param query
     * @return
     */
    private Map<String, Set<String>> getFacetToValuesMap(final String query) {

        String regex = "(";
        boolean firstFacet = true;
        for (FacetName facetName : FacetName.values()) {
            if (facetName.isInterProSpecific()) {
                if (!firstFacet) {
                    regex += "|";
                }
                regex += facetName;
                firstFacet = false;
            }
        }
        regex += "):(\\w+|\\([^)]+\\))";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(query);

        Map<String, Set<String>> facetToValuesMap = new HashMap<String, Set<String>>();
        /*
         * Example query:
         * domain_source:interpro GO:0005524 kinase type:domain insulin dbname:(pfam OR tigrfam)
         * Returns matches:
         * - type:domain
         * - dbname:(pfam OR tigrfam)
         */
        while (matcher.find()) {
            // Example text to parse: "type:(family OR domain)"
            String textToParse = matcher.group();
            String[] s = textToParse.split(":\\(?");
            final String facetName = s[0]; // E.g. "type"
            textToParse = s[1]; // E.g. "family OR domain)"
            if (textToParse.endsWith(")")) {
                textToParse = textToParse.substring(0, textToParse.length() - 1); // E.g. "family OR domain"
            }
            s = textToParse.split("\\sOR\\s"); // [family, domain]
            Set<String> facetValues = new HashSet<String>();
            for (int i = 0; i < s.length; i++) {
                facetValues.add(s[i]);
            }
            if (facetToValuesMap.containsKey(facetName)) {
                //TODO Throw an error here instead??
                // Key already exists so add to it (values are unique in a set)
                facetToValuesMap.get(facetName).addAll(facetValues);
            } else {
                facetToValuesMap.put(facetName, facetValues);
            }
        }
        return facetToValuesMap;
    }


    /**
     * Just remove any facet search filtering terms from the query text provided.
     *
     * @param query        The query to remove facets from (if any).
     * @param interproOnly Only remove InterPro specific facets?
     * @return The query with facets removed
     */
    private String stripFacets(String query, boolean interproOnly) {
        for (FacetName facetName : FacetName.values()) {
            final String facet = facetName + ":";
            if (query.contains(facet)) {
                if (!interproOnly || facetName.isInterProSpecific()) {
                    // Remove this facet text! E.g. remove "type:domain" or "type:(domain OR family)"
                    // TODO Won't work with "kinase (type:domain OR type:family)"
                    query = query.replaceAll(facet + "(\\w+|\\([^)]+\\))", "").trim();
                }
            }
        }

        return query;
    }

    /**
     * Take the query and add escape characters as appropriate.
     * E.g. "infected cells: Cp-IAP type:domain" would become "infected cells\\: Cp-IAP type:domain", note that the escape
     * character was added to the colon in the plain text, but not to the "type" facet.
     *
     * @param query The query without escape characters
     * @return The same query with escape characters added
     */
    private String escapeSpecialChars(String query) {
        StringBuilder sb = new StringBuilder("(?<!");
        FacetName[] facetNames = FacetName.values();
        for (int i = 0; i < facetNames.length; i++) {
            if (i > 0) {
                sb.append("|");
            }
            sb.append(facetNames[i].getName()); // Look for colons that are not preceded with a recognised facet name
        }
        sb.append("):");
        final String regex = sb.toString();
        query = query.replaceAll(regex, "\\\\:");
        return query;
    }

    /**
     * Perform a text search.
     *
     * @param query              The query
     * @param resultIndex        Retrieve results from this (zero indexed) search result number
     * @param resultsPerPage     The number of results to retrieve for a page
     * @param includeDescription Include full search result description text
     * @return A page object containing all necessary variables required to display a search results page
     */
    public InterProSearchFormBean search(final String query, int resultIndex, int resultsPerPage, boolean includeDescription, final String domainIndexName) {
        String internalQuery = query; // An internal version of the query (as entered by the user, but changed behind
        // the scenes to pass the the EBI search webservice).

        // Always add on "domain_source:interpro" to every search page query, even though it's only really necessary
        // when the user entered an empty query, or just a facet with no search terms!
        final String DOMAIN_SOURCE = FacetName.DOMAIN_SOURCE.getName() + ":" + domainIndexName;
//        if (internalQuery.isEmpty()) {
//            internalQuery = DOMAIN_SOURCE;
//        } else if (!internalQuery.contains(DOMAIN_SOURCE)) {
//            internalQuery = DOMAIN_SOURCE + " " + internalQuery;
//        }

        final String escapedInternalQuery = escapeSpecialChars(internalQuery); // Query with escape chars added, not to be displayed to the user!
        TextHighlighter highlighter = new TextHighlighter(escapedInternalQuery);

        try {
            //String[] domains = client.listDomains(); // See what domains are available besides INTERPRO
            List<InterProSearchResult> records = new ArrayList<InterProSearchResult>();
            int count = client.getNumberOfResults(domainIndexName, internalQuery);
            if (count > 0) {
                List<String> f = new ArrayList<String>();
                f.add("id");
                f.add("name");
                f.add("type");
                if (includeDescription) {
                    f.add("description");
                }
                String[] fields = f.toArray(new String[f.size()]);
                String[][] results = client.getResults(domainIndexName, internalQuery, fields, resultIndex, resultsPerPage);
                for (String[] a : results) {
                    String id = null, name = null, type = null, description = null;
                    int len = a.length;
                    if (len > 0) {
                        id = a[0];
                        if (len > 1) {
                            name = a[1];
                            if (len > 2) {
                                type = a[2];
                                if (len > 3) {
                                    description = a[3];
                                }
                            }
                        }
                    } else {
                        throw new IllegalStateException("No results, yet result count reported as " + count);
                    }
                    // Highlight
                    name = highlighter.highlightTitle(name);
                    description = highlighter.highlightDescription(description);
                    records.add(new InterProSearchResult(id, name, type, description));
                }
            }
            // Only strip InterPro specific facets (e.g. type:family, dbname:pfam), leave others (e.g. domain_source:interpro)
            final String queryWithoutFacets = stripFacets(internalQuery, false);
            final String internalQueryWithoutInterProFacets = stripFacets(internalQuery, true);
            final String escapedInternalQueryWithoutInterProFacets = escapeSpecialChars(internalQueryWithoutInterProFacets);
            List<Facet> facets = new ArrayList<Facet>();
            int countWithoutFacets = 0;
            if (!escapedInternalQueryWithoutInterProFacets.isEmpty()) {
                countWithoutFacets = client.getNumberOfResults(domainIndexName, escapedInternalQueryWithoutInterProFacets);
                facets = getFacets(escapedInternalQuery, escapedInternalQueryWithoutInterProFacets, domainIndexName);
            }
            try {
                final String encodedQuery = URLEncoder.encode(query, "UTF-8");
                //All metagenomes search - make it readable
                if (internalQuery.length() > 20) {
                    internalQuery = "All metagenomes";
                }
                return new InterProSearchFormBean(
                        internalQuery, // For display to the user, therefore use the query as entered by the user
                        queryWithoutFacets, // As entered by the user, with all facets removed
                        count,
                        countWithoutFacets,
                        PAGER.getCurrentPageNumber(resultIndex, resultsPerPage),
                        records,
                        PAGER.getLinks("/search?q=" + encodedQuery + "&amp;start=${start}", resultIndex, count, resultsPerPage),
                        facets,
                        null); // No error message as the query was successful
            } catch (UnsupportedEncodingException e) {
                throw new IllegalArgumentException("Could not URL encode query: " + query);
            }
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        } catch (ServiceException e) {
            throw new IllegalStateException(e);
        }
    }

    public InterProSearchFormBean search(final String query, int resultIndex, int resultsPerPage, boolean includeDescription) {
        String queryString = "SRP008047";
        try {
            DomainResult domainResult = client.getDetailledNumberOfResults("nucleotideSequences", queryString, false);
            for (DomainResult subDomainResult : domainResult.getSubDomainsResults()) {
                String[][] results = client.getResults(subDomainResult.getDomainId(), queryString, new String[]{"name", "description"}, 0, 100);
            }
        } catch (RemoteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ServiceException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    /**
     * Retrieve data for related search results from other EBI resources (not from InterPro).
     *
     * @param query The search query (without escape chars).
     * @return Data required to display related search results on a web page.
     */
    public RelatedSearchFormBean relatedSearch(final String query) {
        // Strip all facets (incl domain_source:interpro), not just the InterPro specific facets (e.g. type:family, dbname:pfam)
        final String internalQueryWithoutAllFacets = stripFacets(query, false);
        final String escapedInternalQueryWithoutAllFacets = escapeSpecialChars(internalQueryWithoutAllFacets);
        List<RelatedSearchResult> relatedResults = getRelatedResults(escapedInternalQueryWithoutAllFacets);
        return new RelatedSearchFormBean(query,
                internalQueryWithoutAllFacets,
                relatedResults,
                null); // No error message as the query was successful
    }

    public EBeyeClient getClient() {
        return client;
    }

    /**
     * This main method allows user testing of the search algorithms without the graphical (HTML) output.
     *
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, ServiceException {

        // Get query
        String query = "gut TAXONOMY:9606";
//        if (args.length > 0) {
//            query = args[0];
//            if (query.equals("\"\"")) {
//                query = "";
//            }
//        } else {
//            throw new IllegalArgumentException("Please pass in a search term or sequence");
//        }

        // Get query
        String endPointUrl = "";
//        if (args.length > 1) {
//            System.out.println();
//            endPointUrl = args[1];
//        }


        // It's not a sequence

        boolean includeDescription = true;
        if (args.length > 2) {
            includeDescription = Boolean.valueOf(args[2]);
        }

        // TODO: get resultsPerPage from args (default to 10)
        TextSearch search;
        if (endPointUrl.isEmpty()) {
            search = new TextSearch();
        } else {
            search = new TextSearch(endPointUrl);
        }

        InterProSearchFormBean searchPage = search.search(query, 0, 10, includeDescription, Domain.SRA_SAMPLE.getDatabaseName());
        List<InterProSearchResult> results = searchPage.getResults();

        if (searchPage.getCount() > 0) {

            System.out.println("Page " + searchPage.getCurrentPage() + " of " + searchPage.getCount() + " results for '" + query + "':");
            System.out.println();

            // Show search results
            for (InterProSearchResult r : results) {
                System.out.println(r.getName() + " (" + r.getId() + ") [" + r.getType() + "]");
                if (r.getDescription() != null) {
                    System.out.println(r.getDescription());
                    System.out.println("-------------------------------------------------------------------------------");
                }
            }

            // Display pagination link text (not interactive for the user here though)
            for (LinkInfoBean link : searchPage.getPaginationLinks()) {
                System.out.println(link.getName() + " (" + link.getDescription() + ") -> " + link.getLink());
            }
            System.out.println();

            // Show facets
            System.out.println("Facets:");
            System.out.println("All results (" + searchPage.getCountWithoutFacets() + ")");
            for (Facet f : searchPage.getFacets()) {
                String s = "";
                if (f.getIsSelected()) {
                    s = " -- selected";
                }
                System.out.println(f.getLabel() + " (" + f.getCount() + ") [" + f.getPrefix() + f.getId() + "]" + s);
            }
            System.out.println();


        } else {
            System.out.println("No results for '" + query + "'.");
        }

        // Show related results
        List<RelatedSearchResult> relatedResults = search.getRelatedResults(query);
        System.out.println("Related Results:");
        if (relatedResults == null || relatedResults.size() < 1) {
            System.out.println("None");
        } else {
            for (RelatedSearchResult rr : relatedResults) {
                System.out.println(rr.getName() + " (" + rr.getId() + ") [" + rr.getCount() + "]");
            }
        }
    }

}

package uk.ac.ebi.interpro.metagenomics.memi.core.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.forms.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch.*;
import uk.ac.ebi.webservices.jaxrs.EBeyeClient;
import uk.ac.ebi.webservices.jaxrs.stubs.ebeye.*;

import javax.ws.rs.BadRequestException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by maq on 11/03/2016.
 */
public class EBISearchTool {

    private final static Log log = LogFactory.getLog(EBISearchTool.class);

    public static final String ES_SEARCH_WEBSERVICE_URL = "http://wwwdev.ebi.ac.uk/ebisearch/ws/rest";

    public enum Domain {
        SAMPLE_DOMAIN(
                "Samples",
                "metagenomics_samples",
                "id,displayName,description,biome,experiment_type,metagenomics_project,taxonomy"),
        PROJECT_DOMAIN(
                "Projects",
                "metagenomics_projects",
                "id,displayName,description,biome,experiment_type,metagenomics_sample,taxonomy"),
        RUN_DOMAIN(
                "Runs",
                "metagenomics_runs",
                "id,name,organism,experiment_type,go,interpro,metagenomics_sample"),
        OLD_SAMPLE_DOMAIN(
                "Samples",
                "metagenomics",
                "id,displayName,description,biome,experiment_type,taxonomy,project");

        private String displayName;
        private String domain;
        private String fields;
        Domain(String displayName, String domain, String fields) {
            this.displayName = displayName;
            this.domain = domain;
            this.fields = fields;
        }
        public String getDisplayName() { return displayName; }
        public String getDomain() { return domain; }
        public String getQuery() { return "domain_source:" + domain; }
        public String getFields() { return  fields; }
    }

    public final static Domain DEFAULT_DOMAIN = Domain.SAMPLE_DOMAIN;

    private final static String FACET = "facet";


    EBeyeClient client;

    public EBISearchTool() {
        client = new EBeyeClient();
        client.setDebugLevel(5);
        client.setServiceEndPoint(ES_SEARCH_WEBSERVICE_URL);
    }

    /**
     * Searches for data indexed by the EBI-Search
     * @param searchForm
     * @return
     */
    public EBISearchResults search(EBISearchForm searchForm) {
        log.debug("search");
        Domain selectedDomain = getSelectedDomain(searchForm);
        if (selectedDomain == null) {
            selectedDomain = DEFAULT_DOMAIN;
        }
        EBISearchResults results = new EBISearchResults();

        switch (selectedDomain) {
            case PROJECT_DOMAIN:
                //searchProjects(searchForm, selectedDomain)
                break;
            case SAMPLE_DOMAIN:
                results.setSearchType(EBISearchResults.SearchType.SAMPLE);
                searchSamples(searchForm, selectedDomain, results);
                break;
            case RUN_DOMAIN:
                break;
        }

        return results;
    }

    Domain getSelectedDomain(EBISearchForm searchForm) {
        log.debug("getSelectedDomain");
        Domain selectedDomain = null;

        List<String> facets = searchForm.getFacets();
        if (facets != null) {

        }

        return selectedDomain;
    }

    /**
     * Searches the sample data stored via the EBI-Search client
     * @param searchForm
     * @return EBISampleSearchResults object containing the search hits and a set of facets
     */
    public void searchSamples(EBISearchForm searchForm, Domain domain, EBISearchResults results) {
        log.debug("searchSamples");
        String resultFields = domain.getFields();

        try {
            WsResult searchResults = runSearch(searchForm, domain.getDomain(), domain.getQuery(), resultFields);

            Integer hits = searchResults.getHitCount();
            if (hits != null) {
                int maxPage = (int) Math.ceil(new Double(hits) / new Double(searchForm.getResultsPerPage()));
                searchForm.setMaxPage(maxPage);
                List<EBISampleSearchEntry> entryList = results.getSamples();
                for (WsEntry searchEntry : searchResults.getEntries().getEntry()) {
                    EBISampleSearchEntry entry = sampleResultToEntry(searchEntry);
                    entryList.add(entry);
                }

                List<EBISearchFacet> facets = results.getFacets();
                for (WsFacet searchFacet : searchResults.getFacets().getFacet()) {
                    EBISearchFacet facet = resultToFacet(searchFacet);
                    if (facet != null)
                        facets.add(facet);
                }
                results.setNumberOfHits(hits);
            } else {
                results.setNumberOfHits(0);
                searchForm.setMaxPage(0);
            }
        } catch (BadRequestException e) {
            results.setNumberOfHits(0);
            searchForm.setMaxPage(0);
        }
    }

    /*
    public EBIProjectSearchResults searchProjects(EBISearchForm searchForm) {
        log.debug("searchProjects");
        String resultFields = ;

        EBIProjectSearchResults results = new EBIProjectSearchResults();

        try {
            WsResult searchResults = runSearch(searchForm, Domain.PROJECT_DOMAIN.domainName, GET_ALL_PROJECTS_QUERY, resultFields);

            Integer hits = searchResults.getHitCount();

            if (hits != null) {
                int maxPage = (int) Math.ceil(new Double(hits) / new Double(searchForm.getResultsPerPage()));
                searchForm.setMaxPage(maxPage);
                List<EBIProjectSearchEntry> entryList = results.getEntries();
                for (WsEntry searchEntry : searchResults.getEntries().getEntry()) {
                    EBIProjectSearchEntry entry = projectResultToEntry(searchEntry);
                    entryList.add(entry);
                }

                List<EBISearchFacet> facets = results.getFacets();
                for (WsFacet searchFacet : searchResults.getFacets().getFacet()) {
                    EBISearchFacet facet = resultToFacet(searchFacet);
                    if (facet != null)
                        facets.add(facet);
                }
                results.setNumberOfHits(hits);
            } else {
                results.setNumberOfHits(0);
                searchForm.setMaxPage(0);
            }

        } catch (BadRequestException e) {
            results.setNumberOfHits(0);
            searchForm.setMaxPage(0);
        }

        return results;
    }
    */

    /**
     * Runs the search using SearchForm on the supplied domain query, with the supplied fields
     * @param searchForm
     * @param domain
     * @param defaultQuery
     * @param resultFields
     * @return
     */
    WsResult runSearch(EBISearchForm searchForm, String domain, String defaultQuery, String resultFields) {
        String formattedFacetQuery = "";
        if (searchForm.getFacets() != null) {
            List<String> selectedFacets = searchForm.getFacets();
            formattedFacetQuery = formatFacetFields(selectedFacets);
        }

        String searchText = searchForm.getSearchText();
        if (searchText == null || searchText.matches("^\\s*$")) {
            searchText = defaultQuery;
        }

        int resultsIndex = ((searchForm.getPage() - 1) * searchForm.getResultsPerPage());
        int numResults = searchForm.getResultsPerPage();

        WsResult searchResults = client.getFacetedResults(
                domain,
                searchText,
                resultFields,
                resultsIndex,
                numResults,
                true,
                true,
                null,
                null,
                10,
                "",
                formattedFacetQuery
        );

        return searchResults;
    }

    /**
     * Converts Facets selected on the SearchForm from a list of type-value strings separated by
     * EBISearchFacetValue.FACET_SEPARATOR and converts them to a comma-separated list of colon separated facet types
     * and values
     * @param facetAndValues
     * @return
     */
    String formatFacetFields(List<String> facetAndValues) {
        StringBuilder facetQuery = new StringBuilder();
        for (String facet : facetAndValues) {
            String[] tokens = facet.split(EBISearchFacetValue.FACET_SEPARATOR);
            if (tokens.length == 2) {
                facetQuery.append(tokens[0]);
                facetQuery.append(":");
                facetQuery.append(tokens[1]);
                facetQuery.append(",");
            }
        }
        //remove any trailing commas
        if (facetQuery.length() > 0) {
            facetQuery.deleteCharAt(facetQuery.length()-1);
        }
        return facetQuery.toString();
    }

    /*
    EBIProjectSearchEntry projectResultToEntry(WsEntry searchEntry) {
        EBIProjectSearchEntry entry = new EBIProjectSearchEntry();
        entry.setIdentifier(searchEntry.getId());
        try {
            entry.setUrl(new URL(searchEntry.getFieldURLs().getFieldURL().get(0).getValue()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        for (WsField field : searchEntry.getFields().getField()) {
            if (DESCRIPTION.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setDescription(field.getValues().getValue().get(0));
            } else if (NAME.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setName(field.getValues().getValue().get(0));
            } else if (CENTRE.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setCentreName(field.getValues().getValue().get(0));
            } else if (BIOME.equals(field.getId())) {
                entry.setBiomes(field.getValues().getValue());
            } else if (SAMPLE.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setSamples(field.getValues().getValue());
            }
        }
        return entry;
    }
    */

    EBISampleSearchEntry sampleResultToEntry(WsEntry searchEntry) {
        EBISampleSearchEntry entry = new EBISampleSearchEntry();
        entry.setIdentifier(searchEntry.getId());
        try {
            entry.setUrl(new URL(searchEntry.getFieldURLs().getFieldURL().get(0).getValue()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        for (WsField field : searchEntry.getFields().getField()) {
            if (EBISampleSearchEntry.DESCRIPTION.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setDescription(field.getValues().getValue().get(0));
            } else if (EBISampleSearchEntry.NAME.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setName(field.getValues().getValue().get(0));
            } else if (EBISampleSearchEntry.PROJECT.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setProject(field.getValues().getValue().get(0));
            } else if (EBISampleSearchEntry.OLD_PROJECT.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setProject(field.getValues().getValue().get(0));
            } else if (EBISampleSearchEntry.BIOME.equals(field.getId())) {
                entry.setBiomes(field.getValues().getValue());
            } else if (EBISampleSearchEntry.EXPERIMENT.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setExperimentTypes(field.getValues().getValue());
            } else if (EBISampleSearchEntry.TAXONOMY.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setTaxonomy(field.getValues().getValue().get(0));
            }
        }
        return entry;
    }

    EBISearchFacet resultToFacet(WsFacet searchFacet) {
        EBISearchFacet facet = new EBISearchFacet();
        facet.setIdentifier(searchFacet.getId());
        facet.setLabel(searchFacet.getLabel());
        List<EBISearchFacetValue> values = facet.getValues();
        for (WsFacetValue searchValue : searchFacet.getFacetValues().getFacetValue()) {
            EBISearchFacetValue value = new EBISearchFacetValue();
            value.setType(facet.getIdentifier());
            value.setCount(searchValue.getCount());
            value.setLabel(searchValue.getLabel());
            value.setValue(searchValue.getValue());
            values.add(value);
        }
        return facet;
    }
}

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

    public final static String SAMPLES_DOMAIN = "metagenomics_samples";
    public final static String PROJECTS_DOMAIN = "metagenomics_projects";
    public final static String OLD_SAMPLE_DOMAIN = "metagenomics";

    private final static String FACET = "facet";

    private final static String DESCRIPTION = "description";
    private final static String PROJECT = "METAGENOMICS_PROJECT";
    private final static String SAMPLE = "METAGENOMICS_SAMPLE";
    private final static String OLD_PROJECT = "project";
    private final static String NAME = "name";
    private final static String CENTRE = "centre_name";
    private final static String TAXONOMY = "TAXONOMY";
    private final static String BIOME = "biome";
    private final static String EXPERIMENT = "experiment_type";

    private final static String GET_ALL_SAMPLES_QUERY = "domain_source:" + SAMPLES_DOMAIN;
    private final static String GET_ALL_PROJECTS_QUERY = "domain_source:" + PROJECTS_DOMAIN;
    private final static String GET_ALL_OLD_SAMPLES_QUERY = "domain_source:" + OLD_SAMPLE_DOMAIN;


    EBeyeClient client;

    public EBISearchTool() {
        client = new EBeyeClient();
        client.setDebugLevel(5);
        client.setServiceEndPoint(ES_SEARCH_WEBSERVICE_URL);
    }

    /**
     * Searches the sample data stored via the EBI-Search client
     * @param searchForm
     * @return EBISampleSearchResults object containing the search hits and a set of facets
     */
    public EBISampleSearchResults searchSamples(EBISearchForm searchForm) {
        log.debug("searchSamples");
        String resultFields = "id,name,description,"+ OLD_PROJECT + ","+ PROJECT + "," + TAXONOMY + ",biome,experiment_type";

        EBISampleSearchResults results = new EBISampleSearchResults();

        try {
            WsResult searchResults = runSearch(searchForm, SAMPLES_DOMAIN, GET_ALL_SAMPLES_QUERY, resultFields);

            Integer hits = searchResults.getHitCount();
            if (hits != null) {
                int maxPage = (int) Math.ceil(new Double(hits) / new Double(searchForm.getResultsPerPage()));
                searchForm.setMaxPage(maxPage);
                List<EBISampleSearchEntry> entryList = results.getEntries();
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

        return results;
    }

    public EBIProjectSearchResults searchProjects(EBISearchForm searchForm) {
        log.debug("searchProjects");
        String resultFields = "id,name,description,"+ OLD_PROJECT + ","+ PROJECT + "," + TAXONOMY + ",biome,experiment_type";

        EBIProjectSearchResults results = new EBIProjectSearchResults();

        try {
            WsResult searchResults = runSearch(searchForm, PROJECTS_DOMAIN, GET_ALL_PROJECTS_QUERY, resultFields);

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

    EBISampleSearchEntry sampleResultToEntry(WsEntry searchEntry) {
        EBISampleSearchEntry entry = new EBISampleSearchEntry();
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
            } else if (PROJECT.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setProject(field.getValues().getValue().get(0));
            } else if (OLD_PROJECT.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setProject(field.getValues().getValue().get(0));
            } else if (BIOME.equals(field.getId())) {
                entry.setBiomes(field.getValues().getValue());
            } else if (EXPERIMENT.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setExperimentTypes(field.getValues().getValue());
            } else if (TAXONOMY.equals(field.getId()) && field.getValues().getValue().size() > 0) {
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

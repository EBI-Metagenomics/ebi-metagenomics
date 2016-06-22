package uk.ac.ebi.interpro.metagenomics.memi.core.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.forms.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch.*;
import uk.ac.ebi.webservices.jaxrs.EBeyeClient;
import uk.ac.ebi.webservices.jaxrs.stubs.ebeye.*;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by maq on 11/03/2016.
 */
public class EBISearchTool {

    private final static Log log = LogFactory.getLog(EBISearchTool.class);

    private final static String DOMAIN_SOURCE_FACET = "domain_source";

    public static final String ES_SEARCH_WEBSERVICE_URL = "http://wwwdev.ebi.ac.uk/ebisearch/ws/rest";

    public enum Domain {
        SAMPLES(
                "Samples",
                "metagenomics_samples",
                "id,name,description,biome,experiment_type,METAGENOMICS_PROJECT,TAXONOMY"),
        PROJECTS(
                "Projects",
                "metagenomics_projects",
                "id,name,description,biome_name,METAGENOMICS_SAMPLE"),
        RUNS(
                "Runs",
                "metagenomics_runs",
                "id,experiment_type,pipeline_version,METAGENOMICS_SAMPLE,METAGENOMICS_PROJECT"),
        OLD_SAMPLES(
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
        public String toString() { return displayName.toLowerCase(); }
    }

    public final static Domain DEFAULT_DOMAIN = Domain.SAMPLES;

    private final static String FACET = "facet";

    EBeyeClient client;
    Gson gson;

    public EBISearchTool() {
        client = new EBeyeClient();
        client.setDebugLevel(5);
        client.setServiceEndPoint(ES_SEARCH_WEBSERVICE_URL);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    /**
     * Searches for data indexed by the EBI-Search
     * @param
     * @return
     */
    public String search(EBISearchResults results) {
        log.debug("search");
        Domain selectedDomain = getSelectedDomain(results);
        try {
            if (selectedDomain != null) {
                switch (selectedDomain) {
                    case PROJECTS:
                        searchProjects(selectedDomain, results);
                        break;
                    case SAMPLES:
                        searchSamples(selectedDomain, results);
                        break;
                    case RUNS:
                        searchRuns(selectedDomain, results);
                        break;
                }
            }
        } catch (InternalServerErrorException e) {
            results.setError("Something has gone wrong with the search server. Please try again later and report this error to metagenomics-help@ebi.ac.uk");
        }
        String json = gson.toJson(results);
        return json;
    }

    public String searchAllDomains(EBISearchResults results) {
        //default behaviour is to search all domains
        try {
            searchProjects(Domain.PROJECTS, results);
            searchSamples(Domain.SAMPLES, results);
            searchRuns(Domain.RUNS, results);

        } catch (InternalServerErrorException e) {
            results.setError("Something has gone wrong with the search server. Please try again later and report this error to metagenomics-help@ebi.ac.uk");
        }
        String json = gson.toJson(results);
        return json;
    }

    Domain getSelectedDomain(EBISearchResults results) {
        log.debug("getSelectedDomain");
        Domain selectedDomain = null;
        if (results.getSearchType() != null) {
            selectedDomain = Domain.valueOf(results.getSearchType().toUpperCase());
        }
        return selectedDomain;
    }

    /**
     * Searches the sample data stored via the EBI-Search client
     * @param
     * @return EBISampleSearchResults object containing the search hits and a set of facets
     */
    public void searchSamples(Domain domain, EBISearchResults results) throws InternalServerErrorException {
        log.debug("searchSamples");
        EBISampleSearchResults sampleResults = results.getSamples();

        try {
            WsResult searchResults = runSearch(sampleResults, results.getSearchText(), domain.getDomain(), domain.getQuery(), domain.getFields());

            Integer hits = searchResults.getHitCount();

            if (hits != null) {
                int maxPage = (int) Math.ceil(new Double(hits) / new Double(results.getResultsPerPage()));
                sampleResults.setMaxPage(maxPage);
                List<EBISampleSearchEntry> entryList = sampleResults.getEntries();
                for (WsEntry searchEntry : searchResults.getEntries().getEntry()) {
                    EBISampleSearchEntry entry = sampleResultToEntry(searchEntry);
                    entryList.add(entry);
                }

                List<EBISearchFacet> facets = sampleResults.getFacets();
                for (WsFacet searchFacet : searchResults.getFacets().getFacet()) {
                    EBISearchFacet facet = resultToFacet(searchFacet);
                    if (facet != null)
                        facets.add(facet);
                }
                sampleResults.setNumberOfHits(hits);
            } else {
                sampleResults.setNumberOfHits(0);
                sampleResults.setMaxPage(0);
            }
        } catch (BadRequestException e) {
            sampleResults.setNumberOfHits(0);
            sampleResults.setMaxPage(0);
        }
    }


    public void searchProjects(Domain domain, EBISearchResults results) throws InternalServerErrorException {
        log.debug("searchProjects");

        EBIProjectSearchResults projectResults = results.getProjects();

        try {
            WsResult searchResults = runSearch(projectResults, results.getSearchText(), domain.getDomain(), domain.getQuery(), domain.getFields());

            Integer hits = searchResults.getHitCount();

            if (hits != null) {
                int maxPage = (int) Math.ceil(new Double(hits) / new Double(results.getResultsPerPage()));
                projectResults.setMaxPage(maxPage);
                List<EBIProjectSearchEntry> entryList = projectResults.getEntries();
                for (WsEntry searchEntry : searchResults.getEntries().getEntry()) {
                    EBIProjectSearchEntry entry = projectResultToEntry(searchEntry);
                    entryList.add(entry);
                }

                List<EBISearchFacet> facets = projectResults.getFacets();
                for (WsFacet searchFacet : searchResults.getFacets().getFacet()) {
                    EBISearchFacet facet = resultToFacet(searchFacet);
                    if (facet != null)
                        facets.add(facet);
                }
                projectResults.setNumberOfHits(hits);
            } else {
                projectResults.setNumberOfHits(0);
                projectResults.setMaxPage(0);
            }

        } catch (BadRequestException e) {
            projectResults.setNumberOfHits(0);
            projectResults.setMaxPage(0);
        }
    }

    public void searchRuns(Domain domain, EBISearchResults results) throws InternalServerErrorException {
        log.debug("searchRuns");

        EBIRunSearchResults runResults = results.getRuns();

        try {
            String fields = domain.getFields();
            WsResult searchResults = runSearch(runResults, results.getSearchText(), domain.getDomain(), domain.getQuery(), fields);

            Integer hits = searchResults.getHitCount();

            if (hits != null) {
                int maxPage = (int) Math.ceil(new Double(hits) / new Double(results.getResultsPerPage()));
                runResults.setMaxPage(maxPage);
                List<EBIRunSearchEntry> entryList = runResults.getEntries();
                for (WsEntry searchEntry : searchResults.getEntries().getEntry()) {
                    EBIRunSearchEntry entry = runResultToEntry(searchEntry);
                    entryList.add(entry);
                }

                List<EBISearchFacet> facets = runResults.getFacets();
                for (WsFacet searchFacet : searchResults.getFacets().getFacet()) {
                    EBISearchFacet facet = resultToFacet(searchFacet);
                    if (facet != null)
                        facets.add(facet);
                }
                runResults.setNumberOfHits(hits);
            } else {
                runResults.setNumberOfHits(0);
                runResults.setMaxPage(0);
            }

        } catch (BadRequestException e) {
            runResults.setNumberOfHits(0);
            runResults.setMaxPage(0);
        }
    }

    /**
     * Runs the search using SearchForm on the supplied domain query, with the supplied fields
     * @param searchResults
     * @param domain
     * @param defaultQuery
     * @param resultFields
     * @return
     */
    WsResult runSearch(ISearchResults searchResults, String searchText, String domain, String defaultQuery, String resultFields) {
        String formattedFacetQuery = "";
        if (searchResults.getCheckedFacets() != null) {
            List<String> selectedFacets = searchResults.getCheckedFacets();
            formattedFacetQuery = formatFacetFields(selectedFacets);
        }

        if (searchText == null || searchText.matches("^\\s*$")) {
            searchText = defaultQuery;
        }

        int resultsIndex = ((searchResults.getPage() - 1) * searchResults.getResultsPerPage());
        int numResults = searchResults.getResultsPerPage();

        WsResult webSearchResults = client.getFacetedResults(
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

        return webSearchResults;
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
            if (EBIProjectSearchEntry.DESCRIPTION.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setDescription(field.getValues().getValue().get(0));
            } else if (EBIProjectSearchEntry.NAME.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setName(field.getValues().getValue().get(0));
            } else if (EBIProjectSearchEntry.CENTRE_NAME.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setCentreName(field.getValues().getValue().get(0));
            } else if (EBIProjectSearchEntry.BIOME_NAME.equals(field.getId())) {
                entry.setBiomes(field.getValues().getValue());
            } else if (EBIProjectSearchEntry.SAMPLE.equals(field.getId()) && field.getValues().getValue().size() > 0) {
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
            if (EBISampleSearchEntry.DESCRIPTION.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setDescription(field.getValues().getValue().get(0));
            } else if (EBISampleSearchEntry.NAME.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setName(field.getValues().getValue().get(0));
            } else if (EBISampleSearchEntry.PROJECT.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setProject(field.getValues().getValue().get(0));
            }  else if (EBISampleSearchEntry.BIOME.equals(field.getId())) {
                entry.setBiomes(field.getValues().getValue());
            } else if (EBISampleSearchEntry.EXPERIMENT.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setExperimentTypes(field.getValues().getValue());
            } else if (EBISampleSearchEntry.TAXONOMY.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setTaxonomy(field.getValues().getValue().get(0));
            } else if (EBISampleSearchEntry.OLD_NAME.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setName(field.getValues().getValue().get(0));
            } else if (EBISampleSearchEntry.OLD_PROJECT.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setProject(field.getValues().getValue().get(0));
            }
        }
        return entry;
    }

    EBIRunSearchEntry runResultToEntry(WsEntry searchEntry) {
        EBIRunSearchEntry entry = new EBIRunSearchEntry();
        entry.setIdentifier(searchEntry.getId());
        try {
            entry.setUrl(new URL(searchEntry.getFieldURLs().getFieldURL().get(0).getValue()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        for (WsField field : searchEntry.getFields().getField()) {
            if (EBIRunSearchEntry.EXPERIMENT_TYPE.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setExperimentType(field.getValues().getValue().get(0));
            } else if (EBIRunSearchEntry.PIPELINE_VERSION.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setPipelineVersion(field.getValues().getValue().get(0));
            } else if (EBIRunSearchEntry.SAMPLE.equals(field.getId()) && field.getValues().getValue().size() > 0) {
                entry.setSample(field.getValues().getValue().get(0));
            } else if (EBIRunSearchEntry.PROJECT.equals(field.getId())) {
                entry.setProject(field.getValues().getValue().get(0));
            }
        }
        return entry;
    }

    EBISearchFacet resultToFacet(WsFacet searchFacet) {
        EBISearchFacet facet = null;
        if (!DOMAIN_SOURCE_FACET.equals(searchFacet.getId())) {
            facet = new EBISearchFacet();
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
        }
        return facet;
    }
}

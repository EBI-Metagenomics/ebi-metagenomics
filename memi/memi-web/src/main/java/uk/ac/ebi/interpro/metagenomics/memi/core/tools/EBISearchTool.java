package uk.ac.ebi.interpro.metagenomics.memi.core.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.forms.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch.EBISampleSearchEntry;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch.EBISearchFacet;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch.EBISearchFacetValue;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch.EBISampleSearchResults;
import uk.ac.ebi.webservices.jaxrs.EBeyeClient;
import uk.ac.ebi.webservices.jaxrs.stubs.ebeye.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by maq on 11/03/2016.
 */
public class EBISearchTool {

    private final static Log log = LogFactory.getLog(EBISearchTool.class);

    public static final String ES_SEARCH_WEBSERVICE_URL = "http://www.ebi.ac.uk/ebisearch/ws/rest";

    public final static String DOMAIN = "metagenomics";

    private final static String FACET = "facet";

    private final static String DESCRIPTION = "description";
    private final static String PROJECT = "project";
    private final static String TAXONOMY = "taxonomy";
    private final static String BIOME = "biome";
    private final static String EXPERIMENT = "experiment_type";

    EBeyeClient client;

    public EBISearchTool() {
        client = new EBeyeClient();
        client.setDebugLevel(5);
        client.setServiceEndPoint(ES_SEARCH_WEBSERVICE_URL);
    }

    /**
     * Returns a list of facetted fields with display name, name and id
     * @return
     */
    public Map<String, String> getFacets() {
        Map<String, String> facets = new HashMap<String, String>();
        WsResult result = client.getDomainDetails("metagenomics");

        for (WsDomain domain : result.getDomains().getDomain()) {
            for (WsFieldInfo info : domain.getFieldInfos().getFieldInfo()) {
                for (WsOption option : info.getOptions().getOption()) {
                    if (FACET.equals(option.getName())) {
                        if (Boolean.parseBoolean(option.getValue())) {
                            facets.put(info.getId(), info.getDescription());
                        }
                    }
                }
            }
        }
        return facets;
    }

    public EBISampleSearchResults searchSamples(EBISearchForm searchForm) {
        log.debug("searchSamples");
        String resultFields = "id,description,project,taxonomy,biome,experiment_type";

        String formattedFacetQuery = "";
        if (searchForm.getFacets() != null) {
            formattedFacetQuery = formatFacetFields(searchForm.getFacets());
        }

        WsResult searchResults = client.getFacetedResults(
                DOMAIN,
                searchForm.getSearchText(),
                resultFields,
                searchForm.getPage(),
                searchForm.getResultsPerPage(),
                true,
                true,
                null,
                null,
                10,
                "",
                formattedFacetQuery
        );
        Integer hits = searchResults.getHitCount();
        EBISampleSearchResults results = new EBISampleSearchResults();
        List<EBISampleSearchEntry> entryList = results.getEntries();
        for (WsEntry searchEntry : searchResults.getEntries().getEntry()) {
            EBISampleSearchEntry entry = resultToEntry(searchEntry);
            entryList.add(entry);
        }

        List<EBISearchFacet> facets = results.getFacets();
        for (WsFacet searchFacet : searchResults.getFacets().getFacet()) {
            EBISearchFacet facet = resultToFacet(searchFacet);
            if (facet != null)
                facets.add(facet);
        }
        results.setNumberOfHits(hits);
        return results;
    }

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
        if (facetQuery.length() > 0) {
            facetQuery.deleteCharAt(0);
        }
        return facetQuery.toString();
    }

    EBISampleSearchEntry resultToEntry(WsEntry searchEntry) {
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
            } else if (PROJECT.equals(field.getId()) && field.getValues().getValue().size() > 0) {
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

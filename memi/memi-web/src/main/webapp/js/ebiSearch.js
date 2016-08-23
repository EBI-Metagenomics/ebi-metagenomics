/**
 * Created by maq on 06/04/2016.
 */

var HIDDEN_CLASS = "this_hide";
var FACET_SEPARATOR = "____";
var SEARCH_TAB_CLASS = "search-tab";
var BASE_URL = "https://www.ebi.ac.uk/ebisearch/ws/rest/";

var FACET_SOURCE = "Source";

var GLOBAL_SEARCH_SETTINGS = {
    PROJECT_RESULTS_NUM: 10,
    SAMPLE_RESULTS_NUM: 10,
    RUN_RESULTS_NUM: 20,
    DEFAULT_SEARCH_START: 0,
    FACET_NUM: 10,


    PROJECT: "Projects",
    PROJECT_DOMAIN: "metagenomics_projects",
    PROJECT_FIELDS: "id,name,description,biome_name,METAGENOMICS_SAMPLE",
    SAMPLE: "Samples",
    SAMPLE_DOMAIN: "metagenomics_samples",
    SAMPLE_FIELDS: "id,name,description,experiment_type,METAGENOMICS_PROJECT",
    RUN: "Runs",
    RUN_DOMAIN: "metagenomics_runs",
    RUN_FIELDS: "id,experiment_type,pipeline_version,METAGENOMICS_SAMPLES,METAGENOMICS_PROJECTS",
    METAGENOMICS_SEARCH_TEXT : "www.ebi.ac.uk.metagenomics.searchsettings",
    METAGENOMICS_SEARCH_SETTINGS : "www.ebi.ac.uk.metagenomics.searchsettings",
    METAGENOMICS_SEARCH_SETTINGS_SELECTED_TAB: "www.ebi.ac.uk.metagenomics.selectedFacet"
};

var DatatypeSettings = {};

function SearchSettings (type,
                         resultsNum,
                         page,
                         facetNum,
                         facets,
                         domain,
                         fields,
                         numericalFields) {
    this.type = type;
    this.resultsNum = resultsNum;
    this.page = page;
    this.facetNum = facetNum;
    if (facets == null) {
        facets = {};
    }
    this.facets = facets;
    this.bonsaiTrees = {};
    this.bonsaiState = {};
    this.domain = domain;
    this.fields = fields;
    this.numericalFields = numericalFields;
    this.searchText = "";
};

function NumericalRangeField (name, displayName, unit, minimum, maximum, selectedMinimum, selectedMaximum, callbackName) {
    this.name = name;
    this.displayName = displayName;
    this.unit = unit;
    this.minimum = minimum;
    this.maximum = maximum;
    this.selectedMinimum = selectedMinimum;
    this.selectedMaximum = selectedMaximum;
    this.callback = "searchRange";
};

var initialiseSettings = function() {
    var projectSettings = new SearchSettings(
        GLOBAL_SEARCH_SETTINGS.PROJECT,
        GLOBAL_SEARCH_SETTINGS.PROJECT_RESULTS_NUM,
        GLOBAL_SEARCH_SETTINGS.DEFAULT_SEARCH_START,
        GLOBAL_SEARCH_SETTINGS.FACET_NUM,
        null,
        GLOBAL_SEARCH_SETTINGS.PROJECT_DOMAIN,
        GLOBAL_SEARCH_SETTINGS.PROJECT_FIELDS,
        null
    );

    var sampleTemperature = new NumericalRangeField("temperature", "Temperature", "°C", 0, 100, 0, 100);
    var sampleDepth = new NumericalRangeField("depth", "Depth", "Metres", 0, 1000, 0, 1000);
    //var samplePH = new NumericalRangeField("pH", "pH", null, 0, 14, 0, 14);

    var sampleSettings = new SearchSettings(
        GLOBAL_SEARCH_SETTINGS.SAMPLE,
        GLOBAL_SEARCH_SETTINGS.SAMPLE_RESULTS_NUM,
        GLOBAL_SEARCH_SETTINGS.DEFAULT_SEARCH_START,
        GLOBAL_SEARCH_SETTINGS.FACET_NUM,
        null,
        GLOBAL_SEARCH_SETTINGS.SAMPLE_DOMAIN,
        GLOBAL_SEARCH_SETTINGS.SAMPLE_FIELDS,
        [sampleTemperature, sampleDepth]
    );

    var runTemperature = new NumericalRangeField("temperature", "Temperature", "°C", 0, 100, 0, 100);
    var runDepth = new NumericalRangeField("depth", "Depth", "Metres", 0, 1000, 0, 1000);
    //var runPH = new NumericalRangeField("pH", "pH", null, 0, 14, 0, 14);

    var runSettings = new SearchSettings(
        GLOBAL_SEARCH_SETTINGS.RUN,
        GLOBAL_SEARCH_SETTINGS.RUN_RESULTS_NUM,
        GLOBAL_SEARCH_SETTINGS.DEFAULT_SEARCH_START,
        GLOBAL_SEARCH_SETTINGS.FACET_NUM,
        null,
        GLOBAL_SEARCH_SETTINGS.RUN_DOMAIN,
        GLOBAL_SEARCH_SETTINGS.RUN_FIELDS,
        [runTemperature, runDepth]
    );

    DatatypeSettings.DATA_TYPES = [
        GLOBAL_SEARCH_SETTINGS.PROJECT,
        GLOBAL_SEARCH_SETTINGS.SAMPLE,
        GLOBAL_SEARCH_SETTINGS.RUN
    ];

    DatatypeSettings[GLOBAL_SEARCH_SETTINGS.PROJECT] = projectSettings;
    DatatypeSettings[GLOBAL_SEARCH_SETTINGS.SAMPLE] = sampleSettings;
    DatatypeSettings[GLOBAL_SEARCH_SETTINGS.RUN] = runSettings;
};

/*
Behaviour methods
 */

window.onload = function (event){
    console.log("onload");
    var searchElementID = "local-searchbox";
    var searchElement = document.getElementById(searchElementID);
    if (searchElement != null) {
        var searchText = sessionStorage.getItem(GLOBAL_SEARCH_SETTINGS.METAGENOMICS_SEARCH_TEXT)
        if (searchText != null) {
            searchElement.value = searchText;
        }
    } else {
        console.log("Error: expected input with id = " + searchElementID);
    }
    var seachPageElementID = "searchTabs";
    var searchPageTabElement = document.getElementById(seachPageElementID);
    if (searchPageTabElement != null) {
        initialiseSettings();
        for (var i=0; i < DatatypeSettings.DATA_TYPES.length; i++) {
            var dataType = DatatypeSettings.DATA_TYPES[i];
            var searchSetting = sessionStorage.getItem(GLOBAL_SEARCH_SETTINGS.METAGENOMICS_SEARCH_SETTINGS + dataType);
            if (searchSetting != null) {
                DatatypeSettings[dataType] = JSON.parse(searchSetting);
            }
        }
        loadPageFromServer(runNewSearch, DatatypeSettings);
    }
};

window.popstate =  function(event){
    console.log("popstate");
    var state = event.state;
    var seachPageElementID = "searchTabs";
    var searchPageTabElement = document.getElementById(seachPageElementID);
    if (searchPageTabElement != null) {
        loadCss();
        DatatypeSettings = JSON.parse(state);
        runNewSearch(DatatypeSettings);
    }
};

window.onunload = function(event) {
    console.log("Unloading");
};

var loadCss = function() {
    //load css for the modal box
    var linkElement = document.createElement("link");
    linkElement.rel = "stylesheet";
    linkElement.href = "/metagenomics/css/ajax-modal.css";
    document.head.appendChild(linkElement);

}

/**
 * Creates table header cells and table row
 * @param columns
 * @param table
 */
var addTableHeader = function(columns, table) {
    tableHeadRow = document.createElement("tr");

    for (i in columns) {
        var column = columns[i];
        var tableCol = document.createElement("th");
        tableCol.innerHTML = column.name;
        if ("className" in column) {
            tableCol.className = column.className;
        }
        tableHeadRow.appendChild(tableCol);
    }

    tableHead = document.createElement("thead");
    tableHead.appendChild(tableHeadRow);
    table.appendChild(tableHead);
}

/**
 * Creates a data table row and table cells
 * @param columns
 * @param table
 */
var addTableRow = function(columns, table) {
    tableRow = document.createElement("tr");
    for (i in columns) {
        var column = columns[i];
        var tableCol = document.createElement("td");
        if ("className" in column) {
            tableCol.className = column.className;
        }
        if ("url" in column) {
            var link = document.createElement("a");
            link.href = column.url;
            link.innerHTML = column.name;
            tableCol.appendChild(link);
        } else {
            tableCol.innerHTML = column.name;
        }
        tableRow.appendChild(tableCol);
    }
    table.appendChild(tableRow);
};

/*
var saveFormState = function() {
    var tabContainer = document.getElementById("searchTabs");
    if (tabContainer) {
        var selected = $(tabContainer).tabs('option', 'active');
        console.log("Saving state " + selected);
        sessionStorage.activeTab = selected;
    }
};

var restoreTabState = function() {
    var tabContainer = document.getElementById("searchTabs");
    if (tabContainer) {
        var selected = sessionStorage.activeTab;
        console.log("Loading state " + selected);
        if (selected) {
            var selected = $(tabContainer).tabs('option', 'active', selected);
        }
    }

};
*/

var displayPagination = function(results, searchSettings) {
    //console.log("Adding pagination for " + searchSettings.type);
    var dataType = searchSettings.type;

    var paginationContainer = document.getElementById(dataType + "-searchPagination");
    if (paginationContainer != null) {
        paginationContainer.innerHTML = "";
        var prevButton = document.createElement("input");
        prevButton.type = "button";
        prevButton.id = dataType + "-prevPage";
        prevButton.value = "Previous";

        if (searchSettings.page <= 0 ) {
            prevButton.disabled = true;
        }
        prevButton.addEventListener('click', function(event) {
            searchSettings.page--;
            runDomainSearch(searchSettings);
        });

        var nextButton = document.createElement("input");
        nextButton.type = "button";
        nextButton.id = dataType + "-nextPage";
        nextButton.value = "Next";
        var maxPage = Math.ceil(results.hitCount/searchSettings.resultsNum);
        if ((searchSettings.page+1) >= maxPage) {
            nextButton.disabled = true;
        }
        nextButton.addEventListener('click', function(event) {
            searchSettings.page++;
            runDomainSearch(searchSettings);
        });

        var textSpan = document.createElement("span");
        textSpan.textContent = " Page " + (searchSettings.page+1) + " of " + (maxPage) + " ";
        paginationContainer.appendChild(prevButton);
        paginationContainer.appendChild(textSpan);
        paginationContainer.appendChild(nextButton);
    } else {
        console.log("Error: Expected to find div with id '" + dataType + "-searchPagination'");
    }
};

var isFacetGroupHierarchical = function(facetGroup) {
    var isHierarchical = false;
    for (var i=0; i < facetGroup.facetValues.length; i++) {
        if (facetGroup.facetValues[i].hasOwnProperty("children")) {
            isHierarchical = true;
            break;
        }
    }
    return isHierarchical;
};

var addFacetValueChangeListener = function (facetInput, facetType, facet, searchSettings, bonsaiTreeID) {
    facetInput.addEventListener("change", function(event) {
        if (bonsaiTreeID != null) {
            var bonsaiTree = $("#"+bonsaiTreeID).data('bonsai');
            searchSettings.bonsaiState[facetType] = bonsaiTree.serialize();
        }
        if (facetInput.checked) {
            if (!searchSettings.facets.hasOwnProperty(facetType)) {
                searchSettings.facets[facetType] = [];
            }
            searchSettings.facets[facetType].push(facetInput.value);
        } else {
            if (searchSettings.facets.hasOwnProperty(facetType)) {
                var valueIndex = searchSettings.facets[facetType].indexOf(facetInput.value);
                if (valueIndex > -1) {
                    searchSettings.facets[facetType].splice(valueIndex, 1);
                }
            } else {
                console.log("Error - expected to find facet type: " + facetType);
            }
        }
        console.log("Facet clicked " + facetInput.value + " Setting: " + Object.keys(searchSettings.facets));
        runDomainSearch(searchSettings);
    });
};

var displayFacetGroup = function(facetGroup, container, searchSettings) {
    var dataType = searchSettings.type;
    var facetGroupContainer = document.createElement("div");
    var groupContainerId = dataType + FACET_SEPARATOR + facetGroup.id;
    facetGroupContainer.id = groupContainerId;
    container.appendChild(facetGroupContainer);

    var facetGroupTitle = document.createElement("h4");
    facetGroupTitle.innerHTML = facetGroup.label;
    facetGroupContainer.appendChild(facetGroupTitle);
    for (var i=0; i < facetGroup.facetValues.length; i++) {
        var facet = facetGroup.facetValues[i];
        var identifier = dataType + FACET_SEPARATOR + facetGroup.id + FACET_SEPARATOR + facet.value;
        var facetItem = document.createElement("div");
        var facetInput = document.createElement("input");
        facetInput.id = identifier;
        facetInput.name = facetGroup.id;
        facetInput.form = "local-search";
        facetInput.type = "checkbox";
        facetInput.value = facet.value;
        if (searchSettings.facets != null
            && searchSettings.facets.hasOwnProperty(facetGroup.id)
            && searchSettings.facets[facetGroup.id].indexOf(facetInput.value) >= 0) {
            facetInput.checked = true;
        }

        addFacetValueChangeListener(facetInput, facetGroup.id, facet, searchSettings, null);

        var facetLabel = document.createElement("label");
        facetLabel.htmlFor = facetInput.id;
        facetLabel.innerHTML = facet.label + " (" + facet.count + ")";

        facetItem.appendChild(facetInput);
        facetItem.appendChild(facetLabel);

        var facetContainerDiv = document.createElement("div");
        facetContainerDiv.className = "extra-pad";
        facetContainerDiv.appendChild(facetItem);

        facetGroupContainer.appendChild(facetContainerDiv);
    }
};

var displayHierarchicalFacetGroup = function(facetGroup, container, searchSettings) {
    var dataType = searchSettings.type;
    var facetGroupContainer = document.createElement("div");
    var groupContainerId = dataType + FACET_SEPARATOR + facetGroup.id;
    //facetGroupContainer.id = groupContainerId;
    container.appendChild(facetGroupContainer);

    var facetGroupTitle = document.createElement("h4");
    facetGroupTitle.innerHTML = facetGroup.label;
    facetGroupTitle.innerHTML = facetGroup.label;
    facetGroupContainer.appendChild(facetGroupTitle);

    var facetGroupList = document.createElement("ul");
    facetGroupList.id = groupContainerId;
    facetGroupContainer.appendChild(facetGroupList);
    $("#"+groupContainerId).bonsai({
        checkboxes: true,
        handleDuplicateCheckboxes: true
    });
    for (var i=0; i < facetGroup.facetValues.length; i++) {
        var facet = facetGroup.facetValues[i];
        var identifier = dataType + FACET_SEPARATOR + facetGroup.id + FACET_SEPARATOR + facet.value;
        var facetItem = document.createElement("li");
        var facetInput = document.createElement("input");
        facetInput.id = identifier;
        facetInput.name = facetGroup.id;
        facetInput.form = "local-search";
        facetInput.type = "checkbox";
        facetInput.value = facet.value;

        if (searchSettings.facets != null
            && searchSettings.facets.hasOwnProperty(facetGroup.id)
            && searchSettings.facets[facetGroup.id].indexOf(facetInput.value) > -1) {
            facetInput.checked = true;
            facetInput.setAttribute("data-checked", "");
        }

        addFacetValueChangeListener(facetInput, facetGroup.id, facet, searchSettings, groupContainerId);

        facetItem.appendChild(facetInput);
        facetItem.appendChild(document.createTextNode(facet.label + " (" + facet.count + ")"));

        if (facet.children != null) {
            var facetChildList = document.createElement("ul");
            displayHierarchicalChildren(facetChildList, facet, facetGroup, facetInput.value, searchSettings, groupContainerId);
            facetItem.appendChild(facetChildList);
            //TODO MAQ this is a quick fix for a bug in hierarchical searches - Nicola will fix this soon
            facetGroupList.appendChild(facetItem);
        }
    }
    var bonsaiTree = $("#"+groupContainerId).data('bonsai');
    bonsaiTree.update();
    if ( searchSettings.bonsaiState.hasOwnProperty(facetGroup.id)
        && searchSettings.bonsaiState[facetGroup.id] != null) {
        bonsaiTree.restore(searchSettings.bonsaiState[facetGroup.id]);
        //$("#"+groupContainerId).bonsai('restore', searchSettings.bonsaiState[facetGroup.id]);
    }

};

var displayHierarchicalChildren = function(container, facet, facetGroup, parentPath, searchSettings, bonsaiTreeID) {
    var dataType = searchSettings.type;
    var children = facet.children;
    console.log("Facet with children: " + children.length);
    for (var i = 0; i < children.length; i++) {
        var childFacet = children[i];
        //console.log("Child facet: " + facetGroup.id + " name: " + childFacet.label);

        var value = parentPath + "/" + childFacet.value;
        var identifier = dataType + FACET_SEPARATOR + facetGroup.id + FACET_SEPARATOR + value;

        var facetItem = document.createElement("li");
        var facetInput = document.createElement("input");
        facetInput.id = identifier;
        facetInput.name = facetGroup.id;
        facetInput.form = "local-search";
        facetInput.type = "checkbox";
        facetInput.value = value;

        if (searchSettings.facets != null
            && searchSettings.facets.hasOwnProperty(facetGroup.id)
            && searchSettings.facets[facetGroup.id].indexOf(facetInput.value) > -1) {
            facetInput.checked = true;
            facetInput.setAttribute("data-checked", "");
        }

        addFacetValueChangeListener(facetInput, facetGroup.id, facet, searchSettings, bonsaiTreeID);

        facetItem.appendChild(facetInput);
        facetItem.appendChild(document.createTextNode(childFacet.label + " (" + childFacet.count + ")"));
        container.appendChild(facetItem);

        if (childFacet.children != null) {
            var facetChildList = document.createElement("ul");
            displayHierarchicalChildren(container, childFacet, facetGroup, facetInput.value, searchSettings, bonsaiTreeID);
            facetItem.appendChild(facetChildList);
        }
    }
};

var displayFacets = function(facetGroups, searchSettings) {
    var dataType = searchSettings.type;
    var facetContainer = document.getElementById(dataType + "-searchFacets");
    if (facetContainer != null) {
        facetContainer.innerHTML = ""; //clear out old facets
        var facetContainerTitle = document.createElement("h3");
        facetContainerTitle.innerHTML = "Filter your results";

        facetContainer.appendChild(facetContainerTitle);
        if (searchSettings.hasOwnProperty("numericalFields")
            && searchSettings.numericalFields != null) {
            displayNumericalInputs(facetContainer, searchSettings);
        }
        for (var i =0; i < facetGroups.length; i++) {
            var facetGroup = facetGroups[i];
            if (facetGroup.label !== FACET_SOURCE) {
                if (isFacetGroupHierarchical(facetGroup)) {
                    displayHierarchicalFacetGroup(facetGroup, facetContainer, searchSettings);
                    //console.log("FacetGroup Hierarchical " + facetGroup.label);
                } else {
                    displayFacetGroup(facetGroup, facetContainer, searchSettings);
                    //console.log("FacetGroup " + facetGroup.label);
                }

            }
        }

        var searchInfoP = document.createElement("p");
        var searchInfoSmall = document.createElement("small");
        searchInfoSmall.className = "text-muted";
        searchInfoSmall.innerHTML = "Powered by ";
        searchInfoSmall.innerHTML += '<a href="http://www.ebi.ac.uk/ebisearch/" class="ext" target="_blank">EBI Search</a>';
        searchInfoP.appendChild(searchInfoSmall);
        facetContainer.appendChild(document.createElement("hr"));
        facetContainer.appendChild(searchInfoP);
    } else {
        console.log("Error: Expected to find div with id '" + dataType + "-searchFacets'");
    }
};

var displayNumericalInputs = function(container, searchSettings) {
    var rangeContainerDiv = document.createElement("div");
    container.appendChild(rangeContainerDiv);

    for (var i = 0; i < searchSettings.numericalFields.length; i++) {

        var numericalField = searchSettings.numericalFields[i];
        var fieldContainer = document.createElement("div");
        rangeContainerDiv.appendChild(fieldContainer);
        var rangeTitle = document.createElement("h4");
        rangeTitle.innerHTML = numericalField.displayName;
        fieldContainer.appendChild(rangeTitle);

        var rangeInput = document.createElement("input");
        rangeInput.type = "range";
        rangeInput.setAttribute("multiple", "");
        rangeInput.setAttribute("min", numericalField.minimum);
        rangeInput.setAttribute("max", numericalField.maximum);
        var selectedRange = numericalField.selectedMinimum + "," + numericalField.selectedMaximum;
        rangeInput.setAttribute("value", selectedRange);
        fieldContainer.id = searchSettings.type + FACET_SEPARATOR + numericalField.name;

        var minText = document.createTextNode(numericalField.minimum + " ");
        var maxText = document.createTextNode(" " + numericalField.maximum);

        fieldContainer.appendChild(minText);
        fieldContainer.appendChild(rangeInput);
        fieldContainer.appendChild(maxText);
        multirange(rangeInput); //requires multirange library loaded

        var rangeLabel = document.createElement("label");
        if (numericalField.unit != null) {
            rangeLabel.innerHTML = " " + numericalField.unit;
            rangeLabel.htmlFor = rangeInput.id;
            fieldContainer.appendChild(rangeLabel);
        }

        var selectedRangeContainer = document.createElement("div");
        selectedRangeContainer.name = "selected-range";
        selectedRangeContainer.style["text-align"] = "left";
        if (numericalField.selectedMinimum != numericalField.minimum || numericalField.selectedMaximum != numericalField.maximum) {
            var rangeString = "Filtering: " + numericalField.selectedMinimum + " to " + numericalField.selectedMaximum;
            if (numericalField.unit != null) {
                rangeString += " " + numericalField.unit;
            }
            var selectedRangeText = document.createTextNode(rangeString);
            selectedRangeContainer.appendChild(selectedRangeText);
        }
        fieldContainer.appendChild(selectedRangeContainer);

        addNumericalFieldValueChangeListener(fieldContainer, selectedRangeContainer, rangeInput, numericalField, searchSettings);
    }
};

var addNumericalFieldValueChangeListener = function(fieldContainer, selectedRangeContainer, fieldInput, numericalField, searchSettings) {
    fieldContainer.addEventListener("change", function(event){
        var tokens = fieldInput.value.split(",");
        numericalField.selectedMinimum = tokens[0];
        numericalField.selectedMaximum = tokens[1];
        selectedRangeContainer.innerHTML = "";
        if (numericalField.selectedMinimum != numericalField.minimum || numericalField.selectedMaximum != numericalField.maximum) {
            var rangeString = "Range: " + numericalField.selectedMinimum + " to " + numericalField.selectedMaximum;
            if (numericalField.unit != null) {
                rangeString += " " + numericalField.unit;
            }
            var selectedRangeText = document.createTextNode(rangeString);
            selectedRangeContainer.appendChild(selectedRangeText);
        }
        console.log(numericalField.displayName + " range: " + tokens);
        runDomainSearch(searchSettings);
    });
};

/**
 * Setup for project table
 * @param data
 * @param container
 */
var displayProjectTable = function(results, container) {
    console.log("Showing project data");

    table = document.createElement("table");
    table.border = 1;
    table.className = "table-light";

    var headerData = [
        {name: "Project"},
        {name: "Name"},
        {name: "Biome", className: "xs_hide"},
        {name: "Description", className: "xs_hide"}
    ];

    addTableHeader(headerData, table);

    for (var i=0; i < results.entries.length; i++) {
        var entry = results.entries[i];
        var rowData = [
            {
                name: entry["id"],
                url: "http://" + window.location.host + "/metagenomics/projects/" + entry["id"]
            },
            {name: entry.fields.name[0]},
            {name: entry.fields.biome_name[0], className: "xs_hide"},
            {name: entry.fields.description[0], className: "xs_hide"}
        ];
        addTableRow(rowData, table);
    }
    container.appendChild(table);
};

/**
 * Setup for sample table
 * @param results
 * @param container
 */
var displaySampleTable = function(results, container) {
    console.log("Showing sample data");
    table = document.createElement("table");
    table.border = 1;
    table.className = "table-light";

    var headerData = [
        {name: "Sample"},
        {name: "Project", className: "xs_hide"},
        {name: "Name"},
        {name: "Description", className: "xs_hide"},
    ];

    addTableHeader(headerData, table);

    for (var i=0; i < results.entries.length; i++) {
        var entry = results.entries[i];
        var rowData = [
            {
                name: entry["id"],
                url: "http://" + window.location.host + "/metagenomics/projects/" + entry["fields"]["METAGENOMICS_PROJECT"][0] + "/samples/" +  entry["id"]
            },
            {
                name: entry["fields"]["METAGENOMICS_PROJECT"][0],
                url: "http://" + window.location.host + "/metagenomics/projects/" + entry["fields"]["METAGENOMICS_PROJECT"][0]
            },
            {name: entry.fields.name[0]},
            {name: entry.fields.description[0], className: "xs_hide"}
        ];
        addTableRow(rowData, table);
    }
    container.appendChild(table);
};

/**
 * setup run table
 * @param results
 * @param container
 */
var displayRunTable = function(results, container) {
    console.log("Showing run data");
    table = document.createElement("table");
    table.border = 1;
    table.className = "table-light";

    var headerData = [
        {name: "Run"},
        {name: "Sample", className: "xs_hide"},
        {name: "Project", className: "xs_hide"},
        {name: "Experiment Type"},
        {name: "Pipeline Version", className: "xs_hide"},
    ];

    addTableHeader(headerData, table);

    for (var i=0; i < results.entries.length; i++) {
        var entry = results.entries[i];
        var rowData = [
            {
                name: entry.id,
                url: "http://" + window.location.host + "/metagenomics/projects/"
                + entry.fields.METAGENOMICS_PROJECTS[0] + "/samples/"
                + entry.fields.METAGENOMICS_SAMPLES[0] + "/runs/"
                + entry.id + "/results/versions/"
                + entry.fields.pipeline_version[0]
            },
            {
                name: entry.fields.METAGENOMICS_SAMPLES[0],
                className: "xs_hide",
                url: "http://" + window.location.host + "/metagenomics/projects/"
                + entry.fields.METAGENOMICS_PROJECTS[0] + "/samples/"
                + entry.fields.METAGENOMICS_SAMPLES[0],

            },
            {
                name: entry.fields.METAGENOMICS_PROJECTS[0],
                className: "xs_hide",
                url: "http://" + window.location.host + "/metagenomics/projects/" + entry.fields.METAGENOMICS_PROJECTS[0],
            },
            {
                name: entry.fields.experiment_type[0]
            },
            {
                name: entry.fields.pipeline_version[0],
                className: "xs_hide",
                url: "http://" + window.location.host + "/metagenomics/pipelines/" + entry.fields.pipeline_version[0],
            },

        ];
        addTableRow(rowData, table);
    }

    container.appendChild(table);
};

var displayData = function(results, dataType, dsiplaytableFunction) {
    resultsContainer = document.getElementById(dataType + "-searchData");
    if (resultsContainer != null) {
        resultsContainer.innerHTML = ""; //clear results div
        resultsTitle = document.createElement("h3");
        if (results.hasOwnProperty("entries") && results.entries != null) {
            resultsTitle.innerHTML = "Showing " + results.entries.length
                + " out of " + results.hitCount + " results";
            resultsContainer.appendChild(resultsTitle);
            dsiplaytableFunction(results, resultsContainer);
        } else {
            resultsTitle.innerHTML = "Search returned no results";
        }

    } else {
        console.log("Error: Expected to find div with id '" + dataType + "-searchData'");
    }
};

var displayDomainData = function(httpReq, searchSettings) {
    console.log("displayDomain: " + searchSettings.type);

    var searchElementID = "local-searchbox";
    var searchElement = document.getElementById(searchElementID);
    var searchText = "";
    if (searchElement != null) {
        searchElement.value = searchSettings.searchText;
    } else {
        console.log("Error expected to find input with id " + searchElementID);
    }

    var resultString = httpReq.response;
    var results = JSON.parse(resultString);
    console.log(
        "Search returned "
        + results.hitCount + " "
        + searchSettings.type + " results"
    );

    setTabText(results.hitCount, searchSettings.type);
    if (searchSettings.type == GLOBAL_SEARCH_SETTINGS.PROJECT) {
        displayData(results, searchSettings.type, displayProjectTable);
    } else if (searchSettings.type == GLOBAL_SEARCH_SETTINGS.SAMPLE) {
        displayData(results, searchSettings.type, displaySampleTable);
    } else if (searchSettings.type == GLOBAL_SEARCH_SETTINGS.RUN) {
        displayData(results, searchSettings.type, displayRunTable);
    } else {
        console.log("Error: DisplayDomainData - Unknown data type '" + searchSettings.type + "'");
    }
    displayFacets(results.facets, searchSettings);
    displayPagination(results, searchSettings);
}

var showSpinner = function(searchSettings) {
    var dataType = searchSettings.type;
    setTabText("Searching", dataType);
    resultsContainer = document.getElementById(dataType + "-searchData");
    if (resultsContainer != null) {
        resultsContainer.innerHTML = "";
    } else {
        console.log("Error: Expected to find div with id '" + dataType + "-searchData'");
    }
    facetsContainer = document.getElementById(dataType + "-searchFacets");
    if (facetsContainer != null) {
        facetsContainer.innerHTML = "";
    } else {
        console.log("Error: Expected to find div with id '" + dataType + "-searchFacets'");
    }
    var paginationContainer = document.getElementById(dataType + "-searchPagination");
    if (paginationContainer != null) {
        paginationContainer.innerHTML = "";
    } else {
        console.log("Error: Expected to find div with id '" + dataType + "-searchPagination'");
    }

};

var removeSpinner = function(searchSettings) {

};

var displaySearchError = function (httpReq, searchSettings) {
    var dataType = searchSettings.type;
    setTabText("Error", dataType);
    resultsContainer = document.getElementById(dataType + "-searchData");
    if (resultsContainer != null) {
        resultsContainer.innerHTML = "<div>A problem was encountered running the search. Please try again later.</div><div>If the problem persists please contact us <a href='/metagenomics/contact'>here</a></div>";
    } else {
        console.log("Error: Expected to find div with id '" + dataType + "-searchData'");
    }
};

var runAjax = function(method, url, parameters, callback, errCallback) {
    var httpReq = new XMLHttpRequest();
    httpReq.open(method, url);
    httpReq.send(parameters);

    //handle response
    httpReq.onload = function(event) {
        var readyState = httpReq.readyState;
        if (httpReq.status == 200) {
            callback(httpReq);
        } else {
            errCallback(httpReq);
        }

    };

    //error handling
    httpReq.onerror = function (event) {
        if (errCallback) {
            errCallback(httpReq);
        } else {
            console.log("Ajax error");
        }
    };
};

var parametersToString = function(parameters) {
    var parameterString = "?";
    var types = Object.keys(parameters);
    for (var i = 0; i < types.length; i++) {
        var type = types[i];
        var value = parameters[type];
        if (type === "facets") {
            var facetString = "";
            var facetTypes = Object.keys(value)
            for (var j=0; j < facetTypes.length; j++) {
                var facetType = facetTypes[j];
                var facetValues = value[facetType];
                if (facetValues.length > 0) {
                    for (var k = 0; k < facetValues.length; k++) {
                        var facetValue = facetValues[k];
                        facetString += facetType + ":" + encodeURIComponent(facetValue) + ",";
                    }
                    //remove final trailing comma ','
                    facetString = facetString.substr(0, facetString.length - 1);
                    parameterString += type + "=" + facetString + "&";
                }
            }
        } else {
            parameterString += type + "=" + value + "&";
        }

    }
    //remove final trailing ampersand '&'
    parameterString = parameterString.substr(0, parameterString.length - 1);
    return parameterString;
}

var runDomainSearch = function(searchSettings) {
    console.log("Searchtext = " + searchSettings.searchText);
    sessionStorage.setItem(GLOBAL_SEARCH_SETTINGS.METAGENOMICS_SEARCH_TEXT, searchSettings.searchText);
    sessionStorage.setItem(GLOBAL_SEARCH_SETTINGS.METAGENOMICS_SEARCH_SETTINGS + searchSettings.type,  JSON.stringify(searchSettings));

    console.log("about to push state");
    history.pushState(JSON.stringify(DatatypeSettings), "search", "/metagenomics/search");

    var searchText = searchSettings.searchText;
    if  (searchText == null || searchText == "") {
        searchText = "domain_source:" + searchSettings.domain;
    }
    var numericalQuery = null;
    if (searchSettings.numericalFields != null) {
        var queries = [];
        for(var i=0; i < searchSettings.numericalFields.length; i++) {
            var numericalField = searchSettings.numericalFields[i];
            if (numericalField.selectedMinimum > numericalField.minimum
                || numericalField.selectedMaximum < numericalField.maximum) {
                var fieldQuery = numericalField.name
                    + ":[" + numericalField.selectedMinimum
                    + " TO " + numericalField.selectedMaximum + "]";
                queries.push(fieldQuery);
            }
        }
        if (queries.length > 0) {
            numericalQuery = queries.join(" AND ");
        }
    }
    if (numericalQuery != null) {
        searchText += " AND " + numericalQuery;
    }
    var parameters = {
        "query": encodeURIComponent(searchText),
        "format": "json",
        "size": searchSettings.resultsNum,
        "start": searchSettings.page * searchSettings.resultsNum,
        "fields": searchSettings.fields,
        "facetcount": searchSettings.facetNum,
        "facetsdepth": 2
    };

    if (searchSettings.facets != null) {
        parameters.facets = searchSettings.facets;
    }

    var paramFragment = parametersToString(parameters);
    var url = BASE_URL + searchSettings.domain + paramFragment;
    console.log("Running domain search = " + url);
    showSpinner(searchSettings);
    var successCallback = function(httpReq) {
        removeSpinner(searchSettings);
        displayDomainData(httpReq, searchSettings);
    };
    var errorCallback = function(httpReq) {
        displaySearchError(httpReq, searchSettings);
    };

    runAjax("GET", url, null, successCallback, errorCallback);
};

var runNewSearch = function(allSearchSettings) {
    for(var i=0; i < allSearchSettings.DATA_TYPES.length; i++) {
        var dataType = allSearchSettings.DATA_TYPES[i];
        var searchSetting = allSearchSettings[dataType];
        searchSetting.facets = {};
        runDomainSearch(searchSetting);
    }
};

/**
 * Adds a jquery ui tab set to the supplied div
 * @param container
 * @param disabledList
 */
var setupJQueryTabs = function(container, disabledList) {
    var selectedTab = sessionStorage.getItem(GLOBAL_SEARCH_SETTINGS.METAGENOMICS_SEARCH_SETTINGS_SELECTED_TAB);
    if (selectedTab == null) {
        selectedTab = 0;
    }
    var children = container.getElementsByClassName(SEARCH_TAB_CLASS);

    /*
    for (var i=0; i < children.length; i++) {
        if (disabledList.indexOf(i) == -1) {
            selectedTab = i;
            break;
        }
    }
    */

    $(container).tabs({
        disabled: disabledList,
        active: selectedTab,
        activate: function(event, ui) {
            var selectedTab = $(container).tabs('option', 'active');
            sessionStorage.setItem(GLOBAL_SEARCH_SETTINGS.METAGENOMICS_SEARCH_SETTINGS_SELECTED_TAB, selectedTab);
        }
    });
};

var setTabText = function(hitCount, dataType, element) {
    var titleText = dataType.charAt(0).toUpperCase() + dataType.slice(1); //capitalise first letter
    if (hitCount != null) {
        titleText += " (" + hitCount + ")";
    }

    if (element == null) {
        element = document.getElementById(dataType + "-link");
    }

    if (element != null) {
        element.innerHTML = titleText;
    } else {
        console.log("Error: Expected to find element with id '" + dataType+"-link'")
    }

};

/**
 * displays a set of tabs based on the datatypes property of the data
 * @param data
 * @returns {Element}
 */
var displayTabHeader = function() {
    var tabContainer = document.getElementById("searchTabs");
    if (tabContainer != null) {
        var tabList = tabContainer.getElementsByTagName("ul");
        if (tabList == null || tabList.length <= 0) {
            tabList = document.createElement("ul");

            var disabledList = [];
            for (i in DatatypeSettings.DATA_TYPES) {
                var dataType = DatatypeSettings.DATA_TYPES[i];
                var tabItem = document.createElement("li");
                tabItem.className = SEARCH_TAB_CLASS;
                var tabLink = document.createElement("a");
                tabLink.id = dataType + "-link";
                tabLink.value = dataType;
                tabLink.href = "#" + dataType;
                setTabText(null, dataType, tabLink);

                /*
                if (data != null && data[dataType].numberOfHits == 0) {
                    disabledList.push(parseInt(i));
                }
                */
                tabItem.appendChild(tabLink)
                tabList.appendChild(tabItem);
            }
            tabContainer.insertBefore(tabList, document.getElementById("tabDiv"));
            setupJQueryTabs(tabContainer, disabledList);
        } else {
            console.log("Tabs already exist");
        }

    } else {
        console.log("Expected to find div with id 'searchTabs'");
    }
    return tabContainer;
};

var prepareNewSearchSettings = function() {
    initialiseSettings();
    var searchElementID = "local-searchbox";
    var searchElement = document.getElementById(searchElementID);
    var searchText = "";
    if (searchElement) {
        searchText = searchElement.value;
        for(var i=0; i < DatatypeSettings.DATA_TYPES.length; i++) {
            var dataType = DatatypeSettings.DATA_TYPES[i];
            var searchSetting = DatatypeSettings[dataType];
            searchSetting.searchText = searchText;
        }
    } else {
        console.log("Error: Expected to find text input element " + searchElementID);
    }
    return DatatypeSettings;
};

var loadPageFromServer = function(callback, callbackArgs) {
    runAjax("GET", "/metagenomics/search", null, function(httpReq) {
        var response = httpReq.response;
        console.log("Loading search template");
        document.documentElement.innerHTML = response;
        loadCss();
        displayTabHeader();
        if (callback != null) {
            callback(callbackArgs);
        }
    }, function(httpReq) {
        console.log("Error: Failed to load page template");
    });
};

/**
 * Main page setup. Calls Metagenomics server to get header, footer etc
 * and then then populates search tabs with results
 */
var search = function() {
    var allSearchSettings = prepareNewSearchSettings();
    loadPageFromServer(runNewSearch, allSearchSettings);
};



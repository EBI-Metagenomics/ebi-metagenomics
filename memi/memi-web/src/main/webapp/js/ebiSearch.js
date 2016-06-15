/**
 * Created by maq on 06/04/2016.
 */

var HIDDEN_CLASS = "this_hide";

var setupJQueryTabs = function(container, disabledList) {
    $(container).tabs({
        disabled: disabledList,
    });
};

var displayTabHeader = function(data) {
    var tabContainer = document.getElementById("searchTabs");
    if (tabContainer != null) {
        var tabList = tabContainer.getElementsByTagName("ul");
        if (tabList == null || tabList.length <= 0) {
            tabList = document.createElement("ul");
            var dataTypes = data.dataTypes;
            var disabledList = [];
            for (i in dataTypes) {
                var dataType = dataTypes[i];
                console.log(dataType);
                var tabItem = document.createElement("li");
                var tabLink = document.createElement("a");
                tabLink.id = dataType;
                tabLink.value = dataType;
                tabLink.href = "#" + dataType;

                if (data[dataType].numberOfHits > 0) {
                    disabledList.push(1);
                } else {
                    disabledList.push(0);
                }
                console.log(tabItem.getAttribute("count"));
                var titleText = dataType.charAt(0).toUpperCase() + dataType.slice(1); //capitalise first letter
                //tabLink.text = titleText;
                titleText += " (" + data[dataType].numberOfHits + ")";
                var tabLinkSpan = document.createElement("span");
                tabLinkSpan.innerHTML = titleText;
                tabLink.appendChild(tabLinkSpan);
                tabItem.appendChild(tabLink)
                tabList.appendChild(tabItem);
            }
            tabContainer.appendChild(tabList);
            setupJQueryTabs(tabContainer, disabledList);
        } else {
            console.log("Tabs already exist");
        }

    } else {
        console.log("Expected to find div with id 'searchTabs'");
    }
    return tabContainer;
};

var displayFacetGroup = function(facetGroup, container ,facetCount) {
    facetGroupTitle = document.createElement("h4");
    facetGroupTitle.innerHTML = facetGroup.label;
    container.appendChild(facetGroupTitle);
    for (i in facetGroup.values) {
        facetCount++;
        facet = facetGroup.values[i];

        facetDiv = document.createElement("div");
        facetInput = document.createElement("input");
        facetInput.id = "facets" + facetCount;
        facetInput.name = "facets";
        facetInput.form = "local-search";
        facetInput.type = "checkbox";
        facetInput.value = facet.type + "___" + facet.value;
        facetLabel = document.createElement("label");
        facetLabel.htmlFor = facetInput.id;
        facetLabel.innerHTML = facet.label + " (" + facet.count + ")";
        facetDiv.appendChild(facetInput);
        facetDiv.appendChild(facetLabel);
        facetContainerDiv = document.createElement("div");
        facetContainerDiv.className = "extra-pad";
        facetContainerDiv.appendChild(facetDiv);
        springInput = document.createElement("input");
        springInput.type = "hidden";
        springInput.name = "_facets";
        springInput.value = "on";
        facetContainerDiv.appendChild(springInput);

        container.appendChild(facetContainerDiv);
    }
};

var displayFacets = function(facetGroups, dataType) {
    var facetContainer = document.getElementById(dataType + "-searchFacets");
    if (facetContainer != null) {
        facetContainerTitle = document.createElement("h3");
        facetContainerTitle.innerHTML = "Filter your results";

        facetContainer.appendChild(facetContainerTitle);
        facetCount = 0;
        for (i in facetGroups) {
            var facetGroup = facetGroups[i];
            displayFacetGroup(facetGroup, facetContainer, facetCount);
            console.log("FacetGroup " + facetGroup.label);
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
        console.log("Expected to find div with id '" + dataType + "-searchFacets'");
    }
};

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

var displayProjectTable = function(data, container) {
    console.log("Showing project data");

    table = document.createElement("table");
    table.border = 1;
    table.className = "table-light";

    var headerData = [
        {name: "Project"},
        {name: "Name"},
        {name: "Description", className: "xs_hide"},
        {name: "Biome", className: "xs_hide"}
    ];

    addTableHeader(headerData, table);

    for (i in data.entries) {
        var entry = data.entries[i];
        var rowData = [
            {
                name: entry.identifier,
                url: "http:/metagenomics/projects/" + entry.identifier
            },
            {name: entry.name},
            {name: entry.description, className: "xs_hide"},
            {name: entry.biomes[0], className: "xs_hide"}
        ];
        addTableRow(rowData, table);
    }

    container.appendChild(table);
};

var displaySampleTable = function(data, container) {
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

    for (i in data.entries) {
        var entry = data.entries[i];
        var rowData = [
            {
                name: entry.identifier,
                url: "http:/metagenomics/projects/ " + entry.project + "/samples/" + entry.identifier},
            {
                name: entry.project,
                className: "xs_hide",
                url: "http:/metagenomics/projects/ " + entry.project,
            },
            {name: entry.name},
            {name: entry.description, className: "xs_hide"},

        ];
        addTableRow(rowData, table);
    }

    container.appendChild(table);
};

var displayRunTable = function(data, container) {
    console.log("Showing sample data");
    table = document.createElement("table");
    table.border = 1;
    table.className = "table-light";

    var headerData = [
        {name: "Run"},
        {name: "Project", className: "xs_hide"},
        {name: "Sample", className: "xs_hide"},
        {name: "Pipeline Version", className: "xs_hide"},
    ];

    addTableHeader(headerData, table);

    for (i in data.entries) {
        var entry = data.entries[i];
        var rowData = [
            {
                name: entry.identifier,
                url: "http:/metagenomics/projects/ "
                + entry.project + "/samples/"
                + entry.sample + "/runs/"
                + entry.identifier + "/results/versions/"
                + entry.pipelineVersion
            },
            {
                name: entry.project,
                className: "xs_hide",
                url: "http:/metagenomics/projects/ " + entry.project,
            },
            {
                name: entry.sample,
                className: "xs_hide",
                url: "http:/metagenomics/projects/ "
                + entry.project + "/samples/"
                + entry.sample,

            },
            {
                name: entry.pipelineVersion,
                className: "xs_hide",
                url: "http:/metagenomics/pipelines/" + entry.pipelineVersion,
            },

        ];
        addTableRow(rowData, table);
    }

    container.appendChild(table);
};

var displaySearchResults = function(data, dataType) {
    displayData = data[dataType];
    resultsContainer = document.getElementById(dataType + "-searchData");
    if (resultsContainer != null) {
        resultsTitle = document.createElement("h3");
        resultsTitle.innerHTML = "Showing " + displayData.entries.length
                                    + " out of " + displayData.numberOfHits + " results";
        resultsContainer.appendChild(resultsTitle);
        if (dataType == "projects") {
            displayProjectTable(displayData, resultsContainer);
        } else if (dataType == "samples") {
            displaySampleTable(displayData, resultsContainer);
        } else if (dataType == "runs") {
            displayRunTable(displayData, resultsContainer);
        } else {
            console.log("Unknown data type " + dataType);
        }

    } else {
        console.log("Expected to find div with id '" + dataType + "-searchData'");
    }
};

var displayData = function(data, tab) {

    for (i in data.dataTypes) {
        var dataType = data.dataTypes[i];
        var dataTypeContainer = document.getElementById(dataType);
        if (dataTypeContainer != null) {
            displayFacets(data[dataType].facets, dataType);
            displaySearchResults(data, dataType);
        } else {
            console.log("Expected to find div with id " + dataType);
        }
    }
    var tabContainer = displayTabHeader(data);
}

/*
 Handle processing of search results returned in JSP
 */
var searchResultDiv = document.getElementById("SearchResultsDiv");
if (searchResultDiv != null) {
    var searchValue = searchResultDiv.innerHTML;
    var searchResults = JSON.parse(searchValue);
    displayData(searchResults);
}


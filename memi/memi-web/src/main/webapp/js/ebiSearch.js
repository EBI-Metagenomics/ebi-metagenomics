/**
 * Created by maq on 06/04/2016.
 */

var HIDDEN_CLASS = "this_hide";
var FACET_SEPARATOR = "____";
var SEARCH_TAB_CLASS = "search-tab";

//load css for the modal box
var linkElement = document.createElement("link");
linkElement.rel = "stylesheet";
linkElement.href = "/metagenomics/css/ajax-modal.css"; //Replace here
document.head.appendChild(linkElement);


/*
Behaviour methods
 */

/**
 * reset page numbering for datatype div to first page
 * @param dataType
 */
var resetPage = function(dataType) {
    console.log("Resetting page for " + dataType);
}

/**
 * Fetches new data for datatype via ajax
 * @param dataType
 */
var fetchDataViaAjax = function(dataType, page) {
    console.log("Fetching ajax data for " + dataType);
    var searchForm = document.getElementById("local-search");
    if (searchForm != null) {
        //setup the query object
        var searchText = document.getElementById("local-searchbox").value;
        var checkedFacets = [];
        var facetContainer = document.getElementById(dataType + "-searchFacets");
        if (facetContainer != null) {
            var facetElements = facetContainer.getElementsByTagName("input");
            for (var i=0; i < facetElements.length; i++) {
                var facetElement = facetElements[i];
                if (facetElement.name === 'facets' && facetElement.checked) {
                    checkedFacets.push(facetElement.value);
                }
            }
        } else {
            console.log("Expect to find div id " + dataType + "-searchFacets on page");
        }
        console.log("SearchText = " + searchText);
        var query = {
            searchText: searchText,
            searchType: dataType
        };
        query[dataType] = {
            checkedFacets: checkedFacets,
            page: page,

        };
        var jsonQuery = JSON.stringify(query);

        //setup the request
        var httpReq = new XMLHttpRequest();
        var url = "/metagenomics/search/doAjaxSearch";
        httpReq.open("POST", url);
        httpReq.setRequestHeader("Content-type", "application/json");
        httpReq.setRequestHeader("Accept", "application/json");

        //handle response
        httpReq.onreadystatechange = function(event) {
            if (httpReq.readyState == XMLHttpRequest.DONE) {
                document.getElementById("spinnerDiv").style.display = "none";
                var readyState = httpReq.readyState;
                var response = httpReq.response;
                var data = JSON.parse(response);
                if (data.error != null) {
                    var error = data.error;
                    console.log("Got error: " + error);
                }
                displayFacets(data[dataType].facets, dataType, checkedFacets);
                displaySearchResults(data, dataType);
                displayPagination(data, dataType);
                displayTabHeader(data);
                var tabLink = document.getElementById(dataType + "-link");
                setTabText(data, dataType, tabLink);
            }
        };

        //error handling
        httpReq.addEventListener("error", function(event){
            document.getElementById("spinnerDiv").style.display = "none";
            console.log("Ajax error");
            searchForm.submit();
        });

        document.getElementById("spinnerDiv").style.display = "block";
        httpReq.send(jsonQuery);

    } else {
        console.log("This page should contain 'local-search'");
    }
}

/*
Initialisation methods
 */

/**
 * Adds a jquery ui tab set to the supplied div
 * @param container
 * @param disabledList
 */
var setupJQueryTabs = function(container, disabledList) {
    selectedTab = 0;
    var children = container.getElementsByClassName(SEARCH_TAB_CLASS);
    console.log("Children " + children.length);

    for (var i=0; i < children.length; i++) {
        console.log("Checking " + i);
        if (disabledList.indexOf(i) == -1) {
            console.log("Disabled " + i + " in " + disabledList);
            selectedTab = i;
            break;
        }
    }

    console.log("Selectedtab " + selectedTab);
    $(container).tabs({
        disabled: disabledList,
        active: selectedTab
    });




};

var setTabText = function(data, dataType, element) {
    var titleText = dataType.charAt(0).toUpperCase() + dataType.slice(1); //capitalise first letter
    titleText += " (" + data[dataType].numberOfHits + ")";
    element.innerHTML = titleText;
};

/**
 * displays a set of tabs based on the datatypes property of the data
 * @param data
 * @returns {Element}
 */
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
                var tabItem = document.createElement("li");
                tabItem.className = SEARCH_TAB_CLASS;
                var tabLink = document.createElement("a");
                tabLink.id = dataType + "-link";
                tabLink.value = dataType;
                tabLink.href = "#" + dataType;
                setTabText(data, dataType, tabLink);

                if (data[dataType].numberOfHits == 0) {
                    disabledList.push(parseInt(i));
                }
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

/**
 * Creates title and set of checkbox inputs for a supplied facet groups
 * @param facetGroup
 * @param container
 * @param dataType
 */
var displayFacetGroup = function(facetGroup, container, dataType, checkedFacets) {
    facetGroupTitle = document.createElement("h4");
    facetGroupTitle.innerHTML = facetGroup.label;
    container.appendChild(facetGroupTitle);
    for (i in facetGroup.values) {
        facet = facetGroup.values[i];

        facetDiv = document.createElement("div");
        facetInput = document.createElement("input");
        facetInput.id = facet.type + i;
        facetInput.name = "facets";
        facetInput.form = "local-search";
        facetInput.type = "checkbox";
        facetInput.value = facet.type + FACET_SEPARATOR + facet.value;
        if (checkedFacets != null && checkedFacets.indexOf(facetInput.value) >= 0) {
            facetInput.checked = true;
        }
        facetInput.addEventListener("change", function(event) {
            fetchDataViaAjax(dataType, 1);
        });

        facetLabel = document.createElement("label");
        facetLabel.htmlFor = facetInput.id;
        facetLabel.innerHTML = facet.label + " (" + facet.count + ")";

        facetDiv.appendChild(facetInput);
        facetDiv.appendChild(facetLabel);

        facetContainerDiv = document.createElement("div");
        facetContainerDiv.className = "extra-pad";
        facetContainerDiv.appendChild(facetDiv);

        container.appendChild(facetContainerDiv);
    }
};

/**
 * Fills in facet values for divs corresponding to each datatype
 * @param facetGroups
 * @param dataType
 */
var displayFacets = function(facetGroups, dataType, checkedFacets) {
    var facetContainer = document.getElementById(dataType + "-searchFacets");
    if (facetContainer != null) {
        facetContainer.innerHTML = ""; //clear out old facets
        facetContainerTitle = document.createElement("h3");
        facetContainerTitle.innerHTML = "Filter your results";

        facetContainer.appendChild(facetContainerTitle);
        for (var i =0; i < facetGroups.length; i++) {
            var facetGroup = facetGroups[i];
            displayFacetGroup(facetGroup, facetContainer, dataType, checkedFacets);
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

/**
 * Setup for project table
 * @param data
 * @param container
 */
var displayProjectTable = function(data, container) {
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

    for (i in data.entries) {
        var entry = data.entries[i];
        var rowData = [
            {
                name: entry.identifier,
                url: "http:/metagenomics/projects/" + entry.identifier
            },
            {name: entry.name},
            {name: entry.biomes[0], className: "xs_hide"},
            {name: entry.description, className: "xs_hide"}
        ];
        addTableRow(rowData, table);
    }

    container.appendChild(table);
};

/**
 * Setup for sample table
 * @param data
 * @param container
 */
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

/**
 * setup run table
 * @param data
 * @param container
 */
var displayRunTable = function(data, container) {
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
                name: entry.sample,
                className: "xs_hide",
                url: "http:/metagenomics/projects/ "
                + entry.project + "/samples/"
                + entry.sample,

            },
            {
                name: entry.project,
                className: "xs_hide",
                url: "http:/metagenomics/projects/ " + entry.project,
            },
            {
                name: entry.experimentType
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

/**
 * Displays table data for each datatype
 * @param data
 * @param dataType
 */
var displaySearchResults = function(data, dataType) {
    displayAllData = data[dataType];
    resultsContainer = document.getElementById(dataType + "-searchData");
    if (resultsContainer != null) {
        resultsContainer.innerHTML = ""; //clear results div
        resultsTitle = document.createElement("h3");
        resultsTitle.innerHTML = "Showing " + displayAllData.entries.length
                                    + " out of " + displayAllData.numberOfHits + " results";
        resultsContainer.appendChild(resultsTitle);
        if (dataType == "projects") {
            displayProjectTable(displayAllData, resultsContainer);
        } else if (dataType == "samples") {
            displaySampleTable(displayAllData, resultsContainer);
        } else if (dataType == "runs") {
            displayRunTable(displayAllData, resultsContainer);
        } else {
            console.log("Unknown data type " + dataType);
        }

    } else {
        console.log("Expected to find div with id '" + dataType + "-searchData'");
    }
};

var displayPagination = function(data, dataType) {
    console.log("Adding pagination for " + dataType);
    paginationContainer = document.getElementById(dataType + "-searchPagination");
    if (paginationContainer != null) {
        paginationContainer.innerHTML = "";
        var prevButton = document.createElement("input");
        prevButton.type = "button";
        prevButton.id = dataType + "-prevPage";
        prevButton.value = "Previous";
        if (data[dataType].page <= 1 ) {
            prevButton.disabled = true;
        }
        prevButton.addEventListener('click', function(event) {
            fetchDataViaAjax(dataType, (data[dataType].page - 1));
        });

        var nextButton = document.createElement("input");
        nextButton.type = "button";
        nextButton.id = dataType + "-nextPage";
        nextButton.value = "Next";
        if (data[dataType].page >= data[dataType].maxPage) {
            nextButton.disabled = true;
        }
        nextButton.addEventListener('click', function(event) {
            fetchDataViaAjax(dataType, (data[dataType].page + 1));
        });

        var textSpan = document.createElement("span");
        textSpan.textContent = " Page " + data[dataType].page + " of " + data[dataType].maxPage + " ";
        paginationContainer.appendChild(prevButton);
        paginationContainer.appendChild(textSpan);
        paginationContainer.appendChild(nextButton);
    } else {
        console.log("Expected to find div with id '" + dataType + "-searchPagination'");
    }

};

/**
 * Displays facets and data table for each datatype
 * @param data
 * @param tab
 */
var displayAllData = function(data, tab) {
    var tabContainer = displayTabHeader(data);
    for (i in data.dataTypes) {
        var dataType = data.dataTypes[i];
        var dataTypeContainer = document.getElementById(dataType);
        if (dataTypeContainer != null) {
            displayFacets(data[dataType].facets, dataType);
            displaySearchResults(data, dataType);
            displayPagination(data, dataType);
        } else {
            console.log("Expected to find div with id " + dataType);
        }
    }
}

/**
 * Handle processing of search results returned in JSP
 */
var searchResultDiv = document.getElementById("SearchResultsDiv");
if (searchResultDiv != null) {
    var searchValue = searchResultDiv.innerHTML;
    var searchResults = JSON.parse(searchValue);
    displayAllData(searchResults);
}


/**
 * Created by maq on 06/04/2016.
 */

//"use strict";

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

var SettingsManager = function() {
    this.FACET_SEPARATOR = "____";
    this.SEARCH_TAB_CLASS = "search-tab";

    this.FACET_SOURCE = "Source";

    this.GLOBAL_SEARCH_SETTINGS = {
        PROJECT_RESULTS_NUM: 10,
        SAMPLE_RESULTS_NUM: 10,
        RUN_RESULTS_NUM: 20,
        DEFAULT_SEARCH_START: 0,
        FACET_NUM: 10,
        DEFAULT_FACET_DEPTH: 2,
        DEFAULT_MORE_FACETS_DEPTH: 10,

        PROJECT: "Projects",
        PROJECT_DOMAIN: "metagenomics_projects",
        PROJECT_FIELDS: "id,name,description,biome_name,METAGENOMICS_SAMPLES",
        SAMPLE: "Samples",
        SAMPLE_DOMAIN: "metagenomics_samples",
        SAMPLE_FIELDS: "id,name,description,experiment_type,METAGENOMICS_PROJECTS",
        RUN: "Runs",
        RUN_DOMAIN: "metagenomics_runs",
        RUN_FIELDS: "id,experiment_type,pipeline_version,METAGENOMICS_SAMPLES,METAGENOMICS_PROJECTS",
        METAGENOMICS_SEARCH_TEXT : "www.ebi.ac.uk.metagenomics.searchsettings",
        METAGENOMICS_SEARCH_SETTINGS : "www.ebi.ac.uk.metagenomics.searchsettings",
        METAGENOMICS_SEARCH_SETTINGS_SELECTED_TAB: "www.ebi.ac.uk.metagenomics.selectedFacet",
        MODAL_OVERLAY_ID: "modal-overlay-div",
        MORE_FACET_INPUT_CLASS: "more-facet-input",
        MORE_FACET_TEXT_FILTER_CLASS: "more-facet-text-filter",
        MORE_FACET_CONTENT_CLASS: "more-facet-content",
        HIERARCHICAL_FACET_CLASS: "hierarchical-facet-list",
        HOMEPAGE_LINK_CLASS: "homepage-search-link",

        SEARCH_BOX_ID: "local-searchbox",
        SEARCH_BOX_SMALL_ID: "local-searchbox-xs",
        SEARCH_RESULTS_ID: "searchTabs",
        SPINNER_ID: "spinner",
        SPINNER_OVERLAY_ID: "search-overlay",

        HOMEPAGE_STATS_ID: "stat-public"
    };

    this.DatatypeSettings = {};
    this.DatatypeSettings.DATA_TYPES = {};
    this.DatatypeSettings.DATA_TYPES = [
        this.GLOBAL_SEARCH_SETTINGS.PROJECT,
        this.GLOBAL_SEARCH_SETTINGS.SAMPLE,
        this.GLOBAL_SEARCH_SETTINGS.RUN
    ];

    this.areCssVariablesSupported = function() {
        return window.CSS && window.CSS.supports && window.CSS.supports('--fake-var', 0);
    };

    this.areSettingsInitialisted = function() {
        var areSettingsReady = true;
        for(var i = 0; i < this.DatatypeSettings.DATA_TYPES.length; i++) {
            var dataType = this.DatatypeSettings.DATA_TYPES[i];
            var settings = this.getSearchSettings(dataType);
            if (settings == null) {
                areSettingsReady = false;
            }
        }
        return areSettingsReady;
    };

    this.getEBISearchURL = function() {
        var EBISEARCH_PATH = "ebisearch/ws/rest/";
        var host = window.location.hostname;
        var port = window.location.port;
        var protocol = "https";

        if (host === "localhost" && port == 8000) {
            host = "wwwdev.ebi.ac.uk";
        }
        var searchURL = protocol + "://" + host + "/" + EBISEARCH_PATH;
        return searchURL;
    };

    this.initialiseSettings = function(fullReset) {
        var projectSettings = this.getSearchSettings(this.GLOBAL_SEARCH_SETTINGS.PROJECT);
        var sampleSettings = this.getSearchSettings(this.GLOBAL_SEARCH_SETTINGS.SAMPLE);
        var runSettings = this.getSearchSettings(this.GLOBAL_SEARCH_SETTINGS.RUN);

        var sampleTemperature = new NumericalRangeField("temperature", "Temperature", "°C", -20, 110, -20, 110);
        var sampleDepth = new NumericalRangeField("depth", "Depth", "Metres", 0, 2000, 0, 2000);
        var samplePH = new NumericalRangeField("pH", "pH", null, 0, 14, 0, 14);

        var runTemperature = new NumericalRangeField("temperature", "Temperature", "°C", -20, 110, -20, 110);
        var runDepth = new NumericalRangeField("depth", "Depth", "Metres", 0, 2000, 0, 2000);
        var runPH = new NumericalRangeField("pH", "pH", null, 0, 14, 0, 14);

        if (fullReset || projectSettings == null) {
            projectSettings = new SearchSettings(
                this.GLOBAL_SEARCH_SETTINGS.PROJECT,
                this.GLOBAL_SEARCH_SETTINGS.PROJECT_RESULTS_NUM,
                this.GLOBAL_SEARCH_SETTINGS.DEFAULT_SEARCH_START,
                this.GLOBAL_SEARCH_SETTINGS.FACET_NUM,
                null,
                this.GLOBAL_SEARCH_SETTINGS.PROJECT_DOMAIN,
                this.GLOBAL_SEARCH_SETTINGS.PROJECT_FIELDS,
                null
            );
        }

        if (fullReset || sampleSettings == null) {
            sampleSettings = new SearchSettings(
                this.GLOBAL_SEARCH_SETTINGS.SAMPLE,
                this.GLOBAL_SEARCH_SETTINGS.SAMPLE_RESULTS_NUM,
                this.GLOBAL_SEARCH_SETTINGS.DEFAULT_SEARCH_START,
                this.GLOBAL_SEARCH_SETTINGS.FACET_NUM,
                null,
                this.GLOBAL_SEARCH_SETTINGS.SAMPLE_DOMAIN,
                this.GLOBAL_SEARCH_SETTINGS.SAMPLE_FIELDS,
                [sampleTemperature, sampleDepth]
            );
        }

        if (fullReset || runSettings == null) {
            runSettings = new SearchSettings(
                this.GLOBAL_SEARCH_SETTINGS.RUN,
                this.GLOBAL_SEARCH_SETTINGS.RUN_RESULTS_NUM,
                this.GLOBAL_SEARCH_SETTINGS.DEFAULT_SEARCH_START,
                this.GLOBAL_SEARCH_SETTINGS.FACET_NUM,
                null,
                this.GLOBAL_SEARCH_SETTINGS.RUN_DOMAIN,
                this.GLOBAL_SEARCH_SETTINGS.RUN_FIELDS,
                [runTemperature, runDepth]
            );
        }

        this.DatatypeSettings[this.GLOBAL_SEARCH_SETTINGS.PROJECT] = projectSettings;
        this.DatatypeSettings[this.GLOBAL_SEARCH_SETTINGS.SAMPLE] = sampleSettings;
        this.DatatypeSettings[this.GLOBAL_SEARCH_SETTINGS.RUN] = runSettings;
        var searchText = this.getSearchText();
        if (searchText == null) {
            searchText = "";
        }
        for(var i = 0; i < this.DatatypeSettings.DATA_TYPES.length; i++) {
            var settings = this.DatatypeSettings[this.DatatypeSettings.DATA_TYPES[i]];
            settings["searchText"] = searchText;
            this.setSearchText(searchText);
            this.setSearchSettings(this.DatatypeSettings.DATA_TYPES[i], settings);
        }

        return this.DatatypeSettings;
    };

    this.getSearchText = function() {
        var searchText = sessionStorage.getItem(this.GLOBAL_SEARCH_SETTINGS.METAGENOMICS_SEARCH_TEXT);
        return searchText;
    };

    this.setSearchText = function(term) {
        sessionStorage.setItem(this.GLOBAL_SEARCH_SETTINGS.METAGENOMICS_SEARCH_TEXT, term);
    };

    this.getSearchSettings = function(dataType) {
        var searchSettings = sessionStorage.getItem(this.GLOBAL_SEARCH_SETTINGS.METAGENOMICS_SEARCH_SETTINGS+dataType);
        if (searchSettings != null) {
            searchSettings = JSON.parse(searchSettings);
        }
        return searchSettings;
    };

    this.setSearchSettings = function(dataType, settings) {
        settingString = JSON.stringify(settings)
        sessionStorage.setItem(this.GLOBAL_SEARCH_SETTINGS.METAGENOMICS_SEARCH_SETTINGS+dataType, settingString);
    };

    this.getSelectedTab = function() {
        var selectedTab =  sessionStorage.getItem(this.GLOBAL_SEARCH_SETTINGS.METAGENOMICS_SEARCH_SETTINGS_SELECTED_TAB);
        return selectedTab;
    };

    this.setSelectedTab = function(selectedTab) {
        sessionStorage.setItem(this.GLOBAL_SEARCH_SETTINGS.METAGENOMICS_SEARCH_SETTINGS_SELECTED_TAB, selectedTab);
    };
};

var TableManager = function(searchManager, settingsManager) {

    this.searchManager = searchManager;
    this.settingsManager = settingsManager;

    /**
     * Setup for project table
     * @param data
     * @param container
     */
    this.displayProjectTable = function(results, container) {
        console.log("Showing project data");
        table = document.createElement("table");
        table.border = 1;
        table.classList.add("table-light");

        var headerData = this.getProjectHeader();

        this.addTableHeader(headerData, table);

        for (var i=0; i < results.entries.length; i++) {
            var entry = results.entries[i];
            var rowData = this.getProjectRow(entry);
            this.addTableRow(rowData, table);
        }
        container.appendChild(table);
    };

    /**
     * Returns object used for populating project table header
     * @returns {[*,*,*,*]}
     */
    this.getProjectHeader = function() {
        var headerData = [
            {name: "Project"},
            {name: "Name"},
            {name: "Biome", className: "xs_hide"},
            {name: "Description", className: "xs_hide"}
        ];
        return headerData;
    };

    /**
     * returns object representing row in project table
     * @param entry
     * @returns {[*,*,*,*]}
     */
    this.getProjectRow = function(entry) {
        var rowData = [
            {
                name: entry["id"],
                url: "http://" + window.location.host + "/metagenomics/projects/" + entry["id"]
            },
            {name: entry.fields.name[0]},
            {name: entry.fields.biome_name[0], className: "xs_hide"},
            {name: entry.fields.description[0], className: "xs_hide"}
        ];
        return rowData;
    };

    /**
     * Setup for sample table
     * @param results
     * @param container
     */
    this.displaySampleTable = function(results, container) {
        console.log("Showing sample data");
        table = document.createElement("table");
        table.border = 1;
        table.classList.add("table-light");

        var headerData = this.getSampleHeader();

        this.addTableHeader(headerData, table);

        for (var i=0; i < results.entries.length; i++) {
            var entry = results.entries[i];
            var rowData = this.getSampleRow(entry);
            this.addTableRow(rowData, table);
        }
        container.appendChild(table);
    };

    /**
     * Returns object used for populating sample table header
     * @returns {[*,*,*,*]}
     */
    this.getSampleHeader = function() {
        var headerData = [
            {name: "Sample"},
            {name: "Project", className: "xs_hide"},
            {name: "Name"},
            {name: "Description", className: "xs_hide"},
        ];
        return headerData;
    };

    /**
     * returns object representing row in sample table
     * @param entry
     * @returns {[*,*,*,*]}
     */
    this.getSampleRow = function(entry) {
        var rowData = [
            {
                name: entry["id"],
                url: "http://" + window.location.host + "/metagenomics/projects/" + entry["fields"]["METAGENOMICS_PROJECTS"][0] + "/samples/" +  entry["id"]
            },
            {
                name: entry["fields"]["METAGENOMICS_PROJECTS"][0],
                url: "http://" + window.location.host + "/metagenomics/projects/" + entry["fields"]["METAGENOMICS_PROJECTS"][0]
            },
            {name: entry.fields.name[0]},
            {name: entry.fields.description[0], className: "xs_hide"}
        ];
        return rowData;
    };


    /**
     * setup run table
     * @param results
     * @param container
     */
    this.displayRunTable = function(results, container) {
        console.log("Showing run data");
        table = document.createElement("table");
        table.border = 1;
        table.classList.add("table-light");

        var headerData = this.getRunHeader();

        this.addTableHeader(headerData, table);

        for (var i=0; i < results.entries.length; i++) {
            var entry = results.entries[i];
            var rowData = this.getRunRow(entry);
            this.addTableRow(rowData, table);
        }

        container.appendChild(table);
    };

    /**
     * Returns object used for populating run table header
     * @returns {[*,*,*,*]}
     */
    this.getRunHeader = function() {
        var headerData = [
            {name: "Run"},
            {name: "Sample", className: "xs_hide"},
            {name: "Project", className: "xs_hide"},
            {name: "Experiment Type"},
            {name: "Pipeline Version", className: "xs_hide"},
        ];
        return headerData;
    };

    /**
     * returns object representing row in run table
     * @param entry
     * @returns {[*,*,*,*]}
     */
    this.getRunRow = function(entry) {
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
        return rowData;
    };

    /**
     * Creates table header cells and table row
     * @param columns
     * @param table
     */
    this.addTableHeader = function(columns, table) {
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
    this.addTableRow = function(columns, table) {
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

    this.displayPagination = function(results, searchSettings) {
        var self = this;
        //console.log("Adding pagination for " + searchSettings.type);
        var dataType = searchSettings.type;

        var pageElements = ["header", "footer"];
        for (var i=0; i < pageElements.length; i++) {
            var element = pageElements[i];

            var paginationContainer = document.getElementById(dataType + "-" + element + "-searchPagination");
            if (paginationContainer != null) {
                paginationContainer.innerHTML = "";
                var prevButton = document.createElement("input");
                prevButton.type = "button";
                prevButton.id = element + "-" + dataType + "-prevPage";
                prevButton.value = "Previous";

                if (searchSettings.page <= 0 ) {
                    prevButton.disabled = true;
                }
                prevButton.addEventListener('click', function(event) {
                    searchSettings.page--;
                    self.searchManager.runDomainSearch(searchSettings);
                });

                var nextButton = document.createElement("input");
                nextButton.type = "button";
                nextButton.id = element + "-" + dataType + "-nextPage";
                nextButton.value = "Next";
                var maxPage = Math.ceil(results.hitCount/searchSettings.resultsNum);
                if ((searchSettings.page+1) >= maxPage) {
                    nextButton.disabled = true;
                }
                nextButton.addEventListener('click', function(event) {
                    searchSettings.page++;
                    self.searchManager.runDomainSearch(searchSettings);
                });

                var textContent = " Page " + (searchSettings.page+1) + " of " + (maxPage) + " ";
                var textNode = document.createTextNode(textContent);
                paginationContainer.appendChild(prevButton);
                paginationContainer.appendChild(textNode);
                paginationContainer.appendChild(nextButton);
            } else {
                console.log("Error: Expected to find div with id '" + dataType + "-" + element + "-searchPagination'");
            }

        }

    };

    this.displayDownloadButton = function(results, searchSettings) {
        var self = this;
        try {
            var isFileSaverSupported = !!new Blob;
            if (isFileSaverSupported) {
                var dataType = searchSettings.type;
                console.log("displayDownloadButton: " + dataType);

                var pageElements = ["header", "footer"];
                for (var i=0; i < pageElements.length; i++) {
                    var element = pageElements[i];
                    var downloadContainer = document.getElementById(dataType + "-" + element + "-download");
                    if (downloadContainer != null) {
                        var downloadID = dataType + "downloadButton";
                        var downloadButton = document.getElementById(downloadID);
                        if (downloadButton == null) {
                            downloadButton = document.createElement("input");
                            downloadButton.type = "button";
                            downloadButton.id = element + "-" + downloadID;
                            downloadButton.value = "Download";
                            downloadContainer.appendChild(downloadButton);
                            downloadButton.addEventListener("click", function (event) {
                                self.downloadSearchResults(results, searchSettings);
                            });
                        }
                    } else {
                    console.log("Error: Expected to find div with id '" + dataType + "-" + element + "'-download")
                }
                }



            }
        } catch (e) {
            console.log("Error: Blob type not supported consider upgrading to modern browser (Chrome or Firefox)");
        }
    };

    this.downloadSearchResults = function(results, searchSettings) {
        var dataType = searchSettings.type;

        var headerFunction;
        var rowFunction;
        if (dataType === this.settingsManager.GLOBAL_SEARCH_SETTINGS.PROJECT) {
            headerFunction = this.getProjectHeader;
            rowFunction = this.getProjectRow;
        } else if (dataType === this.settingsManager.GLOBAL_SEARCH_SETTINGS.SAMPLE) {
            headerFunction = this.getSampleHeader;
            rowFunction = this.getSampleRow;
        } else if (dataType === this.settingsManager.GLOBAL_SEARCH_SETTINGS.RUN) {
            headerFunction = this.getRunHeader;
            rowFunction = this.getRunRow;
        } else {
            console.log("Error: Unkown datatype");
        }

        var data = [];
        data.push(headerFunction());
        this.searchManager.runDownloadSearch(rowFunction, data, searchSettings, null);
    }
};

var FacetManager = function(settingsManager, searchManager) {
    this.searchManager = searchManager;
    this.settingsManager = settingsManager;

    this.isFacetGroupHierarchical = function(facetGroup) {
        var isHierarchical = false;
        for (var i=0; i < facetGroup.facetValues.length; i++) {
            if (facetGroup.facetValues[i].hasOwnProperty("children")) {
                isHierarchical = true;
                break;
            }
        }
        return isHierarchical;
    };

    this.addFacetValueChangeListener = function (facetInput, facetType, facet, searchSettings, bonsaiTreeID) {
        var self = this;
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
            self.searchManager.runDomainSearch(searchSettings);
        });
    };

    this.addClearFacetListener = function(searchSettings, facetGroup, element) {
        var self = this;
        element.addEventListener("click", function(event){
            if (searchSettings.facets.hasOwnProperty(facetGroup.id)
                && searchSettings.facets[facetGroup.id] != null
                && searchSettings.facets[facetGroup.id].length > 0) {
                searchSettings.facets[facetGroup.id] = [];
                self.searchManager.runDomainSearch(searchSettings);
            }
        });
    };


    this.addMoreFacetsListener = function (searchSettings, facetGroup, element, container, moreFacetsCallback) {
        var self = this;
        element.addEventListener("click", function(event){
            var facetCount = facetGroup.total;
            if (facetCount > 1000) {
                facetCount = 1000;
            }
            console.log("Fetching " + facetCount + " more facets");
            var parameters = {
                "query": encodeURIComponent("domain_source:" + searchSettings.domain),
                "format": "json",
                "size": 0,
                "facetfields": facetGroup.id,
                "facetcount": facetCount,
                "facetsdepth": self.settingsManager.GLOBAL_SEARCH_SETTINGS.DEFAULT_MORE_FACETS_DEPTH
            };

            var moreFacetsDiv = self.createMoreFacetsDialog(searchSettings, facetGroup);
            var modalOverlay = self.createModalOverlay(searchSettings, facetGroup);
            modalOverlay.appendChild(moreFacetsDiv);
            document.body.appendChild(modalOverlay);
            var paramFragment = self.searchManager.parametersToString(parameters);
            var url = self.settingsManager.getEBISearchURL() + searchSettings.domain + paramFragment;
            console.log("Getting more facets from: " + url);
            self.searchManager.runAjax("GET", "json", url, null, function(response) {
                //success
                var results = response;
                moreFacetsCallback(self, searchSettings, results, moreFacetsDiv, modalOverlay);
            }, function(event) {
                self.showMoreFacetsError(moreFacetsDiv);
            });

        });
    };

    this.createModalOverlay = function (searchSettings, facetGroup) {
        var modalOverlay = document.createElement("div");
        modalOverlay.id = this.settingsManager.GLOBAL_SEARCH_SETTINGS.MODAL_OVERLAY_ID;

        modalOverlay.style.position = "fixed";
        modalOverlay.style.zIndex = "91";
        modalOverlay.style.left = "0";
        modalOverlay.style.top = "0";
        modalOverlay.style.width = "100%";
        modalOverlay.style.height = "100%";
        modalOverlay.style.overflow = "auto";
        modalOverlay.style.backgroundColor = "rgba(0,0,0,0.6)";
        modalOverlay.style.userFocusPointer = "wait";
        //modalOverlay.style.opacity = "0.6";
        document.body.style.overflow = "hidden"; //this is reset when overlay is removed
        return modalOverlay;
    };

    this.removeModalOverlay = function() {
        var modalOverlay = document.getElementById(
            this.settingsManager.GLOBAL_SEARCH_SETTINGS.MODAL_OVERLAY_ID
        );
        if (modalOverlay != null) {
            var parent = modalOverlay.parentElement;
            parent.removeChild(modalOverlay);
        } else {
            console.log("Error: expected to find div with id=" + GLOBAL_SEARCH_SETTINGS.MODAL_OVERLAY_ID);
        }
        document.body.style.overflow = "auto";
    };

    this.createMoreFacetsDialog = function (searchSettings, facetGroup){
        var self = this;
        var dialogDiv = document.createElement("div");
        dialogDiv.style.backgroundColor = "rgb(255,255,255)";
        dialogDiv.style.margin = "5% auto";
        dialogDiv.style.padding = "20px";
        dialogDiv.style.border = "1px solid #888";
        dialogDiv.style.width = "80%";
        dialogDiv.style.height = "80%";
        dialogDiv.style.overflow = "hidden";
        dialogDiv.style.display = "block";

        var headerDiv = document.createElement("div");
        headerDiv.style.width = "100%";

        var title = document.createElement("h3");
        title.innerHTML = facetGroup.label;
        headerDiv.appendChild(title);

        var textFilter = document.createElement("input");
        textFilter.type = "text";
        textFilter.style.margin = "10px";
        textFilter.classList.add(self.settingsManager.GLOBAL_SEARCH_SETTINGS.MORE_FACET_TEXT_FILTER_CLASS);
        headerDiv.appendChild(textFilter);

        var facetsDiv = document.createElement("div");
        facetsDiv.classList.add(self.settingsManager.GLOBAL_SEARCH_SETTINGS.MORE_FACET_CONTENT_CLASS);
        facetsDiv.style.overflow = "scroll";
        facetsDiv.style.position = "relative";
        facetsDiv.style.width = "100%";
        facetsDiv.style.height = "80%";
        facetsDiv.appendChild(document.createTextNode("Loading facets"));

        textFilter.addEventListener("keyup", function(event){
            var listItems = facetsDiv.getElementsByTagName("li");
            for(var i=0; i < listItems.length; i++) {
                var listItem = listItems[i];
                var input = listItem.getElementsByTagName("input")[0];
                var filterText = textFilter.value;
                if (filterText != null && filterText != "") {
                    var listItemText = input.value;
                    if (listItemText.toLowerCase().indexOf(filterText.toLowerCase()) == -1) {
                        listItem.style.display = "none";
                    }
                } else {
                    listItem.style.display = "inline-block";
                }
            }
        });

        var footerDiv = document.createElement("div");
        footerDiv.height = "20%";
        var applyButton = document.createElement("input");
        applyButton.type = "button";
        applyButton.value = "Filter";
        applyButton.style.margin = "10px";
        applyButton.addEventListener("click", function(event){
            self.runMoreFacetsSearch(searchSettings, facetsDiv);
        });

        var cancelButton = document.createElement("input");
        cancelButton.type = "button";
        cancelButton.value = "Cancel";
        cancelButton.style.marginBottom = "10px";
        cancelButton.addEventListener(
            "click", function(event) {
                self.removeModalOverlay();
            }
        );

        footerDiv.appendChild(applyButton);
        footerDiv.appendChild(cancelButton);

        dialogDiv.appendChild(headerDiv);
        dialogDiv.appendChild(facetsDiv);
        dialogDiv.appendChild(footerDiv);
        return dialogDiv;
    };

    this.showMoreHierarchicalFacetsInDialog = function(self, searchSettings, results, container) {
        var dataType = searchSettings.type;
        var facets = results.facets[0];

        var contentDivs = container.getElementsByClassName(
            self.settingsManager.GLOBAL_SEARCH_SETTINGS.MORE_FACET_CONTENT_CLASS
        );
        var contentDiv = null;

        if (contentDivs != null && contentDivs.length == 1) {
            contentDiv = contentDivs[0];
        } else {
            console.log("Expect to find exactly one child div with class " +
                self.settingsManager.GLOBAL_SEARCH_SETTINGS.MORE_FACET_CONTENT_CLASS);
        }

        var textFilter = container.getElementsByClassName(
            self.settingsManager.GLOBAL_SEARCH_SETTINGS.MORE_FACET_TEXT_FILTER_CLASS
        );
        if (textFilter != null && textFilter.length == 1) {
            var textInput = textFilter[0];
            textInput.style.display = "none";
        } else {
            console.log("Expect to find exactly one child div with class " +
                self.settingsManager.GLOBAL_SEARCH_SETTINGS.MORE_FACET_TEXT_FILTER_CLASS);
        }

        var treeId = "more-hierarchical-facets-" + facets.id;
        contentDiv.innerHTML = "";
        contentDiv.classList.add(self.settingsManager.GLOBAL_SEARCH_SETTINGS.HIERARCHICAL_FACET_CLASS)
        var list = document.createElement("ul");
        list.id = treeId;
        self.addMoreHierarchicalFacetsToList(searchSettings, facets.facetValues, facets.id, null, list);
        contentDiv.appendChild(list);
        console.log("Converting more facet list to bonsai tree");

        $("#"+treeId).bonsai({
            checkboxes: true,
            expandAll: true,
            handleDuplicateCheckboxes: true
        });

    };

    this.addMoreHierarchicalFacetsToList = function(searchSettings, facets, facetGroupId, facetValuePrefix, list) {
        var dataType = searchSettings.type;
        var GLOBAL_SEARCH_SETTINGS = this.settingsManager.GLOBAL_SEARCH_SETTINGS;
        var FACET_SEPARATOR = this.settingsManager.FACET_SEPARATOR;
        for(var i=0; i < facets.length; i++) {
            var facet = facets[i];
            var listItem = document.createElement("li");

            var facetValue = facet.value;
            if (facetValuePrefix != null) {
                facetValue = facetValuePrefix + "/" + facet.value;
            }

            var facetInput = document.createElement("input");
            facetInput.type = "checkbox";
            facetInput.classList.add(GLOBAL_SEARCH_SETTINGS.MORE_FACET_INPUT_CLASS);
            facetInput.value = facetValue;
            if (searchSettings.facets != null
                && searchSettings.facets.hasOwnProperty(facetGroupId)
                && searchSettings.facets[facetGroupId].indexOf(facetInput.value) >= 0) {
                facetInput.checked = true;
            }
            facetInput.id = "morefacets" + FACET_SEPARATOR + dataType + FACET_SEPARATOR + facetGroupId + FACET_SEPARATOR + facetValue;

            var facetLabel = document.createElement("label");
            facetLabel.htmlFor = facetInput.id;
            facetLabel.innerHTML = facet.label + " (" + facet.count + ")";
            listItem.appendChild(facetInput);
            listItem.appendChild(facetLabel);
            list.appendChild(listItem);

            if (facet.hasOwnProperty("children")
                && facet.children != null
                && facet.children.length > 0) {
                var subList = document.createElement("ul");
                listItem.appendChild(subList);
                this.addMoreHierarchicalFacetsToList(searchSettings, facet.children, facetGroupId, facetValue, subList);
            }
        }
    };

    this.showMoreFacetsInDialog = function(self, searchSettings, results, container) {
        var dataType = searchSettings.type;
        var facets = results.facets[0];
        var contentDivs = container.getElementsByClassName(
            self.settingsManager.GLOBAL_SEARCH_SETTINGS.MORE_FACET_CONTENT_CLASS
        );
        var contentDiv = null;

        if (contentDivs != null && contentDivs.length == 1) {
            contentDiv = contentDivs[0];
        } else {
            console.log("Expect to find exactly one child div with class "
                + self.settingsManager.GLOBAL_SEARCH_SETTINGS.MORE_FACET_CONTENT_CLASS);
        }

        contentDiv.innerHTML = "";
        var list = document.createElement("ul");
        list.style.listStyle = "None";

        var FACET_SEPARATOR = self.settingsManager.FACET_SEPARATOR;
        for(var i=0; i < facets.facetValues.length; i++) {
            var facet = facets.facetValues[i];
            //prefixing id with 'morefacets' to ensure input id is unique
            var identifier = "morefacets" + FACET_SEPARATOR + dataType + FACET_SEPARATOR + facets.id + FACET_SEPARATOR + facet.value;

            var listItem = document.createElement("li");
            listItem.style.display = "inline-block";
            listItem.style.width = "350px";
            listItem.style.padding = "5px";

            list.appendChild(listItem);
            var facetInput = document.createElement("input");
            facetInput.id = identifier;
            facetInput.type = "checkbox";
            facetInput.classList.add(self.settingsManager.GLOBAL_SEARCH_SETTINGS.MORE_FACET_INPUT_CLASS);
            facetInput.value = facet.label;
            if (searchSettings.facets != null
                && searchSettings.facets.hasOwnProperty(facets.id)
                && searchSettings.facets[facets.id].indexOf(facetInput.value) >= 0) {
                facetInput.checked = true;
            }
            var facetLabel = document.createElement("label");
            facetLabel.htmlFor = facetInput.id;
            facetLabel.innerHTML = facet.label + " (" + facet.count + ")";

            listItem.appendChild(facetInput);
            listItem.appendChild(facetLabel);

        }
        contentDiv.appendChild(list);
    };

    this.showMoreFacetsError = function(container) {

    };

    this.runMoreFacetsSearch = function(searchSettings, container) {
        var facetInputs = document.getElementsByClassName(
            this.settingsManager.GLOBAL_SEARCH_SETTINGS.MORE_FACET_INPUT_CLASS
        );
        for (var i=0; i < facetInputs.length; i++) {
            var checkbox = facetInputs[i];
            var tokens = checkbox.id.split(this.settingsManager.FACET_SEPARATOR);
            var facetType = tokens[2];
            var facetValue = tokens[3];
            if (!searchSettings.facets.hasOwnProperty(facetType)) {
                searchSettings.facets[facetType] = [];
            }

            if (checkbox.checked) {
                console.log("Checkbox: " + checkbox.value + " checked " + facetType + " = " + facetValue);
                if (searchSettings.facets[facetType].indexOf(facetValue) == -1) {
                    searchSettings.facets[facetType].push(facetValue);
                }
            } else {
                var facetValueIndex = searchSettings.facets[facetType].indexOf(facetValue);
                if (facetValueIndex != -1) {
                    searchSettings.facets[facetType].splice(facetValueIndex, 1);
                }
            }
        }
        this.removeModalOverlay();
        this.searchManager.runDomainSearch(searchSettings);
    };

    this.displayFacetGroup = function(facetGroup, container, searchSettings) {
        var dataType = searchSettings.type;
        var facetGroupContainer = document.createElement("div");
        var groupContainerId = dataType + this.settingsManager.FACET_SEPARATOR + facetGroup.id;
        facetGroupContainer.id = groupContainerId;
        container.appendChild(facetGroupContainer);

        var facetGroupTitle = document.createElement("h4");
        facetGroupTitle.innerHTML = facetGroup.label;
        facetGroupContainer.appendChild(facetGroupTitle);
        for (var i=0; i < facetGroup.facetValues.length; i++) {
            var facet = facetGroup.facetValues[i];
            var identifier = dataType + this.settingsManager.FACET_SEPARATOR
                + facetGroup.id + this.settingsManager.FACET_SEPARATOR + facet.value;
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

            this.addFacetValueChangeListener(facetInput, facetGroup.id, facet, searchSettings, null);

            var facetLabel = document.createElement("label");
            facetLabel.htmlFor = facetInput.id;
            facetLabel.innerHTML = facet.label + " (" + facet.count + ")";

            facetItem.appendChild(facetInput);
            facetItem.appendChild(facetLabel);

            var facetContainerDiv = document.createElement("div");
            facetContainerDiv.classList.add("extra-pad");
            facetContainerDiv.appendChild(facetItem);

            facetGroupContainer.appendChild(facetContainerDiv);
        }
        var extraControlsDiv = document.createElement("div");
        extraControlsDiv.classList.add("extra-pad");

        if (facetGroup.total > 10) {
            var moreFacetsLink = document.createElement("a");
            moreFacetsLink.innerHTML = "More...";
            extraControlsDiv.appendChild(moreFacetsLink);
            this.addMoreFacetsListener(searchSettings, facetGroup, moreFacetsLink,
                facetGroupContainer, this.showMoreFacetsInDialog);
        }

        if (searchSettings.facets.hasOwnProperty(facetGroup.id)
            && searchSettings.facets[facetGroup.id] != null
            && searchSettings.facets[facetGroup.id].length > 0) {
            if (facetGroup.total > this.settingsManager.GLOBAL_SEARCH_SETTINGS.FACET_NUM) {
                var textNode = document.createTextNode(" | ");
                extraControlsDiv.appendChild(textNode);
            }

            var clearFacetsLink = document.createElement("a");
            clearFacetsLink.innerHTML = "Clear Selection";
            extraControlsDiv.appendChild(clearFacetsLink);
            this.addClearFacetListener(searchSettings, facetGroup, clearFacetsLink);
        }

        facetGroupContainer.appendChild(extraControlsDiv);
    };

    this.displayHierarchicalFacetGroup = function(facetGroup, container, searchSettings) {
        var dataType = searchSettings.type;
        var facetGroupContainer = document.createElement("div");
        var groupContainerId = dataType + this.settingsManager.FACET_SEPARATOR + facetGroup.id;
        facetGroupContainer.classList.add(this.settingsManager.GLOBAL_SEARCH_SETTINGS.HIERARCHICAL_FACET_CLASS);
        //facetGroupContainer.id = groupContainerId;
        container.appendChild(facetGroupContainer);

        var facetGroupTitle = document.createElement("h4");
        facetGroupTitle.innerHTML = facetGroup.label;
        facetGroupTitle.innerHTML = facetGroup.label;
        facetGroupContainer.appendChild(facetGroupTitle);

        var facetGroupList = document.createElement("ul");
        facetGroupList.id = groupContainerId;
        facetGroupContainer.appendChild(facetGroupList);
        console.log("Converting facet list to bonsai tree");
        $("#"+groupContainerId).bonsai({
            checkboxes: true,
            handleDuplicateCheckboxes: true
        });
        for (var i=0; i < facetGroup.facetValues.length; i++) {
            var facet = facetGroup.facetValues[i];
            var identifier = dataType + this.settingsManager.FACET_SEPARATOR
                + facetGroup.id + this.settingsManager.FACET_SEPARATOR + facet.value;
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

            this.addFacetValueChangeListener(facetInput, facetGroup.id, facet, searchSettings, groupContainerId);

            facetItem.appendChild(facetInput);
            facetItem.appendChild(document.createTextNode(facet.label + " (" + facet.count + ")"));

            if (facet.children != null) {
                var facetChildList = document.createElement("ul");
                this.displayHierarchicalChildren(facetChildList, facet, facetGroup, facetInput.value, searchSettings, groupContainerId);
                facetItem.appendChild(facetChildList);
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

        var extraControlsDiv = document.createElement("div");
        extraControlsDiv.classList.add("extra-pad");
        var moreFacetsLink = document.createElement("a");
        moreFacetsLink.innerHTML = "More...";
        extraControlsDiv.appendChild(moreFacetsLink);
        facetGroupContainer.appendChild(extraControlsDiv);

        this.addMoreFacetsListener(searchSettings, facetGroup, moreFacetsLink,
            facetGroupContainer, this.showMoreHierarchicalFacetsInDialog);


        if (searchSettings.facets.hasOwnProperty(facetGroup.id)
            && searchSettings.facets[facetGroup.id] != null
            && searchSettings.facets[facetGroup.id].length > 0) {
            var textNode = document.createTextNode(" | ");
            extraControlsDiv.appendChild(textNode);

            var clearFacetsLink = document.createElement("a");
            clearFacetsLink.innerHTML = "Clear Selection";
            extraControlsDiv.appendChild(clearFacetsLink);
            this.addClearFacetListener(searchSettings, facetGroup, clearFacetsLink);
        }
    };

    this.displayHierarchicalChildren = function(container, facet, facetGroup, parentPath, searchSettings, bonsaiTreeID) {
        var dataType = searchSettings.type;
        var children = facet.children;
        console.log("Facet with children: " + children.length);
        for (var i = 0; i < children.length; i++) {
            var childFacet = children[i];
            //console.log("Child facet: " + facetGroup.id + " name: " + childFacet.label);

            var value = parentPath + "/" + childFacet.value;
            var identifier = dataType + this.settingsManager.FACET_SEPARATOR
                + facetGroup.id + this.settingsManager.FACET_SEPARATOR + value;

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

            this.addFacetValueChangeListener(facetInput, facetGroup.id, facet, searchSettings, bonsaiTreeID);

            facetItem.appendChild(facetInput);
            facetItem.appendChild(document.createTextNode(childFacet.label + " (" + childFacet.count + ")"));
            container.appendChild(facetItem);

            if (childFacet.children != null) {
                var facetChildList = document.createElement("ul");
                this.displayHierarchicalChildren(facetChildList, childFacet, facetGroup, facetInput.value, searchSettings, bonsaiTreeID);
                facetItem.appendChild(facetChildList);
            }
        }
    };

    this.displayFacets = function(facetGroups, searchSettings) {
        var dataType = searchSettings.type;
        var facetContainer = document.getElementById(dataType + "-searchFacets");
        if (facetContainer != null) {
            facetContainer.innerHTML = ""; //clear out old facets
            var facetContainerTitle = document.createElement("h3");
            facetContainerTitle.innerHTML = "Filter your results";

            facetContainer.appendChild(facetContainerTitle);
            if (searchSettings.hasOwnProperty("numericalFields")
                && searchSettings.numericalFields != null) {
                this.displayNumericalInputs(facetContainer, searchSettings);
            }
            for (var i =0; i < facetGroups.length; i++) {
                var facetGroup = facetGroups[i];
                if (facetGroup.label !== this.settingsManager.FACET_SOURCE) {
                    if (this.isFacetGroupHierarchical(facetGroup)) {
                        this.displayHierarchicalFacetGroup(facetGroup, facetContainer, searchSettings);
                        //console.log("FacetGroup Hierarchical " + facetGroup.label);
                    } else {
                        this.displayFacetGroup(facetGroup, facetContainer, searchSettings);
                        //console.log("FacetGroup " + facetGroup.label);
                    }

                }
            }

            var searchInfoP = document.createElement("p");
            var searchInfoSmall = document.createElement("small");
            searchInfoSmall.classList.add("text-muted");
            searchInfoSmall.innerHTML = "Powered by ";
            searchInfoSmall.innerHTML += '<a href="http://www.ebi.ac.uk/ebisearch/" class="ext" target="_blank">EBI Search</a>';
            searchInfoP.appendChild(searchInfoSmall);
            facetContainer.appendChild(document.createElement("hr"));
            facetContainer.appendChild(searchInfoP);
        } else {
            console.log("Error: Expected to find div with id '" + dataType + "-searchFacets'");
        }
    };

    this.displayNumericalInputs = function(container, searchSettings) {
        var rangeContainerDiv = document.createElement("div");
        container.appendChild(rangeContainerDiv);

        for (var i = 0; i < searchSettings.numericalFields.length; i++) {

            var numericalField = searchSettings.numericalFields[i];
            var fieldContainer = document.createElement("div");
            fieldContainer.id = searchSettings.type + this.settingsManager.FACET_SEPARATOR + numericalField.name;
            fieldContainer.style.textAlign = "center";

            rangeContainerDiv.appendChild(fieldContainer);
            var rangeTitle = document.createElement("h4");
            rangeTitle.innerHTML = numericalField.displayName;
            if (numericalField.unit != null) {
                rangeTitle.innerHTML += " (" + numericalField.unit + ")"
            }
            rangeTitle.style.textAlign = "left";
            fieldContainer.appendChild(rangeTitle);

            var rangeContainer = document.createElement("span");
            rangeContainer.style.paddingLeft = "5px";
            rangeContainer.style.paddingRight = "5px";
            rangeContainer.style.paddingBottom = "10px";
            rangeContainer.style.display = "inline-block";

            var rangeInput = document.createElement("input");
            rangeInput.type = "range";
            rangeInput.setAttribute("multiple", "");
            rangeInput.setAttribute("min", numericalField.minimum);
            rangeInput.setAttribute("max", numericalField.maximum);
            var selectedRange = numericalField.selectedMinimum + "," + numericalField.selectedMaximum;

            if(this.settingsManager.areCssVariablesSupported()) {
                rangeInput.setAttribute("value", selectedRange);
                rangeContainer.appendChild(rangeInput);

                var minText = document.createElement("span");
                minText.appendChild(document.createTextNode(numericalField.minimum));
                minText.style.float = "left";
                var maxText = document.createElement("span");
                maxText.appendChild(document.createTextNode(numericalField.maximum));
                maxText.style.float = "right";

                multirange(rangeInput); //requires multirange library loaded

                fieldContainer.appendChild(minText);
                fieldContainer.appendChild(rangeContainer);
                fieldContainer.appendChild(maxText);
            }

            var minInput = document.createElement("input");
            minInput.placeholder = numericalField.minimum;
            minInput.style.type = "number";
            minInput.style.width = "5em";
            minInput.style.float = "left";
            minInput.classList.add("min-input");

            var maxInput = document.createElement("input");
            maxInput.placeholder = numericalField.maximum;
            maxInput.style.type = "number";
            maxInput.style.width = "5em";
            maxInput.style.float = "right";
            maxInput.classList.add("max-input");

            var inputBoxContainer = document.createElement("fieldset");
            inputBoxContainer.style.textAlign = "center";
            inputBoxContainer.appendChild(minInput);
            inputBoxContainer.appendChild(document.createTextNode(" to "));
            inputBoxContainer.appendChild(maxInput);

            var selectedRangeContainer = document.createElement("div");
            selectedRangeContainer.name = "selected-range";
            selectedRangeContainer.style["text-align"] = "left";
            if (numericalField.selectedMinimum != numericalField.minimum) {
                minInput.value = numericalField.selectedMinimum;
            }
            if (numericalField.selectedMaximum != numericalField.maximum) {
                maxInput.value = numericalField.selectedMaximum;
            }
            fieldContainer.appendChild(selectedRangeContainer);
            fieldContainer.appendChild(inputBoxContainer);

            this.addNumericalFieldValueChangeListener(rangeContainer, minInput, maxInput, rangeInput, numericalField, searchSettings);
        }
    };

    this.addNumericalFieldValueChangeListener = function(rangeContainer, minInput, maxInput, fieldInput, numericalField, searchSettings) {
        var self = this;
        rangeContainer.addEventListener("input", function(event) {
            self.updateTextBoxes(rangeContainer, minInput, maxInput, fieldInput, numericalField);
        });

        rangeContainer.addEventListener("change", function(event) {
            self.updateTextBoxes(rangeContainer, minInput, maxInput, fieldInput, numericalField);
            self.searchManager.runDomainSearch(searchSettings);
        });

        minInput.addEventListener("change", function(event) {
            var minValue = minInput.value;

            if (minValue != null
                && minValue != ""
                && minValue > numericalField.minimum) {
                numericalField.selectedMinimum = minValue;
            } else {
                numericalField.selectedMinimum = numericalField.minimum;
            }

            self.searchManager.runDomainSearch(searchSettings);
        });

        maxInput.addEventListener("change", function(event) {
            var maxValue = maxInput.value;

            if (maxValue != null
                && maxValue != ""
                && maxValue < numericalField.maximum) {
                numericalField.selectedMaximum = maxValue;
            } else {
                numericalField.selectedMaximum = numericalField.maximum;
            }


            self.searchManager.runDomainSearch(searchSettings);
        });
    };

    this.updateTextBoxes = function(container, minInput, maxInput, fieldInput, numericalField) {
        var tokens = fieldInput.value.split(",");
        numericalField.selectedMinimum = tokens[0];
        numericalField.selectedMaximum = tokens[1];

        console.log(numericalField.displayName + " range: " + tokens);

        //update text inputs
        if (numericalField.selectedMinimum != numericalField.minimum) {
            minInput.value = numericalField.selectedMinimum;
        } else {
            minInput.value = "";
            minInput.placeholder = numericalField.minimum;
        }
        if (numericalField.selectedMaximum != numericalField.maximum) {
            maxInput.value = numericalField.selectedMaximum;
        } else {
            maxInput.value = "";
            maxInput.placeholder = numericalField.maximum;
        }
    };
};

var TabManager = function() {
    /**
     * Adds a jquery ui tab set to the supplied div
     * @param container
     * @param disabledList
     */
    this.setupJQueryTabs = function(settingsManager, container, disabledList) {
        var selectedTab = settingsManager.getSelectedTab();
        if (selectedTab == null) {
            selectedTab = 0;
        }
        var children = container.getElementsByClassName(settingsManager.GLOBAL_SEARCH_SETTINGS.SEARCH_TAB_CLASS);

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
                settingsManager.setSelectedTab(selectedTab);
            }
        });
    };

    this.setTabText = function(hitCount, dataType, element) {
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
    this.displayTabHeader = function(settingsManager) {
        var tabContainer = document.getElementById("searchTabs");
        var datatypeSettings = settingsManager.DatatypeSettings;
        if (tabContainer != null) {
            var tabList = tabContainer.getElementsByTagName("ul");
            if (tabList == null || tabList.length <= 0) {
                tabList = document.createElement("ul");

                var dataTypes = datatypeSettings.DATA_TYPES;
                var disabledList = [];
                for (i in dataTypes) {
                    var dataType = dataTypes[i];
                    var tabItem = document.createElement("li");
                    tabItem.classList.add(settingsManager.GLOBAL_SEARCH_SETTINGS.SEARCH_TAB_CLASS);


                    var tabLink = document.createElement("a");
                    tabLink.id = dataType + "-link";
                    tabLink.value = dataType;
                    tabLink.href = "#" + dataType;
                    this.setTabText(null, dataType, tabLink);

                    /*
                     if (data != null && data[dataType].numberOfHits == 0) {
                     disabledList.push(parseInt(i));
                     }
                     */
                    tabItem.appendChild(tabLink)
                    tabList.appendChild(tabItem);
                }
                tabContainer.insertBefore(tabList, document.getElementById("tabDiv"));

                this.setupJQueryTabs(settingsManager, tabContainer, disabledList);
            } else {
                console.log("Tabs already exist");
            }

        } else {
            console.log("Expected to find div with id 'searchTabs'");
        }
        return tabContainer;
    };


};

var ResultsManager = function() {
    this.displayData = function(results, dataType, tableManager, settingsManager) {
        var hitNumText = document.getElementById(dataType + "-hitnum-text");
        if (hitNumText != null) {
            hitNumText.innerHTML = "";
            var resultsTitle = document.createElement("h3");
            if (results.hasOwnProperty("entries") && results.entries != null) {
                resultsTitle.innerHTML = "Showing " + results.entries.length
                    + " out of " + results.hitCount + " results";
                hitNumText.appendChild(resultsTitle);
            }
        } else {
            console.log("Error: Expected to find div with id '" + dataType + "-hitnum-text'");
        }

        var resultsContainer = document.getElementById(dataType + "-searchData");
        if (resultsContainer != null) {
            resultsContainer.innerHTML = ""; //clear results div
            if (results.hasOwnProperty("entries") && results.entries != null) {
                if (dataType == settingsManager.GLOBAL_SEARCH_SETTINGS.PROJECT) {
                    tableManager.displayProjectTable(results, resultsContainer);
                } else if (dataType == settingsManager.GLOBAL_SEARCH_SETTINGS.SAMPLE) {
                    tableManager.displaySampleTable(results, resultsContainer);
                } else if (dataType == settingsManager.GLOBAL_SEARCH_SETTINGS.RUN) {
                    tableManager.displayRunTable(results, resultsContainer);
                } else {
                    console.log("Error: DisplayDomainData - Unknown data type '" + searchSettings.type + "'");
                }
            } else {
                resultsTitle.innerHTML = "Search returned no results";
            }

        } else {
            console.log("Error: Expected to find div with id '" + dataType + "-searchData'");
        }
    };

    this.displayDomainData = function(response,
                                      searchSettings,
                                      settingsManager,
                                      tabManager,
                                      tableManager,
                                      facetManager) {
        console.log("displayDomain: " + searchSettings.type);

        var searchElementID = "local-searchbox";
        var searchElement = document.getElementById(searchElementID);
        var searchText = "";
        if (searchElement != null) {
            searchElement.value = searchSettings.searchText;
        } else {
            console.log("Error expected to find input with id " + searchElementID);
        }

        var results = response;
        console.log(
            "Search returned "
            + results.hitCount + " "
            + searchSettings.type + " results"
        );

        tabManager.setTabText(results.hitCount, searchSettings.type);
        this.displayData(results, searchSettings.type, tableManager, settingsManager);
        facetManager.displayFacets(results.facets, searchSettings);
        tableManager.displayPagination(results, searchSettings);
        tableManager.displayDownloadButton(results, searchSettings);
    };

    this.displaySearchError = function (httpReq, searchSettings, tabManager) {
        var dataType = searchSettings.type;
        tabManager.setTabText("Error", dataType);
        resultsContainer = document.getElementById(dataType + "-searchData");
        if (resultsContainer != null) {
            resultsContainer.innerHTML = "<div>A problem was encountered running the search. Please try again later.</div>";
            resultsContainer.innerHTML += "<div>If the problem persists please contact us <a href='/metagenomics/contact'>here</a></div>";
            resultsContainer.innerHTML += "<div>There are some known issues with Microsoft Internet Explorer.</div>";
            //resultsContainer.innerHTML += "<div>Please consider using Microsoft Edge, Chrome, Firefox or any other modern Browser.</div>";
        } else {
            console.log("Error: Expected to find div with id '" + dataType + "-searchData'");
        }
    };
};

var SearchManager = function(settingsManager, pageManager) {
    this.settingsManager = settingsManager;
    this.pageManager = pageManager;

    this.resetSettings = function() {
        var allSearchSettings = self.settingsManager.initialiseSettings(false);
        return allSearchSettings;
    };

    this.loadPageFromServer = function() {
        self = this;
        this.runAjax("GET", "document", "/metagenomics/search", null, function(response) {
            var response = response;
            console.log("Loading search template");

            document.innerHTML = response;
            self.pageManager.loadCss();
            self.pageManager.populateSearchInputs();
            self.pageManager.displayTabHeader(settingsManager);
            var allSearchSettings = self.resetSettings();
            self.runNewSearch(allSearchSettings);
        }, function(httpReq) {
            console.log("Error: Failed to load page template");
        });
    };

    this.runAjax = function(method,
                            responseType,
                            url,
                            parameters,
                            callback,
                            errCallback,
                            timeout,
                            timeoutCallback) {
        var self = this;
        var httpReq = new XMLHttpRequest();

        httpReq.open(method, url);

        if (responseType != null) {
            httpReq.responseType = responseType;

        }

        if (timeout != null) {
            httpReq.timeout = timeout;
            httpReq.ontimeout = function(event) {
                if (timeoutCallback) {
                    timeoutCallback(httpReq);
                } else {
                    console.log("Timeout occurred");
                }
            };
        }

        httpReq.send(parameters);

        //handle response
        httpReq.onload = function(event) {
            var readyState = httpReq.readyState;
            if (httpReq.status == 200) {
                self.fixInternetExplorerBug(httpReq, callback);
            } else {
                self.fixInternetExplorerBug(httpReq, errCallback);
            }
        };

        //error handling
        httpReq.onerror = function (event) {
            if (errCallback) {
                self.fixInternetExplorerBug(httpReq, errCallback);
            } else {
                console.log("Ajax error");
            }
        };
    };

    this.fixInternetExplorerBug = function(httpRes, callback) {
        //IE specific code. Grrrrr
        var response;
        if (typeof  httpRes.response === "string") {
            var response = JSON.parse(httpRes.response);
        } else {
            response = httpRes.response;
        }

        callback(response);
    };

    this.parametersToString = function(parameters) {
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
                    }
                }
                //remove final trailing comma ','
                facetString = facetString.substr(0, facetString.length - 1);
                if (facetString.length > 0) {
                    parameterString += type + "=" + facetString + "&";
                }
            } else {
                parameterString += type + "=" + value + "&";
            }

        }
        //remove final trailing ampersand '&'
        parameterString = parameterString.substr(0, parameterString.length - 1);
        return parameterString;
    }

    this.settingsToURL = function(searchSettings) {
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
            "facetsdepth": this.settingsManager.GLOBAL_SEARCH_SETTINGS.DEFAULT_FACET_DEPTH
        };

        if (searchSettings.facets != null) {
            parameters.facets = searchSettings.facets;
        }

        var paramFragment = this.parametersToString(parameters);
        var url = this.settingsManager.getEBISearchURL()  + searchSettings.domain + paramFragment;
        return url;
    };

    this.runDomainSearch = function(searchSettings) {
        var self = this;
        console.log("Searchtext = " + searchSettings.searchText);
        this.settingsManager.setSearchText(searchSettings.searchText);
        this.settingsManager.setSearchSettings(searchSettings.type, searchSettings);

        console.log("about to push state");
        //history.pushState(JSON.stringify(searchSettings), "search", "/metagenomics/search");

        self.pageManager.clearDomainResults(searchSettings);
        self.pageManager.showSpinner(searchSettings, false);
        var successCallback = function(httpReq) {
            self.pageManager.removeSpinner(searchSettings);
            self.pageManager.displayDomainData(httpReq, searchSettings);
        };
        var errorCallback = function(httpReq) {
            self.pageManager.removeSpinner(searchSettings);
            self.pageManager.displaySearchError(httpReq, searchSettings);
        };

        var url = self.settingsToURL(searchSettings);
        console.log("Running domain search = " + url);

        self.runAjax("GET", "json", url, null, successCallback, errorCallback);
    };

    this.runNewSearch = function(allSearchSettings, pageManager) {
        for(var i=0; i < allSearchSettings.DATA_TYPES.length; i++) {
            var dataType = allSearchSettings.DATA_TYPES[i];
            var searchSetting = allSearchSettings[dataType];
            this.runDomainSearch(searchSetting, pageManager);
        }
    };

    this.runDownloadSearch = function (rowFunction, data, searchSettings, results) {
        var self = this;

        if (results == null) {
            var searchSettings = JSON.parse(JSON.stringify(searchSettings));
            searchSettings.resultsNum = 100;
            searchSettings.page = 0;
            self.context = self.pageManager.showSpinner(searchSettings, true);
        }

        if (results != null) {
            for(var i = 0; i < results.length; i++) {
                var entry = rowFunction(results[i]);
                data.push(entry);
            }

        }

        var url = this.settingsToURL(searchSettings);

        var successCallback = function(response) {
            //console.log("Downloaded " + (data.length-1) + " of " + response.hitCount);
            var percentage = (data.length-1)/response.hitCount;
            self.pageManager.drawPercentageCircle(self.context, percentage);

            if (data.length <= response.hitCount) {
                var remainingEntries = (response.hitCount - data.length) + 1;
                if (remainingEntries < searchSettings.resultsNum) {
                    searchSettings.resultsNum = remainingEntries;
                }
                searchSettings.page++;
                self.runDownloadSearch(rowFunction, data, searchSettings, response.entries);
            } else {
                self.pageManager.removeSpinner(searchSettings);
                self.saveResults(data, searchSettings);
            }

        };
        var errorCallback = function(httpReq) {
            self.pageManager.removeSpinner(searchSettings);
            console.log("Error: Something went wrong during data download");
        };
        self.runAjax("GET", "json", url, null, successCallback, errorCallback);
    };

    this.saveResults = function(data, searchSettings) {
        var lines = data.map(function(tokens){
            var values = tokens.map(function (item) {
                if (item != null) {
                    var value = item.name;
                    if (value != null) {
                        value = value.replace(/\"/g, '\\"');
                        //value = value.replace(/\s+/g, ' '); //removing newlines etc
                        value = '"' + value + '"';
                        return value;
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            })
            var line = values.join("\t");
            line += "\n";
            return line;
        });

        //the first line describes all the search parameters
        var today = new Date();
        var date = today.getDate();
        var month = today.getMonth() + 1;
        var year = today.getFullYear();
        var hour = today.getHours();
        var minute = today.getMinutes();

        var searchDescription = "Time:year=" + year + ",month=" + month + ",date=" + date + ",hour=" + hour + ",minute=" + minute + ";";
        searchDescription += "Type:" + searchSettings.type + ";";
        searchDescription += "Text:" + searchSettings.searchText + ";";
        var facetNames = Object.keys(searchSettings.facets);
        var facetDescription = "";
        if (facetNames != null && facetNames.length > 0) {
            facetDescription = "Facets:"
            for(var i=0; i < facetNames.length; i++) {
                var facetName = facetNames[i];
                if (searchSettings.facets[facetName].length > 0) {
                    facetDescription += facetName + "=";
                    var facetValues = searchSettings.facets[facetName];
                    for(var j=0; j < facetValues.length; j++) {
                        var value = facetValues[j];
                        facetDescription += "'" + value + "'";
                        if (value !== facetValues[facetValues.length-1]
                            && facetName == facetNames[facetNames.length-1]) {
                            facetDescription += ",";
                        }
                    }
                }

            }
            facetDescription += ";";
        }

        if (facetDescription.length > 0) {
            searchDescription += facetDescription;
        }

        if (searchSettings.hasOwnProperty("numericalFields")
            && searchSettings.numericalFields.length > 0) {
            var numericalFields = searchSettings["numericalFields"];
            for (var i=0; i < numericalFields.length; i++) {
                var numericalField = numericalFields[i];
                if (numericalField.selectedMinimum != numericalField.minimum
                    || numericalField.selectedMaximum != numericalField.maximum) {
                    searchDescription += numericalField.displayName + "-Range:";
                    searchDescription += "Minimum=" + numericalField.selectedMinimum + ",";
                    searchDescription += "Maxmimum=" + numericalField.selectedMaximum + ";";
                }

            }
        }
        searchDescription += "\n";
        lines.unshift(searchDescription);
        var blob = new Blob(lines, {type: "text/plain;charset=utf-8"});
        saveAs(blob, "metagenomics_" + searchSettings.type.toLowerCase() + ".tsv");
    };
};

var HomePageManager = function(settingsManager, searchManager) {

    this.settingsManager = settingsManager;
    this.searchManager = searchManager;

    this.updateExperimentStats = function() {
        var experimentTypes = [
            {
                display: "amplicons",
                search: "amplicon"
            },
            {
                display: "assemblies",
                search: "assembly"
            },
            {
                display: "metabarcoding",
                search: "metabarcoding"
            },
            {
                display: "metagenomes",
                search: "metagenomic"
            },
            {
                display: "metatranscriptomes",
                search: "metatranscriptomic"
            }
        ];

        var settings = this.settingsManager.getSearchSettings(this.settingsManager.GLOBAL_SEARCH_SETTINGS.RUN);
        var settingsCopy = JSON.parse(JSON.stringify(settings)); //make a shallow copy of settings

        for (var i = 0; i < experimentTypes.length; i++) {
            var experimentType = experimentTypes[i];
            var experimentStatElement = document.getElementById(experimentType.display + "-statistics");
            if (experimentStatElement != null) {
                var previousValue = experimentStatElement.innerHTML;
                var facet = {experiment_type:[experimentType.search]};
                experimentStatElement.innerHTML = "";
                settingsCopy.searchText = "";
                settingsCopy.facets = facet;
                settingsCopy.facetNum = 0;
                settingsCopy.numericalFields = {};
                settingsCopy.resultsNum = 0;
                var url = this.searchManager.settingsToURL(settingsCopy);
                this.searchManager.runAjax("GET", "json", url, null,
                    this.updateStatsElement(settingsCopy, experimentStatElement, facet, previousValue),
                    this.onStatsUpdateError(settingsCopy, experimentStatElement, previousValue),
                    1000,
                    this.onStatsTimeout(settingsCopy, experimentStatElement, previousValue)
                );
            } else {
                console.log("Error: Expected to find element with id '" + experimentType + "-statistics'");
            }
        }
    };

    this.updatePublicStats = function() {
        var dataTypes = this.settingsManager.DatatypeSettings.DATA_TYPES;
        for (var i = 0; i < dataTypes.length; i++) {
            var dataType = dataTypes[i];
            var typeStatElement = document.getElementById(dataType + "-statistics");
            if (typeStatElement != null) {
                var previousValue = typeStatElement.innerHTML;
                typeStatElement.innerHTML = "";
                var settings = this.settingsManager.getSearchSettings(dataType);
                var settingsCopy = JSON.parse(JSON.stringify(settings)); //make a shallow copy of settings
                settingsCopy.searchText = "";
                settingsCopy.facets = {};
                settingsCopy.facetNum = 0;
                settingsCopy.numericalFields = {};
                settingsCopy.resultsNum = 0;
                var url = this.searchManager.settingsToURL(settingsCopy);
                this.searchManager.runAjax("GET", "json", url, null,
                    this.updateStatsElement(settingsCopy, typeStatElement),
                    this.onStatsUpdateError(settingsCopy, typeStatElement, previousValue),
                    1000,
                    this.onStatsTimeout(settingsCopy, typeStatElement, previousValue)
                );
            } else {
                console.log("Error: Expected to find element with id '" + settingsCopy.type + "-statistics'");
            }
        }
    };

    this.updateStatsElement = function(settingsCopy, typeStatElement, runFacet, previousValue) {
        var self = this;
        return function(response) {
            var hitCount = response.hitCount;

            console.log(settingsCopy.type + " stats success: " + hitCount);

            if (hitCount != null) {
                var statsLink = document.createElement("a");
                statsLink.classList.add(self.settingsManager.GLOBAL_SEARCH_SETTINGS.HOMEPAGE_LINK_CLASS);
                statsLink.innerHTML = hitCount;
                typeStatElement.appendChild(statsLink);
                typeStatElement.onclick = function(event) {
                    var selectedTabNum = self.settingsManager.DatatypeSettings.DATA_TYPES.indexOf(settingsCopy.type);
                    //reset search settings
                    var allSettings = self.settingsManager.initialiseSettings(true);
                    allSettings.searchText = "";
                    self.settingsManager.setSearchText("");
                    for (var i = 0; i < self.settingsManager.DatatypeSettings.DATA_TYPES.length; i++) {
                        var dataType = self.settingsManager.DatatypeSettings.DATA_TYPES[i];
                        var settings = allSettings[dataType];
                        self.settingsManager.setSearchSettings(dataType, settings);
                    }
                    self.settingsManager.setSelectedTab(selectedTabNum);
                    if (runFacet != null) {
                        var settings = allSettings[self.settingsManager.GLOBAL_SEARCH_SETTINGS.RUN];
                        settings.facets = runFacet;
                        self.settingsManager.setSearchSettings(settings.type, settings);
                    }
                    window.location = "/metagenomics/search";
                };
            } else {
                statsLink.innerHTML = previousValue;
            }

        }
    };

    this.onStatsUpdateError = function(settingsCopy, typeStatElement, previousValue) {
        return function(httpRes) {
            console.log("Error fetching " + settingsCopy.type + " counts for homepage");
            typeStatElement.innerHTML = previousValue;
        };
    };

    this.onStatsTimeout = function(settingsCopy, typeStatElement, previousValue) {
        return function(httpRes) {
            console.log("Error: Time fetching " + settingsCopy.type + " counts for homepage");
            typeStatElement.innerHTML = previousValue;
        };
    };

};

var PageManager = function() {
    this.settingsManager = new SettingsManager();
    this.tabManager = new TabManager();
    this.searchManager = new SearchManager(this.settingsManager, this);
    this.resultsManager = new ResultsManager();
    this.tableManager = new TableManager(this.searchManager, this.settingsManager);
    this.facetManager = new FacetManager(this.settingsManager, this.searchManager);
    this.homePageManager = new HomePageManager(this.settingsManager, this.searchManager);

    this.isSearchPage = function() {
        var searchPageDiv = document.getElementById(this.settingsManager.GLOBAL_SEARCH_SETTINGS.SEARCH_RESULTS_ID)
        return searchPageDiv != null ? true : false;
    }

    this.isHomePage = function() {
        var statsDiv = document.getElementById(this.settingsManager.GLOBAL_SEARCH_SETTINGS.HOMEPAGE_STATS_ID);
        return statsDiv != null ? true : false;
    };

    this.populateSearchInputs = function() {
        //copy search text to search boxes
        var searchText = this.settingsManager.getSearchText();

        var searchInputID = this.settingsManager.GLOBAL_SEARCH_SETTINGS.SEARCH_BOX_ID;
        var searchInput = document.getElementById(searchInputID);
        if (searchInput != null) {
            if (searchText != null) {
                searchInput.value = searchText;
            }
        } else {
            console.log("Error: expected input with id = " + searchInputID);
        }

        var searchInputSmallID = this.settingsManager.GLOBAL_SEARCH_SETTINGS.SEARCH_BOX_SMALL_ID
        var searchInputSmall = document.getElementById(searchInputSmallID);
        if (searchInputSmall != null) {
            var searchInputSmall = this.settingsManager.getSearchText();
            if (searchInputSmall != null) {
                searchInputSmall.value = searchText;
            }
        }
    };

    this.loadCss = function() {
        //load css for the modal box
        var linkElement = document.createElement("link");
        linkElement.rel = "stylesheet";
        linkElement.href = "/metagenomics/css/ajax-modal.css";
        document.head.appendChild(linkElement);
    }

    this.clearDomainResults = function(searchSettings) {
        var dataType = searchSettings.type;
        this.tabManager.setTabText("Searching", dataType);
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
        var pageElements = ["header", "footer"];
        for(var i=0; i < pageElements.length; i++) {
            var element = pageElements[i];
            var paginationContainer = document.getElementById(dataType + "-" + element + "-searchPagination");
            if (paginationContainer != null) {
                paginationContainer.innerHTML = "";
            } else {
                console.log("Error: Expected to find div with id '" + dataType + "-" + element + "-searchPagination'");
            }
            var downloadContainer = document.getElementById(dataType + "-" + element + "-download");
            if (downloadContainer != null) {
                downloadContainer.innerHTML = "";
            } else {
                console.log("Error: Expected to find div with id '" + dataType + "-" + element + "-download'");
            }
            var hitNumContainer = document.getElementById(dataType + "-hitnum-text");
            if (hitNumContainer != null) {
                hitNumContainer.innerHTML = "";
            } else {
                console.log("Error: Expected to find div with id '" + dataType + "-hitNum-text'");
            }
        }


    };

    this.showSpinner = function(searchSettings, determinate) {
        console.log("showSpinner()");
        var global = this.settingsManager.GLOBAL_SEARCH_SETTINGS;
        var overlay = document.getElementById(global.SPINNER_OVERLAY_ID);
        if (overlay != null) {
            overlay.style.display = "block";
            var spinnerCanvas = document.getElementById(global.SPINNER_ID);
            if (spinnerCanvas != null) {
                spinnerCanvas.classList.add("spinner");
                spinnerCanvas.style.display = "block";
                var context = spinnerCanvas.getContext("2d");
                this.drawBaseCircle(context);
                if (determinate) {
                    this.drawPercentageCircle(context, 0.0);
                } else {
                    this.drawPercentageCircle(context, 0.4);
                }
                return context;
            } else {
                console.log("Error: Expected to find div with id '" + global.SPINNER_ID+ "'");
            }
        } else {
            console.log("Error: Expected to find element with id " + global.SPINNER_OVERLAY_ID);
        }
    };

    this.drawBaseCircle = function(context) {
        context.beginPath();
        context.lineWidth = 10;
        context.strokeStyle = '#F0F0F0';
        context.arc(25, 25, 20, 0, 6.28319, false);
        context.stroke();
        context.closePath();
    };

    this.drawPercentageCircle = function(context, percentage) {
        context.beginPath();
        var degrees = 360.0 * percentage
        var radians = (degrees * Math.PI)/180
        context.arc(25, 25, 20, 0, radians, false);
        context.fillStyle = "rgba(0,0,200,0)";
        context.fill();

        // draw the stroke
        context.lineWidth = 8;
        context.strokeStyle = '#2B8A97';
        context.stroke();
        context.closePath();
    };

    this.removeSpinner = function(searchSettings) {
        console.log("removeSpinner()");
        var global = this.settingsManager.GLOBAL_SEARCH_SETTINGS;
        var overlay = document.getElementById(global.SPINNER_OVERLAY_ID);
        if (overlay != null) {
            overlay.style.display = "none";
            var spinnerCanvas = document.getElementById("spinner");
            if (spinnerCanvas != null) {
                spinnerCanvas.classList.remove("spinner");
                spinnerCanvas.style.display = "none";
            } else {
                console.log("Error: Expected to find div with id '" + global.SPINNER_ID+ "'");
            }
        } else {
            console.log("Error: Expected to find element with id " + global.SPINNER_OVERLAY_ID);
        }
    };

    this.displaySearchError = function (httpReq, searchSettings) {
        this.resultsManager.displaySearchError(httpReq, searchSettings, this.tabManager);
    };

    this.displayDomainData = function (httpReq, searchSettings) {
        this.resultsManager.displayDomainData(httpReq,
            searchSettings,
            this.settingsManager,
            this.tabManager,
            this.tableManager,
            this.facetManager
        );
    };

    this.displayTabHeader = function (settingsManager) {
        this.tabManager.displayTabHeader(settingsManager);
    };

    this.searchData = function (httpReq, searchSettings) {

    };

    this.initialisePage = function () {
        this.searchManager.loadPageFromServer();
    };

    this.runSmallSearch = function() {
        var smallSearchBox = document.getElementById(this.settingsManager.GLOBAL_SEARCH_SETTINGS.SEARCH_BOX_SMALL_ID);
        if (smallSearchBox != null) {
            smallSearchBoxText = smallSearchBox.value;
            var searchBox = document.getElementById(this.settingsManager.GLOBAL_SEARCH_SETTINGS.SEARCH_BOX_ID);
            if (searchBox != null) {
                searchBoxText = searchBox.value;
                if (smallSearchBoxText != searchBoxText) {
                    searchBox.value = smallSearchBoxText;
                }
                this.runSearch();
            } else {
                console.log(
                    "Error: Expected to find input with ID "
                    + this.settingsManager.GLOBAL_SEARCH_SETTINGS.SEARCH_BOX_ID
                );
            }
        } else {
            console.log(
                "Error: Expected to find input with ID "
                + this.settingsManager.GLOBAL_SEARCH_SETTINGS.SEARCH_BOX_SMALL_ID
            );
        }
    }

    this.runSearch = function() {
        var searchBox = document.getElementById(this.settingsManager.GLOBAL_SEARCH_SETTINGS.SEARCH_BOX_ID);
        var searchBoxText = null;
        if (searchBox != null) {
            searchBoxText = searchBox.value;
            var storedSearchText = this.settingsManager.getSearchText();
            var resetSearch = false;
            console.log("running search");
            if (searchBoxText != storedSearchText) {
                resetSearch = true;
            }

            if (this.isSearchPage()) {
                for(var i = 0; i < this.settingsManager.DatatypeSettings.DATA_TYPES.length; i++) {
                    var dataType = this.settingsManager.DatatypeSettings.DATA_TYPES[i];
                    var settings = this.settingsManager.getSearchSettings(dataType);
                    settings.searchText = searchBoxText;
                    if (resetSearch) {
                        settings.facets = {};
                        settings.page = 0;
                    }
                    this.searchManager.runDomainSearch(settings);
                }
            } else {
                for(var i = 0; i < this.settingsManager.DatatypeSettings.DATA_TYPES.length; i++) {
                    var dataType = this.settingsManager.DatatypeSettings.DATA_TYPES[i];
                    var settings = this.settingsManager.getSearchSettings(dataType);
                    settings.searchText = searchBoxText;
                    if (resetSearch) {
                        settings.facets = {};
                        settings.page = 0;
                    }
                    this.settingsManager.setSearchSettings(dataType, settings);
                }
                this.settingsManager.setSearchText(searchBoxText);
                window.location = "/metagenomics/search"
            }
        } else {
            console.log(
                "Error: Expected to find input with ID "
                + this.settingsManager.GLOBAL_SEARCH_SETTINGS.SEARCH_BOX_ID
            );
        }

    }

    this.updateHomepageStats = function() {
        if (!this.settingsManager.areSettingsInitialisted()) {
            this.settingsManager.initialiseSettings();
        }
        this.homePageManager.updatePublicStats();
        this.homePageManager.updateExperimentStats();
    }
};

var pageManager = new PageManager();
window.onload = function (event){
    pageManager.populateSearchInputs();
    if (pageManager.isSearchPage()) {
        pageManager.initialisePage();
    } else if (pageManager.isHomePage()) {
        pageManager.updateHomepageStats();
    }
};






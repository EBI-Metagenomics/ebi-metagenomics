/**
 * Created by maq on 06/04/2016.
 */

var HIDDEN_CLASS = "this_hide";

var compileAndSendForm = function() {
    console.log("Compiling and sending form");

    var searchForm = document.getElementById("local-search");
    if (searchForm != null) {
        var facetDiv = document.getElementById("facets");
        if (facetDiv != null) {
            var facetInputs = facetDiv.querySelectorAll("input");
            console.log("Adding " + facetInputs.length + " facet fields");
            for (var i =0; i < facetInputs.length; i++) {
                clonedFacet = facetInputs[i].cloneNode();
                clonedFacet.classList.add(HIDDEN_CLASS);
                searchForm.appendChild(clonedFacet);
            }
        }
        var paginationDiv = document.getElementById("searchPagination");
        if (paginationDiv != null) {
            var paginationInputs = paginationDiv.querySelectorAll("input");
            console.log("Adding " + paginationInputs.length + " pagination fields");
            for (var i =0; i < paginationInputs.length; i++) {
                clonedInput = paginationInputs[i].cloneNode();
                clonedInput.classList.add(HIDDEN_CLASS);
                searchForm.appendChild(clonedInput);
            }
        }
        searchForm.submit();
    } else {
        console.log("This document should contain 'local-search'");
    }

};

/*
 Handle move of facets and pagination elements from EMG header to page body
 */
var hiddenFacetDiv = document.getElementById("hiddenFacets");
var facetDiv = document.getElementById("facets");
if (hiddenFacetDiv != null && facetDiv != null) {
    facetDiv.appendChild(hiddenFacetDiv);
    hiddenFacetDiv.classList.remove(HIDDEN_CLASS);
}

var hiddenPaginationDiv = document.getElementById("hiddenSearchPagination");
var paginationDiv = document.getElementById("searchPagination");
if (hiddenPaginationDiv != null && paginationDiv != null) {
    paginationDiv.appendChild(hiddenPaginationDiv);
    hiddenPaginationDiv.classList.remove(HIDDEN_CLASS);
}

/*
 Handle addition of text to search textfield
 */
var searchForm = document.getElementById("local-search");
if (searchForm != null) {
    console.log("searchForm - attaching onsubmit listener");
    searchForm.addEventListener("submit", function () {
        var searchElement = document.getElementById("local-searchbox");
        var previousSearchElement = document.getElementById("previousSearch");
        if (searchElement != null && previousSearchElement != null) {
            newSearch = searchElement.value;
            previousSearch = previousSearchElement.value;
            if (newSearch != previousSearch) {
                resetPage();
                unsetFacets();
            }
        }
        console.log("Submitting");
        compileAndSendForm();
    });
} else {
    console.log("This document should contain 'local-search'");
}

/*
 Handling of facet checkboxes on search page.
 Triggers new search anytime a checkbox is changed
 */
var checkboxes = document.querySelectorAll("input[name=facets]");
if (checkboxes != null) {
    console.log("Checkboxes = " + checkboxes.length);
    for (var i = 0; i < checkboxes.length; i++) {
        checkboxes[i].addEventListener("change", function (event) {
            resetPage();
            compileAndSendForm();
        });
    }
}

/*
 Handling of next/previous page clicks
 */
var changePage = function (forward) {
    var currentPageElement = document.getElementById("currentPage");
    if (currentPageElement != null) {
        var currentPage = currentPageElement.value;
        var nextPage = currentPage;
        if (forward) {
            nextPage++;
        } else {
            nextPage--;
        }
        currentPageElement.value = nextPage;
        console.log("Changing Page = " + nextPage);
    } else {
        console.log("Failed to find element 'currentPage'");
    }
    var searchForm = document.getElementById("local-search");
    compileAndSendForm();
};

var nextPageElement = document.getElementById("nextPage");
var previousPageElement = document.getElementById("previousPage");
if (nextPageElement != null && previousPageElement != null) {
    //disable previous button if on first page
    var currentPage = document.getElementById("currentPage").value;
    if (currentPage == 1) {
        previousPageElement.disabled = true;
    }
    //disable next button if on last page (can also be the first page)
    var maxPage = document.getElementById("maxPage").value;
    if (currentPage == maxPage) {
        nextPageElement.disabled = true;
    }
    nextPageElement.addEventListener("click", function () {
        changePage(true)
    });
    previousPageElement.addEventListener("click", function () {
        changePage(false)
    });
}

/*
 called when facet fields or text field is altered to ensure new search results
 are displayed from page one onwards
 */
var resetPage = function () {
    console.log("resetPage");
    var currentPageElement = document.getElementById("currentPage");
    if (currentPageElement != null) {
        currentPageElement.value = 1;
    }
}

/*
 called when local-search text field is changed to unset all facets
 */
var unsetFacets = function () {
    console.log("unsetFacets");
    var checkboxes = document.querySelectorAll("input[name=facets]");
    if (checkboxes != null) {
        console.log("Facets = " + checkboxes.length);
        for (var i = 0; i < checkboxes.length; i++) {
            checkboxes[i].checked = false;
        }
    }
}
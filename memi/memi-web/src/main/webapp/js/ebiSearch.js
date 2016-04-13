/**
 * Created by maq on 06/04/2016.
 */
/*
 Handle addition of text to search textfield
 */
var searchElement = document.getElementById("local-searchbox");
if (searchElement != null) {
    searchElement.addEventListener("submit", function () {
        resetPage();
        unsetFacets();
    });
} else {
    console.log("This document should contain 'local-searchbox'")
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
            document.getElementById("searchForm").submit();
        });
    }
}

/*
 Handling of next/previous page clicks
 */
var changePage = function (forward) {
    var currentPageElement = document.getElementById("currentPage");
    var currentPage = currentPageElement.value;
    var nextPage = currentPage;
    if (forward) {
        nextPage++;
    } else {
        nextPage--;
    }
    currentPageElement.value = nextPage;
    console.log("Changing Page = " + nextPage);
    document.getElementById("searchForm").submit();
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
    var currentPageElement = document.getElementById("currentPage");
    if (currentPageElement != null) {
        currentPageElement.value = 1;
    }
}

/*
 called when local-search text field is changed to unset all facets
 */
var unsetFacets = function () {
    var checkboxes = document.querySelectorAll("input[name=facets]");
    if (checkboxes != null) {
        for (var i = 0; i < checkboxes.length; i++) {
            checkboxes[i].checked = false;
        }
    }
}
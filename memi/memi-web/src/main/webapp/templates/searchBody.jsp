<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="container_24" id="mainContainer">

    <h2>Search EBI Metagenomics</h2>
    <div class="grid_24 clearfix" id="content">

        <form:form id="searchForm" method="GET" action="${pageContext.request.contextPath}/search/doEbiSearch" commandName="ebiSearchForm">
            <div class="grid_6 alpha" id="facets">
                <c:choose>
                    <c:when test="${not empty model.ebiSampleSearchResults
                        && fn:length(model.ebiSampleSearchResults.facets) > 0}">
                        <h3>Filter your results</h3>
                        <c:forEach var="facet" items="${model.ebiSampleSearchResults.facets}">
                            <c:if test="${fn:length(facet.values) > 0}">
                                <h4>${facet.label}</h4>
                                <form:checkboxes path="facets" items="${facet.values}" itemLabel="labelAndCount" itemValue="facetAndValue" element="div" />
                            </c:if>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </div>
            <div class="grid_18 omega" id="searchInput">
                <fieldset>
                    <label for="searchText">Search: </label>
                    <span>
                        <form:input path="searchText" id="searchText"/>
                    </span>
                    <form:errors path="errorText" cssClass="error"/>
                </fieldset>
            </div>

            <div class="grid_18 omega">
                <c:choose>
                    <c:when test="${not empty model.ebiSampleSearchResults}">
                        <h3>Showing ${fn:length(model.ebiSampleSearchResults.entries)} out of ${model.ebiSampleSearchResults.numberOfHits} results</h3>
                        <c:choose>
                            <c:when test="${fn:length(model.ebiSampleSearchResults.entries) > 0}">
                                <c:forEach var="result" items="${model.ebiSampleSearchResults.entries}">
                                    <a href="${pageContext.request.contextPath}/projects/${result.project}">${result.project}: </a><a href="${pageContext.request.contextPath}/projects/${result.project}/samples/${result.identifier}">${result.identifier}</a>: ${result.description}<br />
                                </c:forEach>
                                <div>
                                    <input type="button" id="previousPage" value="Previous" />
                                    <form:hidden id="currentPage" path="page"/>
                                    <form:hidden id="maxPage" path="maxPage"/>
                                    Page ${ebiSearchForm.page} of ${ebiSearchForm.maxPage}
                                    <input type="button" id="nextPage" value="Next" />
                                </div>
                            </c:when>
                        </c:choose>
                    </c:when>
                </c:choose>
                <div>
                    Powered by <a href="http://www.ebi.ac.uk/ebisearch/" target="_blank">EBI Search</a>
                </div>
            </div>

        </form:form>
    </div>
</div>

<script>$(function () { $("[data-toggle='tooltip']").tooltip(); });</script>
<script>
    $("#expand_button").click(function(){
        $(".more_citations").slideToggle();
        $("#expand_button").toggleClass("min");
    });
</script>
<script>
    /*
    Handle addition of text to search textfield
    */
    var searchElement = document.getElementById("searchText");
    searchElement.addEventListener("submit", function() {
        resetPage();
    });

    /*
    Handling of facet checkboxes. Triggers new search anytime a checkbox is changed
     */
    var checkboxes = document.querySelectorAll("input[name=facets]");
    console.log("Checkboxes = " + checkboxes.length);
    for(var i=0; i < checkboxes.length; i++) {
        checkboxes[i].addEventListener("change", function(event){
            resetPage();
            document.getElementById("searchForm").submit();
        });
    }

    /*
    Handling of next/previous page clicks
     */
    var changePage = function(forward) {
        var currentPageElement = document.getElementById("currentPage");
        var currentPage = currentPageElement.value;
        var nextPage = currentPage;
        if(forward) {
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
        nextPageElement.addEventListener("click", function() {changePage(true)});
        previousPageElement.addEventListener("click", function() {changePage(false)});
    }

    /*
    called when facet fields or text field is altered to ensure new search results
    are displayed from page one onwards
     */
    var resetPage = function() {
        var currentPageElement = document.getElementById("currentPage");
        currentPageElement.value = 1;
    }

</script>
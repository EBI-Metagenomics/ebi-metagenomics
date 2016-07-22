<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="spinnerDiv" class="modal">
    <div class="modal-spinner">
        <img id="loader" src="/metagenomics/img/spinner.gif" />
    </div>
</div>
<div class="grid_24" id="mainContainer">
    <h2>Search EBI Metagenomics</h2>

    <form:form class="local-search-xs" action="${pageContext.request.contextPath}/search"
               commandName="ebiSearchForm" method="GET">

        <div class="grid_24">

            <div class="grid_18 alpha">

                <fieldset>
                    <label>
                        <form:input path="searchText" type="search"/>
                    </label>
                </fieldset>

            </div>
            <div class="grid_6 omega"> <input type="submit" id="searchsubmit" name="searchsubmit" value="Search" class="submit"></div>

        </div>
    </form:form>

    <div class="grid_24"  id="searchTabs">
        <div id="tabDiv"></div>
        <div id="Projects">
            <div class="grid_5 alpha" id="Projects-searchFacets"></div>

            <div class="grid_19 omega">
                <div class="table-margin-r" id="Projects-searchData"></div>
                <div id="Projects-searchPagination" class="table-pagination"></div>
            </div>
        </div>
        <div id="Samples">
            <div class="grid_5 alpha" id="Samples-searchFacets"></div>

            <div class="grid_19 omega">
                <div class="table-margin-r" id="Samples-searchData"></div>
                <div id="Samples-searchPagination" class="table-pagination"></div>
            </div>
        </div>

        <div id="Runs">
            <div class="grid_5 alpha" id="Runs-searchFacets"></div>

            <div class="grid_19 omega">
                <div class="table-margin-r" id="Runs-searchData"></div>
                <div id="Runs-searchPagination" class="table-pagination"></div>
            </div>
        </div>
    </div>

</div>
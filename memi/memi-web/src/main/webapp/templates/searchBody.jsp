<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="grid_24" id="mainContainer">
    <h2>Search EBI Metagenomics</h2>

    <form:form class="local-search-xs" action="${pageContext.request.contextPath}/search/doEbiSearch"
               commandName="ebiSearchForm" method="POST">

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
    <div class="grid_24" id="searchTabs"></div>

    <div class="grid_24">
        <div id="projects">
            <div class="grid_5 alpha" id="projects-searchFacets"></div>

            <div class="grid_19 omega">
                <div class="table-margin-r" id="projects-searchData"></div>
                <div id="projects-searchPagination" class="table-pagination"></div>
            </div>
        </div>
        <div id="samples">
            <div class="grid_5 alpha" id="samples-searchFacets"></div>

            <div class="grid_19 omega">
                <div class="table-margin-r" id="samples-searchData"></div>
                <div id="samples-searchPagination" class="table-pagination"></div>
            </div>
        </div>

        <div id="runs">
            <div class="grid_5 alpha" id="runs-searchFacets"></div>

            <div class="grid_19 omega">
                <div class="table-margin-r" id="runs-searchData"></div>
                <div id="runs-searchPagination" class="table-pagination"></div>
            </div>
        </div>

    </div>
</div>
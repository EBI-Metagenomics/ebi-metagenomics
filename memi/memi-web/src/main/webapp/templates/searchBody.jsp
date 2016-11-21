<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="grid_24 sample_ana" id="mainContainer">
    <h2>Search EBI Metagenomics</h2>

    <form class="local-search-xs" action="javascript:pageManager.runSmallSearch();"
               commandName="ebiSearchForm" method="GET">

        <div class="grid_24">

            <div class="grid_18 alpha">

                <fieldset>
                    <label>
                        <input path="searchText" type="search" id="local-searchbox-xs"/>
                    </label>
                </fieldset>

            </div>
            <div class="grid_6 omega"> <input type="submit" id="searchsubmit" name="searchsubmit" value="Search" class="submit"></div>

        </div>
    </form>

    <div  id="searchTabs">
        <div id="tabDiv"></div>
        <div id="Projects">
            <div class="search-container" style="overflow: auto;">
            <div class="search-filter grid_5 alpha" id="Projects-searchFacets"></div>

            <div class="search-page grid_19 omega">
                <div class="table-margin-r" id="Projects-searchData"></div>
                <div id="Projects-searchPagination" class="table-pagination"></div>
            </div>
            </div>
        </div>

        <div id="Samples" >
            <div class="search-container" style="overflow: auto;">
            <div class="search-filter grid_5 alpha" id="Samples-searchFacets"></div>

            <div class="search-page grid_19 omega">
                <div class="table-margin-r" id="Samples-searchData"></div>
                <div class="table-pagination" id="Samples-searchPagination"></div>
            </div>
            </div>
        </div>

        <div id="Runs">
            <div class="search-container" style="overflow: auto;">
            <div class="search-filter grid_5 alpha" id="Runs-searchFacets"></div>

            <div class="search-page grid_19 omega">
                <div class="table-margin-r" id="Runs-searchData"></div>
                <div id="Runs-searchPagination" class="table-pagination"></div>
            </div>
        </div>
        </div>
    </div>

</div>
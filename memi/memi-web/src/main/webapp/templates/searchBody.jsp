<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="grid_24 sample_ana" id="mainContainer">
    <div style="overflow: auto">
        <div class="searchTitle">Search EBI Metagenomics</div>
        <span width="50" height="50" id="spinner-container" class="spinner-container" style="position: relative">
            <canvas id="spinner" class="spinner" width="50" height="50"></canvas>
            <span class="percentage-text" id="spinner-percentage" style="z-index: 10; position: absolute; top:-35px;left: 12px"></span>
        </span>

    </div>

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
                <div id="Projects-hitnum-text"></div>

                <div id="Projects-table-header">
                    <div id="Projects-header-searchPagination" class="table-pagination"></div>
                    <div id="Projects-header-download" class="table-download"></div>
                </div>

                <div class="table-margin-r" id="Projects-searchData">

                </div>

                <div id="Projects-table-footer">
                    <div id="Projects-footer-searchPagination" class="table-pagination"></div>
                    <div id="Projects-footer-download" class="table-download"></div>
                </div>
            </div>
            </div>
        </div>

        <div id="Samples" >
            <div class="search-container" style="overflow: auto;">
            <div class="search-filter grid_5 alpha" id="Samples-searchFacets"></div>

            <div class="search-page grid_19 omega">
                <div id="Samples-hitnum-text"></div>

                <div id="Samples-table-header">
                    <div id="Samples-header-searchPagination" class="table-pagination"></div>
                    <div id="Samples-header-download" class="table-download"></div>
                </div>

                <div class="table-margin-r" id="Samples-searchData"></div>

                <div id="Samples-table-footer">
                    <div id="Samples-footer-searchPagination" class="table-pagination"></div>
                    <div id="Samples-footer-download" class="table-download"></div>
                </div>
            </div>
            </div>
        </div>

        <div id="Runs">
            <div class="search-container" style="overflow: auto;">
            <div class="search-filter grid_5 alpha" id="Runs-searchFacets"></div>

            <div class="search-page grid_19 omega">
                <div id="Runs-hitnum-text"></div>

                <div id="Runs-table-header">
                    <div id="Runs-header-searchPagination" class="table-pagination"></div>
                    <div id="Runs-header-download" class="table-download"></div>
                </div>

                <div class="table-margin-r" id="Runs-searchData"></div>

                <div id="Runs-table-footer">
                    <div id="Runs-footer-searchPagination" class="table-pagination"></div>
                    <div id="Runs-footer-download" class="table-download"></div>
                </div>
            </div>
        </div>
        </div>
    </div>
    <div id="search-overlay" class="overlay"></div>
</div>
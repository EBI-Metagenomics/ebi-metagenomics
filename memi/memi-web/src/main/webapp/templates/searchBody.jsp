<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<div class="grid_24" id="mainContainer">

    <h2>Search EBI Metagenomics</h2>
    <form:form class="local-search-xs" action="${pageContext.request.contextPath}/search/doEbiSearch"
               commandName="ebiSearchForm" method="get">
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
    <div class="grid_24">

        <div class="grid_5 alpha" id="facets"></div>


        <div class="grid_19 omega">
            <div class="table-margin-r">
                <c:choose>
                    <c:when test="${not empty model.ebiSampleSearchResults}">
                        <c:choose>
                            <c:when test="${fn:length(model.ebiSampleSearchResults.entries) <= 0}">
                                <h3>No results found</h3>
                                <hr>
                                <p><small class="text-muted">Powered by <a href="http://www.ebi.ac.uk/ebisearch/" class="ext" target="_blank">EBI Search</a></small></p>
                            </c:when>
                            <c:otherwise>
                                <h3>Showing ${fn:length(model.ebiSampleSearchResults.entries)} out
                                    of ${model.ebiSampleSearchResults.numberOfHits} results</h3>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${fn:length(model.ebiSampleSearchResults.entries) > 0}">
                                <table border="1" class="table-light">
                                    <thead>
                                    <tr>
                                        <th>Project</th>
                                        <th>Sample</th>
                                        <th>Name</th>
                                        <th >Description</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="result" items="${model.ebiSampleSearchResults.entries}">
                                        <tr>
                                            <td class="col_sm">
                                                <a href="${pageContext.request.contextPath}/projects/${result.project}">
                                                        ${result.project}
                                                </a>
                                            </td>
                                            <td class="col_sm">
                                                <a href="${pageContext.request.contextPath}/projects/${result.project}/samples/${result.identifier}">
                                                        ${result.identifier}
                                                </a>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${fn:length(result.name)>70}">
                                                        <c:set var="shortName" value="${fn:substring(result.description, 0, 70)}"/>
                                                        ${shortName} ...
                                                    </c:when>

                                                    <c:otherwise>
                                                        ${result.name}
                                                    </c:otherwise>
                                                </c:choose>

                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${fn:length(result.description)>200}">
                                                        <c:set var="shortDesc" value="${fn:substring(result.description, 0, 200)}"/>
                                                        ${shortDesc} ...
                                                    </c:when>

                                                    <c:otherwise>
                                                        ${result.description}
                                                    </c:otherwise>
                                                </c:choose>

                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <div id="searchPagination" class="table-pagination"></div>
                            </c:when>
                        </c:choose>
                    </c:when>
                </c:choose>
            </div>
        </div>

    </div>
</div>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="grid_24" id="mainContainer">

    <h2>Search EBI Metagenomics</h2>

    <div class="grid_24">

        <form:form id="searchForm" method="GET" action="${pageContext.request.contextPath}/search/doEbiSearch"
                   commandName="ebiSearchForm">
            <div class="grid_6 alpha" id="facets">
                <c:choose>
                    <c:when test="${not empty model.ebiSampleSearchResults
                        && fn:length(model.ebiSampleSearchResults.facets) > 0}">
                        <h3>Filter your results</h3>
                        <c:forEach var="facet" items="${model.ebiSampleSearchResults.facets}">
                            <c:if test="${fn:length(facet.values) > 0}">
                                <h4>${facet.label}</h4>
                                <div class="extra-pad"><form:checkboxes path="facets" items="${facet.values}" itemLabel="labelAndCount"
                                                 itemValue="facetAndValue" element="div"/> </div>
                            </c:if>
                        </c:forEach>
                        <hr>
                        <p><small class="text-muted">Powered by <a href="http://www.ebi.ac.uk/ebisearch/" class="ext" target="_blank">EBI Search</a></small></p>
                    </c:when>
                </c:choose>
            </div>


            <div class="grid_18 omega">
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
                                <table border="1" class="table-light result stripe hover order-column">
                                    <thead>
                                        <tr>
                                            <th>Project</th>
                                            <th>Sample</th>
                                            <th>Description</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="result" items="${model.ebiSampleSearchResults.entries}">
                                        <tr>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/projects/${result.project}">
                                                        ${result.project}
                                                </a>
                                            </td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/projects/${result.project}/samples/${result.identifier}">
                                                        ${result.identifier}
                                                </a>
                                            </td>
                                            <td>
                                                ${result.description}
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <div class="table-pagination">
                                    <input type="button" id="previousPage" value="Previous"/>
                                    <form:hidden id="currentPage" path="page"/>
                                    <form:hidden id="maxPage" path="maxPage"/>
                                    <form:hidden id="searchText" path="searchText"/>
                                    Page ${ebiSearchForm.page} of ${ebiSearchForm.maxPage}
                                    <input type="button" id="nextPage" value="Next"/>
                                </div>
                            </c:when>
                        </c:choose>
                    </c:when>
                </c:choose>

            </div>

        </form:form>
    </div>
</div>
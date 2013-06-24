<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- Search results - start -->
<div class="grid_18 omega" id="search-results">

    <c:choose>
        <c:when test="${empty search.results}">
            <%--No search results found--%>

            <c:choose>
                <c:when test="${empty search.query}">
                    <!-- NO Other EBI results -->
                    <h2 class="strapline">EBeye results for
                 <span class="searchterm">
                   * (domain: ${domain})<!-- If query is empty, show alternative message -->
                 </span>
                    </h2>

                </c:when>
                <c:otherwise>

                    <h2 class="strapline">EBeye results for
                 <span class="searchterm">
                ${search.query} (domain: ${domain})<!--If query is not empty, show query-->
                 </span>
                    </h2>
                </c:otherwise>
            </c:choose>


            <c:choose>
                <c:when test="${empty search.errorMessage}">
                    <!--The search seemed to execute correctly, but there were no results-->
                    <div>Your search for <b>${search.query}</b> did not match any records in our database.<br/><br/>Suggestions:<br/><br/>
                    </div>

                    <ul>
                        <li>Make sure all words are spelled correctly.</li>
                    </ul>
                </c:when>
                <c:otherwise>
                    <!--There were no results, but an error was reported when running the search so something is wrong!-->
                    <p>WARNING: An error occurred: ${search.errorMessage}</p>
                </c:otherwise>
            </c:choose>

        </c:when>
        <c:otherwise>
            <%--Search results found, let's show them!--%>
            <section>


                <c:choose>
                    <c:when test="${empty search.query}">
                        <!-- NO Other EBI results -->
                        <h2 class="strapline">EBeye results for <span class="searchterm">*</span> (domain: ${domain})
                        </h2><!-- If query is empty, show alternative message -->
                    </c:when>
                    <c:when test="${fn:contains(search.query, 'type:')}">
                        <!-- There may be other EBI results, but they won't be shown as the component breaks when the
                        type facet is present in the query. For now just don't show it! -->
                        <!-- TODO Review this after the component has been updated! -->
                        <h2 class="strapline">EBeye results for <span class="searchterm"> ${search.query}</span> (domain: ${domain})</h2>
                    </c:when>
                    <c:otherwise>
                        <!-- Other EBI results - start -->
                        <div class="omega shortcuts expander" id="search-extras">
                            <div id="ebi_search_results"><h3 class="slideToggle icon icon-functional" data-icon="u"
                                                             style="margin:0;">Show more data from EMBL-EBI</h3>
                            </div>
                        </div>
                        <!-- /Other EBI results - start -->

                        <h2 class="strapline">EBeye results for <span class="searchterm"> ${search.query} (domain: ${domain})</span>
                        </h2><!--If query is not empty, show query-->
                    </c:otherwise>
                </c:choose>


                <div id="searchterm-details">
                    <div class="searchresult-nb">
                        <c:choose>
                            <c:when test="${search.currentPage == 1}">
                                Found <b> ${search.count}</b> results
                            </c:when>
                            <c:otherwise>
                                Page ${search.currentPage} of ${search.count} results
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </section>

            <div class="result-detail">
                <c:forEach var="result" items="${search.results}">
                    <p class="type">
                        <span class="${result.type}_tab" title="${result.type}"></span>
                        <a href="<c:url value="${entryPagesRootPath}/sample/${result.id}"/>">${result.name}</a>
                        (${result.id})
                        <span class="entry_sum">${result.description}</span>
                    </p>
                </c:forEach>
            </div>

            <!-- Search results pagination -->
            <div class="view-all result-pagination">
                <tags:paginationLinks paginationLinks="${search.paginationLinks}"/>
            </div>
        </c:otherwise>
    </c:choose>

</div>
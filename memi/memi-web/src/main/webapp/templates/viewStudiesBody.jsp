<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h2>Projects list</h2>

<div class="center">

    <div class="filter_form">
        <c:choose>
            <c:when test="${empty model.submitter}">
                <c:set var="actionUrlParam" value="studyVisibility=ALL_PUBLISHED_STUDIES"/>
            </c:when>
            <c:otherwise>
                <c:set var="actionUrlParam" value=""/>
            </c:otherwise>
        </c:choose>
        <form:form method="GET" action="${pageContext.request.contextPath}/projects/doSearch"
                   commandName="studyFilter">
            <fieldset>
                <div class="result_row">

                    <label for="text">Text:</label>
                        <%-- Autocompletion temporarily disabled since it will not scale well with this current implementaion.
          Need to get the autocomplete text from the database before this feature is activated! --%>
                        <%--<form:input id="autocomplete" path="searchTerm"/><br/>--%>
                    <span><form:input path="searchTerm"/></span>
                    <form:errors path="searchTerm" cssClass="error"/>
                </div>

                <div class="result_row">
                    <label for="biome">Biomes:</label>
                     <span>
                      <form:select id="biomeId" path="biome">
                          <form:option value="ALL" label="All"></form:option>
                          <form:option value="AIR">&nbsp;&nbsp;&nbsp;&nbsp;Air</form:option>
                          <form:option value="ENGINEERED">&nbsp;&nbsp;&nbsp;&nbsp;Engineered</form:option>
                          <form:option
                                  value="WASTEWATER">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Wastewater</form:option>
                          <form:option value="FRESHWATER">&nbsp;&nbsp;&nbsp;&nbsp;Freshwater</form:option>
                          <form:option value="HOST_ASSOCIATED">&nbsp;&nbsp;&nbsp;&nbsp;Host-associated</form:option>
                          <form:option
                                  value="HUMAN_HOST">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Human</form:option>
                          <form:option
                                  value="HUMAN_GUT">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Human gut</form:option>
                          <form:option
                                  value="NON_HUMAN_HOST">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Non-human</form:option>
                          <form:option value="MARINE">&nbsp;&nbsp;&nbsp;&nbsp;Marine</form:option>
                          <form:option value="SOIL">&nbsp;&nbsp;&nbsp;&nbsp;Soil</form:option>
                          <form:option
                                  value="FOREST_SOIL">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Forest</form:option>
                          <form:option
                                  value="GRASSLAND">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Grassland</form:option>
                      </form:select>
                     </span>
                </div>

                <c:if test="${not empty model.submitter}">
                    <div class="result_row">
                        <label for="privacy">Privacy:</label>
                     <span>
                      <form:select id="studyVisibility" path="studyVisibility">
                          <form:options items="${model.studyVisibilities}"/>
                      </form:select>
                     </span>
                    </div>
                </c:if>

                <div class="result_row">
                    <div class="filter_button">
                        <input type="submit" name="search" value="Search" class="main_button"/>
                        <c:choose>
                            <c:when test="${empty model.submitter}">
                                | <a
                                    href="<c:url value="${baseURL}/projects/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_PROJECTS"/>">Clear</a>
                            </c:when>
                            <c:otherwise>
                                | <a
                                    href="<c:url value="${baseURL}/projects/doSearch?search=Search&studyVisibility=ALL_PROJECTS"/>">Clear</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </fieldset>
        </form:form>

    </div>

</div>

<c:choose>
    <c:when test="${not empty model.studies}">
        <%--Request the current query string to export only the filtered studies--%>
        <c:set var="queryString" value="${pageContext.request.queryString}" scope="session"/>

        <div class="table_opt">

            <div class="export">
                <a href="<c:url value="${baseURL}/projects/doExportDetails?${queryString}"/>" id="csv_plus"
                   title="<spring:message code="viewStudies.download.anchor.title.detailed"/>">
                    <spring:message code="download.anchor.label.detailed"/>
                </a>
                <a href="<c:url value="${baseURL}/projects/doExport?${queryString}"/>" id="csv"
                   title="<spring:message code="viewStudies.download.anchor.title.table"/>"><spring:message
                        code="viewStudies.download.anchor.label.table"/></a>
            </div>

        </div>

        <div class="table_opt_pag">

                <%--Start of item pagination pattern--%>
            <c:set var="prevId" value="< Prev"/>
            <c:set var="nextId" value="Next >"/>
            <c:set var="firstId" value="First"/>
            <c:set var="lastId" value="Last"/>

            <div class="table_opt_pag_num"><c:out value="${model.pagination.displayedItemRange}"/> of <c:out
                    value="${model.pagination.totalItems}"/>
            </div>
            <div class="table_opt_pag_arr">
                <c:if test="${model.pagination.totalItems > model.pagination.pageSize}">
                    <c:choose>
                        <c:when test="${model.pagination.existPreviousStartPos}">

                            <div class="pag-first"><a
                                    href="<c:url value="${baseURL}/projects/doSearch?searchTerm=${model.filter.searchTerm}&studyVisibility=${model.filter.studyVisibility.upperCaseString}&biome=${model.filter.biome}&search=Search&startPosition=${model.pagination.start}"/>"
                                    id="csv" title="<c:out value="${firstId}"/>"></a></div>
                            <div class="pag-prev"><a
                                    href="<c:url value="${baseURL}/projects/doSearch?searchTerm=${model.filter.searchTerm}&studyVisibility=${model.filter.studyVisibility.upperCaseString}&biome=${model.filter.biome}&search=Search&startPosition=${model.pagination.previousStartPos}"/>"
                                    id="csv" title="<c:out value="${prevId}"/>"></a></div>
                        </c:when>
                        <c:otherwise>
                            <div class="pag-first-off"></div>
                            <div class="pag-prev-off"></div>
                        </c:otherwise>
                    </c:choose>
                    <%--<span style="float:left;padding:0 4px; color:#ABADB3;">prev | next</span>--%>
                    <c:choose>
                        <c:when test="${model.pagination.existNextStartPos}">
                            <div class="pag-next"><a
                                    href="<c:url value="${baseURL}/projects/doSearch?searchTerm=${model.filter.searchTerm}&studyVisibility=${model.filter.studyVisibility.upperCaseString}&biome=${model.filter.biome}&search=Search&startPosition=${model.pagination.nextStartPos}"/>"
                                    id="csv" title="<c:out value="${nextId}"/>"></a></div>
                            <div class="pag-last"><a
                                    href="<c:url value="${baseURL}/projects/doSearch?searchTerm=${model.filter.searchTerm}&studyVisibility=${model.filter.studyVisibility.upperCaseString}&biome=${model.filter.biome}&search=Search&startPosition=${model.pagination.lastLinkPosition}"/>"
                                    id="csv" title="<c:out value="${lastId}"/>"></a></div>
                        </c:when>
                        <c:otherwise>
                            <div class="pag-next-off"></div>
                            <div class="pag-last-off"></div>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </div>
        </div>
        <%--End of item pagination pattern--%>
        <table border="1" class="result">
            <thead>
            <tr>
                <c:forEach var="headerName" items="${model.tableHeaderNames}">
                    <c:set var="headerWidth" value="" scope="page"/>
                    <c:set var="headerId" value="" scope="page"/>
                    <c:choose>
                        <c:when test="${headerName == 'Project name'}">
                            <c:set var="headerId" value="h_left" scope="page"/>
                        </c:when>
                        <c:when test="${headerName == 'Samples'}">
                            <c:set var="headerWidth" value="60px" scope="page"/>
                        </c:when>
                        <c:when test="${headerName == 'Biome'}">
                            <c:set var="headerWidth" value="40px" scope="page"/>
                        </c:when>
                        <%--The Otherwise case is for header name Submitted date--%>
                        <c:otherwise>
                            <c:set var="headerWidth" value="120px" scope="page"/>
                        </c:otherwise>
                    </c:choose>
                    <th class="${headerId}" abbr="${headerName}" width="${headerWidth}" scope="col">${headerName}</th>
                </c:forEach>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="study" items="${model.studies}" varStatus="status">
                <tr>
                    <td>
                        <span class="biome_icon icon_xs ${study.biomeIconCSSClass}"
                              title="${study.biomeIconTitle} biome"></span>

                    </td>
                    <td class="h_left" id="ordered">

                        <a href="<c:url value="${baseURL}/projects/${study.studyId}"/>">${study.studyName}</a>

                           <%-- Show icon only for people are are logged in--%>
                           <c:if test="${not empty model.submitter}">
                            <!-- Private icon-->
                           <c:if test="${!study.public}">
                               <span class="show_tooltip icon icon-functional" data-icon="L" title="Private data"></span>
                           </c:if>
                           <c:if test="${study.public}">
                           <span class="show_tooltip icon icon-functional" data-icon="U" title="Public data"></span>
                           </c:if>
                       </c:if>

                    </td>

                    <td>
                        <a href="<c:url value="${baseURL}/projects/${study.studyId}#samples_id"/>">${study.sampleSize}</a>
                    </td>
                    <td>${study.formattedLastReceived}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <div class="msg_error">No data matching your search</div>
    </c:otherwise>
</c:choose>


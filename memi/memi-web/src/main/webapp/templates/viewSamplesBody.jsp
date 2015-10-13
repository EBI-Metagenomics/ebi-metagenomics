<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

 <div id="sample_list">
    <%--<h2>${pageTitle}</h2>--%>
    <h2>Samples list</h2>

    <div class="center">


        <div class="filter_form">
            <form:form method="GET" action="${pageContext.request.contextPath}/samples/doSearch"
                       commandName="sampleFilter">
                <fieldset>
                    <div class="result_row">
                        <label for="text">Text:</label>
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
                     <span><form:select id="sampleVisibility" path="sampleVisibility">
                         <form:options items="${model.sampleVisibilityList}"/>
                     </form:select>
                     </span>
                        </div>
                    </c:if>

                    <div class="result_row">
                        <div class="filter_button">
                            <input type="submit" name="search" value="Search" class="main_button"/>
                            <c:choose>
                                <c:when test="${empty model.submitter}">
                                    | <a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleVisibility=ALL_PUBLISHED_SAMPLES&search=Search&startPosition=0"/>"
                                        >Clear</a>
                                </c:when>
                                <c:otherwise>
                                    | <a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleVisibility=ALL_SAMPLES&search=Search&startPosition=0"/>"
                                       >Clear</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </fieldset>
                <form:hidden path="startPosition"></form:hidden>
            </form:form>
        </div>

    </div>


    <c:choose>
        <c:when test="${not empty model.samples}">
            <c:if test="${isDialogOpen==false}">
                <span class="error">No export data available for that(these) sample(s)!</span>
            </c:if>
            <%--Request the current query string to export only the filtered studies--%>
            <c:set var="queryString" value="${pageContext.request.queryString}" scope="session"/>

            <div class="table_opt">

            <div class="export">
                    <%--Don't show link to export full sample details here - just provide it on the individual sample page --%>
                    <%--<c:if test="${not empty sampleFilter.sampleType}">--%>
                    <%--<a href="<c:url value="${baseURL}/samples/doExportDetails?${queryString}"/>"  id="csv_plus" title="Export more detailed information about the samples below in CSV format">Export detailed info (CSV)</a>--%>
                    <%--</c:if>--%>
                <a href="<c:url value="${baseURL}/samples/doExportTable?${queryString}"/>" id="csv"
                   title="<spring:message code="viewSamples.download.anchor.title"/>">
                    <spring:message code="viewSamples.download.anchor.label"/></a>
            </div>

            <div class="table_opt_pag">

            <%--Start of item pagination pattern--%>
            <c:set var="prevId" value="< Prev"/>
            <c:set var="nextId" value="Next >"/>
            <c:set var="firstId" value="First"/>
            <c:set var="lastId" value="Last"/>

                <div class="table_opt_pag_num"><c:out value="${model.pagination.displayedItemRange}"/> of <c:out value="${model.pagination.totalItems}"/>
                </div>
                <div class="table_opt_pag_arr">
                <c:if test="${model.pagination.totalItems > model.pagination.pageSize}">
                    <c:choose>
                        <c:when test="${model.pagination.existPreviousStartPos}">

                             <div class="pag-first"><a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=${model.sampleFilter.searchTerm}&sampleVisibility=${model.sampleFilter.sampleVisibility.upperCaseString}&search=Search&startPosition=${model.pagination.start}&biome=${model.sampleFilter.biome}"/>"
                               id="csv" title="<c:out value="${firstId}"/>"></a></div>
                            <div class="pag-prev"><a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=${model.sampleFilter.searchTerm}&sampleVisibility=${model.sampleFilter.sampleVisibility.upperCaseString}&search=Search&startPosition=${model.pagination.previousStartPos}&biome=${model.sampleFilter.biome}"/>"
                               id="csv" title="<c:out value="${prevId}"/>"></a></div>
                        </c:when>
                        <c:otherwise><div class="pag-first-off"></div>  <div class="pag-prev-off"></div></c:otherwise>
                    </c:choose>
                    <%--<span style="float:left;padding:0 4px; color:#ABADB3;">prev | next</span>--%>
                    <c:choose>
                        <c:when test="${model.pagination.existNextStartPos}">
                           <div class="pag-next"><a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=${model.sampleFilter.searchTerm}&sampleVisibility=${model.sampleFilter.sampleVisibility.upperCaseString}&search=Search&startPosition=${model.pagination.nextStartPos}&biome=${model.sampleFilter.biome}"/>"
                               id="csv" title="<c:out value="${nextId}"/>"></a></div>
                             <div class="pag-last"><a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=${model.sampleFilter.searchTerm}&sampleVisibility=${model.sampleFilter.sampleVisibility.upperCaseString}&search=Search&startPosition=${model.pagination.lastLinkPosition}&biome=${model.sampleFilter.biome}"/>"
                               id="csv" title="<c:out value="${lastId}"/>"></a></div>
                        </c:when>
                        <c:otherwise><div class="pag-next-off"></div> <div class="pag-last-off"></div></c:otherwise>
                    </c:choose>
                </c:if>
                </div>
            </div>
            <%--End of item pagination pattern--%>
            </div>
            <table border="1" class="result">
                <thead>
                <tr>
                    <c:forEach var="headerName" items="${model.tableHeaderNames}">
                        <c:set var="headerWidth" value="" scope="page"/>
                        <c:set var="headerId" value="" scope="page"/>
                        <c:choose>
                            <c:when test="${headerName == 'Sample name' || headerName == 'Project name'}">
                                <c:set var="headerId" value="h_left" scope="page"/>
                            </c:when>
                            <c:when test="${headerName == 'Biome'}">
                                <c:set var="headerWidth" value="40px" scope="page"/>
                            </c:when>

                            <c:otherwise>
                                <c:set var="headerWidth" scope="page"/>
                            </c:otherwise>
                        </c:choose>
                        <th class="${headerId}" abbr="${headerName}" width="${headerWidth}" scope="col">${headerName}</th>
                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="sample" items="${model.samples}" varStatus="status">
                    <tr>
                        <td>
                        <span class="biome_icon icon_xs ${sample.biomeIconCSSClass}" title="${sample.biomeIconTitle} biome"></span>
                        </td>
                        <td width="8%">${sample.sampleId}</td>
                        <td class="h_left" id="ordered" width="30%">

                            <a href="<c:url value="${baseURL}/projects/${sample.study.studyId}/samples/${sample.sampleId}"/>" class="fl_uppercase_title">${sample.sampleName}</a>

                           <%-- Show icon only for people are are logged in--%>
                           <c:if test="${not empty model.submitter}">
                            <!-- Private icon-->
                           <c:if test="${!sample.public}">
                               <span class="show_tooltip icon icon-functional" data-icon="L" title="Private data"></span>
                           </c:if>
                           <c:if test="${sample.public}">
                           <span class="show_tooltip icon icon-functional" data-icon="U" title="Public data"></span>
                           </c:if>
                           </c:if>

                        </td>
                        <td class="h_left" width="62%">${sample.study.studyName}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="msg_error">No data matching your search</div>
        </c:otherwise>
    </c:choose>
</div>
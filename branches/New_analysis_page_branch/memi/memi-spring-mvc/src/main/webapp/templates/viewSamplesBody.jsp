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
                            <%--Used sample type instead of study type to not confuse the user--%>
                        <label for="source">Source:</label>
                    <span>
                        <form:select path="sampleType">
                            <form:option value="" label="All"/>
                            <form:option value="ENVIRONMENTAL" label="Environmental"/>
                            <form:option value="HOST_ASSOCIATED" label="Host associated"/>
                            <form:option value="ENGINEERED" label="Man-made"/>
                            <%--<form:options items="${model.sampleTypes}"/>--%>
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
                          
                             <div class="pag-first"><a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=${model.sampleFilter.searchTerm}&sampleVisibility=${model.sampleFilter.sampleVisibility.upperCaseString}&search=Search&startPosition=${model.pagination.start}&sampleType=${model.sampleFilter.sampleType.upperCaseString}"/>"
                               id="csv" title="<c:out value="${firstId}"/>"></a></div>
                            <div class="pag-prev"><a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=${model.sampleFilter.searchTerm}&sampleVisibility=${model.sampleFilter.sampleVisibility.upperCaseString}&search=Search&startPosition=${model.pagination.previousStartPos}&sampleType=${model.sampleFilter.sampleType.upperCaseString}"/>"
                               id="csv" title="<c:out value="${prevId}"/>"></a></div>
                        </c:when>
                        <c:otherwise><div class="pag-first-off"></div>  <div class="pag-prev-off"></div></c:otherwise>
                    </c:choose>
                    <%--<span style="float:left;padding:0 4px; color:#ABADB3;">prev | next</span>--%>
                    <c:choose>
                        <c:when test="${model.pagination.existNextStartPos}">
                           <div class="pag-next"><a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=${model.sampleFilter.searchTerm}&sampleVisibility=${model.sampleFilter.sampleVisibility.upperCaseString}&search=Search&startPosition=${model.pagination.nextStartPos}&sampleType=${model.sampleFilter.sampleType.upperCaseString}"/>"
                               id="csv" title="<c:out value="${nextId}"/>"></a></div>
                             <div class="pag-last"><a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=${model.sampleFilter.searchTerm}&sampleVisibility=${model.sampleFilter.sampleVisibility.upperCaseString}&search=Search&startPosition=${model.pagination.lastLinkPosition}&sampleType=${model.sampleFilter.sampleType.upperCaseString}"/>"
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

                            <c:when test="${headerName == 'Source'}">
                                <c:set var="headerWidth" value="120px" scope="page"/>
                            </c:when>
                            <%--The Otherwise case is for header name Analysis--%>
                            <c:otherwise>
                                <c:set var="headerWidth" value="170px" scope="page"/>
                            </c:otherwise>
                        </c:choose>
                        <th class="${headerId}" abbr="${headerName}" width="${headerWidth}" scope="col">${headerName}</th>
                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="sample" items="${model.samples}" varStatus="status">
                    <tr>
                        <td class="h_left" id="ordered">
                            <c:if test="${!sample.public}"><img alt="private"
                                                                src="${pageContext.request.contextPath}/img/icon_priv_private.gif">&nbsp;&nbsp;</c:if>
                            <a href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>">${sample.sampleName}</a>
                        </td>
                        <td class="h_left" width="240px">${sample.study.studyName}</td>
                        <td>${sample.sampleTypeAsString}</td>
                        <td>
                            <c:choose>
                                <c:when test="${empty sample.analysisCompleted}">in progress
                                    <%--<img
                               src="${pageContext.request.contextPath}/img/ico_IN_PROGRESS_25_8.png"
                               alt="Analysis in progress" title="Analysis in progress">--%></c:when>
                                <c:otherwise>
                                    <%--<a href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>"><img--%>
                                            <%--src="${pageContext.request.contextPath}/img/ico_analysis_chart.gif"--%>
                                            <%--alt="Analysis finished - check the results"--%>
                                            <%--title="Analysis finished - check the results"></a>--%>
                           <a href="<c:url value="${baseURL}/sample/${sample.sampleId}#Taxonomy-Analysis"/>" class="list_sample" title="Taxonomy analysis">Taxonomy </a>|
                          <a href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>#Functional-Analysis" class="list_sample" title="Function analysis">Function </a>|
                          <a class="icon icon-functional list_sample" data-icon="=" href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>#Download" class="list_sample" title="download results"></a>
                                </c:otherwise>
                            </c:choose>
                        </td>
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
    <div class="but_top"><a href="#top" title="back to the top page">Top</a></div>

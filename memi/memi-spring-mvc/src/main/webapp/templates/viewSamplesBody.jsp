<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="content-full">
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
                                 | <a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleVisibility=ALL_PUBLISHED_SAMPLES&search=Search"/>"
                                          title="View samples">Clear</a>
                                </c:when>
                                <c:otherwise>
                                | <a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleVisibility=ALL_SAMPLES&search=Search"/>"
                                           title="View samples">Clear</a>
                                </c:otherwise>
                            </c:choose>
                            </div>
                    </div>
                </fieldset>
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

            <div class="export">
                    <%--Don't show link to export full sample details here - just provide it on the individual sample page --%>
                    <%--<c:if test="${not empty sampleFilter.sampleType}">--%>
                    <%--<a href="<c:url value="${baseURL}/samples/doExportDetails?${queryString}"/>"  id="csv_plus" title="Export more detailed information about the samples below in CSV format">Export detailed info (CSV)</a>--%>
                    <%--</c:if>--%>
                <a href="<c:url value="${baseURL}/samples/doExportTable?${queryString}"/>" id="csv"
                   title="<spring:message code="viewSamples.download.anchor.title"/>">
                    <spring:message code="viewSamples.download.anchor.label"/></a>
            </div>

            <table border="1" class="result">
                <thead>
                <tr>
                    <th scope="col" abbr="Sname" id="h_left">Sample name</th>
                    <th scope="col" abbr="Pname" id="h_left">Project name</th>
                    <th scope="col" abbr="Source" width="140px">Source</th>
                    <th scope="col" abbr="Analysis" width="80px">Analysis</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="sample" items="${model.samples}" varStatus="status">
                    <tr>
                        <td style="text-align:left;" id="ordered">
                            <c:if test="${!sample.public}"><img alt="private"
                                                                src="${pageContext.request.contextPath}/img/icon_priv_private.gif">&nbsp;&nbsp;</c:if>
                            <a href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>">${sample.sampleName}</a>
                        </td>
                        <td style="text-align:left;">${sample.study.studyName}</td>
                        <td>${sample.sampleTypeAsString}</td>
                        <td>
                            <c:choose>
                                <c:when test="${empty sample.analysisCompleted}">in progress
                                    <%--<img
                               src="${pageContext.request.contextPath}/img/ico_IN_PROGRESS_25_8.png"
                               alt="Analysis in progress" title="Analysis in progress">--%></c:when>
                                <c:otherwise>
                                    <a href="<c:url value="${baseURL}/analysisStatsView/${sample.sampleId}"/>"><img
                                            src="${pageContext.request.contextPath}/img/ico_FINISHED_25_8.png"
                                            alt="Analysis finished - check the results"
                                            title="Analysis finished - check the results"></a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="error">No data matching your search</div>
        </c:otherwise>
    </c:choose>
    <div class="but_top"><a href="#top" title="back to the top page">Top</a></div>
</div>

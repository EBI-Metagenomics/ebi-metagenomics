<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="content-full">
    <%--<h2>${pageTitle}</h2>--%>
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

                    <c:if test="${not empty model.submitter}">
                        <div class="result_row">
                            <label for="privacy">Privacy:</label>
                     <span>
                      <form:select id="studyVisibility" path="studyVisibility">
                          <form:options items="${model.studyVisibilityList}"/>
                      </form:select>
                     </span>
                        </div>
                    </c:if>

                    <div class="result_row">
                        <div class="filter_button">
                            <input type="submit" name="search" value="Search" class="main_button"/>
                            <c:choose>
                                <c:when test="${empty model.submitter}">
                                    | <a href="<c:url value="${baseURL}/projects/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_PROJECTS"/>">Clear</a>
                                </c:when>
                                <c:otherwise>
                                    | <a href="<c:url value="${baseURL}/projects/doSearch?search=Search&studyVisibility=ALL_PROJECTS"/>" >Clear</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </fieldset>
            </form:form>

        </div>

    </div>

    <c:choose>
        <c:when test="${not empty model.studySampleSizeMap}">
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
                            <%--The Otherwise case is for header name Submitted date--%>
                            <c:otherwise>
                                <c:set var="headerWidth" value="120px" scope="page"/>
                            </c:otherwise>
                        </c:choose>
                        <th id="${headerId}" abbr="${headerName}" width="${headerWidth}" scope="col">${headerName}</th>
                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="entry" items="${model.studySampleSizeMap}" varStatus="status">
                    <tr>
                        <td style="text-align:left;" id="ordered">
                            <c:if test="${!entry.key.public}"><img alt="private"
                                                                   src="${pageContext.request.contextPath}/img/icon_priv_private.gif">&nbsp;&nbsp;</c:if>
                            <a href="<c:url value="${baseURL}/project/${entry.key.studyId}"/>">${entry.key.studyName}</a>
                        </td>
                        <td>
                            <a href="<c:url value="${baseURL}/project/${entry.key.studyId}#samples_id"/>">${entry.value}</a>
                        </td>
                        <td>${entry.key.formattedLastReceived}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="error">No data matching your search</div>
        </c:otherwise>
    </c:choose>

</div>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="content-full">
    <h2>${pageTitle}</h2>

    <div align="center">
        <table>
            <form:form method="GET" action="${baseURL}/viewStudies/doSearch" commandName="studyFilter">
                <tr>
                    <td>Text:</td>
                    <td><form:input id="autocomplete" path="searchTerm"/></td>
                    <td><form:errors path="searchTerm" cssClass="error"/></td>
                </tr>
                <tr>
                    <td>Study type:</td>
                    <td>
                        <form:select path="studyType">
                            <form:option value="All" label="All"/>
                            <form:options items="${model.studyTypes}"/>
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td>Analysis status:</td>
                    <td><form:select id="studyStatus" path="studyStatus">
                        <form:option value="All" label="All"/>
                        <form:options items="${model.studyStatusList}"/>
                    </form:select></td>
                </tr>
                <c:if test="${not empty model.submitter}">
                    <tr>
                        <td>Privacy:</td>
                        <td><form:select id="studyVisibility" path="studyVisibility">
                            <form:options items="${model.studyVisibilityList}"/>
                        </form:select></td>
                    </tr>
                </c:if>
                <tr>
                    <td></td>
                    <td align="right">
                        <input type="submit" name="search" value="Search"/>
                    </td>
                    <td>
                        <input type="submit" name="clear" value="Clear"/>
                    </td>
                </tr>
            </form:form>
        </table>
    </div>
    <div style="margin-top:40px"></div>
    <%--Request the current query string to export only the filtered studies--%>
    <c:set var="queryString" value="${pageContext.request.queryString}" scope="session"/>
    <div align="left">
        <a href="<c:url value="${baseURL}/viewStudies/doExport?${queryString}"/>">Export to CSV</a>
    </div>

    <table border="1" width="95%">
        <tr>
            <th>No.</th>
            <c:if test="${not empty model.submitter}">
                <th>Privacy</th>
            </c:if>
            <th>Study Id</th>
            <th>Study name</th>
            <th>Study type</th>
            <th>Received date</th>
            <th>Analysis status</th>
            <th>Experimental factor</th>
            <th>NCBI Project Id</th>
        </tr>
        <%
            int i = 1;
        %>
        <c:forEach var="study" items="${model.studies}" varStatus="status">
            <tr>
                <td align="center"><%= i%><% i++;%></td>
                <c:if test="${not empty model.submitter}">
                    <td>
                        <c:choose>
                            <c:when test="${study.public}">
                                <img src="/img/icon_priv_public.gif" height="16" width="16" align="absmiddle" alt=""
                                     border="0"/>
                            </c:when>
                            <c:otherwise>
                                <img src="/img/icon_priv_lock.gif" height="16" width="16" align="absmiddle" alt=""
                                     border="0"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </c:if>
                <td>
                    <a href="<c:url value="${baseURL}/study/${study.studyId}"/>">${study.studyId}</a>
                </td>
                <td>${study.studyName}</td>
                <td>${study.studyType}</td>
                <td>${study.formattedLastReceived}</td>
                <td>${study.studyStatus}</td>
                <td>
                    <c:choose>
                        <c:when test="${empty study.experimentalFactor}">N/A</c:when>
                        <c:otherwise>${study.experimentalFactor}</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${study.ncbiProjectId > 0}">${study.ncbiProjectId}</c:when>
                        <c:otherwise>N/A</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div id="content-full">
    <h2>${pageTitle}</h2>

    <div align="center">
        <table>
            <form:form method="GET" action="${baseURL}/viewSamples/doSearch" commandName="sampleFilter">
                <tr>
                    <td>Text:</td>
                    <td><form:input path="searchTerm"/></td>
                    <td><form:errors path="searchTerm" cssClass="error"/></td>
                </tr>
                <%--Used sample type instead of study type to not confuse the user--%>
                <tr>
                    <td>Sample type:</td>
                    <td>
                        <form:select path="sampleType">
                            <form:option value="All" label="All"/>
                            <form:options items="${mgModel.sampleTypes}"/>
                        </form:select>
                    </td>
                </tr>
                <c:if test="${not empty mgModel.submitter}">
                    <tr>
                        <td>Privacy:</td>
                        <td><form:select id="sampleVisibility" path="sampleVisibility">
                            <form:options items="${mgModel.sampleVisibilityList}"/>
                        </form:select></td>
                    </tr>
                </c:if>
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
    <c:if test="${isDialogOpen==false}">
        <p><span style="color:red">No export data available for that(these) sample(s)!</span></p>
    </c:if>
    <%--Request the current query string to export only the filtered studies--%>
    <c:set var="queryString" value="${pageContext.request.queryString}" scope="session"/>
    <div align="left">
        <a href="<c:url value="${baseURL}/viewSamples/doExportTable?${queryString}"/>">Export table to CSV</a><br>
        <c:if test="${not empty sampleFilter.sampleType}">
            <a href="<c:url value="${baseURL}/viewSamples/doExportDetails?${queryString}"/>">
                Export more detailed sample info to CSV</a>
        </c:if>
    </div>

    <table border="1" width="95%">
        <tr>
            <th>No.</th>
            <th>Sample Id</th>
            <c:if test="${not empty mgModel.submitter}">
                <th>Privacy</th>
            </c:if>
            <th>Sample name</th>
            <th>Collection date</th>
            <th>Valid meta data</th>
            <th>Valid raw data</th>
            <th>Analysis completed</th>
            <th>Archived in ENA</th>
        </tr>
        <%
            int i = 1;
        %>
        <c:forEach var="sample" items="${mgModel.samples}" varStatus="status">
            <tr>
                <td align="center"><%= i%><% i++;%></td>
                <td>
                    <a href="<c:url value="${baseURL}/sampleView/${sample.sampleId}"/>">${sample.sampleId}</a>
                </td>
                <c:if test="${not empty mgModel.submitter}">
                    <td>
                        <c:choose>
                            <c:when test="${sample.public}">
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
                <td>${sample.sampleTitle}</td>
                <td>
                    <c:choose>
                        <c:when test="${empty sample.collectionDate}">N/A</c:when>
                        <c:otherwise>${sample.collectionDate}</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${empty sample.metadataReceived}">
                            <img src="/img/error.gif" height="16" width="16" align="absmiddle" alt=""
                                 border="0"/>
                        </c:when>
                        <c:otherwise>
                            <img src="/img/check.gif" height="16" width="16" align="absmiddle" alt=""
                                 border="0"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${empty sample.sequenceDataReceived}">
                            <img src="/img/error.gif" height="16" width="16" align="absmiddle" alt=""
                                 border="0"/>
                        </c:when>
                        <c:otherwise>
                            <img src="/img/check.gif" height="16" width="16" align="absmiddle" alt=""
                                 border="0"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${empty sample.analysisCompleted}">
                            <img src="/img/error.gif" height="16" width="16" align="absmiddle" alt=""
                                 border="0"/>
                        </c:when>
                        <c:otherwise>
                            <img src="/img/check.gif" height="16" width="16" align="absmiddle" alt=""
                                 border="0"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${empty sample.sequenceDataArchived}">
                            <img src="/img/error.gif" height="16" width="16" align="absmiddle" alt=""
                                 border="0"/>
                        </c:when>
                        <c:otherwise>
                            <img src="/img/check.gif" height="16" width="16" align="absmiddle" alt=""
                                 border="0"/>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
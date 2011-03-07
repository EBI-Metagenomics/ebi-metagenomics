<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div id="content-full">
    <h2>${pageTitle}</h2>


        <div class="filter">
            <form:form method="GET" action="${baseURL}/samples/doSearch" commandName="sampleFilter">
               Text: <form:input path="searchTerm"/> <form:errors path="searchTerm" cssClass="error"/><br/><br/>
                       <%--Used sample type instead of study type to not confuse the user--%>
                Sample type: <form:select path="sampleType">
                            <form:option value="All" label="All"/>
                            <form:options items="${model.sampleTypes}"/>
                        </form:select>

              <br/><br/>
                <c:if test="${not empty model.submitter}">
                    Privacy:<form:select id="sampleVisibility" path="sampleVisibility">
                            <form:options items="${model.sampleVisibilityList}"/>
                        </form:select>
                </c:if>


                    <input type="submit" name="search" value="Search"/> <input type="submit" name="clear" value="Clear"/>

            </form:form>
        </div>


    <div style="margin-top:40px"></div>
    <c:choose>
        <c:when test="${not empty model.samples}">
            <c:if test="${isDialogOpen==false}">
                <p><span style="color:red">No export data available for that(these) sample(s)!</span></p>
            </c:if>
            <%--Request the current query string to export only the filtered studies--%>
            <c:set var="queryString" value="${pageContext.request.queryString}" scope="session"/>
            <div>
                <a href="<c:url value="${baseURL}/samples/doExportTable?${queryString}"/>">Export table to CSV</a><br>
                <c:if test="${not empty sampleFilter.sampleType}">
                    <a href="<c:url value="${baseURL}/samples/doExportDetails?${queryString}"/>">
                        Export more detailed sample info to CSV</a>
                </c:if>
            </div>

            <table border="1">
                <tr>
                    <th>Sample name</th>
                    <th>Project name</th>
                    <th>Source</th>
                    <th>Analysis</th>
                </tr>
                <c:forEach var="sample" items="${model.samples}" varStatus="status">
                    <tr>
                        <td>
                            <a href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>">${sample.sampleTitle}</a>
                            <c:if test="${!sample.public}">
                                <img src="/img/icon_priv_lock.gif" height="16" width="16" align="absmiddle" alt=""
                                     border="0"/>
                            </c:if>
                        </td>
                        <td>${sample.study.studyName}</td>
                        <td>${sample.sampleTypeAString}</td>
                        <td>
                            <c:choose>
                                <c:when test="${empty sample.analysisCompleted}">In Progress</c:when>
                                <c:otherwise>
                                    <a href="<c:url value="${baseURL}/analysisStatsView/${sample.sampleId}"/>">Results</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <div ><b>No data to display</b></div>
        </c:otherwise>
    </c:choose>
 </div>
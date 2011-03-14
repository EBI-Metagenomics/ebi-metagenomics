<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="content-full">
    <h2>${pageTitle}</h2>

     <div class="filter">

            <c:choose>
                <c:when test="${empty model.submitter}">
                    <c:set var="actionUrlParam" value="studyVisibility=ALL_PUBLISHED_STUDIES"/>
                </c:when>
                <c:otherwise>
                    <c:set var="actionUrlParam" value=""/>
                </c:otherwise>
            </c:choose>
            <form:form method="GET" action="${baseURL}/studies/doSearch" commandName="studyFilter">
               Text:<form:input id="autocomplete" path="searchTerm"/> <form:errors path="searchTerm" cssClass="error"/><br/> <br/>
               Analysis status:
                        <form:select id="studyStatus" path="studyStatus">
                            <form:option value="" label="All"/>
                            <form:option value="IN_PROGRESS" label="In Progress"/>
                            <form:option value="FINISHED" cssClass="imagebacked" cssStyle="background-image: url(/img/ico_warning_8.png);" label="Finish"/>
                            <%--<form:options id="" items="${model.studyStatusList}"/>--%>
                        </form:select>
                  <br/> <br/>
                <c:if test="${not empty model.submitter}">
                   Privacy:<form:select id="studyVisibility" path="studyVisibility">
                                <form:options items="${model.studyVisibilityList}"/>
                            </form:select>

                </c:if>

                        <input type="submit" name="search" value="Search" class="main_button"/>

                        <input type="submit" name="clear" value="Clear" class="main_button"/>

            </form:form>
        
    </div>

    <div style="margin-top:40px"></div>
    <c:choose>
        <c:when test="${not empty model.studySampleSizeMap}">
            <%--Request the current query string to export only the filtered studies--%>
            <c:set var="queryString" value="${pageContext.request.queryString}" scope="session"/>
            <div align="left">
                <a href="<c:url value="${baseURL}/studies/doExport?${queryString}"/>">Export table to CSV</a>
            </div>

            <table border="1" width="95%">
                <tr>
                    <th>Project Name</th>
                    <th>Number Of Samples</th>
                    <th>Submitted Date</th>
                    <th>Analysis</th>
                </tr>
                <c:forEach var="entry" items="${model.studySampleSizeMap}" varStatus="status">
                    <tr>
                        <td>
                            <a href="<c:url value="${baseURL}/study/${entry.key.studyId}"/>">${entry.key.studyName}</a>
                            <c:if test="${!entry.key.public}">
                                <img src="/img/icon_priv_lock.gif" height="16" width="16" align="absmiddle" alt=""
                                     border="0"/>
                            </c:if>
                        </td>
                        <td>${entry.value}</td>
                        <td>${entry.key.formattedLastReceived}</td>
                        <td>${entry.key.studyStatusAsString}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <div align="center"><b>No data to display</b></div>
        </c:otherwise>
    </c:choose>
</div>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="content-full">
    <h2>${pageTitle}</h2>

     <div class="center">
     <div id="filter">
            <c:choose>
                <c:when test="${empty model.submitter}">
                    <c:set var="actionUrlParam" value="studyVisibility=ALL_PUBLISHED_STUDIES"/>
                </c:when>
                <c:otherwise>
                    <c:set var="actionUrlParam" value=""/>
                </c:otherwise>
            </c:choose>
            <form:form method="GET" action="${baseURL}/studies/doSearch" commandName="studyFilter">
               <fieldset>
               <label for="text">Text:</label>
               <form:input id="autocomplete" path="searchTerm"/><br/><form:errors path="searchTerm" cssClass="error"/><br/>
               <label for="status"> Analysis status:</label>

                   <form:select id="studyStatus" path="studyStatus">
                   <form:option value="" label="All"/>
                   <form:option value="IN_PROGRESS" label="In Progress" cssClass=""/>
                   <form:option value="FINISHED" label="Complete" cssClass=""/>
                   <%--<form:options items="${model.studyStatusList}"/>--%>
                   </form:select>

                <c:if test="${not empty model.submitter}"><br/><br/>
                <label for="privacy">Privacy:</label>
                <form:select id="studyVisibility" path="studyVisibility">
                <form:options items="${model.studyVisibilityList}"/>
                </form:select>
                </c:if>

                <div id="filter-button">
                <input type="submit" name="search" value="Search" class="main_button"/>
                <input type="submit" name="clear" value="Clear" class="main_button"/>
                </div>
            </fieldset>
            </form:form>
        
    </div></div>

      <c:choose>
        <c:when test="${not empty model.studySampleSizeMap}">
            <%--Request the current query string to export only the filtered studies--%>
            <c:set var="queryString" value="${pageContext.request.queryString}" scope="session"/>
            <div class="export">
               <a href="<c:url value="${baseURL}/studies/doExport?${queryString}"/>" id="csv" title="Export table in CSV format">Export table (CSV)</a>
            </div>

            <table border="1" class="result">
                <thead>
                <tr>
                    <th scope="col" abbr="Name">Project name</th>
                    <th scope="col" abbr="Samples"  width="60px">Samples</th>
                    <th scope="col" abbr="Date" width="120px">Submitted date</th>
                    <th scope="col" abbr="Analysis" width="80px">Analysis</th>
                </tr>
                </thead>

                <tbody>

                <c:forEach var="entry" items="${model.studySampleSizeMap}" varStatus="status">
                    <tr>
                        <td style="text-align:left;">
                             <c:if test="${!entry.key.public}"><img alt="private" src="../img/icon_priv_private.gif">&nbsp;&nbsp;</c:if>
                             <a href="<c:url value="${baseURL}/study/${entry.key.studyId}"/>">${entry.key.studyName}</a>                           
                        </td>
                        <td>${entry.value}</td>
                        <td id="ordered">${entry.key.formattedLastReceived}</td>
                        <td><img src="../img/ico_${entry.key.studyStatus}_small_20.png" alt="${entry.key.studyStatusAsString}" title="${entry.key.studyStatusAsString}"> </td>
                    </tr>
                </tbody>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <div class="error">No data matching your search</div>
        </c:otherwise>
    </c:choose>
</div>
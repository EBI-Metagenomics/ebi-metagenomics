<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sample overview</title>
    <link href="/css/memi.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
<div id="content">
    <h2>Study overview - ${study.studyName} (ID ${study.studyId}) </h2>

    <div style="margin-top:6px"></div>
    <div>
        <table border="0" style="border-width: 1px;border-color: #000000;border-style: solid;">
            <c:forEach var="item" items="${studyPropertyMap}">
                <tr>
                    <c:if test="${item.key!='StudyAbstract'}">
                        <td valign="top"><c:out value="${item.key}:"/></td>
                        <td align="left"><c:out value="${item.value}"/></td>
                    </c:if>
                </tr>
            </c:forEach>
            <tr>
                <td valign="top">Study abstract:</td>
                <td><textarea name=mytextarea cols=50 rows=5 readonly><c:out
                        value="${study.studyAbstract}"/></textarea></td>
            </tr>
        </table>
    </div>
    <%--<div style="margin-top:60px"/>--%>
    <%--<spring:url>Sample list</spring:url>--%>

    <%--<spring:url var="exportUrl" value="exportSamples/${study.studyId}"/>--%>

    <%--<div align="left"><a href="${exportUrl}">Export to CSV</a></div>--%>
    <%--<table border="1">--%>
    <%--<tr>--%>
    <%--<th>Item no.</th>--%>
    <%--<th>Sample Id</th>--%>
    <%--<th>Collection date</th>--%>
    <%--<th>Sample title</th>--%>
    <%--</tr>--%>
    <%--<%--%>
    <%--int i = 1;--%>
    <%--%>--%>
    <%--<c:forEach var="sample" items="${sampleList}" varStatus="status">--%>
    <%--<tr>--%>
    <%--<td align="center"><%= i%><% i++;%>--%>
    <%--</td>--%>
    <%--<td align="center">${sample.sampleId}</td>--%>
    <%--<td align="center">--%>
    <%--<c:choose>--%>
    <%--<c:when test="${sample.collectionDate!=null}">--%>
    <%--${sample.collectionDate}--%>
    <%--</c:when>--%>
    <%--<c:otherwise>N/A--%>
    <%--</c:otherwise>--%>
    <%--</c:choose>--%>
    <%--</td>--%>
    <%--<td align="center">--%>
    <%--<spring:url var="sampleUrl" value="../sampleOverview/${sample.sampleId}"/>--%>
    <%--<a href="${sampleUrl}">${sample.sampleTitle}</a>--%>
    <%--</td>--%>
    <%--</tr>--%>
    <%--</c:forEach>--%>
    <%--</table>--%>
    <%--</div>--%>
</div>
</body>
</html>
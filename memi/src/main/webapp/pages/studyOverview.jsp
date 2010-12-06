<%--
  Created by Maxim Scheremetjew, EMBL-EBI, InterPro
  Date: 30-Nov-2010
  Desc: Study overview page
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="spring" %>
<html>
<head>
    <title>Study overview</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="../css/memi.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
<div id="right_side_navigation">
    <p><a href="<c:url value="../homePage.htm"/>">Home</a></p>

    <p><a href="<c:url value="../installationSitePage.htm"/>">Export to PDF</a></p>

    <p><a href="<c:url value="../installationSitePage.htm"/>">Associated publications</a></p>
</div>
<div id="content">
    <div style="margin-top:60px"></div>
    <h2>Study overview - ${study.studyName} (ID ${study.studyId}) </h2>

    <div style="margin-top:6px"></div>
    <table border="0" style="border-width: 1px;border-color: #000000;border-style: solid;">
        <tr>
            <td>Study name:</td>
            <td><c:out value="${study.studyName}"/></td>
        </tr>
        <tr>
            <td>Study submitter:</td>
            <td>N/A</td>
        </tr>
        <tr>
            <td>Study type:</td>
            <td><c:out value="${study.studyType}"/></td>
        </tr>
        <tr>
            <td>Submit date:</td>
            <td><c:out value="${study.formattedSubmitDate}"/></td>
        </tr>
        <tr>
            <td>Study status:</td>
            <td><c:out value="${study.analyseStatus}"/></td>
        </tr>
        <tr>
            <td>Public:</td>
            <td>
                <c:choose>
                    <c:when test="${study.public}">yes</c:when>
                    <c:otherwise>no</c:otherwise>
                </c:choose>
            </td>
        </tr>
        <tr>
            <td>Release date:</td>
            <td><c:out value="${study.formattedReleaseDate}"/></td>
        </tr>
        <tr>
            <td>Sample size:</td>
            <td><c:out value="${study.sampleSize}"/></td>
        </tr>
        <tr>
            <td valign="top">Study abstract:</td>
            <td><textarea name=mytextarea cols=50 rows=5 readonly><c:out
                    value="This is a non editable text area ..........."/></textarea></td>
        </tr>
    </table>

    <div style="margin-top:60px"></div>
    <h3>Sample list</h3>
    <table border="1">
        <tr>
            <th>Sample Id</th>
            <th>Sample name</th>
            <th>Sample date</th>
            <c:forEach var="item" items="${samplePropertyList}">
                <th><c:out value="${item}"/></th>
            </c:forEach>
            <th>Sample Overview</th>
        </tr>
        <c:forEach var="sample" items="${sampleList}" varStatus="status">
            <tr>
                <spring:url var="sampleUrl" value="../sampleOverview/${sample.sampleId}"/>

                <td>${sample.sampleId}</td>
                <td>
                    <a href='<c:out value="${sampleUrl}"/>'>${sample.sampleName}</a>
                </td>
                <td>${sample.formattedCollectionDate}</td>
                <c:forEach var="item" items="${sample.sampleProperties}">
                    <td><c:out value="${item}"/></td>
                </c:forEach>
                <td>
                    <a href='<c:out value="${sampleId}"/>'>Show overview</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
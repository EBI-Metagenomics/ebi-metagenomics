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
    <link href="/css/memi.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
<div id="right_side_navigation">
    <p><a href="<c:url value="../homePage"/>">Home</a></p>

    <p><a href="<c:url value="../installationSitePage"/>">Export to PDF</a></p>

    <p><a href="<c:url value="../installationSitePage"/>">Associated publications</a></p>
</div>
<div id="content">
    <div style="margin-top:60px"></div>
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
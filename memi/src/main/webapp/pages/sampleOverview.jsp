<%@ page import="uk.ac.ebi.interpro.metagenomics.memi.model.Sample" %>
<%@ page import="java.util.Enumeration" %>
<%--
  Created by Maxim Scheremetjew, EMBL-EBI, InterPro
  Date: 30-Nov-2010
  Desc: Study overview page
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Study overview</title>
    <link href="../css/memi.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
<div id="right_side_navigation">
    <p><a href="<c:url value="../homePage.htm"/>">Home</a></p>
    <p><a href="<c:url value="../installationSitePage.htm"/>">Export to PDF</a></p>
</div>
<div id="content">
    <div style="margin-top:60px"></div>
    <h2>Sample overview - ${sample.sampleName} (ID ${sample.sampleId}) </h2>

    <div style="margin-top:6px"></div>
    <table border="0" style="border-width: 1px;border-color: #000000;border-style: solid;">
        <tr>
            <td>Sample name:</td>
            <td><c:out value="${sample.sampleName}"/></td>
        </tr>
        <tr>
            <td>Collection date:</td>
            <td><c:out value="${sample.formattedCollectionDate}"/></td>
        </tr>
        <c:forEach items="${sample.propertyMap}" var="entry">
            <tr>
                <td><c:out value="${entry.key}"/></td>
                <td><c:out value="${entry.value}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
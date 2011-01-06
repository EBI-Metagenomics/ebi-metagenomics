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

    <%-- CSS file sources --%>
    <%--Link to the CSS file, which includes CSS classes for the EBI main header and footer--%>
    <%--<link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/contents.css" type="text/css"/>--%>
    <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/userstyles.css" type="text/css"/>
    <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/sidebars.css" type="text/css"/>
    <%--Link to the Memi project CSS file--%>
    <link href="/css/memi.css" rel="stylesheet" type="text/css" media="all"/>

    <%-- JavaScript sources --%>
    <script src="http://www.ebi.ac.uk/inc/js/contents.js" type="text/javascript"></script>
</head>
<body onload="if(navigator.userAgent.indexOf('MSIE') != -1) {document.getElementById('head').allowTransparency = true;}">

<%-- EBI main header--%>
<div class="headerdiv" id="headerdiv" style="position:absolute; z-index: 1;">
    <%-- The latest version of the EBI main header can be viewed on http://www.ebi.ac.uk/inc/head.html--%>
    <%@include file='/inc/head.html' %>
</div>
<div id="right_side_navigation">
    <p><a href="<c:url value="../installationSitePage"/>">Associated publications</a></p>
</div>
<div id="content">
    <div style="margin-top:80px"/>
    <a href="..//homePage" title="Home">Home</a>
    <a href="../listStudies" title="Search study">Search study</a>
    <a href="../installationSitePage" title="Help">Help</a>

    <div style="margin-top:60px"/>
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

        <div style="margin-top:60px"/>
        <h3>Sample list</h3>

        <spring:url var="exportUrl" value="exportSamples/${study.studyId}"/>
        <div align="left"><a href="${exportUrl}">Export to CSV</a></div>
        <table border="1">
            <tr>
                <th>Item no.</th>
                <th>Sample Id</th>
                <th>Collection date</th>
                <th>Sample title</th>
            </tr>
            <%
                int i = 1;
            %>
            <c:forEach var="sample" items="${sampleList}" varStatus="status">
                <tr>
                    <td align="center"><%= i%><% i++;%>
                    </td>
                    <td align="center">${sample.sampleId}</td>
                    <td align="center">
                        <c:choose>
                            <c:when test="${sample.collectionDate!=null}">
                                ${sample.collectionDate}
                            </c:when>
                            <c:otherwise>N/A
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td align="center">
                        <spring:url var="sampleUrl" value="../sampleOverview/${sample.sampleId}"/>
                        <a href="${sampleUrl}">${sample.sampleTitle}</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<div style="margin-bottom:290px"></div>
<%@include file='/templates/footer.jsp' %>
</body>
</html>
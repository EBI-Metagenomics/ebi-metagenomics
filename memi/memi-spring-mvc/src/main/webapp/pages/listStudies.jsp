<%--
  Created by Maxim Scheremetjew, EMBL-EBI, InterPro
  Date: 29-Nov-2010
  Desc: Represents a page which lists all studies within a table.
  Example URL: http://www.springbyexample.org/examples/sdms-simple-spring-mvc-web-module.html
  --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Overview about all studies</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <%-- CSS file sources --%>
    <%--Link to the CSS file, which includes CSS classes for the EBI main header and footer--%>
    <%--<link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/contents.css" type="text/css"/>--%>
    <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/userstyles.css" type="text/css"/>
    <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/sidebars.css" type="text/css"/>
    <%--Link to the Memi project CSS file--%>
    <link href="../css/memi.css" rel="stylesheet" type="text/css" media="all"/>

    <%-- JavaScript sources --%>
    <script src="http://www.ebi.ac.uk/inc/js/contents.js" type="text/javascript"></script>
</head>
<%-- The setting of the onload attribute is necessary to ensure that the EBI main header works in IE--%>
<%-- For more information on how to create EBI group and project specific pages please read the guideline on
    http://www.ebi.ac.uk/inc/template/#important--%>
<body onload="if(navigator.userAgent.indexOf('MSIE') != -1) {document.getElementById('head').allowTransparency = true;}">

<%-- EBI main header--%>
<div class="headerdiv" id="headerdiv" style="position:absolute; z-index: 1;">
    <%-- The latest version of the EBI main header can be viewed on http://www.ebi.ac.uk/inc/head.html--%>
    <%@include file='/inc/head.html' %>
</div>
<div id="right_side_navigation">
    <table border="0" style="border-width: 1px;border-color: #000000;border-style: solid;">
        <form:form action="listStudies.htm" commandName="filterForm">
            <tr>
                <td>Study type:</td>
                <td>
                    <form:select path="studyType">
                        <form:option value="NONE" label="--- Select ---"/>
                        <form:options items="${studyTypeList}" itemValue="studyTypeId"
                                      itemLabel="studyTypeName"/>
                    </form:select>
                </td>
                <td><form:errors path="studyType" cssClass="error"/></td>
            </tr>
            <tr>
                <td>Study status:</td>
                <td><form:select path="studyStatus">
                    <form:option value="NONE" label="--- Select ---"/>
                    <form:options items="${studyStatusList}" itemValue="studyStatusId"
                                  itemLabel="studyStatusName"/>
                </form:select></td>
                <td><form:errors path="studyStatus" cssClass="error"/></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td>
                    <input type="submit" value="Do filter"/>
                </td>
            </tr>
        </form:form>
    </table>
</div>
<div id="content">
    <%--MG portal main menu template--%>
    <%@include file='/templates/mainMenu.jsp' %>
    <h2>Overview about all studies</h2>

    <spring:url var="exportUrl" value="listStudies/exportStudies"/>
    <div align="left"><a href="${exportUrl}">Export to CSV</a></div>

    <table border="1" width="95%">
        <tr>
            <th>Study Id</th>
            <th>Study name</th>
            <th>Release date</th>
            <th>NCBI Project Id</th>
            <th>Public</th>
            <th>Study type</th>
            <th>Experimental factor</th>
            <th>Number of samples</th>
            <th>Analysis status</th>
        </tr>
        <c:forEach var="study" items="${studyList}" varStatus="status">
            <tr>
                <spring:url var="studyUrl" value="studyOverview/${study.studyId}"/>

                <td>${study.studyId}</td>
                <td>
                    <a href="<c:url value="${studyUrl}"/>">${study.studyName}</a>
                </td>
                <td>${study.formattedReleaseDate}</td>
                <td>${study.ncbiProject}</td>
                <td>${study.submitterId}</td>
                <td>${study.studyType}</td>
                <td>${study.experimentalFactor}</td>
                <td>${study.numberSamples}</td>
                <td>N/A</td>
            </tr>
        </c:forEach>
    </table>
</div>
<div style="margin-bottom:380px"></div>
<%@include file='/templates/footer.jsp' %>
</body>
</html>
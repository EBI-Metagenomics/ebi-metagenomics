<%--
  Created by Maxim Scheremetjew, EMBL-EBI, InterPro
  Date: 25-Nov-2010
  Desc: Represents a submission form
  --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="label.submission.title"/></title>

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
<body onload="if(navigator.userAgent.indexOf('MSIE') != -1) {document.getElementById('head').allowTransparency = true;}">
<%-- EBI main header--%>
<div class="headerdiv" id="headerdiv" style="position:absolute; z-index: 1;">
    <%-- The latest version of the EBI main header can be viewed on http://www.ebi.ac.uk/inc/head.html--%>
    <%@include file='/inc/head.html' %>
</div>

<div id="content">
    <%--MG portal main menu template--%>
    <%@include file='/templates/mainMenu.jsp' %>
    <h2><fmt:message key="label.submission.title"/></h2>

    <div style="margin-top:6px"></div>
    <form:form action="submissionForm" commandName="subForm">
        <table>
            <tr>
                <td>Submission title*:<form:errors cssStyle="color:red;" path="subTitle"/></td>
                <td><form:input path="subTitle"/></td>
            </tr>
            <tr>
                <td>Release date:*<form:errors cssStyle="color:red;" path="releaseDate"/></td>
                <td><form:input path="releaseDate"/></td>
            </tr>
            <tr>
                <td>Data description:*<form:errors cssStyle="color:red;" path="dataDesc"/></td>
                <td><form:textarea path="dataDesc"/></td>
            </tr>
            <tr>
                <td>Human associated?<form:errors cssStyle="color:red;" path="humanAssociated"/></td>
                <td><form:checkbox path="humanAssociated"/></td>
            </tr>
            <tr>
                <td>Analysis required?<form:errors cssStyle="color:red;" path="humanAssociated"/></td>
                <td><form:checkbox path="humanAssociated"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Submit"/></td>
            </tr>
        </table>
    </form:form>
    <p>* Required</p>
</div>
<div style="margin-bottom:315px"></div>
<%@include file='/templates/footer.jsp' %>
</body>
</html>
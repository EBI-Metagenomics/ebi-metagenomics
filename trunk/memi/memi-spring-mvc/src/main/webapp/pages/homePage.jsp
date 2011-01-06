<%--
  Created by Maxim Scheremetjew, EMBL-EBI, InterPro
  Date: 11-Nov-2010
  Desc: Represents the Metagenomics portal home page, which inludes
  1. a login function
  2. a link to submission form
  3. a link of public and private (if you logged in) studies
  4. a link to study list
  4. info about what Metagenomics is and what kind of services this portal provides
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>MG Portal home page</title>
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
    <%--<p><a href="<c:url value="./loginForm"/>">Login or register</a></p>--%>
    <%--<%@include file='/pages/loginForm.jsp' %>--%>
    <table border="0" style="border-width: 1px;border-color: #000000;border-style: solid;">
        <tr>
            <td>
                <jsp:include page="/pages/loginForm.jsp"/>
            </td>
        <tr>
        <tr>
            <td>
                <a href="<c:url value="https://www.ebi.ac.uk/embl/genomes/submission/registration.jsf"/>"
                   title="Registration">How to register?</a>
            </td>
        <tr>
        <tr>
            <td><a href="<c:url value="https://www.ebi.ac.uk/embl/genomes/submission/forgot-passw.jsf?_afPfm=5"/>"
                   title="Request a new password"> Forgotten your password?</a></td>
        </tr>
    </table>

    <h3>Study list</h3>
    <table border="0" style="border-width: 1px;border-color: #000000;border-style: solid;">
        <c:forEach var="emg_study" items="${studyList}" varStatus="status">
        <tr>
                <spring:url var="studyUrl" value="studyOverview/${emg_study.studyId}"/>
            <td>
                <a href="${studyUrl}">${emg_study.formattedReleaseDate} - ${emg_study.studyName}</a>
            </td>
        <tr>
            </c:forEach>
    </table>
    <a href="<c:url value="./listStudies"/>">more</a>

    <h3>Other useful links</h3>

    <p><a href="<c:url value="./installationSitePage"/>">Sample list</a></p>

    <p><a href="mailto:maxim@ebi.ac.uk?cc=maxim.scheremetjew@gmail.com&subject=Request from the MG portal">Contact
        us</a></p>
</div>
<div id="content">
    <%--MG portal main menu template--%>
    <%@include file='/templates/mainMenu.jsp' %>

    <h2>EBI Metagenomics Portal</h2>

    <div style="margin-top:6px"></div>
    <h3>What's metagenomics?</h3>

    <p>The study of all genomes present in any given environment without the need for prior individual identification or
        amplification is termed metagenomics. For example, in its simplest form a metagenomic study might be the direct
        sequence results of DNA extracted from a bucket of sea water.<br><a
                href="<c:url value="./info"/>">more</a></p>

    <table border="0" width="95%" style="border-width: 1px;border-color: #000000;border-style: solid;">
        <tr>
            <td width="50%" align="center" valign="middle">
                <h3>Release news</h3>
                <table border="0" style="border-width: 1px;border-color: #000000;border-style: solid;">
                    <c:forEach var="news" items="${newsList}">
                        <tr>
                            <td><c:out value="${news.formattedAnnouncedDate} - ${news.newsHeadline}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
            <td width="50%" align="left" valign="top">
                <h3>Submission form</h3>
                <a href="<c:url value="./submissionForm"/>">Submit data</a>
            </td>
        </tr>
        <tr>
            <td width="50%" align="left" valign="top"/>
            <td width="50%" align="left" valign="top"><a href="<c:url value="./installationSitePage"/>">Analyse
                data</a></td>
        </tr>
    </table>
</div>
<div style="margin-bottom:200px"></div>
<%@include file='/templates/footer.jsp' %>
</body>
</html>
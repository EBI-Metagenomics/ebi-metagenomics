<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>MG Portal home page</title>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8" lang="java">

    <meta name="keywords" content="ebi,interpro,metagenomics"/>
    <meta name="description" content="Root template for the EBI MG portal."/>
    <meta name="author" content="InterPro developer team"/>


    <%-- CSS file sources --%>
    <%--Link to the CSS file, which includes CSS classes for the EBI main header and footer--%>
    <%--<link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/contents.css" type="text/css"/>--%>
    <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/userstyles.css" type="text/css"/>
    <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/sidebars.css" type="text/css"/>
    <%--Link to the Memi project CSS file--%>
    <link href="${pageContext.request.contextPath}/css/memi.css" rel="stylesheet" type="text/css" media="all"/>
    <%-- JQuery and JQuery UI source--%>
    <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/ui-lightness/jquery-ui.css"
          type="text/css" media="all"/>

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js" type="text/javascript"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>

    <%-- The date picker is used within the submission page --%>
    <script src="/js/datepicker.js" type="text/javascript"></script>
    <%-- The auto completion is used with the study search page --%>
    <script src="/js/autocompletion.js" type="text/javascript"></script>
    <%-- Used within the home page. The dialog message is trigger when someone tries to submit data when he is not logged in--%>
    <script src="/js/loginmessage.js" type="text/javascript"></script>
    <%--<script src="/js/login.js" type="text/javascript"></script>--%>
</head>
<%-- The setting of the onload attribute is necessary to ensure that the EBI main header works in IE--%>
<%-- For more information on how to create EBI group and project specific pages please read the guideline on
    http://www.ebi.ac.uk/inc/template/#important--%>
<body onload="if(navigator.userAgent.indexOf('MSIE') != -1) {document.getElementById('head').allowTransparency = true;}">
<%-- The following variable saves and provides the base URL for the whole application --%>
<c:set var="baseURL" value="${pageContext.request.contextPath}/memi" scope="session"/>
<div  style="overflow:auto;width:100%;height:100%;">
    <table>
        <tr>
            <td colspan="2">
                <tiles:insertAttribute name="header"/>
                <tiles:insertAttribute name="mainMenu"/>
            </td>
        </tr>
        <tr>
            <td width="88%">
                <tiles:insertAttribute name="body"/>
            </td>
            <td valign="top" width="12%">
                <tiles:insertAttribute name="rightMenu"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <tiles:insertAttribute name="footer"/>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
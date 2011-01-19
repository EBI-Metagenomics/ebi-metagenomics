<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
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
    <link href="../css/memi.css" rel="stylesheet" type="text/css" media="all"/>
    <%-- JQuery and JQuery UI source--%>
    <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/ui-lightness/jquery-ui.css"
          type="text/css" media="all"/>

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js" type="text/javascript"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>

    <%--<script src="/js/datepicker.js" type="text/javascript"></script>--%>

    <script>
        $(function() {
            $("#datepicker").datepicker({
                showOn: 'button',
                buttonText: 'Choose a date',
                buttonImage: '/images/calendar.gif',
                buttonImageOnly: false,
                numberOfMonths: 1,
                maxDate: '+24m',
                minDate: '0d',
                showButtonPanel: true
            });
        });
    </script>

</head>
<%-- The setting of the onload attribute is necessary to ensure that the EBI main header works in IE--%>
<%-- For more information on how to create EBI group and project specific pages please read the guideline on
    http://www.ebi.ac.uk/inc/template/#important--%>
<body onload="if(navigator.userAgent.indexOf('MSIE') != -1) {document.getElementById('head').allowTransparency = true;}">
<%--<div style="overflow:auto; height:1280px; width:1024px;">--%>
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
        <td width="12%">
            <tiles:insertAttribute name="rightMenu"/>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <tiles:insertAttribute name="footer"/>
        </td>
    </tr>
</table>
</body>
</html>
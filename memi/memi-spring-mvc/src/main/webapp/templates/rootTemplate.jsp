<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8" lang="en"/>
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.ico"/>
    <link rel="icon" type="image/ico" href="${pageContext.request.contextPath}/img/favicon.ico"/>
    <link href="../../common/assets/css/ebi/ebi_common.css" rel="stylesheet" type="text/css"/>
    <title><tiles:insertAttribute name="pageTitle"/></title>
    <meta name="description" content="EBI Metagenomics is a new web resource targeted at metagenomic researchers"/>
    <meta name="keywords"
          content="ebi, EBI, InterPro, interpro, metagenomics, metagenomic, metagenome, metagenomes, DNA, microbiology, microbial, ecology, organisms, microorganism, microorganisms, biodiversity, diversity, gene, genes, genome, genomes, genomic, genomics, ecogenomics, community genomics, genetic, sequencing, sequence, environment, environmental, ecosystem, ecosystems, samples, sample, annotation, protein, research, archive, metabolic, pathways, analysis, function, GAIA, shotgun, pyrosequencing, community, communities, metabolism, cultivation, bioinformatics, bioinformatic, database, metadata, dataset, data, repository,   "/>

    <%-- EBI style and code--%>
    <meta http-equiv="Content-Language" content="en-GB"/>
    <meta http-equiv="Window-target" content="_top"/>
    <meta name="no-email-collection" content="http://www.unspam.com/noemailcollection/"/>
    <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/contents.css" type="text/css"/>
    <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/userstyles.css" type="text/css"/>
    <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/sidebars.css" type="text/css"/>
    <script src="http://www.ebi.ac.uk/inc/js/contents.js" type="text/javascript"></script>
    <%-- script EBI to convert header to transparent when IE6, IE7, IE8  --%>
    <script type="text/javascript">
    function loaded() {
    if (navigator.userAgent.indexOf('MSIE') != -1) {
    document.getElementById('head').allowTransparency = true;
    }
    }
    </script>
    <%-- END EBI style and code--%>
    <%-- JQuery CSS--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui-1.8.11.custom.css" type="text/css"
          media="all"/>
    <%-- END JQuery CSS--%>

    <link href="http://fonts.googleapis.com/css?family=Droid+Sans:regular,bold" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/default.css" type="text/css" media="all"/>

    <%-- JQuery and JQuery UI source--%>
    <script src="${pageContext.request.contextPath}/js/jquery-1.4.4.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-ui-1.8.8.custom.min.js" type="text/javascript"></script>
    <%-- The date picker is used within the submission page --%>
    <script src="${pageContext.request.contextPath}/js/datepicker.js" type="text/javascript"></script>
    <%-- The auto completion is used with the study search page --%>
    <script src="${pageContext.request.contextPath}/js/autocompletion.js" type="text/javascript"></script>

    <script src="${pageContext.request.contextPath}/js/formclearer.js" type="text/javascript"></script>
    <%-- Jquey scroller - work only for the anchors on the same page--%>
    <script src="${pageContext.request.contextPath}/js/smoothscrol.js" type="text/javascript"></script>

    <!--[if lt IE 9]><%-- HTML5 tags working in IE8 by including this JavaScript in the head  --%>
<script type="text/javascript">
    document.createElement('header');
    document.createElement('hgroup');
    document.createElement('nav');
    document.createElement('menu');
    document.createElement('section');
    document.createElement('article');
    document.createElement('aside');
    document.createElement('footer');
    document.createElement('figure');
    document.createElement('figcaption');
</script>
    <![endif]-->

    <%-- simple script for alternate row in a table color was #EFF1F1 originally--%>
    <script>
        $(document).ready(function() {
            $("table.result tbody tr:even").css("background-color", "#F4F4F8");
            $("table.result tbody tr:odd").css("background-color", "#e9e9e9");
            <%--Set focus to login input field after clicking the login link--%>
//            $('#loginLinkId').click(function() {
//                $('#loginInputFieldId').focus();
//            });
            <%--Set focus during page initialisation--%>
            $('#loginInputFieldId').focus();
        });
    </script>

    <script type="text/javascript"
            src="http://maps.google.com/maps/api/js?sensor=false">
    </script>
    <script type="text/javascript">
        function initialize(lat, long) {
            var latlng = new google.maps.LatLng(lat, long);
            google.maps.MarkerOptions
            var myOptions = {
                zoom: 4,
                center: latlng,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };
            var map = new google.maps.Map(document.getElementById("map_canvas"),
                    myOptions);
            var marker = new google.maps.Marker({
                position: latlng,
                map: map
            });
        }
    </script>



</head>

<%-- The following variable saves and provides the base URL for the whole application --%>
<c:set var="baseURL" value="" scope="session"/>

<%-- IMPORTANT onload attribute is necessary to ensure that the EBI main header works in IE see  http://www.ebi.ac.uk/inc/template/#important style overflow added because of a bug in the EBI website for the body--%>
<body class="<tiles:getAsString name='bodyClass'/>" id="top" onload="loaded()" <%--onload="initialize()"--%>>

<%-- EBI-Interpro main header - exactly same as Interpro header--%>
<div class="headerdiv" id="headerdiv" style="position:absolute; z-index: 1;">
    <iframe src="/inc/head.html" name="head" id="head" frameborder="0" marginwidth="0px" marginheight="0px"
            scrolling="no"
            width="100%" style="position:absolute; z-index: 1; height: 57px;">Header
    </iframe>
</div>
<%-- END EBI main header --%>

<div class="ebi_contents">

    <tiles:insertAttribute name="breadcrumbs"/>

    <div id="wrapper">

        <header>
            <tiles:insertAttribute name="header"/>
        </header>

        <nav>
            <tiles:insertAttribute name="mainMenu"/>
        </nav>

        <div id="main-content">
            <tiles:insertAttribute name="body"/>
        </div>

    </div>

    <footer>
        <tiles:insertAttribute name="footer"/>
    </footer>

</div>

<div id="extra_feedback"><h1><a href="<c:url value="${baseURL}/contact"/>">Give your feedback</a></h1></div>
<a href="<c:url value="${baseURL}/info#intro"/>" title="About us">
    <div id="extra_beta"></div>
</a>
<!--<div id="extraDiv1"></div> -->


</body>
</html>

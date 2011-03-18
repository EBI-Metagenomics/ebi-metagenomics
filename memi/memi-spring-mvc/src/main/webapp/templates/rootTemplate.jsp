<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8" lang="java">
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/favicon.ico">
    <link rel="icon" type="image/ico" href="${pageContext.request.contextPath}/img/favicon.ico">
    <link href="../../common/assets/css/ebi/ebi_common.css" rel="stylesheet" type="text/css"/>
    <title><tiles:insertAttribute name="pageTitle"/></title>
    <meta name="description" content="EBI Metagenomics is a new web resource targeted at metagenomic researchers"/>
    <meta name="keywords"
          content="ebi, EBI, InterPro, interpro, metagenomics, metagenomic, metagenome, metagenomes, DNA, microbiology, microbial, ecology, organisms, microorganism, microorganisms, biodiversity, diversity, gene, genes, genome, genomes, genomic, genomics, ecogenomics, community genomics, genetic, sequencing, sequence, environment, environmental, ecosystem, ecosystems, samples, sample, annotation, protein, research, archive, metabolic, pathways, analysis, function, GAIA, shotgun, pyrosequencing, community, communities, metabolism, cultivation, bioinformatics, bioinformatic, database, metadata, dataset, data, repository,   "/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/default.css" type="text/css" media="all"/>

    <link href="http://fonts.googleapis.com/css?family=Droid+Sans:regular,bold" rel="stylesheet" type="text/css">


    <%-- JQuery and JQuery UI source--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.css" type="text/css" media="all"/>
    <script src="${pageContext.request.contextPath}/js/jquery-1.4.4.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-ui-1.8.8.custom.min.js" type="text/javascript"></script>
    <%-- The date picker is used within the submission page --%>
    <script src="${pageContext.request.contextPath}/js/datepicker.js" type="text/javascript"></script>
    <%-- The auto completion is used with the study search page --%>
    <script src="${pageContext.request.contextPath}/js/autocompletion.js" type="text/javascript"></script>

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
        });
    </script>
</head>

<%-- The following variable saves and provides the base URL for the whole application --%>
<c:set var="baseURL" value="" scope="session"/>

<%-- onload attribute is necessary to ensure that the EBI main header works in IE see  http://www.ebi.ac.uk/inc/template/#important style overflow addede because of he bug in the EBI website for the body--%>
<body
        class="<tiles:getAsString name='bodyClass'/>" style="overflow:visible;">
<%-- EBI main header                 --%>
<%--<%@include file='../inc/head.html' %>--%>
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

<div id="extra_feedback"><h1><a href="<c:url value="${baseURL}/contact"/>">Give your feedback</a></h1></div>
<div id="extra_beta"></div>
<!--<div id="extraDiv1"></div> -->
</body>
</html>

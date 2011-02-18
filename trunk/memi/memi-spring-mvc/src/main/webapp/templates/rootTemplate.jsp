<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8" lang="java">
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicon.ico">
    <link rel="icon"  type="image/ico" href="${pageContext.request.contextPath}/images/favicon.ico">
    <title>EBI metagenomics portal</title>
    <meta name="description" content="EBI Metagenomics is a new web resource targeted at metagenomic researchers"/>
    <meta name="keywords" content="ebi, EBI, InterPro, interpro, metagenomics, metagenomic, metagenome, metagenomes, DNA, microbiology, microbial, ecology, organisms, microorganism, microorganisms, biodiversity, diversity, gene, genes, genome, genomes, genomic, genomics, ecogenomics, community genomics, genetic, sequencing, sequence, environment, environmental, ecosystem, ecosystems, samples, sample, annotation, protein, research, archive, metabolic, pathways, analysis, function, GAIA, shotgun, pyrosequencing, community, communities, metabolism, cultivation, bioinformatics, bioinformatic, database, metadata, dataset, data, repository,   "/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/default.css" type="text/css" media="all"/>
                                                    
    <%--Link to the CSS file, which includes CSS classes for the EBI main header and footer
   <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/contents.css" type="text/css"/>
    <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/userstyles.css" type="text/css"/>
    <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/sidebars.css" type="text/css"/> --%>

    <%-- JQuery and JQuery UI source--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.css" type="text/css" media="all"/>
    <script src="${pageContext.request.contextPath}/js/jquery-1.4.4.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-ui-1.8.8.custom.min.js" type="text/javascript"></script>
    <%-- The date picker is used within the submission page --%>
    <script src="${pageContext.request.contextPath}/js/datepicker.js" type="text/javascript"></script>
    <%-- The auto completion is used with the study search page --%>
    <script src="${pageContext.request.contextPath}/js/autocompletion.js" type="text/javascript"></script>
</head>

<%-- The following variable saves and provides the base URL for the whole application --%>
<c:set var="baseURL" value="${pageContext.request.contextPath}/metagenomics" scope="session"/>

<%-- onload attribute is necessary to ensure that the EBI main header works in IE see  http://www.ebi.ac.uk/inc/template/#important--%>
<body onload="if(navigator.userAgent.indexOf('MSIE') != -1) {document.getElementById('head').allowTransparency = true;}" id="top">



<div id="wrapper">
    <div id ="content">

    <header id="main-header">
    <tiles:insertAttribute name="header"/>
    </header>

    <nav>
    <tiles:insertAttribute name="mainMenu"/> 
    </nav>
        
    <section id="main-content">
    <tiles:insertAttribute name="body"/>
    <tiles:insertAttribute name="rightMenu"/>    
    </section>

    <footer>
      <tiles:insertAttribute name="footer"/>    
    </footer>
       
        
</div>
</div>
</body>
</html>
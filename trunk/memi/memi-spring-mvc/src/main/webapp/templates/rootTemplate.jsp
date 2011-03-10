<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8" lang="java">
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicon.ico">
    <link rel="icon"  type="image/ico" href="${pageContext.request.contextPath}/images/favicon.ico">
    <link href="../../common/assets/css/ebi/ebi_common.css" rel="stylesheet" type="text/css" />    
    <title>EBI metagenomics portal</title>
    <meta name="description" content="EBI Metagenomics is a new web resource targeted at metagenomic researchers"/>
    <meta name="keywords" content="ebi, EBI, InterPro, interpro, metagenomics, metagenomic, metagenome, metagenomes, DNA, microbiology, microbial, ecology, organisms, microorganism, microorganisms, biodiversity, diversity, gene, genes, genome, genomes, genomic, genomics, ecogenomics, community genomics, genetic, sequencing, sequence, environment, environmental, ecosystem, ecosystems, samples, sample, annotation, protein, research, archive, metabolic, pathways, analysis, function, GAIA, shotgun, pyrosequencing, community, communities, metabolism, cultivation, bioinformatics, bioinformatic, database, metadata, dataset, data, repository,   "/>

    <link rel="stylesheet" href="/css/default.css" type="text/css" media="all"/>

    <link  href="http://fonts.googleapis.com/css?family=Droid+Sans:regular,bold" rel="stylesheet" type="text/css" >


    <%-- JQuery and JQuery UI source--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.css" type="text/css" media="all"/>
    <script src="${pageContext.request.contextPath}/js/jquery-1.4.4.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-ui-1.8.8.custom.min.js" type="text/javascript"></script>
    <%-- The date picker is used within the submission page --%>
    <script src="${pageContext.request.contextPath}/js/datepicker.js" type="text/javascript"></script>
    <%-- The auto completion is used with the study search page --%>
    <script src="${pageContext.request.contextPath}/js/autocompletion.js" type="text/javascript"></script>
     <%-- HTML5 tags working in IE8 by including this JavaScript in the head  --%>
        <script type="text/javascript">
     document.createElement('header');
     document.createElement('hgroup');
     document.createElement('nav');
     document.createElement('menu');
     document.createElement('section');
     document.createElement('article');
     document.createElement('aside');
     document.createElement('footer');
    </script>

    <%-- BEGIN CODE FROM EBI TEMPLATE--%>
    <!--  VERSION 2 MARCH 22 7007 // -->
    <script type="text/javascript">
        <!--
        function do_return() {
            //window.frames['head'].document.forms['Text1293FORM'].elements['query'].value='Enter Text Here';
            document.forms['Text1293FORM'].elements['query'].value = 'Enter Text Here';
            //return false;
        }
        // -->
    </script>
    <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/template.css" type="text/css"/>
    <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/newmenu.css" type="text/css"/>

    <!--[if lt IE 9]><link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/IE_6.css" type="text/css"/><![endif]-->
    <!--[if gte IE 7]><link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/IE_7.css" type="text/css"/><![endif]-->
    <!--[if gte IE 8]><link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/IE_8.css" type="text/css"/><![endif]-->
    <script type="text/javascript" src="http://www.ebi.ac.uk/inc/js/functions.js"></script>
    <script type="text/javascript">
        <!--
        if ((navigator.userAgent.indexOf("Windows") != -1) && ( (navigator.userAgent.indexOf("Safari") != -1)  )) {
            document.write('<link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/windows_safari.css" type="text/css" />');
        }
        document.onload = function() {
            createOrUpdateEBeyeUSerIdCookie();
        }
        // -->
    </script>
    <script type="text/javascript" src="http://www.ebi.ac.uk/ebisearch/js/lib/jquery.cookies.2.2.0.min.js"></script>
    <script type="text/javascript" src="http://www.ebi.ac.uk/ebisearch/js/queryLogging.js"></script>
    <link rel="alternate" title="EBI News RSS" href="http://www.ebi.ac.uk/Information/News/rss/ebinews.xml"
          type="application/rss+xml"/>

     <%-- END CODE FROM EBI TEMPLATE--%>
</head>

<%-- The following variable saves and provides the base URL for the whole application --%>
<c:set var="baseURL" value="${pageContext.request.contextPath}/metagenomics" scope="session"/>

<%-- onload attribute is necessary to ensure that the EBI main header works in IE see  http://www.ebi.ac.uk/inc/template/#important style overflow addede because of he bug in the EBI website for the body--%>
<body onload="if(navigator.userAgent.indexOf('MSIE') != -1) {document.getElementById('head').allowTransparency = true;}"
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

<%--<div id="extraDiv1"></div0 can use this to add --%>
<script type="text/javascript" src="http://www.ebi.ac.uk/inc/js/newmenu.js"></script>
<script type="text/javascript">
    <!--
    try {
        var parentloc = parent.document.location.href;
        if (parentloc.indexOf('db=ebiweb') != -1) {
            document.Text1293FORM.FormsComboBox2.options[1].selected = true;
        }
        else if (parentloc.indexOf('db=literature') != -1) {
            document.Text1293FORM.FormsComboBox2.options[2].selected = true;
        }
        else {
        }
        var loc = "" + parent.document.location.href;
        if (loc.indexOf("docmode=printable") != -1) {
            minimimise();
        }
    }
    catch(err) {

    }
    // -->
</script>

</body>
</html>

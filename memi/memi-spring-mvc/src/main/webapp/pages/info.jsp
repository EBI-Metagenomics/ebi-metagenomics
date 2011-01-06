<%--
  Created by Maxim Scheremetjew, EMBL-EBI, InterPro
  Created date: 21-Dec-2010
  Desc: Represents the Metagenomics portal info page, which contains infos about the project.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="spring" %>
<html>
<head>
    <title>MG Portal info page</title>
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

<div id="content">
    <%--MG portal main menu template--%>
    <%@include file='/templates/mainMenu.jsp' %>
    <h2>MG Portal info page</h2>

    <div style="margin-top:6px"></div>
    <h3>What's metagenomics?</h3>

    <p>The study of all genomes present in any given environment without the need for prior individual identification or
        amplification is termed metagenomics. For example, in its simplest form a metagenomic study might be the direct
        sequence results of DNA extracted from a bucket of sea water.</p>

    <div style="margin-top:6px"></div>
    <h3>What is the EBI doing for metagenomic researchers?</h3>

    <p>The EBI resources of the European Nucleotide Archive (in particular Sequence Read Archive and EMBL-Bank),
        UniProt, InterPro, Ensembl Genomes and IntAct are all used for analysis by metagenomic researchers, but not in
        an integrated manner. We intend to provide a user friendly interface to these services, promoting their utility
        in the field of metagenomics. It will enable protein prediction, function analysis, comparison to complete
        reference genomes and metabolic pathway analysis. </p>

    <div style="margin-top:6px"></div>
    <h3>Mailing list</h3>

    <p>If you would like to be kept informed of further developments with the EBI metagenomics resources please sign up
        for our mailing list by sending Christopher Hunter an email with the subject line 'signup to
        MG-mailing-list'.</p>
</div>
<div style="margin-bottom:290px"></div>
<%@include file='/templates/footer.jsp' %>
</body>
</html>
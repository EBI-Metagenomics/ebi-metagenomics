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
    <link href="../css/memi.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
<div id="right_side_navigation">
    <p><a href="<c:url value="../homePage"/>">Home</a></p>

    <p><a href="<c:url value="./loginForm"/>">Login or register</a></p>
</div>
<div id="content">
    <div style="margin-top:60px"></div>
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
</body>
</html>
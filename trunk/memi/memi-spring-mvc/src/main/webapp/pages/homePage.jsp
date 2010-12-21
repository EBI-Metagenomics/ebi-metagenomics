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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="spring" %>
<html>
<head>
    <title>MG Portal home page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="../css/memi.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
<div id="right_side_navigation">
    <p><a href="<c:url value="./loginForm"/>">Login or register</a></p>

    <h3>Study list</h3>
    <table border="0" style="border-width: 1px;border-color: #000000;border-style: solid;">
        <c:forEach var="emg_study" items="${studyList}" varStatus="status">
            <tr>
                <spring:url var="studyUrl" value="studyOverview/${emg_study.studyId}"/>
                <td>
                    <a href="${studyUrl}">${emg_study.formattedReleaseDate} - ${emg_study.studyName}</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="<c:url value="./listStudies"/>">more</a>

    <h3>Other useful links</h3>

    <p><a href="<c:url value="./installationSitePage"/>">Sample list</a></p>

    <p><a href="<c:url value="./installationSitePage"/>">Contact us</a></p>
</div>
<div id="content">
    <div style="margin-top:60px"></div>
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
                <h3>Latest news</h3>
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
</body>
</html>
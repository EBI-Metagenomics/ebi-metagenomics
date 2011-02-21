<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%--Page variable which is used several time within this page. Used for not specified study attributes.--%>
<c:set var="notGivenId" value="(not given)"/>
<div id="content">
<h2>Study Overview</h2>

<div style="margin-top:6px"></div>
<table frame="box" width="95%">
    <tr>
        <td width="50%" align="left" valign="top">
            <h2>Study ${study.studyId}</h2>
            ${study.studyName}
        </td>
    </tr>
</table>
<div style="margin-top:10px"></div>
<h3>Study Description</h3>
<table frame="box" width="95%">
    <tr>
        <c:choose>
            <c:when test="${not empty study.studyAbstract}">
                <c:set var="studyAbstract" value="${study.studyAbstract}"/>
            </c:when>
            <c:otherwise>
                <c:set var="studyAbstract" value="${notGivenId}"/>
            </c:otherwise>
        </c:choose>
        <td valign="top" align="right" width="150"><b>Study abstract:</b></td>
        <td><textarea name=mytextarea cols=50 rows=5 readonly><c:out
                value="${studyAbstract}"/></textarea></td>
    </tr>
    <tr>
        <c:choose>
            <c:when test="${not empty study.studyType}">
                <c:set var="studyType" value="${study.studyType}"/>
            </c:when>
            <c:otherwise>
                <c:set var="studyType" value="${notGivenId}"/>
            </c:otherwise>
        </c:choose>
        <td valign="top" align="right" width="150"><b>Type of study:</b></td>
        <td><c:out value="${studyType}"/></td>
    </tr>
    <tr>
        <c:choose>
            <c:when test="${not empty study.experimentalFactor}">
                <c:set var="experimentalFactor" value="${study.experimentalFactor}"/>
            </c:when>
            <c:otherwise>
                <c:set var="experimentalFactor" value="${notGivenId}"/>
            </c:otherwise>
        </c:choose>
        <td valign="top" align="right" width="150"><b>Experimental factor:</b></td>
        <td><c:out value="${experimentalFactor}"/></td>
    </tr>
    <tr>
        <td valign="top" align="right" width="150"><b>Publications:</b></td>
        <td>
            <c:forEach var="pub" items="${publications}" varStatus="status">
                <p><c:out value="${pub.authors}"/> et al. (<c:out value="${pub.year}"/>)<br>
                    <i>"<c:out value="${pub.pubTitle}"/>"</i><br><c:out value="${pub.volume}"/><br>
                    <c:if test="${not empty pub.doi}">
                        <a href="<c:url value="http://dx.doi.org/${pub.doi}"/>">doi:<c:out value="${pub.doi}"/></a>
                    </c:if>
                    <c:if test="${not empty pub.pubMedId && pub.pubMedId>0}">
                        <a href="<c:url value="http://www.ncbi.nlm.nih.gov/pubmed/${pub.pubMedId}"/>">PMID <c:out
                                value="${pub.pubMedId}"/></a>
                    </c:if>
                </p>
            </c:forEach>
        </td>
    </tr>
</table>
<div style="margin-top:10px"></div>
<h3>Contact Details:</h3>
<table frame="box" width="95%">
    <tr>
        <td valign="top" align="right" width="150"><b>Submitter name:</b></td>
        <td>
            <c:choose>
            <c:when test="${study.public}">Public data</c:when>
            <c:otherwise>(not given)</c:otherwise>
            </c:choose>
    </tr>
    <tr>
        <td valign="top" align="right" width="150"><b>Contact name:</b></td>
        <td>(not given)</td>
    </tr>
    <tr>
        <td valign="top" align="right" width="150"><b>Contact email:</b></td>
        <td>(not given)</td>
    </tr>
    <tr>
        <c:choose>
            <c:when test="${not empty study.centreName}">
                <c:set var="centreName" value="${study.centreName}"/>
            </c:when>
            <c:otherwise>
                <c:set var="centreName" value="${notGivenId}"/>
            </c:otherwise>
        </c:choose>
        <td valign="top" align="right" width="150"><b>Centre name:</b></td>
        <td><c:out value="${centreName}"/></td>
    </tr>
</table>
<h3>Other Information:</h3>
<table frame="box" width="95%">
    <tr>
        <c:choose>
            <c:when test="${not empty study.publicReleaseDate}">
                <c:set var="publicReleaseDate" value="${study.publicReleaseDate}"/>
            </c:when>
            <c:otherwise>
                <c:set var="publicReleaseDate" value="${notGivenId}"/>
            </c:otherwise>
        </c:choose>
        <td valign="top" align="right" width="150"><b>Public release date:</b></td>
        <td><c:out value="${publicReleaseDate}"/></td>
    </tr>
    <tr>
        <td valign="top" align="right" width="150"><b>BioProject ID:</b></td>
        <c:choose>
            <c:when test="${not empty study.ncbiProjectId && study.ncbiProjectId>0}">
                <td>
                    <a href="<c:url value="http://www.ebi.ac.uk/ena/data/view/Project:${study.ncbiProjectId}"/>"><c:out
                            value="${study.ncbiProjectId}"/></a></td>
            </c:when>
            <c:otherwise>
                <td><c:out value="${notGivenId}"/></td>
            </c:otherwise>
        </c:choose>
    </tr>
</table>
<div style="margin-top:10px"/>
<h3>Associated Sample(s):</h3>

<c:choose>
    <c:when test="${not empty samples}">
        <c:if test="${isDialogOpen==false}">
            <p><span style="color:red">No export data available for that(these) sample(s)!</span></p>
        </c:if>
        <div align="left">
            <a href="<c:url value="${baseURL}/studyView/doExport/${study.id}"/>">Export more detailed sample info to
                CSV</a>
        </div>
        <table border="1">
            <tr>
                <th>No.</th>
                <th>Sample ID</th>
                <th>Collection Date</th>
                <th>Sample Name</th>
                <th>Valid meta data</th>
                <th>Valid raw data</th>
                <th>Analysis completed</th>
                <th>Archived in ENA</th>
            </tr>
            <%
                int i = 1;
            %>
            <c:forEach var="sample" items="${samples}" varStatus="status">
                <tr>
                    <td align="center"><%= i%><% i++;%></td>
                    <td align="center"><a
                            href="<c:url value="${baseURL}/sampleView/${sample.sampleId}"/>">${sample.sampleId}</a></td>
                    <td align="center">
                        <c:choose>
                            <c:when test="${empty sample.collectionDate}">N/A</c:when>
                            <c:otherwise>${sample.collectionDate}</c:otherwise>
                        </c:choose>
                    </td>
                    <td align="center">${sample.sampleTitle}</td>
                    <td align="center">
                        <c:choose>
                            <c:when test="${empty sample.metadataReceived}">
                                <img src="/images/error.gif" height="16" width="16" align="absmiddle" alt=""
                                     border="0"/>
                            </c:when>
                            <c:otherwise>
                                <img src="/images/check.gif" height="16" width="16" align="absmiddle" alt=""
                                     border="0"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td align="center">
                        <c:choose>
                            <c:when test="${empty sample.sequenceDataReceived}">
                                <img src="/images/error.gif" height="16" width="16" align="absmiddle" alt=""
                                     border="0"/>
                            </c:when>
                            <c:otherwise>
                                <img src="/images/check.gif" height="16" width="16" align="absmiddle" alt=""
                                     border="0"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td align="center">
                        <c:choose>
                            <c:when test="${empty sample.analysisCompleted}">
                                <img src="/images/error.gif" height="16" width="16" align="absmiddle" alt=""
                                     border="0"/>
                            </c:when>
                            <c:otherwise>
                                <img src="/images/check.gif" height="16" width="16" align="absmiddle" alt=""
                                     border="0"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td align="center">
                        <c:choose>
                            <c:when test="${empty sample.sequenceDataArchived}">
                                <img src="/images/error.gif" height="16" width="16" align="absmiddle" alt=""
                                     border="0"/>
                            </c:when>
                            <c:otherwise>
                                <img src="/images/check.gif" height="16" width="16" align="absmiddle" alt=""
                                     border="0"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>No samples to display</c:otherwise>
</c:choose>
</div>
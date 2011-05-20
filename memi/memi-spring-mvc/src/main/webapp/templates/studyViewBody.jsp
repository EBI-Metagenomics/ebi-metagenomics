<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--Page variable which is used several time within this page. Used for not specified study attributes.--%>
<c:set var="notGivenId" value="(not given)"/>
<div id="content-full">

<c:choose>
    <c:when test="${not empty model.study.publications}">
        <div id="sidebar-analysis">
            <div id="sidebar-related">
                <h2>Related Publications</h2>
                <span class="separator"></span>
                <ul>
                    <c:forEach var="pub" items="${model.study.publications}" varStatus="status">
                        <li><a class="list_more" href="<c:url value="http://dx.doi.org/${pub.doi}"/>"><c:out value="${pub.pubTitle}"/></a><br>
                            <i><c:out value="${pub.authors}"/> et al.</i><br>
                            <c:out value="${pub.year}"/> <c:out value="${pub.volume}"/><br>

                            <%-- removed cause we have just one link on the Title
                            <c:if test="${not empty pub.doi}"><a class="ext" style="font-size:110%;"
                                                                 href="<c:url value="http://dx.doi.org/${pub.doi}"/>">DOI</a></c:if>
                            <c:if test="${not empty pub.pubMedId && pub.pubMedId>0}">
                                - <a class="ext" style="font-size:110%;"
                                     href="<c:url value="http://www.ncbi.nlm.nih.gov/pubmed/${pub.pubMedId}"/>">PubMed
                                (${pub.pubMedId})</a>
                            </c:if>--%>
                        </li>
                    </c:forEach></ul>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <%--<div id="sidebar-analysis">
        <div id="sidebar-steps">
        <h2>Related publications</h2>
        <span class="separator"></span>
         <p>&nbsp;</p>
        <p>There is no publications available for this project.<br/></p>
        </div>
        </div>--%>
    </c:otherwise>
</c:choose>

<span class="subtitle">Project overview <a href="<c:url value="${baseURL}/study/${model.study.studyId}"/>" style="font-size:90%;"> (${model.study.studyId})</a></span>
<h2>${model.study.studyName}</h2>


<c:choose>
    <c:when test="${not empty model.study.ncbiProjectId && model.study.ncbiProjectId>0}">
        <br/>BioProject ID: <a class="ext"
                               href="<c:url value="http://www.ebi.ac.uk/ena/data/view/Project:${model.study.ncbiProjectId}"/>"><c:out
            value="${model.study.ncbiProjectId}"/></a>
    </c:when>
    <c:otherwise></c:otherwise>
</c:choose>

<c:if test="${!model.study.public}">
<p>Private data <img alt="private" src="${pageContext.request.contextPath}/img/icon_priv_private.gif">
<c:choose>
    <c:when test="${not empty model.study.publicReleaseDate}">
        <c:set var="publicReleaseDate" value="${model.study.publicReleaseDate}"/>
        <span class="list_date">&nbsp;(will be published on the <fmt:formatDate value="${publicReleaseDate}" pattern="dd-MMM-yyyy"/>)</span>
    </c:when>
    <c:otherwise>
        <c:set var="publicReleaseDate" value="${notGivenId}"/>
    </c:otherwise>
</c:choose>
</p>
</c:if>


<p><span>Submitted date: ${model.study.formattedLastReceived}</span></p>

<h3 id="study_desc" style="margin-top:40px;">Description </h3>

<div class="output_form">
    <c:choose>
        <c:when test="${not empty model.study.studyAbstract}">
            <c:set var="studyAbstract" value="${model.study.studyAbstract}"/>
        </c:when>
        <c:otherwise>
            <c:set var="studyAbstract" value="notGivenId"/>
        </c:otherwise>
    </c:choose>

    <p style="font-size:110%;"><c:out value="${studyAbstract}"/></p>


    <c:choose>
        <c:when test="${not empty model.study.experimentalFactor}">
            <c:choose>
                <c:when test="${model.study.experimentalFactor=='none'}"></c:when>
                <c:otherwise>
                    <c:set var="experimentalFactor" value="${model.study.experimentalFactor}"/>
                    <p><h4>Experimental factor:</h4> <c:out value="${experimentalFactor}"/></p>
                </c:otherwise></c:choose>
        </c:when>
        <c:otherwise>
            <c:set var="experimentalFactor" value="${notGivenId}"/>
        </c:otherwise>
    </c:choose>
</div>

<h3>Contact details</h3>

<div class="output_form">
    <c:set var="centreName" value="${model.study.centreName}"/>
    <c:choose>
    <c:when test="${not empty model.study.centreName}">
        <div class="result_row"><label>Institute:</label><span><c:out value="${centreName}"/></span></div>
    </c:when>
        <c:otherwise><div class="result_row"><label>Institute:</label><c:set var="centreName" value="${notGivenId}"/></div></c:otherwise>
    </c:choose>


    <%--make this dynamic in the future--%>
    <div class="result_row"><label>Name:</label><span>none</span></div>

    <%--make this dynamic in the future--%>
    <div class="result_row"><label>Email:</label>
        <span>none</span></div>
</div>


<%-- Do we need to display this static information?
<p>
<strong>Submitter name:</strong>
<c:choose>
<c:when test="${model.study.public}">Public data</c:when>
<c:otherwise>(not given)</c:otherwise>
</c:choose>
Contact name: (not given)
</p>--%>

<%--<h3>Other information</h3>--%>

<h3 id="samples_id">Associated samples</h3>
<c:choose>
    <c:when test="${not empty model.samples}">
        <%-- <c:if test="${isDialogOpen==false}">
            <p><span style="color:red">No export data available for that(these) sample(s)!</span></p>
        </c:if>
        <div>
            <a href="<c:url value="${baseURL}/study/${model.study.studyId}/doExport/"/>">Export more detailed sample info to CSV</a>
        </div>--%>


        <div class="export">
            <a href="<c:url value="${baseURL}/study/${model.study.studyId}/doExport/"/>" id="csv_plus"
               title="<spring:message code="studyView.download.anchor.title"/>">
                <spring:message code="studyView.download.anchor.label"/></a>
        </div>


        <table border="1" class="result">
            <thead>
            <tr>
                <th scope="col" abbr="Sname">Sample name</th>
                <th scope="col" abbr="Pname">Sample ID</th>
                <th scope="col" abbr="Pname">Collection date</th>
                <th scope="col" abbr="Source" width="140px">Source</th>
                <th scope="col" abbr="Analysis" width="80px">Analysis</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="sample" items="${model.samples}" varStatus="status">
                <tr>
                    <td style="text-align:left;" id="ordered">
                        <c:if test="${!sample.public}"><img alt="private"
                                                            src="${pageContext.request.contextPath}/img/icon_priv_private.gif">&nbsp;&nbsp;</c:if>
                        <a href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>">${sample.sampleName}</a>
                    </td>
                    <td style="width:94px;">${sample.sampleId}</td>
                    <td style="width:130px;">
                        <c:choose>
                            <c:when test="${empty sample.collectionDate}">-</c:when>
                            <c:otherwise><fmt:formatDate value="${sample.collectionDate}" pattern="dd-MMM-yyyy"/></c:otherwise>
                        </c:choose></td>
                    <td>${sample.sampleTypeAsString}</td>
                    <td>
                        <c:choose>
                            <c:when test="${empty sample.analysisCompleted}"><%--<img
                                    src="${pageContext.request.contextPath}/img/ico_IN_PROGRESS_25_8.png"
                                    alt="Analysis in progress" title="Analysis in progress">--%>in progress</c:when>
                            <c:otherwise>
                                <a href="<c:url value="${baseURL}/analysisStatsView/${sample.sampleId}"/>"><img
                                        src="${pageContext.request.contextPath}/img/ico_FINISHED_25_8.png"
                                        alt="Analysis finished - check the results"
                                        title="Analysis finished - check the results"></a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>


        <%-- <table border="1">
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
            <c:forEach var="sample" items="${model.samples}" varStatus="status">
                <tr>
                    <td align="center"><%= i%><% i++;%></td>
                    <td align="center"><a
                            href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>">${sample.sampleId}</a></td>
                    <td align="center">
                        <c:choose>
                            <c:when test="${empty sample.collectionDate}">N/A</c:when>
                            <c:otherwise>${sample.collectionDate}</c:otherwise>
                        </c:choose>
                    </td>
                    <td align="center">${sample.sampleName}</td>
                    <td align="center">
                        <c:choose>
                            <c:when test="${empty sample.metadataReceived}">
                                <img src="/img/error.gif" height="16" width="16" align="absmiddle" alt=""
                                     border="0"/>
                            </c:when>
                            <c:otherwise>
                                <img src="/img/check.gif" height="16" width="16" align="absmiddle" alt=""
                                     border="0"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td align="center">
                        <c:choose>
                            <c:when test="${empty sample.sequenceDataReceived}">
                                <img src="/img/error.gif" height="16" width="16" align="absmiddle" alt=""
                                     border="0"/>
                            </c:when>
                            <c:otherwise>
                                <img src="/img/check.gif" height="16" width="16" align="absmiddle" alt=""
                                     border="0"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td align="center">
                        <c:choose>
                            <c:when test="${empty sample.analysisCompleted}">In Progress</c:when>
                            <c:otherwise>
                                <a href="<c:url value="${baseURL}/analysisStatsView/${sample.sampleId}"/>">Analysis
                                    Results</a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td align="center">
                        <c:choose>
                            <c:when test="${empty sample.sequenceDataArchived}">
                                <img src="/img/error.gif" height="16" width="16" align="absmiddle" alt=""
                                     border="0"/>
                            </c:when>
                            <c:otherwise>
                                <img src="/img/check.gif" height="16" width="16" align="absmiddle" alt=""
                                     border="0"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </table>--%>

    </c:when>
    <c:otherwise>No samples to display</c:otherwise>
</c:choose>
 <div class="but_top"><a href="#top" title="back to the top page">Top</a></div>
</div>
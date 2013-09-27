<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%--Page variable which is used several time within this page. Used for not specified study attributes.--%>
<c:set var="notGivenId" value="(not given)"/>

<div id="project_ov">

<div class="title_tab_p">
<span class="subtitle">Project overview <span>(${model.study.studyId})</span></span>
<h2 class="fl_uppercase_title">${model.study.studyName}</h2>
</div>

<tags:publications publications="${model.study.publications}" relatedPublications="${model.relatedPublications}" relatedLinks="${model.relatedLinks}" />

<c:choose>
    <c:when test="${not empty model.study.ncbiProjectId && model.study.ncbiProjectId>0}">
        <p>BioProject ID: <a class="ext"
                             href="<c:url value="https://www.ebi.ac.uk/ena/data/view/Project:${model.study.ncbiProjectId}"/>"><c:out
                value="${model.study.ncbiProjectId}"/></a></p>
    </c:when>
    <c:otherwise></c:otherwise>
</c:choose>

<c:if test="${!model.study.public}">
    <p>Private data <img alt="private" src="${pageContext.request.contextPath}/img/icon_priv_private.gif">
        <c:choose>
            <c:when test="${not empty model.study.publicReleaseDate}">
                <c:set var="publicReleaseDate" value="${model.study.publicReleaseDate}"/>
                <span class="list_date">&nbsp;(will be published on the <fmt:formatDate value="${publicReleaseDate}"
                                                                                        pattern="dd-MMM-yyyy"/>)</span>
            </c:when>
            <c:otherwise>
                <c:set var="publicReleaseDate" value="${notGivenId}"/>
            </c:otherwise>
        </c:choose>
    </p>
</c:if>


<p><span>Submitted date: ${model.study.formattedLastReceived}</span></p>

<h3 id="study_desc" style="margin-top:30px;">Description </h3>

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
                    <h4>Experimental factor: <c:out value="${experimentalFactor}"/></h4>
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
        <c:otherwise>
            <div class="result_row"><label>Institute:</label><c:set var="centreName" value="${notGivenId}"/></div>
        </c:otherwise>
    </c:choose>

    <%--Contact details are distinguished by the origination of the study:<br>--%>
    <%--Submitted: Somebody submitted his Metagenomics project to us<br>--%>
    <%--Mirrored: We integrated a Metagenomics project from an external service provider for instance MG-Rast<br>--%>
    <%--Harvested: Nucleotid sequences submitted to the ENA.--%>
    <c:choose>
        <c:when test="${model.study.dataOrigination == 'SUBMITTED'}">

            <c:set var="contactName" value="${model.submitterName}" scope="page"/>
            <c:set var="contactMail" value="${model.emailAddress}" scope="page"/>

            <c:if test="${not empty contactName && contactName != 'not available'}">
                <div class="result_row"><label>Name:</label><span>${contactName}</span></div>
            </c:if>
            <c:if test="${not empty contactMail && contactMail != 'not available'}">
                <div class="result_row"><label>Email:</label><span class="lowercase">${contactMail}</span></div>
            </c:if>
        </c:when>

        <c:when test="${model.study.dataOrigination == 'HARVESTED'}">

            <c:set var="contactName" value="${model.study.authorName}" scope="page"/>
            <c:set var="contactMail" value="${model.study.authorEmailAddress}" scope="page"/>

            <c:if test="${not empty contactName && contactName != 'not available'}">
                <div class="result_row"><label>Name:</label><span>${contactName}</span></div>
            </c:if>
            <c:if test="${not empty contactMail && contactMail != 'not available'}">
                <div class="result_row"><label>Email:</label><span class="lowercase">${contactMail}</span></div>
            </c:if>

        </c:when>

        <%--The Otherwise case is for data origination MIRRORED OR NULL values--%>
        <c:otherwise>

            <div class="result_row"><label>Name:</label><span>not available</span></div>
            <div class="result_row"><label>Email:</label><span>not available</span></div>
        </c:otherwise>
    </c:choose>

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
            <a href="<c:url value="${baseURL}/project/${model.study.studyId}/doExport/"/>">Export more detailed sample info to CSV</a>
        </div>--%>

        <!-- Removed link temporarily-->
        <%--<div class="export">--%>
        <%--<a href="<c:url value="${baseURL}/project/${model.study.studyId}/doExport/"/>" id="csv_plus"--%>
        <%--title="<spring:message code="studyView.download.anchor.title"/>">--%>
        <%--<spring:message code="download.anchor.label.detailed"/></a>--%>
        <%--</div>--%>


        <table border="1" class="result">
            <thead>
            <tr>
                <th scope="col" abbr="Sname" class="h_left">Sample name</th>
                <th scope="col" abbr="Pname">Sample ID</th>
                <th scope="col" abbr="Pname">Collection date</th>
                <th scope="col" abbr="Source" width="140px">Source</th>
                <th scope="col" abbr="Analysis" width="170px">Analysis results</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="sample" items="${model.samples}" varStatus="status">
                <tr>
                    <td class="h_left" id="ordered">
                        <c:if test="${!sample.public}"><img alt="private"
                                                            src="${pageContext.request.contextPath}/img/icon_priv_private.gif">&nbsp;&nbsp;</c:if>
                        <a href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>" class="fl_uppercase_title">${sample.sampleName}</a>
                    </td>
                    <td style="width:94px;">${sample.sampleId}</td>
                    <td style="width:130px;">
                        <c:choose>
                            <c:when test="${empty sample.collectionDate}">-</c:when>
                            <c:otherwise><fmt:formatDate value="${sample.collectionDate}"
                                                         pattern="dd-MMM-yyyy"/></c:otherwise>
                        </c:choose></td>
                    <td>${sample.sampleTypeAsString}</td>
                    <td>
                        <c:choose>
                            <c:when test="${empty sample.analysisCompleted}"><%--<img
                                    src="${pageContext.request.contextPath}/img/ico_IN_PROGRESS_25_8.png"
                                    alt="Analysis in progress" title="Analysis in progress">--%>in progress</c:when>
                            <c:otherwise>

                                <%--  ICON VERSION
                              <a href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>"><img
                                      src="${pageContext.request.contextPath}/img/ico_analysis_chart.gif"
                                      alt="Analysis finished - check the results"
                                      title="Analysis finished - check the results"></a>
                                --%>
                                <a href="<c:url value="${baseURL}/sample/${sample.sampleId}#Taxonomy-Analysis"/>"
                                   class="list_sample" title="Taxonomy analysis">Taxonomy </a>|
                                <a href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>#Functional-Analysis"
                                   class="list_sample" title="Function analysis">Function </a>| <a
                                    class="icon icon-functional list_sample" data-icon="="
                                    href="<c:url value="${baseURL}/sample/${sample.sampleId}"/>#Download"
                                    class="list_sample" title="download results"></a>
                            </c:otherwise>

                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </c:when>
    <c:otherwise>No samples to display</c:otherwise>
</c:choose>
</div>
<div class="but_top"><a href="#top" title="back to the top page">Top</a></div>

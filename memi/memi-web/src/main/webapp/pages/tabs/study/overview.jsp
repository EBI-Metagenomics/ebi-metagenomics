<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%--Page variable which is used several time within this page. Used for not specified study attributes.--%>
<c:set var="notGivenId" value="(not given)"/>
<c:set var="study" value="${model.study}"/>

<div id="project_ov">

<tags:publications publications="${study.publications}" relatedPublications="${model.relatedPublications}"
                   relatedLinks="${model.relatedLinks}"/>

<%-- Show icon only for people are are logged in--%>
<c:if test="${not empty model.submitter}">
    <!-- Private icon-->
    <c:if test="${!study.public}">
        <p class="show_tooltip icon icon-functional" data-icon="L" title="Private data">Private data
         <c:choose>
             <c:when test="${not empty study.publicReleaseDate}">
                 <c:set var="publicReleaseDate" value="${study.publicReleaseDate}"/>
                 <span class="list_warn">&nbsp;(will be published on the <fmt:formatDate value="${publicReleaseDate}"
                                                                                         pattern="dd-MMM-yyyy"/>)</span>
             </c:when>
             <c:otherwise>
                 <c:set var="publicReleaseDate" value="${notGivenId}"/>
             </c:otherwise>
         </c:choose>
     </p>
    </c:if>
    <c:if test="${study.public}">
            <p class="show_tooltip icon icon-functional" data-icon="U" title="Public data">Public data </p>
     </c:if>
</c:if>


<p class="project_upd_date">
    <c:choose>
        <c:when test="${not empty study.ncbiProjectId && study.ncbiProjectId>0}">
            BioProject <a class="ext"
                          href="<c:url value="https://www.ebi.ac.uk/ena/data/view/Project:${study.ncbiProjectId}"/>"><c:out
                value="${study.ncbiProjectId}"/></a> -
        </c:when>
        <c:otherwise></c:otherwise>
    </c:choose>
    Last updated: ${study.formattedLastReceived}</p>

<h3 class="study_desc">Description</h3>

<div class="output_form">
    <c:choose>
        <c:when test="${not empty study.studyAbstract}">
            <c:set var="studyAbstract" value="${study.studyAbstract}"/>
        </c:when>
        <c:otherwise>
            <c:set var="studyAbstract" value="${notGivenId}"/>
        </c:otherwise>
    </c:choose>

    <p class="study_desc_text"><c:out value="${studyAbstract}"/></p>


    <c:choose>
        <c:when test="${not empty study.experimentalFactor}">
            <c:choose>
                <c:when test="${study.experimentalFactor=='none'}"></c:when>
                <c:otherwise>
                    <c:set var="experimentalFactor" value="${study.experimentalFactor}"/>
                    <h4>Experimental factor: <c:out value="${experimentalFactor}"/></h4>
                </c:otherwise></c:choose>
        </c:when>
        <c:otherwise>
            <c:set var="experimentalFactor" value="${notGivenId}"/>
        </c:otherwise>
    </c:choose>

    <c:if test="${not empty study.biome}">
        <h4>Classification: <c:out value="${fn:replace(fn:replace(study.biome.lineage,'root:',''),':',' > ')}"/></h4>
    </c:if>
</div>

<h3>Contact details</h3>

<div class="output_form">
    <c:set var="centreName" value="${study.centreName}"/>
    <c:choose>
        <c:when test="${not empty study.centreName}">
            <div class="result_row">
                <div class="result_row_label">Institute:</div>
                <div class="result_row_data"><c:out value="${centreName}"/></div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="result_row">
                <div class="result_row_label">Institute:</div>
                <c:set var="centreName" value="${notGivenId}"/></div>
        </c:otherwise>
    </c:choose>

    <c:set var="contactName" value="${study.authorName}" scope="page"/>
    <c:set var="contactMail" value="${study.authorEmailAddress}" scope="page"/>

    <c:choose>
        <c:when test="${not empty contactName}">
            <div class="result_row">
                <div class="result_row_label">Name:</div>
                <div class="result_row_data">${contactName}</div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="result_row">
                <div class="result_row_label">Name:</div>
                <div class="result_row_data">not available</div>
            </div>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${not empty contactMail}">
            <div class="result_row">
                <div class="result_row_label">Email:</div>
                <div class="result_row_data">${contactMail}</div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="result_row">
                <div class="result_row_label">Email:</div>
                <div class="result_row_data lowercase">not available</div>
            </div>
        </c:otherwise>
    </c:choose>

</div>

<%--<h3>Other information</h3>--%>


<c:choose>
    <c:when test="${not empty model.runs}">
        <h3 id="samples_id">Associated runs</h3>
        <%-- <c:if test="${isDialogOpen==false}">
            <p><span style="color:red">No export data available for that(these) sample(s)!</span></p>
        </c:if>
        <div>
            <a href="<c:url value="${baseURL}/project/${study.studyId}/doExport/"/>">Export more detailed sample info to CSV</a>
        </div>--%>

        <!-- Removed link temporarily-->
        <%--<div class="export">--%>
        <%--<a href="<c:url value="${baseURL}/project/${study.studyId}/doExport/"/>" id="csv_plus"--%>
        <%--title="<spring:message code="studyView.download.anchor.title"/>">--%>
        <%--<spring:message code="download.anchor.label.detailed"/></a>--%>
        <%--</div>--%>


        <table border="1" class="result">
            <thead>
            <tr>
                <th scope="col" class="h_left">Sample Name</th>
                <th scope="col">Sample ID</th>
                <th scope="col">Run ID</th>
                <th scope="col">Experiment type</th>
                <th scope="col">Version</th>
                <th scope="col" width="170px">Analysis results</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="runCountLine" value="1"/>
            <c:forEach var="run" items="${model.runs}" varStatus="status">
                <tr>

                    <!-- Only include the the sample ID once for all runs under that sample -->


                    <c:if test="${runCountLine == 1}">
                        <td style="background-color: rgb(244, 244, 248);" id="ordered" rowspan="${run.runCount}"
                            class="h_left"><a
                                href="<c:url value="${baseURL}/projects/${study.studyId}/samples/${run.externalSampleId}"/>"
                                title="Sample ${run.externalSampleId}" class="fl_uppercase_title">${run.sampleName} </a>

                                 <%-- Show icon only for people are are logged in--%>
                       <c:if test="${not empty model.submitter}">
                            <!-- Private icon-->
                           <c:if test="${!study.public}">
                               <span class="show_tooltip icon icon-functional" data-icon="L" title="Private data"></span>
                           </c:if>
                           <c:if test="${study.public}">
                           <span class="show_tooltip icon icon-functional" data-icon="U" title="Public data"></span>
                           </c:if>
                       </c:if>
                        </td>
                        <td style="background-color: rgb(244, 244, 248);" rowspan="${run.runCount}">
                         ${run.externalSampleId}
                        </td>
                    </c:if>
                    <c:choose>
                        <c:when test="${runCountLine == run.runCount}">
                            <c:set var="runCountLine" value="1"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="runCountLine" value="${runCountLine + 1}"/>
                        </c:otherwise>
                    </c:choose>
                    <td>
                        <a title="Run ${run.externalRunIds}"
                           href="<c:url value="${baseURL}/projects/${study.studyId}/samples/${run.externalSampleId}/runs/${run.externalRunIds}/results/versions/${run.releaseVersion}"/>">
                                ${run.externalRunIds}
                        </a>
                    </td>
                    <td class="fl_capitalize">${run.experimentType}</td>
                    <td style="width:60px;">
                        <a href="<c:url value="${baseURL}/pipelines/${run.releaseVersion}"/>"
                           title="Pipeline version ${run.releaseVersion}">${run.releaseVersion}</a>
                    </td>
                    <td>
                        <a title="Taxonomic analysis" class="list_sample"
                           href="<c:url value="${baseURL}/projects/${study.studyId}/samples/${run.externalSampleId}/runs/${run.externalRunIds}/results/versions/${run.releaseVersion}#ui-id-6"/>">Taxonomy </a>|
                        <a title="Function analysis" class="list_sample"
                           href="<c:url value="${baseURL}/projects/${study.studyId}/samples/${run.externalSampleId}/runs/${run.externalRunIds}/results/versions/${run.releaseVersion}#ui-id-8"/>">Function </a>|
                        <a title="Download results"
                           class="icon icon-functional list_sample" data-icon="="
                           href="<c:url value="${baseURL}/projects/${study.studyId}/samples/${run.externalSampleId}/runs/${run.externalRunIds}/results/versions/${run.releaseVersion}#ui-id-10"/>"></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </c:when>
    <c:otherwise>
        <%--<p>No runs to display</p>--%>
    </c:otherwise>
</c:choose>
</div>

<script type="text/javascript" defer="defer">
    $("table.result tbody tr:even").css("background-color", "#F4F4F8");
    $("table.result tbody tr:odd").css("background-color", "#e9e9e9");
</script>

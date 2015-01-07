<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:choose>
<c:when test="${not empty model.sample}">
<aside>
<div id="sidebar-download">

<h3>Download results</h3>

<div id="box-export">
    <h4>Sequence data</h4>
    <ul>
        <c:forEach var="downloadLink" items="${model.downloadSection.seqDataDownloadLinks}" varStatus="loop">
            <li>
                <c:choose>
                    <c:when test="${downloadLink.externalLink}">
                        <a href="${downloadLink.linkURL}"
                           title="${downloadLink.linkTitle}">${downloadLink.linkText}</a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                           title="${downloadLink.linkTitle}">
                                ${downloadLink.linkText}</a><span
                            class="list_date"> - ${downloadLink.fileSize}</span>
                    </c:otherwise>
                </c:choose>
            </li>
        </c:forEach>
    </ul>
    <c:if test="${not empty model.downloadSection.funcAnalysisDownloadLinks}">
        <h4>Functional Analysis</h4>
        <ul>
            <c:forEach var="downloadLink" items="${model.downloadSection.funcAnalysisDownloadLinks}"
                       varStatus="loop">
                <li>
                    <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                       title="${downloadLink.linkTitle}">
                            ${downloadLink.linkText}</a><span
                        class="list_date"> - ${downloadLink.fileSize}</span>
                </li>
            </c:forEach>
        </ul>
    </c:if>
    <c:if test="${not empty model.downloadSection.taxaAnalysisDownloadLinks}">
        <h4>Taxonomic Analysis</h4>
        <ul>
            <c:forEach var="downloadLink" items="${model.downloadSection.taxaAnalysisDownloadLinks}"
                       varStatus="loop">
                <li>
                    <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                       title="${downloadLink.linkTitle}">
                            ${downloadLink.linkText}</a><span
                        class="list_date"> - ${downloadLink.fileSize}</span>
                </li>
            </c:forEach>
        </ul>
    </c:if>
</div>
</div>

</aside>
<span class="subtitle">Sample analysis results - ${model.experimentType.lowerCase} <a
        href="<c:url value="${baseURL}/sample/${model.sample.sampleId}"/>"
        style="font-size:90%;"> (${model.sample.sampleId})</a></span>

<h2>${model.sample.sampleName}</h2>


<c:choose>
    <c:when test="${not empty model.sample.analysisCompleted}">

        <h3>General statistics</h3>
        <c:url var="statsImage" value="/getImage" scope="request">
            <c:param name="imageName" value="_summary.png"/>
            <c:param name="imageType" value="PNG"/>
            <c:param name="dir" value="${model.emgFile.fileID}"/>
        </c:url>
        <p><img src="<c:out value="${statsImage}"/>"/></p>

        <h3>Protein function analysis</h3>

        <p>The entire InterProScan results file (<a title="Click to download full InterPro matches table (TSV)"
                                                    href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportI5TSVFile"/>">download
            here</a>) has been used to produce the following summaries.</p>

        <h4>InterPro match summary</h4>

        <p>Top five most frequently found InterPro matches to this sample:</p>
        <c:choose>
            <c:when test="${not empty model.interProEntries}">
                <div id="small"> <div class="export">
                    <a id="csv"
                       href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportIPRFile"/>"
                       title="<spring:message code="analysisStatsView.label.download.i5.table.view"/>">
                        <spring:message code="analysisStatsView.label.download.i5.table.view"/><%-- <c:out
                    value="${model.emgFile.fileSizeMap['_summary.ipr']}"/>--%>
                    </a>
                </div>
                </div>
                <table border="1" class="result" id="small">
                    <thead>
                    <tr>
                        <th scope="col" abbr="IEid" width="90px">InterPro ID</th>
                        <th scope="col" abbr="IEname" id="h_left">Entry name</th>
                        <th scope="col" abbr="IEnum" width="130px">Proteins matched</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="entry" items="${model.interProEntries}" varStatus="status">
                        <tr>
                            <td>
                                <c:url var="linkToInterProSearch" value="http://www.ebi.ac.uk/interpro/search">
                                    <c:param name="q" value="${entry.entryID}"/>
                                </c:url>
                                <a href="<c:out value="${linkToInterProSearch}"/>"
                                   title="<c:out value="Link to ${entry.entryID}"/>" class="ext">
                                    <c:out value="${entry.entryID}"/>
                                </a>
                            </td>
                            <td style="text-align:left;">${entry.entryDescription}</td>
                            <td id="ordered">${entry.numOfEntryHits}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <c:set var="showFullTableID" value="View full table"/>
                <p><a title="<c:out value="${showFullTableID}"/>"
                      href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/showProteinMatches"/>">
                    <c:out value="${showFullTableID}"/></a></p>

            </c:when>
            <c:otherwise>
                <b><c:out value="${noDisplayID}"/></b>
            </c:otherwise>
        </c:choose>


        <h4>Annotation by GO Terms</h4>

        <p>A summary of Gene Ontology (GO) terms derived from InterPro matches to your sample is provided in the chart
            below. You can view the complete list of GO terms by clicking on the download icon.</p>


        <div class="export" style="float:none; margin-left:9px;">
            <c:if test="${not empty model.pieChartBiologicalProcessURL}">

                <%-- remove detailed TSV export : available on top
          <a title="<spring:emailMessage code="analysisStatsView.label.download.goterms.full.csv"/>" href="<c:url
                   value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportGOFile/${model.emgFile.fileName}"/>">
               <spring:emailMessage code="analysisStatsView.label.download.goterms.full.csv"/></a>--%>


                <a id="csv"
                   title="<spring:message code="analysisStatsView.label.download.go.slim.anchor.title"/>"
                   href="<c:url
                    value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportGOSlimFile"/>">
                    <spring:message
                            code="analysisStatsView.label.download.go.slim.anchor.href.message"/></a>
            </c:if>
        </div>

            <div id="go-chart">
                <div id="go-chart-process">
                    <h2>Biological process</h2>
                    <c:url var="bioImage" value="/getImage" scope="request">
                        <c:param name="imageName" value="_summary_biological_process.png"/>
                        <c:param name="imageType" value="PNG"/>
                        <c:param name="dir" value="${model.emgFile.fileID}"/>
                    </c:url>
                    <p><img src="<c:out value="${bioImage}"/>"/></p>
                        <%--<b><c:out value="${noDisplayID}"/></b>--%>
                </div>

                <div id="go-chart-molecular">
                    <h2>Molecular function</h2>
                    <c:url var="molecularImage" value="/getImage" scope="request">
                        <c:param name="imageName" value="_summary_molecular_function.png"/>
                        <c:param name="imageType" value="PNG"/>
                        <c:param name="dir" value="${model.emgFile.fileID}"/>
                    </c:url>
                    <p><img src="<c:out value="${molecularImage}"/>"/></p>
                </div>
                <div id="go-chart-cellular">
                    <h2>Cellular component</h2>
                    <c:url var="cellImage" value="/getImage" scope="request">
                        <c:param name="imageName" value="_summary_cellular_component.png"/>
                        <c:param name="imageType" value="PNG"/>
                        <c:param name="dir" value="${model.emgFile.fileID}"/>
                    </c:url>
                    <p><img src="<c:out value="${cellImage}"/>"/></p>
                </div>
            </div>

        <div class="but_top"><a href="#top" title="back to the top page">Top</a></div>

    </c:when>
    <c:otherwise>
        <h3>Analysis in progress!</h3>
    </c:otherwise>
</c:choose>
</c:when>
<c:otherwise>
    <h3>Sample ID Not Recognised</h3>
</c:otherwise>
</c:choose>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="content-full">
<div class="grid_24">
<c:choose>
<c:when test="${not empty model.sample}">
<div id="sidebar-analysis">

    <div id="sidebar-steps">
        <h2> Data processing steps </h2>
        <span class="separator"></span>
        <ol>
            <li>1. Reads submitted</li>

            <li>2. Nucleotide sequences processed
                <ol>
                    <li>2.1. Clipped - low quality ends trimmed and adapter sequences removed using Biopython
                        SeqIO
                        package
                    </li>
                    <li>2.2. Quality filtered - sequences with > 10% undetermined nucleotides removed</li>
                    <li>2.3. Read length filtered - sequences shorter than 100 nt removed</li>
                    <li>2.4. Duplicate sequences removed - clustered on 99% identity (UCLUST v 1.1.579),
                        representative sequence chosen
                    </li>
                    <li>2.5. Repeat masked - RepeatMasker (open-3.2.2), removed reads with 50% or more
                        nucleotides
                        masked
                    </li>
                </ol>
            </li>
            <li>3. CDS predicted (FragGeneScan v 1.15)</li>
            <li>4. Matches were generated against predicted CDS with InterProScan 5.0 (beta release) using a subset of
                databases from InterPro release 31.0 (databases used for analysis: Pfam, TIGRFAM, PRINTS, PROSITE
                patterns, Gene3d). The Gene Ontology term summary was generated using the following GO slim: goslim_goa

            </li>
        </ol>
    </div>
</div>

<span class="subtitle">Sample analysis results - ${model.experimentType.lowerCase} <a
        href="<c:url value="${baseURL}/sample/${model.sample.sampleId}"/>"
        style="font-size:90%;"> (${model.sample.sampleId})</a></span>

<h2>${model.sample.sampleName}</h2>


<c:choose>
    <c:when test="${not empty model.sample.analysisCompleted}">

        <h3>Download results</h3>

        <div id="box-export">
            <ul>
                <li>
                    <a title="Click to download all submitted nucleotide data on the ENA website"
                       href="<c:url value="https://www.ebi.ac.uk/ena/data/view/${model.sample.sampleId}"/>">
                        <spring:message code="analysisStatsView.label.download.seq.data"/>
                    </a>
                </li>
                <li>
                    <a title="Click to download processed fasta nucleotide sequences"
                       href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportMaskedFASTAFile"/>"><spring:message
                            code="analysisStatsView.label.download.seq.fasta"/> <c:out
                            value="${model.emgFile.fileSizeMap['_masked.fasta']}"/>
                    </a></li>
                <li>
                    <a title="Click to download predicted CDS in fasta format"
                       href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportCDSFile"/>"><spring:message
                            code="analysisStatsView.label.download.cds.fasta"/> <c:out
                            value="${model.emgFile.fileSizeMap['_CDS.faa']}"/>
                    </a></li>
                <li>
                    <a title="Click to download full InterPro matches table (TSV)"
                       href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportI5TSVFile"/>"><spring:message
                            code="analysisStatsView.label.download.i5.matches"/> <c:out
                            value="${model.emgFile.fileSizeMap['_I5.tsv']}"/>
                    </a></li>
                <li>
                    <a title="<spring:message code="analysisStatsView.label.download.goterms.title"/>"
                       href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportGOFile"/>"><spring:message
                            code="analysisStatsView.label.download.goterms.csv"/> <c:out
                            value="${model.emgFile.fileSizeMap['_summary.go']}"/>
                    </a></li>
                <li>
                    <a title="<spring:message code="analysisStatsView.label.download.interproHits.title"/>"
                       href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportIPRhitsFile"/>"><spring:message
                            code="analysisStatsView.label.download.interproHits.cds.fasta"/> <c:out
                            value="${model.emgFile.fileSizeMap['_IPRhits.fasta']}"/>
                    </a></li>
            </ul>
        </div>

        <h3>General statistics</h3>
        <c:url var="statsImage" value="/getImage" scope="request">
            <c:param name="imageName" value="_summary.png"/>
            <c:param name="imageType" value="PNG"/>
            <c:param name="dir" value="${model.emgFile.fileID}"/>
        </c:url>
        <img src="<c:out value="${statsImage}"/>"/>

        <h3>Protein function analysis</h3>

        <p>The entire InterProScan results file (<a title="Click to download full InterPro matches table (TSV)"
                                                    href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportI5TSVFile"/>">download
            here</a>) has been used to produce the following summaries.</p>

        <h4>InterPro match summary</h4>

        <p>Top five most frequently found InterPro matches to this sample:</p>
        <c:choose>
            <c:when test="${not empty model.interProEntries}">
                <div class="export">
                    <a id="csv"
                       href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportIPRFile"/>"
                       title="<spring:message code="analysisStatsView.label.download.i5.table.view"/>">
                        <spring:message code="analysisStatsView.label.download.i5.table.view"/><%-- <c:out
                    value="${model.emgFile.fileSizeMap['_summary.ipr']}"/>--%>
                    </a>
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

        <div>
            <div id="go-chart">
                <div class="export">
                    <c:if test="${not empty model.pieChartBiologicalProcessURL}">

                        <%-- remove detailed TSV export : available on top
                  <a title="<spring:emailMessage code="analysisStatsView.label.download.goterms.full.csv"/>" href="<c:url
                           value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportGOFile/${model.emgFile.fileName}"/>">
                       <spring:emailMessage code="analysisStatsView.label.download.goterms.full.csv"/></a>--%>


                        <a id="csv" title="<spring:message code="analysisStatsView.label.download.go.slim.anchor.title"/>"
                           href="<c:url
                            value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportGOSlimFile"/>">
                            <spring:message code="analysisStatsView.label.download.go.slim.anchor.href.message"/></a>
                    </c:if>
                </div>
            </div>
            <div id="go-chart">
                <div id="go-chart-process">
                    <h2>Biological process</h2>
                    <c:url var="bioImage" value="/getImage" scope="request">
                        <c:param name="imageName" value="_summary_biological_process.png"/>
                        <c:param name="imageType" value="PNG"/>
                        <c:param name="dir" value="${model.emgFile.fileID}"/>
                    </c:url>
                    <img src="<c:out value="${bioImage}"/>"/>
                        <%--<b><c:out value="${noDisplayID}"/></b>--%>
                </div>

                <div id="go-chart-molecular">
                    <h2>Molecular function</h2>
                    <c:url var="molecularImage" value="/getImage" scope="request">
                        <c:param name="imageName" value="_summary_molecular_function.png"/>
                        <c:param name="imageType" value="PNG"/>
                        <c:param name="dir" value="${model.emgFile.fileID}"/>
                    </c:url>
                    <img src="<c:out value="${molecularImage}"/>"/>
                </div>
                <div id="go-chart-cellular">
                    <h2>Cellular component</h2>
                    <c:url var="cellImage" value="/getImage" scope="request">
                        <c:param name="imageName" value="_summary_cellular_component.png"/>
                        <c:param name="imageType" value="PNG"/>
                        <c:param name="dir" value="${model.emgFile.fileID}"/>
                    </c:url>
                    <img src="<c:out value="${cellImage}"/>"/>
                </div>
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
</div>
</div>
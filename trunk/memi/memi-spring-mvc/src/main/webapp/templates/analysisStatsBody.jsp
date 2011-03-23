<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div id="content-full">
    <c:set var="goSlimExportID" value="Export GO terms as a table (TSV)"/>
    <a name="top"></a>
    <c:choose>
        <c:when test="${not empty model.sample}">

            <h2>Study ${model.sample.study.studyName}</h2>
            <h4>Analysis of Sample <a
                    href="<c:url value="${baseURL}/sample/${model.sample.sampleId}"/>">${model.sample.sampleName}</a>
            </h4>


            <h2>1. Analysis results summary view</h2>

            <div style="margin-top:40px"></div>
            <h4>Statistics</h4>
            <c:set var="noDisplayID" value="No data to display."/>

            <p>Number of sequence reads with predicted CDS features and InterPro matches</p>
            <table border="1" width="95%">
                <tr>
                    <td width="40px">
                        <c:choose>
                            <c:when test="${not empty model.barChartURL}">
                                <img src="<c:out value="${model.barChartURL}"/>"/>
                            </c:when>
                            <c:otherwise>
                                <b><c:out value="${noDisplayID}"/></b>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </table>

            <h4>Annotation profile - GO Terms</h4>

            <table border="1" width="95%">
                <tr>
                    <td>Biological process</td>
                    <td align="left">
                        <c:if test="${not empty model.pieChartBiologicalProcessURL}">
                            <a title="<c:out value="${goSlimExportID}"/>"
                               href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportGOSlimFile/${model.emgFile.fileName}"/>">
                                <c:out value="${goSlimExportID}"/>
                            </a>
                            <br>
                            <c:set var="goExportID" value="Export a more detailed table (TSV)"/>
                            <a title="<c:out value="${goExportID}"/>"
                               href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportGOFile/${model.emgFile.fileName}"/>">
                                <c:out value="${goExportID}"/>
                            </a>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${not empty model.pieChartBiologicalProcessURL}">
                                <a title="<c:out value="${goSlimExportID}"/>"
                                   href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportGOSlimFile/${model.emgFile.fileName}"/>">
                                    <img src="<c:out value="${model.pieChartBiologicalProcessURL}"/>"/>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <b><c:out value="${noDisplayID}"/></b>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td>Cellular component</td>
                </tr>
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${not empty model.pieChartCellularComponentURL}">
                                <a title="<c:out value="${goSlimExportID}"/>"
                                   href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportGOSlimFile/${model.emgFile.fileName}"/>">
                                    <img src="<c:out value="${model.pieChartCellularComponentURL}"/>"/>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <b><c:out value="${noDisplayID}"/></b>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                <tr>
                    <td>Molecular function</td>
                </tr>
                </tr>
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${not empty model.pieChartMolecularFunctionURL}">
                                <a title="<c:out value="${goSlimExportID}"/>"
                                   href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportGOSlimFile/${model.emgFile.fileName}"/>">
                                    <img src="<c:out value="${model.pieChartMolecularFunctionURL}"/>"/>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <b><c:out value="${noDisplayID}"/></b>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </table>
        </c:when>
        <c:otherwise>
            <h2>Analysis Statistics</h2>

            <h3>Sample ID Not Recognised</h3>
        </c:otherwise>
    </c:choose>

    <h2>2. Data and analysis downloads</h2>

    <p>
    <ul>
        <li>Submitted nucleotide data:<br>
            <c:choose>
                <c:when test="${not empty model.archivedSequences}">
                    <c:forEach var="seqId" items="${model.archivedSequences}" varStatus="status">
                        <a href="<c:url value="http://www.ebi.ac.uk/ena/data/view/${seqId}"/>">
                            <c:out value="${seqId}"/></a><br>
                    </c:forEach>
                </c:when>
                <c:otherwise>(not given)</c:otherwise>
            </c:choose>
        </li>
        <li>Processed nucleotide data:<br>
            <a title="Click to download processed fasta nucleotide sequences"
               href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportMaskedFASTAFile/${model.emgFile.fileName}"/>">
                Click
            </a>
            to download processed fasta nucleotide sequences
        </li>
        <li>Predicted CDS:<br>
            <a title="Click to download predicted CDS in fasta format"
               href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportCDSFile/${model.emgFile.fileName}"/>">
                Click
            </a>
            to download predicated CDS in fasta format
        </li>
        <li>InterPro matches:<br>
            <a title="Click to download full InterPro match table (Excel or TSV)"
               href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportI5File/${model.emgFile.fileName}"/>">
                Click
            </a>
            to download full InterPro match table (Excel or TSV)
        </li>
    </ul>

    <h2>3. Data processing steps</h2>

    <p>
    <ul>
        <li>
            Submitted sequences (sff or fasta)
        </li>
        <li>
            Clip/ quality filter reads - The low quality ends were trimmed and adapter sequences removed using the
            BioPython
            SeqIO package
        </li>
        <li>
            Read length filter - Reads shorter than 99nt after clipping were removed
        </li>
        <li>
            Remove duplicate reads - UCLUST v x.xx with variable settings...
        </li>
        <li>Repeat masker - vX.X</li>
        <li>CDS prediction - FragGeneScan v X.x.x with flags 454_10</li>
        <li>Intergrated database searches using InterPro release X.X</li>
        <li>Databases searched:
            <ul>
                <li>PFAM A vX.X</li>
                <li>Gene3D vX.X</li>
            </ul>
        </li>
        <li>Annotation Profile created by mapping GeneOntology terms mapped to "GoSlim-GOA"</li>
    </ul>
</div>
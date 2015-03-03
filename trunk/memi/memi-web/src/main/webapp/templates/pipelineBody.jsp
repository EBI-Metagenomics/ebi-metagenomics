<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>At a glance - Pipeline version ${releaseVersion}</h2>

<h3>Data processing steps:</h3>

<c:choose>
    <c:when test="${releaseVersion == '2.0'}">
        <ol id="p-steps">
            <li>1. Reads submitted</li>
            <li>2. Nucleotide sequences processed
                <ol>
                    <li>2.1. Clipped - low quality ends trimmed and adapter sequences removed using Biopython SeqIO
                        package
                    </li>
                    <li>2.2. Quality filtered - sequences with > 10% undetermined nucleotides removed</li>
                    <li>2.3. Read length filtered - depending on the platform short sequences are removed</li>
                </ol>
            <li>3. rRNA reads are filtered using rRNASelector (rRNASelector v 1.0.1)</li>
            <li>4. Taxonomy analysis is performed upon 16s rRNA using Qiime (Qiime v 1.9).</li>
            <li>5. CDS predicted (FragGeneScan v 1.15)</li>
            <li>6. Matches were generated against predicted CDS with InterProScan 5.9 using a subset of databases from
                InterPro release 50.0 (databases used for analysis: Pfam, TIGRFAM, PRINTS, PROSITE patterns, Gene3d).
                The Gene Ontology term summary was generated using the following GO slim: goslim_goa
            </li>
        </ol>
    </c:when>
    <c:otherwise>
        <ol id="p-steps">
            <li>1. Reads submitted</li>

            <li>2. Nucleotide sequences processed
                <ol>
                    <li>2.1. Clipped - low quality ends trimmed and adapter sequences removed using Biopython
                        SeqIO
                        package
                    </li>
                    <li>2.2. Quality filtered - sequences with > 10% undetermined nucleotides removed</li>
                    <li>2.3. Read length filtered - depending on the platform short sequences are removed</li>
                    <li>2.4. Duplicate sequences removed - clustered on 99% identity (UCLUST v 1.1.579),
                        representative sequence chosen
                    </li>
                    <li>2.5. Repeat masked - RepeatMasker (open-3.2.2), removed reads with 50% or more
                        nucleotides
                        masked
                    </li>
                </ol>
            </li>
            <li>3. rRNA reads are filtered using rRNASelector (rRNASelector v 1.0.0)</li>
            <li>4. Taxonomy analysis is performed upon 16s rRNA using Qiime (Qiime v 1.5).</li>
            <li>5. CDS predicted (FragGeneScan v 1.15)</li>
            <li>6. Matches were generated against predicted CDS with InterProScan 5.0 (beta release) using a subset of
                databases from InterPro release 31.0 (databases used for analysis: Pfam, TIGRFAM, PRINTS, PROSITE
                patterns, Gene3d). The Gene Ontology term summary was generated using the following GO slim: goslim_goa
            </li>
        </ol>

    </c:otherwise>
</c:choose>

<h3>Pipeline Tools</h3>

<table border="1" class="result">
    <thead>
    <tr>
        <th>Tool name</th>
        <th>Description</th>
        <th>Version</th>
        <th>Technical detailsyes</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="pipelineTool" items="${pipelineTools}" varStatus="status">
        <tr>
            <td class="h_left" id="ordered"><a href="${pipelineTool.webLink}">${pipelineTool.toolName}</a></td>
            <td class="h_left">${pipelineTool.description}</td>
            <td class="h_left">${pipelineTool.toolVersion}</td>
            <td class="h_left">
                <a title="Taxonomy analysis" class="list_sample">Execution command </a>|
                <a title="Function analysis" class="list_sample">Configuration file </a>|
                <a title="download results" class="icon icon-functional list_sample" data-icon="=">Download source code</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
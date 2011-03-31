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
<br/>
<h4>Annotation profile -
    <a href="http://www.ebi.ac.uk/interpro/" title="InterPro home page">InterPro</a>
    protein matches (Top 5)</h4>
<c:choose>
    <c:when test="${not empty model.interProEntries}">
        <%--<c:set var="proteinExportID" value="Export full InterPro protein matches table (Excel or TSV)"/>--%>
        <%--<a title="<c:out value="${proteinExportID}"/>"--%>
        <%--href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportIPRFile/${model.emgFile.fileName}"/>">--%>
        <%--<c:out value="${proteinExportID}"/>--%>
        <%--</a>--%>
        <c:set var="showFullTableID" value="Show full protein matches table"/>
        <a title="<c:out value="${showFullTableID}"/>"
           href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/showProteinMatches"/>">
            <c:out value="${showFullTableID}"/>
        </a>
        <table border="1" class="result">
            <thead>
            <tr>
                <th align="center">InterPro Entry ID</th>
                <th align="center">InterPro Entry Description</th>
                <th align="center">Number of protein matches against this entry</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="entry" items="${model.interProEntries}" varStatus="status">
                <tr>
                    <td style="text-align:center;">
                        <c:url var="linkToInterProSearch" value="http://wwwdev.ebi.ac.uk/interpro/ISearch">
                            <c:param name="query" value="${entry.entryID}"/>
                        </c:url>
                        <a href="<c:out value="${linkToInterProSearch}"/>"
                           title="<c:out value="Link to ${entry.entryID}"/>">
                            <c:out value="${entry.entryID}"/>
                        </a>
                    </td>
                    <td style="text-align:left;">${entry.entryDescription}</td>
                    <td style="text-align:center;">${entry.numOfEntryHits}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <b><c:out value="${noDisplayID}"/></b>
    </c:otherwise>
</c:choose>
<br/>
<h4>Annotation profile - GO Terms</h4>
<table border="1" width="95%">
    <tr>
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
        <td></td>
        <td></td>
    </tr>
    <tr>
        <td>Biological process</td>
        <td>Cellular component</td>
        <td>Molecular function</td>
    </tr>
    <tr>
        <td>
            <c:choose>
                <c:when test="${not empty model.pieChartBiologicalProcessURL}">
                    <a title="<c:out value="${goSlimExportID}"/>"
                       href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportGOSlimFile/${model.emgFile.fileName}"/>">
                            <%--<img src="<c:out value="${model.pieChartBiologicalProcessURL}"/>"/>--%>
                        <img src="http://chart.googleapis.com/chart?cht=bhs&chco=ff0a00|ff4700|ff4700|ffb444|f
fb444|ffd088|ffd088|ffebcc|b8b082|b8b082|b8b082|b8b082|b8b082|b8b082|b8b082|
b8b082&chs=300x440&chxt=x&chxs=0,ffffff,0,0,_&chma=0,40,0,0&chds=0,600&chd=t
:508,489,477,271,227,131,120,74,26,11,8,4,3,2,1,1&chxr=0,0,600&chm=tmacromol
ecule+metabolic+process+(508),727272,0,0,10,,:4|tcellular+process+(489),7272
72,0,1,10,,l:4:0|tbiosynthetic+process+(477),727272,0,2,10,,:4|tmetabolic+pr
ocess+(271),727272,0,3,10,,:4|tnucleobase+nucleoside+nucleotide+and+nucleic+
acid+metabolic+process+(227),727272,0,4,10,,:4|ttransport+(131),727272,0,5,1
0,,:8|tregulation+of+biological+process+(120),727272,0,6,10,,:4|tresponse+to
+stimulus+(74),727272,0,7,10,,:4|tcatabolic+process+(26),727272,0,8,10,,:4|t
behavior+%2811%29,727272,0,9,10,,:4|tbiological_process+(4),727272,0,10,10,,
:4|tcellular+component+movement+(4),727272,0,11,10,,:4|tsecretion+(3),727272
,0,12,10,,:4|tcell+communication+(2),727272,0,13,10,,:4|tpathogenesis+(1),72
7272,0,14,10,,:4|tmulti-organism+process+(1),727272,0,15,10,,:4
"/>
                    </a>
                </c:when>
                <c:otherwise>
                    <b><c:out value="${noDisplayID}"/></b>
                </c:otherwise>
            </c:choose>
        </td>
        <td>
            <c:choose>
                <c:when test="${not empty model.pieChartCellularComponentURL}">
                    <a title="<c:out value="${goSlimExportID}"/>"
                       href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportGOSlimFile/${model.emgFile.fileName}"/>">
                            <%--<img src="<c:out value="${model.pieChartCellularComponentURL}"/>"/>--%>
                        <img src="http://chart.googleapis.com/chart?cht=bhs&chco=ccd7ff|e6ebff|e6ebff|b6babe|b
6babe|b6babe|b6babe|b6babe|b6babe&chs=300x270&chxt=x&chma=0,40,0,0&chds=0,60
0&chd=t:226,199,148,25,9,5,4,1,1&chxr=0,0,600&chm=tcytoplasm+(226),727272,0,
0,10,,:4|tintracellular+(199),727272,0,1,10,,:4|tmembrane+(148),727272,0,2,1
0,,:4|texternal+encapsulating+structure+(25),727272,0,3,10,,:4|textracellula
r+region+(9),727272,0,4,10,,:4|tchromosome+(5),727272,0,5,10,,:4|tcellular_c
omponent+(4),727272,0,6,10,,:4|tcell+(1),727272,0,7,10,,:4|tnucleus+(1),7272
72,0,8,10,,:4
"/>
                    </a>
                </c:when>
                <c:otherwise>
                    <b><c:out value="${noDisplayID}"/></b>
                </c:otherwise>
            </c:choose>
        </td>
        <td valign="top">
            <c:choose>
                <c:when test="${not empty model.pieChartMolecularFunctionURL}">
                    <a title="<c:out value="${goSlimExportID}"/>"
                       href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportGOSlimFile/${model.emgFile.fileName}"/>">
                            <%--<img src="<c:out value="${model.pieChartMolecularFunctionURL}"/>"/>--%>
                        <img src="http://chart.googleapis.com/chart?cht=bhs&chco=ccd7ff|e6ebff|e6ebff|b6babe|b
6babe|b6babe|b6babe|b6babe|b6babe&chs=400x280&chds=0,500&chm=N,727272,0,-1,1
0,,:4&chxs=1,000000,12,1,lt&chd=t:226,199,148,25,9,5,4,1,1&chxt=x,x,y&chxr=0
,0,500&chxp=1,100&chxl=1:|Protein+match|2:|nucleus|cell|cellular_component|c
hromosome|extracellular+region|external+encapsulating+structure|membrane|int
racellular|cytoplasm|3:|Go+term
"/>
                    </a>
                </c:when>
                <c:otherwise>
                    <b><c:out value="${noDisplayID}"/></b>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
        <%--<tr>--%>
        <%--<td>Cellular component</td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
        <%--<td>--%>
        <%--<c:choose>--%>
        <%--<c:when test="${not empty model.pieChartCellularComponentURL}">--%>
        <%--<a title="<c:out value="${goSlimExportID}"/>"--%>
        <%--href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportGOSlimFile/${model.emgFile.fileName}"/>">--%>
        <%--&lt;%&ndash;<img src="<c:out value="${model.pieChartCellularComponentURL}"/>"/>&ndash;%&gt;--%>
        <%--<img src="http://chart.googleapis.com/chart?cht=bhs&chco=ccd7ff|e6ebff|e6ebff|b6babe|b--%>
        <%--6babe|b6babe|b6babe|b6babe|b6babe&chs=300x270&chxt=x&chma=0,40,0,0&chds=0,60--%>
        <%--0&chd=t:226,199,148,25,9,5,4,1,1&chxr=0,0,600&chm=tcytoplasm+(226),727272,0,--%>
        <%--0,10,,:4|tintracellular+(199),727272,0,1,10,,:4|tmembrane+(148),727272,0,2,1--%>
        <%--0,,:4|texternal+encapsulating+structure+(25),727272,0,3,10,,:4|textracellula--%>
        <%--r+region+(9),727272,0,4,10,,:4|tchromosome+(5),727272,0,5,10,,:4|tcellular_c--%>
        <%--omponent+(4),727272,0,6,10,,:4|tcell+(1),727272,0,7,10,,:4|tnucleus+(1),7272--%>
        <%--72,0,8,10,,:4--%>
        <%--"/>--%>
        <%--</a>--%>
        <%--</c:when>--%>
        <%--<c:otherwise>--%>
        <%--<b><c:out value="${noDisplayID}"/></b>--%>
        <%--</c:otherwise>--%>
        <%--</c:choose>--%>
        <%--</td>--%>
        <%--</tr>--%>
        <%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<td>Molecular function</td>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
        <%--<tr>--%>
        <%--<td>--%>
        <%--&lt;%&ndash;<c:choose>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<c:when test="${not empty model.pieChartMolecularFunctionURL}">&ndash;%&gt;--%>
        <%--&lt;%&ndash;<a title="<c:out value="${goSlimExportID}"/>"&ndash;%&gt;--%>
        <%--&lt;%&ndash;href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportGOSlimFile/${model.emgFile.fileName}"/>">&ndash;%&gt;--%>
        <%--&lt;%&ndash;&lt;%&ndash;<img src="<c:out value="${model.pieChartMolecularFunctionURL}"/>"/>&ndash;%&gt;&ndash;%&gt;--%>
        <%--&lt;%&ndash;<img src="http://chart.googleapis.com/chart?cht=bhs&chco=ccd7ff|e6ebff|e6ebff|b6babe|b&ndash;%&gt;--%>
        <%--&lt;%&ndash;6babe|b6babe|b6babe|b6babe|b6babe&chs=400x280&chds=0,500&chm=N,727272,0,-1,1&ndash;%&gt;--%>
        <%--&lt;%&ndash;0,,:4&chxs=1,000000,12,1,lt&chd=t:226,199,148,25,9,5,4,1,1&chxt=x,x,y&chxr=0&ndash;%&gt;--%>
        <%--&lt;%&ndash;,0,500&chxp=1,100&chxl=1:|Protein+match|2:|nucleus|cell|cellular_component|c&ndash;%&gt;--%>
        <%--&lt;%&ndash;hromosome|extracellular+region|external+encapsulating+structure|membrane|int&ndash;%&gt;--%>
        <%--&lt;%&ndash;racellular|cytoplasm|3:|Go+term&ndash;%&gt;--%>
        <%--&lt;%&ndash;"/>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</a>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</c:when>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<c:otherwise>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<b><c:out value="${noDisplayID}"/></b>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</c:otherwise>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</c:choose>&ndash;%&gt;--%>
        <%--</td>--%>
        <%--</tr>--%>
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
        to download full InterPro matches table (Excel or TSV)
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
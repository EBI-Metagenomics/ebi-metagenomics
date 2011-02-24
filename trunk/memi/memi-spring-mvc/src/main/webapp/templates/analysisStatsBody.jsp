<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div id="content-full">
    <a name="top"></a>
    <c:choose>
        <c:when test="${not empty mgModel.sample}">
            <h2>Analysis Statistics for Study ${mgModel.sample.study.studyName}</h2>

            <h3>Sample: <a
                    href="<c:url value="${baseURL}/sampleView/${mgModel.sample.sampleId}"/>">${mgModel.sample.sampleTitle}</a>
            </h3>

            <p>${mgModel.sample.sampleDescription}</p>

            <!-- Links to detailed results from here. -->

        </c:when>
        <c:otherwise>
            <h2>Analysis Statistics</h2>

            <h3>Sample ID Not Recognised</h3>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${mgModel.hasStats}">
            <ul>
                <li><a href="#ReadsWithOrfs">Submitted Reads - Proportion with ORFs</a></li>
                <li><a href="#OrfsWithMatches">Open Reading Frames - Proportion with InterPro Matches</a></li>
                <li><a href="#GO">Gene Ontology (GO) Analysis</a></li>
                <li><a href="#Interpro">InterPro Entry Match Analysis</a></li>
            </ul>
            <a name="ReadsWithOrfs"></a>

            <h3>Submitted Reads from this Sample</h3>

            <p>
                The proportion of reads for which open reading frames are predicted
            </p>
            <img src="${mgModel.submittedReadsPieChartURL}"
                 alt="${mgModel.totalReads} Reads, of which ${mgModel.readsWithOrfs} have predicted ORFs"
                 title="${mgModel.totalReads} Reads, of which ${mgModel.readsWithOrfs} have predicted ORFs"/>

            <div align="left"><a href="#top">Back to top</a></div>
            <a name="OrfsWithMatches"></a>

            <h3>Predicted Open Reading Frames (ORFs)</h3>

            <p>
                The proportion of predicted open reading frames that match InterPro Signatures
            </p>
            <img src="${mgModel.orfPieChartURL}"
                 alt="${mgModel.totalOrfs} predicted ORFs, of which ${mgModel.orfsWithMatches} having matches to InterPro"
                 title="${mgModel.totalOrfs} predicted ORFs, of which ${mgModel.orfsWithMatches} having matches to InterPro"/>

            <div align="left"><a href="#top">Back to top</a></div>
            <a name="GO"></a>

            <h3>Gene Ontology (GO) Matches</h3>

            <p>
                This statistic is based upon InterProScan matches. Each InterPro entry may be mapped to a GO term. These
                GO terms
                can then be inferred for the matched protein sequence.
            </p>
            <img src="${mgModel.goPieChartURL}"/>

            <div align="left"><a href="#top">Back to top</a></div>
            <h3>Gene Ontology Match Statistics</h3>

            <table>
                <tr>
                    <th>
                        GO Accession
                    </th>
                    <th>
                        GO Term
                    </th>
                    <th>
                        Number of Matching Proteins
                    </th>
                </tr>
                <c:forEach var="goMatch" items="${mgModel.goMatchStatistics}">
                    <tr>
                        <td>
                                ${goMatch.accession}
                        </td>
                        <td>
                                ${goMatch.term}
                        </td>
                        <td>
                                ${goMatch.count}
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <div align="left"><a href="#top">Back to top</a></div>
            <a name="Interpro"></a>

            <h3>InterPro Match Statistics</h3>

            <table>
                <tr>
                    <th>
                        InterPro Accession
                    </th>
                    <th>
                        Entry Name
                    </th>
                    <th>
                        Number of Matching Proteins
                    </th>
                </tr>
                <c:forEach var="interProMatch" items="${mgModel.interProMatchStatistics}">
                    <tr>
                        <td>
                                ${interProMatch.accession}
                        </td>
                        <td>
                                ${interProMatch.term}
                        </td>
                        <td>
                                ${interProMatch.count}
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <div align="left"><a href="#top">Back to top</a></div>
        </c:when>
        <c:otherwise>
            <p>
                The analysis for this sample has not yet been completed.
            </p>
        </c:otherwise>
    </c:choose>
</div>



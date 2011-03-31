<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="content-full">
    <h4>Annotation profile -
        <a href="http://www.ebi.ac.uk/interpro/" title="InterPro home page">InterPro</a>
        protein matches</h4>
    <c:choose>
        <c:when test="${not empty model.interProEntries}">
            <div class="export">
                <c:set var="exportDetailedID" value="Export detailed file (Excel/TSV)" scope="page"/>
                <a href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportI5File/${model.emgFile.fileName}"/>"
                   id="csv_plus" title="<c:out value="${exportDetailedID}"/>">
                    <c:out value="${exportDetailedID}"/>
                </a>
                <c:set var="exportTableID" value="Export table (Excel/TSV)" scope="page"/>
                <a href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportIPRFile/${model.emgFile.fileName}"/>"
                   id="csv" title="<c:out value="${exportTableID}"/>">
                    <c:out value="${exportTableID}"/>
                </a>
            </div>
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
</div>
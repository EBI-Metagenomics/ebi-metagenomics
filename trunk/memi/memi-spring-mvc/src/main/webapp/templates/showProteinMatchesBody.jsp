<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="content-full">
     <h2>Analysis results<br/>
        ${model.sample.sampleName} sample</h2> 
    <h3>InterPro protein matches table</h3>
    
    <c:choose>
        <c:when test="${not empty model.interProEntries}">
            <div class="export">
                <c:set var="exportDetailedID" value="Export detailed file (TSV, 448Mb)" scope="page"/>
                <a href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportI5File/${model.emgFile.fileName}"/>"
                    title="<c:out value="${exportDetailedID}"/>">
                    <c:out value="${exportDetailedID}"/>
                </a>
                <c:set var="exportTableID" value="Export full table (CSV)" scope="page"/>
                <a href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportIPRFile/${model.emgFile.fileName}"/>"
                   id="csv" title="<c:out value="${exportTableID}"/>">
                    <c:out value="${exportTableID}"/>
                </a>
            </div>
            <br/>

            <div id="pageNavPosition"></div>
            <table id="results" border="1" class="result">
                <thead>
                <tr>
                    <th scope="col" abbr="IEid" width="90px">InterPro ID</th>
                    <th scope="col" abbr="IEname">Entry name</th>
                    <th scope="col" abbr="IEnum"  width="80px">Protein matches</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="entry" items="${model.interProEntries}" varStatus="status">
                    <tr>
                        <td>
                            <c:url var="linkToInterProSearch" value="http://wwwdev.ebi.ac.uk/interpro/ISearch">
                                <c:param name="query" value="${entry.entryID}"/>
                            </c:url>
                            <a href="<c:out value="${linkToInterProSearch}"/>" title="<c:out value="Link to ${entry.entryID}"/>"  class="ext">
                            <c:out value="${entry.entryID}"/>
                            </a>
                        </td>
                        <td style="text-align:left;">${entry.entryDescription}</td>
                        <td id="ordered">${entry.numOfEntryHits}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <b><c:out value="${noDisplayID}"/></b>
        </c:otherwise>
    </c:choose>
    <%--<script type="text/javascript">--%>
    <%--var pager = new Pager('results', 30);--%>
    <%--pager.init();--%>
    <%--pager.showPageNav('pager', 'pageNavPosition');--%>
    <%--pager.showPage(1);--%>
    <%--</script>--%>

</div>
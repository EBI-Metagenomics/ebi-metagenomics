<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="content-full">
   <span class="subtitle">Sample analysis results <a href="<c:url value="${baseURL}/sample/${model.sample.sampleId}"/>" style="font-size:90%;"> (${model.sample.sampleId})</a></span>
    <h2>${model.sample.sampleName}</h2>

    <h3>InterPro match summary</h3>

    <c:choose>
        <c:when test="${not empty model.interProEntries}">
            <div class="export">
               <%-- remove detailed TSV export : available on top
                <a href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportI5File/${model.emgFile.fileName}"/>"
                   title="<spring:message code="analysisStatsView.label.download.i5.matches.detailed"/>"/>
                <spring:message code="analysisStatsView.label.download.i5.matches.detailed"/>
                </a>--%>
                <a href="<c:url value="${baseURL}/analysisStatsView/${model.sample.sampleId}/doExportIPRFile/${model.emgFile.fileName}"/>"
                   id="csv" title="<spring:message code="analysisStatsView.label.download.i5.table.view"/>"/>
                <spring:message code="analysisStatsView.label.download.i5.table.view"/>
                </a>
            </div>
            <br/>

            <div id="pageNavPosition"></div>
            <table id="results" border="1" class="result">
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
                            <c:url var="linkToInterProSearch" value="http://wwwdev.ebi.ac.uk/interpro/ISearch">
                                <c:param name="query" value="${entry.entryID}"/>
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
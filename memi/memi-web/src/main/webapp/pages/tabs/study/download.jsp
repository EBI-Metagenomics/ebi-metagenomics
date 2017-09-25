<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div id="fragment-download">

    <div class="box-export">
        <p>In this section you can download the different results matrix files summarising the project. Each
            downloadable file contains an aggregation of the analysis results from the individual project runs. To
            visualise and download the analysis results for individual runs, please access their respective pages.</p>
        <p>Additionally, estimates are provided for the number of individuals that would need to be sequenced in order
            to see a given fraction of the total population diversity (based on the assumption of an underlying
            Poisson-log-normal taxa abundance distribution). These provide guidance for the sequencing effort likely to
            be required for a more complete characterisation of the microbial community of interest.</p>

        <c:forEach var="downloadSection" items="${model.downloadSectionMap}">
            <h3>Pipeline version <c:out value="${downloadSection.key}"/></h3>

            <c:if test="${not empty downloadSection.value.funcAnalysisDownloadLinks}">
                <h4>Functional analysis for the project</h4>
                <ul>
                    <c:forEach var="downloadLink" items="${downloadSection.value.funcAnalysisDownloadLinks}">
                        <li>
                            <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                               title="${downloadLink.linkTitle}">${downloadLink.linkTitle}</a>
                            <span class="list_date"> - ${downloadLink.fileSize}</span>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
            <c:if test="${not empty downloadSection.value.taxaAnalysisDownloadLinks}">
                <h4>Taxonomic analysis for the project</h4>

                <h3>Abundance</h3>
                <ul>
                    <c:forEach var="downloadLink" items="${downloadSection.value.taxaAnalysisDownloadLinks}">
                        <li>
                            <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                               title="${downloadLink.linkTitle}">${downloadLink.linkTitle}</a>
                            <c:if test="${not empty downloadLink.fileSize}">
                                <span class="list_date"> - ${downloadLink.fileSize}</span>
                            </c:if>
                        </li>
                    </c:forEach>
                </ul>
                <c:if test="${fn:length(downloadSection.value.statsDownloadLinks)>0}">
                    <h3>Statistics</h3>
                </c:if>
                <ul>
                    <c:forEach var="downloadLink" items="${downloadSection.value.statsDownloadLinks}">
                        <li>
                            <c:choose>
                                <c:when test="${downloadLink.linkText == 'pca.svg'}">
                                    <a class="ext" target="_blank"
                                       href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                                       title="${downloadLink.linkTitle}">${downloadLink.linkTitle}</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                                       title="${downloadLink.linkTitle}">${downloadLink.linkTitle}</a>
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${not empty downloadLink.fileSize}">
                                <span class="list_date"> - ${downloadLink.fileSize}</span>
                            </c:if>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
        </c:forEach>

    </div>

</div>
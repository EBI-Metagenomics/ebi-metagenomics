<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="fragment-download">

    <div class="box-export">
        <p>In this section you can download the different results matrix files summarising the project. Each
            downloadable file contains an aggregation of the analysis results from the individual project runs. To
            visualise and download the analysis results for individual runs, please access their respective pages.</p>

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

                <a target="_blank"
                   href="${pageContext.request.contextPath}/projects/${model.study.studyId}/download/${downloadSection.key}/pca"
                   title="Click to view principal component analysis (PCA) for all study runs">
                    <img src="${pageContext.request.contextPath}/img/ico_graph_pca_on.png" width="60" height="60"
                         alt="pca">
                </a>

                <ul>
                    <c:forEach var="downloadLink" items="${downloadSection.value.taxaAnalysisDownloadLinks}">
                        <li>
                            <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                               title="${downloadLink.linkTitle}">${downloadLink.linkTitle}</a>
                            <span class="list_date"> - ${downloadLink.fileSize}</span>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
        </c:forEach>

    </div>

</div>
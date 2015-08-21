<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="fragment-download">

    <div class="box-export">
        <p>In this section you can download the different matrix files. Each downloadable file contains an aggregation of the analysis results from the individual project runs. To visualise and download the analysis results for individual runs, please access their respective pages.</p>

        <c:forEach var="downloadSection" items="${model.downloadSectionMap}">
            <h3>Pipeline version <c:out value="${downloadSection.key}"/></h3>
            <h4>Functional Analysis</h4>
            <ul>
                <c:forEach var="downloadLink" items="${downloadSection.value.funcAnalysisDownloadLinks}">
                    <li>
                        <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                           title="${downloadLink.linkTitle}">${downloadLink.linkTitle}</a>
                            <%--${downloadLink.linkText}--%>
                                <span
                                        class="list_date"> - ${downloadLink.fileSize}</span>
                    </li>
                </c:forEach>
            </ul>
            <h4>Taxonomic Analysis</h4>
            <ul>
                <c:forEach var="downloadLink" items="${downloadSection.value.taxaAnalysisDownloadLinks}">
                    <li>
                        <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                           title="${downloadLink.linkTitle}">${downloadLink.linkTitle}</a>
                            <%--${downloadLink.linkText}--%>
                                <span
                                        class="list_date"> - ${downloadLink.fileSize}</span>
                    </li>
                </c:forEach>
            </ul>
        </c:forEach>

    </div>

</div>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="fragment-download">

    <div class="box-export">
        <p>You can download in this section the TODO.</p>
        <h4>TODO</h4>
        <p>${study.studyId}</p>
        <p>Questions:
        What about abundance tables for different pipeline versions? We just show the latest ones? Or show files for all pipeline versions?
            </p>

        <%--<ul>--%>
            <%--<c:forEach var="downloadLink" items="${model.downloadSection.seqDataDownloadLinks}" varStatus="loop">--%>
                <%--<c:choose>--%>
                    <%--<c:when test="${downloadLink.externalLink}">--%>
                        <%--<li>--%>
                            <%--<a href="${downloadLink.linkURL}"--%>
                               <%--title="${downloadLink.linkTitle}">${downloadLink.linkText}</a>--%>
                        <%--</li>--%>
                    <%--</c:when>--%>
                    <%--<c:when test="${!downloadLink.externalLink && not empty model.sample.analysisCompleted}">--%>
                        <%--<li>--%>
                            <%--<a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"--%>
                               <%--title="${downloadLink.linkTitle}">${downloadLink.linkText}</a><span--%>
                                <%--class="list_date"> - ${downloadLink.fileSize}</span>--%>
                        <%--</li>--%>
                    <%--</c:when>--%>
                    <%--<c:otherwise>--%>
                    <%--</c:otherwise>--%>
                <%--</c:choose>--%>
            <%--</c:forEach>--%>
        <%--</ul>--%>
    </div>

</div>
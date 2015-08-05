<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="fragment-download">

    <div class="box-export">
        <p>You can download in this section the full set of analysis results files (abundance tables) that have been merged for all samples on this project.</p>

        <c:if test="${not empty downloadLinksV2}">
            <h3>Pipeline version 2.0</h3>
            <h4>Functional Analysis</h4>
            <ul>
                <c:forEach var="downloadLink" items="${downloadLinksV2}" varStatus="loop">
                    <c:choose>
                        <c:when test="${downloadLink.externalLink}">
                            <li>
                                <a href="${downloadLink.linkURL}"
                                   title="${downloadLink.linkTitle}">${downloadLink.linkTitle}</a>
                                <%--${downloadLink.linkText}--%>
                            </li>
                        </c:when>
                        <c:when test="${!downloadLink.externalLink}">
                            <%--<c:when test="${!downloadLink.externalLink && not empty model.sample.analysisCompleted}">--%>
                            <li>
                                <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                                   title="${downloadLink.linkTitle}">${downloadLink.linkTitle}</a>
                                <%--${downloadLink.linkText}--%>
                                <span
                                    class="list_date"> - ${downloadLink.fileSize}</span>
                            </li>
                        </c:when>
                        <c:otherwise>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
        </c:if>

        <c:if test="${not empty downloadLinksV1}">
            <h3>Pipeline version 1.0</h3>
            <h4>Functional Analysis</h4>
            <ul>
                <c:forEach var="downloadLink" items="${downloadLinksV1}" varStatus="loop">
                    <c:choose>
                        <c:when test="${downloadLink.externalLink}">
                            <li>
                                <a href="${downloadLink.linkURL}"
                                   title="${downloadLink.linkTitle}">${downloadLink.linkTitle}</a>
                            </li>
                        </c:when>
                        <c:when test="${!downloadLink.externalLink}">
                            <%--<c:when test="${!downloadLink.externalLink && not empty model.sample.analysisCompleted}">--%>
                            <li>
                                <a href="<c:url value="${baseURL}/${downloadLink.linkURL}"/>"
                                   title="${downloadLink.linkTitle}">${downloadLink.linkTitle}</a><span
                                    class="list_date"> - ${downloadLink.fileSize}</span>
                            </li>
                        </c:when>
                        <c:otherwise>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
        </c:if>
    </div>

</div>
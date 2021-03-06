<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="publications" required="true" type="java.util.Set" %>
<%@ attribute name="relatedPublications" required="true" type="java.util.List" %>
<%@ attribute name="relatedLinks" required="true" type="java.util.List" %>

<c:if test="${not empty publications}">

    <div class="sidebar-allrel">
        <c:if test="${not empty relatedPublications}">
            <div id="sidebar-related">
                <h2>Related Publications</h2>
                    <%--<span class="separator"></span>--%>
                <ul>
                    <c:forEach var="pub" items="${relatedPublications}" varStatus="status">
                        <li>
                            <strong><c:out value="${pub.pubTitle}"/></strong><br/>
                            <i><c:out value="${pub.shortAuthors}"/></i><br/>
                            <c:out value="${pub.year}"/> <c:out value="${pub.volume}"/>
                            <c:if test="${not empty pub.pubMedId and not empty pub.doi}">(
                                <c:if test="${not empty pub.pubMedId}"><b>PMID:</b><a class="ext" href="<c:url
                                value="http://europepmc.org/abstract/MED/${pub.pubMedId}"/>"><c:out
                                        value="${pub.pubMedId}"/></a></c:if>
                                <c:if test="${not empty pub.doi}"><b>DOI:</b><a class="ext" href="<c:url
                                value="http://dx.doi.org/${pub.doi}"/>"><c:out
                                        value="${pub.doi}"/></a></c:if>)</c:if><br/>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

        <c:if test="${not empty relatedLinks}">
            <div id="sidebar-related" style="margin-top:14px;">
                <h2>Related resources</h2>
                    <%--<span class="separator"></span>--%>
                <ul>
                    <c:forEach var="pub" items="${relatedLinks}" varStatus="status">
                        <li>
                            <a class="list_more" href="<c:url value="${pub.url}"/>"><c:out
                                    value="${pub.pubTitle}"/></a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>
    </div>
</c:if>
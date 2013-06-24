<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--Builds the pagination links for a page.
--%>

<%@ attribute name="paginationLinks" required="true" type="java.util.List" %>


    <c:forEach var="link" items="${paginationLinks}">
        <c:choose>
            <c:when test="${empty link.link}">
                <%--Don't put a link on the currently displayed page--%>
                <span class="paging current">${link.name}</span>
            </c:when>
            <c:otherwise>
            <span class="paging">
                <a class ="${link.className}" href="<c:url value="${link.link}"/>" title="${link.description}" rel="nofollow">
                    <c:choose>
                        <c:when test="${link.name eq 'First'}">
                           <span class="icon icon-functional" data-icon="["></span>
                           <%--${link.name}--%>
                        </c:when>
                        <c:when test="${link.name eq 'Last'}">
                            <%--${link.name}--%>
                            <span class="icon icon-functional" data-icon="]"></span>
                        </c:when>
                        <c:when test="${link.name eq 'Previous'}">
                            <span class="icon icon-functional" data-icon="<"></span>
                            <%--${link.name}--%>
                        </c:when>
                        <c:when test="${link.name eq 'Next'}">
                            <%--${link.name} --%>
                            <span class="icon icon-functional" data-icon=">"></span>
                        </c:when>
                        <c:otherwise>
                            ${link.name}
                        </c:otherwise>
                    </c:choose>
                </a>
            </span>
            </c:otherwise>
        </c:choose>
    </c:forEach>


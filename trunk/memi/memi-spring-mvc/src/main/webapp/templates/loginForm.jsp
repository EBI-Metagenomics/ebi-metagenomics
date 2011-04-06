<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<% final String url = request.getRequestURL().toString();
    final String context = request.getContextPath();
    final String requestRoot = url.substring(0, url.indexOf(context) + context.length());%>
<c:set var="enaUrlParameter" value="<%=requestRoot%>"/>
<%-- This template is used within the login page component--%>
<c:if test="${empty model.submitter}">
    <div id="sidebar-login">

        <img src="${pageContext.request.contextPath}/img/icon_lock.gif" alt="Login lock icon">

        <h2>Login</h2>
        Login to submit and view your data:<br/>
        <form:form method="POST" action="" commandName="loginForm">
            E-Mail:<br/> <form:errors cssClass="error" path="emailAddress"/><form:input id="loginInputFieldId" path="emailAddress"/> <br/>

            Password:<br/> <form:errors cssClass="error" path="password"/><form:password path="password"/> <br/>

            <input type="submit" name="login" class="main_button" value="Login"/>
        </form:form>

        <c:url var="enaPasswordUrl"
               value="${model.propertyContainer.enaSubmissionURL.forgottenPwdLink}">
            <c:param name="url" value="${enaUrlParameter}"/>
        </c:url>
        <a href="<c:out value="${enaPasswordUrl}"/>" title="Request a new password">Forgot your password?</a>
        <span class="separator"></span>
        <strong>New to metagenomics?</strong><br/>
        <c:url var="enaRegistrationUrl"
               value="${model.propertyContainer.enaSubmissionURL.registrationLink}">
            <c:param name="url" value="${enaUrlParameter}"/>
        </c:url>
        <a href="<c:out value="${enaRegistrationUrl}"/>" title="Registration">Click here to register</a>
    </div>
</c:if>


<div id="sidebar-news">
    <a href="${model.rssUrl}" rel="alternate" type="application/rss+xml" title="Metagenomics RSS feeds"><img
            src="${pageContext.request.contextPath}/img/icon_rss.gif" alt="Metagenomics RSS feeds"></a>

    <h2>News and Events</h2>
    <span class="separator"></span>
    <c:choose>
        <c:when test="${empty model.rssItems}">
            No news
        </c:when>
        <c:otherwise>
            <ul>
                <c:forEach var="entry" items="${model.rssItems}" varStatus="status">
                    <li>${entry.title}</li>
                </c:forEach>
            </ul>

        </c:otherwise>
    </c:choose>
    <%--<div id="rssfeed">--%>
    <%--<a href="${model.rssUrl}" class="rss" rel="alternate" type="application/rss+xml">RSS feed</a>--%>
    <%--</div>        --%>

    <p><a href="http://twitter.com/EBImetagenomics" class="twitter">Follow us on Twitter</a></p>
</div>

<div id="sidebar-mailing">
    <img src="${pageContext.request.contextPath}/img/icon_mailing.gif" alt="Mailing list">

    <h2>Mailing list</h2>

    <p>Subscribe to the EBI metagenomics mailing list to receive by email updated information on the website activities:
        <a href="http://listserver.ebi.ac.uk/mailman/listinfo/metagenomics">click here.</a></p>
</div>

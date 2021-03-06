<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- This template is used within the login page component - old login compenent .. to remove? --%>
<c:if test="${empty model.submitter}">
    <div id="sidebar-login">

        <img src="${pageContext.request.contextPath}/img/icon_lock.gif" alt="Login private data"/>

        <h2>Login</h2>
        Login to submit and view your data:<br/>
        <form:form method="POST" action="" commandName="loginForm">
            E-Mail:<br/> <form:errors cssClass="error" path="userName"/><form:input id="loginInputFieldId"
                                                                                        path="userName"/> <br/>

            Password:<br/> <form:errors cssClass="error" path="password"/><form:password path="password"/> <br/>

            <input type="submit" name="login" class="main_button" value="Login"/>
        </form:form>

        <%--<c:url var="enaPasswordUrl"--%>
               <%--value="${model.propertyContainer.enaSubmissionURL.forgottenPwdLink}">--%>
            <%--<c:param name="url" value="${enaUrlParam}"/>--%>
        <%--</c:url>--%>
        <a href="https://www.ebi.ac.uk/ena/submit/sra/#reset-password" title="Request a new password">Forgot your password?</a>
        <span class="separator"></span>
        <strong>New to metagenomics?</strong><br/>
        <c:url var="enaRegistrationUrl"
               value="${model.propertyContainer.enaSubmissionURL.registrationLink}">
            <c:param name="url" value="${enaUrlParam}"/>
        </c:url>
        <a href="<c:out value="${enaRegistrationUrl}"/>" title="Registration">Click here to register</a>
    </div>
</c:if>


<div id="sidebar-news">
    <a href="${model.rssUrl}" rel="alternate" type="application/rss+xml" title="Metagenomics RSS feeds"><img
            src="${pageContext.request.contextPath}/img/icon_rss.gif" alt="Metagenomics RSS feeds"/></a>

    <h2>News and Events</h2>
    <span class="separator"></span>
    <!--
    <ul>
        <li><span style="color:red;">Please be advised that due to engineering works this web service will be unavailable from Friday (October 21st) 2pm to Monday (October 24th) 12am. </span>
        </li>
    </ul>
    -->
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

    <p><a href="http://twitter.com/MGnifyDB" class="twitter">Follow us on Twitter</a></p>
</div>

<div id="sidebar-mailing">
    <img src="${pageContext.request.contextPath}/img/icon_mailing.gif" alt="Mailing list"/>

    <h2>Mailing list</h2>

    <p>Subscribe to the EBI metagenomics mailing list to receive update information.
        <a class="ext" href="http://listserver.ebi.ac.uk/mailman/listinfo/metagenomics">click here.</a></p>
</div>

<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--
Random filler text since if the error page is less 513 bytes then Internet Explorer will display ones of it's own
"Friendly Error Pages" instead.
Random filler text since if the error page is less 513 bytes then Internet Explorer will display ones of it's own
"Friendly Error Pages" instead.
Random filler text since if the error page is less 513 bytes then Internet Explorer will display ones of it's own
"Friendly Error Pages" instead.
Random filler text since if the error page is less 513 bytes then Internet Explorer will display ones of it's own
"Friendly Error Pages" instead.
-->


<div id="content-full">
    <h2>An Error Has Occurred</h2>

    <p class="intro">
        Sorry, there was a problem with this page/file.
        Please consider <a href="<c:url value="${baseURL}/contact"/>" title="Contact us">contacting us</a> to report
        this issue, including details of what happened and which web browser/version number you are using.
        <br/>To continue you can try going back to your <a href="#" onclick="history.go(-1);return false;">previous
        browser page</a>, using the navigation menu above or starting again from the EBI metagenomics
        <a href="<c:url value="${baseURL}/"/>" title="Home">home page</a>.
    </p>

    <%--<p>--%>
    <%--<strong>ErrorMessage:</strong><br/>--%>
    <%--${pageContext.exception.message}--%>
    <%--</p>--%>

    <%--<p>--%>
    <%--<strong>Stack Trace:</strong><br/>--%>
    <%--<c:forEach var="st" items="${pageContext.exception.stackTrace}">--%>
    <%--${st}--%>
    <%--</c:forEach>--%>
    <%--</p>--%>
</div>

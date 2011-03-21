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

    <p>
        Sorry, there was a problem with this page/file.
    </p>

    <p>
        <strong>ErrorMessage:</strong><br />
        ${pageContext.exception.message}
    </p>
    <p>
        <strong>Stack Trace:</strong><br />
        <c:forEach var="st" items="${pageContext.exception.stackTrace}">
           ${st}
        </c:forEach>
    </p>


</div>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${empty mgModel.submitter}">
        <form:form method="POST" action="" commandName="loginForm">
            <table>
                <tr>
                    <td>E-Mail*:<form:errors cssStyle="color:red;" path="emailAddress"/></td>
                </tr>
                <tr>
                    <td><form:input path="emailAddress"/></td>
                </tr>
                <tr>
                    <td>Password:<form:errors cssStyle="color:red;" path="password"/></td>
                </tr>
                <tr>

                    <td><form:password path="password"/></td>
                </tr>
                <tr>
                    <td><input type="submit" value="Submit"/></td>
                </tr>
            </table>
        </form:form>
        <p>* A valid login is:<br>
            <b>E-Mail:</b> TEST<br>
            <b>Password:</b> test</p>
    </c:when>
    <c:otherwise>
        <c:out value="Hallo ${mgModel.submitter.surname}"/><br>
        <spring:url var="logoutUrl" value="logout"/>
        <a href="<c:url value="${logoutUrl}"/>">Logout</a>
    </c:otherwise>
</c:choose>

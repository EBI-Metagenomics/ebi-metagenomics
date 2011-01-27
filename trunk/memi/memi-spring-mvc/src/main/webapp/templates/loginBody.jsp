<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="content">
    <div style="margin-top:6px"></div>
    <table id="login">
        <form:form method="POST" action="login" commandName="loginForm">
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
        </form:form>
        <tr>
            <td>* A valid login is:</td>
        <tr>
        <tr>
            <td><b>E-Mail:</b> TEST</td>
        <tr>
        <tr>
            <td><b>Password:</b> test</td>
        <tr>
        <tr>
            <td>
                <a href="<c:url value=" https://www.ebi.ac.uk/embl/genomes/submission/registration.jsf"/>"
                   title="Registration">How to register?</a>
            </td>
        <tr>
        <tr>
            <td>
                <a href="<c:url value=" https://www.ebi.ac.uk/embl/genomes/submission/forgot-passw.jsf?_afPfm=5"/>"
                   title="Request a new password"> Forgotten your password?</a></td>
        </tr>
    </table>
</div>
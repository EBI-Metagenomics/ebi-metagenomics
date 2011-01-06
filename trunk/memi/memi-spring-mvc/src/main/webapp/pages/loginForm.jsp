<%--
  Created by Maxim Scheremetjew, EMBL-EBI, InterPro
  Date: 12-Nov-2010
  Desc: Represents a login form, which inludes
  1. textfield for email address
  2. password field
  3. submit button called Log in
  4. link to register form
  5. link to 'Forgetten your password?'
  --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<body>
<form:form method="POST" action="homePage" commandName="loginForm">
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
</body>
</html>
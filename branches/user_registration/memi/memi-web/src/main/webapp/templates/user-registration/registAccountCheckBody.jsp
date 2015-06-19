<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 6/18/15
  Time: 3:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h2>Do you have an EBI Webin account?</h2>

<form:form method="POST" commandName="registrationForm">
    <table>
        <tr>
            <td><form:radiobutton path="doesAccountExist" value="true" />Yes</td>
        </tr>
        <tr>
            <td><form:radiobutton path="doesAccountExist" value="false"/>No</td>
        </tr>
        <tr>
            <td><form:errors path="doesAccountExist" cssClass="error"/></td>
        </tr>
        <tr>
            <td>User name :</td>
            <td><form:input path="userName"/></td>
            <td>Submission account id (Webin-*)</td>
        </tr>
        <tr>
            <td><form:errors path="userName" cssClass="error"/>
            </td>
        </tr>
        <tr>
            <td colspan="3"><input type="submit" value="Check"
                                   name="_target1"/> <input type="submit" value="Cancel"
                                                            name="_cancel"/></td>
        </tr>
    </table>
</form:form>
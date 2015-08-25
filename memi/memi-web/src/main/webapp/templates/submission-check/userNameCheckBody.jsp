<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 6/18/15
  Time: 3:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h2>Enter Webin account user name</h2>

<p class="intro">
    We would like to check if you gave us permission to access your data from ENA for analysis.
</p>

<form:form method="POST" commandName="registrationForm">
    <table>
        <tr>
            <td>User name :</td>
            <td><form:input path="userName"/></td>
            <td>Email address or submission account id (Webin-*)</td>
        </tr>
        <tr>
            <td><form:errors path="userName" cssClass="error"/>
            </td>
        </tr>
        <tr>
            <td colspan="3"><input type="submit" value="Previous"
                                   name="_target0"/> <input type="submit" value="Next"
                                                            name="_target2"/> <input type="submit" value="Cancel"
                                                                                     name="_cancel"/></td>
        </tr>
    </table>
</form:form>
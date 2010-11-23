<%--
  Created by Maxim Scheremetjew, EMBL-EBI, InterPro
  Date: 12-Nov-2010
  Desc: Represents login page, which inludes
  1. textfield for email address
  2. password field
  3. submit button called Log in
  4. link to register form
  5. link to 'Forgetten your password?'
  --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>MG Portal Login form</title>
    <link href="css/memi.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
<div id="right_side_navigation">
    <p><a href="<c:url value="homePage.htm"/>">Home</a></p>
</div>
<div id="content">
    <div style="margin-top:60px"></div>
    <h2><fmt:message key="label.loginForm.title"/></h2>

    <div style="margin-top:6px"></div>
    <form:form action="loginForm.htm" commandName="loginForm">
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
    <p>* A valid login is:<br><br>
        <b>E-Mail:</b> TEST<br>
        <b>Password:</b> test</p>
</div>
</body>
</html>
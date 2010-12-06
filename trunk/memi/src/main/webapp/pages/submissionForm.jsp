<%--
  Created by Maxim Scheremetjew, EMBL-EBI, InterPro
  Date: 25-Nov-2010
  Desc: Represents a submission form
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
    <title><fmt:message key="label.submission.title"/></title>
    <link href="css/memi.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
<div id="right_side_navigation">
    <p><a href="<c:url value="homePage.htm"/>">Home</a></p>
</div>
<div id="content">
    <div style="margin-top:60px"></div>
    <h2><fmt:message key="label.submission.title"/></h2>

    <div style="margin-top:6px"></div>
    <form:form action="submissionForm.htm" commandName="subForm">
        <table>
            <tr>
                <td>Submission title*:<form:errors cssStyle="color:red;" path="subTitle"/></td>
            </tr>
            <tr>
                <td><form:input path="subTitle"/></td>
            </tr>
            <tr>
                <td>Submission explanation:*<form:errors cssStyle="color:red;" path="subExplanation"/></td>
            </tr>
            <tr>

                <td><form:textarea path="subExplanation"/></td>
            </tr>
            <tr>
                <td>Data description:*<form:errors cssStyle="color:red;" path="dataDesc"/></td>
            </tr>
            <tr>

                <td><form:textarea path="dataDesc"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Submit"/></td>
            </tr>
        </table>
    </form:form>
    <p>* Required</p>
</div>
</body>
</html>
<%--
  Created by Maxim Scheremetjew, EMBL-EBI, InterPro
  Date: 25-Nov-2010
  Desc: Represents the Metagenomics portal home page, which inludes
  1. a login function
  2. a link to submission form
  3. a link of public and private (if you logged in) studies
  4. a link to study list
  4. info about what Metagenomics is and what kind of services this portal provides
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>MG Portal Submission Success Page</title>
    <link href="css/memi.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
<div id="right_side_navigation">
    <p><a href="<c:url value="homePage.htm"/>">Home</a></p>
</div>
<div id="content">
    <div style="margin-top:60px"></div>
    <h2>MG Portal Submission Success Page</h2>

    <div style="margin-top:6px"></div>
    <p>Sent submission successfully!</p>
</div>
</body>
</html>
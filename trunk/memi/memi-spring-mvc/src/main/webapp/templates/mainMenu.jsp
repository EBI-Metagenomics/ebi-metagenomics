<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
Created by Maxim Scheremetjew, EMBL-EBI, InterPro
Date: 04-Jan-2011
MG portal main menu template
--%>
<div style="margin-top:80px"/>
<div align="center">
    <a href="<c:url value="${baseURL}/index"/>" title="Home">Home</a>
    <a id="login" href="#" title="Login">Login</a>

    <a href="<c:url value="${baseURL}/listStudies"/>" title="Search study">Search study</a>
    <a href="#" title="Search samples">Search samples</a>

    <a href="#" title="Help">Help</a>
    <a href="mailto:maxim@ebi.ac.uk?cc=maxim.scheremetjew@gmail.com&subject=Request from the MG portal">Contact
        us</a>
</div>
<div style="margin-top:60px"/>
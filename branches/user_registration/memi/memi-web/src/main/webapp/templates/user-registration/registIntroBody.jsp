<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 6/18/15
  Time: 4:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Registration and data submission</h2>

<p class="intro">
    If you have data that you wish to have analysed, you need an <strong>ENA Webin account</strong> that
    has been registered with EBI Metagenomics. This allows us to track your submitted data and ensures that we have
    consent to access it for analysis.
</p>

<p class="intro">
    Click <a href="<c:url value="${baseURL}/wizard.form"/>" title="Register">here</a> to start the registration
    procedure.
</p>
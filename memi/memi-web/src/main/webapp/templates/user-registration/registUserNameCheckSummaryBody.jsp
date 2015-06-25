<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 6/18/15
  Time: 3:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h2>User account consent note</h2>

<div class="register_form">
<form:form method="POST" commandName="registrationForm" id="sub_form">
<div class="user_det_box">
<div class="form_row">
<p class="intro" title="${accountCheckResult}">Could you please read carefully the following text?</p>
<p><form:checkbox path="consentCheck" value="on" id="gwt-debug-consentField-input"/> By checking this box, I give consent and confirm that the data submitted through this account is NOT sensitive, restricted-access or human-identifiable.
    <form:errors path="consentCheck" cssClass="error"/>
</p>
<p>
<input id="submit_button" name="_target2" value="Submit" class="main_button" type="submit"/>
<span class="clear_but">| <a href="/metagenomics/register" title="cancel">Cancel</a></span>
</p>
</div></div>
</form:form>
</div>
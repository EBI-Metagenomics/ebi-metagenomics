<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="content-full">
    <h3>Beta Website Feedback</h3>

    <p>If you have feedback about this new website, please use the form below to send us your
        comments:</p>

    <form:form id="feedback_form" action="feedback" commandName="feedbackForm">
        <div class="feedbackform_tab"></div>
        <fieldset>
            <table class="result" id="contact">
                <tr>
                    <td><span><spring:message code="forms.email.inputField.label"/><small>*</small> :</span>
                        <form:input path="emailAddress" cssStyle="margin-left:14px; width:284px;"/>
                    </td>
                </tr>
                <tr>
                    <td><form:errors cssClass="error" path="emailAddress"/></td>
                </tr>
                <tr>
                    <td><span><spring:message code="feedbackForm.inputField.subject.label"/><small>*</small> :</span>
                        <form:input path="emailSubject" cssStyle="width:284px;"/>
                    </td>
                </tr>
                <tr>
                    <td><form:errors cssClass="error" path="emailSubject"/></td>
                </tr>
                <tr>
                    <td><span><spring:message code="feedbackForm.inputField.message.label"/><small>*
                    </small> :</span><br/><form:textarea path="emailMessage" cols="50" rows="10"/></td>
                </tr>
                <tr>
                    <td><form:errors cssClass="error" path="emailMessage"/></td>
                </tr>
                <!-- If you are a human please leave this field blank -->
                <form:input type="hidden" path="leaveIt" cssStyle="margin-left:14px; width:284px;"/>
                <tr>
                    <td>
                        <input type="submit" name="submit" value="Submit" class="main_button"/>
                        <span class="clear_but" style="float:none;">| <input id="resetFeedbackFrom" type="reset"
                                                                             value="Reset"
                                                                             class="main_button"/></span>
                    </td>
                </tr>
            </table>
        </fieldset>
        <small>* Required</small>
    </form:form>

    <%--<form id="feedback_form" action="feedback" method="post">--%>
    <%--<div class="feedbackform_tab"></div>--%>
    <%--<fieldset>--%>
    <%--<table class="result" id="contact">--%>
    <%--<tr>--%>
    <%--<td><label for="emailAddress">E-Mail address--%>
    <%--<small>*</small>--%>
    <%--:</label>--%>
    <%--<input type="email" id="emailAddress" name="emailAddress" required="required"--%>
    <%--style="margin-left:14px; width:284px;"/>--%>
    <%--</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td>--%>
    <%--<div id="addressErrorMessage" class="error"></div>--%>
    <%--</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td><label for="emailSubject">Feedback subject--%>
    <%--<small>*</small>--%>
    <%--:</label><input type="text" id="emailSubject" name="emailSubject" required="required"--%>
    <%--style="width:284px;"/>--%>
    <%--</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td>--%>
    <%--<div id="subjectErrorMessage" class="error"></div>--%>
    <%--</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td><label for="emailMessage">Your comments--%>
    <%--<small>*</small>--%>
    <%--:</label><br/><textarea id="emailMessage" name="emailMessage" required="required" cols="50"--%>
    <%--rows="10"></textarea>--%>
    <%--</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td>--%>
    <%--<div id="msgError" class="error"></div>--%>
    <%--</td>--%>
    <%--</tr>--%>
    <%--<input type="hidden" name="leaveIt" id="leaveIt" style="margin-left:14px; width:284px;"/>--%>
    <%--<tr>--%>
    <%--<td>--%>
    <%--<input type="submit" name="submit" value="Submit" class="main_button"/>--%>
    <%--<span class="clear_but" style="float:none;">| <input id="resetFeedbackFrom" type="reset"--%>
    <%--value="Reset"--%>
    <%--class="main_button"/></span>--%>
    <%--</td>--%>
    <%--</tr>--%>
    <%--</table>--%>
    <%--</fieldset>--%>
    <%--<small>* Required</small>--%>
    <%--</form>--%>
</div>
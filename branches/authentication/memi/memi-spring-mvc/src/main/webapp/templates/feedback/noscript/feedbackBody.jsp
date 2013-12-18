<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


    <h3>Beta website feedback</h3>

    <p>If you have feedback about this new website, please use the form below to send us your
        comments:</p>

    <div class="feedbackform_tab_o"></div>
    <div class="sub">
        <form:form id="feedback_form_no" action="feedback" commandName="feedbackForm">
            <fieldset>
                <table class="result">
                    <tr>
                        <td>
                            <label for="feedbackForm_emailAddress" id="required"><spring:message
                                    code="email.label"/></label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:input id="feedbackForm_emailAddress" path="emailAddress" cssErrorClass="error"
                                        cssStyle="width:284px;"/>
                            <form:errors cssClass="error" path="emailAddress"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="feedbackForm_emailSubject" id="required"><spring:message
                                    code="feedbackForm.inputField.subject.label"/></label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:input id="feedbackForm_emailSubject" path="emailSubject" cssErrorClass="error"
                                        cssStyle="width:284px;"/>
                            <form:errors cssClass="error" path="emailSubject"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="feedbackForm_emailMessage" id="required"><spring:message
                                    code="feedbackForm.inputField.message.label"/></label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:textarea id="feedbackForm_emailMessage" path="emailMessage" cssErrorClass="error"
                                           cols="50" rows="10"/>
                            <form:errors cssClass="error" path="emailMessage"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="submit" name="submit" value="Submit" class="main_button"/>
                                <%--<span class="clear_but">| <a href="javascript:clearFeedbackForm()" title="Clear the form">Clear</a></span>--%>
                            <span class="clear_but">| <input type="reset" value="Clear" class="main_button"/></span>
                        </td>
                    </tr>
                    <!-- If you are a human please leave this field blank -->
                    <form:input type="hidden" path="leaveIt"/>
                    <tr>
                        <td>
                            <span id="required"></span>&nbsp;
                            <small>required</small>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </form:form>
    </div>

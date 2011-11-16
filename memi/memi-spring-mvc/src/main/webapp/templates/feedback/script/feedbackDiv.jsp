<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="feedback_div">
    <div class="feedbackform_tab_v">
        <a id="script_feedbackLink" href="javascript:hideFeedbackForm()" title="Give your feedback"></a>
        <!--<a id="noscript_feedbackLink" href="<c:url value="${baseURL}/feedback"/>" title="Give your feedback"></a>-->
    </div>

    <div class="cont_feedback_form">
        <form id="feedback_form" action="**/doFeedback">
            <!--<div class="feedbackform_tab_o"></div>-->
            <!--<h3>Send us your feedback</h3>-->
            <fieldset>
                <table class="result"
                       style="width:600px;/*made it larger than the container in purpose to make it nicer*/">
                    <tr>
                        <td>
                            <div class="close_wind">
                                <a href="javascript:hideFeedbackForm()" title="Close this window"></a>
                            </div>
                            <label for="emailAddress"  id="required"><spring:message code="feedbackForm.inputField.email.label"/></label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input id="emailAddress" name="emailAddress"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="emailSubject"  id="required"><spring:message code="feedbackForm.inputField.subject.label"/></label>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="text" name="emailSubject" id="emailSubject"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="emailMessage"  id="required"><spring:message code="feedbackForm.inputField.message.label"/></label>
                            <br/><textarea id="emailMessage" name="emailMessage"></textarea>
                        </td>
                    </tr>

                     <tr>
                        <td>
                            <input type="submit" name="submit" value="Submit" class="main_button"/>
                            <span class="clear_but">| <a href="#" onclick="clearFeedbackForm()"
                                                         title="Clear the form">Clear</a></span>
                        </td>
                    </tr>
                <input type="hidden" name="leaveIt" id="leaveIt" />
                <tr>
                    <td>
                    <span id="required"></span>&nbsp;<small>required</small>
                    </td>
                </tr>

                </table>
            </fieldset>
        </form>
    </div>
</div>
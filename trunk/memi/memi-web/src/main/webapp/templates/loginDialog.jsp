<%--author: Maxim Scheremetjew, EBI, InterPro--%>
<%--Represents a JSP which provides a JQuery login dialog--%>
<%--Login form validation is handled on the server side by a AJAX POST request--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- login dialog script for error messages -->
<script src="${pageContext.request.contextPath}/js/loginDialog_script.js" type="text/javascript"></script>
<!-- Jquery BlockUI to display popup-->
<script src="${pageContext.request.contextPath}/js/jquery.blockUI.js" type="text/javascript"></script>

<%--Login dialog--%>
<div id="login_dialog_div" class="sub">
    <div class="anim close_window close_window_button" title="Close this window">&#10006;</div>
    <div class="sub_log">
        <form:form id="login_form" action="**/doLogin" commandName="loginForm">

                <div class="form_row"><h3>Login</h3></div>
                <div class="form_row">
                    <label for="loginDialog_userName"><spring:message code="username.label"/></label>
                    <form:input id="loginDialog_userName" path="userName" cssStyle="width:260px;"/>
                </div>
                <div class="form_row">
                    <label for="loginDialog_password"><spring:message code="loginForm.inputField.password.label"/></label>
                    <form:password id="loginDialog_password" path="password" cssStyle="width:260px;"/>
                    <span id="loginDialog_userName.errors" class="error"></span>
                </div>
                <div>
                    <a href="https://www.ebi.ac.uk/ena/submit/sra/#reset-password" title="Request a new password" style="color:#188960;">
                        <spring:message code="loginForm.link.external.forgotten.password"/>
                    </a>
                </div>
                <div class="form_row_log">
                    <input type="submit" name="login" value="Login" class="main_button"/>&nbsp;
                    <span class="clear_but">| <a class="close_window_button" href=""
                                                 title="Cancel">Cancel</a></span>
                </div>

        </form:form>
    </div>

    <div class="sub_sign">
        <div class="form_row"><h3>Not registered yet?</h3>
            <%--<c:url var="enaRegistrationUrl"--%>
                   <%--value="${model.propertyContainer.enaSubmissionURL.registrationLink}">--%>
                <%--<c:param name="url" value="${enaUrlParam}"/>--%>
            <%--</c:url>--%>
            <span class="sub_sign_text"><a href="<c:url value="${baseURL}/register"/>"
                                           title="Registration" style="color:#188960;">Sign-up</a> to register</span>
        </div>
        <div class="form_row" style="font-size:140%;">or</div>
        <p><a href="https://www.ebi.ac.uk/ena/submit/sra/#home"> <img src="${pageContext.request.contextPath}/img/ico_ena_user.jpg"
                                                          alt="ENA member" border="0"></a></p>

        <p class="sub_sign_note">If you already are a registered user of the European Nucleotide Archive (ENA), you
            should simply use your ENA account to login.</p>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        //Click event for openEntryViewPopup
        $('#LoginBlockUI, #LoginBlockUI-text').click(function () {
            $.blockUI({ message:$('#login_dialog_div'), baseZ:100000, overlayCSS:{ backgroundColor:'#000', opacity:0.6, cursor:'pointer'}, css:{top:'25%', left:'25%', width:'708px', padding:'0px', border:'1px #AAA solid', 'border-radius':'12px', backgroundColor:'#C9C9C9' }});
            //make the black overlay clickable and removable
            $('.blockOverlay').click($.unblockUI);
            $('#login_form').trigger("reset");
            return false;
        });//end click event

        //Click on close cross icon
        $(".close_window_button").click(function () {
                  $.unblockUI();
            return false;
              });//end cancel click

    });
</script>
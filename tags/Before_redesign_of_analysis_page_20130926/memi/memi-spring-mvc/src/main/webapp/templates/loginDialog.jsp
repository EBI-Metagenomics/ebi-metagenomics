<%--author: Maxim Scheremetjew, EBI, InterPro--%>
<%--Represents a JSP which provides a JQuery login dialog--%>
<%--Login form validation is handled on the server side by a AJAX POST request--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- The following line includes the login dialog script. -->
<script src="${pageContext.request.contextPath}/js/loginDialog_script.js" type="text/javascript"></script>
<%--Login dialog--%>
<div id="login_dialog_div" class="sub">
    <div class="sub_log">
        <form:form id="login_form" action="**/doLogin" commandName="loginForm">
            <fieldset>
                <div class="form_row"><h3>Login</h3></div>
                <div class="form_row">
                    <label for="loginDialog_emailAddress"><spring:message
                            code="email.label"/></label>
                    <form:input id="loginDialog_emailAddress" path="emailAddress" cssStyle="width:313px;"/>
                </div>
                <div class="form_row">
                    <label for="loginDialog_password"><spring:message
                            code="loginForm.inputField.password.label"/></label>
                    <form:password id="loginDialog_password" path="password" cssStyle="width:313px;"/>
                    <span id="loginDialog_emailAddress.errors" class="error"></span>
                </div>
                    <%--ENA URL parameter: Neccessary for the ENA implementation to redirect back to the Metagenomics website for instance if somebody wants to create a new account--%>
                <c:url var="enaPasswordUrl"
                       value="${model.propertyContainer.enaSubmissionURL.forgottenPwdLink}">
                    <c:param name="url" value="${enaUrlParam}"/>
                </c:url>
                <div>
                    <a href="<c:out value="${enaPasswordUrl}"/>" title="Request a new password" style="color:#188960;">
                        <spring:message code="loginForm.link.external.forgotten.password"/>
                    </a>
                </div>
                <div class="form_row_log">
                    <input type="submit" name="login" value="Login" class="main_button"/>&nbsp;
                    <span class="clear_but">| <a href="javascript:closeLoginDialogForm()"
                                                 title="Cancel">Cancel</a></span>
                </div>
            </fieldset>
        </form:form>
    </div>

    <div class="sub_sign">
        <div class="form_row"><h3>Not registered yet?</h3>
            <c:url var="enaRegistrationUrl"
                   value="${model.propertyContainer.enaSubmissionURL.registrationLink}">
                <c:param name="url" value="${enaUrlParam}"/>
            </c:url>
            <span class="sub_sign_text"><a href="<c:url value="${enaRegistrationUrl}"/>"
                                           title="Registration" style="color:#188960;">Sign-up</a> to register</span>
        </div>
        <div class="form_row" style="font-size:140%;">or</div>
        <p><a href="https://www.ebi.ac.uk/ena/home"> <img src="${pageContext.request.contextPath}/img/ico_ena_user.jpg"
                                                          alt="ENA member" border="0"></a></p>

        <p class="sub_sign_note">If you already are a registered user of the European Nucleotide Archive (ENA), you
            should simply use your ENA account to login.</p>
    </div>
</div>
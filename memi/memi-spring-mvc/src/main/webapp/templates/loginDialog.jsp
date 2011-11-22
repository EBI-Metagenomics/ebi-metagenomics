<%--author: Maxim Scheremetjew, EBI, InterPro--%>
<%--Represents a JSP which provides a JQuery login dialog--%>
<%--Login form validation is handled on the server side by a AJAX POST request--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">
    var dialog;
    var loginDialogValidator;
    $(function() {
        dialog =
                $("#login_dialog_div").dialog({
                    autoOpen: false, // Avoids auto open after a new dialog instance is being created
                    title: 'Login for EBI Metagenomics', //Dialog title
                    modal: true, //Dialog has modal behavior; other items on the page are disabled
                    closeOnEscape: true, //Dialog closes when it has focus and the user presses the esacpe (ESC) key
                    height: 400,
                    width: 730,
                    beforeClose: function(event, ui) {
                        $("#errorMessage").html("");
                    }
                });

        //Close dialog by mouse click in the overlay area
        $('.ui-widget-overlay').live("click", function() {
            dialog.dialog("close");
        });

        var settings = {
            type: "POST",
            url: $("#login_form").attr('action'),
            data: $("#login_form").serialize(),
            dataType: 'html',
            success: function() {
                window.location = self.location;
            },
            //The following part handles server side detected form validation errors
            error: function(xhr, ajaxOptions, thrownError) {
                var jsonObject = jQuery.parseJSON(xhr.responseText);
                if (jsonObject != null) {
                    //Makes error message DIV block visible
                    document.getElementById('loginDialog_emailAddress.errors').style.display = "block";
                    //Sets colour of input field to red, indicating an input error
                    document.getElementById("loginDialog_emailAddress").setAttribute("class", "error");
                    document.getElementById("loginDialog_password").setAttribute("class", "error");
                    //Sets error message of DIV block
                    document.getElementById('loginDialog_emailAddress.errors').innerHTML = jsonObject.password;
                }
            }
        };

//        $("#login_form").submit(function() {
//            //when the form is going to be submitted it is sent as an ajax request
//            //so the answer can be placed in the current page
//            $.ajax(settings);
//            //return false to avoid the default submit() behaviour can take place
//            return false;
//        });

        //      Client side feedback form validation and submission. Please notice that there also exists a server side form validation
//      Be aware of the following issue: http://www.ozonesolutions.com/programming/2011/09/ajaxform-and-validation-plugins-for-jquery/
        loginDialogValidator = $('#login_form').validate(
        {
            submitHandler: function(form) {
                // pass options to ajaxSubmit
                $(form).ajaxSubmit(settings);
                return false;
            },
            rules: {
                emailAddress: {
                    required: true,
                    email: true
                },
                password: {
                    required: true
                }
            },
            messages: {
                emailAddress: {
                    required: "Please enter a valid email address",
                    email: "Your email address must be in the format of name@domain.com"
                },
                password: {
                    required: "Please enter your password"
                }
            }
        });
//      How JQuery form Plugin works: http://jquery.malsup.com/form/#getting-started
        $('#login_form').ajaxForm(settings);
    });
    //Opens the dialog
    function openLoginDialogForm() {
        loginDialogValidator.resetForm();
        dialog.dialog("open");
    }
    //Closes the dialog
    function closeLoginDialogForm() {
        dialog.dialog("close");
    }
</script>
<%--Login dialog--%>
<div id="login_dialog_div" class="sub">
    <%--<div id="errorMessage" class="error"></div>--%>
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
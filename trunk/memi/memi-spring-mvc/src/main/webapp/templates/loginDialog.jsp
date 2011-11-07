<%--author: Maxim Scheremetjew, EBI, InterPro--%>
<%--Represents a JSP which provides a JQuery login dialog--%>
<%--Login form validation is handled on the server side by a AJAX POST request--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    final String url = request.getRequestURL().toString();
    final String context = request.getContextPath();
    final String requestRoot = url.substring(0, url.indexOf(context));
    // using getAttribute allows us to get the orginal url out of the page when a forward has taken place.
    String queryString = "?" + request.getAttribute("javax.servlet.forward.query_string");
    String requestURI = "" + request.getAttribute("javax.servlet.forward.request_uri");
    if (requestURI.equals("null")) {
        // using getAttribute allows us to get the orginal url out of the page when a include has taken place.
        queryString = "?" + request.getAttribute("javax.servlet.include.query_string");
        requestURI = "" + request.getAttribute("javax.servlet.include.request_uri");
        if (requestURI.equals("null")) {
            queryString = "?" + request.getQueryString();
            requestURI = request.getRequestURI();
        }
    }
    if (queryString.equals("?null")) queryString = "";
    String activePage = requestRoot + requestURI + queryString;
%>
<script>
    $(function() {
        var $dialog =
                $("#login_dialog_div").dialog({
                    autoOpen: false, // Avoids auto open after a new dialog instance is being created
                    title: 'Login for EBI Metagenomics', //Dialog title
                    modal: true, //Dialog has modal behavior; other items on the page are disabled
                    closeOnEscape: true, //Dialog closes when it has focus and the user presses the esacpe (ESC) key
                    height: 400,
                    width: 730
                });

        //Open dialog command by clicking link with Id jqueryLogin
        $("#jqueryLogin")
                .click(function() {
            $dialog.dialog("open");
            return false;
        });
         $("#j2querylogin")
                .click(function() {
            $dialog.dialog("open");
            return false;
        });

        //Close dialog by mouse click in the overlay area
        $('.ui-widget-overlay').live("click", function() {
            $dialog.dialog("close");
        });
        //Close dialog by press the mouse button
        $('#cancelLoginDialog').live("click", function() {
            $dialog.dialog("close");
            return false;
        });

        $("#login_form").submit(function() {
            //when the form is going to be submitted it is sent as an ajax request
            //so the answer can be placed in the current page
            $.ajax({
                type: "POST",
                url: $("#login_form").attr('action'),
                data: $("#login_form").serialize(),
                dataType: 'html',
                success: function() {
                    window.location = self.location;
                },
                error: function(request, error) {
                    $("#errorMessage").html(request.responseText);
                }
            });
            //return false to avoid the default submit() behaviour can take place
            return false;
        });
    });
</script>
<%--ENA URL parameter: Neccessary for the ENA implementation to redirect back to the Metagenomics website for instance if somebody wants to create a new account--%>
<c:set var="enaUrlParam" value="<%=activePage%>"/>
<%--Login dialog--%>
<div id="login_dialog_div" class="sub">
    <div id="errorMessage" class="error"></div>
    <div class="sub_log">
        <form id="login_form" action="doLogin">
            <fieldset>
                <div class="form_row"><h3>Login</h3></div>

                <div class="form_row">
                    <label for="emailAddress">E-Mail:</label>
                    <br/>
                    <input type="text" name="emailAddress" id="emailAddress" style="width:313px;"/>
                </div>
                <div class="form_row">
                    <label for="password">Password:</label>
                    <br/>
                    <input type="password" name="password" id="password" style="width:313px;"/>
                </div>
                <c:url var="enaPasswordUrl"
                       value="${model.propertyContainer.enaSubmissionURL.forgottenPwdLink}">
                    <c:param name="url" value="${enaUrlParam}"/>
                </c:url>
                <div><a href="<c:out value="${enaPasswordUrl}"/>" title="Request a new password" style="color:#188960;">Forgot your
                    password?</a></div>
                <div class="form_row_log">
                    <input type="submit" name="login" value="Login" class="main_button"/>&nbsp;
                    <span class="clear_but" style="float:none;">| <a id="cancelLoginDialog" href="" title="Cancel">Cancel</a></span>
                </div>
            </fieldset>
        </form>
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
        <p><a href="https://www.ebi.ac.uk/ena/home"> <img src="${pageContext.request.contextPath}/img/ico_ena_user.jpg" alt="ENA member" border="0"></a></p>

        <p class="sub_sign_note">If you already are a registered user of the European Nucleotide Archive (ENA), you should simply use your ENA account to log-in.</p>
    </div>
</div>
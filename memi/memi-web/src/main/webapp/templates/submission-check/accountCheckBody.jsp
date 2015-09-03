<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 6/18/15
  Time: 3:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="register_form">

    <c:url var="accountCheck" value="${baseURL}/registration/account-check"/>
    <form:form method="POST" commandName="consentCheckForm" action="${accountCheck}" id="sub_form">

        <c:if test="${consentCheckForm.newUser}">


            <h2>Do you have an EBI Webin account?</h2>

            <p>A Webin account is an ENA submission account provided by the European Nucleotide Archive. You
                should have received a Webin account if you have previously submitted data to ENA (read data,
                genome assemblies, etc.).</p>

            <div class="form_row">
                <label style="display:inline-block;"><form:radiobutton path="doesAccountExist"
                                                                       id="radio_button_y" value="true"/>
                    Yes</label>
                <label style="display:inline-block;"><form:radiobutton path="doesAccountExist"
                                                                       id="radio_button_n" value="false"/>
                    No</label>
                        <span class="form_info" id="ENA_redirect" style="display:none;margin:18px 0 0 9px;"><span
                                class="ico_loading ico-spin"></span> You will be redirected to ENA website to create an ENA submitter account. If this doesn't happen automatically, please <a
                                href="https://www.ebi.ac.uk/ena/submit/sra/#metagenome_registration">click
                            here</a>.</span>
            </div>

        </c:if>

        <div class="user_det_box" style="display:${displayUsernameBox};">
            <h2>Registered with us?</h2>

            <p>In order to confirm that your ENA submitter account is registered with us, please enter your account details.</p>

            <fieldset style="border:0px solid grey;">
                <div class="form_row">
                    <label>Username</label>
                    <form:input path="userName" cssErrorClass="error"/>
                    <span class="form_info">The username is the submission account id, starting with <i>Webin-*</i></span>
                    <form:errors path="userName" cssClass="error"/>
                    <label>Email Address</label>
                    <form:input path="email" cssErrorClass="error"/>
                    <span class="form_info">Your contact email address associated with the submission account above.</span>
                    <form:errors path="email" cssClass="error"/>
                    <label>Password</label>
                    <form:password path="password" cssErrorClass="error"/>
                    <form:errors path="password" cssClass="error"/>
                </div>
                <span class="error" style="display:${displayUsernameBox};">${accountCheckResult}</span>

                    <%--<form:radiobutton cssStyle="display: none;" path="newUser" value="${consentCheckForm.newUser}"/>--%>
                <p>
                    <input id="submit_button" name="_target1" value="Check" class="main_button" type="submit"/>
                            <span class="clear_but">| <a href="/metagenomics/submission"
                                                         title="cancel">Cancel</a></span>
                </p>
            </fieldset>
        </div>


    </form:form>

</div>

<script type="text/javascript">
    $(document).ready(function () {
        //script to do auto redirections
        var timeoutFunction;
        $('input:radio[name="doesAccountExist"]').change(function () {
            if ($(this).val() == 'true') {
                if (timeoutFunction != undefined) {
                    clearTimeout(timeoutFunction);
                }
                $('#ENA_redirect').hide();
                $('.user_det_box').fadeIn("slow");
            }
            if ($(this).val() == 'false') {
                $('.user_det_box').hide();
                $('#ENA_redirect').show();
                timeoutFunction = setTimeout(function () {
                    window.location.href = "https://www.ebi.ac.uk/ena/submit/sra/#metagenome_registration";
                }, 9000);
            }
        });
    });

</script>
<script type="text/javascript">
    //script to get the url parameter and change page behavior depending on it
    var urlParams;
    (window.onpopstate = function () {
        var match,
                pl = /\+/g, // Regex for replacing addition symbol with a space
                search = /([^&=]+)=?([^&]*)/g,
                decode = function (s) {
                    return decodeURIComponent(s.replace(pl, " "));
                },
                query = window.location.search.substring(1);

        urlParams = {};
        while (match = search.exec(query))
            urlParams[decode(match[1])] = decode(match[2]);
    })();

    <c:if test="${not consentCheckForm.newUser}">
    $('.webin').toggle();
    $('.user_det_box').show();
    </c:if>
</script>
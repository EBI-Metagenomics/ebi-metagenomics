<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 6/18/15
  Time: 4:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2>Submit data</h2>

<p class="intro">
    If you have data that you wish to have analysed, you need an <strong>ENA Webin account</strong> that
    has been registered with EBI Metagenomics. This allows us to track your submitted data and ensures that we have
    consent to access it for analysis.
</p>

<p class="intro">
   <a href="<c:url value="${baseURL}/wizard.form"/>" title="Register">Click here to start the submission</a>.
    <%--Click <a id="registrationBlockUI" href="" title="Register">here</a> to start the registration procedure.--%>
</p>

<div id="registration_dialog_div" class="sub">
    <div class="anim close_window close_window_button" title="Close this window">&#10006;</div>
    <div class="sub_log" style="width:inherit;">

<div class="register_form">

<form:form method="POST" commandName="registrationForm" id="sub_form">
    <div class="webin">
        <div class="form_row">
          <h3>Do you have an EBI Webin account?</h3>

              <label style="display:inline-block;"><form:radiobutton path="doesAccountExist" id ="radio_button_y" value="true" /> Yes</label>
              <label style="display:inline-block;"><form:radiobutton path="doesAccountExist" id="radio_button_n" value="false" /> No</label>
              <span class="form_info" id="ENA_redirect" style="display:none;margin:18px 0 0 9px;">You will be redirected to ENA website to create an ENA submitter account. if this doesn't happen automatically, please <a href="https://www.ebi.ac.uk/ena/submit/sra/#metagenome_registration">click here</a>.</span>
              <%--<form:errors path="doesAccountExist" cssClass="error"/>--%>
          </div>

        </div>

    <div class="user_det_box" style="display:${displayUsernameBox};">
      <fieldset style="border:0px solid grey;">

      <%--User name --%>
      <div class="form_row">
          <label>User name</label>
          <form:input path="userName" cssErrorClass="error"/>
         <span class="form_info">The username is the submission account id, starting with <i>Webin-*</i></span>
          <form:errors path="userName" cssClass="error"/>
      </div>
          <span class="error" style="display:${displayUsernameBox};">${accountCheckResult}</span>
      <p>
          <input id="submit_button" name="_target1" value="Check" class="main_button" type="submit"/>
          <span class="clear_but">| <a href="/metagenomics/submit" title="cancel">Cancel</a></span>
      </p>
      </fieldset>
    </div>


</form:form>

</div>



<script>
$(document).ready(function(){
    var timeoutFunction;
    $('input:radio[name="doesAccountExist"]').change(function(){
        if($(this).val() == 'true'){
            if (timeoutFunction != undefined) {
                clearTimeout(timeoutFunction);
            }
            $('#ENA_redirect').hide();
            $('.user_det_box').fadeIn( "slow" );
        }
        if($(this).val() == 'false'){
            $('.user_det_box').hide();
            $('#ENA_redirect').show();
            timeoutFunction = setTimeout(function() {
              window.location.href = "https://www.ebi.ac.uk/ena/submit/sra/#metagenome_registration";
            }, 4000);
        }
    });
});

</script>
    </div>


        <%--<p class="sub_sign_note">If you already are a registered user of the European Nucleotide Archive (ENA), you--%>
            <%--should simply use your ENA account to login.</p>--%>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        //Click event for openEntryViewPopup
        $('#registrationBlockUI').click(function () {
            $.blockUI({ message:$('#registration_dialog_div'), baseZ:100000, overlayCSS:{ backgroundColor:'#000', opacity:0.6, cursor:'pointer'}, css:{top:'25%', left:'25%', width:'517px', padding:'0px', border:'1px #AAA solid', 'border-radius':'12px', backgroundColor:'#C9C9C9' }});
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
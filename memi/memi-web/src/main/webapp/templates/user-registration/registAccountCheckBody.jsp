<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 6/18/15
  Time: 3:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>



<div class="register_form">

<form:form method="POST" commandName="registrationForm" id="sub_form">
    <div class="webin">

          <h2>Do you have an EBI Webin account?</h2>

          <div class="form_row">
              <label style="display:inline-block;"><form:radiobutton path="doesAccountExist" id ="radio_button_y" value="true" /> Yes</label>
              <label style="display:inline-block;"><form:radiobutton path="doesAccountExist" id="radio_button_n" value="false" /> No</label>
              <span class="form_info" id="ENA_redirect" style="display:none;margin:18px 0 0 9px;">You will be redirected to ENA website to create an ENA submitter account. If this doesn't happen automatically, please <a href="https://www.ebi.ac.uk/ena/submit/sra/#metagenome_registration">click here</a>.</span>
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
            }, 6000);
        }
    });
});

</script>
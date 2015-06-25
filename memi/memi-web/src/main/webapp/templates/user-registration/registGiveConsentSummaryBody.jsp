<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 6/18/15
  Time: 3:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="register_form">
<h2>Thank you</h2>

<p class="intro">
    Thank you for giving your consent to the EBI Metagenomics, you will receive a duplicate version of this consent by email.</p>
    <span class="form_info" style="margin:18px 0 0 9px;"> You will be redirected to ENA website (ENA Webin tool), where you can finish the submission process. If this doesn't happen automatically, please <a href="https://www.ebi.ac.uk/ena/submit/sra/#home">click here</a></span>

</div>
<script>
$(document).ready(function(){
    var timeoutFunction;
            timeoutFunction = setTimeout(function() {
              window.location.href = "https://www.ebi.ac.uk/ena/submit/sra/#home";
            }, 9000);})


</script>
<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 6/18/15
  Time: 3:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="register_form">
<h2>Consent already sent</h2>

<p class="intro">
   You already gave us your consent to analyse your data. You can now <a href="https://www.ebi.ac.uk/ena/submit/sra/#home">submit your data directly on the ENA website</a>, using the ENA Webin tool.</p>
    <span class="form_info" style="margin:18px 0 0 9px;"> Or wait to be automatically redirected to the ENA website ... </span></div>
<script>
$(document).ready(function(){
    var timeoutFunction;
            timeoutFunction = setTimeout(function() {
              window.location.href = "https://www.ebi.ac.uk/ena/submit/sra/#home";
            }, 9000);})


</script>
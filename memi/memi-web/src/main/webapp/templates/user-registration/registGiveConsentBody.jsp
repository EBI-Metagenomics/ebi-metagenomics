<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 6/18/15
  Time: 3:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="register_form">
<h2>Account already registered with EBI Metagenomics</h2>

<p class="intro">
   Thank you, you already gave us your consent to analyse your data. You can now <a href="https://www.ebi.ac.uk/ena/submit/sra/#home">submit your data directly using the ENA Webin tool</a>.</p>
    <span class="form_info" style="margin:18px 0 0 9px;"><span class="ico_loading ico-spin"></span> Or wait to be automatically redirected to the ENA Webin tool... </span></div>

<script type="text/javascript">
$(document).ready(function(){
    var timeoutFunction;
            timeoutFunction = setTimeout(function() {
              window.location.href = "https://www.ebi.ac.uk/ena/submit/sra/#home";
            }, 10000);})
</script>
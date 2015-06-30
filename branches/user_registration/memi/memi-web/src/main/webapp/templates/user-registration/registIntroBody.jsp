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
    We provide a free service for submission of raw metagenomics sequence data and associated metadata to the European
    Nucleotide Archive (ENA) and analysis by EBI Metagenomics.
</p>

<div class="grid_24 registration-check">
    <div class="box-registration">
    <a href="<c:url value="${baseURL}/wizard.form?user=new"/>" title="New user - start the submission" class="box-registration-link" >
    <div class="box-registration-cont anim box-ban">
        <span class="icon icon-functional" data-icon="7"></span><br/><h3>New user</h3>
    <p class="intro"> If you are new user, Click here to start the submission.
    </p>
    </div>
    </a>

    </div>

    <div class="box-registration">
        <a href="<c:url value="${baseURL}/wizard.form?user=existing"/>" title="Existing user" class="box-registration-link" id="test">
            <div class="box-registration-cont anim box-blue">
            <span class="icon icon-functional" data-icon="/"></span><br/><h3>Existing user</h3>
            <p class="intro">
            Already user? we need your consent to analyse your data.
             </p>
        </div>

        </a>
    </div>
</div>
<p >
    If you have data that you wish to have analysed, you need an <strong>ENA submitter account</strong> that
    has been registered with EBI Metagenomics. This allows us to track your submitted data and ensures that we have
    consent to access it for analysis.
    </p>
<p>With a valid ENA submitter account, you can submit your data directly using the <a class="ext"
                                                                                      title="Click here to submit data to ENA"
                                                                                      href="https://www.ebi.ac.uk/ena/submit/sra/#home">ENA
    Webin tool</a>, which will help you describe your metadata and upload your sequence data.</p>


<p >Once your reads are uploaded to the ENA, the EBI Metagenomics team will access them and perform the
    analysis, which is done in several steps. You will receive an email once the analysis starts and another when the
    analysis of all samples is complete. The analysis time is dependent on the number of samples submitted and requests
    by other submitters at the time. If your samples are private, you will need to log in to the EBI Metagenomics
    homepage to be able to view the results of the analysis.
</p>

<script type="text/javascript">
    $(document).ready(function () {
        //TEMP - TO DELETE IF WE DON'T USE POPUP
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

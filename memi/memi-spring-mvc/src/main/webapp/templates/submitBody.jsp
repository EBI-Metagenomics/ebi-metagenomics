<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="content-full">

       <%--<div id="sidebar">
        <div id="sidebar-warning">
       <span class="error" style="font-weight:bold;padding-top:2px;font-size:80%; float:right;">IMPORTANT</span> <h2>Data type</h2>
        <p>
        Presently, analysis is restricted to "long" (average reads lengths over 200nt), unassembled sequence reads, i.e. Roche 454 sequences,
        from metagenomic or metatranscriptomic samples.</p>
        <span class="separator"></span>
        <p><a href="mailto:datasubs@ebi.ac.uk?subject=EBI Metagenomics - dataset" title="Send an enquiry about Metagenomics data submission"> Contact us</a>, if your datasets do not fit this description, to help us better understand your needs so we can tailor our future developments appropriately.</p>
        </div></div>--%>

     <h2>Submit data</h2>

     <p class="intro">We currently provide a manually-supported submission service. You are asked to send us a summary of your data using an online form. You will then be contacted by our curation team to enable submission of your data to our analysis pipeline and to the underlying archive (SRA). Our curators will translate your data and metadata to the required formats and provide you with accession numbers that you can use for publication purposes, and a direct link to your analysis results (note, depending on the size of your submission, the analysis may take a few days to complete).</p>

    <%-- Removed because the two pages (login and SSend the form look too similar
    <p class="sub_warn"><img src="${pageContext.request.contextPath}/img/ico_warning_8.png" alt="Important: Data type"> Presently, analysis is restricted to "long" (average reads lengths over 200nt), unassembled sequence reads, i.e. Roche 454 sequences, from metagenomic or metatranscriptomic samples.</p>
--%>
     <p class="step_breadcrumbs"><span id="selected"><%--<span class="num">2</span>--%> Send the form </span></p>

     <div class="sub">

     <div class="sub_form">    
     <form:form action="submit" commandName="subForm">
     <fieldset>
     <div class="form_row">
     <label for="title">Project title <small>*</small> :</label>
     <br/>
     <form:input path="subTitle" title="Project title" cssStyle="width:313px;"/>
     <span class="form_help">The title will be used in the subject line of future email communications to help identify the subject matter and be used in the eventual submission as the title of the project under which the samples and analyses will be grouped (can be changed/updated later).</span>
     <form:errors path="subTitle" cssClass="error"/></div>

     <div class="form_row">
     <label for="dateprivacy">Hold private until <small>*</small> :</label>
     <br/>
     <form:input id="datepicker" path="releaseDate" title="Hold project data private until date" cssStyle="width:296px;"/>
     <span class="form_help">This is the date at which the information will be released to the public. All data submitted to EBI metagenomics must be suitable for public release eventually. The maximum length of time data can be held privately is 2 years.</span>
     <form:errors cssClass="error" path="releaseDate"/>
     </div>

     <div class="form_row">
     <label for="description">Describe your submission <small>*</small> :</label>
     <br/>
     <form:textarea path="dataDesc" title="Comments about your project" cssStyle="width:303px; height:100px;float:left;"/>
     <span class="form_help">Provide additional details about your data, such as: <ul><li>number of samples</li><li>sequencing platform</li><li>isolation source</li></ul><br/>This field can also be used to ask questions about the submission process.</span>
     <form:errors cssClass="error" path="dataDesc"/>
     </div>
     <p style="clear:both;/*For IE6*/">
     <input type="submit" name="submit" value="Submit" class="main_button"/>
     <span class="clear_but" style="float:none;">| <a href="/metagenomics" title="cancel">Cancel</a></span>
     </p>
     </fieldset>
    </form:form>
     <small>* Required</small>
     </div>
     </div>
    
    <div class="sub_ask"> If your datasets do not fit this description, to help us better understand your needs, please email us (<a href="mailto:datasubs@ebi.ac.uk?subject=EBI Metagenomics - data submission" title="Send an enquiry about Metagenomics data submission">datasubs@ebi.ac.uk</a>).</div>

</div>

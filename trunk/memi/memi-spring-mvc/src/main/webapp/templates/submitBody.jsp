<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="content-full" class="menu_bgd">
    
        <div id="sidebar">
        <div id="sidebar-warning">
       <span class="error" style="font-weight:bold;padding-top:2px;font-size:80%; float:right;">IMPORTANT</span> <h2>Data type</h2>
        <p>
        Presently, analysis is restricted to "long" (average reads lengths over 200nt), unassembled random shotgun sequence reads, i.e. Roche 454 sequences,
        from metagenomic or metatranscriptomic samples.</p>
        <span class="separator"></span>
        <p><a href="mailto:datasubs@ebi.ac.uk" title="Send an enquiry about Metagenomics data submission"> Contact us</a>, if your datasets do not fit this description, to help us better understand your needs so we can tailor our future developments appropriately.</p>
        </div></div>

<div style="width:682px;">
     <h2>Submit data</h2>
        <p class="step_breadcrumbs"><span class="num">1</span> Login |<span id="selected""><span class="num">2</span> Send the form </span> |<span class="num">3</span> Receive feedback |<span class="num">4</span> Analysis by our team |<span class="num">5</span> View your data |</p>

     <h3>2- Send the form</h3>


    <p>
        Please complete the details below to initiate the submission process. We are currently working on a dedicated submission wizard, but in the meantime we are providing the service by requesting the required details by email, our curators will then translate your data and metadata to the required formats.
    </p>


   
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
     <form:textarea path="dataDesc" title="Comments about your project" cssStyle="width:297px; height:100px;"/>
     <span class="form_help">Please provide additional details about what you wish to submit and/or ask questions about the submission process.</span>
     <form:errors cssClass="error" path="dataDesc"/>
     </div>
     <p>
     <input type="submit" name="submit" value="Submit" class="main_button"/>
     <input type="submit" name="cancel" value="Cancel" class="main_button"/>
     </p>
     </fieldset>
    </form:form>
     <small>* Required</small>
</div>
</div>

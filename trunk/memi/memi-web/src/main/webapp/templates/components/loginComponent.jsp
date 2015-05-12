<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<h2><spring:message code="submitView.title"/></h2>

<figure class="fr"><img src="${pageContext.request.contextPath}/img/graphic_submission_00.gif" alt="Details of the submission and analysis process on EBI metagenomics
website"/>
<figcaption><p>Details of the submission and analysis process on EBI metagenomics website</p></figcaption></figure>

<p class="intro">
    We provide a free service for submission of raw sequence data and associated metadata to the EBI Metagenomics.
    <%--A <a class="ext" title="Click here to watch the full video tutorial" href="http://www.youtube.com/watch?v=Zml8jTqfQPg">video tutorial</a> is available outlining this process.--%>
</p>
<%--<iframe width="210" height="158" src="//www.youtube.com/embed/Zml8jTqfQPg" frameborder="0" allowfullscreen></iframe>--%>
<p class="intro">
    If you have data that you wish to have analysed, you need an <strong>ENA submitter account</strong>. This allows your raw data and metadata to be archived in ENA, so that we can analyse it and make the results available on the EBI Metagenomics website. <br/>With an ENA submitter account, you can submit your data directly using the <a class="ext" title="Click here to submit data to ENA" href="https://www.ebi.ac.uk/ena/submit/sra/#home">ENA Webin tool</a>, which will help you describe your metadata and upload your sequence read data (if you have ENA submitter account created before November 2014, please <a
                href="mailto:metagenomics-help@ebi.ac.uk?subject=EBI Metagenomics - data privacy statement">send us an email</a> stating that you let EBI Metagenomics access your private data for analysis).</p>

<p class="intro">If you DO NOT have an existing ENA submitter account, <strong><a title="Click here to register for a new ENA submitter account" href="https://www.ebi.ac.uk/ena/submit/sra/#metagenome_registration"> click here to create one</a></strong>, and follow the instructions provided.</p>




<p class="intro">Once your reads are uploaded to the ENA, the EBI Metagenomics team will access them and perform the analysis, which is done in several steps. You will receive an email once the analysis starts and another when the analysis of all samples is complete. The analysis time is dependent on the number of samples submitted and requests by other submitters at the time.
If your samples are private, you will need to log in to the EBI Metagenomics homepage to be able to view the results of the analysis.
</p>

    <%--<a title="Click here to register for an SRA account" href="<c:url value="${baseURL}/submit/register"/>">Register for SRA Webin</a>--%>
    <%--<spring:message code="submitView.introduction.part2"/><br>--%>
    <%--<spring:message code="submitView.introduction.part3"/>--%>


<%--<p class="sub_warn"><img src="${pageContext.request.contextPath}/img/ico_warning_8.png"><spring:message--%>
<%--code="submitView.warning.restriction"/></p>--%>
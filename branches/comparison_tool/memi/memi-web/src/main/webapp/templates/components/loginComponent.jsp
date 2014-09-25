<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<h2><spring:message code="submitView.title"/></h2>

<p class="intro">

    We provide a service for submission of raw sequence data and associated meta-data to the Sequence Read Archive (SRA) and our analysis pipelines. A <a class="ext" title="Click here to watch the full video tutorial" href="http://www.youtube.com/watch?v=Zml8jTqfQPg">video tutorial</a> is available outlining this process.
    <br><br>
    If you have data that you wish to have archived and analysed, you need an ENA submitter account to archive the data in the Sequence Read Archive and view the results of the analysis on EBI metagenomics portal.
    <br><br>
    You can <a title="Click here to register for an EBI metagenomics account" href="<c:url value="${baseURL}/register"/>">register for a new ENA submitter account here</a>. Note that if you already have an ENA submitter account, you can use that to log in to EBI metagenomics portal. Users who registered before the 7th of February 2014 can access their account using their current EMBL-Bank account credentials. With an ENA submitter account, you can submit your data directly using the <a class="ext" title="Click here to submit data to SRA via the SRA Webin Tool" href="https://www.ebi.ac.uk/ena/submit/sra/#home">SRA Webin tool</a>, which will help you describe your metadata and upload your sequence read data.
    Alternatively, you may submit your data using ISAcreator, which is part of the <a class="ext" title="Click here to find out more about the ISA tools suite" href="http://isa-tools.org">ISA tools suite</a>.
    This standalone tool can be used offline, and has been designed to handle the collection, formatting and submission of multi-omics study data. OntoMaton is a widget for Google Spreadsheets and supports collaborative annotation of experimental data.

    <%--<a title="Click here to register for an SRA account" href="<c:url value="${baseURL}/submit/register"/>">Register for SRA Webin</a>--%>
    <%--<spring:message code="submitView.introduction.part2"/><br>--%>
    <%--<spring:message code="submitView.introduction.part3"/>--%>
</p>

<%--<p class="sub_warn"><img src="${pageContext.request.contextPath}/img/ico_warning_8.png"><spring:message--%>
<%--code="submitView.warning.christmas"/></p>--%>

<%--<p class="sub_warn"><img src="${pageContext.request.contextPath}/img/ico_warning_8.png"><spring:message--%>
<%--code="submitView.warning.restriction"/></p>--%>
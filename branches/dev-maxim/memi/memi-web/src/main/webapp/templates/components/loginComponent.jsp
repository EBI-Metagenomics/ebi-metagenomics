<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<h2><spring:message code="submitView.title"/></h2>

<p class="intro">
We provide a service for submission of raw sequence data and associated meta-data to the European Nucleotide Archive (ENA) and our analysis pipelines. A <a class="ext" title="Click here to watch the full video tutorial" href="http://www.youtube.com/watch?v=Zml8jTqfQPg">video tutorial</a> is available outlining this process.
</p>
<%--<iframe width="210" height="158" src="//www.youtube.com/embed/Zml8jTqfQPg" frameborder="0" allowfullscreen></iframe>--%>
<p class="intro">
    If you have data that you wish to have analysed, you need an ENA submitter account. This will allows your raw data to be archived in ENA so that we can analyse it and make the results available on EBI metagenomics portal.
    If you don't already have one, you can <a title="Click here to register for an EBI metagenomics account" href="<c:url value="${baseURL}/register"/>">register for a new ENA submitter account here</a>. Users who registered before the 7th of February 2014 can access their account using their current EMBL-Bank account credentials.
   </p>
<p class="intro">With an ENA submitter account, you can submit your data directly using the <a class="ext" title="Click here to submit data to ENA" href="https://www.ebi.ac.uk/ena/submit/sra/#home">ENA Webin tool</a>, which will help you describe your metadata and upload your sequence read data.
    Alternatively, you may submit your data using ISAcreator and OntoMaton, which are part of the <a class="ext" title="Click here to find out more about the ISA tools suite" href="http://isa-tools.org">ISA tools suite</a>. These standalone tools have been designed to handle the collection, formatting and submission of multi-omics study data.
</p>

    <%--<a title="Click here to register for an SRA account" href="<c:url value="${baseURL}/submit/register"/>">Register for SRA Webin</a>--%>
    <%--<spring:message code="submitView.introduction.part2"/><br>--%>
    <%--<spring:message code="submitView.introduction.part3"/>--%>


<%--<p class="sub_warn"><img src="${pageContext.request.contextPath}/img/ico_warning_8.png"><spring:message--%>
<%--code="submitView.warning.christmas"/></p>--%>

<%--<p class="sub_warn"><img src="${pageContext.request.contextPath}/img/ico_warning_8.png"><spring:message--%>
<%--code="submitView.warning.restriction"/></p>--%>
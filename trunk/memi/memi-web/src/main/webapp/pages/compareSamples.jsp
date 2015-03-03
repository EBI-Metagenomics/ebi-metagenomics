<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- Creates HTML select option for each sample of selected project that have data (sample selection - comparison tool submission page) --%>
<c:forEach var="analysisJob" items="${analysisJobs}">
        <option value="${analysisJob.jobId}" title="Run ${analysisJob.externalRunIDs} | ${analysisJob.sample.sampleName} | ${analysisJob.sample.sampleAlias}">${analysisJob.sample.sampleName} - ${analysisJob.externalRunIDs}</option>
    </c:forEach>

<%-- Creates HTML select option for each sample of selected project that have no data (sample selection - comparison tool submission page) --%>
<c:forEach var="analysisJob" items="${deactivatedAnalysisJobs}">
    <option value="${analysisJob.jobId}" title="Run ${analysisJob.externalRunIDs} | ${analysisJob.sample.sampleName} | ${analysisJob.sample.sampleAlias}" disabled>${analysisJob.sample.sampleName} - ${analysisJob.externalRunIDs} - NO DATA AVAILABLE</option>
</c:forEach>

<%-- Sorts HTML select options alphabetically (sample selection - comparison tool submission page) --%>
<script type="text/javascript">
    SortSamplesByText(); // Function defined in compareBody.jsp.
</script>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- Creates HTML select option for each sample of selected project that have data (sample selection - comparison tool submission page) --%>
<c:forEach var="analysisJob" items="${analysisJobs}">
        <option value="${analysisJob.jobId}" title="Run ${analysisJob.externalRunIDs} | ${analysisJob.sampleName} | ${analysisJob.sampleAlias} (v${analysisJob.releaseVersion})">${analysisJob.sampleName} - ${analysisJob.externalRunIDs} (v${analysisJob.releaseVersion})</option>
    </c:forEach>

<%-- Creates HTML select option for each sample of selected project that have no data (sample selection - comparison tool submission page) --%>
<c:forEach var="analysisJob" items="${deactivatedAnalysisJobs}">
    <option value="${analysisJob.jobId}" title="Run ${analysisJob.externalRunIDs} | ${analysisJob.sampleName} | ${analysisJob.sampleAlias}" disabled>${analysisJob.sampleName} - ${analysisJob.externalRunIDs} - NO DATA AVAILABLE</option>
</c:forEach>

<%-- Creates HTML select option for each sample of selected project that have no data (sample selection - comparison tool submission page) --%>
<c:forEach var="analysisJob" items="${ampliconAnalysisJobs}">
    <option value="${analysisJob.jobId}" title="Run ${analysisJob.externalRunIDs} | ${analysisJob.sampleName} | ${analysisJob.sampleAlias}" disabled>${analysisJob.sampleName} - ${analysisJob.externalRunIDs} - AMPLICON DATA</option>
</c:forEach>

<%-- Sorts HTML select options alphabetically (sample selection - comparison tool submission page) --%>
<script type="text/javascript">
    SortSamplesByText(); // Function defined in compareBody.jsp.
</script>

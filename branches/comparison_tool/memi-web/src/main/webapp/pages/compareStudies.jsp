<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="description-content">
<p><a href="<c:url value="${baseURL}/project/${study.studyId}"/>"
       class="more_view" target="_blank">View current project page</a> - <a
            href="<c:url value="${baseURL}/project/${study.studyId}"/>#samples_id"
            class="list_sample" target="_blank"><c:out value="${study.sampleSize} View current project samples"/></a>
</p>
</div>

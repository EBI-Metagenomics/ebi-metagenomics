<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2><spring:message code="submitView.title"/></h2>

<p class="intro"><spring:message code="submitView.introduction.part1"/>
    <a title="<spring:message code="submissionForm.label.template.download.link.title"/>"
       href="<c:url value="${baseURL}/submit/doOpenTemplateDownload"/>">
        <spring:message code="submissionForm.label.template.download.link"/>
    </a>
    <spring:message code="submitView.introduction.part2"/><br>
    <spring:message code="submitView.introduction.part3"/>
</p>

<%--<p class="sub_warn"><img src="${pageContext.request.contextPath}/img/ico_warning_8.png"><spring:message--%>
<%--code="submitView.warning.christmas"/></p>--%>

<p class="sub_warn"><img src="${pageContext.request.contextPath}/img/ico_warning_8.png"><spring:message
        code="submitView.warning.restriction"/></p>
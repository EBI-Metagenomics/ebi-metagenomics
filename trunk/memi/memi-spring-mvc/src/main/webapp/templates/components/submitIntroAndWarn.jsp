<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2><spring:message code="submitView.title"/></h2>

<p class="intro">

    <spring:message code="submitView.introduction.part4"/>
    If you are a new user, please <a
        href="https://www.ebi.ac.uk/embl/genomes/submission/external-reg-link.jsf?url=http%3a%2f%2fapple.windows.ebi.ac.uk%3a8082%2fmetagenomics%2flogin%3fdisplay%3dtrue"
        title=""/>register with us</a> first.


    <%--<a title="<spring:message code="submissionForm.label.template.download.link.title"/>"--%>
    <%--href="<c:url value="${baseURL}/submit/doOpenTemplateDownload"/>">--%>
    <%--<spring:message code="submissionForm.label.template.download.link"/>--%>
    <%--</a>--%>
    <%--<spring:message code="submitView.introduction.part2"/><br>--%>
    <%--<spring:message code="submitView.introduction.part3"/>--%>
</p>

<%--<p class="sub_warn"><img src="${pageContext.request.contextPath}/img/ico_warning_8.png"><spring:message--%>
<%--code="submitView.warning.christmas"/></p>--%>

<%--<p class="sub_warn"><img src="${pageContext.request.contextPath}/img/ico_warning_8.png"><spring:message--%>
<%--code="submitView.warning.restriction"/></p>--%>
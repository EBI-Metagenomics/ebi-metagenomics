<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2><spring:message code="sraRegistrationForm.title"/></h2>

<p class="intro">

    <spring:message code="sraRegistrationForm.introduction.part1"/>

    <a title="SRA Webin submission system"
       href="https://www.ebi.ac.uk/ena/submit/sra/#home"> SRA Webin submission system</a>
    <spring:message code="sraRegistrationForm.introduction.part2"/><br><br>
    <%--<spring:message code="submitView.introduction.part3"/>--%>
    We need the following information to create an SRA account for you. The registration form has been
    automatically filled with the information we found in your EMBL-Bank account.
</p>

<%--<p class="sub_warn"><img src="${pageContext.request.contextPath}/img/ico_warning_8.png"><spring:message--%>
<%--code="submitView.warning.christmas"/></p>--%>

<%--<p class="sub_warn"><img src="${pageContext.request.contextPath}/img/ico_warning_8.png"><spring:message--%>
<%--code="submitView.warning.restriction"/></p>--%>
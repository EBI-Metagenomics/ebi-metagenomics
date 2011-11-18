<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="scriptlets/_getActivePage.jsp" %>
<%-- This template is used for the login page --%>
<div id="content-full">

    <h2>Submit data</h2>

    <p class="intro">We currently provide a manually-supported submission service. After registering, you will be asked
        to send us a summary of your data using an online form. You will then be contacted by our curation team to
        enable submission of your data to our analysis pipeline and to the underlying archive (SRA). Our curators will
        translate your data and metadata to the required formats and provide you with accession numbers that you can use
        for publication purposes, and a direct link to your analysis results (note, depending on the size of your
        submission, the analysis may take a few days to complete).</p>

    <p class="sub_warn"><img src="${pageContext.request.contextPath}/img/ico_warning_8.png" alt="Important: Data type">
        Presently, analysis is restricted to "long" (average reads lengths over 200nt), unassembled sequence reads, i.e.
        Roche 454 sequences, from metagenomic or metatranscriptomic samples.</p>

    <p class="step_breadcrumbs"><span
            id="selected"><%--<span class="num">1</span>--%> Login, start submission process</span></p>

    <div class="sub">
        <%--<p>Please login to submit or view your projects.</p>--%>

        <div class="sub_log">
            <form:form method="POST" action="login" commandName="loginForm" id="submit_form">

                <div class="form_row"><h3>Login</h3></div>

                <div class="form_row">
                    <label for="loginPage_emailAddress"><spring:message
                            code="email.label"/></label>
                    <form:input id="loginPage_emailAddress" path="emailAddress" cssErrorClass="error"
                                cssStyle="width:313px;"/>
                    <form:errors path="emailAddress" cssClass="error"/>
                </div>

                <div class="form_row">
                    <label for="loginPage_password"><spring:message code="loginForm.inputField.password.label"/></label>
                    <form:password id="loginPage_password" path="password" cssErrorClass="error"
                                   cssStyle="width:313px;"/>
                    <form:errors path="password" cssClass="error"/>
                </div>
                <c:set var="enaUrlParam" value="<%=activePage%>"/>
                <c:url var="enaPasswordUrl"
                       value="${model.propertyContainer.enaSubmissionURL.forgottenPwdLink}">
                    <c:param name="url" value="${enaUrlParam}"/>
                </c:url>
                <div>
                    <a href="<c:out value="${enaPasswordUrl}"/>" title="Request a new password">
                        <spring:message code="loginForm.link.external.forgotten.password"/>
                    </a>
                </div>
                <div class="form_row_log">
                    <input type="submit" name="login" value="Login" class="main_button"/>&nbsp;
                    <span class="clear_but">| <a href="/metagenomics" title="cancel">Cancel</a></span>
                </div>

            </form:form>
        </div>

        <div class="sub_sign">
            <div class="form_row"><h3>Not registered yet?</h3>
                <c:url var="enaRegistrationUrl"
                       value="${model.propertyContainer.enaSubmissionURL.registrationLink}">
                    <c:param name="url" value="${enaUrlParameter}"/>
                </c:url>
                <span class="sub_sign_text"><a href="<c:url value="${enaRegistrationUrl}"/>" title="Registration">Sign-up</a> to register</span>
            </div>
            <div class="form_row" style="font-size:140%;">or</div>
            <p><a href="https://www.ebi.ac.uk/ena/home"><img
                    src="${pageContext.request.contextPath}/img/ico_ena_user.jpg" alt="ENA member"></a></p>

            <p class="sub_sign_note">If you already are a registered user of the European Nucleotide Archive (ENA), you
                should simply use your ENA account to login.</p>
        </div>
    </div>

    <div class="sub_ask">If you have any questions about submitting your data to EBI metagenomics, please email us (<a
            href="mailto:datasubs@ebi.ac.uk?subject=EBI Metagenomics - data submission"
            title="Send an enquiry about Metagenomics data submission">datasubs@ebi.ac.uk</a>).
    </div>

</div>

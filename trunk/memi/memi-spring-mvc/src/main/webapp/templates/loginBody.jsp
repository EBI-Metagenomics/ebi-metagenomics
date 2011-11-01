<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<% final String url = request.getRequestURL().toString();
    final String context = request.getContextPath();
    final String requestRoot = url.substring(0, url.indexOf(context) + context.length()) + "/login";%>
<c:set var="enaUrlParameter" value="<%=requestRoot%>"/>
<%-- This template is used for the login page --%>
<div id="content-full">

        <h2>Submit data</h2>

        <p class="intro">We currently provide a manually-supported submission service. After registering, you will be asked to send us a summary of your data using an online form. You will then be contacted by our curation team to enable submission of your data to our analysis pipeline and to the underlying archive (SRA). Our curators will translate your data and metadata to the required formats and provide you with accession numbers that you can use for publication purposes, and a direct link to your analysis results (note, depending on the size of your submission, the analysis may take a few days to complete).</p>

       <p class="sub_warn"><img src="${pageContext.request.contextPath}/img/ico_warning_8.png" alt="Important: Data type"> Presently, analysis is restricted to "long" (average reads lengths over 200nt), unassembled sequence reads, i.e. Roche 454 sequences, from metagenomic or metatranscriptomic samples.</p>
        
        <p class="step_breadcrumbs"><span id="selected"><%--<span class="num">1</span>--%> Login, start submission process</span></p>

       <div class="sub">
        <%--<p>Please login to submit or view your projects.</p>--%>

        <div class="sub_log">
        <form:form method="POST" action="login" commandName="loginForm">
        <fieldset>
        <div class="form_row"><h3>Login</h3></div>
        <div class="form_row">
        <label for="email">E-Mail:</label>
        <br/>
        <form:input path="emailAddress" cssStyle="width:313px;"/>
        <form:errors cssClass="error" path="emailAddress"/>
        </div>
        <div class="form_row">
        <label for="password">Password:</label>
        <br/>
        <form:password path="password" cssStyle="width:313px;"/>
        <form:errors cssClass="error" path="password"/>
        </div>
        <c:url var="enaPasswordUrl"
               value="${model.propertyContainer.enaSubmissionURL.forgottenPwdLink}">
            <c:param name="url" value="${enaUrlParameter}"/>
        </c:url>
        <div ><a href="<c:out value="${enaPasswordUrl}"/>"  title="Request a new password">Forgot your password?</a></div>
        <div class="form_row_log">
        <input type="submit" name="login" value="Login" class="main_button"/>&nbsp;
        <span class="clear_but" style="float:none;">| <a href="/metagenomics" title="cancel">Cancel</a></span>
        </div>
        </fieldset>
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
        <p><a href="http://www.ebi.ac.uk/ena/home"><img src="${pageContext.request.contextPath}/img/ico_ena_user.jpg" alt="ENA member"></a></p>
        <p class="sub_sign_note">If you already are a registered user of the European Nucleotide Archive (ENA), you should simply use your ENA account to log-in.</p>
        </div>
        </div>

        <div class="sub_ask">If you have any questions about submitting your data to EBI metagenomics, please email us (<a href="mailto:datasubs@ebi.ac.uk?subject=EBI Metagenomics - data submission" title="Send an enquiry about Metagenomics data submission">datasubs@ebi.ac.uk</a>).</div>

</div>

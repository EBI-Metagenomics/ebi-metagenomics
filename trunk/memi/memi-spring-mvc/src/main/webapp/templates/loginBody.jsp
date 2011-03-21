<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- This template is used for the login page --%>
<div id="content-full">
    <form:form method="POST" action="login" commandName="loginForm">
        <h2>Start Submission Process</h2>
        <p> Login first to submit and view your data.</p>
        <h3>Login</h3>

        E-Mail:<form:input path="emailAddress"/><br/><form:errors cssClass="error" path="emailAddress"/>
        Password:<form:password path="password"/> <br/> <form:errors cssClass="error" path="password"/>
        <input type="submit" name="login" value="Login" class="main_button"/>
        <input type="submit" name="cancel" value="Cancel" class="main_button"/>
    </form:form>
    <%--* A valid login is: E-Mail: TEST; Password:test--%>
    <p>
        <a href="<c:url value=" https://www.ebi.ac.uk/embl/genomes/submission/registration.jsf"/>" title="Registration">Register</a>
        <br/>
        <a href="<c:url value=" https://www.ebi.ac.uk/embl/genomes/submission/forgot-passw.jsf?_afPfm=5"/>"
           title="Request a new password">Forgotten password</a>
    </p>
     <h3>Data Submission</h3>
    <p>If you have any questions about submitting your data to the EBI metagenomics resource for archiving and analysis, please email us at <a href="mailto:datasubs@ebi.ac.uk" title="Send an enquiry about Metagenomics data submission">datasubs@ebi.ac.uk</a>.</p>
</div>

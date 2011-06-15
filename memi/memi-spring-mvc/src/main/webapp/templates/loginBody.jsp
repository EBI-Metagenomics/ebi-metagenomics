<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- This template is used for the login page --%>
<div id="content-full" class="menu_bgd">

    <div id="sidebar">
    <div id="sidebar-mailing">
    <img src="${pageContext.request.contextPath}/img/icon_info.gif" alt="Information">
    <h2>Data Submission</h2>
    <p>If you have any questions about submitting your data to EBI metagenomics, please email us (<a href="mailto:datasubs@ebi.ac.uk?subject=EBI Metagenomics - data submission" title="Send an enquiry about Metagenomics data submission">datasubs@ebi.ac.uk</a>).</p>
    </div>

    <div id="sidebar-ena">
    <img src="${pageContext.request.contextPath}/img/icon_ena.gif" alt="Information">
    <h2>ENA user?</h2>
    <p>If you already are a registered user of the European Nucleotide Archive (ENA) or SPIN, you don't need to get another Username and Password. You can simply use your ENA account to log-in.<br/> If you are not a member yet , you will need to <a href="<c:url value=" https://www.ebi.ac.uk/embl/genomes/submission/registration.jsf"/>" title="Registration">register</a>.</p>
    </div>
    </div>

    <div style="width:682px;">

        <h2>Submit data</h2>
        <p class="step_breadcrumbs"><span id="selected"><span class="num">1</span> Login </span>|<span class="num">2</span> Send the form |<span class="num">3</span> Provide your data |<span class="num">4</span> Await analysis results |<span class="num">5</span> Browse your data |</p>

        <h3>1- Login, start submission process</h3>

        <p>Please login to submit or view your projects.</p>


        <form:form method="POST" action="login" commandName="loginForm">
        <fieldset>
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
        <div class="form_row">
        <input type="submit" name="login" value="Login" class="main_button"/>&nbsp;
        <span class="clear_but" style="float:none;">| <a href="/metagenomics" title="cancel">Cancel</a></span>
        </div>
        </fieldset>
        </form:form>
   <%--  <div class="loginform_tab"></div>
     <form:form method="POST" action="login" commandName="loginForm">
        <table class="result" id="contact">
        <tr><td>E-Mail: <form:input path="emailAddress"  cssStyle="margin-left:20px; width:284px;"/></td></tr>
        <tr><td ><form:errors cssClass="error" path="emailAddress"  /></td></tr>
        <tr><td>Password: <form:password path="password" cssStyle="width:284px;"/></td></tr>
        </tr>
        <tr><td><form:errors cssClass="error" path="password"/></td></tr>
        <tr>
        <td>
        <input type="submit" name="login" value="Login" class="main_button"/>
        <input type="submit" name="cancel" value="Cancel" class="main_button"/>
        </td>
        </tr>
        </table>
    </form:form>--%>

    <p><a href="<c:url value=" https://www.ebi.ac.uk/embl/genomes/submission/forgot-passw.jsf?_afPfm=5"/>"  title="Request a new password">Forgot your password?</a><br/>
        <a href="<c:url value=" https://www.ebi.ac.uk/embl/genomes/submission/registration.jsf"/>" title="Registration">Register</a>
    </p>
    </div>
</div>

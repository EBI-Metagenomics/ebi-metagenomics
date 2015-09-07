<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 6/18/15
  Time: 3:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Registration and consent note</h2>

<div class="register_form">
    <c:url var="consent" value="${baseURL}/registration"/>
    <form:form method="POST" commandName="consentCheckForm" action="${consent}" id="sub_form">
        <%--${accountCheckResult}--%>
        <p class="intro"><span>Your Webin account is currently not registered with EBI metagenomics. You have the choice to register with us now.<br/></span>
        </p>

        <div class="form_row">
                <%--<p class="intro">Could you please read carefully the following text?</p>--%>
            <p><form:checkbox path="consentCheck" value="on" id="gwt-debug-consentField-input"/>Note, if you plan to
                submit pre-publication data for analysis, which is to be held as confidential, we need consent to access
                your data in accordance with ENA’s policies. By keeping this box checked, you consent to the EBI
                Metagenomics team analysing your private data. Note that the data, as well as the analysis results, will
                remain confidential until the release date you specify when submitting your sequencing study. You also
                confirm that the data submitted through this account is NOT sensitive, restricted-access or
                human-identifiable.
                <form:errors path="consentCheck" cssClass="error"/>
            </p>

            <p>
                <input id="submit_button" value="Register" class="main_button" type="submit" style="float: none;"/>
                <span class="clear_but">| <a href="/metagenomics/submission" title="cancel">Cancel</a></span>
            </p>
        </div>
    </form:form>
</div>
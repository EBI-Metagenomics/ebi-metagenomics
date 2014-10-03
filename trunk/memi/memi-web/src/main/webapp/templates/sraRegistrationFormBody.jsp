<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<h2><spring:message code="sraRegistrationForm.title"/></h2>

<p class="intro">
    You need to register for an ENA account in order to be able to upload your sequence to the archives. Do this using the form below; we have pre-filled in some of the fields using the information you have already supplied to us. You will be contacted by the email address you supply once the account is activated.</p>
<%--<%@ include file="components/sraRegistrationFormComponent.jsp" %>--%>

    <div class="sub">
        <div class="sub_form">
            <div style="padding-bottom: 18px;"><span class="required"></span>&nbsp; Please note that all fields in this form are required.</div>
            <form:form id="sub_form" commandName="sraRegistrationForm" method="post" action="submit">
                <fieldset>
                    <legend>User details</legend>
                        <%--Email input field--%>
                    <div class="form_row">
                        <label for="email"><spring:message
                                code="registrationForm.inputField.email.label"/></label><br/>
                        <form:input id="email" path="email" cssErrorClass="error" readonly="false"
                                    title="Please enter a valid email address"/>
                                <span class="form_help"><spring:message
                                        code="registrationForm.inputField.email.help"/></span>
                        <form:errors path="email" cssClass="error"/></div>
                        <%--First name input field--%>
                    <div class="form_row">
                        <label for="firstName"><spring:message
                                code="registrationForm.inputField.firstName.label"/></label><br/>
                        <form:input id="firstName" path="firstName" cssErrorClass="error"
                                    title="Please enter your first name"/>
                                <%--<span class="form_help"><spring:message--%>
                                        <%--code="registrationForm.inputField.firstName.help"/></span>--%>
                        <form:errors path="firstName" cssClass="error"/>
                    </div>
                        <%--Last name input field--%>
                    <div class="form_row">
                        <label for="lastNameId" ><spring:message
                                code="registrationForm.inputField.lastName.label"/></label>
                        <br/>
                        <form:input id="lastNameId" path="lastName" cssErrorClass="error"
                                    title="Please enter your last name"/>
                                <%--<span class="form_help"><spring:message--%>
                                        <%--code="registrationForm.inputField.lastName.help"/></span>--%>
                        <form:errors path="lastName" cssClass="error"/>
                    </div>
                        <%--Department input field--%>
                    <div class="form_row">
                        <label for="department" ><spring:message
                                code="registrationForm.inputField.department.label"/></label><br/>
                        <form:input id="department" path="department" cssErrorClass="error"
                                    title="Please enter your department within your Institute"/>
                                <%--<span class="form_help"><spring:message--%>
                                        <%--code="registrationForm.inputField.department.help"/></span>--%>
                        <form:errors path="department" cssClass="error"/></div>
                        <%--Institute input field--%>
                    <div class="form_row">
                        <label for="institute" ><spring:message
                                code="registrationForm.inputField.institute.label"/></label><br/>
                        <form:input id="institute" path="institute" cssErrorClass="error"
                                    title="Please enter your Institute"/>
                                <%--<span class="form_help"><spring:message--%>
                                        <%--code="registrationForm.inputField.institute.help"/></span>--%>
                        <form:errors path="institute" cssClass="error"/></div>
                        <%--Postal address input field--%>
                    <div class="form_row">
                        <label for="postalAddress" ><spring:message
                                code="registrationForm.inputField.postalAddress.label"/></label><br/>
                        <form:input id="postalAddress" path="postalAddress" cssErrorClass="error"
                                    title="Please enter your Institute's postal address"/>
                                <span class="form_help"><spring:message
                                        code="registrationForm.inputField.postalAddress.help"/></span>
                        <form:errors path="postalAddress" cssClass="error"/></div>
                        <%--Postal code input field--%>
                    <div class="form_row">
                        <label for="postalCode" ><spring:message
                                code="registrationForm.inputField.postalCode.label"/></label><br/>
                        <form:input id="postalCode" path="postalCode" cssErrorClass="error"
                                    title="Please enter your Institute's postal code"/>
                                <%--<span class="form_help"><spring:message--%>
                                        <%--code="registrationForm.inputField.postalCode.help"/></span>--%>
                        <form:errors path="postalCode" cssClass="error"/></div>
                        <%--Country selection field--%>
                    <div class="form_row">
                        <label for="country" ><spring:message
                                code="registrationForm.inputField.country.label"/></label><br/>
                        <form:select id="country" path="country" cssErrorClass="error"
                                     title="Please select your Institute's country">
                            <form:option value="" label="--- Select country ---"/>
                            <form:options items="${model.countries}"/>
                        </form:select>
                                <%--<span class="form_help"><spring:message--%>
                                        <%--code="registrationForm.inputField.country.help"/></span>--%>
                        <form:errors path="country" cssClass="error"/></div>


                </fieldset>
                <br/>
                <fieldset>
                    <legend>Project data</legend>
                        <%--Project title input field--%>
                    <div class="form_row">
                        <label for="title" ><spring:message
                                code="submissionForm.inputField.title.label"/></label><br/>
                        <form:input id="title" path="subTitle" cssErrorClass="error" title="Project title"/>
                                <span class="form_help"><spring:message
                                        code="submissionForm.inputField.title.help"/></span>
                        <form:errors path="subTitle" cssClass="error"/></div>
                        <%--Date picker input field--%>
                    <div class="form_row">
                        <label for="datepicker" ><spring:message
                                code="submissionForm.inputField.date.label"/></label>
                        <br/>
                        <form:input id="datepicker" path="releaseDate" cssErrorClass="error"
                                    title="Hold project data private until date"/>
                                <span class="form_help"><spring:message
                                        code="submissionForm.inputField.date.help"/></span>
                        <form:errors cssClass="error" path="releaseDate"/>
                    </div>
                        <%--Description text area--%>
                    <div class="form_row">
                        <label for="description" ><spring:message
                                code="submissionForm.inputField.desc.label"/></label><br/>
                        <form:textarea id="description" path="dataDesc" cssErrorClass="error"
                                       cssStyle="float:left;"
                                       title="Comments about your project"/>
                        <span class="form_help"><spring:message code="submissionForm.inputField.desc.help.provide"/>
                            <%--<ul>--%>
                            <%--<li><spring:message code="submissionForm.inputField.desc.help.li.1"/></li>--%>
                            <%--<li><spring:message code="submissionForm.inputField.desc.help.li.2"/></li>--%>
                            <%--<li><spring:message code="submissionForm.inputField.desc.help.li.3"/></li>--%>
                        <%--</ul>--%>
                            <br/><spring:message code="submissionForm.inputField.desc.help.this"/></span>
                        <form:errors cssClass="error" path="dataDesc"/>
                    </div>
                        <%--Sensitive data option--%>
                    <div class="form_row">
                      <form:checkbox id="notSensitiveData" path="notSensitiveData"
                                       cssErrorClass="error"
                                       title="Please tick this option to confirm that you don't want to submit sensitive data"/>
                        <span>I confirm that the data submitted through this account is NOT sensitive, restricted-access or human-identifiable.</span>
                        <form:errors cssClass="error" path="notSensitiveData"/>
                    </div>
                    <form:input type="hidden" path="leaveIt"/>
                    <p style="clear:both;/*For IE6*/">
                        <input name="submit" value="Submit" class="main_button" type="submit"/>
                        <span class="clear_but">| <a href="/metagenomics" title="cancel">Cancel</a></span>

                    </p>
                </fieldset>
            </form:form>
        </div>
    </div>

    <div class="sub_ask"> If your datasets do not fit this description, to help us better understand your needs,
        please email us (<a href="mailto:datasubs@ebi.ac.uk?subject=EBI Metagenomics - data submission"
                            title="Send an enquiry about Metagenomics data submission">datasubs@ebi.ac.uk</a>).
    </div>

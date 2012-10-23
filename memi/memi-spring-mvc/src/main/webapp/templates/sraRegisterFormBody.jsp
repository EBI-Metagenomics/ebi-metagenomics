<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>--%>

<div id="content-full">

    <%@ include file="components/sraRegisterFormInfo.jsp" %>

    <c:choose>
        <c:when test="${hasSraAccount}">
            <p>
                    <%--<span style="color: #ff0000;font-weight: bolder;">Please put in new text here!</span><br>--%>
                We noticed that you have an active SRA submission account, which you could use to submit your meta data
                via <a
                    title="Link to SRA Webin - Beta" href="https://www.ebi.ac.uk/ena/submit/sra/#home">SRA Webin -
                Beta</a>. For
                more information on how to use the new <a
                    title="Link to SRA Webin - Beta" href="https://www.ebi.ac.uk/ena/submit/sra/#home">SRA Webin </a> to
                submit MIxS compliant meta data to SRA watch our video on YouTube.
            </p>

            <p>Further information on the Minimum Information about any (x) Sequence (MIxS) can be found on the <a
                    title="Link to Genomic Standards Consortium's website"
                    href="http://gensc.org/gc_wiki/index.php/MIxS">Genomic Standards Consortium</a> website.

            <div style="overflow: auto; display: block;">
                    <%--TODO: Check it if iframe is supported by most Browsers and older version--%>
                    <%--TODO: Put a YouTube link to older Browser versions--%>
                <iframe width="420" height="315" src="http://www.youtube.com/embed/Zml8jTqfQPg" frameborder="0"
                        allowfullscreen></iframe>
            </div>
            </p>
        </c:when>
        <c:otherwise>

            <div class="sub">

                <div class="sub_form">
                    <form:form id="sub_form" commandName="sraRegistrationForm" method="post" action="submit"
                               enctype="multipart/form-data">
                        <fieldset>
                            <legend>User details</legend>
                                <%--Email input field--%>
                            <div class="form_row">
                                <label for="email"><spring:message
                                        code="registrationForm.inputField.email.label"/></label><br/>
                                <form:input id="email" path="email" cssErrorClass="error" readonly="true"
                                            title="Please enter a valid email address"/>
                                <span class="form_help"><spring:message
                                        code="registrationForm.inputField.email.help"/></span>
                                <form:errors path="email" cssClass="error"/></div>
                                <%--First name input field--%>
                            <div class="form_row">
                                <label for="firstName" id="required"><spring:message
                                        code="registrationForm.inputField.firstName.label"/></label><br/>
                                <form:input id="firstName" path="firstName" cssErrorClass="error"
                                            title="Please enter your first name"/>
                                <span class="form_help"><spring:message
                                        code="registrationForm.inputField.firstName.help"/></span>
                                <form:errors path="firstName" cssClass="error"/>
                            </div>
                                <%--Last name input field--%>
                            <div class="form_row">
                                <label for="lastNameId" id="required"><spring:message
                                        code="registrationForm.inputField.lastName.label"/></label>
                                <br/>
                                <form:input id="lastNameId" path="lastName" cssErrorClass="error"
                                            title="Please enter your last name"/>
                                <span class="form_help"><spring:message
                                        code="registrationForm.inputField.lastName.help"/></span>
                                <form:errors path="lastName" cssClass="error"/>
                            </div>
                                <%--Department input field--%>
                            <div class="form_row">
                                <label for="department" id="required"><spring:message
                                        code="registrationForm.inputField.department.label"/></label><br/>
                                <form:input id="department" path="department" cssErrorClass="error"
                                            title="Please enter your department within your Institute"/>
                                <span class="form_help"><spring:message
                                        code="registrationForm.inputField.department.help"/></span>
                                <form:errors path="department" cssClass="error"/></div>
                                <%--Institute input field--%>
                            <div class="form_row">
                                <label for="institute" id="required"><spring:message
                                        code="registrationForm.inputField.institute.label"/></label><br/>
                                <form:input id="institute" path="institute" cssErrorClass="error"
                                            title="Please enter your Institute"/>
                                <span class="form_help"><spring:message
                                        code="registrationForm.inputField.institute.help"/></span>
                                <form:errors path="institute" cssClass="error"/></div>
                                <%--Postal address input field--%>
                            <div class="form_row">
                                <label for="postalAddress" id="required"><spring:message
                                        code="registrationForm.inputField.postalAddress.label"/></label><br/>
                                <form:input id="postalAddress" path="postalAddress" cssErrorClass="error"
                                            title="Please enter your Institute's postal address"/>
                                <span class="form_help"><spring:message
                                        code="registrationForm.inputField.postalAddress.help"/></span>
                                <form:errors path="postalAddress" cssClass="error"/></div>
                                <%--Postal code input field--%>
                            <div class="form_row">
                                <label for="postalCode" id="required"><spring:message
                                        code="registrationForm.inputField.postalCode.label"/></label><br/>
                                <form:input id="postalCode" path="postalCode" cssErrorClass="error"
                                            title="Please enter your Institute's postal code"/>
                                <span class="form_help"><spring:message
                                        code="registrationForm.inputField.postalCode.help"/></span>
                                <form:errors path="postalCode" cssClass="error"/></div>
                                <%--Country selection field--%>
                            <div class="form_row">
                                <label for="country" id="required"><spring:message
                                        code="registrationForm.inputField.country.label"/></label><br/>
                                <form:select id="country" path="country" cssErrorClass="error"
                                             title="Please select your Institute's country">
                                    <form:option value="" label="--- Select country ---"/>
                                    <form:options items="${model.countries}"/>
                                </form:select>
                                <span class="form_help"><spring:message
                                        code="registrationForm.inputField.country.help"/></span>
                                <form:errors path="country" cssClass="error"/></div>
                            <legend>Project data</legend>
                                <%--Project title input field--%>
                            <div class="form_row">
                                <label for="title" id="required"><spring:message
                                        code="submissionForm.inputField.title.label"/></label><br/>
                                <form:input id="title" path="subTitle" cssErrorClass="error" title="Project title"/>
                                <span class="form_help"><spring:message
                                        code="submissionForm.inputField.title.help"/></span>
                                <form:errors path="subTitle" cssClass="error"/></div>
                                <%--Date picker input field--%>
                            <div class="form_row">
                                <label for="datepicker" id="required"><spring:message
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
                                <label for="description" id="required"><spring:message
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
                                <label for="notSensitiveData" id="required"><spring:message
                                        code="registrationForm.inputField.notSensitiveData.label"/></label>
                                <br/>
                                <form:checkbox id="notSensitiveData" path="notSensitiveData"
                                               cssErrorClass="error"
                                               title="Please tick this option to confirm that you don't want to submit sensitive data"/>
                                <span class="form_help"><spring:message
                                        code="registrationForm.inputField.notSensitiveData.help"/></span>
                                <form:errors cssClass="error" path="notSensitiveData"/>
                            </div>
                            <p style="clear:both;/*For IE6*/">
                                <input name="submit" value="Submit" class="main_button" type="submit"/>
                                <span class="clear_but">| <a href="/metagenomics" title="cancel">Cancel</a></span>
                                <br/>
                                <span id="required"></span>&nbsp;
                                <small>required</small>
                            </p>
                        </fieldset>
                    </form:form>
                </div>
            </div>

            <div class="sub_ask"> If your datasets do not fit this description, to help us better understand your needs,
                please email us (<a href="mailto:datasubs@ebi.ac.uk?subject=EBI Metagenomics - data submission"
                                    title="Send an enquiry about Metagenomics data submission">datasubs@ebi.ac.uk</a>).
            </div>
        </c:otherwise>
    </c:choose>

</div>

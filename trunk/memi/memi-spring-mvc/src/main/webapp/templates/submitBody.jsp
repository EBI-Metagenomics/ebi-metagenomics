<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="content-full">

    <%@ include file="components/submitIntroAndWarn.jsp" %>

    <div class="sub">

        <div class="sub_form">
            <form:form action="submit" commandName="subForm" id="submit_form">
                <fieldset>
                    <legend>Enter details</legend>
                    <div class="form_row">
                        <label for="title" id="required"><spring:message
                                code="submissionForm.inputField.title.label"/></label><br/>
                        <form:input id="title" path="subTitle" cssErrorClass="error" title="Project title"/>
                        <span class="form_help"><spring:message code="submissionForm.inputField.title.help"/></span>
                        <form:errors path="subTitle" cssClass="error"/></div>

                    <div class="form_row">
                        <label for="datepicker" id="required"><spring:message
                                code="submissionForm.inputField.date.label"/></label>
                        <br/>
                        <form:input id="datepicker" path="releaseDate" cssErrorClass="error"
                                    title="Hold project data private until date"/>
                        <span class="form_help"><spring:message code="submissionForm.inputField.date.help"/></span>
                        <form:errors cssClass="error" path="releaseDate"/>
                    </div>

                    <div class="form_row">
                        <label for="description" id="required"><spring:message
                                code="submissionForm.inputField.desc.label"/></label><br/>
                        <form:textarea id="description" path="dataDesc" cssErrorClass="error" cssStyle="float:left;"
                                       title="Comments about your project"/>
                        <span class="form_help"><spring:message code="submissionForm.inputField.desc.help.provide"/> <ul>
                            <li><spring:message code="submissionForm.inputField.desc.help.li.1"/></li>
                            <li><spring:message code="submissionForm.inputField.desc.help.li.2"/></li>
                            <li><spring:message code="submissionForm.inputField.desc.help.li.3"/></li>
                        </ul><br/><spring:message code="submissionForm.inputField.desc.help.this"/></span>
                        <form:errors cssClass="error" path="dataDesc"/>
                    </div>
                    <p style="clear:both;/*For IE6*/">
                        <input type="submit" name="submit" value="Submit" class="main_button"/>
                        <span class="clear_but">| <a href="/metagenomics" title="cancel">Cancel</a></span>
                        <br/>
                        <span id="required"></span>&nbsp;
                        <small>required</small>
                    </p>
                </fieldset>
            </form:form>

        </div>
    </div>

    <div class="sub_ask"> If your datasets do not fit this description, to help us better understand your needs, please
        email us (<a href="mailto:datasubs@ebi.ac.uk?subject=EBI Metagenomics - data submission"
                     title="Send an enquiry about Metagenomics data submission">datasubs@ebi.ac.uk</a>).
    </div>

</div>

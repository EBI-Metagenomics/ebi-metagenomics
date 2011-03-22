<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="content-full">
    <h2>Contact us</h2>
    
    <p>If you have any questions related to the EBI metagenomics resource, please email us at <a href="mailto:datasubs@ebi.ac.uk" title="Send an enquiry about Metagenomics data submission">datasubs@ebi.ac.uk</a>.</p>

    <p>You can also contact us on Twitter using <a href="http://twitter.com/EBImetagenomics" class="twitter"> @EBImetagenomics</a></p>

    <h3>Beta Website Feedback</h3>
    <p>If you have feedback about this new website, please use the form below to send us your comments:</p>

 
    <form:form action="contact" commandName="contactForm">
        <div class="feedback_form"></div>
        <table class="result" id="contact">
            <tr>
                <td>E-Mail address <small>*</small> : <form:input path="sender"  cssStyle="margin-left:14px; width:284px;"/></td>
            </tr>
            <tr><td ><form:errors cssClass="error" path="sender"/></td></tr>
            <tr>
                <td>Feedback subject <small>*</small> : <form:input path="emailSubject" cssStyle="width:284px;"/></td>
            </tr>
             <tr><td ><form:errors cssClass="error" path="emailSubject"/></td></tr>
            <tr>
                <td>Your comments <small>*</small> :<br/><form:textarea path="message" cols="50" rows="10"/></td>
            </tr>
             <tr ><td><form:errors cssClass="error" path="message"/></td></tr>
            <tr>
               
                <td>
                    <input type="submit" name="submit" value="Submit" class="main_button" />
                    <input type="reset" name="reset" value="Clear" class="main_button"/>
                </td>
            </tr>
        </table>
       <small>* Required</small>
    </form:form>

    

</div>

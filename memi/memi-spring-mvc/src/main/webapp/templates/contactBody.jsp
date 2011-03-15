<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="content-full">
    <h2>Contact us</h2>

    <h3>Data Submission</h3>
    If you have any questions about <b>submitting your data</b> to the EBI metagenomics resource for archiving and analysis, please email us at <a href="mailto:datasubs@ebi.ac.uk" title="Send an enquiry about Metagenomics data submission">datasubs@ebi.ac.uk</a>.

    <h3>Beta Website Feedback</h3>
    If you have feedback about this new website, please use the form below to send us your comments:<br/><br/>

 
    <form:form action="contact" commandName="contactForm">
        <table>
            <tr>
                <td>Your email address*:<form:errors cssClass="error" path="sender"/></td>
                <td><form:input path="sender"/></td>
            </tr>
            <tr>
                <td>Feedback subject*:<form:errors cssClass="error" path="emailSubject"/></td>
                <td><form:input path="emailSubject"/></td>
            </tr>
            <tr>
                <td>Your comments*:<form:errors cssClass="error" path="message"/></td>
                <td><form:textarea path="message" cols="50" rows="10"/></td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <input type="submit" name="submit" value="Submit" class="main_button" />
                    <input type="reset" name="reset" value="Clear" class="main_button"/>
                </td>
            </tr>
        </table>
        <p>* Required</p>
    </form:form>

    

</div>

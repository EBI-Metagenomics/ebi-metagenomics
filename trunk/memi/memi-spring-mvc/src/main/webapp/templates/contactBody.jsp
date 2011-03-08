<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="content-full">
    <h2>Contact us</h2>

    <h3>Data Submission</h3>
    If you have any questions about <b>submitting your data</b> to the EBI metagenomics resource for archiving and analysis, please email us (<a href=mailto:datasubs@ebi.ac.uk>datasubs@ebi.ac.uk</a>).

    <h3>Beta Website Feedback</h3>
    If you have feedback about the design of this new website, please use the form below to send us your comments:

    <div>Subject: Feedback about the new Metagenomics Website</div>

    <div style="margin-top:6px"></div>
    <form:form action="contact" commandName="contactForm">
        <table>
            <tr>
                <td>Your email address*:<form:errors cssStyle="color:red;" path="sender"/></td>
                <td><form:input path="sender"/></td>
            </tr>
            <tr>
                <td>Your comments*:<form:errors cssStyle="color:red;" path="message"/></td>
                <td><form:textarea path="message" cols="50" rows="10"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" name="submit" value="Send Feedback" />
                    <input type="reset" name="reset" value="Reset" />
                </td>
            </tr>
        </table>
    </form:form>
    <p>* Required</p>

    <h3>The team</h3>
    This new resource for metagenomics is supported by the <a href="http://www.ebi.ac.uk/Information/Staff/searchx.php?s_keyword=&s_group=28">InterPro Team</a> at EBI, in collaboration with the ENA and UniProt groups.

</div>

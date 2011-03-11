<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="content-full">
    <h2><fmt:message key="label.submission.title"/></h2>

    <p>
        Please complete the details below to initiate the submission process. We are currently working on a dedicated submission wizard, but in the meantime we are providing the service by requesting the required details by email, our curators will then translate your data and metadata to the required formats.
    </p>
    <p>
        <strong>IMPORTANT:</strong> Presently, analysis is restricted to "long" (average reads lengths over 200nt), unassembled random shotgun sequence reads, i.e. Roche 454 sequences, from metagenomic or metatranscriptomic samples. However, we would like to hear from users with datasets that do not fit this description to help us better understand your needs so we can tailor our future developments appropriately.
    </p>

    <div style="margin-top:6px"></div>
    <form:form action="submit" commandName="subForm">
        <table>
            <tr>
                <td>Project title*:<form:errors cssStyle="color:red;" path="subTitle"/></td>
                <td><form:input path="subTitle" title="The project title will be used in the subject line of email communications to help identify the subject matter, as well as be used in the eventual submission as the title of the project under which the samples and analyses will be grouped (NB this can be changed/updated in the future)." /></td>
            </tr>
            <tr>
                <td>Hold Private Until:*<form:errors cssStyle="color:red;" path="releaseDate"/></td>
                <td><form:input id="datepicker" path="releaseDate" title="The hold private until date is the date at which the information you provide will be released to the public. All data submitted to the EBI metagenomics services must be suitable for public release eventually, i.e. we cannot keep data private indefinitely. The maximum length of time data can be held privately is 2 years." /></td>
            </tr>
            <tr>
                <td>Describe your Submission:*<form:errors cssStyle="color:red;" path="dataDesc"/></td>
                <td><form:textarea path="dataDesc" title="The comments field is a free text box to allow the user to provide additional details about what they wish to submit and/or ask questions about the submission process." /></td>
            </tr>
            <tr>
                <td>
                    <input type="submit" name="submit" value="Submit" class="main_button"/>
                    <input type="submit" name="cancel" value="Cancel" class="main_button"/>
                </td>
            </tr>
        </table>
    </form:form>
    <p>* Required</p>
</div>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="content">
    <h2><fmt:message key="label.submission.title"/></h2>

    <div style="margin-top:6px"></div>
    <form:form action="submissionForm" commandName="subForm">
        <table>
            <tr>
                <td>Submission title*:<form:errors cssStyle="color:red;" path="subTitle"/></td>
                <td><form:input path="subTitle"/></td>
            </tr>
            <tr>
                <td>Release date:*<form:errors cssStyle="color:red;" path="releaseDate"/></td>
                <td><form:input id="datepicker" path="releaseDate"/></td>
            </tr>
            <tr>
                <td>Data description:*<form:errors cssStyle="color:red;" path="dataDesc"/></td>
                <td><form:textarea path="dataDesc"/></td>
            </tr>
            <tr>
                <td>Human associated?<form:errors cssStyle="color:red;" path="humanAssociated"/></td>
                <td><form:checkbox path="humanAssociated"/></td>
            </tr>
            <tr>
                <td>Analysis required?<form:errors cssStyle="color:red;" path="humanAssociated"/></td>
                <td><form:checkbox path="humanAssociated"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Submit"/></td>
            </tr>
        </table>
    </form:form>
    <p>* Required</p>
</div>
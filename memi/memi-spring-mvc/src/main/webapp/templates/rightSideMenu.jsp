<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<div>
    <table border="0" style="border-width: 1px;border-color: #000000;border-style: solid;">
        <tr>
            <td>
                <tiles:insertAttribute name="loginForm"/>
            </td>
        <tr>
        <tr>
            <td>
                <a href="<c:url value=" https://www.ebi.ac.uk/embl/genomes/submission/registration.jsf"/>"
                   title="Registration">How to register?</a>
            </td>
        <tr>
        <tr>
            <td><a href="<c:url value=" https://www.ebi.ac.uk/embl/genomes/submission/forgot-passw.jsf?_afPfm=5"/>"
                   title="Request a new password"> Forgotten your password?</a></td>
        </tr>
    </table>

    <h3>Study list</h3>
    <table border="0" style="border-width: 1px;border-color: #000000;border-style: solid;">
        <c:forEach var="emg_study" items="${mgModel.studies}" varStatus="status">
        <tr>
                <spring:url var="studyUrl" value="studyOverview/${emg_study.studyId}"/>
            <td>
                <a href="${studyUrl}">${emg_study.formattedReleaseDate} - ${emg_study.studyName}</a>
            </td>
        <tr>
            </c:forEach>
    </table>
    <a href="<c:url value="./listStudies"/>">more</a>

    <h3>Other useful links</h3>

    <p><a href="<c:url value="./installationSite"/>">Sample list</a></p>

    <p><a href="mailto:maxim@ebi.ac.uk?cc=maxim.scheremetjew@gmail.com&subject=Request from the MG portal">Contact
        us</a></p>
</div>
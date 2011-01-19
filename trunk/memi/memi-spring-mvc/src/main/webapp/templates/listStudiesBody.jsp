<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div id="content">
    <h2>Overview about all studies</h2>

    <div align="center">
        <table border="0" style="border-width: 1px;border-color: #000000;border-style: solid;">
            <form:form action="listStudies.htm" commandName="filterForm">
                <tr>
                    <td>Study type:</td>
                    <td>
                        <form:select path="studyType">
                            <form:option value="NONE" label="--- Select ---"/>
                            <form:options items="${studyTypeList}" itemValue="studyTypeId"
                                          itemLabel="studyTypeName"/>
                        </form:select>
                    </td>
                    <td><form:errors path="studyType" cssClass="error"/></td>
                </tr>
                <tr>
                    <td>Study status:</td>
                    <td><form:select path="studyStatus">
                        <form:option value="NONE" label="--- Select ---"/>
                        <form:options items="${studyStatusList}" itemValue="studyStatusId"
                                      itemLabel="studyStatusName"/>
                    </form:select></td>
                    <td><form:errors path="studyStatus" cssClass="error"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td>
                        <input type="submit" value="Do filter"/>
                    </td>
                </tr>
            </form:form>
        </table>
    </div>
    <div style="margin-top:40px"></div>
    <spring:url var="exportUrl" value="listStudies/exportStudies"/>
    <div align="left"><a href="${exportUrl}">Export to CSV</a></div>

    <table border="1" width="95%">
        <tr>
            <th>Study Id</th>
            <th>Study name</th>
            <th>Release date</th>
            <th>NCBI Project Id</th>
            <th>Public</th>
            <th>Study type</th>
            <th>Experimental factor</th>
            <th>Number of samples</th>
            <th>Analysis status</th>
        </tr>
        <c:forEach var="study" items="${studyList}" varStatus="status">
            <tr>
                <spring:url var="studyUrl" value="studyOverview/${study.studyId}"/>

                <td>${study.studyId}</td>
                <td>
                    <a href="<c:url value="${studyUrl}"/>">${study.studyName}</a>
                </td>
                <td>${study.formattedReleaseDate}</td>
                <td>${study.ncbiProject}</td>
                <td>${study.submitterId}</td>
                <td>${study.studyType}</td>
                <td>${study.experimentalFactor}</td>
                <td>${study.numberSamples}</td>
                <td>N/A</td>
            </tr>
        </c:forEach>
    </table>
</div>
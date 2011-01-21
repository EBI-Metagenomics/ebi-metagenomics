<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div id="content">
    <h2>Overview about all studies</h2>

    <div align="center">
        <table border="0" style="border-width: 1px;border-color: #000000;border-style: solid;">
            <form:form method="POST" action="${baseURL}/listStudies/doSearch" commandName="searchStudiesForm">
                <tr>
                    <td>Text:</td>
                    <td><form:input id="autocomplete" path="searchTerm"/></td>
                    <td><form:errors path="searchTerm" cssClass="error"/></td>
                </tr>
                <tr>
                    <td>Study type:</td>
                    <td>
                        <form:select path="studyType">
                            <form:option value="All" label="All"/>
                            <form:options items="${mgModel.studyTypes}" itemValue="studyTypeName"
                                          itemLabel="studyTypeName"/>
                        </form:select>
                    </td>
                    <td><form:errors path="studyType" cssClass="error"/></td>
                </tr>
                <tr>
                    <td>Study status:</td>
                    <td><form:select id="studyStatus" path="studyStatus">
                        <form:option value="All" label="All"/>
                        <form:options items="${mgModel.studyStatusList}" itemValue="studyStatusName"
                                      itemLabel="studyStatusName"/>
                    </form:select></td>
                    <td><form:errors path="studyStatus" cssClass="error"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td>
                        <input type="submit" value="Search"/>
                    </td>
                </tr>
            </form:form>
        </table>
    </div>
    <div style="margin-top:40px"></div>
    <div align="left">
        <a href="<c:url value="${baseURL}/listStudies/exportStudies"/>">Export to CSV</a>
    </div>

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
        <c:forEach var="study" items="${mgModel.studies}" varStatus="status">
            <tr>
                <td>${study.studyId}</td>
                <td>
                    <a href="<c:url value="${baseURL}/studyOverview/${study.studyId}"/>">${study.studyName}</a>
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
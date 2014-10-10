<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="loginDialog.jsp" %>

<ul id="local-nav">
    <li class="${model.tabClasses["tabClassHomeView"]} first"><a href="<c:url value="${baseURL}/"/>"
                                                                 title="Home">Home</a></li>
    <li class="${model.tabClasses["tabClassSubmitView"]}"><a href="<c:url value="${baseURL}/submit"/>"
                                                             class="more_desc" title="Submit data">Submit
        data</a></li>

    <li class="${model.tabClasses["tabClassProjectsView"]}">
        <c:choose>
            <c:when test="${empty model.submitter}">
                <a href="<c:url value="${baseURL}/projects/doSearch?search=Search&studyVisibility=ALL_PUBLISHED_PROJECTS"/>"
                   title="View projects">Projects</a>
            </c:when>
            <c:otherwise>
                <a href="<c:url value="${baseURL}/projects/doSearch?search=Search&studyVisibility=ALL_PROJECTS"/>"
                   title="View projects">Projects</a>
            </c:otherwise>
        </c:choose>
    </li>
    <li class="${model.tabClasses["tabClassSamplesView"]}">
        <c:choose>
            <c:when test="${empty model.submitter}">
                <a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleVisibility=ALL_PUBLISHED_SAMPLES&search=Search&startPosition=0"/>"
                   title="View samples">Samples</a>
            </c:when>
            <c:otherwise>
                <a href="<c:url value="${baseURL}/samples/doSearch?searchTerm=&sampleVisibility=ALL_SAMPLES&search=Search&startPosition=0"/>"
                   title="View samples">Samples</a>
            </c:otherwise>
        </c:choose>
    </li>


    <li class="${model.tabClasses["tabClassCompareView"]}"><a href="<c:url value="${baseURL}/compare"/>"
                                                              title="Compare samples of same project">Comparison tool <span class="icon_beta_menu">beta</span></a></li>

    <li class="${model.tabClasses["tabClassAboutView"]}"><a href="<c:url value="${baseURL}/info"/>"
                                                            title="About EBI Metagenomics">About EBI Metagenomics</a>
    </li>


    <li class="${model.tabClasses["tabClassContactView"]} last"><a href="<c:url value="${baseURL}/contact"/>"
                                                                   title="Contact us">Contact</a></li>

    <c:choose>
        <c:when test="${empty model.submitter}">
            <%--<li class="functional last"><a href="#" class="icon icon-static" data-icon="\">Feedback</a></li>--%>
            <li class="functional last">
                    <%--<a  class="icon icon-functional" data-icon="l" id="script_loginLink" href="javascript:openLoginDialogForm()" title="Login">Login</a>--%>
                        <a id="LoginBlockUI" href="" title="Login">Login</a>
                        <%--<a id="script_loginLink" href="javascript:openLoginDialogForm()" title="Login">Login</a>--%>

                <a class="icon icon-functional" data-icon="l" id="noscript_loginLink"
                   href="<c:url value="${baseURL}/login?display=false"/>" title="Login">Login</a>
            </li>
            <li class="logout"> Not logged in</li>
        </c:when>

        <c:otherwise>
            <li class="functional last">
                    <%--<a class="icon icon-functional" data-icon="l" href="<c:url value="${baseURL}/logout"/>" title="logout">logout</a>--%>
                <a href="<c:url value="${baseURL}/logout"/>" title="logout">logout</a>
            </li>
            <%--<li class="login_name"><c:out value="${model.submitter.loginName}"/>--%>
                <%--<c:url var="editPrefsUrl" value="${model.propertyContainer.enaSubmissionURL.editPrefsLink}"><c:param--%>
                        <%--name="url" value="${enaUrlParam}"/></c:url>--%>
                <%--&nbsp;<a href="<c:out value="${editPrefsUrl}"/>" title="Edit preferences">(edit)</a></li>--%>

            <li class="login_name"><c:out value="${model.submitter.loginName}"/>
                &nbsp;<a href="https://www.ebi.ac.uk/ena/submit/sra/#home" title="Edit preferences">(edit)</a></li>

        </c:otherwise>
    </c:choose>

</ul>





<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- This template is used for the login page --%>
<div id="content-full">
        <form:form method="POST" action="login" commandName="loginForm">

            <h3>Login</h3><br/>
             Login to submit and view your data:<br/>
                <form:errors cssStyle="color:red;" path="emailAddress"/>
                    <form:errors cssStyle="color:red;" path="password"/>

            E-Mail*:<form:input path="emailAddress"/><br/>
            Password:<form:password path="password"/> <br/>
            <input type="submit" name="login" value="Login"/>
                    <input type="submit" name="cancel" value="Cancel"/>
            </tr>
        </form:form>
        <%--* A valid login is: E-Mail: TEST; Password:test--%>
       <br/>
       <a href="<c:url value=" https://www.ebi.ac.uk/embl/genomes/submission/registration.jsf"/>" title="Registration">Register</a>
       <br/>
       <a href="<c:url value=" https://www.ebi.ac.uk/embl/genomes/submission/forgot-passw.jsf?_afPfm=5"/>"
                   title="Request a new password">Forgotten password</a>
        
</div>
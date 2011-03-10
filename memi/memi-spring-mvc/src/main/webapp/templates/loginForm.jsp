<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- This template is used within the login page component--%>
<c:if test="${empty model.submitter}">
<div id ="sidebar-login">

    <img src="../img/icon_lock.gif" alt="Login lock icon"><h2>Login</h2>
    Login to submit and view your data:<br/>
         <form:form  method="POST" action="" commandName="loginForm">
         E-Mail:<br/> <form:errors cssClass="error" path="emailAddress"/><form:input path="emailAddress"/> <br/>

         Password:<br/> <form:errors cssClass="error" path="password"/><form:password path="password"/> <br/>

         <input type="submit" name="login" class="main_button" value="Login"/>
         </form:form>
     <a href="<c:url value="https://www.ebi.ac.uk/embl/genomes/submission/forgot-passw.jsf?_afPfm=5"/>" title="Request a new password">Forgot your password?</a>
         <%--* A valid login is: E-Mail: TEST; Password:test--%>
         <span class="separator"></span>
         <strong>New to metagenomics?</strong><br/>
         <a href="<c:url value=" https://www.ebi.ac.uk/embl/genomes/submission/registration.jsf"/>" title="Registration">Click here to register</a>
         <%-- <br/>
         <a href="<c:url value=" https://www.ebi.ac.uk/embl/genomes/submission/forgot-passw.jsf?_afPfm=5"/>"
                     title="Request a new password">Forgotten password</a>--%>
   
</div>
</c:if>


<div id ="sidebar-news">
  <a href="${model.rssUrl}" rel="alternate" type="application/rss+xml" title="Metagenomics RSS feeds" ><img src="../img/icon_rss.gif" alt="Metagenomics RSS feeds"></a><h2>News and Events</h2>
  <span class="separator"></span>
<c:choose>
    <c:when test="${empty model.rssItems}">
        No news
    </c:when>
    <c:otherwise>
        <ul>
        <c:forEach var="entry" items="${model.rssItems}" varStatus="status">
            <li><a href="${entry.link}" title="${entry.description.value}">${entry.title}</a></li>
        </c:forEach>
        </ul>
        
    </c:otherwise>
</c:choose>
<%--<ul>--%>
    <%--<li><a href="">GSC11 conference- Hinxton, UK.</a> <span class="date">April 4-6th, 2011</span> </li>--%>
    <%--<li><a href="">Keystone symposium - Microbial Communities as Drivers of Ecosystem Complexity Breckenridge, Colorado, USA.</a> <span class="date">March 25 - 30, 2011</span></li>--%>
    <%--<li><a href="">Metagenomics: From the Bench to the Data Analysis - Heidelberg, Germany. </a> <span class="date">October 23 - 29, 2011</span></li>--%>
<%--</ul>--%>
<%----%>
    
<p><a href="http://twitter.com/EBImetagenomics" class="twitter">Follow us on Twitter</a></p>
</div>

<div id ="sidebar-mailing">
<img src="../img/icon_mailing.gif" alt="Mailing list"><h2>Mailing list</h2>
<p>Subscribe to the EBI metagenomics mailing list to receive by email  updated information on the website activities: <a href="http://listserver.ebi.ac.uk/mailman/listinfo/metagenomics">click here.</a></p>
</div>
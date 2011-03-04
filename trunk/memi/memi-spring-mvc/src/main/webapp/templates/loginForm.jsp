<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- This template is used within the login page component--%>
<c:if test="${empty model.submitter}">
<div id ="sidebar-login">
   
   <form:form  method="POST" action="" commandName="loginForm">

              <h2>Login</h2>
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
         <br/>New to metagenomics?
         <a href="<c:url value=" https://www.ebi.ac.uk/embl/genomes/submission/registration.jsf"/>" title="Registration" class="but_signup">Sign-up</a>
         <%-- <br/>
         <a href="<c:url value=" https://www.ebi.ac.uk/embl/genomes/submission/forgot-passw.jsf?_afPfm=5"/>"
                     title="Request a new password">Forgotten password</a>--%>
</div>
</c:if>


<div id ="sidebar-news">
<h2>News and Events</h2>
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
<div id="rssfeed">
    <a href="${model.rssUrl}" class="rss" rel="alternate" type="application/rss+xml">RSS feed</a>
</div>        

<%--<ul>--%>
    <%--<li><a href="">GSC11 conference- Hinxton, UK.</a> <span class="date">April 4-6th, 2011</span> </li>--%>
    <%--<li><a href="">Keystone symposium - Microbial Communities as Drivers of Ecosystem Complexity Breckenridge, Colorado, USA.</a> <span class="date">March 25 - 30, 2011</span></li>--%>
    <%--<li><a href="">Metagenomics: From the Bench to the Data Analysis - Heidelberg, Germany. </a> <span class="date">October 23 - 29, 2011</span></li>--%>
<%--</ul>--%>
<%--<p><a href="#">Follow us on Twitter</a></p>--%>    
</div>

<div id ="sidebar-mailing">
<h2>Mailing list</h2>
<p>Subscribe to the EBI metagenomics mailing list to receive by email  updated information on the website activities: <a href="#">Click here</a></p>
</div>
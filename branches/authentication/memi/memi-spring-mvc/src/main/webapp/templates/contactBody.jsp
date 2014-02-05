<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Contact us</h2>

<p>If you have any questions related to the EBI metagenomics resource, please <a title="EBI's support & feedback form"
                                                                                 href="http://www.ebi.ac.uk/support/metagenomics"
                                                                                 class="ext">contact us</a>.</p>

<h3>Follow us on Twitter</h3>

<p>Follow us on Twitter using <a href="http://twitter.com/EBImetagenomics">@EBImetagenomics</a></p>

<a title="ENA account administration"
   href="<c:url value="${baseURL}/contact/redirect"/>"
   class="ext">Edit ENA Account Preferences</a>

<input type="button" id="enaPost" value="Login SRA Webin"/>

<%--<script type="text/javascript">--%>
    <%--$(document).ready(function () {--%>
        <%--$("#enaPost").click(function () {--%>
            <%--var loginDetails = [--%>
                <%--{"username":"metagenomics.ebi@gmail.com", "password":"testtest", "rememberMe":true}--%>
            <%--];--%>

            <%--$.ajax({--%>
                <%--url:"/ena/account/admin/rest/auth/login",--%>
                <%--type:"POST",--%>
                <%--// The key needs to match your method's input parameter (case-sensitive).--%>
                <%--data:JSON.stringify({"username":"metagenomics.ebi@gmail.com", "password":"testtest", "rememberMe":true}),--%>
                <%--contentType:"application/json; charset=utf-8",--%>
                <%--dataType:"json",--%>
                <%--success:function (data) {--%>
                    <%--alert(data);--%>
                <%--},--%>
                <%--failure:function (errMsg) {--%>
                    <%--alert(errMsg);--%>
                <%--}--%>
            <%--});--%>
        <%--});--%>
    <%--});//end document ready method--%>
<%--</script>--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Thank you for completing the ENA account registration form</h2>

<figure class="fr"><img src="${pageContext.request.contextPath}/img/graphic_submission_01e01.gif" alt="5-7 steps submission and analysis process"/>
<figcaption><p>First step: registration (detail of 5-7 steps submission and analysis process)</p></figcaption></figure>
    <p class="intro">
        Your request has been successfully sent to the European Nucleotide Archive (ENA). <br/>You will be contacted on the email address
        you supplied once the account is activated.

        A copy of your request has been sent to your email address, if you do not receive this within a
        few minutes, then please check your spam/bulk mail folder and/or <a title="EBI's support & feedback form"
                                                                                         href="http://www.ebi.ac.uk/support/metagenomics"
                                                                                         class="ext">contact us</a>. Go back to our <a title="Home page"
                                                                                    href="<c:url value="${baseURL}/"/>">homepage</a>.
    </p>

<%--
  Created by Maxim Scheremetjew, EMBL-EBI, InterPro
  Date: 04-Jan-2011
  Footer template (integrates the EBI main footer)
  --%>
<html>
<body>
<%-- EBI main footer --%>
<%-- For more information on how to create EBI group and project specific pages please read the guideline on
http://www.ebi.ac.uk/inc/template/#important--%>
<table class="footerpane" id="footerpane" summary="The main footer pane of the page">
    <tr>
        <td class="footerrow" colspan="4">
            <div class="footerdiv" id="footerdiv" style="z-index:2;">
                <%-- The latest version of the EBI main header can be viewed on http://www.ebi.ac.uk/inc/foot.html--%>
                <%@include file='/inc/foot.html' %>
            </div>
        </td>
    </tr>
</table>
</body>
</html>
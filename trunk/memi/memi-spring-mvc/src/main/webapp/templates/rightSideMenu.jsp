<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<div id="right_side_navigation">
    <table border="0" style="border-width: 1px;border-color: #000000;border-style: solid;">
        <tr>
            <td>
                <tiles:insertAttribute name="loginForm"/>
            </td>
        <tr>
    </table>
</div>
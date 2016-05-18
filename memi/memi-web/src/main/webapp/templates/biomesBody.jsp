<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div id="content-full">
    <h2>All Biomes</h2>

<c:choose>
    <c:when test="${not empty biome_counter}">

        <table border="1"  class="table-heading result" id="list-biomes">
            <thead>
                <tr>
                    <td></td>
                    <th class="biome" abbr="Biome" scope="col">Biome</th>
                    <th class="number-projects" abbr="num_proj" scope="col">Number of Projects</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="biome" items="${biome_counter}" varStatus="status">
                    <tr>
                        <td>
                            <span style="display: none">${biome[1].lineage}</span>
                            <span class="biome_icon icon_xs ${biome[1].cssClass}"
                                  title="${biome[1].iconTitle} biome"></span>
                        </td>
                        <td>
                            <a href="projects/doSearch?searchTerm=&biomeLineage=${biome[1].lineage}&search=Search">${biome[1].biomeName}</a><br/>
                            ${biome[1].formattedLineage}
                        </td>
                        <td>
                            <a href="projects/doSearch?searchTerm=&biomeLineage=${biome[1].lineage}&search=Search">${biome[0]}</a>
                        </td>
                    </tr>

                </c:forEach>
            </tbody>
        </table>
        <br/>
        <script>
            $(document).ready(function() {
                $('#list-biomes').DataTable();
            } );
        </script>
    </c:when>
    <c:otherwise>
        <div class="msg_error">No biome-data was recovered</div>
    </c:otherwise>
</c:choose>

</div>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="uk.ac.ebi.interpro.metagenomics.memi.basic.TestDB"%>
<%
    uk.ac.ebi.interpro.metagenomics.memi.basic.TestDB db = new uk.ac.ebi.interpro.metagenomics.memi.basic.TestDB();

    String query = request.getParameter("q");

    List<String> countries = db.getData(query);

    Iterator<String> iterator = countries.iterator();
    while(iterator.hasNext()) {
        String country = (String)iterator.next();
        out.println(country);
    }
%>
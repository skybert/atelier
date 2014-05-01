<%@ page import="java.util.*,
                 no.studios.atelier.util.*,
                 no.studios.atelier.view.*,
                 no.studios.atelier.model.*" %>
<jsp:include page="header.jspf" />
<%
CustomerView view = new CustomerView();
Customer customer = null;

String id = request.getParameter("id");
String oldId = request.getParameter("old-id");
String name = request.getParameter("name");
String searchCriterion = "ID";
List<Customer> searchResults = new ArrayList<Customer>();

if (!StringUtil.isEmpty(id))
{
  customer = view.getCustomer(id);
  if (customer != null) 
  {
    searchResults.add(customer);
  }
}
else if (!StringUtil.isEmpty(oldId)) 
{
  searchResults = view.getCustomersByAnyOldId(oldId);
}
else if (!StringUtil.isEmpty(name)) 
{
  searchResults = view.getCustomersByAnyName(name);
}

%>
<div id="main">
  <h1>Kunde</h1>
  <div id="content">
    <jsp:include page="customerToolbar.jspf" />
    <h2>S&oslash;k etter kunde</h2>
    <form action="searchCustomer.jsp" method="get">
      <div>
         Kundenummer: <input name="id" /> (etter 2008)<br/>
         Gammelt nr: <input name="old-id" /> (gammelt arkiv- eller kundenummer f&oslash;r 2008)<br/>
         Navn: <input name="name" /> (fornavn, mellomnavn og/eller etternavn).
        <br/>
      </div>
      <div>
        <input type="submit" value="S&oslash;k"/>
      </div>
    </form>
    <h2>S&oslash;ket ga <%= searchResults.size() %> treff</h2>
<%
if (searchResults.size() == 1)
{
%>
    <script type="text/javascript">
      window.location = "viewCustomer.jsp?<%=
      view.getURIFragment(searchResults.get(0))
      %>";
    </script>
<%
}
else if (searchResults.size() > 0) 
{
%>
  <ul id="search-results">
<%
  for (Customer theCustomer : searchResults)
  {
%>    
      <li>
        <a href="viewCustomer.jsp?<%=
          Customer.KEY_CUSTOMER_ID + "=" + theCustomer.getId() %>">
          ID <%= theCustomer.getId() %>
          -
          <%= view.print(theCustomer.getFirstName()) %>
          <%= view.print(theCustomer.getLastName()) %>
          -
          registrert:
          <%= view.print(theCustomer.getCreationDate()) %>
        </a>
      </li>
<%
  }
%>
    </ul>
<%
}
%>
  </div>
</div>
<jsp:include page="menu.jspf" />
<jsp:include page="footer.jsp" />

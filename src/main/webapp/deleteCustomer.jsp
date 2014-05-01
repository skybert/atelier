<%@ page import="java.util.*,
                 no.studios.atelier.util.*,
                 no.studios.atelier.view.*,
                 no.studios.atelier.model.*" %>
<jsp:include page="header.jspf" />
<div id="main">
  <h1>Slette kunde?</h1>
  <jsp:include page="customerToolbar.jspf" />
  <div id="content">
<%
CustomerView view = new CustomerView();
Customer customer = view.getCustomer(request);

if (customer != null) 
{
%>
    <h2>
      Vil du virkelig slette bruker
      <%= "ID " + customer.getId()
          + " - " + customer.getFirstName()
          + " "
          + customer.getLastName()
          + "?"
          %>
    </h2>
    <p>
      <a href="<%= request.getContextPath()
                   + "/" + Customer.URI_CUSTOMER 
                   + "/" + Customer.URI_DELETE %>"
      >
        Slett kunde
      </a>
    </p>
<!--
    <form action="/customer/delete" method="get">
      <div>
        <input
            type="hidden"
            name="<%= Customer.KEY_CUSTOMER_ID %>"
            value="<%= customer.getId() %>"
        />
        <input type="submit" value="Slett bruker"/>
      </div>
    </form>
-->
<%
}
%>
    <!-- id=content -->
  </div>
  <!-- id=main -->
</div>
<jsp:include page="menu.jspf" />
<jsp:include page="footer.jsp" />

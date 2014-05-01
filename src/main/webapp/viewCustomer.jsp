<%@ page import="java.util.*,
                 no.studios.atelier.util.*,
                 no.studios.atelier.view.*,
                 no.studios.atelier.model.*" %>
<jsp:include page="header.jspf" />
<div id="main">
  <h1>Kunde</h1>
  <div id="content">
<%
CustomerView view = new CustomerView();
CustomerOrderView customerOrderView = new CustomerOrderView();
Customer customer = view.getCustomer(request);
PostPlaceView postView = new PostPlaceView();

if (customer != null) 
{
%>
    <div id="toolbar">
      <a href="newCustomer.jsp">
        <img src="graphics/document-new.png" alt="new"
          title="Ny kunde"
        />
        Ny kunde
      </a>
      <a href="newCustomer.jsp?<%=
        Customer.KEY_CUSTOMER_ID + "=" + customer.getId() %>">
        <img src="graphics/accessories-text-editor.png"
             alt="edit"
        />
        Rediger kunde
      </a>
      <!--
      <a href="deleteCustomer.jsp?<%=
        Customer.KEY_CUSTOMER_ID + "=" + customer.getId() %>">
        <img src="graphics/edit-delete.png"
             alt="delete"
        />
        Slett kunde
      </a>
      -->
      <a href="searchCustomer.jsp">
        <img src="graphics/system-search.png" alt="search"
          title="S&oslash;k etter kunde"
        />
        S&oslash;k etter kunde
      </a>
      <a href="javascript:window.print()">
        <img src="graphics/printer.png" alt="print"
          title="Skriv ut"
        />
        Skriv ut kunde
      </a>
    </div>
    <div id="customer-info">
      <h2>Kunde nummer <%= customer.getId() %></h2>
      <p>
        Navn: <%= view.printName(customer) %><br/>
        Opprettet: <%= view.print(customer.getCreationDate()) %><br/>
        Gammelt kundenr:
        <%= view.print(customer.getOldCustomerId()) %><br/>
        Gammelt arkivnr:
        <%= view.print(customer.getOldArchiveId()) %><br/>
      </p>
      <p>
        Addresse:
        <%= view.print(customer.getAddress()) %>
        <br/>
        Postnummer:
        <%= view.print(customer.getPostCode()) %>
        Poststed: 
        <%= view.print(postView.getPostPlace(customer.getPostCode())) %>
        <br/>
        F&oslash;dselsdato:
        <%= view.print(customer.getBirthDate()) %>
        <br/>
        Telefon hjem:
        <%= view.print(customer.getHomePhone()) %>
        <br/>
        Telefon mobil:
        <%= view.print(customer.getMobilePhone()) %>
        <br/>
        Telefon arbeid:
        <%= view.print(customer.getWorkPhone()) %>
        <br/>
        Epost:
        <a href="mailto:<%= customer.getEmailAddress() %>">
          <%= customer.getEmailAddress() %>
        </a>
      </p>
    </div>
    <!-- orders start -->
    <div id="customer-orders">
      <h2>Ordreliste</h2>
<%
  List<CustomerOrder> orderList = 
    view.getAllCustomerOrders(customer.getId());

  if (orderList.size() > 0)
  {
%>
      <table id="customer-order-list">
        <tr>
          <th>Ordrenr.</th>
          <th>Produkt</th>
          <th>Leveringsdato</th>
          <th>Betalingsm&aring;te</th>
          <th>Sum</th>
          <th>Betalt</th>
          <th>Rest</th>
          <th>Status</th>
        </tr>
<%  
    for (CustomerOrder customerOrder : orderList) 
    {
%>  
        <tr>
          <td>
            <a href="viewOrder.jsp?<%= CustomerOrder.KEY_ORDER_ID 
                                    + "=" + customerOrder.getId() %>">
              <%= customerOrder.getId() %>
            </a>
          </td>
          <td>
          <%= 
          "n/a"
          %>
          </td>
          <td>
            <%= view.print(customerOrder.getDeliveryDate()) %>
          </td>
          <td>
            <%= customerOrder.getPaymentType() != null ?
                customerOrder.getPaymentType().getName() : "n/a"
            %>
          </td>
          <td>
            <%=
            view.print(customerOrderView.getOrderPrice(customerOrder))
            %>
          </td>
          <td>
            <%= view.print(customerOrder.getPaidAmount()) %>
          </td>
          <td>
            <%=
            view.print(customerOrderView.getOrderBalance(customerOrder))
            %>
          </td>
          <td>
          <%= customerOrder.getOrderStatus() != null ?
              customerOrder.getOrderStatus().getName() : "n/a"
          %>
          </td>
        </tr>
<%
    }
%>
      </table>  
<%
  }
%>
      <a href="newOrder.jsp?customer-id=<%= customer.getId() %>">
        <img src="graphics/document-new.png"
             alt="new"
             title="Ny ordre"
        />
        Legg til ny ordre
      </a>
    </div>
    <!-- orders stop -->
<%
}
else
{
%>
    <p class="failure">
      Det finnes ingen kunde med kundenummer <%= view.getCustomerId(request) %>
    </p>
<%
}
%>
  </div>
</div>
<jsp:include page="menu.jspf" />
<jsp:include page="footer.jsp" />

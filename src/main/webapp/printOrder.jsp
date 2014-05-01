<%@ page import="java.util.*,
                 no.studios.atelier.util.*,
                 no.studios.atelier.view.*,
                 no.studios.atelier.model.*" %><%--
$Id: printOrder.jsp,v 1.5 2008/10/11 19:17:48 torstein Exp $                 
--%>
<%
CustomerOrderView view = new CustomerOrderView();
CustomerOrder customerOrder = view.getCustomerOrder(request);
%>
<jsp:include page="printHeader.jspf" />
<div id="main">
  <div id="content">
<%
if (customerOrder != null)
{
  Customer customer = customerOrder.getCustomer();
%>
    <div id="order-header">
      <table>
        <tr>
          <td class="header">
            Ordrenummer: <%= customerOrder.getId() %><br/>
            <%= view.print(customer.getLastName()) %>,
            <%= view.print(customer.getFirstName()) %>
          </td>
          <td class="header">
            Dato:
            <%= view.print(customerOrder.getCreationDate()) %>
          </td>
        </tr>
      </table>
    </div>
    <div id="customer-info">
      <table>
        <tr>
          <td>
            Kundenr: <%= customer.getId() %><br/>
            Gammelt kundenr:
            <%= view.print(customer.getOldCustomerId()) %><br/>
            Gammelt arkivnr:
            <%= view.print(customer.getOldArchiveId()) %><br/>
            Navn:
            <%= view.print(customer.getLastName()) %>,
            <%= view.print(customer.getFirstName()) %><br/>
            Adresse: 
            <%= view.print(customer.getAddress()) %><br/>
            Postnr: 
            <%= view.print(customer.getPostCode()) %>
          </td>
          <td>
            Telefon: <%= view.print(customer.getHomePhone()) %>
            <br/>
            Mobil: <%= view.print(customer.getMobilePhone()) %>
            <br/>
            Arbeid: <%= view.print(customer.getWorkPhone()) %>
            <br/>
            F&oslash;dt: <%= view.print(customer.getBirthDate()) %>
          </td>
          <td>
            Pris: <%= view.print(view.getOrderPrice(customerOrder)) %>
            <br/>
            Betalt: <%= view.print(customerOrder.getPaidAmount()) %>
            <br/>
            Rest:
            <%= view.print(view.getOrderPrice(customerOrder) 
                                 - customerOrder.getPaidAmount()) %>
            <br/>
            Leveringsdato:
            <%= view.print(customerOrder.getDeliveryDate()) %>
            <br/>
            Status:
            <%= customerOrder.getOrderStatus() != null ?
            customerOrder.getOrderStatus().getName() : "n/a"
            %>
            <br/>
            Kan brukes i avis:
            <%= view.print(customerOrder.getAllowedUsedInNewspaper()) %>
            <br/>
            Kan brukes i markedsf&oslash;ring:
            <%= view.print(customerOrder.getAllowedUsedForMarketing()) %>
            <br/>
            Betalingstype:
            <%= customerOrder.getPaymentType() != null ?
            customerOrder.getPaymentType().getName() : "n/a" %>
            <br/>
            Sist oppdatert:
            <%= view.print(customerOrder.getUpdatedDate()) %><br/>
          </td>
        </tr>
      </table>
    </div>
    <div id="order-item-list">
<%
  List<OrderItem> orderItemList = customerOrder.getOrderItems();
  if (orderItemList != null && orderItemList.size() > 0)
  { %>      
      <table id="order-item-table">
        <tr>
          <th>Betegnelse</th>
          <th>Antall</th>
          <th>Pris</th>
          <th>Rabatt</th>
          <th>Bel&oslash;p</th>          
          <th>Kommentar</th>          
        </tr>
<%
    for (OrderItem orderItem : orderItemList)
    {
%>
        <tr>
          <td><%= view.print(orderItem.getProduct().getName()) %></td>
          <td><%= orderItem.getNumberOfItems() %></td>
          <td class="right"><%= view.print(orderItem.getProduct().getPrice()) %></td>
          <td class="right"><%= view.print(orderItem.getDiscount()) %></td>
          <td class="right">
            <%= view.print(orderItem.getTotalAmount()) %>
          </td>
          <td>
<pre>
<%= view.print(orderItem.getComment()) %>
</pre>
          </td>
        </tr>
<%
    }
%>        
      </table>
    </div>
      
<%
  }
  // if no order items ...
  else
  {
%>
      <p>Ordren har ingen ordre elementer.</p>
<%
  }
%>
<%
// if no customer ...
}

%>
  <div>
</div>
<jsp:include page="printFooter.jspf" />

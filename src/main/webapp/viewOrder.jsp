<%@ page import="java.util.*,
                 no.studios.atelier.util.*,
                 no.studios.atelier.view.*,
                 no.studios.atelier.model.*" %><%--
$Id: viewOrder.jsp,v 1.13 2008/10/11 19:17:48 torstein Exp $                 
--%>
<%
CustomerOrderView view = new CustomerOrderView();
CustomerOrder customerOrder = view.getCustomerOrder(request);
PostPlaceView postView = new PostPlaceView();
%>
<jsp:include page="header.jspf" />
<div id="main">
  <h1>Ordre</h1>
  <div id="content">
    <jsp:include page="toolbar.jsp">
      <jsp:param name="toolbar-type" value="toolbar-order" />
    </jsp:include>
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
            <%= view.printName(customer) %>
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
            Kundenr: <%= customer.getId() %>
          </td>
          <td>
            Telefon: <%= view.print(customer.getHomePhone()) %>
          </td>
          <td>
            Pris: <%= view.print(view.getOrderPrice(customerOrder)) %>
          </td>
        </tr>
        <tr>
          <td>
            Navn:
            <a href="viewCustomer.jsp?<%= view.getURIFragment(customer) %>">
              <%= view.printName(customer) %>
            </a>
          </td>
          <td>
            Mobil: <%= view.print(customer.getMobilePhone()) %>
          </td>
          <td>
            Betalt: <%= view.print(customerOrder.getPaidAmount()) %>
          </td>
        </tr>
        <tr>
          <td>
            Adresse: 
            <%= view.print(customer.getAddress()) %>
          </td>
          <td>
            Arbeid: <%= view.print(customer.getWorkPhone()) %>
          </td>
          <td>
            Rest:
            <%= view.print(view.getOrderPrice(customerOrder) 
                                 - customerOrder.getPaidAmount()) %>
          </td>
        </tr>
        <tr>
          <td>
            Postnr: 
            <%= view.print(customer.getPostCode()) %>
            <%= view.print(postView.getPostPlace(customer.getPostCode())) %>
          </td>
          <td>
            F&oslash;dt: <%= view.print(customer.getBirthDate()) %>            
          </td>
          <td>
            Leveringsdato:
            <%= view.print(customerOrder.getDeliveryDate()) %>
          </td>
        </tr>
        <tr>
          <td></td>
          <td></td>
          <td>
            Status:
            <%= customerOrder.getOrderStatus() != null ?
            customerOrder.getOrderStatus().getName() : "n/a"
            %>
          </td>
        </tr>
        <tr>
          <td>
            Kan brukes i avis:
            <%= view.print(customerOrder.getAllowedUsedInNewspaper()) %>
          </td>
          <td></td>
          <td>
            Betalingstype:
            <%= customerOrder.getPaymentType() != null ?
            customerOrder.getPaymentType().getName() : "n/a" %>
          </td>
        </tr>
        <tr>
          <td>
            Kan brukes i markedsf&oslash;ring:
            <%= view.print(customerOrder.getAllowedUsedForMarketing()) %>
          </td>
          <td></td>
          <td>
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
          <th></th>
          <th>Ordreel.</th>
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
          <td>
            <a href="newOrderItem.jsp?<%=
              AtelierEntity.KEY_ORDER_ID + "=" + customerOrder.getId()
              + "&"
              + AtelierEntity.KEY_ORDER_ITEM_ID + "=" + orderItem.getId()
              %>">
              <img src="graphics/accessories-text-editor-small.png"
                   alt="edit"
                   title="Rediger ordre"
              />
            </a>
          </td>
          <td>
            <a href="viewOrderItem.jsp?<%=
              AtelierEntity.KEY_ORDER_ID + "=" + customerOrder.getId()
              + "&"
              + AtelierEntity.KEY_ORDER_ITEM_ID + "=" + orderItem.getId()
              %>">
              <%= orderItem.getId() %>
            </a>
          </td>
          <td><%= view.print(orderItem.getProduct().getName()) %></td>
          <td><%= orderItem.getNumberOfItems() %></td>
          <td class="right">
            <%= view.print(orderItem.getProduct().getPrice()) %>
          </td>
          <td class="right">
            <%= view.print(orderItem.getDiscount()) %>
          </td>
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
      <p>
        <a href="newOrderItem.jsp?<%= view.getURIFragment(customerOrder) %>">
          <img src="graphics/document-new.png" alt="new"
            title="ny vare"
          />
          Legg til vare
        </a>
      </p>
<%
// if no customer ...
}

%>
  <div>
</div>
<jsp:include page="menu.jspf" />
<jsp:include page="footer.jsp" />

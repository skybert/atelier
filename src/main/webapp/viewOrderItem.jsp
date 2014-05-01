<%@ page import="java.util.*,
                 no.studios.atelier.util.*,
                 no.studios.atelier.view.*,
                 no.studios.atelier.model.*" %><%--
$Id: viewOrderItem.jsp,v 1.5 2008/03/31 11:21:24 torstein Exp $                 
--%>
<%
OrderItemView view = new OrderItemView();
OrderItem orderItem = view.getOrderItem(request);

CustomerOrderView orderView = new CustomerOrderView();
// CustomerOrder customerOrder = orderView.getCustomerOrder(request);
CustomerOrder customerOrder =
  orderView.getCustomerOrder(Integer.toString(orderItem.getOrderId()));
%>
<jsp:include page="header.jspf" />
<div id="main">
  <h1>Ordre element</h1>
  <div id="content">
<%
if (orderItem != null && customerOrder != null)
{
%>
    <div id="toolbar">
      <a href="viewOrder.jsp?<%=
        CustomerOrder.KEY_ORDER_ID + "=" + customerOrder.getId() %>">
        <img src="graphics/go-previous.png" alt="tilbake" title="tilbake"/>
        Tilbake til ordre nummer <%= customerOrder.getId() %>
      </a>
      <a href="newOrderItem.jsp?<%= view.getURIFragment(customerOrder)
                                 + "&" + view.getURIFragment(orderItem)%>">
        <img src="graphics/accessories-text-editor.png"
             alt="edit"
        />
        Rediger ordre element
      </a>
      <a href="deleteOrderItem.jsp?<%= view.getURIFragment(customerOrder)
                                       + "&"
                                       + view.getURIFragment(orderItem)%>">

        <img src="graphics/edit-delete.png"
             alt="delete"
        />
        Slett ordre element
      </a>
    </div>
    <h2>
      Ordreelement nummer <%= orderItem.getId() %>
      for ordre nummer <%= customerOrder.getId() %>
    </h2>
    <div id="order-item-info">
      Varenummer: <%= orderItem.getProduct().getId() %><br/>
      Betegnelese: <%= orderItem.getProduct().getName() %><br/>
      Antall: <%= orderItem.getNumberOfItems() %><br/>
      Pris: <%= StringUtil.print(orderItem.getProduct().getPrice()) %><br/>
      Rabatt: <%= orderItem.getDiscount() %><br/>
      Bel&oslash;p:
      <%= StringUtil.print(view.getOldTotalAmount(orderItem)) %><br/>
      Kommentar: <pre><%= StringUtil.print(orderItem.getComment()) %></pre>
    </div>
<%
}
%>    
  </div>
</div>
<jsp:include page="menu.jspf" />
<jsp:include page="footer.jsp" />


<%@ page import="java.util.*,
                 no.studios.atelier.util.*,
                 no.studios.atelier.view.*,
                 no.studios.atelier.model.*" %><%--
$Id: viewOrderItemInList.jsp,v 1.1 2008/03/31 11:21:39 torstein Exp $                 
--%><%
OrderItemView view = new OrderItemView();
OrderItem orderItem = view.getOrderItem(request, true);
%>
        <tr>
          <td>
            <a href="newOrderItem.jsp?<%= view.getURIFragment(orderItem) %>">
              <%= orderItem.getId() %>
            </a>
          </td>
          <td>
            <%= orderItem.getProduct().getId() %>
          </td>
          <td><%= view.print(orderItem.getProduct().getName()) %></td>
          <td><%= orderItem.getNumberOfItems() %></td>
          <td>
            <%= view.print(orderItem.getProduct().getPrice()) %>
          </td>
          <td><%= view.print(orderItem.getDiscount()) %></td>
          <td>
            <%= view.print(orderItem.getTotalAmount()) %>
          </td>
          <td>
<pre>
<%= view.print(orderItem.getComment()) %>
</pre>
          </td>
        </tr>

<%@ page import="java.util.*,
                 no.studios.atelier.util.*,
                 no.studios.atelier.view.*,
                 no.studios.atelier.model.*"
%><%--
  
 Used for both creating new order items and editing existing ones.

 $Id: newOrderItem.jsp,v 1.4 2008/11/08 11:51:49 torstein Exp $

--%><%
CustomerOrderView orderView = new CustomerOrderView();
CustomerOrder customerOrder = null;

OrderItemView view = new OrderItemView();
// are we working on an existing order item?
OrderItem orderItem = view.getOrderItem(request);
if (orderItem != null)
{
  customerOrder =
    orderView.getCustomerOrder(orderItem.getOrderId());
}
else
{
  customerOrder = orderView.getCustomerOrder(request);
}

ProductView productView = new ProductView();
List<Product> productList = productView.getAllProducts();

%>
<jsp:include page="header.jspf" />
<div id="main">
<%
if (orderItem != null)
{ %>
  <h1>Rediger ordre element nummer <%= orderItem.getId() %></h1><%
}
else
{ %>   
  <h1>
    Legg ny vare til ordre nummer
    <%= customerOrder != null ? customerOrder.getId() : "" %>
  </h1><%
}
%>
  <div id="content">
    <div id="toolbar">
      <a href="viewOrder.jsp?<%= view.getURIFragment(customerOrder) %>">
        <img src="graphics/go-previous.png" alt="back" title="tilbake"/>
        Avbryt, g&aring; tilbake
      </a>
    </div>
    <form action="<%= view.getFormURI(request,
                      AtelierEntity.URI_ORDER_ITEM, orderItem) %>"
          method="get">
      <div>
        <input name="<%= AtelierEntity.KEY_ORDER_ID %>"
               value="<%= customerOrder.getId() %>"
               type="hidden"
        />
<% if (orderItem != null)
   { %>
        <input name="<%= AtelierEntity.KEY_ORDER_ITEM_ID %>"
               value="<%= orderItem.getId() %>"
               type="hidden"
        />
<% } %>
        Vare:
        <select name="<%= AtelierEntity.KEY_PRODUCT_ID %>">
<%
for (Product product : productList)
{
%>
          <option value="<%= product.getId() %>"<%
  if (orderItem != null &&
      orderItem.getProduct() != null &&
      orderItem.getProduct().getId() == product.getId())
  {
    %> selected<%
  }
         %>>
            <%= view.print(product.getName())
              + " - "
              + view.print(product.getPrice())
              + "kr"
            %>
          </option>
<%
}
%>
        </select>
        <br/>
        Antall:
        <input
            name="<%= AtelierEntity.KEY_NUMBER_OF_ITEMS %>"
            value="<%= view.getNumberOfItems(orderItem) %>"
        />
        <br/>
        Rabatt:
        <input
            onblur="validate(this)"
            name="<%= AtelierEntity.KEY_DISCOUNT %>"
            value="<%= view.getDiscount(orderItem) %>"
        />
        <br/>
        Bel&oslash;p
        <input
            onblur="validate(this)"
            name="<%= AtelierEntity.KEY_TOTAL_AMOUNT %>"
            value="<%= view.print(view.getOldTotalAmount(orderItem)) %>"
        />
        (normalt skrives ikke noe her, det kalkuleres automatisk)
        <br/>
        Kommentar:
        <textarea
            rows="2"
            cols="40"
            name="<%= AtelierEntity.KEY_COMMENT %>"
        ><%= StringUtil.print(view.getComment(orderItem)) %></textarea>
      </div>
      <div>
        <input
            type="submit"
            value="<%= view.getSubmitLabel(orderItem) %>"
        />
      </div>
    </form>
  </div>
</div>
<jsp:include page="menu.jspf" />
<jsp:include page="footer.jsp" />

<%@ page import="java.util.*,
                 no.studios.atelier.util.*,
                 no.studios.atelier.view.*,
                 no.studios.atelier.model.*"
%><%--
  
 Used for both creating new orders and editing existing ones.

 $Id: newOrder.jsp,v 1.7 2008/11/08 11:51:49 torstein Exp $

--%><%

CustomerOrderView customerOrderView = new CustomerOrderView();
ProductView productView = new ProductView();
PaymentTypeView paymentTypeView = new PaymentTypeView();
CustomerView customerView = new CustomerView();
Customer customer = null;
CustomerOrder customerOrder = customerOrderView.getCustomerOrder(request);

// if an existing customer order, there's also an existing customer
if (customerOrder != null)
{
  customer = customerOrder.getCustomer();
}
else
{
  customer = customerView.getCustomer(request);
}

List<PaymentType> paymentTypeList = paymentTypeView.getAllPaymentTypes();
%>
<jsp:include page="header.jspf" />
<div id="main">
<%
if (customerOrder != null)
{ %>
  <h1>Rediger ordre nummer: <%= customerOrder.getId() %></h1><%
}
else
{ %>   
  <h1>Ny ordre</h1><%
}
%>
  <div id="content">
    <div id="toolbar">
      <a href="javascript:history.go(-1)">
        <img src="graphics/go-previous.png" alt="tilbake" title="tilbake"/>
        Avbryt, g&aring; tilbake
      </a>
    </div>
<%
if (customerOrderView.hasNewOrderFailed(request))
{
%>
     <p class="falied">
       Orderen ble ikke opprettet :-(
     </p><%
}

if (customer != null)
{ %>
    <form action="<%= request.getContextPath()
                     + "/"
                     + CustomerOrder.URI_ORDER
                     + "/"
                     + (customerOrder != null ?
                        CustomerOrder.URI_EDIT :
                        CustomerOrder.URI_NEW)
                   %>"
          method="get">
      <div>
        Kundenummer: <%= customer.getId() %>
        <input name="<%= Customer.KEY_CUSTOMER_ID %>"
               value="<%= customer.getId() %>"
               type="hidden"
        /><br/>
        Navn:
        <a href="viewCustomer.jsp?id=<%= customer.getId() %>">
          <%= StringUtil.print(customer.getFirstName()) 
                    + " "
                    + StringUtil.print(customer.getLastName())
          %>
        </a>
        <br/>
        Adresse: <%= StringUtil.print(customer.getAddress()) %>
        <%= StringUtil.print(customer.getPostCode()) %>
        <%= StringUtil.print(customer.getPostPlace()) %>
      </div>
      <div>
        Betaling:
        <select name="<%= CustomerOrder.KEY_PAYMENT_TYPE_ID %>">
<%
  for (PaymentType paymentType : paymentTypeList)
  { %>
          <option value="<%= paymentType.getId() %>"<%
    if (customerOrder != null &&
        customerOrder.getPaymentType() != null &&
        customerOrder.getPaymentType().getId() == paymentType.getId())
    {
        %> selected<%
    }
        %>>
            <%= paymentType.getName() %>
          </option>
<%}%>
        </select>
        <br/>
        Status:
        <select name="<%= OrderStatus.KEY_STATUS_ID %>">
<%
  OrderStatusView orderStatusView = new OrderStatusView();
  List<OrderStatus> orderStatusList = orderStatusView.getAllOrderStatus();

  for (OrderStatus orderStatus : orderStatusList)
  {
%>
          <option value="<%= orderStatus.getId() %>"<%
    if (customerOrder != null &&
        customerOrder.getOrderStatus() != null &&
        customerOrder.getOrderStatus().getId() == orderStatus.getId())
    {
        %> selected<%
    }
        %>>
            <%= orderStatus.getName() %>
          </option>
<%
  }
%>
        </select>
        <br/>
        Betalt:
        <input
            onblur="validate(this)"
            name="<%= CustomerOrder.KEY_PAID_AMOUNT %>"<%
if (customerOrder != null)
{ %>
               value="<%= Double.toString(customerOrder.getPaidAmount()) %>"<%
} %>
        />
        <br/>
        Kan brukes i avis:
        <input
            type="checkbox"
            value="true"
            name="<%= CustomerOrder.KEY_ALLOWED_IN_NEWSPAPER %>"<%
if (customerOrder != null && customerOrder.getAllowedUsedInNewspaper())
{ %>
               checked<%
} %>/>
        <br/>
        Kan brukes i markedsf&oslash;ring:
        <input
            type="checkbox"
            value="true"
            name="<%= CustomerOrder.KEY_ALLOWED_FOR_MARKETING %>"<%
if (customerOrder != null && customerOrder.getAllowedUsedForMarketing())
{ %>
               checked<%
} %>/>
<%
if (customerOrder != null)
{
%>
        <input
          type="hidden"
          name="<%= CustomerOrder.KEY_ORDER_ID %>"
          value="<%= customerOrder.getId() %>"
        />
<%
}
%>
      </div>
      <div>
        <input type="submit"
               value="<%= customerOrder != null ? "Endre" : "Bestill" %>"
        />
      </div>
    </form>
<%
}
else
{
%>
    <p>
      Du m&aring; <a href="searchCustomer.jsp">velge en kunde</a>
      f&oslash;rst.
    </p>
<%
}
%>
  </div>
</div>
<jsp:include page="menu.jspf" />
<jsp:include page="footer.jsp" />

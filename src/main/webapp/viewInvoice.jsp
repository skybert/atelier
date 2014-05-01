<%@ page import="java.util.*,
                 no.studios.atelier.util.*,
                 no.studios.atelier.view.*,
                 no.studios.atelier.model.*" %><%--
$Id: viewInvoice.jsp,v 1.8 2008/12/14 16:43:14 torstein Exp $                 
--%>
<%
InvoiceView view = new InvoiceView(request);
Invoice invoice = view.getInvoice();
CustomerOrderView orderView = new CustomerOrderView();
PostPlaceView postView = new PostPlaceView();
%>
<jsp:include page="header.jspf" />
    <div id="main">
      <h1>Faktura</h1>
      <div id="content">
        <jsp:include page="toolbar.jsp">
          <jsp:param name="toolbar-type" value="toolbar-invoice" />
        </jsp:include>
        <div id="company-contact-info">
          Telefon: 69 15 57 60<br/>
          Bankgiro: <%= View.BANK_ACCOUNT %><br/>
          Epost: fotografen@studio-s.no<br/>
          Foretaksnr: <%= View.COMPANY_NUMBER %><br/>
        </div>
        <div id="company-address">
          Studio S, fotografmester Terje Sten Johansen<br/>
          Glengsgaten 20<br/>
          Postboks 236<br/>
          1702 Sarpsborg
        </div>
        <hr/>
<%
if (invoice != null)
{
  CustomerOrder customerOrder = invoice.getOrder();
  // customerOrder should never be null
  Customer customer = customerOrder.getCustomer();
%>
    <div id="invoice-info">
      Fakturanr: <%= invoice.getId() %><br/>
      Ordrenr:
      <a href="viewOrder.jsp?<%= view.getURIFragment(customerOrder) %>">
        <%= customerOrder.getId() %>
      </a><br/>
      Faktura dato: <%= view.print(invoice.getCreationDate()) %><br/>
      Forfall: <%= view.print(invoice.getDueDate()) %>
    </div>
    <div id="customer-info">
      Kundenr: <%= customer.getId() %><br/>
      <a href="viewCustomer.jsp?<%= view.getURIFragment(customer) %>">
        <%= view.print(customer.getLastName()) %>,
        <%= view.print(customer.getFirstName()) %>
      </a>
      <br/>
      <%= view.print(customer.getAddress()) %>
      <br/>
      <%= view.print(customer.getPostCode()) %>
      <%= view.print(postView.getPostPlace(customer.getPostCode())) %>
    </div>
    <hr/>
    <table id="order-items">
      <tr>
        <th>Odreel. nr</th>
        <th>Varenr.</th>
        <th>Betegnelse</th>
        <th>Antall</th>
        <th>Pris</th>
        <th>Rabatt</th>
        <th>Bel&oslash;p</th>
        <th>Kommentar</th>
      </tr>
<%
for (OrderItem item : customerOrder.getOrderItems())
{
  request.setAttribute(OrderItem.class.getName(), item);
%>
  <jsp:include page="viewOrderItemInList.jsp"/>
<%
}
%>
    </table>
    <hr/>
    <div id="invoice-summary">
      <span id="invoice-summary-sum">
        Sum:
        <%= view.print(view.getOrderPrice(customerOrder)) %>
      </span>
      <span id="invoice-summary-tax">
        <%= Double.toString(view.TAX) %>% mva:
        <%= view.print(view.getTax(invoice)) %>
      </span>
      <span id="invoice-summary-total">
        Total: <%= view.print(view.getInvoicePrice(invoice)) %>
      </span>
    </div>
    <div id="invoice-footer">
      <div id="invoice-footer-header">
        <span id="invoice-footer-header-account-number">
          8215 02 00853
        </span>
        <span id="invoice-footer-header-total-amount">
          <%= view.print(view.getInvoicePrice(invoice)) %>
        </span>
      </div>
      <div id="invoice-footer-header-date">
        <%= view.print(invoice.getDueDate()) %>
      </div>
      <div id="invoice-footer-ids">
        Ordrenr: <%= customerOrder.getId() %><br/>
        Fakturanr: <%= invoice.getId() %><br/>
        Kundenr: <%= view.print(customer.getId()) %>
      </div>
      <div id="invoice-footer-customer">
        <%= view.print(customer.getFirstName()) %>
        <%= view.print(customer.getLastName()) %>
        <br/>
        <%= view.print(customer.getAddress()) %>
        <br/>
        <%= view.print(customer.getPostCode()) %>
        <%= view.print(postView.getPostPlace(customer.getPostCode())) %>
      </div>
      <div id="invoice-footer-recipient">
        Studio S,<br/>
        Fotografmester Terje S. Johansen A/S<br/>
        Glengsgaten 20, Postboks 236<br/>
        1702 Sarpsborg
      </div>
      <div id="invoice-footer-footer">
        <span id="invoice-footer-footer-total-amount">
          <%= view.print(view.getInvoicePrice(invoice), true) %>
        </span>
        <span id="invoice-footer-footer-total-amount-decimals">
          00
        </span>
        <span id="invoice-footer-footer-account-number">
          <%= View.BANK_ACCOUNT %>
        </span>
      </div>
    </div>
<%
}
// if no invoice ...
%>
    </div>
  </body>
</html>  
<%--
<jsp:include page="footer.jsp" />
--%>
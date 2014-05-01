<%@ page import="java.util.*,
                 no.studios.atelier.util.*,
                 no.studios.atelier.view.*,
                 no.studios.atelier.model.*"
%><%--
  
 Used for both creating new invoices and editing existing ones.

 $Id: newInvoice.jsp,v 1.6 2008/10/11 19:17:48 torstein Exp $

--%><%
InvoiceView view = new InvoiceView(request);
Invoice invoice = view.getInvoice();
CustomerOrder order = null;

// if editing an existing invoice
if (invoice != null)
{
  order = invoice.getOrder();
}
// if creating a new invoice
else
{
  order = view.getCustomerOrder(request);
}
%>
<jsp:include page="header.jspf" />
    <div id="main">
      <h1>Faktura</h1>
      <jsp:include page="toolbar.jsp">
        <jsp:param name="toolbar-type" value="toolbar-cancel" />
      </jsp:include>
      <div id="content">
        Lager faktura av ordre nummer:
        <a href="viewOrder.jsp?<%= view.getURIFragment(order) %>">
          <%= order.getId() %>
        </a>
        <form
            action="<%= view.getFormURI(request,
                                        AtelierEntity.URI_INVOICE,
                                        invoice) %>"
            method="get">
           Forfall:
           <input name="<%= Customer.KEY_DUE_DATE %>"
                  value="<%= view.print(view.getDueDate(invoice)) %>"
                  size="10"
           /><br/>
           Moms inkludert:
           <input type="checkbox"
                  name="<%= Invoice.KEY_TAX_INCLUDED %>"
<% if (invoice != null && invoice.getTaxIncluded()) { %>checked<% } %>
           />
           <input
               type="hidden"
               name="<%= Invoice.KEY_ORDER_ID %>"
               value="<%= order.getId() %>"
           />
           <input
               type="hidden"
               name="<%= Invoice.KEY_INVOICE_ID %>"
               value="<%= invoice != null ? invoice.getId() : "-1" %>"
           />
           <br/>
           <input type="submit"
             value="<%= invoice != null ? "Endre" : "Bestill" %>"
            />
         </form>
      </div>
    </div>
<jsp:include page="menu.jspf" />
<jsp:include page="footer.jsp" />

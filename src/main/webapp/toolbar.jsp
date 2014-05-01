<%@ page import="java.util.*,
             no.studios.atelier.util.*,
             no.studios.atelier.view.*,
             no.studios.atelier.model.*"
%>
        <div id="toolbar">
<%
String type = request.getParameter(AtelierEntity.URI_TOOLBAR_TYPE);

if (AtelierEntity.TOOLBAR_TYPE_CANCEL.equals(type))
{
%>
          <a href="javascript:history.go(-1)">
            <img src="graphics/go-previous.png" alt="tilbake" title="tilbake"/>
            Avbryt, g&aring; tilbake
          </a>
<%
}
else if (AtelierEntity.TOOLBAR_TYPE_INVOICE_LIST.equals(type))
{
%>
          <a href="javascript:history.go(-1)">
            <img src="graphics/go-previous.png" alt="tilbake" title="tilbake"/>
            G&aring; tilbake
          </a>
          <a href="javascript:window.print()">
            <img src="graphics/printer.png" alt="print" title="Skriv ut" />
            Skriv ut fakturaliste
          </a>
<%
}
else if (AtelierEntity.TOOLBAR_TYPE_INVOICE.equals(type))
{
  InvoiceView view = new InvoiceView(request);
  Invoice invoice = view.getInvoice();
%>
<%
  if (invoice != null)
  {%>
          <a href="<%= URIBuilder.getViewURI((CustomerOrder) invoice.getOrder()) %>">
            <img src="graphics/go-previous.png" alt="tilbake" title="tilbake"/>
            Tilbake til ordre nr <%= invoice.getOrder().getId() %>
          </a>
          <a href="<%= URIBuilder.getNewURI(invoice) %>">
            <img src="graphics/accessories-text-editor.png"
                 alt="edit"
            />
            Rediger faktura
          </a>
          <a href="<%= URIBuilder.getDeleteURI(invoice) %>">
            <img src="graphics/edit-delete.png"
                 alt="delete"
           />
           Slett faktura
         </a>
<%}%>
         <a href="javascript:window.print()">
            <img src="graphics/printer.png" alt="print" title="Skriv ut" />
            Skriv ut faktura
         </a>
<%
}
else if (AtelierEntity.TOOLBAR_TYPE_ORDER.equals(type))
{
  CustomerOrderView view = new CustomerOrderView();
  CustomerOrder customerOrder = view.getCustomerOrder(request);

  if (customerOrder == null)
  {
    return;
  }

  Customer customer = customerOrder.getCustomer();
%>
          <a href="newOrder.jsp?<%= view.getURIFragment(customer) %>">
            <img src="graphics/document-new.png" alt="new" title="Ny" />
            Ny ordre
          </a>
          <a href="<%= URIBuilder.getNewURI(customerOrder) %>">
            <img src="graphics/accessories-text-editor.png"
                 alt="edit"
            />
            Rediger ordre
          </a>
          <a href="<%= URIBuilder.getDeleteURI(customerOrder) %>">
            <img src="graphics/edit-delete.png"
                 alt="delete"
            />
            Slett ordre
          </a>
<%
int invoiceId = view.getInvoiceId(customerOrder);
%>
          <a href="<%= URIBuilder.getInvoiceURI(customerOrder, invoiceId) %>">
            <img src="graphics/accessories-calculator.png"
                 alt="invoice"
              title="faktura"
            />
<% if (invoiceId == -1)
   { %>
            Lag faktura
<% }
   else
   { %>
            Se faktura
<% } %>
          </a>
          <a href="printOrder.jsp?<%= view.getURIFragment(customerOrder) %>">
            <img src="graphics/printer.png" alt="print"
              title="Skriv ut"
            />
            Skriv ut ordre
          </a>
<%
}
else
{
%>
          <h2>Unknown type=<%= type %></h2>
<%
}
%>
          <a href="doc/user-guide.html" target="help-window">
            <img src="graphics/help-browser.png" alt="help"
                 title="Hjelp" />
            Hjelp
          </a>
        </div>


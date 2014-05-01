<%@ page import="java.util.*,
                 no.studios.atelier.util.*,
                 no.studios.atelier.view.*,
                 no.studios.atelier.model.*" %><%--
$Id: viewInvoiceList.jsp,v 1.2 2008/11/08 18:31:47 torstein Exp $                 
--%>
<%
InvoiceView view = new InvoiceView(request);
CustomerOrderView orderView = new CustomerOrderView(request);
Date fromDate = view.getDate(Invoice.KEY_FROM_DATE);
Date toDate = view.getDate(Invoice.KEY_TO_DATE);
%>
<jsp:include page="header.jspf" />
    <div id="main">
<%
  if (fromDate != null && toDate != null)
  { %>
      <h1>
        Faktura oversikt fra <%= view.print(fromDate) %>
        til <%= view.print(toDate) %>
      </h1>
<% }
   else
   { %>
      <h1>Faktura oversikt</h1>
<%
// if there's no from and to date, we'll set a default of the last month.
Calendar calendar = Calendar.getInstance();
calendar.roll(Calendar.MONTH, -1);
fromDate = calendar.getTime();
toDate = new Date();
   } %>
      <div id="content">
      <jsp:include page="toolbar.jsp">
        <jsp:param name="toolbar-type" 
                   value="<%= AtelierEntity.TOOLBAR_TYPE_INVOICE_LIST %>" 
        />
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
      <form action="viewInvoiceList.jsp" method="get">
        <div>
          Fra dato:
          <input
              onblur="validate(this)"
              name="<%= Invoice.KEY_FROM_DATE %>"
              value="<%= view.print(fromDate) %>"
              size="10"
          />
          Til dato:
          <input
              onblur="validate(this)"
              name="<%= Invoice.KEY_TO_DATE %>"
              value="<%= view.print(toDate) %>"
              size="10"
          />
          <input type="submit" value="Sl&aring; opp"/>
          <br/>
          (datoformatet er dd.mm.yyyy, f.eks. 29.12.2007)
        </div>
      </form>
<%
if (fromDate != null && toDate != null)
{
  List<Invoice> invoiceList = view.getInvoiceList(fromDate, toDate);

  if (invoiceList.size() > 0)
  { %>
      <table>
        <tr>
          <th>Fakturanr.</th>
          <th>Navn</th>
          <th>Fakturadato</th>
          <th>Forfallsdato</th>
          <th>Sum</th>
          <th>Moms</th>
          <th>Total</th>
        </tr>
<%}
  for (Invoice invoice : invoiceList)
  {
    CustomerOrder customerOrder = invoice.getOrder();
    // customerOrder should never be null
    Customer customer = customerOrder.getCustomer();

%>
        <tr>
          <td>
            <a href="viewInvoice.jsp?<%= view.getURIFragment(invoice) %>">
              <%= invoice.getId() %>
            </a>
          </td>
          <td>
            <a href="viewCustomer.jsp?<%= view.getURIFragment(customer) %>">
              <%= view.printName(customer) %>
            </a>
          </td>
          <td><%= view.print(invoice.getCreationDate()) %></td>
          <td><%= view.print(invoice.getDueDate()) %></td>
          <td class="right"><%= view.print(view.getOrderPrice(customerOrder)) %></td>
          <td class="right"><%= view.print(view.getTax(invoice)) %></td>
          <td class="right"><%= view.print(view.getInvoicePrice(invoice)) %></td>
        </tr>
<%
  } 
%>
<%
  if (invoiceList.size() > 0)
  { %>
         <tr>
           <td>Sum total</td>
           <td></td>
           <td></td>
           <td></td>
           <td></td>
           <td></td>
           <td class="right">
             <%= view.print(view.getInvoicePrice(invoiceList)) %>
           </td>
         </tr>
       </table>
<%}
}
%>
    </div>
<jsp:include page="menu.jspf" />
<jsp:include page="footer.jsp" />


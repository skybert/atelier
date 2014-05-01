<%@ page import="java.util.*,
                 no.studios.atelier.util.*,
                 no.studios.atelier.view.*,
                 no.studios.atelier.model.*" %>
<jsp:include page="header.jspf" />
<div id="main">
  <h1>Faktura</h1>
  <div id="content">
    <div id="toolbar">
      <jsp:include page="goBack.jspf"/>
    </div>
<%
InvoiceView view = new InvoiceView();
Invoice invoice = view.getInvoice(request);

if (invoice != null) 
{
%>
    <h2>Slette faktura?</h2>
    <p>
      <img src="graphics/dialog-warning.png" alt="advarsel"
           title="advarsel"
      /> 
      Vil du virkelig slette faktura nummer
      <%= Integer.toString(invoice.getId()) %>?
    </>
    <p>
      <a href="<%= request.getContextPath()
                   + "/" + Invoice.URI_INVOICE
                   + "/" + Invoice.URI_DELETE
                   + "?" + view.getURIFragment(invoice)
                   %>"
      >
        Ja, jeg vil slette fakturaen
      </a>
      &nbsp;~&nbsp;
      <a href="javascript:history.go(-1)">Nei, jeg vil avbryte</a>
    </p>
<%
}
%>
    <!-- id=content -->
  </div>
  <!-- id=main -->
</div>
<jsp:include page="menu.jspf" />
<jsp:include page="footer.jsp" />

<%@ page import="java.util.*,
                 no.studios.atelier.util.*,
                 no.studios.atelier.view.*,
                 no.studios.atelier.model.*" %>
<jsp:include page="header.jspf" />
<div id="main">
  <h1>Ordre</h1>
  <div id="content">
    <div id="toolbar">
      <jsp:include page="goBack.jspf"/>
    </div>
<%
CustomerOrderView view = new CustomerOrderView();
CustomerOrder customerOrder = view.getCustomerOrder(request);

if (customerOrder != null) 
{
%>
    <h2>Slette ordre?</h2>
    <p>
      <img src="graphics/dialog-warning.png" alt="advarsel"
           title="advarsel"
      /> 
      Vil du virkelig slette ordrenummer
      <%= Integer.toString(customerOrder.getId()) %>?
    </>
    <p>
      <a href="<%= request.getContextPath()
                   + "/" + CustomerOrder.URI_ORDER
                   + "/" + CustomerOrder.URI_DELETE
                   + "?" + Customer.KEY_CUSTOMER_ID
                   + "=" + customerOrder.getCustomer().getId()
                   + "&" + CustomerOrder.KEY_ORDER_ID
                   + "=" + customerOrder.getId()
                   %>"
      >
        Ja, jeg vil slette ordren
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

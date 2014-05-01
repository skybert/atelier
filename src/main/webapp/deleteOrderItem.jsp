<%@ page import="java.util.*,
                 no.studios.atelier.util.*,
                 no.studios.atelier.view.*,
                 no.studios.atelier.model.*" %>
<%
OrderItemView view = new OrderItemView();
OrderItem orderItem = view.getOrderItem(request);
%>
<jsp:include page="header.jspf" />
<div id="main">
  <h1>Ordre element</h1>
  <div id="content">
    <div id="toolbar">
      <jsp:include page="goBack.jspf"/>
    </div>
<%
if (orderItem != null) 
{
%>
    <h2>Slette vare fra ordre?</h2>
    <p>
      <img src="graphics/dialog-warning.png" alt="advarsel"
           title="advarsel"
      /> 
      Vil du virkelig slette vare
      <%= "#" + orderItem.getId()
          + " - " + orderItem.getProduct().getName()
      %>?
    </>
    <p>
      <a href="<%= request.getContextPath()
                   + "/" + OrderItem.URI_ORDER_ITEM
                   + "/" + OrderItem.URI_DELETE
                   + "?" + Customer.KEY_ORDER_ITEM_ID
                   + "=" + orderItem.getId()
                   + "&"
                   + Customer.KEY_ORDER_ID
                   + "=" + orderItem.getOrderId()
                   %>"
      >
        Ja, jeg vil slette varen fra ordren
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

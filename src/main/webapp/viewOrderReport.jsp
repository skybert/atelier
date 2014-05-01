<%@ page import="java.util.*,
                 no.studios.atelier.util.*,
                 no.studios.atelier.view.*,
                 no.studios.atelier.model.*" %><%--
$Id: viewOrderReport.jsp,v 1.1 2008/01/20 22:26:52 torstein Exp $                 
--%>
<%
CustomerOrderView view = new CustomerOrderView();
CustomerOrder customerOrder = view.getCustomerOrder(request);
PostPlaceView postView = new PostPlaceView();
%>
<jsp:include page="header.jspf" />
<div id="main">
  <h1>Ordre rapport</h1>
  <div id="content">
  </div>
</div>
<jsp:include page="menu.jspf" />
<jsp:include page="footer.jsp" />

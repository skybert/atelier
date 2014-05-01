<%@ page import="java.util.*,
                 no.studios.atelier.data.*,
                 no.studios.atelier.model.*" %>
<jsp:include page="header.jspf" />
<%
ObjectFactory objectFactory = new DefaultObjectFactory();
List<Product> productList = objectFactory.getAllProducts();
%>
<h1>Produkter</h1>
<table>
  <tr>
    <th>Produkt ID</th>
    <th>Navn</th>
    <th>Pris</th>
    <th>Leveringstid</th>
    <th>Type</th>
  </tr>
<%
  for (Product product : productList) {
%>
  <tr>
    <td>
      <%= Integer.toString(product.getId()) %>
    </td>
    <td>
      <%= product.getName() %>
    </td>
    <td>
      <%= Long.toString(product.getPrice()) %>
    </td>
    <td>
      <%= Integer.toString(product.getProductionTime()) %>
    </td>
    <td>
      <%= product.getProductType() %>
    </td>
  </tr>
<%
}
%>  
</table>

<jsp:include page="menu.jspf" />
<jsp:include page="footer.jsp" />

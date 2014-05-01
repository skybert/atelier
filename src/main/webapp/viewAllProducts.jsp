<%@ page import="java.util.*,
                 no.studios.atelier.util.*,
                 no.studios.atelier.view.*,
                 no.studios.atelier.model.*"
%><%--
  
 Used Listing all products.

 $Id: viewAllProducts.jsp,v 1.4 2008/01/31 17:40:57 torstein Exp $

--%><%
ProductView productView = new ProductView();
List<Product> productList = productView.getAllProducts();
%>
<jsp:include page="header.jspf" />
<div id="main">
  <h1>Produkter</h1>
  <div id="content">
    <div id="toolbar">
      <a href="newProduct.jsp">
        <img src="graphics/document-new.png" alt="new"
          title="Nytt produkt"
        />
        Nytt produkt
      </a>
    </div>
    <table>
      <tr>
        <th>ID</th>
        <th>Produktnavn</th>
        <th>Pris</th>
        <th>Leveringstid</th>
        <th>Produkttype</th>
      </tr>
<%
for (Product product : productList)
{
%>
      <tr>
        <td>
          <a href="viewProduct.jsp?<%=
            Customer.KEY_PRODUCT_ID + "=" + product.getId() %>">
            <%= product.getId() %>
          </a>
        </td>
        <td><%= StringUtil.print(product.getName()) %></td>
        <td><%= StringUtil.print(product.getPrice()) %> kr</td>
        <td><%= product.getProductionTime() %> dager</td>
        <td><%= StringUtil.print(product.getProductType().getName()) %></td>
      </tr>
<%
}
%>
    </table>
  </div>
</div>
<jsp:include page="menu.jspf" />
<jsp:include page="footer.jsp" />



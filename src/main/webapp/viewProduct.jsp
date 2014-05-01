<%@ page import="java.util.*,
                 no.studios.atelier.util.*,
                 no.studios.atelier.view.*,
                 no.studios.atelier.model.*" %>
<jsp:include page="header.jspf" />
<div id="main">
  <h1>Produkt</h1>
  <div id="content">
<%
ProductView view = new ProductView();
Product product = view.getProduct(request);

if (product != null) 
{
%>
    <div id="toolbar">
      <a href="newProduct.jsp">
        <img src="graphics/document-new.png" alt="new"
          title="Nytt produkt"
        />
        Nytt produkt
      </a>
      <a href="newProduct.jsp?<%=
        Product.KEY_PRODUCT_ID + "=" + product.getId() %>">
        <img src="graphics/accessories-text-editor.png"
             alt="edit"
        />
        Rediger produkt
      </a>
      <!--
      <a href="deleteProduct.jsp?<%=
        Product.KEY_PRODUCT_ID + "=" + product.getId() %>">
        <img src="graphics/edit-delete.png"
             alt="delete"
        />
        Slett produkt
      </a>
      -->
      <a href="viewAllProducts.jsp">
        <img src="graphics/system-search.png" alt="search"
          title="S&oslash;k etter produkt"
        />
        S&oslash;k etter produkt
      </a>
    </div>
    <div id="product-info">
      <h2>Produkt nummer <%= product.getId() %></h2>
      <p>
        Navn: <%= view.print(product.getName()) %><br/>
        Opprettet: <%= view.print(product.getCreationDate()) %><br/>
        Pris: <%= view.print(product.getPrice()) %> kr<br/>
        Leveringstid: <%= view.print(product.getProductionTime()) %> dager<br/>
        Produkttype: <%= product.getProductType().getName() %><br/>
        Beskrivelse: <%= view.print(product.getDescription()) %>
      </p>
<%
}
else
{
%>
    <p class="failure">
      Det finnes ingen produkt med produktnummer <%= view.getProductId(request) %>
    </p>
<%
}
%>
  </div>
</div>
<jsp:include page="menu.jspf" />
<jsp:include page="footer.jsp" />

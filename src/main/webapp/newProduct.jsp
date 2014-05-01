<%@ page import="java.util.*,
                 no.studios.atelier.util.*,
                 no.studios.atelier.view.*,
                 no.studios.atelier.model.*"
%><%
ProductView view = new ProductView();
Product product = view.getProduct(request);
List<ProductType> productTypeList = view.getAllProductTypes();
%>
<jsp:include page="header.jspf" />
<div id="main">
  <h1>Produkt</h1>
  <div id="content">
    <div id="toolbar">
      <a href="javascript:history.go(-1)">
        <img src="graphics/go-previous.png" alt="tilbake" title="tilbake"/>
        Avbryt, g&aring; tilbake
      </a>      
    </div>
<%
if (product != null)
{ %>
  <h2>Rediger produkt nummer <%= product.getId() %></h2><%
}
else
{ 
%>
  <h2>Nytt produkt</h2>
<% 
}
 %>
    <form action="<%= request.getContextPath()
                     + "/"
                     + Product.URI_PRODUCT
                     + "/"
                     + (product != null ?
                        Product.URI_EDIT :
                        Product.URI_NEW)
                   %>"
      method="get">
      <div>
        <% if (product != null)
           { %>
          Produktnummer: <%= product.getId() %>
          <br/>
          Registrert dato: <%= StringUtil.print(product.getCreationDate()) %>
          <br/>
          <input
              type="hidden"
              name="<%= Product.KEY_PRODUCT_ID %>"
              value="<%= product.getId() %>"
          />
        <% } %>
        Navn:
        <input
          name="<%= Product.KEY_NAME %>"
          value="<%= product != null ?
                     StringUtil.print(product.getName()) : "" %>"
        />
        <br/>
        Pris:
        <input
            onblur="validate(this)"
            name="<%= Product.KEY_PRICE %>"
            value="<%= product != null ?
                       StringUtil.print(product.getPrice()) : "" %>"
        />
        kr (f.eks. 2130.50)<br/>
        Beskrivelse:
        <input
          name="<%= Product.KEY_DESCRIPTION %>"
          value="<%= product != null ?
                     StringUtil.print(product.getDescription()) : "" %>"
        /><br/>
        Produkttype:
        <select name="<%= Product.KEY_PRODUCT_TYPE_ID %>">
<%
for (ProductType productType : productTypeList)
{
%>
          <option value="<%= productType.getId() %>"<%
  if (product != null &&
      product.getProductType() != null &&
      productType.getId() == product.getProductType().getId())
  {
    %> selected<%
  }
         %>>
            <%= Integer.toString(productType.getId())
              + " - "
              + productType.getName()
            %>
          </option>
<%
}
%>
        </select>
        <br/>
        Leveringstid:
        <input
          name="<%= Product.KEY_PRODUCTION_TIME %>"
          value="<%= product != null ?
                     view.print(product.getProductionTime()) : "" %>"
        />
        dager
      </div>
      <div>
        <input
            type="submit"
            value="<%= product != null ? "Endre" : "Registrer" %>"
        />
      </div>
    </form>
  </div>
</div>
<jsp:include page="menu.jspf" />
<jsp:include page="footer.jsp" />

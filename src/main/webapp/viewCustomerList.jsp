<%@ page import="java.util.*,
                 no.studios.atelier.util.*,
                 no.studios.atelier.view.*,
                 no.studios.atelier.model.*" %><%--
$Id: viewCustomerList.jsp,v 1.1 2008/10/18 10:29:09 torstein Exp $                 
--%>
<%
CustomerView view = new CustomerView(request);
CustomerOrderView orderView = new CustomerOrderView(request);
ProductView productView = new ProdutView(request);
List<ProductType> productTypeList = view.getAllProductTypes();
%>
<jsp:include page="header.jspf" />
    <div id="main">
      <div id="content">
      <form action="viewCustomerList.jsp" method="get">
        <div>
          Se alle kunder som har bestilt en
          <select name="<%= Product.KEY_PRODUCT_TYPE_ID %>">
<%
for (ProductType productType : productTypeList)
{
%>
          <option value="<%= productType.getId() %>">
            <%= Integer.toString(productType.getId())
              + " - "
              + productType.getName()
            %>
          </option>
<%
}
%>
          </select>
          men ingen av disse produktene:
<%
int productTypeId = 1;
List<Product> productList = view.getAllProductsOfType(productTypeId);
%>
          <select multiple size="<%= productList.size() %>">
<%
for (Product produt : productList)
{
%>
            <option value="<%= product.getId() %>">
              <%= product.getName() %>
            </option>
<%
}
%>
          </select>
          <input type="submit" value="Sl&aring; opp"/>
        </div>
      </form>
    </div>
<jsp:include page="menu.jspf" />
<jsp:include page="footer.jsp" />


<%@ page import="java.util.*,
                 no.studios.atelier.util.*,
                 no.studios.atelier.view.*,
                 no.studios.atelier.model.*" %><%--
$Id: viewPurchaseOfCertainProducts.jsp,v 1.3 2008/11/08 21:04:33 torstein Exp $                 
--%>
<%
ReportView view = new ReportView(request);
ProductView productView = new ProductView(request);
PostPlaceView postView = new PostPlaceView();

Date creationDate = view.getDate(Invoice.KEY_CREATION_DATE);
List<Product> productList = productView.getAllProducts();
List<Integer> productIdList = view.getIntList(Invoice.KEY_PRODUCT_ID);

if (creationDate == null)
{
// if there's no creation date, we'll set a default of the last month.
Calendar calendar = Calendar.getInstance();
calendar.roll(Calendar.MONTH, -1);
creationDate = calendar.getTime();
}
%>
<jsp:include page="header.jspf" />
    <div id="main">
      <h1>Oversikt over bestemte varekj&oslash;p</h1>
      <div id="content">
        <form action="viewPurchaseOfCertainProducts.jsp" method="get">
          <div style="float: right;">
            Kunder som har bestillt ett eller flere av f&oslash;lgende
            produkter:
            <br/>
            <select
                size="8"
                name="<%= Invoice.KEY_PRODUCT_ID %>"
                multiple>
  <%
  for (Product product : productList)
  {
  %>
              <option value="<%= product.getId() %>"
                      <%= productIdList.contains(product.getId()) ? "selected" : "" %>>
                <%= view.print(product.getName()) %> - <%= view.print(product.getPrice()) %>
              </option>
  <%
  }
  %>
            </select>
          </div>
          <div>
            Og hvor ordren ble opprettet etter:<br/>
            <input
                onblur="validate(this)"
                name="<%= Invoice.KEY_CREATION_DATE %>"
                value="<%= view.print(creationDate) %>"
                size="10"
            />
            <br/>
            (datoformatet er dd.mm.yyyy, f.eks. 29.12.2007)
            <br/>
            <br/>
            <input type="submit" value="Sl&aring; opp"/>
          </div>
        </form>
      </div>
<%
if (productIdList.size() > 0)
{
  List<Map> resultList = view.getOrderedCertainProducts(creationDate, productIdList);
%>
      <div id="results">
        <h1>S&oslash;ket ga <%= resultList.size() %> treff</h1>
<%
  if (resultList.size() > 0)
  {
%>
       <table>
<%
    for (Map<String, Object> row : resultList)
    {
%>
         <tr>
           <td>
             <a href="viewCustomer.jsp?<%= Customer.KEY_CUSTOMER_ID %>=<%=
               Integer.toString((Integer) row.get(view.DB_ID)) %>">
               <%= view.print((String) row.get(view.DB_FIRST_NAME))
                   + " "
                   + view.print((String) row.get(view.DB_LAST_NAME))
               %>
             </a>

           </td>
           <td><%= view.print(row.get(view.DB_ADDRESS)) %></td>
           <td><%= view.print(row.get(view.DB_POST_CODE)) %></td>
           <td>
            <%= view.print(postView.getPostPlace(row.get(view.DB_POST_CODE))) %>
           </td>
           <td>
             <a href="viewOrder.jsp?<%= CustomerOrder.KEY_ORDER_ID %>=<%=
               view.print(row.get(view.DB_ORDER_ID)) %>">
               ordrenr:
               <%= view.print(row.get(view.DB_ORDER_ID)) %>
               -
               betalt:
               <%= view.print(row.get(view.DB_PAID_AMOUNT)) %>
             </a>
           </td>
         </tr>
<%
    }
%>
       </table>
<%
  }
%>
      </div>
<%
}
%>
    </div>
<jsp:include page="menu.jspf" />
<jsp:include page="footer.jsp" />
    
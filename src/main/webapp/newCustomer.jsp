<%@ page import="java.util.*,
                 no.studios.atelier.util.*,
                 no.studios.atelier.view.*,
                 no.studios.atelier.model.*"
%><%
CustomerView view = new CustomerView();
PostPlaceView postPlaceView = new PostPlaceView();
Customer customer = view.getCustomer(request);
List<PostPlace> postPlaceList = postPlaceView.getAllPostPlaces();
%>
<jsp:include page="header.jspf" />
<div id="main">
  <h1>Kunde</h1>
  <div id="content">
    <div id="toolbar">
      <a href="javascript:history.go(-1)">
        <img src="graphics/go-previous.png" alt="tilbake" title="tilbake"/>
        Avbryt, g&aring; tilbake
      </a>
    </div>
<%
if (customer != null)
{ %>
  <h2>Rediger kunde nummer <%= customer.getId() %></h2><%
}
else
{
%>
  <h2>Ny kunde</h2>
<%
}
 %>
    <form action="<%= request.getContextPath()
                     + "/"
                     + Customer.URI_CUSTOMER
                     + "/"
                     + (customer != null ?
                        Customer.URI_EDIT :
                        Customer.URI_NEW)
                   %>"
      method="get">
      <div>
        <% if (customer != null)
           { %>
          Kundenummer: <%= customer.getId() %>
          <br/>
          Registrert dato: <%= view.print(customer.getCreationDate()) %>
          <br/>
          <input
              type="hidden"
              name="<%= Customer.KEY_CUSTOMER_ID %>"
              value="<%= customer.getId() %>"
          />
        <% } %>
        Fornavn:
        <input
          name="<%= Person.KEY_FIRST_NAME %>"
          value="<%= customer != null ?
                     view.print(customer.getFirstName()) : "" %>"
        /><br/>
        Etternavn:
        <input
          name="<%= Person.KEY_LAST_NAME %>"
          value="<%= customer != null ?
                     view.print(customer.getLastName()) : "" %>"
        />
        <br/>
        Adresse:
        <input
          name="<%= Person.KEY_ADDRESS %>"
          value="<%= customer != null ?
                     view.print(customer.getAddress()) : "" %>"
        /><br/>
        Postnummer:
        <select name="<%= Person.KEY_POST_CODE %>">
<%
for (PostPlace postPlace : postPlaceList)
{
%>
          <option value="<%= postPlace.getPostCode() %>"<%
  if (postPlace != null &&
      customer != null &&
      postPlace.getPostCode().equals(customer.getPostCode()))
  {
    %> selected<%
  }
         %>>
            <%= postPlace.getPostCode()
              + " - "
              + view.print(postPlace.getPostPlace())
            %>
          </option>
<%
}
%>
        </select>
        <%= customer != null ?
            view.print(customer.getPostPlace()) : "" %>
        <br/>
        Telefon hjem:
        <input
          name="<%= Person.KEY_HOME_PHONE %>"
          value="<%= customer != null ?
                     view.print(customer.getHomePhone()) : "" %>"
        /><br/>
        Telefon mobil:
        <input
          name="<%= Person.KEY_MOBILE_PHONE %>"
          value="<%= customer != null ?
                     view.print(customer.getMobilePhone()) : "" %>"
        /><br/>
        Telefon arbeid:
        <input
          name="<%= Person.KEY_WORK_PHONE %>"
          value="<%= customer != null ?
                     view.print(customer.getWorkPhone()) : "" %>"
        />
        <br/>
        Epost:
        <input
          name="<%= Person.KEY_EMAIL %>"
          type="email"
          value="<%= customer != null ?
                     view.print(customer.getEmailAddress()) : "" %>"
        />
        <br/>
        F&oslash;dselsdato:
        <input
            onblur="validate(this)"
            name="<%= Person.KEY_BIRTH_DATE %>"
            value="<%= customer != null ?
                     view.print(customer.getBirthDate()) : "" %>"
        />
        (datoformatet er: dd.mm.yyyy, f.eks. 29.12.2007)
      </div>
      <div>
        <input
            type="submit"
            value="<%= customer != null ? "Endre" : "Registrer" %>"
        />
      </div>
    </form>
  </div>
</div>
<jsp:include page="menu.jspf" />
<jsp:include page="footer.jsp" />

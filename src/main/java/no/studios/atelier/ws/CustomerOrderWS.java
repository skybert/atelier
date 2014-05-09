package no.studios.atelier.ws;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import no.studios.atelier.data.*;

/**
 * CustomerOrderWS
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@Path(WSConstants.PATH_CUSTOMER_ORDER)
@RequestScoped
public class CustomerOrderWS
{
  @PersistenceContext(unitName = DBConstants.ATELIER_PERSISTENCE_UNIT)
  private EntityManager entityManager;

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public CustomerOrder getCustomerOrder(@PathParam("id") Integer pId)
  {
    CustomerOrder customerOrder = entityManager.find(CustomerOrder.class, pId);

    System.out.println("customerOrder=" + customerOrder);

    return customerOrder;
  }

}

package no.studios.atelier.ws;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import no.studios.atelier.data.Customer;
import no.studios.atelier.data.DBConstants;

/**
 * CustomerWS
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@Path(WSConstants.PATH_CUSTOMER)
@RequestScoped
public class CustomerWS
{
  @PersistenceContext(unitName = DBConstants.ATELIER_PERSISTENCE_UNIT)
  EntityManager entityManager;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String ping()
  {
    System.out.println("entityManager=" + entityManager);

    return "Pong from " + getClass().getName();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Customer getCustomer(@PathParam("id") Integer pId)
  {
    return entityManager.find(Customer.class, pId);
  }

}

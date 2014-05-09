package no.studios.atelier.ws;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import no.studios.atelier.data.DBConstants;
import no.studios.atelier.data.Product;

/**
 * ProductWS
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@Path(WSConstants.PATH_PRODUCT)
@RequestScoped
public class ProductWS
{

  @PersistenceContext(unitName = DBConstants.ATELIER_PERSISTENCE_UNIT)
  private EntityManager entityManager;

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Product getProduct(@PathParam("id") Integer pId)
  {
    Product product = entityManager.find(Product.class, pId);
    System.out.println("product=" + product);
    return product;
  }

}

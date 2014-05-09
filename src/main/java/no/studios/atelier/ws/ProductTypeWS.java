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
import no.studios.atelier.data.ProductType;

/**
 * CustomerWS
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@Path(WSConstants.PATH_PRODUCT_TYPE)
@RequestScoped
public class ProductTypeWS
{
  @PersistenceContext(unitName = DBConstants.ATELIER_PERSISTENCE_UNIT)
  private EntityManager entityManager;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String ping()
  {
    return "Pong from " + getClass().getName();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public ProductType getProductType(@PathParam("id") Integer pId)
  {
    ProductType productType = entityManager.find(ProductType.class, pId);

    System.out.println("productType=" + productType);

    return productType;
  }

}

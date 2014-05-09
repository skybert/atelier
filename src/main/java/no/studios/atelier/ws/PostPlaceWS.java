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
import no.studios.atelier.data.PostPlace;

/**
 * PostPlaceWS
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@Path(WSConstants.PATH_POST_PLACE)
@RequestScoped
public class PostPlaceWS
{

  @PersistenceContext(unitName = DBConstants.ATELIER_PERSISTENCE_UNIT)
  private EntityManager entityManager;

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public PostPlace getPostPlace(@PathParam("id") Integer pId)
  {
    PostPlace postPlace = entityManager.find(PostPlace.class, pId);
    System.out.println("postPlace=" + postPlace);
    return postPlace;
  }

}

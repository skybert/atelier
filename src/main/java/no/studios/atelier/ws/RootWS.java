package no.studios.atelier.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * RootWS
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@Path(WSConstants.PATH_ROOT)
public class RootWS
{
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String sayPlainTextHello()
  {
    return "Hello from " + getClass().getName() + "\n";
  }

}

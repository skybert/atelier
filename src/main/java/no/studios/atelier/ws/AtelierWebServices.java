package no.studios.atelier.ws;

import java.util.Set;
import java.util.HashSet;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Registering the root context of all Atelier web services. Using this
 * approach, it's not necessary to set up WS scanning in <code>web.xml</code>.
 *
 * @author Torstein Krause Johansen
 * @version 1.0
 */
@ApplicationPath(WSConstants.PATH_WS_ROOT)
public class AtelierWebServices extends Application
{
  @Override
  public Set<Class<?>> getClasses()
  {
    HashSet<Class<?>> classes = new HashSet<Class<?>>();
    classes.add(CustomerOrderWS.class);
    classes.add(CustomerWS.class);
    classes.add(PostPlaceWS.class);
    classes.add(ProductTypeWS.class);
    classes.add(RootWS.class);
    return classes;
  }
}

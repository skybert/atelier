package no.studios.atelier.ws;

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

}

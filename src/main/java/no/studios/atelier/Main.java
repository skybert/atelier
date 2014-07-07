package no.studios.atelier;

import io.undertow.servlet.api.DeploymentInfo;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.test.TestPortProvider;
import no.studios.atelier.ws.AtelierWebServices;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Main class starting Atelier.
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
public class Main
{
  private final static int DEFAULT_PORT = 8080;
  private final static String DEFAULT_CONTEXT_PATH = "/";
  private UndertowJaxrsServer server;

  public Main()
  {
    server = new UndertowJaxrsServer();
    server.deploy(AtelierWebServices.class);
  }

  public static void main(String[] args) throws Exception
  {
    Main app = new Main();
    app.start();
  }

  private void start() throws Exception
  {
    System.out.println("Starting " + getClass().getName());

    server.start();
  }

  private void stop() throws Exception
  {
    server.stop();
  }

}

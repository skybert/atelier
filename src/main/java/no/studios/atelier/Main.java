package no.studios.atelier;

import no.studios.atelier.ws.AtelierWebServices;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;

/**
 * Main class starting Atelier.
 * 
 * @author Torstein Krause Johansen
 * @version 1.0
 */
public class Main
{
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

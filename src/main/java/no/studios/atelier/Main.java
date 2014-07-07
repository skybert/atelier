package no.studios.atelier;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.ProtectionDomain;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

import org.eclipse.jetty.plus.jndi.Resource;
import org.eclipse.jetty.plus.jndi.EnvEntry;
import org.eclipse.jetty.plus.jndi.Transaction;
import org.eclipse.jetty.plus.jndi.EnvEntry;
import org.eclipse.jetty.plus.jndi.Resource;
import org.eclipse.jetty.plus.jndi.Transaction;
import org.eclipse.jetty.webapp.Configuration.ClassList;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

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

  public static void main(String[] args) throws Exception
  {
    Main app = new Main();
    app.start();
  }

  private void start() throws Exception
  {
    Server server = new Server(new QueuedThreadPool(512));
    server.setStopAtShutdown(true);
    server.setStopTimeout(5000);

    ServerConnector connector = new ServerConnector(server);
    connector.setPort(DEFAULT_PORT);
    connector.setIdleTimeout(30000);
    server.setConnectors(new Connector[] { connector });

    ProtectionDomain protectionDomain = Main.class.getProtectionDomain();
    String warFile = protectionDomain.getCodeSource().getLocation()
        .toExternalForm();
    String currentDir = new File(protectionDomain.getCodeSource().getLocation()
        .getPath()).getParent();

    WebAppContext webapp = new WebAppContext(warFile, DEFAULT_CONTEXT_PATH);
    webapp.setServer(server);

    HandlerList handlers = new HandlerList();
    handlers.addHandler(webapp);
    server.setHandler(handlers);

    server.start();
    server.join();
  }

}

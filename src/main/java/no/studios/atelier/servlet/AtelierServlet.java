package no.studios.atelier.servlet;

/**
 * Some definitions useful to atelier servlets.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public interface AtelierServlet
{
  public final String CURRENT_REQUESTING_USER = "requesting-user";
  public final String CURRENT_OUTPUT_STREAM = "output-stream";
  public final String NO_CACHE = "no-cache";
  public final String HEADER_X_SCHEME = "X-Scheme";
}

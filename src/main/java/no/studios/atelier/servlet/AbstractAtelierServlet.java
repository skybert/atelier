package no.studios.atelier.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import java.io.OutputStream;
import java.util.*;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import no.studios.atelier.util.*;
import no.studios.atelier.data.*;
import no.studios.atelier.model.*;
import org.apache.log4j.Logger;

/**
 * Describe class <code>AbstractAtelierServlet</code> here.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision$ $Date$
 */
public abstract class AbstractAtelierServlet extends HttpServlet implements
    AtelierServlet
{
  protected final static int NO_COMMAND = -1;
  protected final static int COMMAND_NEW = 1;
  protected final static int COMMAND_EDIT = 2;
  protected final static int COMMAND_DELETE = 3;

  protected final Logger mLogger = Logger.getLogger(getClass());
  protected static final String CURRENT_OUTPUT_STREAM = "output-stream";
  private static final String NO_CACHE = "no-cache";
  private final DateFormat mRFC822DateFormat = new SimpleDateFormat(
      "EEE, dd MMM yyyy HH:mm:ss Z");
  private String mService;

  protected ObjectFactory mObjectFactory = new DefaultObjectFactory();

  public void configure(
    HttpServletRequest pRequest,
    HttpServletResponse pResponse) throws IOException, ServletException
  {
    if ("GET".equalsIgnoreCase(pRequest.getMethod()))
    {
      pResponse.setContentType("text/xml; charset=UTF-8");
    }

    OutputStream outputStream = pResponse.getOutputStream();
    pRequest.setAttribute(CURRENT_OUTPUT_STREAM, outputStream);

  }

  private void setCacheHeaders(HttpServletResponse pResponse)
  {
    if (!"whois".equals(mService) && !"".equals(mService))
    {
      // for HTTP 1.0 only caches
      pResponse.setHeader("Pragma", NO_CACHE);
      // for HTTP 1.1 caches
      pResponse.setHeader("Cache-Control", NO_CACHE);
    }
    else
    {
      Date inTenMinutes = new Date(System.currentTimeMillis()
          + (10 * 60 * 1000));
      pResponse.setHeader("Expires", mRFC822DateFormat.format(inTenMinutes));
    }
  }

  protected OutputStream getOutputStream(HttpServletRequest pRequest)
  {
    return (OutputStream) pRequest.getAttribute(CURRENT_OUTPUT_STREAM);
  }

  /**
   * Writes a string using UTF8 encoding.
   * 
   * @param pResponse
   *          a <code>HttpServletResponse</code> value.
   * @param pString
   *          the String to write.
   * @throws IOException
   *           if an I/O error occured.
   */
  protected void writeStringUTF8(
    final HttpServletResponse pResponse,
    final String pString) throws IOException
  {
    byte[] bytes = StringUtil.toUTF8ByteArray(pString);
    OutputStream outputStream = pResponse.getOutputStream();
    pResponse.setStatus(HttpServletResponse.SC_OK);
    outputStream.write(bytes);
    outputStream.flush();
  }

  protected int getCommand(final String pURI, final String pDomain)
  {
    String[] elements = pURI.split("/");

    // first element is empty (for some reason, String#split does
    // this.
    if (elements.length != 4 || !elements[2].equals(pDomain))
    {
      return NO_COMMAND;
    }
    else if (elements[3].equals(AtelierEntity.URI_NEW))
    {
      return COMMAND_NEW;
    }
    else if (elements[3].equals(AtelierEntity.URI_EDIT))
    {
      return COMMAND_EDIT;
    }
    else if (elements[3].equals(AtelierEntity.URI_DELETE))
    {
      return COMMAND_DELETE;
    }

    return NO_COMMAND;
  }

  /**
   * Gets the ID converted to an int. Returns <code>-1</code> if the conversion
   * fails or if the key couldn't be found.
   * 
   * @param pRequest
   *          a <code>HttpServletRequest</code> value
   * @param pIdKey
   *          a <code>String</code> value
   * @return an <code>int</code> value
   */
  public int getInt(final HttpServletRequest pRequest, final String pIdKey)
  {
    String idString = pRequest.getParameter(pIdKey);
    if (!StringUtil.isEmpty(idString))
    {
      try
      {
        return Integer.parseInt(idString);
      }
      catch (NumberFormatException nfe)
      {
        mLogger.error("couldn't convert pIdKey=" + pIdKey + " value="
            + idString + " to an int", nfe);
      }
    }

    return -1;
  }

  public Date getDate(final HttpServletRequest pRequest, final String pKey)
  {
    Date date = null;
    String dateString = pRequest.getParameter(pKey);
    if (!StringUtil.isEmpty(dateString))
    {
      date = DateUtil.parseSimpleNorwegian(dateString);
    }
    return date;
  }

  public boolean
    getBoolean(final HttpServletRequest pRequest, final String pKey)
  {
    String booleanString = pRequest.getParameter(pKey);
    return ("on".equals(booleanString) || "true".equals(booleanString));
  }

  protected void sendErrorMessage(
    String pStatusParameters,
    HttpServletRequest pRequest,
    HttpServletResponse pResponse) throws IOException
  {
    // TODO proper
    mLogger.error("failed=" + pRequest.getRequestURI());
    pResponse.sendRedirect(pRequest.getContextPath() + "/index.jsp?"
        + pStatusParameters);
  }

  protected void debug(final HttpServletRequest pRequest) throws IOException
  {
    if (mLogger.isDebugEnabled())
    {
      StringBuffer debug = new StringBuffer();
      debug.append("requestURI=" + pRequest.getRequestURI() + "\n");
      debug.append("requestURL=" + pRequest.getRequestURL() + "\n");

      Enumeration headerNames = pRequest.getHeaderNames();
      while (headerNames.hasMoreElements())
      {
        String name = (String) headerNames.nextElement();
        String value = (String) pRequest.getHeader(name);
        debug.append("header " + name + "=" + value + "\n");
      }

      Enumeration names = pRequest.getParameterNames();

      while (names.hasMoreElements())
      {
        String name = (String) names.nextElement();
        String value = (String) pRequest.getParameter(name);
        String valueDecodedUTF = URLDecoder.decode(pRequest.getParameter(name),
            StringUtil.ENC_UTF8);
        String valueDecodedISO = URLDecoder.decode(pRequest.getParameter(name),
            StringUtil.ENC_ISO_8859_1);
        debug.append(name + "=" + value + "(decodedUTF=" + valueDecodedUTF
            + " decodedISO=" + valueDecodedISO + ")\n");
      }

      mLogger.debug(debug.toString());
    }
  }

  protected String getBaseURL(HttpServletRequest pRequest)
  {
    String scheme = "http";
    if (pRequest.getHeader(HEADER_X_SCHEME) != null)
    {
      scheme = pRequest.getHeader(HEADER_X_SCHEME);
    }

    return scheme + "://" + pRequest.getHeader("host");
  }
}

package no.studios.atelier.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.OutputStream;
import no.studios.atelier.data.*;
import no.studios.atelier.util.*;
import no.studios.atelier.model.*;
import java.util.*;

/**
 * Describe class <code>CustomerServlet</code> here.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.5 $ $Date: 2008/01/31 17:40:57 $
 */
public class CustomerServlet extends AbstractAtelierServlet
{
  private final static String URI_CUSTOMER = "customer";

  private CustomerFactory mCustomerFactory;

  public CustomerServlet()
  {
    mCustomerFactory = new DefaultCustomerFactory();
  }

  /**
   * We're not too picky at the moment :-)
   * 
   * @param pOrderMap
   *          a <code>Map</code> value
   * @return a <code>boolean</code> value
   */
  private boolean validCustomer(final Map pOrderMap)
  {
    return pOrderMap.containsKey(Customer.KEY_LAST_NAME);
  }

  private Customer getCustomer(final HttpServletRequest pRequest)
  {
    Customer customer = new DefaultCustomer();

    // will only be there when editing an existing customer
    int customerId = getInt(pRequest, Customer.KEY_CUSTOMER_ID);
    if (customerId != -1)
    {
      customer.setId(customerId);
    }

    String firstName = pRequest.getParameter(Customer.KEY_FIRST_NAME);
    if (!StringUtil.isEmpty(firstName))
    {
      customer.setFirstName(firstName);
    }

    String lastName = pRequest.getParameter(Customer.KEY_LAST_NAME);
    if (!StringUtil.isEmpty(lastName))
    {
      customer.setLastName(lastName);
    }

    String address = pRequest.getParameter(Customer.KEY_ADDRESS);
    if (!StringUtil.isEmpty(address))
    {
      customer.setAddress(address);
    }

    String postCode = pRequest.getParameter(Customer.KEY_POST_CODE);
    if (!StringUtil.isEmpty(postCode))
    {
      customer.setPostCode(postCode);
    }

    String homePhone = pRequest.getParameter(Customer.KEY_HOME_PHONE);
    if (!StringUtil.isEmpty(homePhone))
    {
      customer.setHomePhone(homePhone);
    }

    String mobilePhone = pRequest.getParameter(Customer.KEY_MOBILE_PHONE);
    if (!StringUtil.isEmpty(mobilePhone))
    {
      customer.setMobilePhone(mobilePhone);
    }

    String workPhone = pRequest.getParameter(Customer.KEY_WORK_PHONE);
    if (!StringUtil.isEmpty(workPhone))
    {
      customer.setWorkPhone(workPhone);
    }

    String email = pRequest.getParameter(Customer.KEY_EMAIL);
    if (!StringUtil.isEmpty(email))
    {
      customer.setEmailAddress(email);
    }

    String birthDateString = pRequest.getParameter(Customer.KEY_BIRTH_DATE);
    if (!StringUtil.isEmpty(birthDateString))
    {
      Date birthDate = DateUtil.parseSimpleNorwegian(birthDateString);
      if (birthDate != null)
      {
        customer.setBirthDate(birthDate);
      }
    }

    return customer;
  }

  public void doGet(HttpServletRequest pRequest, HttpServletResponse pResponse)
    throws ServletException,
    IOException
  {
    super.configure(pRequest, pResponse);
    int command = getCommand(pRequest.getRequestURI(), URI_CUSTOMER);

    debug(pRequest);

    switch (command)
    {
      case COMMAND_NEW:
        createCustomer(pRequest, pResponse);
        break;
      case COMMAND_EDIT:
        editCustomer(pRequest, pResponse);
        break;
      case COMMAND_DELETE:
        deleteCustomer(pRequest, pResponse);
        break;
      default:
        if (mLogger.isDebugEnabled())
        {
          mLogger.debug("no recognisable command, URI="
              + pRequest.getRequestURI());
        }

        pResponse.sendRedirect(pRequest.getContextPath());
        break;
    }
  }

  private void createCustomer(
    HttpServletRequest pRequest,
    HttpServletResponse pResponse) throws ServletException, IOException
  {
    if (validCustomer(pRequest.getParameterMap()))
    {
      try
      {
        Customer customer = getCustomer(pRequest);
        customer.setCreationDate(new Date());
        mCustomerFactory.createCustomer(customer);

        // show the new user
        int customerId = mCustomerFactory.getLastCustomerId();
        pResponse.sendRedirect(pRequest.getContextPath() + "/viewCustomer.jsp?"
            + Customer.KEY_CUSTOMER_ID + "=" + customerId + "&"
            + Customer.URI_NEW + "=" + Customer.URI_SUCCESS);
        return;
      }
      catch (DataOperationFailedException dofe)
      {
        mLogger.error(dofe);
      }
    }
    else if (mLogger.isDebugEnabled())
    {
      mLogger.debug("not a valid customer=" + pRequest.getParameterMap());
    }

    sendErrorMessage(Customer.URI_NEW + "=" + Customer.URI_FAILED, pRequest,
        pResponse);
  }

  private void editCustomer(
    HttpServletRequest pRequest,
    HttpServletResponse pResponse) throws ServletException, IOException
  {
    if (validCustomer(pRequest.getParameterMap()))
    {
      try
      {
        Customer customer = getCustomer(pRequest);
        mCustomerFactory.updateCustomer(customer);
        pResponse.sendRedirect(pRequest.getContextPath() + "/viewCustomer.jsp?"
            + Customer.KEY_CUSTOMER_ID + "=" + customer.getId() + "&"
            + Customer.URI_EDIT + "=" + Customer.URI_SUCCESS);
        // pResponse.setStatus(HttpServletResponse.SC_OK);
        return;
      }
      catch (DataOperationFailedException dofe)
      {
        mLogger.error(dofe);
      }
      catch (NoSuchObjectException nsoe)
      {
        mLogger.error(nsoe);
      }
    }
    else if (mLogger.isDebugEnabled())
    {
      mLogger.debug("not a valid customer=" + pRequest.getParameterMap());
    }

    sendErrorMessage(Customer.URI_EDIT + "=" + Customer.URI_FAILED, pRequest,
        pResponse);
  }

  private void deleteCustomer(
    HttpServletRequest pRequest,
    HttpServletResponse pResponse) throws ServletException, IOException
  {
    try
    {
      Customer customer = getCustomer(pRequest);
      mCustomerFactory.deleteCustomer(customer.getId());

      pResponse.sendRedirect(pRequest.getContextPath() + "/searchCustomer.jsp?"
          + Customer.URI_DELETE + "=" + Customer.URI_SUCCESS);
      return;
    }
    catch (DataOperationFailedException dofe)
    {
      mLogger.error(dofe);
    }

    sendErrorMessage(Customer.URI_DELETE + "=" + Customer.URI_FAILED, pRequest,
        pResponse);
  }

}

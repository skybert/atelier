package no.studios.atelier.view;

import no.studios.atelier.data.*;
import no.studios.atelier.model.*;
import no.studios.atelier.util.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A nice paymentType view, is always nice, i.e. it never throws exceptions,
 * returns emtpy lists and null if only one object is requested (and not a
 * list).
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public final class PaymentTypeView extends AbstractView
{
  private PaymentTypeFactory mPaymentTypeFactory;

  public PaymentTypeView()
  {
    mPaymentTypeFactory = new DefaultPaymentTypeFactory();
  }

  public PaymentType getPaymentType(final String pId)
  {
    PaymentType paymentType = null;

    int id = -1;

    try
    {
      id = Integer.parseInt(pId);
    }
    catch (NumberFormatException nfe)
    {
      if (mLogger.isDebugEnabled())
      {
        mLogger.debug("could not convert pId=" + pId + " to an int", nfe);
      }
      return paymentType;
    }

    try
    {
      paymentType = mPaymentTypeFactory.getPaymentType(id);
    }
    catch (NoSuchObjectException nsoe)
    {
      if (mLogger.isDebugEnabled())
      {
        mLogger.debug(nsoe);
      }
    }
    catch (DataOperationFailedException dofe)
    {
      mLogger.error(dofe);
    }
    return paymentType;
  }

  public List<PaymentType> getAllPaymentTypes()
  {
    List<PaymentType> paymentTypeList = new ArrayList<PaymentType>();

    try
    {
      paymentTypeList = mPaymentTypeFactory.getAllPaymentTypes();
    }
    catch (NoSuchObjectException nsoe)
    {
      if (mLogger.isDebugEnabled())
      {
        mLogger.debug(nsoe);
      }
    }
    catch (DataOperationFailedException dofe)
    {
      mLogger.error(dofe);
    }

    Collections.sort(paymentTypeList, new PaymentTypeComparator());
    return paymentTypeList;
  }

}
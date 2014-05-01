package no.studios.atelier.util;

import no.studios.atelier.model.*;

/**
 * PriceUtil.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.1 $
 */
public class PriceUtil
{

  /**
   * Creates a new <code>PriceUtil</code> instance.
   * 
   */
  private PriceUtil()
  {

  }

  public static double getPrice(final CustomerOrder pOrder)
  {
    double price = 0.0;

    for (OrderItem orderItem : pOrder.getOrderItems())
    {
      price += orderItem.getTotalAmount();
    }

    return price;
  }

}

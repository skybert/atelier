package no.studios.atelier.util;

import no.studios.atelier.model.*;
import java.util.*;

/**
 * OrderStatusComparator. We always want "Ny" to be first in the list.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.1 $
 */
public class OrderStatusComparator implements Comparator<OrderStatus>
{

  /**
   * Creates a new <code>OrderStatusComparator</code> instance.
   */
  public OrderStatusComparator()
  {

  }

  public int compare(final OrderStatus pStatus1, final OrderStatus pStatus2)
  {
    if ("Ny".equals(pStatus1.getName()))
    {
      return 0;
    }

    return 1;
  }

}

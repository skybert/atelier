package no.studios.atelier.util;

import no.studios.atelier.model.*;
import java.util.*;

/**
 * PaymentTypeComparator. We always want "Kontant" to be first in the list.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.1 $
 */
public class PaymentTypeComparator implements Comparator<PaymentType>
{

  /**
   * Creates a new <code>PaymentTypeComparator</code> instance.
   */
  public PaymentTypeComparator()
  {

  }

  public int compare(final PaymentType pStatus1, final PaymentType pStatus2)
  {
    if ("Kontant".equals(pStatus1.getName()))
    {
      return 0;
    }

    return 1;
  }

}

package no.studios.atelier.data;

import java.util.*;
import no.studios.atelier.model.*;

/**
 * OrderItem.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.2 $
 */
public class DefaultOrderItem extends AbstractOrderItem
{
  public void setNumberOfItems(final int pNumberOfItems)
  {
    mNumberOfItems = pNumberOfItems;
    // setTotalAmount(getNumberOfItems() * getProduct().getPrice());
  }

}

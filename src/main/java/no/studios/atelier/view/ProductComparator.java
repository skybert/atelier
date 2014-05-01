package no.studios.atelier.view;

import java.util.Comparator;
import no.studios.atelier.model.Product;

/**
 * For sorting Products after product name.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.1 $
 */
public class ProductComparator implements Comparator<Product>
{
  public int compare(Product pProduct1, Product pProduct2)
  {
    if (pProduct1 != null && pProduct2 != null && pProduct1.getName() != null
        && pProduct2.getName() != null)
    {
      return pProduct1.getName().toLowerCase()
          .compareTo(pProduct2.getName().toLowerCase());
    }

    return 0;
  }

  /**
   * Creates a new <code>ProductComparator</code> instance.
   * 
   */
  public ProductComparator()
  {

  }

}

package no.studios.atelier.view;

import java.util.Comparator;
import no.studios.atelier.model.ProductType;

/**
 * For sorting ProductTypes after product name.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.1 $
 */
public class ProductTypeComparator implements Comparator<ProductType>
{
  public int compare(ProductType pProductType1, ProductType pProductType2)
  {
    if (pProductType1 != null && pProductType2 != null
        && pProductType1.getName() != null && pProductType2.getName() != null)
    {
      return pProductType1.getName().toLowerCase()
          .compareTo(pProductType2.getName().toLowerCase());
    }

    return 0;
  }

  /**
   * Creates a new <code>ProductTypeComparator</code> instance.
   * 
   */
  public ProductTypeComparator()
  {

  }

}

package no.studios.atelier.data;

import no.studios.atelier.model.*;
import java.util.*;

/**
 * Various reports
 * 
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.1 $ $Date: 2008/11/08 20:51:56 $
 */
public interface ReportFactory
{
  public final static String ORDERED_A_CERTAIN_PRODUCT = "get-list-ordered-a-certain-product";

  public List<Map> getOrderedCertainProducts(
    final Date pOrderFromDate,
    final List<Integer> pProductIdList) throws DataOperationFailedException;
}

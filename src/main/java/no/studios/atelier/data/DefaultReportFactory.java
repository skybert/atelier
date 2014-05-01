package no.studios.atelier.data;

import no.studios.atelier.model.*;
import java.sql.SQLException;
import java.util.*;

/**
 * Factory factory.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.1 $
 */
public final class DefaultReportFactory extends AbstractFactory implements
    ReportFactory
{
  public List<Map> getOrderedCertainProducts(
    final Date pOrderFromDate,
    final List<Integer> pProductIdList) throws DataOperationFailedException
  {
    Map parameterMap = new HashMap();
    parameterMap.put("creationDate", pOrderFromDate);
    StringBuilder productIdListString = new StringBuilder();

    for (int i = 0; i < pProductIdList.size(); i++)
    {
      productIdListString.append(Integer.toString(pProductIdList.get(i)));
      if (i < pProductIdList.size() - 1)
      {
        productIdListString.append(", ");
      }
    }
    parameterMap.put("productIdList", pProductIdList);
    List<Map> result = null;
    try
    {
      result = (List<Map>) mSQLMapClient.queryForList(
          ORDERED_A_CERTAIN_PRODUCT, parameterMap);
    }
    catch (SQLException se)
    {
      throw new DataOperationFailedException(se);
    }

    if (mLogger.isDebugEnabled())
    {
      mLogger.debug("returning " + result.size() + " rows "
          + "for creationDate=" + pOrderFromDate + " productIdListString="
          + pProductIdList);
    }

    return result;
  }
}

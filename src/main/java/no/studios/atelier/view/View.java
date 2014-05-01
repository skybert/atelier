package no.studios.atelier.view;

import java.util.Date;

/**
 * Interface for all views.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public interface View
{
  /**
   * Returns a date from the request.
   * 
   * @param pKey
   *          a <code>String</code> value
   * @return a <code>Date</code> value
   */
  public Date getDate(final String pKey);

  public final String BANK_ACCOUNT = "9486 05 31555";
  public final String COMPANY_NUMBER = "935 350 794";

  public final String DB_ID = "id";
  public final String DB_FIRST_NAME = "first_name";
  public final String DB_LAST_NAME = "last_name";
  public final String DB_ADDRESS = "address";
  public final String DB_POST_CODE = "post_code";
  public final String DB_PAID_AMOUNT = "paid_amount";
  public final String DB_ORDER_ID = "order_id";
}
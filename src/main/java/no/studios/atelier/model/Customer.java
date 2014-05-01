package no.studios.atelier.model;

/**
 * Describe interface Customer here.
 * 
 * 
 * Created: Mon Nov 26 16:20:00 2007
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public interface Customer extends Person
{
  public static final String KEY_CUSTOMER_ID = "customer-id";
  public final static String URI_CUSTOMER = "customer";

  // legacy system support
  public String getOldArchiveId();

  public void setOldArchiveId(final String pArchiveId);

  public String getOldCustomerId();

  public void setOldCustomerId(final String pCustomerId);

}

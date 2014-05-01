package no.studios.atelier.model;

import java.util.Date;

/**
 * Post place.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.1 $
 */
public interface PostPlace extends AtelierEntity
{
  public String getPostCode();

  public void setPostCode(final String pPostCode);

  public String getPostPlace();

  public void setPostPlace(final String pPostPlace);
}

package no.studios.atelier.model;

import no.studios.atelier.model.*;
import java.util.Date;

/**
 * PostPlace.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.2 $
 */
public abstract class AbstractPostPlace extends AbstractAtelierEntity implements
    PostPlace
{
  private String mPostCode;
  private String mPostPlace;

  public String getPostCode()
  {
    return mPostCode;
  }

  public void setPostCode(final String pPostCode)
  {
    mPostCode = pPostCode;
  }

  public String getPostPlace()
  {
    return mPostPlace;
  }

  public void setPostPlace(final String pPostPlace)
  {
    mPostPlace = pPostPlace;
  }

  public String getType()
  {
    return TYPE_POST_PLACE;
  }

}

package no.studios.atelier.view;

import java.util.*;
import no.studios.atelier.data.*;
import no.studios.atelier.util.*;
import no.studios.atelier.model.*;

/**
 * A nice postPlace view, is always nice, i.e. it never throws exceptions,
 * returns emtpy lists and null if only one object is requested (and not a
 * list).
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public final class PostPlaceView extends AbstractView
{
  private PostPlaceFactory mPostPlaceFactory;

  public PostPlaceView()
  {
    mPostPlaceFactory = new DefaultPostPlaceFactory();
  }

  public String getPostPlace(final Object pPostCode)
  {
    if (pPostCode instanceof String)
    {
      return getPostPlace((String) pPostCode);
    }
    return "";
  }

  public String getPostPlace(final String pPostCode)
  {
    if (StringUtil.isEmpty(pPostCode))
    {
      return "";
    }

    PostPlace postPlace = null;

    try
    {
      postPlace = mPostPlaceFactory.getPostPlace(pPostCode);
    }
    catch (NoSuchObjectException nsoe)
    {
      if (mLogger.isDebugEnabled())
      {
        mLogger.debug(nsoe);
      }
    }
    catch (DataOperationFailedException dofe)
    {
      mLogger.error(dofe);
    }

    return postPlace != null ? postPlace.getPostPlace() : "";
  }

  public List<PostPlace> getAllPostPlaces()
  {
    List<PostPlace> postPlaceList = new ArrayList<PostPlace>();

    try
    {
      postPlaceList = mPostPlaceFactory.getAllPostPlaces();
    }
    catch (NoSuchObjectException nsoe)
    {
      if (mLogger.isDebugEnabled())
      {
        mLogger.debug(nsoe);
      }
    }
    catch (DataOperationFailedException dofe)
    {
      mLogger.error(dofe);
    }

    return postPlaceList;
  }

}
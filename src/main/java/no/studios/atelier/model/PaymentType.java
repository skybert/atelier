package no.studios.atelier.model;

import java.util.*;

/**
 * Describe interface Order here.
 * 
 * 
 * Created: Mon Nov 26 16:26:09 2007
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.2 $
 */
public interface PaymentType extends AtelierEntity
{
  public String getName();

  public void setName(final String pName);
}

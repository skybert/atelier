package no.studios.atelier.model;

import java.util.*;

/**
 * An invoice.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.2 $
 */
public interface Invoice extends AtelierEntity
{
  public CustomerOrder getOrder();

  public void setOrder(final CustomerOrder pOrder);

  public Date getDueDate();

  public void setDueDate(final Date pDueDate);

  public void setTaxIncluded(final boolean pTaxIncluded);

  public boolean getTaxIncluded();
}

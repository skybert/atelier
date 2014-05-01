package no.studios.atelier.model;

import java.util.*;

/**
 * Order status, e.g. "paid".
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public interface OrderStatus extends AtelierEntity
{
  public static final String KEY_STATUS_ID = "status-id";

  public String getName();

  public void setName(final String pName);
}

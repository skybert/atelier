package no.studios.atelier.model;

/**
 * Describe interface Product here.
 * 
 * 
 * Created: Mon Nov 26 16:22:07 2007
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public interface ProductType extends AtelierEntity
{
  public String getName();

  public void setName(final String pName);
}

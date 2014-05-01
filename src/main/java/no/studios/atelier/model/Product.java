package no.studios.atelier.model;

/**
 * A Product.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public interface Product extends AtelierEntity
{
  public ProductType getProductType();

  public void setProductType(final ProductType pProductTupe);

  public double getPrice();

  public void setPrice(final double pPrice);

  public String getName();

  public void setName(final String pName);

  public String getDescription();

  public void setDescription(final String pDescription);

  /**
   * The time in days it takes to produce the product (if applicable).
   * 
   * @return an <code>int</code> value
   */
  public int getProductionTime();

  /**
   * The time in days it takes to produce the product (if applicable).
   * 
   * @param pProductionTime
   *          an <code>int</code> value
   */
  public void setProductionTime(final int pProductionTime);
}

package no.studios.atelier.model;

/**
 * AbstractProduct.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version 1.0
 */
public abstract class AbstractProduct extends AbstractAtelierEntity implements
    Product
{
  private ProductType mProductType;
  private double mPrice;
  private int mProductionTime;
  private String mDescription;

  private String mName;

  public String getName()
  {
    return mName;
  }

  public void setName(final String pName)
  {
    mName = pName;
  }

  public ProductType getProductType()
  {
    return mProductType;
  }

  public void setProductType(final ProductType pProductTupe)
  {
    mProductType = pProductTupe;
  }

  public double getPrice()
  {
    return mPrice;
  }

  public void setPrice(final double pPrice)
  {
    mPrice = pPrice;
  }

  public int getProductionTime()
  {
    return mProductionTime;
  }

  public void setProductionTime(final int pProductionTime)
  {
    mProductionTime = pProductionTime;
  }

  public String getDescription()
  {
    return mDescription;
  }

  public void setDescription(final String pDescription)
  {
    mDescription = pDescription;
  }

  /**
   * {@inheritDoc}
   */
  public String toString()
  {
    final int sbSize = 1000;
    final String variableSeparator = "  ";
    final StringBuffer sb = new StringBuffer(sbSize);

    sb.append(getClass().getName() + "[");
    sb.append("mProductType=").append(mProductType);
    sb.append(variableSeparator);
    sb.append("mPrice=").append(mPrice);
    sb.append(variableSeparator);
    sb.append("mProductionTime=").append(mProductionTime);
    sb.append(variableSeparator);
    sb.append("mDescription=").append(mDescription);
    sb.append(variableSeparator);
    sb.append("mName=").append(mName);
    sb.append("]");

    return sb.toString();
  }
}

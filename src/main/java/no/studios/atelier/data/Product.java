package no.studios.atelier.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Product extends AtelierEntityBase
{
  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Date creationDate;
  private String name;
  private Double price;
  private Integer productionTime;
  private String description;

  @ManyToOne
  @JoinColumn(name = DBConstants.PRODUCT_TYPE_ID)
  private ProductType productType;

  @Override
  public Integer id()
  {
    return id;
  }

  public Date creationDate()
  {
    return creationDate;
  }

  public void creationDate(Date date)
  {
    creationDate = date;
  }

  public String name()
  {
    return name;
  }

  public void name(String name)
  {
    this.name = name;
  }

  public String description()
  {
    return description;
  }

  public void description(String description)
  {
    this.description = description;
  }

  public Double price()
  {
    return price;
  }

  public void price(final Double pPrice)
  {
    price = pPrice;
  }

  public Integer productionTime()
  {
    return productionTime;
  }

  public void productionTime(final Integer pTime)
  {
    productionTime = pTime;
  }

}

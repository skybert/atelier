package no.studios.atelier.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class ProductType extends AtelierEntityBase
{
  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Date creationDate;
  private String name;

  public ProductType()
  {
  }

  public ProductType(final String pName)
  {
    name = pName;
  }

  @Override
  public Integer getId()
  {
    return id;
  }

  public Date getCreationDate()
  {
    return creationDate;
  }

  public String getName()
  {
    return name;
  }

}

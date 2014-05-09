package no.studios.atelier.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { DBConstants.NAME }) })
public class OrderStatus extends AtelierEntityBase
{

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Date creationDate;
  private String name;
  private String description;

  public OrderStatus()
  {
  }

  public OrderStatus(
      final Date pCreationDate,
      final String pName,
      final String pDescription)
  {
    creationDate = pCreationDate;
    name = pName;
    description = pDescription;
  }

  @Override
  public Integer getId()
  {
    return id;
  }

  /** For testing */
  void id(final Integer pId)
  {
    id = pId;
  }

  public Date getCreationDate()
  {
    return creationDate;
  }

  public void creationDate(Date date)
  {
    creationDate = date;
  }

  public String getName()
  {
    return name;
  }

  public void name(String name)
  {
    this.name = name;
  }

  public String getDescription()
  {
    return description;
  }

  public void description(String description)
  {
    this.description = description;
  }

}

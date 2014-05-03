package no.studios.atelier.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { DBConstants.POSTCODE }) })
public class PostPlace extends AtelierEntityBase
{

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String postCode;
  private String postPlace;

  public PostPlace()
  {
  }

  public PostPlace(String pPostCode, String pPostPlace)
  {
    postCode = pPostCode;
    postPlace = pPostPlace;
  }

  @Override
  public Integer id()
  {
    return id;
  }

  public String postCode()
  {
    return postCode;
  }

  public String postPlace()
  {
    return postPlace;
  }

}

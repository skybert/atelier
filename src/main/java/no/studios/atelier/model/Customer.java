package no.studios.atelier.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Customer extends AtelierEntityBase
{
  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Date creationDate;
  private Date birthDate;
  private String firstName;
  private String lastName;
  private String address;
  private String emailAddress;
  private String oldCustomerId;
  private String oldArchiveId;
  private String postCode;

  private String mobilePhone;

  private String workPhone;

  private String homePhone;

  @Override
  public Integer getId()
  {
    return id;
  }

  @Override
  public void setId(Integer pId)
  {
    id = pId;

  }

  @Override
  public Date getCreationDate()
  {
    return creationDate;
  }

  @Override
  public void setCreationDate(Date date)
  {
    creationDate = date;
  }

  public Date getBirthDate()
  {
    return birthDate;
  }

  public void setBirthDate(Date date)
  {
    birthDate = date;
  }

  public void setFirstName(String pFirstName)
  {
    firstName = pFirstName;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setLastName(String pLastName)
  {
    lastName = pLastName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void setAddress(String pAddress)
  {
    address = pAddress;
  }

  public String getAddress()
  {
    return address;
  }

  public void setEmailAddress(String pEmailAddress)
  {
    emailAddress = pEmailAddress;
  }

  public String getEmailAddress()
  {
    return emailAddress;
  }

  public String getOldCustomerId()
  {
    return oldCustomerId;
  }

  public void setOldCustomerId(String oldCustomerId)
  {
    this.oldCustomerId = oldCustomerId;
  }

  public String getOldArchiveId()
  {
    return oldArchiveId;
  }

  public void setOldArchiveId(String oldArchiveId)
  {
    this.oldArchiveId = oldArchiveId;
  }

  public String getPostCode()
  {
    return postCode;
  }

  public void setPostCode(String postCode)
  {
    this.postCode = postCode;
  }

  public String getHomePhone()
  {
    return homePhone;
  }

  public void setHomePhone(String pHomePhone)
  {
    homePhone = pHomePhone;
  }

  public String getWorkPhone()
  {
    return workPhone;
  }

  public void setWorkPhone(String pWorkPhone)
  {
    workPhone = pWorkPhone;

  }

  public String getMobilePhone()
  {
    return mobilePhone;
  }

  public void setMobilePhone(String pMobilePhone)
  {
    mobilePhone = pMobilePhone;

  }

}

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
public class Customer extends AtelierEntityBase
{
  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Date birthDate;
  private Date creationDate;
  private String address;
  private String emailAddress;
  private String firstName;
  private String homePhone;
  private String lastName;
  private String mobilePhone;
  private String oldArchiveId;
  private String oldCustomerId;
  private String workPhone;

  // TODO migrate existing date or change this to a simple string
  // field and handle the mapping on a higher level?
  @ManyToOne
  @JoinColumn(name = DBConstants.POST_PLACE_ID)
  private PostPlace postPlace;

  public Integer getId()
  {
    return id;
  }

  public Date getCreationDate()
  {
    return creationDate;
  }

  public void creationDate(Date date)
  {
    creationDate = date;
  }

  public Date getBirthDate()
  {
    return birthDate;
  }

  public void birthDate(Date date)
  {
    birthDate = date;
  }

  public void firstName(String pFirstName)
  {
    firstName = pFirstName;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void lastName(String pLastName)
  {
    lastName = pLastName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void address(String pAddress)
  {
    address = pAddress;
  }

  public String getAddress()
  {
    return address;
  }

  public void emailAddress(String pEmailAddress)
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

  public void oldCustomerId(String oldCustomerId)
  {
    this.oldCustomerId = oldCustomerId;
  }

  public String getOldArchiveId()
  {
    return oldArchiveId;
  }

  public void oldArchiveId(String oldArchiveId)
  {
    this.oldArchiveId = oldArchiveId;
  }

  public PostPlace getPostPlace()
  {
    return postPlace;
  }

  public void postPlace(PostPlace pPostPlace)
  {
    this.postPlace = pPostPlace;
  }

  public String getHomePhone()
  {
    return homePhone;
  }

  public void homePhone(String pHomePhone)
  {
    homePhone = pHomePhone;
  }

  public String getWorkPhone()
  {
    return workPhone;
  }

  public void workPhone(String pWorkPhone)
  {
    workPhone = pWorkPhone;

  }

  public String getMobilePhone()
  {
    return mobilePhone;
  }

  public void mobilePhone(String pMobilePhone)
  {
    mobilePhone = pMobilePhone;

  }

}

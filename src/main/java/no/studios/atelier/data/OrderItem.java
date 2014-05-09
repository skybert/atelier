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
public class OrderItem extends AtelierEntityBase
{
  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Date creationDate;

  @ManyToOne
  @JoinColumn(name = DBConstants.ORDER_ID)
  private CustomerOrder customerOrder;

  @ManyToOne
  @JoinColumn(name = DBConstants.PRODUCT_ID)
  private Product product;

  private Integer numberOfItems;
  private Double discount;
  private Double totalAmount;
  private String comment;

  @Override
  public Integer getId()
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

  public Integer numberOfItems()
  {
    return numberOfItems;
  }

  public void numberOfItems(Integer numberOfItems)
  {
    this.numberOfItems = numberOfItems;
  }

  public Double discount()
  {
    return discount;
  }

  public void discount(Double discount)
  {
    this.discount = discount;
  }

  public Double totalAmount()
  {
    return totalAmount;
  }

  public void totalAmount(Double totalAmount)
  {
    this.totalAmount = totalAmount;
  }

  public String comment()
  {
    return comment;
  }

  public void comment(String comment)
  {
    this.comment = comment;
  }

}

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
public class CustomerOrder extends AtelierEntityBase
{
  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Integer customerId;

  @ManyToOne
  @JoinColumn(name = DBConstants.PAYMENT_TYPE_ID)
  private PaymentType paymentType;

  @ManyToOne
  @JoinColumn(name = DBConstants.ORDER_STATUS_ID)
  private OrderStatus orderStatus;

  private Date creationDate;
  private Date updatedDate;
  private Date deliveryDate;
  private Double paidAmount;
  private Double totalAmount;
  private String comment;
  private boolean newspaperAllowed;
  private boolean marketingAllowed;

  @Override
  public Integer id()
  {
    return id;
  }

  public PaymentType paymentType()
  {
    return paymentType;
  }

  public void paymentType(final PaymentType pPaymentType)
  {
    paymentType = pPaymentType;
  }

  public OrderStatus orderStatus()
  {
    return orderStatus;
  }

  public void orderStatusId(OrderStatus pStatus)
  {
    orderStatus = pStatus;
  }

  public Date creationDate()
  {
    return creationDate;
  }

  public void creationDate(Date date)
  {
    creationDate = date;
  }

  public Date deliveryDate()
  {
    return deliveryDate;
  }

  public void deliveryDate(Date date)
  {
    deliveryDate = date;
  }

  public Integer customerId()
  {
    return customerId;
  }

  public void customerId(Integer customerId)
  {
    this.customerId = customerId;
  }

  public Date updatedDate()
  {
    return updatedDate;
  }

  public void updatedDate(Date updatedDate)
  {
    this.updatedDate = updatedDate;
  }

  public Double paidAmount()
  {
    return paidAmount;
  }

  public void paidAmount(Double paidAmount)
  {
    this.paidAmount = paidAmount;
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

  public boolean isNewspaperAllowed()
  {
    return newspaperAllowed;
  }

  public void newspaperAllowed(boolean newspaperAllowed)
  {
    this.newspaperAllowed = newspaperAllowed;
  }

  public boolean isMarketingAllowed()
  {
    return marketingAllowed;
  }

  public void marketingAllowed(boolean marketingAllowed)
  {
    this.marketingAllowed = marketingAllowed;
  }

}

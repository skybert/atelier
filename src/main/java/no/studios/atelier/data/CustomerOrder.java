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

  @ManyToOne
  @JoinColumn(name = DBConstants.CUSTOMER_ID)
  private Customer customer;

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
  public Integer getId()
  {
    return id;
  }

  public PaymentType getPaymentType()
  {
    return paymentType;
  }

  public void paymentType(final PaymentType pPaymentType)
  {
    paymentType = pPaymentType;
  }

  public OrderStatus getOrderStatus()
  {
    return orderStatus;
  }

  public void orderStatusId(OrderStatus pStatus)
  {
    orderStatus = pStatus;
  }

  public Date getCreationDate()
  {
    return creationDate;
  }

  public void creationDate(Date date)
  {
    creationDate = date;
  }

  public Date getDeliveryDate()
  {
    return deliveryDate;
  }

  public void deliveryDate(Date date)
  {
    deliveryDate = date;
  }

  public Customer getCustomer()
  {
    return customer;
  }

  public void customer(final Customer pCustomer)
  {
    customer = pCustomer;
  }

  public Date getUpdatedDate()
  {
    return updatedDate;
  }

  public void updatedDate(Date updatedDate)
  {
    this.updatedDate = updatedDate;
  }

  public Double getPaidAmount()
  {
    return paidAmount;
  }

  public void paidAmount(Double paidAmount)
  {
    this.paidAmount = paidAmount;
  }

  public Double getTotalAmount()
  {
    return totalAmount;
  }

  public void totalAmount(Double totalAmount)
  {
    this.totalAmount = totalAmount;
  }

  public String getComment()
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

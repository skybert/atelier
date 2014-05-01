package no.studios.atelier.data;

import no.studios.atelier.model.*;
import java.util.List;

/**
 * PaymentTypeFactory.
 * 
 * @author Torstein Krause Johansen
 * 
 * @version $Revision: 1.3 $
 */
public interface PaymentTypeFactory
{
  public List<PaymentType> getAllPaymentTypes()
    throws NoSuchObjectException,
    DataOperationFailedException;

  public PaymentType getPaymentType(final int pPaymentTypeId)
    throws NoSuchObjectException,
    DataOperationFailedException;

  public void createPaymentType(final PaymentType pPaymentType)
    throws DataOperationFailedException;

  public void deletePaymentType(final int pPaymentTypeId)
    throws DataOperationFailedException;

  public void updatePaymentType(final PaymentType pPaymentType)
    throws NoSuchObjectException,
    DataOperationFailedException;
}

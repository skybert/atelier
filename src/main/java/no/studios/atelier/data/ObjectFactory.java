package no.studios.atelier.data;

/**
 * An ObjectFactory is responsible for reading and writing all Atelier
 * resources.
 * 
 * @author <a href="mailto:torstein@skybert.nu">Torstein Krause Johansen</a>
 * @version $Revision: 1.11 $
 */
public interface ObjectFactory extends CommentFactory, CustomerFactory,
    CustomerOrderFactory, InvoiceFactory, OrderStatusFactory, OrderItemFactory,
    PaymentTypeFactory, PostPlaceFactory, ProductFactory, ProductTypeFactory,
    UserFactory
{

}

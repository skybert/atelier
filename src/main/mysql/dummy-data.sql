-- $Id: dummy-data.sql,v 1.7 2007/12/29 18:00:40 torstein Exp $


-- customers

insert into customer
  (first_name, last_name, creation_date)
  values
  ("Arne", "Arnesen", '2007-04-10 08:00:00.0');

insert into customer
  (first_name, last_name, creation_date)
  values
  ("Lise", "Lisesen", '2007-04-10 08:00:00.0');


-- customer_order

insert into customer_order
   (customer_id, product_id, payment_type_id, order_status_id,
    creation_date, discount, total_amount, paid_amount)
   values
   (1, 1, 1, 1,
   '2007-04-10 08:00:00.0', 0.00, 0.00, 0.00);
insert into customer_order
   (customer_id, product_id, payment_type_id, order_status_id,
    creation_date, discount, total_amount, paid_amount)
   values
   (1, 2, 2, 2,
   '2007-04-10 08:00:00.0', 0.00, 0.00, 0.00);
insert into customer_order
   (customer_id, product_id, payment_type_id, order_status_id,
    creation_date, discount, total_amount, paid_amount)
   values
   (2, 2, 2, 1,
   '2007-04-10 08:00:00.0', 0.00, 0.00, 0.00);
insert into customer_order
   (customer_id, product_id, payment_type_id, order_status_id,
    creation_date, discount, total_amount, paid_amount)
   values
   (2, 3, 2, 1,
   '2007-04-10 08:00:00.0', 0.00, 0.00, 0.00);

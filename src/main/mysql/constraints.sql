-- customer
alter table customer
      add constraint fk_customer_post_code
      foreign key(post_code)
      references post_place(post_code);

-- user
alter table user
      add constraint fk_user_post_code
      foreign key(post_code)
      references post_place(post_code);

-- customer_order
alter table customer_order
      add constraint fk_order_customer
      foreign key(customer_id)
      references customer(id);
alter table customer_order
      add constraint fk_order_payment_type
      foreign key(payment_type_id)
      references payment_type(id);
alter table customer_order
      add constraint fk_order_status
      foreign key(order_status_id)
      references order_status(id);

-- order_item
alter table order_item
      add constraint fk_order_item_product
      foreign key(product_id)
      references product(id);
alter table order_item
      add constraint fk_order_item_order
      foreign key(order_id)
      references customer_order(id);

-- invoice
alter table invoice
      add constraint fk_invoice_order
      foreign key(order_id)
      references customer_order(id);

-- order_comment
alter table order_comment
      add constraint fk_comment_user
      foreign key(user_id)
      references user(id);
alter table order_comment
      add constraint fk_comment_order
      foreign key(order_id)
      references customer_order(id);

-- product
alter table product
      add constraint fk_product_type
      foreign key(product_type_id)
      references product_type(id);

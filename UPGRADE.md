# Upgrade notes

## Upgrade to 3.0

```sql
alter table invoice
      add column paid INTEGER not null;

alter table invoice
      add column customer_id INTEGER not null;

alter table invoice
      add constraint fk_invoice_customer
      foreign key(customer_id)
      references customer(id);
```

## Upgrade to 2.4
```
alter table customer add updated_date datetime null;
alter table customer add newspaper_allowed integer not null;
alter table customer add marketing_allowed integer not null;
alter table customer add internet_allowed integer not null;
```

## Upgrade from 2.0.x to 2.1.x
```
update customer_order set payment_type_id = 1 where payment_type_id is null;
update customer_order set order_status_id = 1 where order_status_id is null;
```

## From 1.x to 2.x
If you're running an older version of the Atelier DB (pre 2015-03-31),
you need to remove the `total_amount` column from the `customer_order`
table and add a new `internet_allowed` column:

```
alter table customer_order drop column total_amount;
alter table customer_order add internet_allowed integer not null;
```


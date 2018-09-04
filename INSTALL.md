# Installation notes

```
$ docker-compose up --build
```

## Upgrade to 3.0

```sql
alter table invoice 
      add column invoice_paid INTEGER not null;
```

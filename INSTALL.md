# Installation notes

Check out <a href="https://github.com/skybert/atelier/releases">the
version you want</a> (or use `develop` to get the latest development
code) and start it as a Docker container:

```
$ git clone https://github.com/skybert/atelier.git
$ cd atelier
$ git checkout <tag>
$ docker-compose up --build
```

If you wish to install it without Docker, just follow the steps in the
<a href="Dockerfile">Dockerfile</a> for the project, including the
endpoint script which bootstraps the database on a fresh system.

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

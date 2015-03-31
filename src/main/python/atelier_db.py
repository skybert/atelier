import MySQLdb as mdb
import atelier_sql as sql
from datetime import datetime
from datetime import timedelta

class AtelierDB:
    """Module which provides Atelier DB access.

    It contains knowledge of the DB schema and maps higher level
    domain models into DB structures that match the DB schema.
    """

    def __init__(self, db_host, db_user, db_password, db, logger):
        self.db_host = db_host
        self.db_user = db_user
        self.db_password = db_password
        self.db = db
        self.logger = logger

    def get_db_connection(self):
        con = mdb.connect(self.db_host, self.db_user, self.db_password, self.db)
        con.set_character_set('utf8')
        return con

    def query_list(self, query, query_values):
        self.logger.debug("query_list: " + query + "\n" + str(query_values))

        con = self.get_db_connection()
        with con:
            cur = con.cursor(mdb.cursors.DictCursor)
            cur.execute(query, query_values)
            return cur.fetchall()

    def query_one(self, query, values):
        self.logger.debug("query_one: " + query + "\n" + str(values))
        con = self.get_db_connection()
        with con:
            cur = con.cursor(mdb.cursors.DictCursor)
            cur.execute(query, values)
            return cur.fetchone()

    def delete(self, table, id):
        con = self.get_db_connection()
        with con:
            cur = con.cursor()
            cur.execute("delete from %s where id = %s" %(table, id))

    ## Returns the ID from the DB cursor
    def insert(self, query, values):
        self.logger.debug("insert: " + query + "\n" + str(values))
        con = self.get_db_connection()
        with con:
            cur = con.cursor(mdb.cursors.DictCursor)
            cur.execute(query, values)
            return cur.lastrowid

    ## Post place
    def get_post_place_list(self):
        return self.query_list("select * from post_place", None)

    ## Customer
    def get_customer(self, id):
        return self.query_one("select * from customer where id = %s", (id))

    def get_customer_order_list(self, customer_id):
        sql="select * from customer_order where customer_id = %s"
        return self.query_list(sql, (customer_id))

    def find_customers_by_name(self, name):
        # TODO find_customers_by_name : make wildcards work with prepared statement
        query = "select * from customer where first_name like %s or last_name like %s"
        return self.query_list(query, (name, name))

    def create_customer(self, form):
        insert_sql, values = sql.get_sql_and_values("customer", form)
        return self.insert(insert_sql, values)

    def update_customer(self, request_form):
        """
        Updates the customer described in request_form and returns the
        freshly updated customer entry.

        """
        update_sql, values = sql.get_sql_and_values("customer", request_form)
        self.query_one(update_sql, tuple(values))
        return self.get_customer(request_form["id"])

    def set_creation_date_to_now(self, form):
        form["creation_date"] = datetime.now()

    ## Order
    def get_order(self, id):
        query = """
        select
          o.*,
          sum(oi.total_amount) as total_amount
        from
          customer_order o,
          order_item oi
        where
          o.id = oi.order_id
          and o.id = %s
        """
        return self.query_one(query, (id))

    def create_order(self, form):
        self.set_creation_date_to_now(form)
        update_sql, values = sql.get_sql_and_values("customer_order", form)
        return self.insert(update_sql, tuple(values))

    def update_order(self, form):
        update_sql, values = sql.get_sql_and_values("customer_order", form)
        self.query_one(update_sql, tuple(values))

    def get_order_item(self, id):
        return self.query_one("select * from order_item where id = %s",
                                       (id))
    def update_order_item(self, form):
        update_sql, values = sql.get_sql_and_values("order_item", form)
        self.query_one(update_sql, tuple(values))

    def get_product_price(self, id):
        price = self.query_one("select price from product where id = %s", (id))
        return price["price"]

    def delete_order_item(self, id):
        return self.delete("order_item", id)

    def get_order_item_list(self, order_id):
        query = """
        select
          o.creation_date,
          o.id,
          o.number_of_items,
          o.total_amount,
          p.name as product_name,
          p.price as product_price
        from order_item o, product p
        where o.order_id = %s
        and o.product_id = p.id
        """
        return self.query_list(query, (order_id))

    def add_order_item(self, form):
        order_id = form["order_id"]
        price = self.get_product_price(form["product_id"])
        order_item_total =  price *  int(form["number_of_items"])
        form["total_amount"] = order_item_total
        update_sql, values = sql.get_sql_and_values("order_item", form)
        self.set_creation_date_to_now(form)
        self.logger.debug("update_sql="+ update_sql + "\n" + str(values))

        ## IMPROVEMENT make this update excute in the same tranaction as the
        ## order item insert.
#        self.update_order_total(order_id, order_item_total)

        return self.insert(update_sql, tuple(values))

    def update_order_total(self, id, delta):
        query = "select total_amount from customer_order where id = %s"
        old_amount = self.query_one(query, (id))["total_amount"]

        if old_amount != None:
            new_amount = old_amount + delta
        else:
            new_amount = delta

        form = {}
        form["id"] = id
        form["total_amount"] = new_amount
        update_order_sql, values = sql.get_sql_and_values("customer_order", form)
        return self.query_one(update_order_sql, tuple(values))


    ## Product
    def get_product(self, id):
        return self.query_one("select * from product where id = %s", (id))

    def get_product_list(self):
        query = """
        select p.id, p.name, p.creation_date, p.production_time, p.price, pt.name
        from product p, product_type pt
        where p.product_type_id = pt.id
        """
        return self.query_list(query, None)

    def create_product(self, form):
        insert_sql, values = sql.get_sql_and_values("product", form)
        return self.insert(insert_sql, values)

    def update_product(self, form):
        update_sql, values = sql.get_sql_and_values("product", form)
        self.query_one(update_sql, tuple(values))

    def delete_product(self, id):
        return self.delete("product", id)

    def get_product_type_list(self):
        return self.query_list("select * from product_type", None)

    ## Reports
    def get_order_list(self, from_date, to_date, product_list=[]):
        """
        If :product_list  is empty, all products will be included in the report.
        """

        # Since we use less than and greater than in the dates, we
        # adjust the input dates accordingly

        # TODO: get_order_list handle from/to__date being unicode(strings)
        if isinstance(from_date, datetime):
            from_date = from_date - timedelta(days=1)
        if isinstance(to_date, datetime):
            to_date = to_date + timedelta(days=1)

        if len(product_list) == 0:
            product_list_dict = self.query_list("select id from product", None)
            for el in product_list_dict:
                product_list.append(el["id"])

        product_in_string = ",".join(['%s'] * len(product_list))

        query = """
        select
          c.first_name,
          c.last_name,
          o.creation_date,
          o.customer_id,
          o.id as order_id,
          oi.id as order_item_id,
          oi.product_id,
          p.name as product_name
        from customer_order o, order_item oi, product p, customer c
        where o.id=oi.order_id
        and p.id=oi.product_id
        and c.id=o.customer_id
        and o.creation_date > %s
        and o.creation_date < %s
        and oi.product_id in (""" + product_in_string + """)
        order by o.creation_date
        """
        result = self.query_list(query, (from_date, to_date) + tuple(product_list))

        query = """
        select sum(oi.total_amount) as total_amount
        from order_item oi, customer_order o, product p
        where o.id=oi.order_id
        and p.id=oi.product_id
        and o.creation_date > %s
        and o.creation_date < %s
        and oi.product_id in (""" + product_in_string + """)
        """
        total_amount = self.query_list(query, (from_date, to_date) + tuple(product_list))


        # TODO get product_count_list sorted by count
        product_count_list={}
        for r in result:
            key = r["product_name"]
            product_count_list.setdefault(key, 0)
            product_count_list[key] += 1

        return result, product_count_list, total_amount

import MySQLdb as mdb
import atelier_sql as sql

class AtelierDB:
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

    def execute_return_list(self, query):
        con = self.get_db_connection()
        with con:
            cur = con.cursor(mdb.cursors.DictCursor)
            cur.execute(query)
            return cur.fetchall()

    def execute_return_one(self, query, query_values):
        con = self.get_db_connection()
        with con:
            cur = con.cursor(mdb.cursors.DictCursor)
            cur.execute(query, query_values)
            return cur.fetchone()

    def get_post_place_list(self):
        return self.execute_return_list("select * from post_place")

    def customer(self, id):
        return self.execute_return_one("select * from customer where id = %s", (id))

    def find_customers_by_name(self, name):
        # TODO re-write find_customers_by_name to prepared statement
        query = "select * from customer where first_name like '%" + name + "%' or last_name like '%" + name + "%'"
        return self.execute_return_list(query)


    def update_customer(self, request_form):
        sql, values = sql.get_sql_and_values("customer", request_form)
        app.logger.debug(sql + "\n" + str(values))
        self.execute_return_one(sql, tuple(values))

    def get_product(self, id):
        return self.execute_return_one("select * from product where id = %s", (id))

    def get_product_list(self):
        return self.execute_return_list("select p.id, p.name, p.creation_date, p.production_time, p.price, pt.name from product p, product_type pt where p.product_type_id = pt.id;")

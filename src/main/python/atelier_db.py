import MySQLdb as mdb

class AtelierDB:
    def __init__(self, db_host, db_user, db_password, db):
        self.db_host = db_host
        self.db_user = db_user
        self.db_password = db_password
        self.db = db

    def get_db_connection(self):
        con = mdb.connect(self.db_host, self.db_user, self.db_password, self.db)
        con.set_character_set('utf8')
        return con

    def get_dict_from_query(self, query):
        con = self.get_db_connection()
        with con:
            cur = con.cursor(mdb.cursors.DictCursor)
            cur.execute(query)
            return cur.fetchall()

    def get_object_from_query(self, query, query_values):
        con = self.get_db_connection()
        with con:
            cur = con.cursor(mdb.cursors.DictCursor)
            cur.execute(query, query_values)
            return cur.fetchone()

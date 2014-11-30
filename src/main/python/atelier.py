#! pyenv/bin/python

from flask import Flask
import MySQLdb as mdb
from flask import jsonify
from flask import render_template
from flask import request

app = Flask(__name__, static_url_path="/files", static_folder="files")

def get_db_connection():
    con = mdb.connect('localhost', 'atelieruser', 'atelierpassword', 'atelierdb')
    con.set_character_set('utf8')
    return con

@app.route("/")
@app.route("/search")
def get_search():
    return render_template("search.html")

@app.route("/search/query")
def search():
    customer_id=request.args.get("customer_id")

    app.logger.debug("customer_id=" + customer_id)

    con=get_db_connection()
    with con:
        cur = con.cursor(mdb.cursors.DictCursor)
        cur.execute("select * from customer where id=" + customer_id)
        customers= cur.fetchall()

    return render_template("customer-list.html", customers=customers)

if __name__ == '__main__':
    app.run(debug=True)




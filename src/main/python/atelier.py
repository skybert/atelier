#! pyenv/bin/python

from flask import Flask
from flask import render_template
from flask import request
import MySQLdb as mdb
from adb import AtelierDB
import conf
import flask

app = Flask(__name__, static_url_path="/files", static_folder="files")
@app.route("/")
@app.route("/search")
def get_search():
    return render_template("search.html")

@app.route("/search/query")
def search():
    customer_id=request.args.get("customer_id")
    app.logger.debug("customer_id=" + customer_id)
    query = "select * from customer where id=" + customer_id
    customers = db.get_dict_from_query(query)
    return render_template("customer-list.html", customers=customers)

@app.route("/post-place-list")
def get_post_place_list():
    return db.get_dict_from_query("select * from post_place")

@app.route("/customer", methods = ["GET"])
def customer(customer=None):
    customer_id = request.args.get("id")
    customer={}
    if customer_id:
        query = "select * from customer where id = %s"
        customer = db.get_object_from_query(query, (customer_id))

    return render_template("customer.html",
                           customer=customer,
                           post_place_list=get_post_place_list())

@app.route("/customer", methods=["POST", "PUT"])
def update_customer():
    customer={}
    if request.form.has_key("id"):
        sql = "update customer set "
        for key in request.form:
            if isinstance(request.form[key], basestring):
                sql += "\n\t" + key + "= '%s',"
            else:
                sql += "\n\t" + key + "= %s,"
        sql += "\nwhere id = " + request.form["id"]
    else:
        sql = "insert into customer ("
        for key in request.form:
            if request.form[key]:
                sql += key + ", "
        sql = sql[:-2] + ")"
        sql += "\nvalues ("
        for key in request.form:
            if request.form[key]:
                if isinstance(request.form[key], basestring):
                    sql += "'" + request.form[key] + "', "
                else:
                    sql += request.form[key] + ","

        sql = sql[:-2] + ")"

    app.logger.debug(sql)

    con = get_db_connection()
    with con:
        cur = con.cursor()
        cur.execute(sql);


    return "hello"

@app.route("/about")
def about():
    app.logger.debug(flask.__version__)
    info={}
    info["bootstrap-version"] = '3.3.1'
    info["flask_version"] = flask.__version__
    info["author"] = "Torstein Krause Johansen"

    con = db.get_db_connection()
    with con:
        cur = con.cursor()
        cur.execute("select version()")
        db_version = cur.fetchone()

    info["mysql_version"] = db_version[0]
    return render_template("about.html", about_info=info)

if __name__ == '__main__':
    conf_data = conf.read_conf_from_file()
    db = AtelierDB(
        conf_data["db"]["host"],
        conf_data["db"]["user"],
        conf_data["db"]["password"],
        conf_data["db"]["db"],
    )
    app.run(debug=True)




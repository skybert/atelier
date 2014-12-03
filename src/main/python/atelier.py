#! pyenv/bin/python

from flask import Flask
from flask import render_template
from flask import request
import MySQLdb as mdb
from atelier_db import AtelierDB
import atelier_conf
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
        customer = get_customer(customer_id)

    return render_template("customer.html",
                           customer=customer,
                           post_place_list=get_post_place_list())

def get_customer(customer_id):
    query = "select * from customer where id = %s"
    return db.get_object_from_query(query, (customer_id))

def get_sql_and_values(table_name, form):
    values = list()
    if form.has_key("id") and form["id"] != "":
        customer_id = form["id"]
        sql = "update " + table_name + " set "
    else:
        sql = "insert into " + table_name + " ("

    for key in form:
        if key == "id":
            continue
        if form[key] == "":
            continue

        if form.has_key("id") and form["id"] != "":
            sql += "\n\t" + key + " = %s, "
        else:
            sql += "\n\t" + key + ", "

        values.append(form[key])

    # abort if no values are to be inserted/updated
    if len(values) == 0:
        return "no change", 304

    # remove the last comma
    sql = sql[:-2]

    if form.has_key("id") and form["id"] != "":
        sql += "\nwhere id = %s"
        values.append(customer_id)
    else:
        sql += ")"
        sql += "\nvalues ("
        for key in form:
            if key == "id":
                continue
            if form[key] == "":
                continue
            sql += "%s, "

        sql = sql[:-2] + ")"

    return sql, values


@app.route("/customer", methods=["POST", "PUT"])
def update_customer():
    # TODO customer_id dynamically
    customer_id=1
    sql, values = get_sql_and_values("customer", request.form)

    app.logger.debug(sql + "\n" + str(values))
    db.get_object_from_query(sql, tuple(values))
    customer = get_customer(customer_id)
    return render_template("customer.html", customer=customer), 201

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

def filter_suppress_none(value):
    if not value is None:
        return value
    else:
        return ""

if __name__ == '__main__':
    conf_data = atelier_conf.read_conf_from_file()
    app.jinja_env.filters["sn"] = filter_suppress_none
    db = AtelierDB(
        conf_data["db"]["host"],
        conf_data["db"]["user"],
        conf_data["db"]["password"],
        conf_data["db"]["db"],
    )
    app.run(debug=True)




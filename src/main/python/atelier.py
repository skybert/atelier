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
    customers = []

    customer_id = request.args.get("customer_id")
    if customer_id:
        app.logger.debug("customer_id=" + customer_id)
        customers.append(db.customer(customer_id))

    name = request.args.get("name")
    if name:
        customers = db.find_customers_by_name(name)

    return render_template("customer-list.html", customers=customers)

@app.route("/product-list")
def get_product_list():
    products = db.get_product_list()
    return render_template("product-list.html", products=products)

@app.route("/product/<id>", methods = ["GET"])
def product(id):
    product = db.get_product(id)
    return render_template("product.html", product=product)

@app.route("/customer/<id>", methods = ["GET"])
def get_customer(id):
    customer = db.customer(id)
    # TODO customer not found 404
    return render_template("customer.html",
                           customer=customer,
                           post_place_list=db.get_post_place_list()), 200

@app.route("/customer", methods=["POST", "PUT"])
def update_customer():
    customer = db.update_customer(request_form)
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
        return value.decode('utf-8')
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
        app.logger
    )
    app.run(debug=True)




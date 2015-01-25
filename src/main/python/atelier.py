#! pyenv/bin/python

from datetime import datetime

import flask
from flask import Flask
from flask import render_template
from flask import request

import MySQLdb as mdb

from atelier_db import AtelierDB
import atelier_conf
from atelier_filters import filter_suppress_none

app = Flask(__name__, static_url_path="/files", static_folder="files")

## Search
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

## Product
@app.route("/product-list")
def get_product_list():
    products = db.get_product_list()
    return render_template("product-list.html", products=products)

@app.route("/product/<id>", methods = ["GET"])
def product(id):
    product = db.product(id)
    return render_template("product.html", product=product)

## Customer
@app.route("/customer/<id>", methods = ["GET"])
def get_customer(id):
    # TODO get_customer customer not found -> 404
    customer = db.customer(id)
    order_list = db.customer_order_list(id)
    post_place_list = db.get_post_place_list()
    return render_template("customer.html",
                           customer=customer,
                           order_list=order_list,
                           post_place_list=post_place_list), 200

@app.route("/customer", methods=["POST", "PUT"])
def update_customer():
    customer = db.update_customer(request.form)
    return render_template("customer.html", customer=customer), 201

def clone_form_and_add_creation_date(form):
    result = form.copy()
    result["creation_date"] = datetime.now()
    return result

## Order related functions
@app.route("/order", methods = ["POST"])
def new_order():
    """
    Creates a new order.
    Works on the a request.form object
    """
    form = clone_form_and_add_creation_date(request.form)
    order_id = db.create_order(form)
    customer = db.customer(form["customer_id"])
    order = db.order(order_id)
    ## TODO make new_order 201 > Location /order/<order_id>
    return get_order(order_id)


@app.route("/order-item/<id>", methods = ["GET"])
def get_order_item(id):
    order_item = db.order_item(id)
    return render_template("order-item.html", order_item=order_item)

@app.route("/order-item/<id>/delete", methods = ["POST"])
def delete_order_item(id):
    order_item = db.order_item(id)
    order = db.order(order_item["order_id"])
    db.delete_order_item(id)
    return render_template("order.html", order=order)

@app.route("/order/<id>", methods = ["GET"])
def get_order(id):
    order = db.order(id)
    customer = db.customer(order["customer_id"])
    order_item_list = db.order_item_list(id)
    app.logger.debug("order_item_list=" + str(order_item_list))

    return render_template("order.html",
                           order=order,
                           customer=customer,
                           order_item_list=order_item_list,
                           product_list=db.get_product_list())

@app.route("/order/<id>/item-list", methods = ["POST"])
def add_order_item(id):
    ## TODO add_order_item make it possible to add order items
    form = clone_form_and_add_creation_date(request.form)
    form["order_id"] = id

    ## TODO add_order_item calculate total_amount
    order_item_id = db.add_order_item(form)
    return get_order(id)

## Various
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


## Main
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




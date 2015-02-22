#! pyenv/bin/python

from datetime import datetime

import flask
from flask import Flask
from flask import redirect
from flask import render_template
from flask import request
from flask import url_for

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
def get_product(id):
    product = db.product(id)
    return render_template("product.html", product=product)

@app.route("/product/<id>", methods = ["POST"])
def update_product(id):
    ## TODO update_product: implement data layer
    product = db.update_product(request.form)
    return redirect(url_for("get_product", id = id))

## Customer
@app.route("/customer/<id>", methods = ["GET"])
def get_customer(id):
    # TODO get_customer: customer not found -> 404
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
@app.route("/order/<id>", methods=["POST", "PUT"])
def update_order(id):
    ## TODO update_order: make it work ;-)
    order = db.update_order(request.form)
    return render_template("order.html", order=order), 200

@app.route("/order", methods = ["POST"])
def new_order():
    """
    Creates a new order.
    Works on the a request.form object
    """
    form = clone_form_and_add_creation_date(request.form)
    order_id = db.create_order(form)

    return redirect(url_for("get_order", id = order_id))


@app.route("/order-item/<id>", methods = ["GET"])
def get_order_item(id):
    order_item = db.order_item(id)
    return render_template("order-item.html", order_item=order_item)

@app.route("/order-item/<id>/delete", methods = ["POST"])
def delete_order_item(id):
    order_item = db.order_item(id)
    db.delete_order_item(id)
    return redirect(url_for("get_order", id = order_item["order_id"]))

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
    form = clone_form_and_add_creation_date(request.form)
    form["order_id"] = id

    ## TODO add_order_item:  calculate total_amount on item and order
    db.add_order_item(form)
    return redirect(url_for("get_order", id = id))

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




#! pyenv/bin/python

from datetime import datetime
from datetime import timedelta
from decimal import Decimal
from locale import LC_ALL
from locale import setlocale

import flask
from flask import Flask
from flask import abort
from flask import redirect
from flask import render_template
from flask import request
from flask import url_for

from atelier_db import AtelierDB
import atelier_conf
from atelier_filters import filter_iso_date
from atelier_filters import filter_object_suppress_none
from atelier_filters import filter_suppress_none

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
        customers.append(db.get_customer(customer_id))

    name = request.args.get("name")
    if name:
        customers = db.find_customers_by_name(name)

    return render_template("customer-list.html", customers=customers)

@app.route("/product-list")
def get_product_list():
    products = db.get_product_list()
    if products == None:
        abort(404)
    return render_template("product-list.html", products=products)

@app.route("/product/<id>", methods = ["GET"])
def get_product(id):
    product = db.get_product(id)
    if product == None:
        abort(404)
    return render_template("product.html",
                           product=product,
                           product_type_list=db.get_product_type_list())

@app.route("/product/<id>", methods = ["POST"])
def update_product(id):
    product = db.update_product(request.form)
    return redirect(url_for("get_product", id = id))

@app.route("/product/<id>/delete", methods = ["POST"])
def delete_product(id):
    """Deletes order only if it's not being used"""
    # TODO delete_product only if it's not being used
    db.delete_product(id)

@app.route("/customer/<id>", methods = ["GET"])
def get_customer(id, updated = False):
    customer = db.get_customer(id)
    if customer == None:
        abort(404)
    order_list = db.get_customer_order_list(id)
    post_place_list = db.get_post_place_list()
    return render_template("customer.html",
                           customer=customer,
                           order_list=order_list,
                           post_place_list=post_place_list), 200

@app.route("/customer", methods = ["GET"])
def get_new_customer_form():
    return render_template("new-customer.html",
                           post_place_list=db.get_post_place_list()), 200

@app.route("/customer", methods = ["POST"])
def new_customer():
    form = clone_form_and_add_creation_date(request.form)
    id = db.create_customer(form)
    return redirect(url_for("get_customer", id = id))


@app.route("/customer/<id>", methods=["POST", "PUT"])
def update_customer(id):
    db.update_customer(request.form)
    return redirect(url_for("get_customer", id = id, updated = True))

def clone_form_and_add_creation_date(form):
    result = form.copy()
    result["creation_date"] = datetime.now()
    return result

def clone_form_and_add_updated_date(form):
    result = form.copy()
    result["updated_date"] = datetime.now()
    return result

@app.route("/order/<id>", methods=["POST", "PUT"])
def update_order(id):
    form = clone_form_and_add_updated_date(request.form)
    form["id"] = id
    db.update_order(form)
    return redirect(url_for("get_order", id = id))

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
    order_item = db.get_order_item(id)
    return render_template("order-item.html",
                           order_item=order_item,
                           product_list=db.get_product_list())

@app.route("/order-item/<id>", methods = ["POST", "PUT"])
def update_order_item(id):
    form = request.form.copy()
    form["id"] = id
    db.update_order_item(form)
    # TODO update_order_item update order item total amount
    # TODO update_order_item update order total amount
    return redirect(url_for("get_order_item", id=id))

@app.route("/order-item/<id>/delete", methods = ["POST"])
def delete_order_item(id):
    """
    Deletes the order item and updates the total amount of the order.
    """
    order_item = db.get_order_item(id)
    order_item_total = order_item["total_amount"]
    order_id = order_item["order_id"]
    db.delete_order_item(id)
    db.update_order_total(order_id, (order_item_total * -1))
    return redirect(url_for("get_order", id = order_item["order_id"]))

@app.route("/order/<id>", methods = ["GET"])
def get_order(id):
    order = db.get_order(id)
    if order == None:
        abort(404)

    if order["total_amount"] is None:
        order["total_amount"] = Decimal(0)
    if order["paid_amount"] is None:
        order["paid_amount"] = Decimal(0)

    customer = db.get_customer(order["customer_id"])
    order_item_list = db.get_order_item_list(id)

    return render_template("order.html",
                           order=order,
                           customer=customer,
                           order_item_list=order_item_list,
                           product_list=db.get_product_list())

@app.route("/order/<id>/item-list", methods = ["POST"])
def add_order_item(id):
    form = clone_form_and_add_creation_date(request.form)
    form["order_id"] = id
    db.add_order_item(form)
    return redirect(url_for("get_order", id = id))

@app.route("/reports/order-overview")
def order_overview():
    from_date = request.args.get("from_date")
    to_date = request.args.get("to_date")
    selected_product_list = request.args.getlist("selected_product_list")

    if from_date is None:
        from_date = datetime.today() - timedelta(days=30)
    if to_date is None:
        to_date = datetime.today()

    product_list = db.get_product_list()
    order_list, product_count_list = db.get_order_list(from_date,
                                                       to_date,
                                                       selected_product_list)
    return render_template("reports/order-overview.html",
                           from_date=from_date,
                           to_date=to_date,
                           product_list=product_list,
                           order_list=order_list,
                           product_count_list=product_count_list)

@app.route("/about")
def about():
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

@app.errorhandler(404)
def page_not_found(error):
    return render_template("error/404.html"), 404

if __name__ == '__main__':
    conf_data = atelier_conf.read_conf_from_file()
    app.jinja_env.filters["sn"] = filter_suppress_none
    app.jinja_env.filters["sdn"] = filter_object_suppress_none
    app.jinja_env.filters["iso_date"] = filter_iso_date
    db = AtelierDB(
        conf_data["db"]["host"],
        conf_data["db"]["user"],
        conf_data["db"]["password"],
        conf_data["db"]["db"],
        app.logger
    )
    setlocale(LC_ALL, str(conf_data["locale"]))
    app.run(debug=True)




#! /usr/bin/env python

from datetime import datetime
from datetime import timedelta
from decimal import Decimal
from locale import LC_ALL
from locale import setlocale
from logging import FileHandler
from logging import Formatter
import logging

from flask import Flask
from flask import abort
from flask import redirect
from flask import render_template
from flask import request
from flask import url_for

from atelier_date import get_datetime_or_past_datetime
from atelier_db import AtelierDB
from atelier_filters import *
from reverse_proxied import ReverseProxied
import atelier_conf

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
        customers.append(db.get_customer(customer_id))

    old_customer_id = request.args.get("old_customer_id")
    if old_customer_id:
        customers.extend(db.get_customer_by_old_id(old_customer_id))

    name = request.args.get("name")
    if name:
        customers.extend(db.find_customers_by_name(name))

    return render_template("customer-list.html", customers=customers)

@app.route("/product-list")
def get_product_list():
    products = db.get_product_list()
    if products == None:
        abort(404)
    return render_template("product-list.html", products=products)

@app.route("/product", methods = ["GET"])
def get_new_product_form():
    return render_template("product.html",
                           product={},
                           product_type_list=db.get_product_type_list())


@app.route("/product/<id>", methods = ["GET"])
def get_product(id):
    product = db.get_product(id)
    if product == None:
        abort(404)
    return render_template("product.html",
                           product=product,
                           product_type_list=db.get_product_type_list())

@app.route("/product", methods = ["POST"])
@app.route("/product/", methods = ["POST"])
def create_product():
    form = clone_form_and_add_creation_date(request.form)
    id = db.create_product(form)
    return redirect(url_for("get_product", id = id))

@app.route("/product/<id>", methods = ["POST"])
def update_product(id):
    db.update_product(request.form)
    return redirect(url_for("get_product", id = id))

@app.route("/product/<id>/delete", methods = ["GET"])
def get_product_delete_page(id):
    product = db.get_product(id)
    order_item_list = db.get_order_item_list_by_product_id(id)
    return render_template("delete-product.html",
                           product = product,
                           order_item_list = order_item_list)

@app.route("/product/<id>", methods = ["DELETE"])
@app.route("/product/<id>/delete", methods = ["POST"])
def delete_product(id):
    """Deletes order only if it's not being used"""
    # TODO delete_product add confirmation screen for

    # IMPROVEMENT delete_product only if it's not being used (right
    # now, the DB constraints and Flask error handler takes care of
    # this).
    db.delete_product(id)
    return redirect(url_for("get_product_list"))

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
    form = clone_form_and_add_updated_date(request.form)
    form["id"] = id

    if len(form.getlist("marketing_allowed")) == 0:
        form["marketing_allowed"] = 0
    if len(form.getlist("newspaper_allowed")) == 0:
        form["newspaper_allowed"] = 0
    if len(form.getlist("internet_allowed")) == 0:
        form["internet_allowed"] = 0

    db.update_customer(form)
    return redirect(url_for("get_customer", id = id, updated = True))

@app.route("/customer/<id>/delete", methods = ["GET"])
def get_customer_delete_page(id):
    customer = db.get_customer(id)
    order_list = db.get_customer_order_list(id)
    return render_template("delete-customer.html",
                           customer = customer,
                           order_list = order_list)

@app.route("/customer/<id>", methods = ["DELETE"])
@app.route("/customer/<id>/delete", methods = ["POST"])
def delete_customer(id):
    """
    Deletes a customer. Only possible if there customer doesn't have any orders
    """
    db.delete_customer(id)
    return render_template("search.html")

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
    values = request.form.copy()
    values["id"] = id
    set_total_amount(values)
    db.update_order_item(values)
    return redirect(url_for("get_order_item", id=id))

@app.route("/order-item/<id>/delete", methods = ["GET"])
def get_order_item_delete_page(id):
    order_item = db.get_order_item(id)
    order = db.get_order(order_item["order_id"])
    return render_template("delete-order-item.html",
                           order_item = order_item,
                           order = order)

@app.route("/order-item/<id>", methods = ["DELETE"])
@app.route("/order-item/<id>/delete", methods = ["POST"])
def delete_order_item(id):
    """
    Deletes the order item and updates the total amount of the order.
    """
    order_item = db.get_order_item(id)
    order_id = order_item["order_id"]

    db.delete_order_item(id)
    update_delivery_date(order_id)
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
    payment_type_list = db.get_payment_type_list()
    order_status_list = db.get_order_status_list()
    invoice_id_list = db.get_invoice_id_list_by_order_id(id)

    return render_template(
        "order.html",
        order = order,
        customer = customer,
        order_item_list = order_item_list,
        product_list = db.get_product_list(),
        payment_type_list = payment_type_list,
        order_status_list = order_status_list,
        invoice_id_list = invoice_id_list
    )

@app.route("/print/order/<id>", methods = ["GET"])
def get_printable_order(id):
    order = db.get_order(id)
    if order == None:
        abort(404)

    if order["total_amount"] is None:
        order["total_amount"] = Decimal(0)
    if order["paid_amount"] is None:
        order["paid_amount"] = Decimal(0)

    customer = db.get_customer(order["customer_id"])
    order_item_list = db.get_order_item_list(id)
    payment_type_list = db.get_payment_type_list()
    order_status_list = db.get_order_status_list()

    return render_template("print-order.html",
                           order = order,
                           customer = customer,
                           order_item_list = order_item_list,
                           product_list = db.get_product_list(),
                           payment_type_list = payment_type_list,
                           order_status_list = order_status_list)


@app.route("/order/<id>/delete", methods = ["GET"])
def get_order_delete_page(id):
    order = db.get_order(id)
    return render_template("delete-order.html", order = order)

@app.route("/order/<id>", methods = ["DELETE"])
@app.route("/order/<id>/delete", methods = ["POST"])
def delete_order(id):
    customer = db.get_customer(db.get_customer_id_by_order_id(id))
    db.delete_order(id)
    message = "Ordre numer " + id + " slettet"
    return render_template("customer.html",
                           customer = customer,
                           message = message)

def update_delivery_date(order_id):
    order = db.get_order(order_id)
    production_time = db.get_order_production_time(order_id)["production_time"]
    # IMPROVEMENT update_delivery_date more clever base date than the
    # order's creation date
    if production_time is not None:
        delivery_date = order["creation_date"] + timedelta(days = production_time)
    else:
        delivery_date = order["creation_date"]

    values = {
        "id" : order_id,
        "delivery_date" : delivery_date
    }
    db.update_order(values)

def set_total_amount(values):
    """
    Sets the total amount based on three values present in the value
    object: product_id, number_of_items and discount. The calee is
    responsible for having all three values present in the value
    object.

    """
    price = db.get_product_price(values["product_id"])
    total =  (price *  int(values["number_of_items"]))
    discount = Decimal(values["discount"])
    values["total_amount"] = total - (total *  discount / 100)

@app.route("/order/<order_id>/item-list", methods = ["POST"])
def add_order_item(order_id):
    values = clone_form_and_add_creation_date(request.form)
    values["order_id"] = order_id
    if int(values["total_amount"]) == 0:
        set_total_amount(values)

    db.add_order_item(values)
    update_delivery_date(order_id)
    return redirect(url_for("get_order", id = order_id))

@app.route("/invoice/<id>", methods = ["GET"])
def get_invoice(id):
    invoice = db.get_invoice(id)
    if invoice == None:
        abort(404)
    order = db.get_order(invoice["order_id"])
    customer = db.get_customer(invoice["customer_id"])
    order_item_list = db.get_order_item_list(order["id"])

    total_amount = Decimal(0)
    for order_item in order_item_list:
      total_amount += order_item["total_amount"]
    total_tax = total_amount * Decimal(0.25)
    sum_exclusive_tax = total_amount - total_tax

    return render_template(
        "invoice.html",
        invoice = invoice,
        order = order,
        customer = customer,
        order_item_list = order_item_list,
        sum_exclusive_tax = sum_exclusive_tax,
        total_amount = total_amount,
        total_tax = total_tax
    )

@app.route("/invoice/of/<order_id>", methods = ["GET"])
def create_invoice_request(order_id):
    order = db.get_order(order_id)
    customer = db.get_customer(order["customer_id"])
    order_item_list = db.get_order_item_list(order["id"])

    return render_template(
        "invoice.html",
        invoice = {},
        order = order,
        customer = customer,
        order_item_list = order_item_list
    )

@app.route("/invoice", methods = ["POST"])
@app.route("/invoice/", methods = ["POST"])
def create_invoice():
    form = clone_form_and_add_creation_date(request.form)
    id = db.create_invoice(form)
    return redirect(url_for("get_invoice", id = id))

@app.route("/invoice/<id>", methods=["POST", "PUT"])
def update_invoice(id):
    form = clone_form_and_add_updated_date(request.form)
    form["id"] = id

    if len(form.getlist("paid")) == 0:
        form["paid"] = 0
    if len(form.getlist("tax_included")) == 0:
        form["tax_included"] = 0

    db.update_invoice(form)
    return redirect(url_for("get_invoice", id = id, updated = True))

@app.route("/invoice/<id>/delete", methods = ["GET"])
def get_invoice_delete_page(id):
    invoice = db.get_invoice(id)
    return render_template("delete-invoice.html", invoice = invoice)

@app.route("/invoice/<id>", methods = ["DELETE"])
@app.route("/invoice/<id>/delete", methods = ["POST"])
def delete_invoice(id):
    invoice = db.get_invoice(id)
    order = db.get_order(invoice["order_id"])
    customer = db.get_customer(order["customer_id"])
    db.delete_invoice(id)
    message = "Faktura med numer " + id + " slettet"

    return redirect(url_for("get_order", id = order["id"]))

@app.route("/reports/invoice-overview")
def invoice_overview():
    from_date = get_datetime_or_past_datetime(
        request.args.get("from_date"), 30)
    to_date = get_datetime_or_past_datetime(
        request.args.get("to_date"), 0)
    invoice_list, total_amount = db.get_invoice_list(from_date, to_date)
    return render_template(
        "reports/invoice-overview.html",
        from_date = from_date,
        to_date = to_date,
        invoice_list = invoice_list,
        total_amount = total_amount
    )

@app.route("/reports/order-overview")
def order_overview():
    from_date = get_datetime_or_past_datetime(request.args.get("from_date"), 30)
    to_date = get_datetime_or_past_datetime(request.args.get("to_date"), 0)
    selected_product_list = request.args.getlist("selected_product_list")

    product_list = db.get_product_list()
    order_list, product_count_list, total_amount = db.get_order_list(
        from_date,
        to_date,
        selected_product_list)

    return render_template("reports/order-overview.html",
                           from_date=from_date,
                           to_date=to_date,
                           product_list=product_list,
                           order_list=order_list,
                           product_count_list=product_count_list,
                           total_amount=total_amount)

@app.route("/reports/sessions-without-further-orders")
def sessions_without_further_orders():
    from_date = get_datetime_or_past_datetime(request.args.get("from_date"), 45)
    to_date = get_datetime_or_past_datetime(request.args.get("to_date"), 14)

    product_type_enlargement = 1L
    product_type_photo_session = 4L
    order_list = db.get_order_list_without_product_type(from_date,
                                                        to_date,
                                                        product_type_photo_session,
                                                        product_type_enlargement)

    return render_template("reports/sessions-without-further-orders.html",
                           from_date=from_date,
                           to_date=to_date,
                           order_list=order_list)

@app.route("/reports/promise-list")
def get_promise_list():
    from_date = get_datetime_or_past_datetime(request.args.get("from_date"), 0)
    to_date = get_datetime_or_past_datetime(request.args.get("to_date"), -14)

    order_list = db.get_promise_list(from_date, to_date)
    return render_template("reports/promise-list.html",
                           from_date = from_date,
                           to_date = to_date,
                           order_list = order_list)

@app.route("/reports/monthly-comparison")
def get_monthly_revenue_comparison():
    l = db.get_monthly_revenue_comparison_list()
    return render_template("reports/monthly-comparison.html", comparison_list = l)

@app.errorhandler(404)
def page_not_found(error):
    return render_template("error/404.html", error=error), 404

@app.errorhandler(500)
def internal_server_error(error):
    return render_template("error/500.html", error=error), 500

if __name__ == '__main__':
    conf_data = atelier_conf.read_conf_from_file()
    app.jinja_env.filters["sn"] = filter_suppress_none
    app.jinja_env.filters["sdn"] = filter_object_suppress_none
    app.jinja_env.filters["iso_date"] = filter_iso_date
    app.jinja_env.filters["number_of_days"] = filter_number_of_days
    app.jinja_env.filters["boolean_to_yes_no"] = filter_boolean_to_yes_no
    app.jinja_env.filters["compact_norwegian_date"] = filter_compact_norwegian_date
    db = AtelierDB(
        conf_data["db"]["host"],
        conf_data["db"]["user"],
        conf_data["db"]["password"],
        conf_data["db"]["db"],
        app.logger
    )

    if "log_dir" in conf_data:
        log_dir = conf_data["log_dir"]
    else:
        log_dir = "/var/log/studio-s"
    
    setlocale(LC_ALL, str(conf_data["locale"]))
    app.wsgi_app = ReverseProxied(app.wsgi_app)

    if not app.debug:
        file_handler = FileHandler(log_dir + "/" + "atelier.log")
        file_handler.setLevel(logging.DEBUG)
        file_handler.setFormatter(Formatter(
            '%(asctime)s %(levelname)s: %(message)s '
            '[in %(pathname)s:%(lineno)d]'
        ))
        app.logger.addHandler(file_handler)

    # TODO make debug mode configurable
    app.run(
        host='0.0.0.0',
        debug=True
    )

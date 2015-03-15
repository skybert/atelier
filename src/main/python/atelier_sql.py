def get_sql_and_values(table_name, form):
    values = list()
    if form.has_key("id") and form["id"] != "":
        id = form["id"]
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
        values.append(id)
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

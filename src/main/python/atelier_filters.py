from datetime import datetime

def filter_suppress_none(value):
    if not value is None:
        if  isinstance(value, basestring):
            return value.decode('utf-8')
        elif isinstance(value, datetime) and value.year > 1900:
            return value.strftime("%A %d %B %Y").decode('utf-8')
        else:
            return value
    else:
        return ""

def filter_object_suppress_none(value):
    if not value is None:
        return value
    else:
        return ""


def filter_iso_date(value):
    if value is None:
        return ""
    if isinstance(value, datetime) and value.year > 1900:
        return value.strftime("%Y-%m-%d")
    else:
        return value

def filter_number_of_days(value):
    return str(value.days)


def filter_boolean_to_yes_no(value):
    if value:
        return "Ja"
    else:
        return "Nei"

def filter_compact_norwegian_date(value):
    if isinstance(value, datetime):
        return value.strftime("%d.%m.%Y")
    else:
        return value

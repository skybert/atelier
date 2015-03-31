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
    if not value is None and isinstance(value, datetime):
        return value.strftime("%Y-%m-%d")
    else:
        return ""




from datetime import datetime

def filter_suppress_none(value):
    if not value is None:
        if  isinstance(value, basestring):
            return value.decode('utf-8')
        elif isinstance(value, datetime):
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


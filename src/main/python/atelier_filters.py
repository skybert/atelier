
def filter_suppress_none(value):
    if not value is None:
        if  isinstance(value, basestring):
            return value.decode('utf-8')
        else:
            return value
    else:
        return ""

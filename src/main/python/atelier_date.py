from datetime import datetime
from datetime import timedelta

def iso_to_datetime(iso_date):
    return datetime.strptime(iso_date, "%Y-%m-%d")

def get_datetime_or_past_datetime(iso_date, days_in_the_past):
    if iso_date == None:
        return datetime.today() - timedelta(days=days_in_the_past)
    else:
        return iso_to_datetime(iso_date)


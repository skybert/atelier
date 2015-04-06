import json
import sys

default_conf_file = "atelier-conf.json"

def read_conf_from_file():
    conf_data = ""

    with open(default_conf_file) as conf_file:
        try:
            conf_data = json.load(conf_file)
        except ValueError, e:
            print "Something wrong in", default_conf_file, ":-(", "Exiting."
            print e
            sys.exit(1)

    return conf_data


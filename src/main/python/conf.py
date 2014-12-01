import json

default_conf_file = "atelier-conf.json"

def read_conf_from_file():
    with open(default_conf_file) as conf_file:
        conf_data = json.load(conf_file)
    return conf_data


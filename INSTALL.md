# Installation notes
- NOT finished


## OS & python packages

```
# apt-get install mariadb-server mariadb-client
# apt-get install python-pip
# apt-get install python-mysqldb
# pip install flask
```

## Database

```
mysql> create database foodb character set utf8 collate utf8_general_ci;
mysql> grant all on foodb.* to foouser@'%' identified by 'foopassword';
mysql> grant all on foodb.* to foouser@'localhost' identified by 'foopassword';

```

## Configuration file

Ensure you've got a valid `atelier-conf.json` file in the diretory
where you start up atelier:

```
{
    "db": {
        "host": "localhost",
        "user": "foouser",
        "password": "foopassword",
        "db" : "foodb"
    },
    "locale": "nb_NO.utf8"
}
```

Download `Chart.js`:
```
$ cd src/main/python/files
$ wget https://raw.githubusercontent.com/nnnick/Chart.js/master/Chart.js
```


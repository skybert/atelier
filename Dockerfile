from debian:latest

run export DEBIAN_FRONTEND=noninteractive && \
    apt-get update && \
    apt-get install -y \
    mariadb-client \
    python-pip \
    python-mysqldb \
    wget
run pip install flask

copy src/main/python /opt/atelier
copy src/main/bash/atelier-endpoint /usr/sbin/
copy src/main/mysql /var/spool/mysql

run mkdir -p /opt/atelier/files/js

#     wget https://raw.githubusercontent.com/nnnick/Chart.js/master/Chart.js \
# -O /opt/atelier/files/js/Chart.js

cmd /usr/sbin/atelier-endpoint

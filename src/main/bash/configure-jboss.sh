#! /usr/bin/env bash

# Command which adds JDBC driver as JBoss module and sets up a
# datasource using it. All operations are accomplished using the
# command line interface shipped with JBoss >= 7.

# by torstein.k.johansen@conduct.no

jboss_dir=/opt/wildfly-8.1.0.CR1
ds_name=atelierdb
db_vendor=mysql
db_user=atelieruser
db_password=atelierpassword
db_driver_module=com.mysql.jdbc
db_connection_url=jdbc:mysql://localhost/atelierdbRR

function get_db_driver_jar() {
  local candiates=$(
    find $(dirname $0)/../../../target/ -name ${db_vendor}*.jar | head -1
  )

  # TODO checks, more than one, zero and so on.

  echo $candiates
}

function add_jdbc_driver() {
  ${jboss_dir}/bin/jboss-cli.sh --connect <<EOF
module add \
  --name=${db_driver_module} \
  --resources=${get_db_driver_jar} \
  --dependencies=javax.api

/subsystem=datasources/jdbc-driver=${db_vendor}:add( \
  driver-name=${db_vendor}, \
  driver-module-name=${db_driver_module} \
)
EOF
}

function add_datasource() {
  ${jboss_dir}/bin/jboss-cli.sh --connect <<EOF
data-source add \
--name=${ds_name} \
--jndi-name="java:/jdbc/${ds_name}" \
--connection-url="${db_connection_url}" \
--driver-name="${db_vendor}" \
--user-name="${db_user}" \
--password="${db_password}" \
--idle-timeout-minutes="0"

data-source enable --name=${ds_name}
EOF
}

add_jdbc_driver
add_datasource


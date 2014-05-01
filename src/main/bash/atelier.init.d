#! /usr/bin/env bash

# $Id: atelier.init.d,v 1.1 2008/11/08 11:41:25 torstein Exp $

atelier_script=/home/torstein/projects/atelier/src/main/bash/atelier

run_dir=/var/run/studio-s
log_dir=/var/log/studio-s
tomcat_dir=/opt/tomcat

user=www-data

if [ ! -d $run_dir  ]; then
    mkdir -p $run_dir
fi

if [ ! -d $log_dir  ]; then
    mkdir -p $log_dir
fi

chown -R $user:$user $run_dir/
chown -R $user:$user $log_dir/
chown -R $user:$user $tomcat_dir/

su - $user -c "bash $atelier_script $@"


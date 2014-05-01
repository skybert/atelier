#! /usr/bin/evn bash

# $Id: deploy.sh,v 1.1 2008/11/08 11:41:25 torstein Exp $

src_dir=$HOME/projects/atelier
dest_dir=leica.doesntexist.org:/opt/tomcat/webapps
webapp=atelier-0.1.war

cd $src_dir
mvn clean compile war:war
scp target/$webapp $dest_dir/



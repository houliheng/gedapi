#!/usr/bin/env bash

# restart for mdm application

if [ -z $CONFIG_SERVER ]; then
  echo "ERROR: Environment variable CONFIG_SERVER is not set."
  exit 1
fi
echo "Using CONFIG_SERVER: $CONFIG_SERVER"

if [ -z $JAVA_HOME ]; then
  echo "ERROR: JAVA_HOME is not found in your Environment."
  exit 1
fi
echo "Using JAVA_HOME: $JAVA_HOME"

# the path of the application installed.
APP_HOME=$(cd "`dirname $0`/.."; pwd)
echo "Using APP_HOME: $APP_HOME"

num=`ps -ef|grep "org.springframework.boot.loader.JarLauncher" |grep -v grep | awk '{print $2}'`
if [ -z $num ]; then
   $APP_HOME/bin/startup.sh
else
   kill -9 $num
   $APP_HOME/bin/startup.sh
fi

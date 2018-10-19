#!/usr/bin/env bash

#startup for mdm application

APP_NAME=gedapi

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

# add jar to classpath
CP=$CLASSPATH
for JAR in $APP_HOME/lib/*.jar
do
    CP=$CP:$JAR
done

echo "CLASSPATH: $CP"

#Dynamic Calculate Available Heap
maxFreeNeeded=2000

# Assume here that file system cache is also "free"
freeMem=`free -m | grep 'buffers/cache' | awk '{print $4}'`
availMem=$freeMem

if [ $availMem -gt $maxFreeNeeded ]
then
    MAX_HEAP=4G
else
    MAX_HEAP=$(($availMem*9/10))
    MAX_HEAP=${MAX_HEAP}M
fi

JAVA_OPTS="-server -Xmx$MAX_HEAP -Xms$MAX_HEAP -Xmn256m -Xss256k -XX:+DisableExplicitGC  -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -Duser.timezone=GMT+8"

TOMCAT_BASE_DIR=/home/tomcat/servers/$APP_NAME

nohup $JAVA_HOME/bin/java -cp $CP -DCONFIG_SERVER=$CONFIG_SERVER $JAVA_OPTS -DAPP_HOME=$APP_HOME -Xloggc:$TOMCAT_BASE_DIR/logs/gc.log org.springframework.boot.loader.JarLauncher --server.port=8080 --server.tomcat.basedir=$TOMCAT_BASE_DIR --server.tomcat.access-log-enabled=true --server.tomcat.access-log-pattern="'remote' %{X-Forwarded-For}i 'lb' %h %t %r %s %b %D" > /dev/null 2>&1 &


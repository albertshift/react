#!/usr/bin/env bash

#export JAVA_HOME=

JAVA_OPTS="-server"
JAVA_OPTS="$JAVA_OPTS -Xms2048m -Xmx2048m -XX:MaxPermSize=256m"
#JAVA_OPTS="$JAVA_OPTS -Xdebug -Xrunjdwp:transport=dt_socket,address=9000,suspend=n,server=y"
#JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.port=8000 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
export JAVA_OPTS="$JAVA_OPTS -Xss412k"



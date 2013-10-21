#!/usr/bin/env bash

COMMANDS="start stop status deploy"
USAGE="Usage: $0 command -Dproperty=value -Dproperty=value"

if [ "$#" == "0" ]; then\
	echo "$USAGE"
	exit 1
fi

CMD=$1
shift
ARGS=$@

NotValidCommand() {
    for item in $COMMANDS; do
        if [ "$1" == "$item" ]; then
            return 1
        fi
    done
    return 0
}

if NotValidCommand $CMD; then
    echo "$USAGE"
    echo "Supported commands: $COMMANDS"
    exit 1
fi 

BIN=`dirname "$0"`
pushd $BIN/..

. bin/config.sh

echo "jopts=$JAVA_OPTS"
echo "jhome=$JAVA_HOME"

CLASSPATH='conf'

for f in lib/*.jar; do
  CLASSPATH=${CLASSPATH}:$f;
done

if [ "$JAVA_HOME" == "" ]; then
    JAVA=$JAVA_HOME/bin/java
else
    JAVA=java
fi

start() {
    $JAVA -server -classpath ${CLASSPATH} ${JAVA_OPTS} ${ARGS} com.shvid.react.cmd.StartCmd
}

stop() {
    $JAVA -server -classpath ${CLASSPATH} ${ARGS} com.shvid.react.cmd.StopCmd
}

status() {
    $JAVA -server -classpath ${CLASSPATH} ${ARGS} com.shvid.react.cmd.StatusCmd
}

deploy() {
    $JAVA -server -classpath ${CLASSPATH} ${ARGS} com.shvid.react.cmd.DeployCmd
}


case "$CMD" in

start)  start
    ;;
stop)  stop
    ;;
status)  status
    ;;
deploy) deploy
   ;;
*) echo "Unknown command $CMD"
   ;;
esac


popd



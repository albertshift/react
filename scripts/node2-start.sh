#!/usr/bin/env bash

SCRIPTS=`dirname "$0"`
pushd $SCRIPTS/..

mkdir target/node2

bash target/react-distr/bin/react.sh start -Dreact.node.dataDir=target/node2 -Dreact.node.name=Node2 -Dreact.node.bindPort=5301 -Dreact.server.bindPort=6301

popd

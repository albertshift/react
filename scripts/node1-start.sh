#!/usr/bin/env bash

SCRIPTS=`dirname "$0"`
pushd $SCRIPTS/..

mkdir target/node1

bash target/react-distr/bin/react.sh start -Dreact.node.dataDir=target/node1 -Dreact.node.name=Node1 -Dreact.node.bindPort=5300 -Dreact.server.bindPort=6300

popd


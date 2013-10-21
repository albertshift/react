#!/usr/bin/env bash

SCRIPTS=`dirname "$0"`
pushd $SCRIPTS/..

bash target/react-distr/bin/react.sh stop -Dreact.node.dataDir=target/node1

popd
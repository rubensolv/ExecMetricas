#!/bin/bash

BASEDIR=$(dirname "$(readlink -fn "$0")")

cd $BASEDIR

BASEDIR2=$(dirname "$(readlink -fn "$0")")"/bin"

(qsub -l nodes=1:STANDARD:ppn=1,mem=1gb -v FILENAME=$BASEDIR2 EvalSparcraftState.sh) &


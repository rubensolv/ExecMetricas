#!/bin/bash

BASEDIR=$(dirname "$(readlink -fn "$0")")

cd $BASEDIR

(qsub -l nodes=1:STANDARD:ppn=1,mem=1gb execJava.sh) &




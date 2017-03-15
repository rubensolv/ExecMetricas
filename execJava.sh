#!/bin/bash

# Set the limit to ~1 GB
ulimit -v 1000000

#PBS -q serial 
#PBS -l nodes=1:ppn=4,mem=1gb

BASEDIR=$(dirname "$(readlink -fn "$0")")

cd $BASEDIR

source /etc/profile.d/modules.sh 

module load java/jre1.7.0_67

#(qsub -l nodes=1:STANDARD:ppn=1,mem=1gb -v execMetricas.jar) &
java -jar execMetricas.jar



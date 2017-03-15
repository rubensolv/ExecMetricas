#!/bin/bash

# Set the limit to ~1 GB
ulimit -v 1000000

#set the time limit to 10 minutes
#ulimit -t 600

echo ${FILENAME}
cd ${FILENAME}

source /etc/profile.d/modules.sh 

module load sdl2/2.0.5
module load sdl2-image/2.0.1

# Path to which the results will be added
#RESULTS=results/topspin/dfid

./SparCraft ../sample_experiment/exp_linux_metrics.txt

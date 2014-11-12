source /usr/local/uvcdat/2.0.0/bin/setup_runtime.sh

export UVCDAT_ANONYMOUS_LOG=yes


ts_per_file=120

tot_ts=360


div=$(( $tot_ts / $ts_per_file  ))

var=ts

script=mk_tab.py



for n in `seq 1 $div` ; do

    /usr/bin/time sh run_parallel.sh $n $ts_per_file $1 $script $var  2>&1


done
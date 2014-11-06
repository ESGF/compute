
top=$(( $1 - 1 ))

seq 0 $top | parallel -j $1 -P $1 sh single_ingest.sh {} $2

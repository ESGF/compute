idx=$(( $1 - 1 )) 


start=$(( $idx * $2 ))



less=$(( $2 - 1 ))

top=$(( $start + $less )) 


seq $start $top | parallel -j $2 -P $2 sh single_ingest.sh {} $3 $4 $5

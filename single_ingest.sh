n=$1

filename=$2

python nc2ascii.py --file $filename --var cropFrac --timeType index --time $n | python add_head.py $n > $n.out

 
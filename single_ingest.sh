n=$1

filename=$2

bn=`basename .xml`

python nc2ascii.py --file $filename --var $4 --timeType index --time $n | python $3 $n  > ts-data/$filename.$n.out

 
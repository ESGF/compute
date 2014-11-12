count=1


for n in $1/* ; do

scp $n greyworm$count:/dev/shm/tmp 

count=$(( $count + 1 ))

if [ $count == 9 ] ; then
    count=1
fi

done
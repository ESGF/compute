source /usr/local/uvcdat/2.0.0/bin/setup_runtime.sh

export UVCDAT_ANONYMOUS_LOG=yes

for n in 2 4 8 16 24 32 64 96 120 160 240 300 360; do

echo -n "$n "

/usr/bin/time sh run_parallel.sh $n $1 2>&1

done
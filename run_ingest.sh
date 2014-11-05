

for n in `seq 0 120` 
do 

/usr/bin/time $HOME/build-uvcdat/install/bin/python nc2ascii.py --file /cmip5_css02/data/cmip5/output1/MIROC/MIROC4h/rcp45/mon/land/Lmon/r1i1p1/cropFrac/1/cropFrac_Lmon_MIROC4h_rcp45_r1i1p1_200601-201512.nc --var cropFrac --timeType index --time $n | python add_head.py $n > $n.out

done

 
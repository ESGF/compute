import cdms2,numpy

import sys

import argparse

p = argparse.ArgumentParser()

p.add_argument("--time",default=0,help="time to extract")
p.add_argument("--timeType",help="time to extract",default="date",choices=("index","date","value"))
p.add_argument("--levelType",help="level to extract",default="index",choices=("index","value"))
p.add_argument("--level",default=None,help="level to extract")
p.add_argument("--lat1",type=float,help="first latitude")
p.add_argument("--lat2",type=float,help="last lat")
p.add_argument("--lon1",type=float,help="first longitude")
p.add_argument("--lon2",type=float,help="last longitude")
p.add_argument("--file",required=True)
p.add_argument("--var",required=True)
p.add_argument("--format",default="%.6g",help="formatting of output text file")


p= p.parse_args(sys.argv[1:])

if p.timeType=="value":
    t=float(p.time)
elif p.timeType=="index":
    t=int(p.time)
    t=slice(t,t+1)
elif p.timeType=="date":
    t=p.time

kargs={"time":t,"squeeze":1}

f=cdms2.open(p.file)
V=f[p.var]
levs = V.getLevel()
if levs is not None:
    if level is None:
        raise RuntimeError("%s contains levels you need to provide a level" % p.var)
    if p.levelType=="index":
        kargs["level"]=int(p.level)
    else:
        kargs["level"]=float(p.level)

if p.lat1 is not None:
    if p.lat2 is None:
        raise RuntimeError("You need to provide both first and last latitude")
    kargs["latitude"]=(float(p.lat1),float(p.lat2),"ccn")
if p.lon1 is not None:
    if p.lon2 is None:
        raise RuntimeError("You need to provide both first and last longitude")
    kargs["longitude"]=(float(p.lon1),float(p.lon2),"ccn")

s=V(**kargs)

numpy.savetxt(sys.stdout,s.filled(1.e20),p.format)

import cdms2,sys,os,cdutil,MV2
f=cdms2.open(sys.argv[1])
V=f["ts"]

OUT = MV2.zeros((V.shape[0],))

for i in range(V.shape[0]):

  s=V[i]
  OUT[i]=cdutil.averager(s,axis="xy")

OUT.setAxis(0,V.getTime().clone())
OUT.id=V.id

print OUT


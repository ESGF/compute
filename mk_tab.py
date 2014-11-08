import sys

if 

if (len(sys.argv) > 2):

    var_name = sys.argv[2]

key = sys.argv[1]

l_count = 0

for line in sys.stdin:

    parts = line.strip().split()

    v_count = 0
    for val in parts:
        print key + " " + str(l_count) + " " + str(v_count) + " " val 
        v_count = v_count + 1
    l_count = l_count + 1

        


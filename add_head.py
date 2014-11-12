import sys

key = sys.argv[1]

count = 0

for line in sys.stdin:

    sys.stdout.write(key + " " + count +  " " + line)
    count = count + 1


import sys

key = sys.argv[1]

for line in sys.stdin:

    sys.stdout.write(key + " " + line)



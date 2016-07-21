# Sort

Sort engine takes in the data in whatever form, then tries different sort methods on it.

Default data is Large to small, dataSize to 1.

flags are:

-v = verbose: Show steps in sorting algorithm (Will affect time used)

-r = random: Generate random data in the range of 0 to 10*dataSize

-t = timed: Show the time taken by the sorting algorithm

-f = file: the next argument is a filename with one datum per line. Ignores -s,-r

-s = size: the next argument is an int for how large the data array should be
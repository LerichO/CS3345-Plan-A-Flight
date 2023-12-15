# CS3345 Data Structures and Algorithmic Analysis - Project: Plan a Flight

Original project assignment and course credited with permission to Dr. Kamran Ziaullah Khan's CS3345 course and the Erik Jonsson School of Engineering and Computer Science at UT Dallas.

This program was written for a project involving the representation of flight data using stack and graph data structures, as well as the use of Dijkstra's shortest path algorithm to determine all possible shortest paths from given flight data. 

## Data and Requested Flights

Flight data will be read from the lines of `FlightDataFile.txt`, where the first line denotes the number of flights to be input followed by each line containing individual flights formatted as `origin city|destination city|cost weight|time weight` with cost and time weights being integer values. An example input file is shown below:

<pre>
4
Dallas|austin|98|47
Austin|Houston|95|39
Dallas|Houston|101|51
Austin|Chicago|144|192
</pre>

Requested flight paths will be read from the lines of `PathsToCalculateFile.txt`, where the first line denotes the number of paths requested followed by each line containing individual requests formatted as `origin city|destination city|sort by time (T) or by cost (C)`. The top three paths will be calculated for each requested path, depending on if it is sorted by time or cost. An example input file is show below:

<pre>
2
Dallas|Houston|T
Chicago|Dallas|C
</pre>

## Execution

All java files and input files should be kept in the same directory before compilation and execution. Enter the following two commands in the directory to compile and execute the program; the `flightPlan` java class file will be run with the .txt file of flight data, .txt file of requested flights, and the name of the output file as arguments.

<pre>
$ javac flightPlan.java
$ java flightPlan FlightDataFile.txt PathsToCalculateFile.txt OutputFile.txt
</pre>

Upon execution, the program will write each requested flight path and their respective top three options into an output .txt file, denoting if they are sorted by time or dollar cost. If there is less than 3 possible flight paths then only the amount of flight paths available will be written; if there are no possible flight paths then an error message will be written noting that no path could be calculated. An example output file is shown below:
<pre>
Flight 1: Dallas, Houston (Time)
Path 1: Dallas -> Houston. Time: 51 Cost: 101.00
Path 2: Dallas -> Austin -> Houston. Time: 86 Cost: 193.00

Flight 2: Chicago, Houston (Time)
Path 1: Chicago -> Austin -> Dallas. Time: 237 Cost: 242.00
Path 2: Chicago -> Austin -> Houston -> Dallas. Time: 282 Cost: 340.00
</pre>

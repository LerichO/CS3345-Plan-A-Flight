# CS3345 Data Structures and Algorithmic Analysis - Project: Plan a Flight

Original project assignment and course credited to Dr. Kamran Ziaullah Khan's CS3345 course and the Erik Jonsson School of Engineering and Computer Science at UT Dallas.

This program was written for a project involving the representation of flight data using stack and graph data structures, as well as the use of Dijkstra's shortest path algorithm to determine all possible shortest paths from given flight data. 

## Data and Requested Flights

Flight data will be read from the lines of a `FlightDataFile.txt`, where the first line denoting the number of flights to be input followed by each line containing individual flights formatted as `origin city|destination city|cost weight|time weight` with cost and time weights being integer values. An example input file is shown below:
<pre>
4
Dallas|austin|98|47
Austin|Houston|95|39
Dallas|Houston|101|51
Austin|Chicago|144|192
</pre>

Requested flight paths

## Execution

<pre>
$ javac flightPlan.java
$ java flightPlan FlightDataFile.txt PathsToCalculateFile.txt OutputFile.txt
</pre>

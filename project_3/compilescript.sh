#! /bin/bash

# these two lines must be here to compile java code
javac PCB.java PriorityComparator.java ShortestJobComparator.java CPUScheduling.java &&
jar -cmf CPUScheduling.mf CPUScheduling.jar PCB.class PriorityComparator.class ShortestJobComparator.class CPUScheduling.class &&

# first come first serve algorithm - no pre-emption
java -cp CPUScheduling.jar CPUScheduling 1

# shortest job next algorithm - no pre-emption
java -cp CPUScheduling.jar CPUScheduling 2

# priority algorithm - no pre-emption
java -cp CPUScheduling.jar CPUScheduling 3

# round robin first come first serve algorithm with time quantum 10
java -cp CPUScheduling.jar CPUScheduling 4

# round robin first come first serve algorithm with time quantum 100
java -cp CPUScheduling.jar CPUScheduling 5

# round robin first come first serve algorithm with time quantum 1000
java -cp CPUScheduling.jar CPUScheduling 6

# round robin shortest job next algorithm with time quantum 50
java -cp CPUScheduling.jar CPUScheduling 7

# round robin priority algorithm with time quantum 50
java -cp CPUScheduling.jar CPUScheduling 8
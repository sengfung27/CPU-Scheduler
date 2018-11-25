#! /bin/bash

javac PCB.java PriorityComparator.java ShortestJobComparator.java CPUScheduling.java FirstComeFirstServeComparator.java &&
jar -cmf CPUScheduling.mf CPUScheduling.jar PCB.class PriorityComparator.class ShortestJobComparator.class CPUScheduling.class FirstComeFirstServeComparator.class &&

# first come first serve algorithm - no pre-emption
java -cp CPUScheduling.jar CPUScheduling 1

# shortest job next algorithm - no pre-emption
java -cp CPUScheduling.jar CPUScheduling 2

# priority algorithm - no pre-emption
java -cp CPUScheduling.jar CPUScheduling 3
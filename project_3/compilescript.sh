#! /bin/bash

javac PCB.java ShortestJobComparator.java CPUScheduling.java FirstComeFirstServeComparator.java &&
jar -cmf CPUScheduling.mf CPUScheduling.jar PCB.class ShortestJobComparator.class CPUScheduling.class FirstComeFirstServeComparator.class  &&

# first come first serve algorithm
java -cp CPUScheduling.jar CPUScheduling 1

# shortest job next algorithm
java -cp CPUScheduling.jar CPUScheduling 2


#! /bin/bash

javac PCB.java CPUScheduling.java &&
jar -cmf CPUScheduling.mf CPUScheduling.jar PCB.class CPUScheduling.class &&
java -cp CPUScheduling.jar CPUScheduling first


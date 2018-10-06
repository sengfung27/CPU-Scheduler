#! /bin/bash

javac PCB.java PriorityComparator.java Scheduler.java CpuEmulator.java &&
jar -cmf Scheduler.mf Scheduler.jar PCB.class PriorityComparator.class Scheduler.class &&
jar -cmf CpuEmulator.mf CpuEmulator.jar PCB.class CpuEmulator.class &&
gcc -o init init.c &&




## Default: Round Robin Scheduling
./init

## Optional: Priority First Scheduling
./init priority

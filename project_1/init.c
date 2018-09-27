#include <stdio.h>
#include <stdlib.h>
#include <wait.h>
#include <unistd.h>

int main()
{
    int cpu_emulator_pid, scheduler_fork;
    scheduler_fork = fork();
    if (scheduler_fork == 0)
    {
        // execl("/usr/bin/javac","javac", "-d", "out", "src/PCB.java", "src/Scheduler.java", "src/CpuEmulator.java", NULL);
        execl("/usr/bin/java","/usr/bin/java", "-cp", "Scheduler.jar", "Scheduler", NULL);
        perror("scheduler failed");
        exit(EXIT_FAILURE);
    }
    
    wait(NULL);

    return EXIT_SUCCESS;
}
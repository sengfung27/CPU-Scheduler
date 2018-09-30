#include <stdio.h>
#include <stdlib.h>
#include <wait.h>
#include <unistd.h>

int main()
{
    int cpu_emulator_fork, scheduler_fork;
    scheduler_fork = fork();
    if (scheduler_fork == 0)
    {
        execl("/usr/bin/java","/usr/bin/java", "-cp", "Scheduler.jar", "Scheduler", NULL);
        perror("scheduler failed.");
        exit(EXIT_FAILURE);
    }
    cpu_emulator_fork = fork();
    if (cpu_emulator_fork == 0)
    {
        execl("/usr/bin/java","/usr/bin/java", "-cp", "CpuEmulator.jar", "CpuEmulator", NULL);
        perror("cpu emulator failed.");
        exit(EXIT_FAILURE);
    }

    return EXIT_SUCCESS;
}
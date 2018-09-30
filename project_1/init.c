#include <stdio.h>
#include <stdlib.h>
#include <wait.h>
#include <unistd.h>

int main()
{
    int cpu_emulator_fork, scheduler_fork, status, pid;

    cpu_emulator_fork = fork();
    if (cpu_emulator_fork == 0)
    {
        printf("cpu pid: %d\n", getpid());
        execl("/usr/bin/java","/usr/bin/java", "-cp", "CpuEmulator.jar", "CpuEmulator", NULL);
        perror("cpu emulator failed.");
        exit(EXIT_FAILURE);
    }
    scheduler_fork = fork();
    if (scheduler_fork == 0)
    {
        printf("scheduler pid: %d\n", getpid());
        execl("/usr/bin/java","/usr/bin/java", "-cp", "Scheduler.jar", "Scheduler", NULL);
        perror("scheduler failed.");
        exit(EXIT_FAILURE);
    }
    
    while((pid = waitpid(-1, &status, 0)) != -1)
    {
        printf("My child with pid: %d has terminated.\n",pid);
    }
    printf("All my children has terminated. \n");

    return EXIT_SUCCESS;
}
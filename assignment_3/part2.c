#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>


int main(int argc, char **argv)
{
    char* argc[] = {"ls", "-la", NULL};
    int pid, status;
    int child = fork();
    if (child == 0)
    {
        printf("I am child, my pid is %d\n", getpid());
        execvp(argc[0], argc);
    }
    else
    {
        printf("I am the parent, my pid is: %d\n", getpid());
        while ((pid = waitpid(-1, &status, 0)) != -1)
        {
            printf("My child with pid: %d has terminated.\n", pid);
        }
    }
}
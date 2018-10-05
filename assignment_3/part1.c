#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <errno.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <fcntl.h>
#include <string.h>

int main(int argc, char **argv)
{
    int child = fork();
    int pid;
    if (child == 0)
    {
        printf("I am child, my pid is %d\n", getpid());
        execl("/bin/date", "/bin/date", NUll);
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
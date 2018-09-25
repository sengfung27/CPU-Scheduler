#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>

int main(int argc, char** argv)
{
    int status;
    int pid;
    int first_child = fork();
    if (first_child == 0)
    {
        printf("I am child one, my pid is: %d\n", getpid());
    }
    else
    {
        int second_child = fork();
        if (second_child == 0)
        {
            printf("I am child second, my pid is: %d\n", getpid());
        }
        else
        {
            printf("I am the parent, my pid is: %d\n", getpid());
            while((pid = waitpid(-1, &status, 0)) != -1)
            {
                printf("My child with pid: %d has terminated.\n",pid);
            }
            printf("All my children has terminated. \n");
        }
    }
    
}
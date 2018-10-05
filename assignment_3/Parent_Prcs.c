#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <errno.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <fcntl.h>
#include <string.h>

int main(int argc, char *argv[])
{
    char *[] argc_1 = {"./prcs_p1", NULL};
    char *[] argc_2 = {"./prcs_p2", "destination1.txt", "destination2.txt", NULL};
    errno = 0;
    int child_1 = fork();
    if (child_1 == 0)
    {
        execv(argc_1[0], argc_1);
        printf("\n[%s]", strerror(errno));
    }
    else
    {
        // sleep 2 sec
        sleep(2);
        int child_2 = fork();
        if (child_2 == 0)
        {
            execv(argc_2[0], argc_2);
            printf("\n[%s]", strerror(errno));
        }
        else
        {
            while ((pid = waitpid(-1, &status, 0)) != -1)
            {
                printf("My child with pid: %d has terminated.\n", pid);
            }
            printf("I am parent, now I terminate.");
        }
    }
}
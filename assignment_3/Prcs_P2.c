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
    int fd[3];
    // check if there are three arguments
    errno = 0;
    // open first file with read only
    fd[0] = open(argv[1], O_RDONLY);

    if (0 > fd[0])
    {
        printf("\n Error in open() source.txt: [%s] \n", strerror(errno));
        return -1;
    }
    // open second and thrid files with write only
    fd[1] = open(argv[2], O_WRONLY);
    fd[2] = open(argv[3], O_WRONLY);
    if (0 > fd[1] && 0 > fd[2])
    {
        if (close(fd[0]) != 0 && close(fd[1]) != 0)
        {
            printf("\n make sure compile into \"prcs_p1\" and \"prcs_p2\" are there. [%s]\n", strerror(errno));
            return -1;
        }
        printf("\n Error in open() destination.txt: [%s]", strerror(errno));
        return -1;
    }

    // Copy content
    char buffer1[101], buffer2[51];   
    int count_100, count_50;
    while ((count_100 = read(fd[0], buffer1, sizeof(buffer1))) > 0)  
    {
        // Replace '1' with 'A'
        for (int i = 0; i < count_100; i++)
        {
            if (buffer1[i] == '1')
            {
                buffer1[i] = 'A';
            }
        }
        // write in destination1.txt
        write(fd[1], buffer1, count_100);

        if (count_100 == 101)    //100 words
        {
            if ((count_50 = read(fd[0], buffer2, sizeof(buffer2))) > 0)  
            {
                // Replace '2' with 'B'
                for (int i = 0; i < count_50; i++)
                {
                    if (buffer2[i] == '2')
                    {
                        buffer2[i] = 'B';
                    }
                }
                // write in destination2.txt
                write(fd[2], buffer2, count_50);
            }
        }
    }

    // close
    int length = 0;
    while (3 > length)
    {
        if (close(fd[length]) != 0)
        {
            printf("\n Error in close(): [%s]", strerror(errno));
            return -1;
        }
        ++length;
    }
    printf("Successful write in destination1.txt and destination2.txt\n");

    return 0;
}

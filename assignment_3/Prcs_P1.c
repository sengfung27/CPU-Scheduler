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
	int fd1 = open("destination1.txt", O_CREAT|O_TRUNC, S_IRUSR | S_IWUSR | S_IXUSR);
    if(-1 == fd1)
	{
		printf("\n open() failed with error: [%s]\n",strerror(errno));
		return -1;
	} 
    int fd2 = open("destination2.txt", O_CREAT|O_TRUNC, S_IRUSR | S_IWUSR | S_IXUSR);
    if(-1 == fd2)
	{
		printf("\n open() failed with error: [%s]\n",strerror(errno));
		return -1;
	} 

    if (close(fd1) != 0)
    {
        printf("\n close() failed with error: [%s]\n",strerror(errno));
        return -1;
    }
    if (close(fd2) != 0)
    {
        printf("\n close() failed with error: [%s]\n",strerror(errno));
        return -1;
    }
    
    printf("\nSuccessful created destination1.txt and destination2.txt\n");

    return 0;
}
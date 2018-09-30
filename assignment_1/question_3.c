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
    int fd[2];
    // check if there are two arguments
    if(3 != argc)
    {
        printf("\n Enter source.txt and destination.txt.  \n");
        return -1;
    }

	errno = 0;
    // open first file with read only
	fd[0] = open(argv[1], O_RDONLY);

	if(0 > fd[0])
    {
		printf("\n Error in open() source.txt: [%s] \n", strerror(errno));
		return -1;
	}
    // open second file with write only, create one if doesn't exist
    fd[1] = open(argv[2], O_WRONLY | O_CREAT, S_IRUSR | S_IWUSR);
    if(0 > fd[1])
    {
        // close first file if failed to open second file
        if (close(fd[0]) != 0)
        {
            printf("\n Error in close() [%s]", strerror(errno));
            return -1;
        }
        printf("\n Error in open() destination.txt: [%s]", strerror(errno));
        return -1;
    }

    // Copy content
    char buffer[100];
    while ((count = read(fd[0], buffer, sizeof(buffer))) > 0) {
        // Replace '1' with 'A'
        for (int i = 0; i < count; i++) {
            if (buffer[i] == '1') {
                buffer[i] = 'A';
            }
        }
        
        write(fd[1], buffer, count);

        // Write "XYZ"
        if (count == 100)
            write(fd[1], "XYZ", 3);
    }
	
    // close
    int length = 0;
    while (2 > length){
        if (close(fd[length]) != 0)
        {
            printf("\n Error in close(): [%s]", strerror(errno));
		    return -1;
        }
        ++length;
    }

	return 0;
}

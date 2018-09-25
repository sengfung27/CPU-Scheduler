#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <errno.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <fcntl.h>
#include <string.h>

int main(int argc, char *argv[]) {
	int fd[2];
    
    // check if there are to arguments of text files
    if(3 != argc)
    {
        printf("\n Enter source.txt and destination.txt.  \n");
        return -1;
    }

	errno = 0;
    // open first argument file
	fd[0] = open(argv[1], O_RDONLY);

	if(0 > fd[0])
    {
		printf("\n Error in open() source.txt: [%s] \n", strerror(errno));
		return -1;
	}
    // open second argument file
    fd[1] = open(argv[2], O_WRONLY | O_CREAT, S_IRUSR | S_IWUSR);
    if(0 > fd[1])
    {
        // close first file if second file occurs error
        if (close(fd[0]) != 0)
        {
            printf("\n Error in close() [%s]", strerror(errno));
            return -1;
        }
        printf("\n Error in open() destination.txt: [%s]", strerror(errno));
        return -1;
    }
    // determine buffer size
    size_t buffer_size = lseek(fd[0], 0L, SEEK_END);
    lseek(fd[0], 0L, SEEK_SET);
	char buffer[buffer_size];

    // read
    while(read(fd[0], buffer, buffer_size) != 0)
    {   
        // write into file
        if ((write(fd[1], buffer, buffer_size)) < 0)
	    {
		    printf("\n Error in write(): [%s]", strerror(errno));
            return -1;
	    }
    }

    int length = 0;
    while (2 > length)
    {
        // close file
        if (close(fd[length]) != 0)
        {
            printf("\n Error in close(): [%s]", strerror(errno));
		    return -1;
        }
        ++length;
    }

	return 0;
}

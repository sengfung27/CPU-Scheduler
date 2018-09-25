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
	int fd;
	// check if there is a text file
    if(2 != argc)
    {
        printf("\n Usage : Enter a file name  \n");
        return -1;
    }

	errno = 0;
	// open
	fd = open(argv[1], O_RDONLY);

	if(0 > fd)
    {
		printf("\n Error in open(): [%s]", strerror(errno));
		return -1;
	}
	// determine the bufffer size
	size_t buffer_size = lseek(fd, 0L, SEEK_END);
    lseek(fd, 0L, SEEK_SET);

	char buffer[buffer_size];
	// read
	ssize_t bytes = read(fd, buffer, buffer_size);
	if (bytes < 0)
	{
		printf("\n Error in read(): [%s]", strerror(errno));
		return -1;
	}
	// display its content
	ssize_t nwrite = write(STDOUT_FILENO, buffer, buffer_size);
	if (nwrite < 0)
	{
		printf("\n Error in write(): [%s]", strerror(errno));
		return -1;
	}
	// close
	if (close(fd) != 0)
	{
		printf("\n Error in close(): [%s]", strerror(errno));
		return -1;
	}
		
	return 0;
	
}


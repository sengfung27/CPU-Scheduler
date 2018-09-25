#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>
#include <errno.h>
int main(int argc, char *argv[]){ 
	int fd;
	if(2 != argc)
	{ 
		printf("\n Usage : \n"); return 1;
	} 
	errno = 0; 
	// open
	fd = open(argv[1], O_CREAT, S_IRUSR | S_IWUSR);
	// if failed, output error
	if(-1 == fd)
	{
		printf("\n open() failed with error: [%s]\n",strerror(errno));
		return -1;
	} 
	else 
	{ 
		// open successful, then close
		printf("\n open() Successful\n");
		if (close(fd) != 0)
		{
			printf("\n close() failed with error: [%s]\n",strerror(errno));
			return -1;
		}
		printf("\n close() Successful\n");
	}
	
	return 0;
}

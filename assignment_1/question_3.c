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

    // keep track of characters.
    size_t count_chars = 0; 

    //determine the read file size
    size_t read_size = lseek(fd[0], 0L, SEEK_END); 
    lseek(fd[0], 0L, SEEK_SET);

    // 103, because 100 characters and XYZ
    char buffer[103]; 
    
    while (read(fd[0], buffer, 100) != 0)
    { 
        //read and write 100 characters with XYZ
        if(read_size - count_chars >= 100) 
        {   
            // replace 1 with A
            for (int i = 0; i < 100; i++)
            {
                if(buffer[i] == '1')
                    buffer[i] = 'A';
        
            }
            // write XYZ 
            buffer[100] = 'X';
            
            buffer[101] = 'Y';
        
            buffer[102] = 'Z';
            
            if ((write(fd[1], buffer, 103)) < 0)
            {
                printf("\n Error in write(): [%s]", strerror(errno));
                return -1;
            }
            // keep track of characters has been read
            count_chars += 100;
        }

        // write the remaining characters without XYZ if less than 100 characters
        else 
        {
            size_t remaining_chars = read_size - count_chars;
            for (int i = 0; i < remaining_chars; i++)
            {
                if(buffer[i] == '1')
                    buffer[i] = 'A';
            }
            if ((write(fd[1], buffer, remaining_chars)) < 0)
            {
                printf("\n Error in write(): [%s]", strerror(errno));
                return -1;
            }
        }
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
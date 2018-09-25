#include <stdio.h>

#include <unistd.h> 
#include <errno.h>
#include <string.h>
int main (int argc, char* argv[])
{ 
	char* filepath = argv[1]; 
	int returnval;
	// Check file existence 
	returnval = access(filepath, F_OK); 
	if (returnval == 0) 
		printf ("\n %s exists\n", filepath);
	else { 
		if (errno == ENOENT) printf ("%s does not exist\n", filepath);
		else if (errno == EACCES) printf ("%s is not accessible\n", filepath);
		return 0;
	}

	// Check read access ...
	returnval = access(filepath, R_OK); 
	if (returnval == 0) 
	{
		printf ("\n Read Access\n");
	}

	else 
	{ 
		printf ("\n DENIED Read Access \n");	
	}

	// Check write access ...
	returnval = access(filepath, W_OK); 
	if (returnval == 0) 
	{
		printf ("\n Write Access\n");
	}
	
	else
	{ 
		printf ("\n DENIED Write Access \n");
	}
	return 0;
}

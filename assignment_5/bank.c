#include <stdio.h>
#include <stdlib.h>
#include <sys/sem.h>
#include <sys/time.h>
#include "sem.h"
#define CHILD 0 /* Return value of child proc from fork call */
#define TRUE 0
#define FALSE 1
#define PERMS 0666 // My variable

FILE *fp1, *fp2, *fp3, *fp4; /* File Pointers */

main()
{
	int pid;		 // Process ID after fork call
	int i;			 // Loop index
	int N;			 // Number of times dad does update
	int N_Att;		 // Number of time sons allowed to do update
	int status;		 // Exit status of child process
	int bal1, bal2;  // Balance read by processes
	int flag, flag1; // End of loop variables

	//Initialize the file balance to be $100
	fp1 = fopen("balance", "w");
	bal1 = 100;
	fprintf(fp1, "%d\n", bal1);
	fclose(fp1);

	//Initialize the number of attempts to be 20
	fp4 = fopen("attempt", "w");
	N_Att = 20;
	fprintf(fp4, "%d\n", N_Att);
	fclose(fp4);

	/* My Solution */
	// create semaphore
	int sem_m; // * My semiphore variable
	sem_m = semget(IPC_PRIVATE, 1, PERMS | IPC_CREAT); 
	sem_create(sem_m, 1);
	struct timeval timer_1, timer_2, timer_3, timer_4, timer_5, timer_6;
	/* My Solution */

	//Create child processes that will do the updates
	if ((pid = fork()) == -1)
	{
		//fork failed!
		perror("fork");
		exit(1);
	}

	if (pid == CHILD)
	{
		//First Child Process. Dear old dad tries to do some updates.

		N = 5;
		for (i = 1; i <= N; i++)
		{
			/* My Solution */
			// T(P), measurement of bounded time
			gettimeofday(&timer_1, NULL);
			//Adding Sem operation
			P(sem_m);
			// time ends
			gettimeofday(&timer_2, NULL);
			printf("Dad's T(P)= %f sec\n", (double)(timer_2.tv_usec - timer_1.tv_usec)/1000000 + (double) (timer_2.tv_sec - timer_1.tv_sec));
			/* My Solution */

			printf("Dear old dad is trying to do update.\n");
			fp1 = fopen("balance", "r+");
			fscanf(fp1, "%d", &bal2);
			printf("Dear old dad reads balance = %d \n", bal2);

			//Dad has to think (0-14 sec) if his son is really worth it
			sleep(rand() % 15);
			fseek(fp1, 0L, 0);
			bal2 += 60;
			printf("Dear old dad writes new balance = %d \n", bal2);
			fprintf(fp1, "%d \n", bal2);
			fclose(fp1);

			printf("Dear old dad is done doing update. \n");
			sleep(rand() % 5); /* Go have coffee for 0-4 sec. */
			V(sem_m);		   // release lock
		}
	}

	else
	{
		//Parent Process. Fork off another child process.
		if ((pid = fork()) == -1)
		{
			//Fork failed!
			perror("fork");
			exit(1);
		}
		if (pid == CHILD)
		{
			printf("First Son's Pid: %d\n", getpid());
			//Second child process. First poor son tries to do updates.
			flag = FALSE;

			while (flag == FALSE)
			{
				/* My Solution */
				// T(P), measurement of bounded time


				gettimeofday(&timer_3, NULL);
				//Adding Sem operation
			    P(sem_m);
			    // time ends
				gettimeofday(&timer_4, NULL);
				printf("First Son's T(P)= %f sec\n", (double)(timer_4.tv_usec - timer_3.tv_usec)/1000000 + (double) (timer_4.tv_sec - timer_3.tv_sec));
				/* My Solution */

				fp3 = fopen("attempt", "r+");
				fscanf(fp3, "%d", &N_Att);
				if (N_Att == 0)
				{
					fclose(fp3);
					flag = TRUE;
				}
				else
				{
					printf("Poor SON_1 wants to withdraw money.\n");
					fp2 = fopen("balance", "r+");
					fscanf(fp2, "%d", &bal2);
					printf("Poor SON_1 reads balance. Available Balance: %d \n", bal2);
					if (bal2 == 0)
					{
						fclose(fp2);
						fclose(fp3);
					}
					else
					{
						sleep(rand() % 5);
						fseek(fp2, 0L, 0);
						bal2 -= 20;
						printf("Poor SON_1 write new balance: %d \n", bal2);
						fprintf(fp2, "%d\n", bal2);
						fclose(fp2);
						printf("poor SON_1 done doing update.\n");
						fseek(fp3, 0L, 0);
						N_Att -= 1;
						fprintf(fp3, "%d\n", N_Att);
						fclose(fp3);
					}
				}
				V(sem_m); // release lock
			}
		}
		else
		{
			//Parent Process. Fork off one more child process.
			if ((pid = fork()) == -1)
			{
				//fork failed!
				perror("fork");
				exit(1);
			}
			if (pid == CHILD)
			{
				printf("Second Son's Pid: %d\n", getpid());
				//Third child process. Second poor son tries to do updates.
				flag1 = FALSE;
				while (flag1 == FALSE)
				{
					/* My Solution */
					// T(P), measurement of bounded time	

					//Adding time
					gettimeofday(&timer_5, NULL);
					//Adding Sem operation
			        P(sem_m);
			        gettimeofday(&timer_6, NULL);
					printf("Second Son's T(P)= %f sec\n", (double)(timer_6.tv_usec - timer_5.tv_usec)/1000000 + (double) (timer_6.tv_sec - timer_5.tv_sec));
		
					/* My Solution */

					fp3 = fopen("attempt", "r+");
					fscanf(fp3, "%d", &N_Att);
					if (N_Att == 0)
					{
						fclose(fp3);
						flag1 = TRUE;
					}
					else
					{
						printf("Poor SON_2 wants to withdraw money.\n");
						fp2 = fopen("balance", "r+");
						fscanf(fp2, "%d", &bal2);
						printf("Poor SON_2 reads balance. Available Balance: %d \n", bal2);
						if (bal2 == 0)
						{
							fclose(fp2);
							fclose(fp3);
						}
						else
						{
							sleep(rand() % 5);
							fseek(fp2, 0L, 0);
							bal2 -= 20;
							printf("Poor SON_2 write new balance: %d \n", bal2);
							fprintf(fp2, "%d\n", bal2);
							fclose(fp2);

							printf("Poor SON_2 done doing update.\n");
							fseek(fp3, 0L, 0);
							N_Att -= 1;
							fprintf(fp3, "%d\n", N_Att);
							fclose(fp3);
						}
					}
					V(sem_m); // release lock
				}
			}
			else
			{
				//Now parent process waits for the child processes to finish
				pid = wait(&status);
				printf("Process(pid = %d) exited with the status %d. \n", pid, status);

				pid = wait(&status);
				printf("Process(pid = %d) exited with the status %d. \n", pid, status);

				pid = wait(&status);
			}
			exit(0);
		}
		exit(0);
	}
	exit(0);
}

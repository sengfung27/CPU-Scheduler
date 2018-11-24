#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>
#include <errno.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>

double Average(int homeworks)
{
  int file_descriptor;
  file_descriptor = open("input.txt", O_RDONLY);
  errno = 0;
  if (-1 == file_descriptor)
  {
    printf("\nopen() failed with error [%s]\n", strerror(errno));
    return 1;
  }
  else
  {
    int int_grade, buffer_size = 0, sum = 0, start_point = 0, students = 10;
    char buffer;
    char grade[3];
    for (int j = 0; j < students; j++)
    {
      start_point = (homeworks - 1) * 4 + (j * 17);
      lseek(file_descriptor, start_point, SEEK_SET);
      for (int i = start_point; i < start_point + 3; i++) 
      {
        read(file_descriptor, &buffer, 1);
        if (buffer != ' ')
        {
          grade[buffer_size] = buffer;
          buffer_size++;
        }
      }
      int_grade = atoi(grade);
      sum = sum + int_grade;
      //clean buffer
      buffer_size = 0;
      memset(grade, 0x0, 3);
    }
    return sum / 10.0;
  }
}

int main()
{
  double average;
  int first_manager = fork();

  //Manager 1
  if (first_manager == 0)
  {

    int first_worker = fork();
    if (first_worker == 0)
    {
      average = Average(1);
      printf("Homework 1 's average is:%.2f\n", average);
      exit(0);
    }
    else
    {
      int second_worker = fork();
      if (second_worker == 0)
      {
        average = Average(2);
        printf("Homework 2 's average is:%.2f\n", average);
        exit(0);
      }
      else
      {
        int pid, status;
        while ((pid = waitpid(-1, &status, 0)) != -1)
        {
          printf("I am Manager 1, My child with pid: %d has terminated.\n", pid);
        }
        printf("I am Manager 1 with pid: %d, now I terminate.\n", getpid());
      }
    }
  }
  else
  {

    // Manager 2
    int second_manager = fork();
    if (second_manager == 0)
    {
      int third_worker = fork();
      if (third_worker == 0)
      {
        average = Average(3);
        printf("Homework 3 's average is:%.2f\n", average);
        exit(0);
      }
      else
      {
        int fourth_worker = fork();
        if (fourth_worker == 0)
        {
          average = Average(4);
          printf("Homework 4 's average is:%.2f\n", average);
          exit(0);
        }
        else
        {
          int pid, status;
          while ((pid = waitpid(-1, &status, 0)) != -1)
          {
            printf("I am Manager 2. My child with pid: %d has terminated.\n", pid);
          }
          printf("I am Manager 2 with pid: %d, now I terminate.\n", getpid());
        }
      }
    }

    // Director
    else
    {
      int pid, status;
      while ((pid = waitpid(-1, &status, 0)) != -1)
      {
        printf("I am Director, My child with pid: %d has terminated.\n", pid);
      }
      printf("I am Director with pid: %d, now I terminate.\n", getpid());
      exit(0);
    }
  }
}

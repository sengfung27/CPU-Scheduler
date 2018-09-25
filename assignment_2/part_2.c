#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>

int main(int argc, char** argv)
{
    int a = 10, b = 25, fq = 0, fr = 0;
    fq = fork();
    if (fq == 0)
    {
        a = a + b;
        printf("First fork\n");
        printf("The value of a is %d\n", a); //35
        printf("The value of b is %d\n", b); //25
        printf("The value of pid is %d\n", getpid());
        fr = fork();
        if (fr != 0)
        {
            b = b + 20;
            printf("The value of a is %d\n", a); //35
            printf("The value of b is %d\n", b); //45
            printf("The value of pid is %d\n", getpid());
        }
        else
        {
            printf("Second fork\n");
            a = (a * b) + 30;
            printf("The value of a is %d\n", a); //905
            printf("The value of b is %d\n", b); //25
            printf("The value of pid is %d\n", getpid());
        }
    }
    else
    {
        b = a + b - 5;
        printf("Main Parent\n");
        printf("The value of a is %d\n", a); //10
        printf("The value of b is %d\n", b); //30
        printf("The value of pid is %d\n", getpid());
    }
}
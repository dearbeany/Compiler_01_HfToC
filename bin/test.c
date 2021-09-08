#include <stdio.h>
#include <stdlib.h>

int main(){
printf("Hello world!");
system("ls -al");
printf("**********sample.txt**********");
system("cat sample.txt");
system("ls > current_directory_list.txt");
printf("delete sample.txt");
system("rm sample.txt");
}
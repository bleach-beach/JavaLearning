//
// Created by yanghao on 7/21/19.
//

#include "SimpleThread.h"
#include <pthread.h>//头文件
#include <stdio.h>
#include <unistd.h>

pthread_t pid;//定义一个变量，接受创建线程后的线程id



void* thread_entity(void* arg) {
    while(1){
        usleep(100);
        printf("I am new Thread\n");
    }
}

/*
void *aa() {
    printf("i am aa!");
    printf("end\n");
}
*/

void start(){
    pthread_create(&pid,NULL,thread_entity,NULL);
    while(1){
        usleep(100);
        printf("I am main\n");
    }
}
int main(){

    //调用操作系统的函数创建线程，注意四个参数
    start();
    pthread_create(&pid,NULL,thread_entity,NULL);

}



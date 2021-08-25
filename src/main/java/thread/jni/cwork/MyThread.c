//
// Created by yanghao on 7/21/19.
//

#include <pthread.h>//头文件

#include <stdio.h>
#include <unistd.h>
#include "MyThreadJni.h"

pthread_t pid;//定义一个变量，接受创建线程后的线程id



void* thread_entity(void* arg) {
    /*while(1){*/
        usleep(10);
        printf("I am new Thread\n");
    /*}*/
}


/*
void *aa() {
    printf("i am aa!");
    printf("end\n");
}
*/

JNIEXPORT void JNICALL Java_MyThreadJni_start0(JNIEnv *env, jobject c1){
    //调用操作系统的函数创建线程，注意四个参数，thread_entity为线程体回调方法，主要执行逻辑在此
    //JNI直接调用LINUX线程
    pthread_create(&pid,NULL,thread_entity,*env);
    /*while(1){*/
        usleep(1000);
        printf("I am main\n");
    /*}*/
}


JNIEXPORT void JNICALL Java_MyThreadJni_run0(JNIEnv *env, jobject c1){

    //JNI反射调用JAVA MyThreadJni类 run方法
    jclass cls;
    jobject obj;
    jmethodID cid;
    jmethodID rid;
    jint ret = 0;

    cls = (*env)->FindClass(env,"MyThreadJni");
    if(cls == NULL){
        printf("FindClass error!\n");
    }

    cid = (*env)->GetMethodID(env,cls,"<init>","()V");

    if(cid == NULL){
        printf("query constructor error!\n");
    }

    obj = (*env)->NewObject(env,cls,cid);

    if(obj == NULL){
        printf("newObject  error!\n");
    }

    rid = (*env)->GetMethodID(env,cls,"run","()V");

    ret = (*env)->CallIntMethod(env,obj,rid,NULL);

    printf("END!\n");
}




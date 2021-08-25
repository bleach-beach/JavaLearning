cmake_minimum_required(VERSION 3.14)
project(cwork)

set(CMAKE_CXX_STANDARD 14)

add_executable(cwork main.cpp MyThread.c myThread.h SimpleThread.cpp SimpleThread.h)

find_package(Threads REQUIRED)
target_link_libraries(cwork Threads::Threads)
cmake_minimum_required(VERSION 3.22.1)
project(mcwrapper)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Native library
add_library(mcwrapper SHARED
    native-lib.cpp
)

# Link Android libraries
target_link_libraries(mcwrapper
    android
    log
)

# Include directories
target_include_directories(mcwrapper PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}/include
)

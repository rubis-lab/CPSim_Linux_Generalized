# Required Cmake Version
CMAKE_MINIMUM_REQUIRED (VERSION 3.8)

# Project name and version
PROJECT ("CPSim")
SET ( PROJECT_VERSION_MAJOR 0 )
SET ( PROJECT_VERSION_MINOR 1 )

# Build configuration and makefile creation
SET ( CMAKE_BUILD_TYPE Debug )
SET ( CMAKE_VERBOSE_MAKEFILE true )

# Source file list for Build
SET ( OUTPUT_ELF
        "${CMAKE_PROJECT_NAME}-${PROJECT_VERSION_MAJOR}.${PROJECT_VERSION_MINOR}.out"
        )
SET ( SRC_FILES
        Engine/main.cpp Engine/Initializer.h Engine/Initializer.cpp Engine/ScheduleSimulator.h
        Engine/ScheduleSimulator.cpp Engine/OfflineGuider.h Engine/OfflineGuider.cpp
        Engine/Executor.h Engine/Executor.cpp
        )

# Compiler Information
SET ( CMAKE_C_COMPILER "g++" )

# Common Header Includes
# INCLUDE_DIRECTORIES ( include driver/include )

# Compile Options, Linking Options
# -g : Debug
# -wall : warn to every vague codes
# ADD_COMPILE_OPTIONS ( -g -wall )
# SET ( CMAKE_EXE_LINKER_FLAGS "-static -Wl, --gc-sections" )

# Common Linking Libraries
LINK_LIBRARIES( dl )

# "Debug" Configruation options
SET ( CMAKE_C_FLAGS_DEBUG "-DDEBUG -DC_FLAGS" )
SET ( CMAKE_EXE_LINKER_FLAGS_DEBUG "-DDEBUG -DLINKER_FLAGS" )
 
# "Release" configuration options
SET ( CMAKE_C_FLAGS_RELEASE "-DRELEASE -DC_FLAGS" )
SET ( CMAKE_EXE_LINKER_FLAGS_RELEASE "-DRELEASE -DLINKER_FLAGS" )

# Output Directory
SET ( CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_BUILD_TYPE} )
SET ( CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_BUILD_TYPE}/lib )
SET ( CMAKE_ARCHIVE_OUTPUT_DIRECTORY ${CMAKE_BUILD_TYPE}/lib )

# Add source files at here using this formats.
add_executable ( ${OUTPUT_ELF} ${SRC_FILES})
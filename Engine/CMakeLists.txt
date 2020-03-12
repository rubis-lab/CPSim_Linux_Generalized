# Required Cmake Version
CMAKE_MINIMUM_REQUIRED (VERSION 3.8)

# Project name and version
PROJECT ("CPSim")
SET ( PROJECT_VERSION_MAJOR 0 )
SET ( PROJECT_VERSION_MINOR 1 )

# Build configuration and makefile creation
SET ( CMAKE_BUILD_TYPE Debug )
SET ( CMAKE_VERBOSE_MAKEFILE true )




# Add source files at here using this formats.
add_executable ( ${OUTPUT_ELF} ${SRC_FILES})
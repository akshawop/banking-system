#!/bin/bash
# compile.sh — Compile all Java files in src/me/akshawop/banking recursively

# Stop script on error
set -e

# Ensure bin directory exists
mkdir -p bin

# Find all .java files
java_files=$(find src/me/akshawop/banking -name "*.java")

# Compile them
javac -cp "lib/*" -d bin $java_files

echo "Compilation complete. Class files are in ./bin"

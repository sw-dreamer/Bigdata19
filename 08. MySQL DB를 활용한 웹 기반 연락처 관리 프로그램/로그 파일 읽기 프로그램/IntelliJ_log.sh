#!/bin/bash

# Prompt for the log file location
echo "Please enter the folder location and file using the absolute path."
read logpath

tail -f "$logpath"

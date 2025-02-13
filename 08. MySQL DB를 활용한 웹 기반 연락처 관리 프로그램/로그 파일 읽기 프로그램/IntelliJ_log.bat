@echo off
set /p logpath="Please enter the folder location and file using the absolute path. >"

powershell -Command "Get-Content -Path '%logpath%' -Wait -Tail 10 -Encoding UTF8"
pause

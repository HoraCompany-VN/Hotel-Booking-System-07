@echo off
cd /d "%~dp0"
echo ================================
echo  Hotel Booking System
echo ================================
echo.
echo Compiling...
call mvn compile -q
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo *** BUILD FAILED ***
    pause
    exit /b 1
)
echo Running...
java -cp "target\classes;%USERPROFILE%\.m2\repository\com\mysql\mysql-connector-j\8.0.33\mysql-connector-j-8.0.33.jar" HotelBooking.HotelBooking
pause

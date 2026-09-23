@echo off
title FitPulse - Stop All Services
color 0C
echo ==========================================================
echo       Stopping all FitPulse Services (8080, 8081, 5173)...
echo ==========================================================

echo Stopping Port 8080 (Owner Backend)...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr ":8080" ^| findstr "LISTENING"') do taskkill /f /pid %%a >nul 2>&1

echo Stopping Port 8081 (Trainer Backend)...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr ":8081" ^| findstr "LISTENING"') do taskkill /f /pid %%a >nul 2>&1

echo Stopping Port 5173 (React Frontend)...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr ":5173" ^| findstr "LISTENING"') do taskkill /f /pid %%a >nul 2>&1

echo.
echo All services have been stopped.
timeout /t 2 >nul

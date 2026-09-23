@echo off
title FitPulse Gym System - 1-Click Launcher
color 0B
echo ==========================================================
echo         FITPULSE GYM MANAGEMENT SYSTEM (NIBM EAD02)
echo           Starting All Services in 1-Click...
echo ==========================================================
echo.

set ROOT_DIR=%~dp0

echo [1/3] Starting Owner & Customer Backend on Port 8080...
start "FitPulse - Owner Backend (8080)" cmd /k "cd /d "%ROOT_DIR%Owner" && .\mvnw.cmd spring-boot:run"

timeout /t 5 /nobreak >nul

echo [2/3] Starting Trainer Backend on Port 8081...
start "FitPulse - Trainer Backend (8081)" cmd /k "cd /d "%ROOT_DIR%Trainer" && .\mvnw.cmd spring-boot:run"

timeout /t 5 /nobreak >nul

echo [3/3] Starting React Frontend on Port 5173...
start "FitPulse - React Frontend (5173)" cmd /k "cd /d "%ROOT_DIR%frontend" && npm run dev"

timeout /t 3 /nobreak >nul

echo.
echo ==========================================================
echo   All 3 services are launching!
echo   Opening browser at http://localhost:5173 ...
echo ==========================================================
start http://localhost:5173

pause

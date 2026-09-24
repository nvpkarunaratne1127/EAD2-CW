@echo off
title FitPulse Gym System - 1-Click Launcher
color 0B
echo ==========================================================
echo         FITPULSE GYM MANAGEMENT SYSTEM (NIBM EAD02)
echo           Starting Backend and Frontend in 1-Click...
echo ==========================================================
echo.

set ROOT_DIR=%~dp0

echo [1/2] Starting Spring Boot REST API Backend (Port 8080)...
start "FitPulse - Spring Boot API (8080)" cmd /k "cd /d "%ROOT_DIR%" && .\mvnw.cmd spring-boot:run"

timeout /t 6 /nobreak >nul

echo [2/2] Starting React Frontend (Port 5173)...
start "FitPulse - React Frontend (5173)" cmd /k "cd /d "%ROOT_DIR%frontend" && npm run dev"

timeout /t 3 /nobreak >nul

echo.
echo ==========================================================
echo   Services are running!
echo   Web App:  http://localhost:5173
echo   Swagger:  http://localhost:8080/swagger-ui.html
echo ==========================================================
start http://localhost:5173

pause

@echo off
cd /d "%~dp0"
echo ========================================================
echo   E-Health / Tabib App (PostgreSQL / MySQL Support)
echo ========================================================
javac -d bin -cp "bin;lib/postgresql-42.7.4.jar;lib/swingx-all-1.6.4.jar;lib/mysql-connector-j-9.1.0.jar" src\ehealth\*.java src\pages\*.java
if %ERRORLEVEL% equ 0 (
    echo [OK] Build successful!
    echo Starting E-Health Main Application...
    start javaw -cp "bin;lib/postgresql-42.7.4.jar;lib/swingx-all-1.6.4.jar;lib/mysql-connector-j-9.1.0.jar" ehealth.PreLoginPage
) else (
    echo [ERROR] Build failed.
    pause
)

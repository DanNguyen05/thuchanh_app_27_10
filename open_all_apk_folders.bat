@echo off
echo ========================================
echo OPENING APK FOLDERS
echo ========================================
echo.

cd /d "%~dp0"
echo Opening Calculator APK folder...
if exist "app\build\outputs\apk\cal
culator\debug" (
    start "" "%cd%\app\build\outputs\apk\calculator\debug"
)

timeout /t 2 /nobreak >nul

echo Opening Gallery APK folder...
if exist "app\build\outputs\apk\gallery\debug" (
    start "" "%cd%\app\build\outputs\apk\gallery\debug"
)

timeout /t 2 /nobreak >nul

echo Opening Weather APK folder...
if exist "app\build\outputs\apk\weather\debug" (
    start "" "%cd%\app\build\outputs\apk\weather\debug"
)

timeout /t 2 /nobreak >nul

echo Opening Task APK folder...
if exist "app\build\outputs\apk\task\debug" (
    start "" "%cd%\app\build\outputs\apk\task\debug"
)

echo.
echo ========================================
echo All APK folders opened!
echo Drag and drop APK files into emulator to install
echo ========================================
pause


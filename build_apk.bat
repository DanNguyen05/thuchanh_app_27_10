@echo off
echo ========================================
echo BUILDING APK FILE
echo ========================================
echo.

cd /d "%~dp0"

echo Cleaning previous build...
call gradlew clean

echo.
echo Building Debug APK...
call gradlew assembleDebug

echo.
echo ========================================
if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo BUILD SUCCESSFUL!
    echo APK file location:
    echo %cd%\app\build\outputs\apk\debug\app-debug.apk
    echo.
    echo Opening APK folder...
    start "" "%cd%\app\build\outputs\apk\debug"
) else (
    echo BUILD FAILED!
    echo Please check the error messages above.
)
echo ========================================
pause


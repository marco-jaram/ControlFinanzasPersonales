@echo off
set INSTALL_DIR=%USERPROFILE%\ControlGastos

echo Creando directorio...
mkdir "%INSTALL_DIR%"

echo Copiando archivos...
xcopy /E /Y app\* "%INSTALL_DIR%\"

echo Creando acceso directo en el escritorio...
echo [InternetShortcut] > "%USERPROFILE%\Desktop\Control Gastos.url"
echo URL="%INSTALL_DIR%\run.bat" >> "%USERPROFILE%\Desktop\Control Gastos.url"
echo IconFile=%INSTALL_DIR%\icono.ico >> "%USERPROFILE%\Desktop\Control Gastos.url"

echo Instalación completa!
pause
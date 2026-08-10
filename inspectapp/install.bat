@echo off
REM Script d'installation automatique de iNSPECTAPP pour Windows
REM Plateforme de gestion pour inspecteurs pédagogiques

echo ============================================
echo   Installation de iNSPECTAPP
echo   Plateforme de gestion pour inspecteurs pedagogiques
echo ============================================
echo.

REM Vérifier si Java est installé
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERREUR: Java n'est pas installe sur votre systeme.
    echo Veuillez installer JDK 17 ou superieur depuis:
    echo   https://adoptium.net/
    pause
    exit /b 1
)

for /f "tokens=3" %%a in ('java -version 2^>^&1 ^| findstr /i "version"') do set JAVA_VERSION=%%a
set JAVA_VERSION=%JAVA_VERSION:"=%
echo Java detecte: version %JAVA_VERSION%

REM Copier le fichier JAR
set INSTALL_DIR=%USERPROFILE%\iNSPECTAPP
if not exist "%INSTALL_DIR%" mkdir "%INSTALL_DIR%"
echo Repertoire d'installation: %INSTALL_DIR%

copy /Y "target\inspectapp-1.0.0.jar" "%INSTALL_DIR\" >nul
echo Application copiee dans %INSTALL_DIR%

REM Créer le script de lancement
(
echo @echo off
echo set SCRIPT_DIR=%%~dp0
echo java -Xmx512m --add-modules javafx.controls,javafx.fxml,javafx.web,javafx.swing,javafx.media ^
echo      --add-opens java.base/java.lang=ALL-UNNAMED ^
echo      --add-opens java.base/java.util=ALL-UNNAMED ^
echo      --add-opens java.base/java.lang.reflect=ALL-UNNAMED ^
echo      --add-opens java.base/java.text=ALL-UNNAMED ^
echo      --add-opens java.desktop/java.awt.font=ALL-UNNAMED ^
echo      -jar "%%SCRIPT_DIR%%inspectapp-1.0.0.jar" %%*
) > "%INSTALL_DIR%\inspectapp.bat"

echo Script de lancement cree

REM Créer un raccourci sur le Bureau
set DESKTOP_DIR=%USERPROFILE%\Desktop
if exist "%DESKTOP_DIR%" (
    powershell -Command "$WshShell = New-Object -ComObject WScript.Shell; $Shortcut = $WshShell.CreateShortcut('%DESKTOP_DIR%\iNSPECTAPP.lnk'); $Shortcut.TargetPath = '%INSTALL_DIR%\inspectapp.bat'; $Shortcut.WorkingDirectory = '%INSTALL_DIR%'; $Shortcut.Description = 'Plateforme de gestion pour inspecteurs pedagogiques'; $Shortcut.Save()"
    echo Raccourci cree sur le Bureau
)

echo.
echo ============================================
echo   Installation terminee avec succes!
echo ============================================
echo.
echo Pour lancer l'application:
echo   1. Double-cliquez sur le raccourci cree sur votre Bureau
echo   2. Ou executez: %INSTALL_DIR%\inspectapp.bat
echo.
echo La base de donnees sera creee automatiquement dans:
echo   %%USERPROFILE%%\.inspectapp\inspectapp.db
echo.
pause

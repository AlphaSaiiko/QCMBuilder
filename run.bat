@echo off
setlocal enabledelayedexpansion

REM Définir les répertoires des sources et des binaires
set SRC_DIR=src
set BIN_DIR=bin

REM Vérifier si le répertoire src existe
if not exist "%SRC_DIR%" (
    echo Erreur : Le repertoire '%SRC_DIR%' est introuvable.
    exit /b 1
)

REM Créer le répertoire bin s'il n'existe pas
if not exist "%BIN_DIR%" (
    mkdir "%BIN_DIR%"
)

REM Compiler tous les fichiers Java en une seule commande
echo Compilation des fichiers Java...

REM Collecter tous les fichiers .java dans le répertoire src et ses sous-répertoires
for /r "%SRC_DIR%" %%f in (*.java) do (
    set SOURCES=!SOURCES! "%%f"
)

REM Compiler les fichiers Java
javac -d "%BIN_DIR%" !SOURCES!
if errorlevel 1 (
    echo Erreur : La compilation a echoue.
    exit /b 1
)

REM Exécution de Controleur.class
java -cp bin controleur.Controleur

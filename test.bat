@echo off
setlocal enabledelayedexpansion

REM Vérifier si un argument est fourni
if "%1"=="" (
    echo Erreur : Vous devez fournir le nom de la classe a tester.
    echo Exemple : test.bat [nom de la classe a tester]
    exit /b 1
)

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

REM Initialisation de la liste des fichiers source
set SOURCES=

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

REM Normaliser le nom de la classe a tester
set CLASS_TO_TEST=%1
set CLASS_TO_TEST=%CLASS_TO_TEST:Test=%
set CLASS_TO_TEST=Test%CLASS_TO_TEST%

REM Exécuter la classe de test
echo Execution de la classe de test : %CLASS_TO_TEST%
java -cp "%BIN_DIR%" test.%CLASS_TO_TEST%

if errorlevel 1 (
    echo Erreur : Impossible d'executer la classe %CLASS_TO_TEST%.
    exit /b 1
)

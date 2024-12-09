@echo off

REM Vérifier si un argument est fourni
if "%1"=="" (
    echo Erreur : Vous devez fournir le nom de la classe a tester.
    echo Exemple : ./test TestNomDeLaClasseATester
    exit /b 1
)

REM Définir les répertoires des sources et des binaires
set SRC_DIR=src
set BIN_DIR=bin

REM Vérifier si le répertoire src existe
if not exist "%SRC_DIR%" (
    echo Erreur : Le repertoire "%SRC_DIR%" est introuvable.
    exit /b 1
)

REM Compiler les fichiers Java
echo Compilation des fichiers Java...
cd %SRC_DIR%
javac @sources.txt -d ../%BIN_DIR%

if errorlevel 1 (
    echo Erreur : La compilation a echoue.
    exit /b 1
)

cd ..

REM Vérifier si le répertoire bin existe après compilation
if not exist "%BIN_DIR%" (
    echo Erreur : Le repertoire "%BIN_DIR%" est introuvable apres la compilation.
    exit /b 1
)

REM Exécuter la classe de test
echo Execution de la classe de test : %1
cd %BIN_DIR%
java test.%1

REM Revenir au répertoire principal
cd ..

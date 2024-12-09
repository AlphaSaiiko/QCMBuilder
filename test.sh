#!/bin/bash

# Vérifier si un argument est fourni
if [ -z "$1" ]; then
    echo "Erreur : Vous devez fournir le nom de la classe à tester."
    echo "Exemple : ./test.sh TestNomDeLaClasseATester"
    exit 1
fi

# Définir les répertoires des sources et des binaires
SRC_DIR="src"
BIN_DIR="bin"

# Vérifier si le répertoire src existe
if [ ! -d "$SRC_DIR" ]; then
    echo "Erreur : Le répertoire '$SRC_DIR' est introuvable."
    exit 1
fi

# Compiler les fichiers Java
echo "Compilation des fichiers Java..."
javac @"$SRC_DIR/sources.txt" -d "$BIN_DIR"
if [ $? -ne 0 ]; then
    echo "Erreur : La compilation a échoué."
    exit 1
fi

# Vérifier si le répertoire bin existe après la compilation
if [ ! -d "$BIN_DIR" ]; then
    echo "Erreur : Le répertoire '$BIN_DIR' est introuvable après la compilation."
    exit 1
fi

# Exécuter la classe de test
echo "Exécution de la classe de test : $1"
java -cp "$BIN_DIR" test."$1"
if [ $? -ne 0 ]; then
    echo "Erreur : Impossible d'exécuter la classe $1."
    exit 1
fi

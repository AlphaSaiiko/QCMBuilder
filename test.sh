#!/bin/bash

# Vérifier si un argument est fourni
if [ -z "$1" ]; then
    echo "Erreur : Vous devez fournir le nom de la classe à tester."
    echo "Exemple : ./test.sh [nom de la classe à tester]"
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
javac $(find "$SRC_DIR" -name "*.java") -d "$BIN_DIR"
if [ $? -ne 0 ]; then
    echo "Erreur : La compilation a échoué."
    exit 1
fi

# Vérifier si le répertoire bin existe après la compilation
if [ ! -d "$BIN_DIR" ]; then
    echo "Erreur : Le répertoire '$BIN_DIR' est introuvable après la compilation."
    exit 1
fi

# Normaliser le nom de la classe (sans tenir compte des majuscules et avec ou sans "Test")
CLASS_TO_TEST=$(echo "$1")

# Exécuter la classe de test
echo "Exécution de la classe de test : $CLASS_TO_TEST"
java -cp "$BIN_DIR" test."$CLASS_TO_TEST"
if [ $? -ne 0 ]; then
    echo "Erreur : Impossible d'exécuter la classe $CLASS_TO_TEST."
    exit 1
fi


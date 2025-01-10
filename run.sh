#!/bin/bash

# Définir les répertoires des sources et des binaires
SRC_DIR="src"
BIN_DIR="bin"

# Vérifier si le répertoire src existe
if [ ! -d "$SRC_DIR" ]; then
    echo "Erreur : Le répertoire '$SRC_DIR' est introuvable."
    exit 1
fi

# Créer le répertoire bin s'il n'existe pas
mkdir -p "$BIN_DIR"

# Compiler tous les fichiers Java en une seule commande
echo "Compilation des fichiers Java..."

# Collecter tous les fichiers .java dans le répertoire src et ses sous-répertoires
SOURCES=$(find "$SRC_DIR" -name "*.java")

# Compiler les fichiers Java
javac -d "$BIN_DIR" $SOURCES
if [ $? -ne 0 ]; then
    echo "Erreur : La compilation a échoué."
    exit 1
fi

# Exécution de Controleur.class
java -cp bin controleur.Controleur

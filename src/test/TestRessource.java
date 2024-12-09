package test;

import java.io.File;

import modele.*;

public class TestRessource
{
	public static void main(String[] args) {
		Ressource tmpRes;

		tmpRes = new Ressource("ressource1");

		// Création d'un objet File représentant le répertoire
		File repertoire = new File("../lib/ressources/"+tmpRes.getNom());

		// Vérification et création du répertoire
		if (!repertoire.exists()) {
			boolean estCree = repertoire.mkdirs(); // mkdir() pour un seul niveau, mkdirs() pour plusieurs niveaux
			if (estCree) {
				System.out.println("Répertoire créé avec succès !");
			} else {
				System.out.println("Échec de la création du répertoire.");
			}
		} else {
			System.out.println("Le répertoire existe déjà.");
		}
	}
}

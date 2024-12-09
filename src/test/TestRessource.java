package test;

import java.io.File;
import java.util.ArrayList;

import modele.*;
import modele.option.*;

public class TestRessource
{
	public static void main(String[] args) {
		/*Ressource tmpRes;

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
		}*/

		ArrayList<Ressource> lstRes = new ArrayList<>();

		for (int i = 0; i <= 6; i++) {
			Ressource ressource = new Ressource("ressource" + (i+1));
			lstRes.add(ressource);

			for (int j = 0; j <= 3; j++) {
				ressource.ajouterNotion(new Notion("notion" + (j+1)));
				for (int k=0; k<=10 ;k++)
				{
					ressource.getNotion(j).ajouterQuestion(new Question("type", "intitulé", i, k, i, ressource.getNotion(j)));
					for(int l=0; l<=10;l++)
					{
						ressource.getNotion(j).getQuestion(k).ajouterOption(new Option("Question", "çava?", true));
					}
				}
			}

		}


		TestCreerFichier fichier = new TestCreerFichier(lstRes);
		fichier.creerRessource();


		
	}

}

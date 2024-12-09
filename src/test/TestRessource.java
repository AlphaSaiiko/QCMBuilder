package test;

import java.io.File;
import java.util.ArrayList;

import javax.swing.text.html.Option;

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

		for (int i = 0; i <= 2; i++) {
			Ressource ressource = new Ressource("ressource" + (i+1));
			lstRes.add(ressource);

			for (int j = 0; j <= 3; j++) {
				ressource.ajouterNotion(new Notion("notion" + (j+1)));
				for (int k=0; k<=5 ;k++)
				{
					ressource.getNotion(j).ajouterQuestion(new Question("type", "intitulé", i, k, i, ressource.getNotion(j)));
					for(int l=0; l<=5;l++)
					{
						//ressource.getNotion(j).getQuestion(k).ajouterOption(new Option("Question", "çava?", true));
					}
				}
			}

		}


		CreerFichier fichier = new CreerFichier(lstRes);
		fichier.creerRessource();

		Fichier fich = new Fichier("lib/ressources/ressource1/notion1/");
		fich.ajouterTxt("testFichier");
		fich.ajouterFichier("repertoirTest");


		
	}

}

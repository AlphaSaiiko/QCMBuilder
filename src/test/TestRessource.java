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

		for (int i = 0; i <= 2; i++) {
			Ressource ressource = Ressource.creerRessource("ressource" + (i+1));
			lstRes.add(ressource);

			for (int j = 0; j < 3; j++) {
				ressource.ajouterNotion(Notion.creerNotion("notion" + (j), ressource));
				for (int k=0; k<3 ;k++)
				{
					ressource.getNotion(j).ajouterQuestion(new Question("type", "intitulé", i, k, i, ressource.getNotion(j)));
				}
			}

		}

		for (Ressource ressource : lstRes)
		{
			for (Notion notion : ressource.getEnsNotions())
			{
				for (Question question : notion.getEnsQuestions())
				{
					for(int l=0; l<3;l++)
					{
						question.ajouterOption(new Option("Question", "çava?", true,question));
					}
				}
			}
		}


		
	}

}

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import modele.*;
import modele.option.*;

public class QuestionCreator {

	public static List<Question> creerQuestions(String cheminRacine) {
		List<Question> questions = new ArrayList<>();
		File dossierRacine = new File(cheminRacine);

		// Vérifie que le dossier racine existe
		if (!dossierRacine.exists() || !dossierRacine.isDirectory()) {
			System.out.println("Le dossier racine n'existe pas ou n'est pas un dossier.");
			return questions;
		}

		// Parcourir récursivement les dossiers pour trouver les fichiers .rtf
		parcourirDossiers(dossierRacine, questions);

		return questions;
	}

	private static void parcourirDossiers(File dossier, List<Question> questions) {
		for (File sousFichier : dossier.listFiles()) {
			if (sousFichier.isDirectory()) {
				parcourirDossiers(sousFichier, questions);
			} else if (sousFichier.isFile() && sousFichier.getName().endsWith(".rtf")) {
				Question question = lireQuestionDepuisFichier(sousFichier);
				if (question != null) {
					questions.add(question);
				}
			}
		}
	}

	private static Question lireQuestionDepuisFichier(File fichier) {
		try {
			// Lire la première ligne du fichier .rtf
			List<String> lignes = Files.readAllLines(fichier.toPath());
			if (!lignes.isEmpty()) {
				String[] params = lignes.get(0).split("\t");
				if (params.length == 6) {
					// Extraire les paramètres
					String type = params[0];
					String intitule = params[1];
					int nbPoints = Integer.parseInt(params[2]);
					int temps = Integer.parseInt(params[3]);
					int difficulte = Integer.parseInt(params[4]);
					Notion notion = Notion.creerNotion(params[5], Ressource.creerRessource("intitule"));

					// Créer l'objet Question
					return new Question(type, intitule, nbPoints, temps, difficulte, notion);
				}
			}
		} catch (IOException e) {
			System.err.println("Erreur lors de la lecture du fichier : " + fichier.getAbsolutePath());
		} catch (NumberFormatException e) {
			System.err.println("Erreur de format dans le fichier : " + fichier.getAbsolutePath());
		}

		return null;
	}

	public static void main(String[] args) {
		String cheminRacine = "/lib/ressources"; // Chemin vers le dossier racine
		List<Question> questions = creerQuestions(cheminRacine);

		// Afficher les questions créées
		for (Question question : questions) {
			System.out.println(question);
		}
	}
}
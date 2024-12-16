package controleur;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;
import modele.*;
import modele.option.Option;
import modele.option.OptionAssociation;
import modele.option.OptionElimination;
import vue.*;

public class Controleur
{
	/*
	 * +------------+
	 * | PARAMETRES |
	 * +------------+
	 */

	private static Accueil acc;
	private static Metier metier;

	/*
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */
	public Controleur()
	{
		Controleur.metier = new Metier(this);
	}

	/*
	 * +-------------------------------+
	 * | CHARGER RESSOURCES ET NOTIONS |
	 * +-------------------------------+
	 */
	public static void chargerRessourcesEtNotion()
	{
		String resourcesPath = "lib/ressources";
		try (Stream<Path> paths = Files.walk(Paths.get(resourcesPath)))
		{
			paths.filter(Files::isDirectory).forEach(path ->
			{
				File dir = path.toFile();
				File parent = dir.getParentFile();

				// Si le répertoire actuel est une ressource
				if (parent != null && parent.getName().equals("ressources"))
				{
					System.out.println("Ressource trouvée : " + dir.getName());
					String[] parts = dir.getName().split("_", 2);
					if (parts.length == 2) {
						String nom = parts[0];
						String id = parts[1];
						Ressource.creerRessource(id, nom);
					} else {
						System.out.println("Nom de répertoire invalide, attendu (id_nom)");
					}
				}
								
				// Si le répertoire parent est une ressource
				else if (parent != null && parent.getParentFile() != null && parent.getParentFile().getName().equals("ressources"))
				{
					String[] parts = parent.getName().split("_", 2);
					if (parts.length == 2) {
						String id = parts[0];
						String nom = parts[1];
						Ressource ressource = Ressource.creerRessource(id, nom);
						if (ressource == null)
						{
							System.out.println("Ressource non trouvée pour la notion, création d'une nouvelle ressource : " + parent.getName());
							Ressource.creerRessource(id, nom);
							ressource = Ressource.creerRessource(id, nom);
						}

						// Création de la notion
						Notion notion = Notion.creerNotion(dir.getName(), ressource);
						System.out.println("Notion créée : " + notion.getNom() + " pour la ressource : " + ressource.getNom());

						Controleur.chargerQuestion(notion, dir);

						// Ajout de la notion à la ressource
						System.out.println("Notion ajoutée à la ressource : " + ressource.getNom());
					} else {
						System.out.println("Nom de répertoire parent invalide, attendu (id_nom)");
					}
				}

			});
		}
		catch (IOException e)
		{
			System.err.println("Erreur lors du chargement des ressources et des notions : " + e.getMessage());
		}
	}

	/*
	 * +--------------------+
	 * | CHARGER QUESTIONS  |
	 * +--------------------+
	 */
	public static void chargerQuestion(Notion notion, File dir)
	{
		int cpt = 0;
		// Boucle pour chaque question
		for (File dossier : dir.listFiles())
		{
			File fichierRTF;
			if (dossier.listFiles() != null)
			{
				fichierRTF = new File(dossier, dossier.getName() + ".rtf");
			}
			else return;

			try
			{
				Scanner sc = new Scanner(new FileInputStream(fichierRTF));
				String ligneQuestion = sc.nextLine();
				Object[] ligne = ligneQuestion.split("\t");

				String type = String.valueOf(ligne[0]);
				String intitule = String.valueOf(ligne[1]);
				int nbPoints = Integer.valueOf(String.valueOf(ligne[2]));
				int temps = Integer.valueOf(String.valueOf(ligne[3]));
				int difficulte = Integer.valueOf(String.valueOf(ligne[4]));

				Question tmp = Question.creerQuestion(nbPoints, temps, notion, difficulte, type);

				sc.close();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			cpt++;
		}
	}

	/*
	 * +-------------------+
	 * | METHODES D'ACTION |
	 * +-------------------+
	 */
	
	public static void creerEvaluation()
	{
		new CreerEvaluation();
	}

	public static void ouvrirCreerQuestion()
	{
		new CreerQuestion();
	}

	public static void ouvrirCreerEvaluation()
	{
		new CreerEvaluation();
	}

	public static void ouvrirParametres()
	{
		new Parametre();
	}

	public static void ouvrirAccueil()
	{
		new Accueil();
	}

	public static void creerNotion(String titreNotion, Ressource ressource)
	{
		Notion.creerNotion(titreNotion, ressource);
	}

	public static OptionElimination creerReponseElimination(String texte, int ordre, double points, boolean correct, Question question)
	{
		return new OptionElimination("QAEPR", texte, correct, ordre, points, question);
	}

	public static OptionAssociation creerReponseAssociation( String enonce, Question question)
	{
		return new OptionAssociation("QAE", enonce, question);
	}

	public static Option creerReponse(String enonce, boolean validite, Question question)
	{
		return new Option("QCM", enonce, validite, question);
	}

	public static void creerRessource(String nom, String id)
	{
		Ressource.creerRessource(nom, id);
	}


	/*
	 * +-----------------+
	 * | METHODES METIER |
	 * +-----------------+
	 */
	

	public static Ressource trouverRessourceParNom(String nom)
	{
		return Metier.trouverRessourceParNom(nom);
	}

	public static Notion trouverNotionParNom(String nom)
	{
		return Metier.trouverNotionParNom(nom);
	}

	public static String[] getNomsRessources()
	{
		return Metier.getNomsRessources();
	}

	public static String[] getNomsNotions()
	{
		return Metier.getNomsNotions();
	}

	public static List<Ressource> getListRessource()
	{
		return Metier.getListRessource();
	}

	public static List<Notion> getListNotion()
	{
		return Metier.getListNotion();
	}

	public static List<Question> getListQuestion()
	{
		return Metier.getListQuestion();
	}

    public static void ajouterRessource(Ressource ressource)
    {
        Metier.ajouterRessource(ressource);
    }

	public static void ajouterNotion(Notion notion)
	{
		Metier.ajouterNotion(notion);
	}

	public static void ajouterQuestion(Question question)
	{
		Metier.ajouterQuestion(question);
	}

	public static void supprRessource(Ressource ressource)
	{
		Metier.supprRessource(ressource);
		Ressource.mettreAJourRessources();
	}

	public void supprNotion(Notion notion)
	{
		Metier.supprNotion(notion);
		Notion.mettreAJourNotions();
	}

	public void supprQuestion(Question question)
	{
		Metier.supprQuestion(question);
		Question.mettreAJourQuestions();
	}

	/*
	 * +-------+
	 * | MAIN  |
	 * +-------+
	 */
	public static void main(String[] args)
	{
		Controleur controleur = new Controleur();
		controleur.chargerRessourcesEtNotion();
		Controleur.acc = new Accueil();
	}
}

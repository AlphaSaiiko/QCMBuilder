package controleur;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;
import modele.*;
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
					Ressource.creerRessource(dir.getName());
				}
				
				// Si le répertoire parent est une ressource
				else if (parent != null && parent.getParentFile() != null && parent.getParentFile().getName().equals("ressources"))
				{
					Ressource ressource = Ressource.creerRessource(parent.getName());
					if (ressource == null)
					{
						System.out.println("Ressource non trouvée pour la notion, création d'une nouvelle ressource : " + parent.getName());
						Ressource.creerRessource(parent.getName());
						ressource = Ressource.creerRessource(parent.getName());
					}

					// Création de la notion
					Notion notion = Notion.creerNotion(dir.getName(), ressource);
					System.out.println("Notion créée : " + notion.getNom() + " pour la ressource : " + ressource.getNom());

					Controleur.chargerQuestion(notion, dir);

					// Ajout de la notion à la ressource
					System.out.println("Notion ajoutée à la ressource : " + ressource.getNom());
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

				notion.ajouterQuestion(tmp);
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

	public static void creerQuestion()
	{
		new CreerQuestion();
	}

	public static void ouvrirParametres()
	{
		new Parametre();
	}

	public static void ouvrirAccueil()
	{
		new Accueil();
	}
    
    public static void ouvrirTabEvaluation()
    {
        new TabEvaluation();
    }

	public static void creerNotion(String titreNotion, Ressource ressource)
	{
		Notion.creerNotion(titreNotion, ressource);
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

	/*
	 * +-------+
	 * | MAIN  |
	 * +-------+
	 */
	public static void main(String[] args)
	{
		Controleur controleur = new Controleur();
		Controleur.acc = new Accueil();
		controleur.chargerRessourcesEtNotion();
	}
}

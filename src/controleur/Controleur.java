package controleur;

import java.io.*;
import java.util.*;
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
		/*String resourcesPath = "lib/ressources";
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
		}*/

		try {
			Scanner scRes = new Scanner(new FileInputStream("./Ressources.csv"));

			String nomRessource;
			String idRessource;
			Ressource ressource;

			while (scRes.hasNextLine())
			{
				String ligne = scRes.nextLine();

				boolean aNotion = !(ligne.indexOf("notions:") == -1);

				idRessource  = ligne.substring(3, ligne.indexOf("nom:")-1);
				if (aNotion) nomRessource = ligne.substring(ligne.indexOf("nom:")+4, ligne.indexOf("notions:")-1);
				else         nomRessource = ligne.substring(ligne.indexOf("nom:")+4);
				


				ressource = Ressource.creerRessource(nomRessource, idRessource);


				if (aNotion)
				{
					Notion notion;

					
					ligne = ligne.substring(ligne.indexOf("notions:")+8);
					String[] ligneQuestion = ligne.split(",");

					for (int cpt = 0; cpt < ligneQuestion.length; cpt++)
					{
						notion = Notion.creerNotion(ligneQuestion[cpt], ressource);
							
						Controleur.chargerQuestion(notion);


						// Ajout de la notion à la ressource
						System.out.println("Notion ajoutée à la ressource : " + ressource.getId() + "_" + ressource.getNom());
					}
				}
				
				

				
			}

		} catch (Exception e) {
			System.err.println("Erreur lors du chargement des ressources et des notions : " );
			e.printStackTrace();
		}
		


	}

	/*
	 * +--------------------+
	 * | CHARGER QUESTIONS  |
	 * +--------------------+
	 */
	public static void chargerQuestion(Notion notion/* , File dir */)
	{
		try {
			Scanner scQst = new Scanner(new FileInputStream("./Questions.csv"));

			String nomQuestion;
			Question question;

			while (scQst.hasNextLine())
			{
				String ligne = scQst.nextLine();

				String[] ligneQuestion = ligne.split("\t");

				nomQuestion = ligneQuestion[0].substring(4);
				int nbPoints   = Integer.valueOf(ligneQuestion[1].substring(8));
				int temps      = Integer.valueOf(ligneQuestion[2].substring(6));
				int difficulte = Integer.valueOf(ligneQuestion[3].substring(10));
				String type    =                 ligneQuestion[4].substring(5);

				question = Question.creerQuestion(nbPoints, temps, notion, difficulte, type);

				/*   Finir la méthode trouverOptionParId() dans question pour décommenter
				// Si la question à des options
				if (ligneQuestion[5].length() > 12)
				{
					ligne = ligne.substring(ligne.indexOf("options :")+9);

					String[] tabReponse = ligne.split(" | ");
					for (int cpt = 0; cpt < tabReponse.length-2; cpt++)
					{
						String[] attributsRep = tabReponse[cpt].split(";");

						String typeO  = attributsRep[0];
						String enonce = attributsRep[1];
						int    id     = Integer.valueOf(attributsRep[2]);

						int     idAssocie;
						boolean estReponse;
						int     ordre;
						double  pointsMoins;
						if (typeO == "QAE")
						{
							idAssocie = Integer.valueOf(attributsRep[3]);
						}
						else
						{
							estReponse = Boolean.valueOf(attributsRep[4]);

							if (typeO == "QAEPR")
							{
								ordre       = Integer.valueOf(attributsRep[5]);
								pointsMoins = Double.valueOf(attributsRep[6]);
							}
						}

						switch (typeO)
						{
							case "QAE"  -> question.ajouterOption((new OptionAssociation(typeO, enonce, question)).setAssocie(question.trouverOptionParId(idAssocie)));
							case "QAEP" -> question.ajouterOption(new OptionElimination(typeO, enonce, estReponse, ordre, pointsMoins, question));
							default     -> question.ajouterOption(new Option(typeO, enonce, estReponse, question));
						}
					}
				}

				scQst.close();


*/
				


				
			}

		} catch (Exception e) {
			System.err.println("Erreur lors du chargement des questions : " + e.getMessage());
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

	public static void creerRessource(String titreRessource, String idRessource)
	{
		Ressource.creerRessource(titreRessource, idRessource);
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


	/*
	 * +-----------------+
	 * | METHODES METIER |
	 * +-----------------+
	 */
	

	public static Ressource trouverRessourceParId(String id)
	{
		return Metier.trouverRessourceParId(id);
	}

	public static Notion trouverNotionParNom(String nom)
	{
		return Metier.trouverNotionParNom(nom);
	}

	public static String[] getNomsRessources()
	{
		return Metier.getNomsRessources();
	}

	public static String[] getIDsNomsRessources()
	{
		return Metier.getIDsNomsRessources();
	}

	public static String[] getNomsNotions()
	{
		return Metier.getNomsNotions();
	}

	public static List<Ressource> getListRessource()
	{
		return Metier.getListeRessources();
	}

	public static List<Notion> getListNotion()
	{
		return Metier.getListeNotions();
	}

	public static List<Question> getListQuestion()
	{
		return Metier.getListeQuestions();
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
		Metier.supprimerRessource(ressource);
		Ressource.mettreAJourRessources();
	}

	public static void supprNotion(Ressource ressource, Notion notion)
	{
		Metier.supprimerNotion(notion);
		Metier.retirerNotion(ressource, notion);
		Notion.mettreAJourNotions();
	}

	public void supprQuestion(Question question)
	{
		Metier.supprimerQuestion(question);
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

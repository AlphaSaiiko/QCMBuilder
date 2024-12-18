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
		try {
			Scanner scRes = new Scanner(new FileInputStream("./RessourceEtNotion.csv"));

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
	public static void chargerQuestion(Notion notion) {
		int cpt = 0; // Boucle pour chaque question
		File dir = new File("./lib/ressources/" + notion.getRessource().getId() + "_" + notion.getRessource().getNom() + "/" + notion.getNom() + "/");
		for (File dossier : dir.listFiles()) {
			File fichierRTF;
			if (dossier.listFiles() != null) {
				System.out.println("Dossier : " + dossier.getName());
				fichierRTF = new File(dossier, dossier.getName() + ".rtf");
			} else {
				return;
			}
			
			try {
				Scanner sc = new Scanner(new FileInputStream(fichierRTF));
				String ligneQuestion = sc.nextLine();
				Object[] ligne = ligneQuestion.split(";");
				
				if (ligne.length < 5) {
					System.err.println("Erreur : la ligne ne contient pas assez d'éléments.");
					sc.close();
					return;
				}
	
				String type = String.valueOf(ligne[0]);
				String intitule = String.valueOf(ligne[1]);
				int nbPoints = Integer.valueOf(String.valueOf(ligne[2]));
				int temps = Integer.valueOf(String.valueOf(ligne[3]));
				int difficulte = Integer.valueOf(String.valueOf(ligne[4]));
				
				
				Question tmp = Question.creerQuestion(nbPoints, temps, notion, difficulte, type);
				tmp.setEnonce(intitule);
				System.out.println("Question créée : " + tmp.getEnonce());
				sc.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			cpt++;
		}
	}
	


		/*try {
			Scanner scQst = new Scanner(new FileInputStream("./Questions.csv"));

			String enonce;
			Question question;

			while (scQst.hasNextLine())
			{
				String ligne = scQst.nextLine();

				System.out.println("Ligne : " + ligne);

				String[] ligneQuestion = ligne.split(";");

				enonce         =                 ligneQuestion[0] ;
				int nbPoints   = Integer.valueOf(ligneQuestion[1]);
				int temps      = Integer.valueOf(ligneQuestion[2]);
				int difficulte = Integer.valueOf(ligneQuestion[3]);
				String type    =                 ligneQuestion[4] ;

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
				
			}

		} catch (Exception e) {
			System.err.println("Erreur lors du chargement des questions : " + e.getMessage());
		}*/

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
		Ressource.mettreAJourRessources();
	}

	public void supprQuestion(Question question)
	{
		Metier.supprimerQuestion(question);
		question.mettreAJourQuestions();
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

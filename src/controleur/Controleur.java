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

				if (ligne.length > 6)
				{
					String reponses = String.valueOf(ligne[6]);
					switch (tmp.getType()) {
						case "QCMRU":
						case "QCMRM":
							String[] reponsesQCM = reponses.split("\\|");
							for (int i = 0; i < reponsesQCM.length; i++) {
								String[] reponse = reponsesQCM[i].split("/");
								Option o = Controleur.creerReponse(reponse[1], Boolean.parseBoolean(reponse[3]), tmp);
								tmp.ajouterOption(o);
								System.out.println("Réponse créée : " + reponsesQCM[i]);
							}
							break;
						
						case "QAE":
							String[] reponsesQAE = reponses.split("\\|");
							OptionAssociation previousOption = null;
							for (int i = 0; i < reponsesQAE.length; i++) {
								String[] reponse = reponsesQAE[i].split("/");
								OptionAssociation currentOption = Controleur.creerReponseAssociation(reponse[1], tmp);
								
								// Si previousOption n'est pas null, associe les deux options
								if (previousOption != null) {
									previousOption.setAssocie(currentOption);
									currentOption.setAssocie(previousOption);
									
									// Ajoute les options à la question
									tmp.ajouterOption(previousOption);
									tmp.ajouterOption(currentOption);

									// Réinitialise previousOption pour la prochaine paire
									previousOption = null;
								} else {
									// Stocke l'option actuelle pour la prochaine itération
									previousOption = currentOption;
								}
							}

							// Si il reste une option non associée
							if (previousOption != null) {
								System.err.println("Attention : une réponse reste non associée.");
							}

							break;

						case "QAEPR":
							String[] reponsesQAEPR = reponses.split("\\|");
							for (int i = 0; i < reponsesQAEPR.length; i++) {
								String[] reponse = reponsesQAEPR[i].split("/");
								OptionElimination o = Controleur.creerReponseElimination(reponse[1], Integer.parseInt(reponse[4]), Double.parseDouble(reponse[5]), Boolean.parseBoolean(reponse[3]), tmp);
								tmp.ajouterOption(o);
								System.out.println("Réponse créée : " + reponsesQAEPR[i]);
							}
								
							break;
						default:
							break;
					}
				}
				
				sc.close();
			} catch (FileNotFoundException e) {
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

	public static void recupererQuestion(Evaluation eval, Notion notion, int tf, int f, int m, int d)
	{
		eval.recupererQuestion(notion, tf, f, m, d);
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

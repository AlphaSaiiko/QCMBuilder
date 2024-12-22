package modele;

import controleur.Controleur;
import controleur.ControleurFichier;
import java.util.ArrayList;
import java.util.List;

public class Notion
{
	/**
	 * +-----------+
	 * | ATTRIBUTS |
	 * +-----------+
	 */ 
	private List<Question> listeQuestions;
	private String         nom           ;
	private Ressource      ressource     ;
	private int questionTresFacile, questionFacile, questionMoyen, questionDifficile;




	/**
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */

	public Notion(String nom, Ressource ressource)
	{
		this.nom       = nom      ;
		this.ressource = ressource;

		Controleur.ajouterNotion(this);
		this.creerFichierNotion();
		this.listeQuestions = new ArrayList<>();
		ressource.ajouterNotion(this);
		Ressource.mettreAJourRessources();
		this.questionDifficile = this.questionFacile = this.questionMoyen = this.questionTresFacile = 0;
	}



	/**
	 * Crée une nouvelle notion associée à une ressource si elle n'existe pas déjà.
	 *
	 * @param nom Le nom de la notion à créer.
	 * @param ressource La ressource à laquelle la notion est associée.
	 * @return La notion existante si elle est déjà présente, ou la nouvelle notion créée.
	 */
	public static Notion creerNotion(String nom, Ressource ressource)
	{
		Notion notion = Notion.trouverNotionParNom(nom, ressource);
		if (notion == null)
		{
			notion = new Notion(nom, ressource);
		}
		else
			System.out.println("La notion existe déjà dans la ressource: " + ressource.getNom());
			
		return notion;
	}




	/**
	 * +----------+
	 * | GETTEURS |
	 * +----------+
	 */

	public String         getNom           ()           { return nom                            ; }
	public Ressource      getRessource     ()           { return this.ressource                 ; }
	public int            getNbQuestion    ()           { return this.listeQuestions.size()     ; }
	public List<Question> getListeQuestions()           { return this.listeQuestions            ; }
	public Question       getQuestion      (int indice) { return this.listeQuestions.get(indice); }
	public int getNbQuestionTresFacile	   ()			{return this.questionTresFacile			; }
	public int getNbQuestionFacile		   ()			{return this.questionFacile				; }
	public int getNbQuestionMoyen		   ()			{return this.questionMoyen				; }
	public int getNbQuestionDifficile	   ()			{return this.questionDifficile			; }




	/**
	 * +----------+
	 * | SETTEURS |
	 * +----------+
	 */ 

	public void setNom(String nom)
	{
		this.nom       = nom      ;
		Ressource.mettreAJourRessources();
	}

	public void setRessource(Ressource ressource) { this.ressource = ressource; }




	/**
	 * +----------+
	 * | METHODES |
	 * +----------+
	 */

	 /**
	 * Crée un fichier pour la notion dans le répertoire associé à la ressource.
	 * Le fichier est ajouté au chemin de la ressource correspondante.
	 */
	public void creerFichierNotion()
	{
		ControleurFichier fichierControleur = new ControleurFichier("lib/ressources/" + ressource.getId() + "_" + ressource.getNom() + "/");
		fichierControleur.ajouterFichier(this.getNom());
	}
	


	/**
	 * Ajoute une question à l'ensemble des questions associées à la notion.
	 * Met à jour les notions après l'ajout.
	 *
	 * @param question La question à ajouter. Elle ne doit pas être nulle.
	 */
	public void ajouterQuestion(Question question)
	{
		if (question != null)
		{
			this.listeQuestions.add(question);
			Ressource.mettreAJourRessources();

			switch (question.getDifficulte()) {
				case 1: this.questionTresFacile++;
					break;
				case 2: this.questionFacile++;
					break;
				case 3: this.questionMoyen++;
					break;
				case 4: this.questionDifficile++;
					break;
			
				default:
					break;
			}
		}
	}



	/**
	 * Supprime une question de l'ensemble des questions associées à la notion.
	 * Met à jour les notions après la suppression.
	 *
	 * @param question La question à supprimer. Elle doit correspondre à une question existante.
	 */
	public void supprimerQuestion(Question question)
	{
		this.listeQuestions.removeIf(qst -> question.equals(qst));
		Ressource.mettreAJourRessources();
	}



	/**
	 * Recherche une notion dans une ressource en utilisant son nom.
	 *
	 * @param nom Le nom de la notion à rechercher.
	 * @param ressource La ressource contenant les notions.
	 * @return La notion correspondante si elle est trouvée, sinon null.
	 */
	public static Notion trouverNotionParNom(String nom, Ressource ressource)
	{
		for (Notion notion : ressource.getEnsNotions())
		{
			if (notion.getNom().equals(nom))
				return notion;
		}

		return null;
	}
}

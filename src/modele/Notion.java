package modele;

import java.util.ArrayList;
import java.util.List;

import controleur.Controleur;
import controleur.ControleurFichier;

public class Notion
{
	/**
	 * +-----------+
	 * | ATTRIBUTS |
	 * +-----------+
	 */ 
	private List<Question> ensQuestions;
	private String         nom         ;
	private Ressource      ressource   ;




	/**
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */

	private Notion(String nom, Ressource ressource)
	{
		this.nom       = nom      ;
		this.ressource = ressource;

		Controleur.ajouterNotion(this);
		this.creerFichierNotion();
		this.ensQuestions = new ArrayList<>();
		ressource.ajouterNotion(this);
		Notion.mettreAJourNotions();
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
			System.out.println("Nouvelle notion créée avec le titre: " + nom + " pour la ressource: " + ressource.getNom());
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

	public String         getNom         ()           { return nom                          ; }
	public Ressource      getRessource   ()           { return this.ressource               ; }
	public int            getNbQuestion  ()           { return this.ensQuestions.size()     ; }
	public List<Question> getEnsQuestions()           { return this.ensQuestions            ; }
	public Question       getQuestion    (int indice) { return this.ensQuestions.get(indice); }




	/**
	 * +----------+
	 * | SETTEURS |
	 * +----------+
	 */ 

	public void setNom      (String    nom      ) { this.nom       = nom      ; }
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
		ControleurFichier fichierControleur = new ControleurFichier("lib/ressources/" + ressource.getNom() + "/");
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
			this.ensQuestions.add(question);
			Notion.mettreAJourNotions();
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
		this.ensQuestions.removeIf(qst -> question.equals(qst));
		Notion.mettreAJourNotions();
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



	/**
	 * Met à jour les notions en les enregistrant dans le fichier de stockage.
	 */
	public static void mettreAJourNotions()
	{
		List<Notion> notions = Controleur.getListNotion();
		ControleurFichier.ecrireNotions(notions);
	}
}

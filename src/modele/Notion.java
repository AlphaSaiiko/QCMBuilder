package modele;

import controleur.Controleur;
import controleur.ControleurFichier;
import controleur.Ecrire;
import java.util.ArrayList;
import java.util.List;

public class Notion
{
	/*
	 * +------------+
	 * | PARAMETRES |
	 * +------------+
	 */ 
	private List<Question> ensQuestions;
	private String nom;
	private Ressource ressource;

	/*
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
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
		{
			System.out.println("La notion existe déjà dans la ressource: " + ressource.getNom());
		}
		return notion;
	}

	private Notion(String nom, Ressource ressource)
	{
		this.nom = nom;
		this.ressource = ressource;
		Controleur.ajouterNotion(this);
		this.creerFichierNotion();
		this.ensQuestions = new ArrayList<>();
		ressource.ajouterNotion(this);
		Notion.mettreAJourNotions();
	}

	/*
	 * +---------+
	 * | GETTERS |
	 * +---------+
	 */
	public String getNom()
	{
		return nom;
	}
	
	public Ressource getRessource()
	{
		return this.ressource;
	}

	public int getNbQuestion()
	{
		return this.ensQuestions.size();
	}

	public List<Question> getEnsQuestions()
	{
		return this.ensQuestions;
	}

	public Question getQuestion(int indice)
	{
		return this.ensQuestions.get(indice);
	}

	/*
	 * +---------+
	 * | SETTERS |
	 * +---------+
	 */ 
	public void setNom(String nom)
	{
		this.nom = nom;
	}

	public void setRessource(Ressource ressource)
	{
		this.ressource = ressource;
	}

	/*
	 * +----------+
	 * | METHODES |
	 * +----------+
	 */
	public void creerFichierNotion()
	{
		ControleurFichier fichierControleur = new ControleurFichier("lib/ressources/" + ressource.getNom() + "/");
		fichierControleur.ajouterFichier(this.getNom());
	}
	
	public void ajouterQuestion(Question question)
	{
		if (question != null)
		{
			this.ensQuestions.add(question);
			Notion.mettreAJourNotions();
		}
	}

	public void supprimerQuestion(Question question)
	{
		this.ensQuestions.removeIf(qst -> question.equals(qst));
		Notion.mettreAJourNotions();
	}

	public static Notion trouverNotionParNom(String nom, Ressource ressource)
	{
		for (Notion notion : ressource.getEnsNotions())
		{
			if (notion.getNom().equals(nom))
			{
				return notion;
			}
		}
		return null;
	}

	public static void mettreAJourNotions()
	{
		List<Notion> notions = Controleur.getListNotion();
		Ecrire.ecrireNotions(notions);
	}
}

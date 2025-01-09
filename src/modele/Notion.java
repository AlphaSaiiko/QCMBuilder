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

	private int            questionD     ;
	private int            questionF     ;
	private int            questionM     ;
	private int            questionTf    ;
	private List<Question> listeQuestions;
	private int            nbQuestion = 0;
	private Ressource      ressource     ;
	private String         nom           ;




	/**
	 * +-------------------------+
	 * | CONSTRUCTEUR ET FACTORY |
	 * +-------------------------+
	 */

	public Notion(String nom, Ressource ressource)
	{
		this.questionD      = 0                ;
		this.questionF      = 0                ;
		this.questionM      = 0                ;
		this.questionTf     = 0                ;
		this.listeQuestions = new ArrayList<>();
		this.ressource      = ressource        ;
		this.nom            = nom              ;

		Controleur.ajouterNotion(this);
		this.creerFichierNotion();
		ressource.ajouterNotion(this);
		Ressource.mettreAJourRessources();
	}



	public static Notion creerNotion(String nom, Ressource ressource)
	{
		Notion notion = Notion.trouverNotionParNom(nom, ressource);

		if (notion == null)
			notion = new Notion(nom, ressource);
			
		return notion;
	}




	/**
	 * +----------+
	 * | GETTEURS |
	 * +----------+
	 */

	public int            getNbQuestion          ()           { return this.nbQuestion                ; }
	public int            getNbQuestionDifficile ()           { return this.questionD                 ; }
	public int            getNbQuestionFacile    ()           { return this.questionF                 ; }
	public int            getNbQuestionMoyen     ()           { return this.questionM                 ; }
	public int            getNbQuestionTresFacile()           { return this.questionTf                ; }
	public List<Question> getListeQuestions      ()           { return this.listeQuestions            ; }
	public Question       getQuestion            (int indice) 
	{
		if (this.listeQuestions == null)           return null;
		if (this.listeQuestions.size() < indice+1) return null;
		return this.listeQuestions.get(indice);
	}
	public Ressource      getRessource           ()           { return this.ressource                 ; }
	public String         getNom                 ()           { return this.nom                       ; }




	/**
	 * +----------+
	 * | SETTEURS |
	 * +----------+
	 */ 

	public void setRessource(Ressource ressource) { this.ressource = ressource;                                    }
	public void setNom      (String nom)          { this.nom       = nom      ; Ressource.mettreAJourRessources(); }




	/**
	 * +----------+
	 * | METHODES |
	 * +----------+
	 */

	public void ajouterQuestion(Question question)
	{
		if (question != null)
		{
			this.listeQuestions.add(question);
			this.nbQuestion++;
			
			Ressource.mettreAJourRessources();

			switch (question.getDifficulte())
			{
				case 1 -> this.questionTf++;
				case 2 -> this.questionF ++;
				case 3 -> this.questionM ++;
				case 4 -> this.questionD ++;
			}
		}
	}



	public void creerFichierNotion()
	{
		ControleurFichier controleurFichier = new ControleurFichier(
			"lib/ressources/" + ressource.getId () + 
			"_"               + ressource.getNom() +
			"/"
		);

		controleurFichier.ajouterFichier(this.getNom());
	}
	

	
	public void supprimerQuestion(Question question)
	{
		this.listeQuestions.removeIf(qst -> question.equals(qst));
		Ressource.mettreAJourRessources();
	}



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

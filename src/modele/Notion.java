package modele;

import java.util.ArrayList;
import java.util.List;

public class Notion
{
	/*
	 *  +------------+
	 *  | PARAMETRES |
	 *  +------------+
	 */ 
	private String nom;
	private int nbQuestion;

	private Ressource ressource;

	private final  int id;
	private static int nbNotion = 0;

	private List<Question> ensQuestions;
	
	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
	public Notion(String nom, Ressource ressource)
	{
		this.nom = nom;
		this.ressource = ressource;
		this.id = ++nbNotion;
		this.nbQuestion = 0;
		this.ensQuestions = new ArrayList<Question>();
		this.creerFichierNotion();
		ressource.ajouterNotion(this);
	}


	/*
	 *  +----------+
	 *  | GETTEURS |
	 *  +----------+
	 */ 

	public String getNom() { return nom; }

	public int getId() { return this.id;}

	public List<Question> getEnsQuestions() { return ensQuestions; }

	public Question getQuestion (int indice) { return ensQuestions.get(indice);}

	public Ressource getRessource() {return this.ressource;}


	/*
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */ 
	public void setNom(String nom) { this.nom = nom; }


	/*
	 *  +----------+
	 *  | METHODES |
	 *  +----------+
	 */
	public void creerFichierNotion()
	{
		Fichier tmp = new Fichier("lib/ressources/"+ressource.getNom()+"/");
		if (!tmp.exists(this.getNom())) {
			tmp.ajouterFichier(this.getNom());
		}
	}
	
	public void ajouterQuestion(Question question)
	{
		if (question != null && this.ensQuestions != null)
		{
			this.ensQuestions.add(question);
			this.nbQuestion++;
		}
	}

	public int getNbQuestion(){return this.nbQuestion;}

	public void modifierQuestion(Question question)
	{
		for (Question qst : this.ensQuestions)
		{
			if (question.getId() == qst.getId())
			{
				qst = question;
			}
		}
	}

	public void supprimerQuestion(Question question)
	{
		for (Question qst : this.ensQuestions)
		{
			if (question.equals(qst))
			{
				this.ensQuestions.remove(qst);
			}
		}
	}
}

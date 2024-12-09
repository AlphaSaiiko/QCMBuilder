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

<<<<<<< HEAD
	private Ressource ressource;
=======
	private final  int id;
	private static int nbNotion = 0;
>>>>>>> 7cb81c917bd2c4dd650dbf83ea5a7ec127fa92a1

	private List<Question> ensQuestions;

	
	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
<<<<<<< HEAD
	public Notion(String nom, Ressource ressource)
	{
		this.nom = nom;
		this.ressource = ressource;
=======

	public Notion(String nom)
	{
		this.nom = nom;
		this.id = ++nbNotion;
>>>>>>> 7cb81c917bd2c4dd650dbf83ea5a7ec127fa92a1
		this.ensQuestions = new ArrayList<Question>();
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
	
	public void ajouterQuestion(Question question)
	{
		if (question != null && this.ensQuestions != null)
		{
			this.ensQuestions.add(question);
		}
	}

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

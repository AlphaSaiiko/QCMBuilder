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

	private final  int id;
	private static int nbNotion = 0;

	private List<Question> ensQuestions;

	
	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */

	public Notion(String nom)
	{
		this.nom = nom;
		this.id = ++nbNotion;
		this.ensQuestions = new ArrayList<Question>();
	}


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
}

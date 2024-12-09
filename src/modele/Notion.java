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

	private List<Question> ensQuestions;

	
	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
	public Notion(String nom)
	{
		this.nom = nom;
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

	public List<Question> getEnsQuestions() { return ensQuestions; }

	public Question getQuestion (int indice) { return ensQuestions.get(indice);}


	/*
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */ 
	public void setNom(String nom) { this.nom = nom; }
}

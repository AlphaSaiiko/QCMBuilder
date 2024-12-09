package modele;

import java.util.ArrayList;
import java.util.List;

public class Evaluation 
{
	/*
	 *  +------------+
	 *  | ATTRIBUTS  |
	 *  +------------+
	 */

	private Ressource ressource;
	private List<Question> listeQuestions;

	private String lienEval; // Lien de l'évaluation à mettre sur l'intranet


	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */

	public Evaluation(Ressource ressource)
	{
		this.ressource = ressource;
		this.listeQuestions = new ArrayList<Question>();
	}
	/*
	 *  +----------+
	 *  | GETTEURS |
	 *  +----------+
	 */

	

	public Ressource getRessource() 
	{
		return ressource;
	}

	public List<Question> getListeQuestions() 
	{
		return listeQuestions;
	}

	public String getLienEval() 
	{
		return lienEval;
	}


	/*
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */

	public void setRessource(Ressource ressource) {
		this.ressource = ressource;
	}

	public void setLienEval(String lienEval) {
		this.lienEval = lienEval;
	}


	/*
	 *  +----------+
	 *  | METHODES |
	 *  +----------+
	 */
	
	public boolean ajouterQuestion(Question qt)
	{
		if (this.listeQuestions != null && qt != null)
		{
			this.listeQuestions.add(qt);
			return true;
		}
		return false;
	}


	 
}

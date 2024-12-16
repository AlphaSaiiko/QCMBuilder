package modele;

import java.util.ArrayList;
import java.util.List;

public class Evaluation
{
	/**
	 * +------------+
	 * | ATTRIBUTS  |
	 * +------------+
	 */

	private Ressource       ressource    ;
	private List<Notion>   listeNotions  ;
	private List<Question> listeQuestions;
	private String         lienEval      ; // Lien de l'évaluation à mettre sur l'intranet




	/**
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */

	public Evaluation(Ressource ressource, Notion notion)
	{
		this.ressource	    = ressource                ;
		this.listeNotions   = new ArrayList<Notion>  ();
		this.listeQuestions = new ArrayList<Question>();
	}




	/*
	 * +----------+
	 * | GETTEURS |
	 * +----------+
	 */

	public Ressource      getRessource     () { return ressource     ; }
	public List<Notion>   getListeNotions  () { return listeNotions  ; }
	public List<Question> getListeQuestions() { return listeQuestions; }
	public String         getLienEval      () { return lienEval      ; }




	/*
	 * +----------+
	 * | SETTEURS |
	 * +----------+
	 */

	public void setRessource(Ressource ressource) { this.ressource = ressource; }
	public void setLienEval (String    lienEval ) { this.lienEval  = lienEval ; }




	/*
	 * +----------+
	 * | METHODES |
	 * +----------+
	 */

	public boolean ajouterQuestion(Question question)
	{
		if (this.listeQuestions != null && question != null)
		{
			this.listeQuestions.add(question);
			return true;
		}
		return false;
	}

	public void ajouterNotion(Notion notion)
	{
		if (notion != null && this.listeNotions != null)
			this.listeNotions.add(notion);
	}
}

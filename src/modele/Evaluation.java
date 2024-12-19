package modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Evaluation
{
	/**
	 * +-----------+
	 * | ATTRIBUTS |
	 * +-----------+
	 */

	private Ressource       ressource    ;
	private List<Notion>   listeNotions  ;
	private List<Question> listeQuestions;
	private String         lienEval      ; // Lien de l'évaluation à mettre sur l'intranet
	private boolean			bChrono;




	/**
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */

	public Evaluation(Ressource ressource, boolean bChrono)
	{
		this.ressource	    = ressource                ;
		this.listeNotions   = new ArrayList<Notion>  ();
		this.listeQuestions = new ArrayList<Question>();
		this.bChrono = bChrono;
	}




	/**
	 * +----------+
	 * | GETTEURS |
	 * +----------+
	 */

	public Ressource      getRessource     () { return ressource     ; }
	public List<Notion>   getListeNotions  () { return listeNotions  ; }
	public List<Question> getListeQuestions() { return listeQuestions; }
	public String         getLienEval      () { return lienEval      ; }
	public boolean getChrono() { return this.bChrono;}




	/**
	 * +----------+
	 * | SETTEURS |
	 * +----------+
	 */

	public void setRessource(Ressource ressource) { this.ressource = ressource; }
	public void setLienEval (String    lienEval ) { this.lienEval  = lienEval ; }




	/**
	 * +----------+
	 * | METHODES |
	 * +----------+
	 */

	/**
	 * Ajoute une question à la liste des questions si elle n'est pas nulle.
	 *
	 * @param question La question à ajouter à la liste.
	 *                 Elle ne doit pas être nulle.
	 * @return true si la question a été ajoutée avec succès, false sinon.
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



	/**
	 * Ajoute une notion à la liste des notions si elle n'est pas nulle.
	 *
	 * @param notion La notion à ajouter à la liste.
	 *               Elle ne doit pas être nulle.
	 */
	public void ajouterNotion(Notion notion)
	{
		if (notion != null && this.listeNotions != null)
			this.listeNotions.add(notion);
	}


	public String toString()
	{
		StringBuilder notionsStr = new StringBuilder();
		for (Notion notion : listeNotions)
		{
			notionsStr.append(notion.getNom()).append(", ");
		}
		// Supprimer la dernière virgule et l'espace
		if (notionsStr.length() > 0)
		{
			notionsStr.setLength(notionsStr.length() - 2);
		}
		return "Evaluation{" + "ressource=" + ressource.getNom() + ", notions=[" + notionsStr.toString() + "]"
				+ ", lienEval='" + lienEval + '\'' + '}';
	}

	public void recupererQuestion(Notion notion, int tf, int f, int m, int d)
	{
		List<Question> tmpLst = new ArrayList<Question>();
		List<Question> lstQuestion = new ArrayList<Question>();
		lstQuestion = notion.getListeQuestions();

		Collections.shuffle(lstQuestion);

		for (Question question : lstQuestion)
		{
			if (question.getDifficulte() == 1 && tf>0)
			{
				tmpLst.add(question);
				tf = tf-1;
			}
			if (question.getDifficulte() == 2 && f>0)
			{
				tmpLst.add(question);
				f = f-1;
			}
			if (question.getDifficulte() == 3 && m>0)
			{
				tmpLst.add(question);
				m = m-1;
			}
			if (question.getDifficulte() == 4 && d>0)
			{
				tmpLst.add(question);
				d = d-1;
			}
		}

		for (Question question : tmpLst)
		{
			this.listeQuestions.add(question);
		}

		this.listeNotions.add(notion);
		Collections.shuffle(this.listeQuestions);
	}
}

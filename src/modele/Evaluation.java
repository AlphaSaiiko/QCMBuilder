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

	private boolean        bChrono        ;
	private float          nbPoint        ;
	private int            questionD      ;
	private int            questionF      ;
	private int            questionM      ;
	private int            questionTf     ;
	private List<Notion>   listeNotions   ;
	private List<Question> listeQuestions ;
	private Ressource      ressource      ;
	private String         chemin         ;
	private String         lienEval       ; // Lien de l'évaluation à mettre sur l'intranet




	/**
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */

	public Evaluation(Ressource ressource, boolean bChrono, String chemin)
	{
		this.bChrono        = bChrono                  ;
		this.nbPoint        = 0                        ;
		this.questionD      = 0                        ;
		this.questionF      = 0                        ;
		this.questionM      = 0                        ;
		this.questionTf     = 0                        ;
		this.listeNotions   = new ArrayList<Notion>  ();
		this.listeQuestions = new ArrayList<Question>();
		this.ressource      = ressource                ;
		this.chemin         = chemin                   ;
	}




	/**
	 * +----------+
	 * | GETTEURS |
	 * +----------+
	 */

	public boolean        getChrono            () { return this.bChrono                                                       ; }
	public float          getNbPoints          () { return this.nbPoint                                                       ; }
	public int            getNbQuestion        () { return this.questionTf + this.questionF + this.questionM + this.questionD ; }
	public int            getQuestionDifficile () { return this.questionD                                                     ; }
	public int            getQuestionFacile    () { return this.questionF                                                     ; }
	public int            getQuestionMoyen     () { return this.questionM                                                     ; }
	public int            getQuestionTresFacile() { return this.questionTf                                                    ; }
	public List<Notion>   getListeNotions      () { return this.listeNotions                                                  ; }
	public List<Question> getListeQuestions    () { return this.listeQuestions                                                ; }
	public Ressource      getRessource         () { return this.ressource                                                     ; }
	public String         getChemin            () { return this.chemin                                                        ; }
	public String         getLienEval          () { return this.lienEval                                                      ; }




	/**
	 * +----------+
	 * | SETTEURS |
	 * +----------+
	 */

	public void setRessource(Ressource ressource) { this.ressource = ressource; }
	public void setLienEval (String    lienEval ) { this.lienEval  = lienEval ; }
	public void setChemin(String chemin)		  { this.chemin    = chemin	  ; }




	/**
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



	public String toString()
	{
		StringBuilder stringNotions = new StringBuilder();

		for (Notion notion : listeNotions)
			stringNotions.append(notion.getNom()).append(", ");

		if (stringNotions.length() > 0)
			stringNotions.setLength(stringNotions.length() - 2);
			
		return "Evaluation{"  +
			   "ressource="   + ressource.getNom()       +
			   ", notions=["  + stringNotions.toString() + "]"  +
			   ", lienEval='" + lienEval                 + '\'' + '}';
	}


	
	public void recupererQuestion(Notion notion, int tf, int f, int m, int d)
	{
		List<Question> tmpLst      = new ArrayList<Question>();
		List<Question> lstQuestion = new ArrayList<Question>();

		lstQuestion = notion.getListeQuestions();

		Collections.shuffle(lstQuestion);

		for (Question question : lstQuestion)
		{
			if (question.getDifficulte() == 1 && tf>0)
			{
				tmpLst.add(question);
				tf = tf - 1;
				this.questionTf++;
				this.nbPoint += question.getNbPoints();
			}
			if (question.getDifficulte() == 2 && f>0)
			{
				tmpLst.add(question);
				f = f - 1;
				this.questionF++;
				this.nbPoint += question.getNbPoints();
			}
			if (question.getDifficulte() == 3 && m>0)
			{
				tmpLst.add(question);
				m = m - 1;
				this.questionM++;
				this.nbPoint += question.getNbPoints();
			}
			if (question.getDifficulte() == 4 && d>0)
			{
				tmpLst.add(question);
				d = d - 1;
				this.questionD++;
				this.nbPoint += question.getNbPoints();
			}
		}

		for (Question question : tmpLst)
			this.listeQuestions.add(question);

		this.listeNotions.add(notion);

		Collections.shuffle(this.listeQuestions);
	}
}

package modele;

import java.util.ArrayList;
import java.util.List;

import modele.option.IOption;

public class Question
{
	/*
	 *  +------------+
	 *  | PARAMETRES |
	 *  +------------+
	 */
	private String type    ;
	private String intitule;

	private int nbPoints  ;
	private int temps     ;
	private int difficulte;
	public int numQuestion;

	private final  int id;
	private static int nbQuestion = 0;

	private List<String> complements; // Les pièces jointes ou petites images

	private List<IOption> ensOptions;

	private Notion notion;

	
	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
	public Question(String type, String intitule, int nbPoints, int temps, int difficulte, Notion notion)
	{
		this.type        = type       ;
		this.intitule    = intitule   ;
		this.nbPoints    = nbPoints   ;
		this.temps       = temps      ;
		this.difficulte  = difficulte ;
		this.notion      = notion     ;
		this.numQuestion = notion.getNbQuestion();

		this.id = ++Question.nbQuestion;

		this.complements = new ArrayList<String>();
		this.ensOptions  = new ArrayList<IOption>();

		this.creerFichierQuestion();

	}


	/*
	 *  +----------+
	 *  | GETTEURS |
	 *  +----------+
	 */

	public String getType    () { return type    ; }
	public String getIntitule() { return intitule; }

	public int getNbPoints  () { return nbPoints  ; }
	public int getTemps     () { return temps     ; }
	public int getDifficulte() { return difficulte; }

	public Notion getNotion() { return notion; }

	public int getId       () { return this.id;} 

	public List<String> getComplements() { return complements; }

	public IOption getOptions(int ind) { return ensOptions.get(ind);}

	public List<IOption> getEnsOptions() { return ensOptions; }

	public int getNumQuestion() {return this.numQuestion;}


	/*
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */
	public void setType    (String type    ) {
		this.type     = type    ;
		this.modifierQuestion();
	}
	public void setIntitule(String intitule) { 
		this.intitule = intitule;
		this.modifierQuestion();
	}

	public void setNbPoints  (int nbPoints  ) { 
		this.nbPoints   = nbPoints  ; 
	}
	public void setTemps     (int temps     ) {
		 this.temps      = temps     ;
		 this.modifierQuestion(); 
	}
	public void setDifficulte(int difficulte) {
		 this.difficulte = difficulte; 
		 this.modifierQuestion();
	}

	public void setComplements(ArrayList<String> complements) {
		 this.complements = complements; 
	}

	public void setNotion(Notion notion) { 
		this.notion = notion;
		this.modifierQuestion();
	}



	/*
	 *  +----------+
	 *  | METHODES |
	 *  +----------+
	 */

	public void creerFichierQuestion()
	{
		Fichier tmp = new Fichier("lib/ressources/"+notion.getRessource().getNom()+"/"+notion.getNom()+"/");

		tmp.ajouterFichier("question"+notion.getNbQuestion());
		tmp.ajouterRtf("question"+notion.getNbQuestion()+"/"+"question"+this.getNumQuestion());
		tmp.ecrireQuestion("question"+notion.getNbQuestion()+"/"+"question"+this.getNumQuestion(), this);
	}

	public boolean ajouterOption(IOption opt)
	{
		if (opt == null || this.ensOptions == null) return false;

		this.ensOptions.add(opt);
		return true;
	}


	public boolean supprimerOption(IOption opt)
	{
		return true; // Temporaire
	}

	public void modifierQuestion()
	{
		Fichier tmp = new Fichier("lib/ressources/"+notion.getRessource().getNom()+"/"+notion.getNom()+"/" +"question"+this.numQuestion+"/");
		tmp.modifierQuestion("question"+this.getNumQuestion(), this);
	}
}
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

	private List<String> complements; // Les pièces jointes ou petites images

	
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

		this.complements = new ArrayList<String>();
	}


	/*
	 *  +----------+
	 *  | GETTEURS |
	 *  +----------+
	 */
	private Notion notion;

	public String getType    () { return type    ; }
	public String getIntitule() { return intitule; }

	public int getNbPoints  () { return nbPoints  ; }
	public int getTemps     () { return temps     ; }
	public int getDifficulte() { return difficulte; }

	public List<String> getComplements() { return complements; }

	public Notion getNotion() { return notion; }


	/*
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */
	public void setType    (String type    ) { this.type     = type    ; }
	public void setIntitule(String intitule) { this.intitule = intitule; }

	public void setNbPoints  (int nbPoints  ) { this.nbPoints   = nbPoints  ; }
	public void setTemps     (int temps     ) { this.temps      = temps     ; }
	public void setDifficulte(int difficulte) { this.difficulte = difficulte; }

	public void setComplements(ArrayList<String> complements) { this.complements = complements; }

	public void setNotion(Notion notion) { this.notion = notion; }


	/*
	 *  +----------+
	 *  | METHODES |
	 *  +----------+
	 */
	public boolean ajouterOption(IOption opt)
	{
		return true; // Temporaire
	}


	public boolean supprimerOption(IOption opt)
	{
		return true; // Temporaire
	}
}
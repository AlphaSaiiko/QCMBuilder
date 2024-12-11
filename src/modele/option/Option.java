package modele.option;

import modele.*;

public class Option implements IOption
{
	/*
	 *  +------------+
	 *  | PARAMETRES |
	 *  +------------+
	 */
	private String type    ;
	private String intitule;

	private boolean estReponse;

	private Question question;

	private final int idReponse;
	private static int compteurReponse = 0;

	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
	public Option(String type, String intitule, boolean estReponse, Question question)
	{
		this.type       = type      ;
		this.intitule   = intitule  ;
		this.estReponse = estReponse;
		this.question   = question  ;
		this.idReponse  = ++Option.compteurReponse;

		this.ecrireReponse();
	}


	/*
	 *  +----------+
	 *  | GETTEURS |
	 *  +----------+
	 */
	public String getIntitule() { return this.intitule; }
	public String getType()     { return this.type    ; }

	public boolean getEstReponse() { return this.estReponse; }
	public int getId(){return this.idReponse;}


	/*
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */
	public void setIntitule(String intitule) { 
		this.intitule = intitule;
		this.modifierReponse();
	}
	public void setType    (String type    ) { 
		this.type     = type    ;
		this.modifierReponse();
	}

	public void setEstReponse(boolean estRep) { 
		this.estReponse = estRep;
		this.modifierReponse();
	}

	/*
	 *  +----------+
	 *  | METHODES |
	 *  +----------+
	 */

	 public void modifierReponse()
	 {
		Fichier tmp = new Fichier("lib/ressources/"+question.getNotion().getRessource().getNom()+"/"+question.getNotion().getNom()+"/question"+question.getNumQuestion());
		tmp.modifierReponse("/question"+question.getNumQuestion(), this);
	 }

	 public void ecrireReponse(){
		Fichier tmp = new Fichier("lib/ressources/"+question.getNotion().getRessource().getNom()+"/"+question.getNotion().getNom()+"/question"+question.getNumQuestion());
		tmp.ecrireReponse("/question"+question.getNumQuestion(), this);
	 }
}

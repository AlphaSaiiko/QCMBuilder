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
		this.question   = 	question;

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


	/*
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */
	public void setIntitule(String intitule) { this.intitule = intitule; }
	public void setType    (String type    ) { this.type     = type    ; }

	public void setEstReponse(boolean estRep) { this.estReponse = estRep; }

	/*
	 *  +----------+
	 *  | METHODES |
	 *  +----------+
	 */

	 public void ecrireReponse(){
		Fichier tmp = new Fichier("lib/ressources/"+question.getNotion().getRessource().getNom()+"/"+question.getNotion().getNom()+"/question"+question.getNotion().getNbQuestion());
		tmp.ecrireReponse("/question"+question.getNotion().getNbQuestion(), this);
	 }
}

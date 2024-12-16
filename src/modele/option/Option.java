package modele.option;

import controleur.ControleurFichier;
import modele.*;

public class Option implements IOption
{
	/*
	 *  +------------+
	 *  | PARAMETRES |
	 *  +------------+
	 */
	private String type    ;
	private String enonce;

	private boolean estReponse;

	private Question question;

	private final int idReponse;
	private static int compteurReponse = 0;

	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
	public Option(String type, String enonce, boolean estReponse, Question question)
	{
		this.type       = type      ;
		this.enonce   = enonce  ;
		this.estReponse = estReponse;
		this.question   = question  ;
		this.idReponse  = ++Option.compteurReponse;

		question.ajouterOption(this);

		this.ecrireReponse();
	}


	/*
	 *  +----------+
	 *  | GETTEURS |
	 *  +----------+
	 */
	public String getEnonce() { return this.enonce; }
	public String getType()     { return this.type    ; }

	public boolean getEstReponse() { return this.estReponse; }
	public int getId(){return this.idReponse;}


	/*
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */
	public void setEnonce(String enonce) { 
		this.enonce = enonce;
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
		ControleurFichier tmp = new ControleurFichier("lib/ressources/"+question.getNotion().getRessource().getNom()+"/"+question.getNotion().getNom()+"/question"+question.getNumQuestion());
		tmp.modifierReponse("/question"+question.getNumQuestion(), this);
	 }

	 public void ecrireReponse(){
		ControleurFichier tmp = new ControleurFichier("lib/ressources/"+question.getNotion().getRessource().getNom()+"/"+question.getNotion().getNom()+"/question"+question.getNumQuestion());
		tmp.ecrireReponse("/question"+question.getNumQuestion(), this);
	 }
}

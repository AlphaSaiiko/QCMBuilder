package modele.option;

import controleur.ControleurFichier;
import modele.*;

public class Option implements IOption
{
	/**
	 *  +-----------+
	 *  | ATTRIBUTS |
	 *  +-----------+
	 */

	private        boolean  estReponse         ;
	private static int      compteurReponse = 0;
	private final  int      idReponse          ;
	private        Question question           ;
	private        String   type               ;
	private        String   enonce             ;




	/**
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */

	public Option(String type, String enonce, boolean estReponse, Question question)
	{
		this.estReponse = estReponse              ;
		this.idReponse  = ++Option.compteurReponse;
		this.question   = question                ;
		this.type       = type                    ;
		this.enonce     = enonce                  ;

		question.ajouterOption(this);
	}




	/**
	 *  +----------+
	 *  | GETTEURS |
	 *  +----------+
	 */

	public boolean  getEstReponse() { return this.estReponse; }
	public int      getId        () { return this.idReponse ; }
	public Question getQuestion  () { return this.question  ; }
	public String   getEnonce    () { return this.enonce    ; }
	public String   getType      () { return this.type      ; }




	/**
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */

	public void setEnonce(String enonce)
	{ 
		this.enonce = enonce;
		this.modifierReponse();
	}

	public void setEstReponse(boolean estRep)
	{ 
		this.estReponse = estRep;
		this.modifierReponse();
	}

	public void setType(String type)
	{ 
		this.type = type;
		this.modifierReponse();
	}




	/**
	 *  +----------+
	 *  | METHODES |
	 *  +----------+
	 */

	public void ecrireReponse()
	{
		ControleurFichier controleurFichier = new ControleurFichier(
			"lib/ressources/" + question.getNotion().getRessource().getId()  +
			"_"               + question.getNotion().getRessource().getNom() +
			"/"               + question.getNotion().getNom()                +
			"/question"       + question.getNumQuestion()
		);

		controleurFichier.modifierQuestion("question" + this.question.getNumQuestion(), question);
	}


	
	public void modifierReponse()
	{
		ControleurFichier controleurFichier = new ControleurFichier(
			"lib/ressources/" + question.getNotion().getRessource().getNom() +
			"/"               + question.getNotion().getNom()                +
			"/question"       + question.getNumQuestion()
		);

		controleurFichier.modifierReponse("/question" + question.getNumQuestion(), this);
	}
}

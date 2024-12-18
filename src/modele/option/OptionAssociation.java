package modele.option;

import modele.*;

public class OptionAssociation implements IOption
{
	/*
	 *  +------------+
	 *  | PARAMETRES |
	 *  +------------+
	 */
	private String type    ;
	private String enonce;

	private OptionAssociation associe;

	private Question question;

	private final int idReponse;
	private static int compteurReponse = 0;


	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
	public OptionAssociation(String type, String enonce, Question question)
	{
		this.type       = type      ;
		this.enonce   = enonce  ;
		this.idReponse  = ++OptionAssociation.compteurReponse;
		this.associe = null;
		this.question = question;

		question.ajouterOption(this);
		System.out.println("Option créée : " + this.enonce);
	}


	/*
	 *  +----------+
	 *  | GETTEURS |
	 *  +----------+
	 */
	public String getEnonce() { return this.enonce; }
	public String getType    () { return this.type    ; }

	public OptionAssociation getAssocie() { return this.associe; }

	public int getId() {return this.idReponse;}

	public Question  getQuestion() {return this.question;}


	/*
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */
	public void setEnonce(String enonce) { this.enonce = enonce; }
	public void setType  (String type  ) { this.type   = type  ; }

	public void setAssocie(OptionAssociation associe) {
		this.associe = associe;
		System.out.println("Association créée : " + this.associe.getEnonce());
	}
}
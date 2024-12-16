package modele.option;

import modele.*;

public class OptionElimination implements IOption
{
	/*
	 *  +------------+
	 *  | PARAMETRES |
	 *  +------------+
	 */
	private String type    ;
	private String enonce;

	private boolean estReponse;

	private int ordre;

	private double nbPointsMoins;

	private Question question;

	private final int idReponse;
	private static int compteurReponse = 0;


	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
	public OptionElimination (String type, String enonce, boolean estReponse, int ordre, double nbPointsMoins, Question question)
	{
		this.type          = type         ;
		this.enonce        = enonce       ;
		this.estReponse    = estReponse   ;
		this.ordre         = ordre        ;
		this.nbPointsMoins = nbPointsMoins;
		this.question = question;
		this.idReponse  = ++OptionElimination.compteurReponse;

		question.ajouterOption(this);
	}


	/*
	 *  +----------+
	 *  | GETTEURS |
	 *  +----------+
	 */
	public String getEnonce() { return this.enonce; }
	public String getType    () { return this.type    ; }

	public boolean getEstReponse() { return this.estReponse; }

	public int getOrdre() { return this.ordre; }

	public double getNbPointsMoins() { return this.nbPointsMoins; }
	public Question getQuestion() {return this.question;}

	public int getId(){return this.idReponse;}


	/*
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */
	public void setEnonce(String enonce) { this.enonce = enonce; }
	public void setType    (String type)     { this.type     = type    ; }

	public void setEstReponse(boolean estRep) { this.estReponse = estRep; }

	public void setOrdre(int ordre) { this.ordre = ordre; }

	public void setNbPointsMoins(double nbPoints) { this.nbPointsMoins = nbPoints; }
	public void setQuestion(Question question)	{this.question = question;}
}
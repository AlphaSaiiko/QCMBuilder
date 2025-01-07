package modele.option;

import modele.*;

public class OptionElimination implements IOption
{
	/**
	 *  +-----------+
	 *  | ATTRIBUTS |
	 *  +-----------+
	 */

	private        boolean  estReponse         ;
	private        double   nbPointsMoins      ;
	private        int      ordre              ;
	private final  int      idReponse          ;
	private static int      compteurReponse = 0;
	private        Question question           ;
	private        String   type               ;
	private        String   enonce             ;




	/**
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */

	public OptionElimination (String type, String enonce, boolean estReponse, int ordre, double nbPointsMoins, Question question)
	{
		this.estReponse    = estReponse                         ;
		this.nbPointsMoins = nbPointsMoins                      ;
		this.ordre         = ordre                              ;
		this.idReponse     = ++OptionElimination.compteurReponse;
		this.question      = question                           ;
		this.type          = type                               ;
		this.enonce        = enonce                             ;

		question.ajouterOption(this);
	}




	/**
	 *  +----------+
	 *  | GETTEURS |
	 *  +----------+
	 */

	public boolean  getEstReponse   () { return this.estReponse   ; }
	public double   getNbPointsMoins() { return this.nbPointsMoins; }
	public int      getId           () { return this.idReponse    ; }
	public int      getOrdre        () { return this.ordre        ; }
	public Question getQuestion     () { return this.question     ; }
	public String   getEnonce       () { return this.enonce       ; }
	public String   getType         () { return this.type         ; }




	/**
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */

	public void setEstReponse   (boolean  estRep  ) { this.estReponse    = estRep  ; }
	public void setNbPointsMoins(double   nbPoints) { this.nbPointsMoins = nbPoints; }
	public void setOrdre        (int      ordre   ) { this.ordre         = ordre   ; }
	public void setQuestion     (Question question)	{ this.question      = question; }
	public void setEnonce       (String   enonce  ) { this.enonce        = enonce  ; }
	public void setType         (String   type    ) { this.type          = type    ; }
}
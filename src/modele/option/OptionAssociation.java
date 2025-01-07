package modele.option;

import modele.*;

public class OptionAssociation implements IOption
{
	/**
	 *  +-----------+
	 *  | ATTRIBUTS |
	 *  +-----------+
	 */

	private static int               compteurReponse = 0;
	private final  int               idReponse          ;
	private        OptionAssociation associe            ;
	private        Question          question           ;
	private        String            enonce             ;
	private        String            type               ;




	/**
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */

	public OptionAssociation(String type, String enonce, Question question)
	{
		this.idReponse = ++OptionAssociation.compteurReponse;
		this.associe   = null                               ;
		this.question  = question                           ;
		this.enonce    = enonce                             ;
		this.type      = type                               ;

		question.ajouterOption(this);
	}




	/**
	 *  +----------+
	 *  | GETTEURS |
	 *  +----------+
	 */

	public int               getId      () { return this.idReponse; }
	public OptionAssociation getAssocie () { return this.associe  ; }
	public Question          getQuestion() { return this.question ; }
	public String            getEnonce  () { return this.enonce   ; }
	public String            getType    () { return this.type     ; }




	/**
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */

	public void setAssocie(OptionAssociation associe) { this.associe = associe; }
	public void setEnonce (String            enonce ) { this.enonce  = enonce ; }
	public void setType   (String            type   ) { this.type    = type   ; }
}
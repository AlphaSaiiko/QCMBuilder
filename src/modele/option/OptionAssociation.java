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
	private String intitule;

	private OptionAssociation associe;

	private IOption optionAssocie;

	private Question question;

	private final int idReponse;
	private static int compteurReponse = 0;


	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
	public OptionAssociation(String type, String intitule, Question question)
	{
		this.type       = type      ;
		this.intitule   = intitule  ;
		this.idReponse  = ++OptionAssociation.compteurReponse;
		this.associe = null;
		this.question = question;
	}


	/*
	 *  +----------+
	 *  | GETTEURS |
	 *  +----------+
	 */
	public String getIntitule() { return this.intitule; }
	public String getType    () { return this.type    ; }

	public OptionAssociation getAssocie() { return this.associe; }

	public IOption getOptionAssocie() { return this.optionAssocie; }

	public int getId() {return this.idReponse;}

	public Question  getQuestion() {return this.question;}


	/*
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */
	public void setIntitule(String intitule) { this.intitule = intitule; }
	public void setType    (String type)     { this.type     = type    ; }

	public void setAssocie(OptionAssociation associe) { this.associe = associe; }

	public void setOptionAssocie(IOption opt) { this.optionAssocie = opt; }
}
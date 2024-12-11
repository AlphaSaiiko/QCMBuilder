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

	private boolean estReponse;

	private IOption optionAssocie;

	private Question question;


	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
	public OptionAssociation(String type, String intitule, boolean estReponse, Question question)
	{
		this.type       = type      ;
		this.intitule   = intitule  ;
		this.estReponse = estReponse;
	}


	/*
	 *  +----------+
	 *  | GETTEURS |
	 *  +----------+
	 */
	public String getIntitule() { return this.intitule; }
	public String getType    () { return this.type    ; }

	public boolean getEstReponse() { return this.estReponse; }

	public IOption getOptionAssocie() { return this.optionAssocie; }


	/*
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */
	public void setIntitule(String intitule) { this.intitule = intitule; }
	public void setType    (String type)     { this.type     = type    ; }

	public void setEstReponse(boolean estRep) { this.estReponse = estRep; }

	public void setOptionAssocie(IOption opt) { this.optionAssocie = opt; }
}
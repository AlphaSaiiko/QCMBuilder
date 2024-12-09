package modele.option;

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


	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
	public Option(String type, String intitule, boolean estReponse)
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
}

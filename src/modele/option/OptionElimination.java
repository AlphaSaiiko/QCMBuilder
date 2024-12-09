package modele.option;

public class OptionElimination implements IOption
{
	/*
	 *  +------------+
	 *  | PARAMETRES |
	 *  +------------+
	 */
	private String type    ;
	private String intitule;

	private boolean estReponse;

	private int ordre;

	private double nbPointsMoins;


	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
	public OptionElimination (String type, String intitule, boolean estReponse, int ordre, double nbPointsMoins)
	{
		this.type          = type         ;
		this.intitule      = intitule     ;
		this.estReponse    = estReponse   ;
		this.ordre         = ordre        ;
		this.nbPointsMoins = nbPointsMoins;
	}


	/*
	 *  +----------+
	 *  | GETTEURS |
	 *  +----------+
	 */
	public String getIntitule() { return this.intitule; }
	public String getType    () { return this.type    ; }

	public boolean getEstReponse() { return this.estReponse; }

	public int getOrdre() { return this.ordre; }

	public double getNbPointsMoins() { return this.nbPointsMoins; }


	/*
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */
	public void setIntitule(String intitule) { this.intitule = intitule; }
	public void setType    (String type)     { this.type     = type    ; }

	public void setEstReponse(boolean estRep) { this.estReponse = estRep; }

	public void setOrdre(int ordre) { this.ordre = ordre; }

	public void setNbPointsMoins(double nbPoints) { this.nbPointsMoins = nbPoints; }	
}
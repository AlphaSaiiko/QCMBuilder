package modele.option;

public interface IOption
{
	/*
	 *  +----------+
	 *  | GETTEURS |
	 *  +----------+
	 */ 
	public String getIntitule   ();
	public String getType       ();
	public boolean getEstReponse();

	
	/*
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */ 
	public void setIntitule  (String intitule);
	public void setType      (String type    );
	public void setEstReponse(boolean estRep );
}
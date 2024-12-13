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
	public int getId();

	
	/*
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */ 
	public void setIntitule  (String intitule);
	public void setType      (String type    );

}
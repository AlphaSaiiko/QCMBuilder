package modele.option;

public interface IOption
{
	/*
	 *  +----------+
	 *  | GETTEURS |
	 *  +----------+
	 */ 
	public String getEnonce   ();
	public String getType       ();
	public int getId();

	
	/*
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */ 
	public void setEnonce  (String Enonce);
	public void setType      (String type    );

}
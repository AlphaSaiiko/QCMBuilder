package modele.option;

public interface IOption
{
	/**
	 *  +----------+
	 *  | GETTEURS |
	 *  +----------+
	 */

	public int    getId    ();
	public String getEnonce();
	public String getType  ();



	
	/**
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */

	public void setEnonce (String Enonce);
	public void setType   (String type  );
}
package modele;

import java.util.ArrayList;
import java.util.List;

import controleur.Controleur;

public class Metier 
{
	
	/*
	 * +------------+
	 * | Attributs  |
	 * +------------+
	 */

	private Controleur controleur;

	private static List<Evaluation> listEval;

	private static List<Ressource>  listRessource;


	/*
	 * +--------------+
	 * | Constructeur |
	 * +--------------+
	 */

	public Metier(Controleur controleur)
	{
		this.controleur = controleur;
		Metier.listEval	  = new ArrayList<Evaluation>();
		Metier.listRessource = new ArrayList<Ressource>();
	}


	/*
	 * +---------+
	 * | Getters |
	 * +---------+
	 */


	public Controleur getControleur() 
	{
		return this.controleur;
	}

	public static List<Evaluation> getListEval() 
	{
		return Metier.listEval;
	}

	public static List<Ressource> getListRessource() 
	{
		return Metier.listRessource;
	}




	/*
	 * +---------+
	 * | Setters |
	 * +---------+
	 */
	

	public void setControleur(Controleur controleur) 
	{
		this.controleur = controleur;
	}


	public static void setListEval(List<Evaluation> listEval) 
	{
		Metier.listEval = listEval;
	}


	public static void setListRessource(List<Ressource> listRessource) 
	{
		Metier.listRessource = listRessource;
	}



}
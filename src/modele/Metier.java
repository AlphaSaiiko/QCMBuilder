package modele;

import controleur.Controleur;
import java.util.ArrayList;
import java.util.List;

public class Metier 
{

	/*
	 * +------------+
	 * | ATTRIBUTS  |
	 * +------------+
	 */

	private Controleur controleur;
	private static List<Evaluation> listEval;
	private static List<Ressource> listRessource;


	/*
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */

	public Metier(Controleur controleur)
	{
		this.controleur = controleur;
		Metier.listEval = new ArrayList<>();
		Metier.listRessource = new ArrayList<>();
	}


	/*
	 * +---------+
	 * | GETTERS |
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
	 * | SETTERS |
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


	/*
	 * +------------+
	 * | Méthodes   |
	 * +------------+
	 */


	public static String[] getNomsRessources()
	{
		if (Metier.listRessource.isEmpty())
		{
			return null;
		}
		String[] nomsRessources = new String[Metier.listRessource.size()];
		for (int i = 0; i < Metier.listRessource.size(); i++)
		{
			nomsRessources[i] = Metier.listRessource.get(i).getNom();
		}
		return nomsRessources;
	}

	public static Ressource trouverRessourceParNom(String nom)
	{
		for (Ressource ressource : Metier.listRessource)
		{
			if (ressource.getNom().equalsIgnoreCase(nom))
			{
				return ressource;
			}
		}
		return null;
	}
}

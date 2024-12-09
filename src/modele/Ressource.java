package modele;

import java.util.ArrayList;
import java.util.List;

public class Ressource
{
	/*
	 *  +------------+
	 *  | PARAMETRES |
	 *  +------------+
	 */ 
	private String nom;
	private final int id;

	private static int nbRessource = 0;

	private List<Notion> ensNotions;

	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */ 
	public Ressource(String nom)
	{
		this.nom = nom;
		this.id = ++nbRessource;
		this.ensNotions = new ArrayList<Notion>();
	}


	/*
	 *  +----------+
	 *  | GETTEURS |
	 *  +----------+
	 */ 

	public String getNom              ()        { return this.nom; }

	public int getId                  ()        { return this.id; }

	public static int getNbRessource  ()        { return Ressource.nbRessource; }

	public int getNbNotion            ()        { return this.ensNotions.size(); }

	public List<Notion> getEnsNotions ()        { return this.ensNotions; }

	public Notion getNotion           (int ind) {return this.ensNotions.get(ind);}

	/*
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */ 
	public void setNom(String nom) { this.nom = nom; }



	/*
	 *  +----------+
	 *  | METHODES |
	 *  +----------+
	 */ 
	public void ajouterNotion(Notion notion)
	{
		if (notion != null && this.ensNotions != null)
		{
			this.ensNotions.add(notion);
		}
	}

	public void supprimerNotion(Notion notion)
	{
		for (Notion not : this.ensNotions)
		{
			if (notion.equals(not))
			{
				this.ensNotions.remove(not);
			}
		}
	}

	public void modifierNotion(Notion notion)
	{
		for (Notion not : this.ensNotions)
		{
			if (notion.getId() == not.getId())
			{
				not.setNom(notion.getNom());
			}
		}
	}


}

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

	private List<Notion> ensNotions;

	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */ 
	public Ressource(String nom)
	{
		this.nom = nom;
		this.ensNotions = new ArrayList<Notion>();
	}

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


	/*
	 *  +----------+
	 *  | GETTEURS |
	 *  +----------+
	 */ 
	public String getNom() { return nom; }

	public List<Notion> getEnsNotions() { return ensNotions; }

	/*
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */ 
	public void setNom(String nom) { this.nom = nom; }
}

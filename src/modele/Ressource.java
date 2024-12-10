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

	private static final List<Ressource> listRessource = new ArrayList<>();
	
    private List<Notion> ensNotions = null;
	
		private String nom;
	
	
		/*
		 *  +--------------+
		 *  | CONSTRUCTEUR |
		 *  +--------------+
		 */
	
		public static boolean creerRessource(String nom)
		{
			Ressource ressource = Ressource.trouverRessourceParNom(nom);
			if (ressource == null)
			{
				new Ressource(nom);
				return true;
			}
			return false;
		}
	
		private Ressource(String nom)
		{
			for (Ressource ressource : Ressource.listRessource) 
			{
				if (ressource.getNom().equalsIgnoreCase(nom))
				{
					return;
				}
			}
			this.nom = nom;
			Ressource.listRessource.add(this);
			this.creerFichierRessource();
			this.ensNotions = new ArrayList<>();
    }


	/*
	 *  +----------+
	 *  | GETTEURS |
	 *  +----------+
	 */ 

	public String getNom() { return this.nom; }

	public int getNbNotion() { return this.ensNotions.size(); }

	public List<Notion> getEnsNotions() { return this.ensNotions; }

	public Notion getNotion(int ind) {return this.ensNotions.get(ind);}
    
	public static List<Ressource> getListRessource() { return Ressource.listRessource; }
	
	public String[] getNomsNotions() {
		String[] nomsNotions = new String[this.ensNotions.size()];
		for (int i = 0; i < this.ensNotions.size(); i++) {
			nomsNotions[i] = this.ensNotions.get(i).getNom();
		}
		return nomsNotions;
	}


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

    public void creerFichierRessource()
    {
        Fichier tmp = new Fichier("lib/ressources/");
        tmp.ajouterFichier(this.getNom());
    }

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

	public static String[] getNomsRessources()
	{
		if (Ressource.listRessource.size() < 1)
			return null;

		String[] nomsRessources = new String[Ressource.listRessource.size()];
		for (int i = 0; i < Ressource.listRessource.size(); i++) {
			nomsRessources[i] = Ressource.listRessource.get(i).getNom();
		}
		return nomsRessources;
	}

	public static Ressource trouverRessourceParNom(String nom) 
	{
        List<Ressource> ressources = Ressource.listRessource;
		for (Ressource ressource : ressources)
        {
			if (ressource.getNom().equalsIgnoreCase(nom))
            {
				return ressource;
			}
		}
		return null;
    }

}
package modele;

import java.util.ArrayList;
import java.util.List;

import controleur.Controleur;
import controleur.ControleurFichier;

public class Ressource
{
	/**
	 * +-----------+
	 * | ATTRIBUTS |
	 * +-----------+
	 */

	private ControleurFichier controleurFichier               ;
	private List<Notion>      listeNotions = new ArrayList<>();
	private String            id                              ;
	private String            nom                             ;




	/**
	 * +-------------------------+
	 * | CONSTRUCTEUR ET FACTORY |
	 * +-------------------------+
	 */

	private Ressource(String nom, String id)
	{
		this.id  = id ;
		this.nom = nom;

		Controleur.ajouterRessource(this);
		this.controleurFichier = new ControleurFichier("lib/ressources/");
		this.creerFichierRessource();
		Ressource.mettreAJourRessources();
	}



	public static Ressource creerRessource(String nom, String id)
	{
		Ressource ressource = Metier.trouverRessourceParId(id);

		if (ressource == null)
			ressource = new Ressource(nom, id);

		return ressource;
	}




	/**
	 * +----------+
	 * | GETTEURS |
	 * +----------+
	 */

	public int          getNbNotion  () { return this.listeNotions.size(); }
	public List<Notion> getEnsNotions() { return this.listeNotions       ; }
	public String       getNom       () { return this.nom                ; }
	public String       getId        () { return this.id                 ; }



	public Notion getNotion(String nom)
	{
		for (Notion notion : this.listeNotions)
		{
			if (notion.getNom().equalsIgnoreCase(nom))
				return notion;
		}

		return null;
	}



	public String[] getNomsNotions()
	{
		String[] nomsNotions = new String[this.listeNotions.size()];

		for (int i = 0; i < this.listeNotions.size(); i++)
			nomsNotions[i] = this.listeNotions.get(i).getNom();

		return nomsNotions;
	} 




	/**
	 * +----------+
	 * | SETTEURS |
	 * +----------+
	 */

	public void setId (String id ) { this.id  = id ; Ressource.mettreAJourRessources(); }
	public void setNom(String nom) { this.nom = nom; Ressource.mettreAJourRessources(); }




	/**
	 * +----------+
	 * | METHODES |
	 * +----------+
	 */

	public void creerFichierRessource() { this.controleurFichier.ajouterFichier(this.id + "_" + this.nom); }



	public void ajouterNotion(Notion notion)
	{
		if (notion != null)
		{
			this.listeNotions.add(notion);
			Ressource.mettreAJourRessources();
		}
	}



	public static void mettreAJourRessources()
	{
		List<Ressource> ressources = Controleur.getListRessource();
		ControleurFichier.ecrireRessources(ressources);
	}


	
	public void supprimerNotion(Notion notion)
	{
		this.listeNotions.removeIf(not -> notion.equals(not));
		Ressource.mettreAJourRessources();
	}
}

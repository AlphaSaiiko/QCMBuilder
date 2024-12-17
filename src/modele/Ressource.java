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

	private List<Notion> listeNotions = new ArrayList<>();
	private String nom;
	private String id ;
	private ControleurFichier fichierControleur;

	/**
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */
	public static Ressource creerRessource(String nom, String id)
	{
		Ressource ressource = Metier.trouverRessourceParId(id);
		if (ressource == null)
		{
			ressource = new Ressource(nom, id);
			System.out.println("Nouvelle ressource créée avec le titre: " + nom);
		}
		else
		{
			System.out.println("La ressource existe déjà.");
		}
		return ressource;
	}

	private Ressource(String nom, String id)
	{
		this.nom = nom;
		this.id  = id ;
		Controleur.ajouterRessource(this);
		this.fichierControleur = new ControleurFichier("lib/ressources/");
		this.creerFichierRessource();
		Ressource.mettreAJourRessources();
	}

	/**
	 * +----------+
	 * | GETTEURS |
	 * +----------+
	 */
	public String getNom()
	{
		return this.nom;
	}

	public String getId()
	{
		return this.id;
	}

	public int getNbNotion()
	{
		return this.listeNotions.size();
	}

	public List<Notion> getEnsNotions()
	{
		return this.listeNotions;
	}

	public Notion getNotion(String nom)
	{
		for (Notion notion : this.listeNotions)
		{
			if (notion.getNom().equalsIgnoreCase(nom))
			{
				return notion;
			}
		}
		return null;
	}


	public String[] getNomsNotions()
	{
		String[] nomsNotions = new String[this.listeNotions.size()];
		for (int i = 0; i < this.listeNotions.size(); i++)
		{
			nomsNotions[i] = this.listeNotions.get(i).getNom();
		}
		return nomsNotions;
	} 

	/**
	 * +----------+
	 * | SETTEURS |
	 * +----------+
	 */
	public void setNom(String nom)
	{
		this.nom = nom;
		Ressource.mettreAJourRessources();
	}

	public void setId(String id)
	{
		this.id = id;
		Ressource.mettreAJourRessources();
	}

	/**
	 * +----------+
	 * | METHODES |
	 * +----------+
	 */
	public void creerFichierRessource()
	{
		this.fichierControleur.ajouterFichier(this.id + "_" + this.nom);
	}

	public void ajouterNotion(Notion notion)
	{
		if (notion != null)
		{
			this.listeNotions.add(notion);
			Ressource.mettreAJourRessources();
		}
	}

	public void supprimerNotion(Notion notion)
	{
		this.listeNotions.removeIf(not -> notion.equals(not));
		Ressource.mettreAJourRessources();
	}

	public static void mettreAJourRessources()
	{
		List<Ressource> ressources = Controleur.getListRessource();
		ControleurFichier.ecrireRessources(ressources);
	}

}

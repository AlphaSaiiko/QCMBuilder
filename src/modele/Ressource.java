package modele;

import controleur.Controleur;
import controleur.ControleurFichier;
import controleur.Ecrire;
import java.util.ArrayList;
import java.util.List;

public class Ressource
{
	/*
	 * +------------+
	 * | PARAMETRES |
	 * +------------+
	 */

	
	private List<Notion> ensNotions = new ArrayList<>();
	private String nom;
	private ControleurFichier fichierControleur;

	/*
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */
	public static Ressource creerRessource(String nom)
	{
		Ressource ressource = Metier.trouverRessourceParNom(nom);
		if (ressource == null)
		{
			ressource = new Ressource(nom);
			System.out.println("Nouvelle ressource créée avec le titre: " + nom);
		}
		else
		{
			System.out.println("La ressource existe déjà.");
		}
		return ressource;
	}

	private Ressource(String nom)
	{
		this.nom = nom;
		Controleur.ajouterRessource(this);
		this.fichierControleur = new ControleurFichier("lib/ressources/");
		this.creerFichierRessource();
		Ressource.mettreAJourRessources();
	}

	/*
	 * +---------+
	 * | GETTERS |
	 * +---------+
	 */
	public String getNom()
	{
		return this.nom;
	}

	public int getNbNotion()
	{
		return this.ensNotions.size();
	}

	public List<Notion> getEnsNotions()
	{
		return this.ensNotions;
	}

	public Notion getNotion(String nom)
	{
		for (Notion notion : this.ensNotions)
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
		String[] nomsNotions = new String[this.ensNotions.size()];
		for (int i = 0; i < this.ensNotions.size(); i++)
		{
			nomsNotions[i] = this.ensNotions.get(i).getNom();
		}
		return nomsNotions;
	}

	/*
	 * +---------+
	 * | SETTERS |
	 * +---------+
	 */
	public void setNom(String nom)
	{
		this.nom = nom;
	}

	/*
	 * +----------+
	 * | METHODES |
	 * +----------+
	 */
	public void creerFichierRessource()
	{
		this.fichierControleur.ajouterFichier(this.nom);
	}

	public void ajouterNotion(Notion notion)
	{
		if (notion != null)
		{
			this.ensNotions.add(notion);
			Ressource.mettreAJourRessources();
		}
	}

	public void supprimerNotion(Notion notion)
	{
		this.ensNotions.removeIf(not -> notion.equals(not));
	}

	public static void mettreAJourRessources()
	{
		List<Ressource> ressources = Controleur.getListRessource();
		Ecrire.ecrireRessources(ressources);
	}

}

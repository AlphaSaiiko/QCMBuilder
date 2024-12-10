package modele;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
// import modele.Notion; // This import is unnecessary as it's in the same package

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

	private final List<Notion> ensNotions;

	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */ 
	public Ressource(String nom)
	{
		this.nom = nom;
		this.id = ++nbRessource;
		this.ensNotions = new ArrayList<>();
		this.creerFichierRessource();
	}


	/*
	 *  +----------+
	 *  | GETTEURS |
	 *  +----------+
	 */ 

	public String getNom              ()        
	{ return this.nom; }

	public int getId                  ()        
	{ return this.id; }

	public static int getNbRessource  ()        
	{ return Ressource.nbRessource; }

	public int getNbNotion            ()        
	{ return this.ensNotions.size(); }

	public List<Notion> getEnsNotions ()        
	{ return this.ensNotions; }
	
	public String[] getNomsNotions() {
		String[] nomsNotions = new String[this.ensNotions.size()];
		for (int i = 0; i < this.ensNotions.size(); i++) {
			nomsNotions[i] = this.ensNotions.get(i).getNom();
		}
		return nomsNotions;
	}

	public Notion getNotion           (int ind) 
	{return this.ensNotions.get(ind);}

	/*
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */ 
	public void setNom(String nom) 
	{ this.nom = nom; }



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

	public static String[] getNomsRessources()
	{
		ArrayList<Ressource> ensRessources = getAllRessources();

		if (ensRessources.size() < 1)
			return null;

		String[] nomsRessources = new String[ensRessources.size()];
		for (int i = 0; i < ensRessources.size(); i++) {
			nomsRessources[i] = ensRessources.get(i).getNom();
		}
		return nomsRessources;
	}

	public static ArrayList<Ressource> getAllRessources() {
		ArrayList<Ressource> ensRessources = new ArrayList<>();

		// Le chemin du dossier à parcourir
		String cheminDossier = "../QCMBuilder/lib/ressources";

		// Créer un objet File pour le dossier
		File dossier = new File(cheminDossier);

		// Vérifier si le chemin est bien un dossier
		if (dossier.exists() && dossier.isDirectory()) {
			// Lister tous les fichiers/dossiers dans ce répertoire
			File[] fichiers = dossier.listFiles();

			// Si le répertoire contient des éléments
			if (fichiers != null) {
				for (File fichier : fichiers) {
					// Vérifier si l'élément est un répertoire
					if (fichier.isDirectory()) {
						ensRessources.add(new Ressource(fichier.getName()));
					}
				}
			} else {
				System.out.println("Erreur lors de la lecture du dossier.");
			}
		} else {
			System.out.println("Le chemin spécifié n'est pas un répertoire valide.");
		}

		return ensRessources;
	}

	public static Ressource trouverRessourceParNom(String nom) 
	{
		ArrayList<Ressource> ressources = getAllRessources();
		for (Ressource ressource : ressources) {
			if (ressource.getNom().equalsIgnoreCase(nom)) {
				return ressource;
			}
		}
		return null;
	}
}
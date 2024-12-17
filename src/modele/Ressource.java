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

	private List<Notion>      listeNotions = new ArrayList<>();
	private String            nom                             ;
	private String            id                              ;
	private ControleurFichier controleurFichier               ;




	/**
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */

	private Ressource(String nom, String id)
	{
		this.nom = nom;
		this.id  = id ;

		Controleur.ajouterRessource(this);
		this.controleurFichier = new ControleurFichier("lib/ressources/");
		this.creerFichierRessource();
		Ressource.mettreAJourRessources();
	}



	/**
	 * Crée une nouvelle ressource si elle n'existe pas déjà.
	 * 
	 * Cette méthode vérifie si une ressource avec l'ID donné existe déjà. Si ce n'est pas le cas, elle crée une nouvelle 
	 * ressource avec le nom et l'ID spécifiés.
	 * 
	 * @param nom Le nom de la ressource.
	 * @param id L'identifiant unique de la ressource.
	 * @return La ressource créée ou existante.
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
			System.out.println("La ressource existe déjà.");

		return ressource;
	}




	/**
	 * +----------+
	 * | GETTEURS |
	 * +----------+
	 */

	public String       getNom       () { return this.nom                ; }
	public String       getId        () { return this.id                 ; }
	public int          getNbNotion  () { return this.listeNotions.size(); }
	public List<Notion> getEnsNotions() { return this.listeNotions       ; }

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

	/**
	 * Crée un fichier pour la ressource en utilisant son ID et son nom.
	 * 
	 * Cette méthode utilise le contrôleur de fichiers pour ajouter un fichier dont le nom est composé de l'ID et du nom
	 * de la ressource. Cela permet de créer un fichier unique pour chaque ressource.
	 */
	public void creerFichierRessource()
	{
		this.controleurFichier.ajouterFichier(this.id + "_" + this.nom);
	}



	/**
	 * Ajoute une notion à la liste des notions de la ressource.
	 * 
	 * Si la notion est non nulle, elle est ajoutée à la liste des notions et la ressource est mise à jour.
	 * 
	 * @param notion La notion à ajouter.
	 */
	public void ajouterNotion(Notion notion)
	{
		if (notion != null)
		{
			this.listeNotions.add(notion);
			Ressource.mettreAJourRessources();
		}
	}



	/**
	 * Supprime une notion de la liste des notions de la ressource.
	 * 
	 * Si la notion est présente dans la liste, elle est supprimée, puis la ressource est mise à jour.
	 * 
	 * @param notion La notion à supprimer.
	 */
	public void supprimerNotion(Notion notion)
	{
		this.listeNotions.removeIf(not -> notion.equals(not));
		Ressource.mettreAJourRessources();
	}



	/**
	 * Met à jour les ressources en les réécrivant dans le fichier de stockage.
	 * 
	 * Cette méthode récupère la liste des ressources à l'aide du contrôleur, puis les écrit dans le fichier de stockage
	 * via le contrôleur de fichiers pour s'assurer que les modifications sont persistées.
	 */
	public static void mettreAJourRessources()
	{
		List<Ressource> ressources = Controleur.getListRessource();
		ControleurFichier.ecrireRessources(ressources);
	}
}

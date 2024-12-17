package modele;

import controleur.Controleur;
import java.util.ArrayList;
import java.util.List;

public class Metier 
{

	/**
	 * +-----------+
	 * | ATTRIBUTS |
	 * +-----------+
	 */

	private        Controleur       controleur      ;
	private static List<Evaluation> listeEvaluations;
	private static List<Ressource>  listeRessources ;
	private static List<Notion>     listeNotions    ;
	private static List<Question>   listeQuestions  ;




	/**
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */

	public Metier(Controleur controleur)
	{
		this.controleur         = controleur       ;
		Metier.listeEvaluations = new ArrayList<>();
		Metier.listeRessources  = new ArrayList<>();
		Metier.listeNotions     = new ArrayList<>();
		Metier.listeQuestions   = new ArrayList<>();
	}




	/**
	 * +----------+
	 * | GETTEURS |
	 * +----------+
	 */

	public        Controleur       getControleur      () { return this.controleur        ; }
	public static List<Evaluation> getListeEvaluations() { return Metier.listeEvaluations; }
	public static List<Ressource>  getListeRessources () { return Metier.listeRessources ; }
	public static List<Notion>     getListeNotions    () { return Metier.listeNotions    ; }
	public static List<Question>   getListeQuestions  () { return Metier.listeQuestions  ; }




	/**
	 * +---------+
	 * | SETTEURS |
	 * +---------+
	 */

	public        void setControleur      (Controleur       controleur      ) { this.controleur         = controleur      ; }
	public static void setListeEvaluations(List<Evaluation> listeEvaluations) { Metier.listeEvaluations = listeEvaluations; }
	public static void setListeRessources (List<Ressource>  listeRessources ) { Metier.listeRessources  = listeRessources ; }
	public static void setListeNotions    (List<Notion>     listeNotions    ) { Metier.listeNotions     = listeNotions    ; }
	public static void setListeQuestions  (List<Question>   listeQuestions  ) { Metier.listeQuestions   = listeQuestions  ; }




	/**
	 * +----------+
	 * | METHODES |
	 * +----------+
	 */

	/**
	 * Retourne un tableau contenant les noms des ressources si la liste n'est pas vide.
	 *
	 * @return Un tableau de chaines de caractères contenant les noms des ressources,
	 *         ou null si la liste des ressources est vide.
	 */
	public static String[] getNomsRessources()
	{
		if (Metier.listeRessources.isEmpty())
			return null;

		String[] nomsRessources = new String[Metier.listeRessources.size()];

		for (int i = 0; i < Metier.listeRessources.size(); i++)
			nomsRessources[i] = Metier.listeRessources.get(i).getNom();

		return nomsRessources;
	}

	public static String[] getIDsNomsRessources()
	{
		if (Metier.listeRessources.isEmpty())
			return null;

		String[] idsNomsRessources = new String[Metier.listeRessources.size()];

		for (int i = 0; i < Metier.listeRessources.size(); i++)
			idsNomsRessources[i] = Metier.listeRessources.get(i).getId() + "_" + Metier.listeRessources.get(i).getNom();

		return idsNomsRessources;
	}

	/**
	 * Recherche une ressource par son nom dans la liste des ressources.
	 *
	 * @param nom Le nom de la ressource à rechercher. La comparaison n'est pas sensible à la casse.
	 * @return La ressource correspondante si elle est trouvée, sinon null.
	 */
	public static Ressource trouverRessourceParId(String id)
	{
		for (Ressource ressource : Metier.listeRessources)
		{
			if (ressource.getId().equalsIgnoreCase(id))
			{
				return ressource;
			}
		}
		return null;
	}



	/**
	 * Retourne un tableau contenant les noms des notions si la liste n'est pas vide.
	 *
	 * @return Un tableau de chaines de caractères contenant les noms des notions,
	 *         ou null si la liste des notions est vide.
	 */
	public static String[] getNomsNotions()
	{
		if (Metier.listeNotions.isEmpty())
			return null;

		String[] nomsNotions = new String[Metier.listeNotions.size()];

		for (int i = 0; i < Metier.listeNotions.size(); i++)
			nomsNotions[i] = Metier.listeNotions.get(i).getNom();

		return nomsNotions;
	}



	/**
	 * Recherche une notion par son nom dans la liste des notions.
	 *
	 * @param nom Le nom de la notion à rechercher. La comparaison n'est pas sensible à la casse.
	 * @return La notion correspondante si elle est trouvée, sinon null.
	 */
	public static Notion trouverNotionParNom(String nom)
	{
		for (Notion notion : Metier.listeNotions)
		{
			if (notion.getNom().equalsIgnoreCase(nom))
				return notion;
		}

		return null;
	}



	/**
	 * Ajoute un élément (ressource, notion ou question) à la liste correspondante.
	 *
	 * @param element L'élément à ajouter. Il doit être non nul.
	 *                Exemple : une instance de Ressource, Notion ou Question.
	 */
	public static void ajouterRessource(Ressource ressource) { Metier.listeRessources.add(ressource); }
	public static void ajouterNotion   (Notion    notion   ) { Metier.listeNotions.add   (notion)   ; }
	public static void ajouterQuestion (Question  question ) { Metier.listeQuestions.add (question) ; }



	/**
	 * Supprime un élément (ressource, notion ou question) de la liste correspondante.
	 *
	 * @param element L'élément à supprimer. Il doit être présent dans la liste.
	 *                Exemple : une instance de Ressource, Notion ou Question.
	 */
	public static void supprimerRessource(Ressource ressource) { Metier.listeRessources.remove(ressource); }
	public static void supprimerNotion   (Notion    notion   ) { Metier.listeNotions.remove   (notion)   ; }
	public static void supprimerQuestion (Question  question ) { Metier.listeQuestions.remove (question) ; }
}

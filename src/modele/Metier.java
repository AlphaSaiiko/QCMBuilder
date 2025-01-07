package modele;

import java.util.ArrayList;
import java.util.List;

import controleur.Controleur;

public class Metier 
{
	/**
	 * +-----------+
	 * | ATTRIBUTS |
	 * +-----------+
	 */

	private        Controleur       controleur      ;
	private static List<Evaluation> listeEvaluations;
	private static List<Notion>     listeNotions    ;
	private static List<Question>   listeQuestions  ;
	private static List<Ressource>  listeRessources ;




	/**
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */

	public Metier(Controleur controleur)
	{
		this.controleur         = controleur       ;
		Metier.listeEvaluations = new ArrayList<>();
		Metier.listeNotions     = new ArrayList<>();
		Metier.listeQuestions   = new ArrayList<>();
		Metier.listeRessources  = new ArrayList<>();
	}




	/**
	 * +----------+
	 * | GETTEURS |
	 * +----------+
	 */

	public        Controleur       getControleur      () { return this.controleur        ; }
	public static List<Evaluation> getListeEvaluations() { return Metier.listeEvaluations; }
	public static List<Notion>     getListeNotions    () { return Metier.listeNotions    ; }
	public static List<Question>   getListeQuestions  () { return Metier.listeQuestions  ; }
	public static List<Ressource>  getListeRessources () { return Metier.listeRessources ; }



	public static String[] getNomsNotions()
	{
		if (Metier.listeNotions.isEmpty())
			return null;

		String[] nomsNotions = new String[Metier.listeNotions.size()];

		for (int i = 0; i < Metier.listeNotions.size(); i++)
			nomsNotions[i] = Metier.listeNotions.get(i).getNom();

		return nomsNotions;
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



	public static String[] getNomsRessources()
	{
		if (Metier.listeRessources.isEmpty())
			return null;

		String[] nomsRessources = new String[Metier.listeRessources.size()];

		for (int i = 0; i < Metier.listeRessources.size(); i++)
			nomsRessources[i] = Metier.listeRessources.get(i).getNom();

		return nomsRessources;
	}




	/**
	 * +----------+
	 * | SETTEURS |
	 * +----------+
	 */

	public        void setControleur      (Controleur       controleur      ) { this.controleur         = controleur      ; }
	public static void setListeEvaluations(List<Evaluation> listeEvaluations) { Metier.listeEvaluations = listeEvaluations; }
	public static void setListeNotions    (List<Notion>     listeNotions    ) { Metier.listeNotions     = listeNotions    ; }
	public static void setListeQuestions  (List<Question>   listeQuestions  ) { Metier.listeQuestions   = listeQuestions  ; }
	public static void setListeRessources (List<Ressource>  listeRessources ) { Metier.listeRessources  = listeRessources ; }




	/**
	 * +----------+
	 * | METHODES |
	 * +----------+
	 */

	public static void ajouterRessource(Ressource ressource) { Metier.listeRessources.add(ressource); }
	public static void ajouterNotion   (Notion    notion   ) { Metier.listeNotions   .add(notion)   ; }
	public static void ajouterQuestion (Question  question ) { Metier.listeQuestions .add(question) ; }



	public static void supprimerRessource(Ressource ressource) {                                                   Metier.listeRessources.remove(ressource); }
	public static void supprimerNotion   (Notion    notion   ) {                                                   Metier.listeNotions   .remove(notion)   ; }
	public static void supprimerQuestion (Question  question ) { question.getNotion().supprimerQuestion(question); Metier.listeQuestions .remove(question) ; }
	


	public static void retirerNotion (Ressource ressource, Notion notion) { ressource.supprimerNotion(notion); }



	public static Notion trouverNotionParNom(String nom)
	{
		for (Notion notion : Metier.listeNotions)
		{
			if (notion.getNom().equalsIgnoreCase(nom))
				return notion;
		}

		return null;
	}



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
}

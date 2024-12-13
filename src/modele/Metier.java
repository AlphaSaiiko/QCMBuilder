package modele;

import controleur.Controleur;
import java.util.ArrayList;
import java.util.List;

public class Metier 
{

	/*
	 * +------------+
	 * | ATTRIBUTS  |
	 * +------------+
	 */

	private Controleur controleur;
	private static List<Evaluation> listEval;
	private static List<Ressource> listRessource;
	private static List<Notion> listNotion;
	private static List<Question> listQuestion;


	/*
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */

	public Metier(Controleur controleur)
	{
		this.controleur = controleur;
		Metier.listEval = new ArrayList<>();
		Metier.listRessource = new ArrayList<>();
		Metier.listNotion = new ArrayList<>();
		Metier.listQuestion = new ArrayList<>();
	}


	/*
	 * +---------+
	 * | GETTERS |
	 * +---------+
	 */

	public Controleur getControleur() 
	{
		return this.controleur;
	}

	public static List<Evaluation> getListEval() 
	{
		return Metier.listEval;
	}

	public static List<Ressource> getListRessource() 
	{
		return Metier.listRessource;
	}

	public static List<Notion> getListNotion() 
	{
		return Metier.listNotion;
	}

	public static List<Question> getListQuestion() 
	{
		return Metier.listQuestion;
	}


	/*
	 * +---------+
	 * | SETTERS |
	 * +---------+
	 */

	public void setControleur(Controleur controleur) 
	{
		this.controleur = controleur;
	}

	public static void setListEval(List<Evaluation> listEval) 
	{
		Metier.listEval = listEval;
	}

	public static void setListRessource(List<Ressource> listRessource) 
	{
		Metier.listRessource = listRessource;
	}

	public static void setListNotion(List<Notion> listNotion) 
	{
		Metier.listNotion = listNotion;
	}

	public static void setListQuestion(List<Question> listQuestion) 
	{
		Metier.listQuestion = listQuestion;
	}


	/*
	 * +------------+
	 * | Méthodes   |
	 * +------------+
	 */


	public static String[] getNomsRessources()
	{
		if (Metier.listRessource.isEmpty())
		{
			return null;
		}
		String[] nomsRessources = new String[Metier.listRessource.size()];
		for (int i = 0; i < Metier.listRessource.size(); i++)
		{
			nomsRessources[i] = Metier.listRessource.get(i).getNom();
		}
		return nomsRessources;
	}

	public static Ressource trouverRessourceParNom(String nom)
	{
		for (Ressource ressource : Metier.listRessource)
		{
			if (ressource.getNom().equalsIgnoreCase(nom))
			{
				return ressource;
			}
		}
		return null;
	}

	public static void ajouterRessource(Ressource ressource)
	{
		Metier.listRessource.add(ressource);
	}

	public static String[] getNomsNotions()
	{
		if (Metier.listNotion.isEmpty())
		{
			return null;
		}
		String[] nomsNotions = new String[Metier.listNotion.size()];
		for (int i = 0; i < Metier.listNotion.size(); i++)
		{
			nomsNotions[i] = Metier.listNotion.get(i).getNom();
		}
		return nomsNotions;
	}

	public static Notion trouverNotionParNom(String nom)
	{
		for (Notion notion : Metier.listNotion)
		{
			if (notion.getNom().equalsIgnoreCase(nom))
			{
				return notion;
			}
		}
		return null;
	}

	public static void ajouterNotion(Notion notion)
	{
		Metier.listNotion.add(notion);
	}

	public static void ajouterQuestion(Question question)
	{
		Metier.listQuestion.add(question);
	}
}

package modele;

import java.util.ArrayList;
import java.util.List;

import modele.Fichier;
import modele.Question;
import modele.Ressource;

public class Notion
{
	/*
	 *  +------------+
	 *  | PARAMETRES |
	 *  +------------+
	 */ 

	private List<Question> ensQuestions;

	private String nom;

	private Ressource ressource;

	
	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */

    public static Notion creerNotion(String nom, Ressource ressource)
	{
		Notion notion = Notion.trouverNotionParNom(nom, ressource);
		if (notion == null)
		{
			notion = new Notion(nom, ressource);
			System.out.println("Nouvelle notion créée avec le titre: " + nom + " pour la ressource: " + ressource.getNom());
		}
		else
		{
			System.out.println("La notion existe déjà dans la ressource: " + ressource.getNom());
		}
		return notion;
	}

	public Notion(String nom, Ressource ressource)
	{
		this.nom = nom;
		this.ressource = ressource;
		this.creerFichierNotion();
		this.ensQuestions = new ArrayList<Question>();
		ressource.ajouterNotion(this);
	}


	/*
	 *  +----------+
	 *  | GETTEURS |
	 *  +----------+
	 */ 

	public String getNom() { return nom; }
    
	public Ressource getRessource() {return this.ressource;}

    public int getNbQuestions() { return this.ensQuestions.size(); }

	public List<Question> getEnsQuestions() { return this.ensQuestions; }

	public Question getQuestion (int indice) { return this.ensQuestions.get(indice);}


	/*
	 *  +----------+
	 *  | SETTEURS |
	 *  +----------+
	 */ 

	public void setNom(String nom) { this.nom = nom; }

    public void setRessource(Ressource ressource) { this.ressource = ressource; }


	/*
	 *  +----------+
	 *  | METHODES |
	 *  +----------+
	 */

	public void creerFichierNotion()
	{
		Fichier tmp = new Fichier("lib/ressources/"+ressource.getNom()+"/");
		tmp.ajouterFichier(this.getNom());
	}
	
	public void ajouterQuestion(Question question)
	{
		if (question != null)
		{
			this.ensQuestions.add(question);
		}
	}

	public void supprimerQuestion(Question question)
	{
		for (Question qst : this.ensQuestions)
		{
			if (question.equals(qst))
			{
				this.ensQuestions.remove(qst);
			}
		}
	}

    public static Notion trouverNotionParNom(String nom, Ressource ressource)
    {
        for (Notion notion : ressource.getEnsNotions())
        {
            if (notion.getNom().equals(nom))
            {
                return notion;
            }
        }
        return null;
    }
}
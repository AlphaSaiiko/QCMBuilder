package modele;

import controleur.Controleur;
import controleur.ControleurFichier;
import java.util.ArrayList;
import java.util.List;
import modele.option.IOption;

public class Question
{
	/*
	 * +-----------+
	 * | ATTRIBUTS |
	 * +-----------+
	 */
	private String type;
	private String enonce;
	private int nbPoints;
	private int temps;
	private int difficulte;
	public int numQuestion;
	private List<String> complements; // Les pièces jointes ou petites images
	private List<IOption> ensOptions;
	private Notion notion;

	/*
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */
	public static Question creerQuestion(int nbPoints, int temps, Notion notion, int difficulte, String type)
	{
		Question question = new Question(nbPoints, temps, notion, difficulte, type);
		if (question != null)
		{
			System.out.println("La question a été créée avec succès.");
		}
		else
		{
			System.out.println("La question n'a pas pu être créée.");
		}
		return question;
	}

	private Question(int nbPoints, int temps, Notion notion, int difficulte, String type)
	{
		this.nbPoints = nbPoints;
		this.temps = temps;
		this.notion = notion;
		this.difficulte = difficulte;
		this.type = type;
		this.numQuestion = notion.getNbQuestion();
		Question.mettreAJourQuestions();
		this.complements = new ArrayList<>();
		this.ensOptions = new ArrayList<>();
		this.creerFichierQuestion();
		notion.ajouterQuestion(this);
	}

	/*
	 * +---------+
	 * | GETTERS |
	 * +---------+
	 */
	public String getType()
	{
		return type;
	}

	public String getEnonce()
	{
		return enonce;
	}

	public int getNbPoints()
	{
		return nbPoints;
	}

	public int getTemps()
	{
		return temps;
	}

	public int getDifficulte()
	{
		return difficulte;
	}

	public Notion getNotion()
	{
		return notion;
	}

	public List<String> getComplements()
	{
		return complements;
	}

	public IOption getOptions(int ind)
	{
		return ensOptions.get(ind);
	}

	public List<IOption> getEnsOptions()
	{
		return ensOptions;
	}

	public int getNumQuestion()
	{
		return this.numQuestion;
	}

	/*
	 * +---------+
	 * | SETTERS |
	 * +---------+
	 */
	public void setType(String type)
	{
		this.type = type;
		this.modifierQuestion();
	}

	public void setEnonce(String enonce)
	{
		this.enonce = enonce;
		this.modifierQuestion();
	}

	public void setNbPoints(int nbPoints)
	{
		this.nbPoints = nbPoints;
	}

	public void setTemps(int temps)
	{
		this.temps = temps;
		this.modifierQuestion();
	}

	public void setDifficulte(int difficulte)
	{
		this.difficulte = difficulte;
		this.modifierQuestion();
	}

	public void setComplements(ArrayList<String> complements)
	{
		this.complements = complements;
	}

	public void setNotion(Notion notion)
	{
		this.notion = notion;
		this.modifierQuestion();
	}

	/*
	 * +----------+
	 * | METHODES |
	 * +----------+
	 */
	public void creerFichierQuestion()
	{
		ControleurFichier fichierControleur = new ControleurFichier("lib/ressources/" + notion.getRessource().getNom() + "/" + notion.getNom() + "/");
		fichierControleur.ajouterFichier("question" + notion.getNbQuestion());
		fichierControleur.ajouterRtf("question" + notion.getNbQuestion() + "/question" + this.getNumQuestion());
		fichierControleur.ecrireQuestion("question" + notion.getNbQuestion() + "/question" + this.getNumQuestion(), this);
	}

	public boolean ajouterOption(IOption opt)
	{
		if (opt == null) return false;
		this.ensOptions.add(opt);
		Question.mettreAJourQuestions();
		return true;
	}

	public boolean supprimerOption(IOption opt)
	{
		Question.mettreAJourQuestions();
		return true; // Temporaire
	}

	public void modifierQuestion()
	{
		ControleurFichier fichierControleur = new ControleurFichier("lib/ressources/" + notion.getRessource().getNom() + "/" + notion.getNom() + "/question" + this.numQuestion + "/");
		fichierControleur.modifierQuestion("question" + this.getNumQuestion(), this);
		Question.mettreAJourQuestions();
	}

	public static void mettreAJourQuestions()
	{
		List<Question> questions = Controleur.getListQuestion();
		ControleurFichier.ecrireQuestions(questions);
	}
}

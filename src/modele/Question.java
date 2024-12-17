package modele;

import controleur.Controleur;
import controleur.ControleurFichier;
import java.util.ArrayList;
import java.util.List;
import modele.option.IOption;

public class Question
{
	/**
	 * +-----------+
	 * | ATTRIBUTS |
	 * +-----------+
	 */

	private String        type            ;
	private String        enonce          ;
	private int           nbPoints        ;
	private int           temps           ;
	private int           difficulte      ;
	private int           numQuestion     ;
	private List<String>  listeComplements; // Les pièces jointes ou petites images
	private List<IOption> listeOptions    ;
	private Notion        notion          ;




	/**
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */

	private Question(int nbPoints, int temps, Notion notion, int difficulte, String type)
	{
		this.nbPoints   = nbPoints  ;
		this.temps      = temps     ;
		this.notion     = notion    ;
		this.difficulte = difficulte;
		this.type       = type      ;

		this.numQuestion = notion.getNbQuestion();
		Question.mettreAJourQuestions();
		this.listeComplements = new ArrayList<>();
		this.listeOptions = new ArrayList<>();
		this.creerFichierQuestion();
		notion.ajouterQuestion(this);
	}



	/**
	 * Crée une nouvelle question avec les paramètres spécifiés.
	 * 
	 * @param nbPoints Le nombre de points attribués à la question.
	 * @param temps Le temps limite pour répondre à la question (en secondes).
	 * @param notion La notion associée à la question.
	 * @param difficulte Le niveau de difficulté de la question.
	 * @param type Le type de la question.
	 * @return La question créée.
	 */
	public static Question creerQuestion(int nbPoints, int temps, Notion notion, int difficulte, String type)
	{
		Question question = new Question(nbPoints, temps, notion, difficulte, type);

		if (question != null)
			System.out.println("La question a été créée avec succès.");

		return question;
	}




	/**
	 * +----------+
	 * | GETTEURS |
	 * +----------+
	 */

	public String        getType       ()        { return type                 ; }
	public String        getEnonce     ()        { return enonce               ; }
	public int           getNbPoints   ()        { return nbPoints             ; }
	public int           getTemps      ()        { return temps                ; }
	public int           getDifficulte ()        { return difficulte           ; }
	public Notion        getNotion     ()        { return notion               ; }
	public List<String>  getComplements()        { return listeComplements     ; }
	public IOption       getOptions    (int ind) { return listeOptions.get(ind); }
	public List<IOption> getEnsOptions ()        { return listeOptions         ; }
	public int           getNumQuestion()        { return this.numQuestion     ; }




	/**
	 * +----------+
	 * | SETTEURS |
	 * +----------+
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
		this.listeComplements = complements;
	}

	public void setNotion(Notion notion)
	{
		this.notion = notion;
		this.modifierQuestion();
	}




	/**
	 * +----------+
	 * | METHODES |
	 * +----------+
	 */

	/**
	 * Crée et enregistre un fichier de question pour la notion courante.
	 * 
	 * Cette méthode utilise le contrôleur de fichiers pour générer un répertoire spécifique à la notion et y ajouter
	 * un fichier pour la question en cours. Elle crée également un fichier RTF et enregistre les informations de la question.
	 */
	public void creerFichierQuestion()
	{
		ControleurFichier fichierControleur = new ControleurFichier("lib/ressources/" + notion.getRessource().getId() + "_" + notion.getRessource().getNom() + "/" + notion.getNom() + "/");
		fichierControleur.ajouterFichier("question" + this.numQuestion);
		fichierControleur.ajouterRtf("question" + this.numQuestion + "/question" + this.numQuestion);
	}



	/**
	 * Ajoute une option à la question.
	 * 
	 * Si l'option est valide (non nulle), elle est ajoutée à la liste des options de la question et la liste des
	 * questions est mise à jour. Si l'option est nulle, l'ajout échoue.
	 * 
	 * @param opt L'option à ajouter.
	 * @return {@code true} si l'option a été ajoutée avec succès, {@code false} si l'option est nulle.
	 */
	public boolean ajouterOption(IOption opt)
	{
		if (opt == null)
			return false;

		this.listeOptions.add(opt);
		Question.mettreAJourQuestions();
		return true;
	}



	/**
	 * Supprime une option de la question en mettant à jour la liste des questions.
	 * 
	 * @param opt L'option à supprimer.
	 * @return Toujours {@code true}.
	 */
	public boolean supprimerOption(IOption opt)
	{
		Question.mettreAJourQuestions();
		return true;
	}



	/**
	 * Modifie les informations d'une question existante et met à jour le fichier correspondant.
	 * 
	 * Cette méthode modifie la question en utilisant le contrôleur de fichiers pour mettre à jour le fichier de la question
	 * dans le répertoire correspondant. Après cela, la liste des questions est mise à jour.
	 */
	public void modifierQuestion()
	{
		ControleurFichier fichierControleur = new ControleurFichier("lib/ressources/" + notion.getRessource().getNom() + "/" + notion.getNom() + "/question" + this.numQuestion + "/");
		fichierControleur.modifierQuestion("question" + this.getNumQuestion(), this);
		Question.mettreAJourQuestions();
	}



	/**
	 * Met à jour les questions en les réécrivant dans le fichier de stockage.
	 * 
	 * Cette méthode récupère la liste des questions à l'aide du contrôleur, puis les écrit dans le fichier de stockage
	 * via le contrôleur de fichiers pour s'assurer que les modifications sont persistées.
	 */
	public static void mettreAJourQuestions()
	{
		List<Question> questions = Controleur.getListQuestion();
		ControleurFichier.ecrireQuestions(questions);
	}
}

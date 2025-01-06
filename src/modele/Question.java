package modele;

import controleur.Controleur;
import controleur.ControleurFichier;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import modele.option.IOption;
import modele.option.Option;
import modele.option.OptionAssociation;
import modele.option.OptionElimination;

public class Question
{
	/**
	 * +-----------+
	 * | ATTRIBUTS |
	 * +-----------+
	 */

	private String        type            ;
	private String        enonce          ;
	private String 	      feedback        ;
	private int           nbPoints        ;
	private int           temps           ;
	private int           difficulte      ;
	private int           numQuestion     ;
	private List<String>  listeComplements; // Les pièces jointes ou petites images
	private List<IOption> listeOptions    ;
	private Notion        notion          ;
	private final int idQuestion;
	private static int compteurQuestion = 0;




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

		this.idQuestion  = ++Question.compteurQuestion;
		this.feedback = "";

		this.numQuestion = (notion.getNbQuestion() + 1);
		this.listeComplements = new ArrayList<>();
		this.listeOptions = new ArrayList<>();
		this.creerFichierQuestion();
		notion.ajouterQuestion(this);
		Controleur.ajouterQuestion(this);
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
	public String        getFeedback   ()        { return feedback             ; }
	public int           getNbPoints   ()        { return nbPoints             ; }
	public int           getTemps      ()        { return temps                ; }
	public int           getDifficulte ()        { return difficulte           ; }
	public Notion        getNotion     ()        { return notion               ; }
	public List<String>  getComplements()        { return listeComplements     ; }
	public IOption       getOptions    (int ind) { return listeOptions.get(ind); }
	public List<IOption> getEnsOptions ()        { return listeOptions         ; }
	public int           getNumQuestion()        { return this.idQuestion      ; }




	/**
	 * +----------+
	 * | SETTEURS |
	 * +----------+
	 */

	public void setType(String type)
	{
		this.type = type;
		this.mettreAJourQuestions();
	}

	public void setEnonce(String enonce)
	{
		this.enonce = enonce;
		this.mettreAJourQuestions();
	}

	public void setFeedback(String feedback)
	{
		this.feedback = feedback;
		this.mettreAJourQuestions();
	}

	public void setNbPoints(int nbPoints)
	{
		this.nbPoints = nbPoints;
	}

	public void setTemps(int temps)
	{
		this.temps = temps;
		this.mettreAJourQuestions();
	}

	public void setDifficulte(int difficulte)
	{
		this.difficulte = difficulte;
		this.mettreAJourQuestions();
	}

	public void setComplements(ArrayList<String> complements)
	{
		this.listeComplements = complements;
	}

	public void setNotion(Notion notion)
	{
		this.notion = notion;
		this.mettreAJourQuestions();
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
		String chemin = "lib/ressources/" + notion.getRessource().getId() + "_" + notion.getRessource().getNom() + "/" + notion.getNom() + "/question" + this.numQuestion + "/question" + this.numQuestion + ".rtf";
		File fichier = new File(chemin);

		// Vérifiez si le fichier existe déjà
		if (!fichier.exists()) 
		{
			ControleurFichier fichierControleur = new ControleurFichier("lib/ressources/" + notion.getRessource().getId() + "_" + notion.getRessource().getNom() + "/" + notion.getNom() + "/question" + this.numQuestion);
			fichierControleur.ajouterFichier("");
			fichierControleur.ajouterRtf("/question" + this.numQuestion + ".rtf");
			fichierControleur.ecrireQuestion("/question" + this.numQuestion + ".rtf", this);
			System.out.println("Fichier créé : " + chemin);
		} 
		else 
		{
			System.out.println("Le fichier existe déjà : " + chemin);
		}
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
		if (opt == null) {
			return false;
		}

		// Vérification de la validité de l'option en comparant les attributs
		for (IOption optionExistante : this.listeOptions) {
			if (optionsSontEgales(optionExistante, opt)) {
				return false;
			}
		}


		this.listeOptions.add(opt);
		this.mettreAJourQuestions();
		return true;
	}

	// Méthode pour comparer deux options en fonction de leurs attributs
	private boolean optionsSontEgales(IOption opt1, IOption opt2) {
		if (opt1.getType().equals(opt2.getType()) &&
			opt1.getEnonce().equals(opt2.getEnonce()))
			{

			if (opt1 instanceof OptionAssociation && opt2 instanceof OptionAssociation && ((OptionAssociation) opt1).getAssocie() != null && ((OptionAssociation) opt2).getAssocie() != null) {
				return ((OptionAssociation) opt1).getAssocie().equals(((OptionAssociation) opt2).getAssocie());
			}
			else if (opt1 instanceof OptionElimination && opt2 instanceof OptionElimination) {
				OptionElimination optionE1 = (OptionElimination) opt1;
				OptionElimination optionE2 = (OptionElimination) opt2;
				return optionE1.getEstReponse() == optionE2.getEstReponse() &&
					optionE1.getOrdre() == optionE2.getOrdre() &&
					optionE1.getNbPointsMoins() == optionE2.getNbPointsMoins();
			}
			else if (opt1 instanceof Option && opt2 instanceof Option) {
				return ((Option) opt1).getEstReponse() == ((Option) opt2).getEstReponse();
			}
			return true;
		}
		return false;
	}




	/**
	 * Supprime une option de la question en mettant à jour la liste des questions.
	 * 
	 * @param opt L'option à supprimer.
	 * @return Toujours {@code true}.
	 */
	public boolean supprimerOption(IOption opt)
	{
		this.listeOptions.remove(opt);
		this.mettreAJourQuestions();
		return true;
	}


	/**
	 * Met à jour les questions en les réécrivant dans le fichier de stockage.
	 * 
	 * Cette méthode récupère la liste des questions à l'aide du contrôleur, puis les écrit dans le fichier de stockage
	 * via le contrôleur de fichiers pour s'assurer que les modifications sont persistées.
	 */
	public void mettreAJourQuestions()
	{
		String emplacement = "lib/ressources/" + notion.getRessource().getId() + "_" + notion.getRessource().getNom() + "/" + notion.getNom() + "/question" + this.numQuestion;
		File fichier = new File(emplacement);
	

		// Vérifiez si le fichier existe avant de tenter de le modifier
		if (fichier.exists())
		{
			ControleurFichier controleurFichier = new ControleurFichier("lib/ressources/" + notion.getRessource().getId() + "_" + notion.getRessource().getNom() + "/" + notion.getNom() + "/");
			controleurFichier.modifierQuestion("question" + this.numQuestion + "/question" + this.numQuestion, this);
		}
		else
			System.err.println("Erreur : Le fichier n'existe pas et ne peut donc pas être modifié.");
	}



	public boolean ajouterComplement(String emplacement) {
		if (this.listeComplements.contains(emplacement))
			return false;
		
		return this.listeComplements.add(emplacement);
	}
}
	
package modele;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import controleur.Controleur;
import controleur.ControleurFichier;
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

	private static int           compteurQuestion = 0;
	private        int           difficulte          ;
	private final  int           idQuestion          ;
	private        int           nbPoints            ;
	private        int           numQuestion         ;
	private        int           temps               ;
	private        List<String>  listeComplements    ;
	private        List<IOption> listeOptions        ;
	private        Notion        notion              ;
	private        String        type                ;
	private        String        enonce              ;
	private        String        feedback            ;




	/**
	 * +-------------------------+
	 * | CONSTRUCTEUR ET FACTORY |
	 * +-------------------------+
	 */

	private Question(int nbPoints, int temps, Notion notion, int difficulte, String type)
	{
		this.difficulte       = difficulte                  ;
		this.idQuestion       = ++Question.compteurQuestion ;
		this.nbPoints         = nbPoints                    ;
		this.numQuestion      = (notion.getNbQuestion() + 1);
		this.temps            = temps                       ;
		this.listeComplements = new ArrayList<>()           ;
		this.listeOptions     = new ArrayList<>()           ;
		this.notion           = notion                      ;
		this.type             = type                        ;
		this.feedback         = ""                          ;

		this.creerFichierQuestion();
		notion.ajouterQuestion(this);
		Controleur.ajouterQuestion(this);
	}



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

	public IOption       getOptions    (int ind) { return this.listeOptions.get(ind); }
	public int           getDifficulte ()        { return this.difficulte           ; }
	public int           getNumQuestion()        { return this.idQuestion           ; }
	public int           getNbPoints   ()        { return this.nbPoints             ; }
	public int           getTemps      ()        { return this.temps                ; }
	public List<String>  getComplements()        { return this.listeComplements     ; }
	public List<IOption> getEnsOptions ()        { return this.listeOptions         ; }
	public Notion        getNotion     ()        { return this.notion               ; }
	public String        getType       ()        { return this.type                 ; }
	public String        getEnonce     ()        { return this.enonce               ; }
	public String        getFeedback   ()        { return this.feedback             ; }




	/**
	 * +----------+
	 * | SETTEURS |
	 * +----------+
	 */

	public void setType       (String            type)                         { this.type             = type       ;                     this.mettreAJourQuestions();   }
	public void setEnonce     (String            enonce)                       { this.enonce           = enonce     ;                     this.mettreAJourQuestions();   }
	public void setEnonce     (String            enonce, boolean majQuestions) { this.enonce           = enonce     ; if (majQuestions) { this.mettreAJourQuestions(); } }
	public void setFeedback   (String            feedback)                     { this.feedback         = feedback   ;                     this.mettreAJourQuestions();   }
	public void setNbPoints   (int               nbPoints)                     { this.nbPoints         = nbPoints   ;                                                    }
	public void setTemps      (int               temps)                        { this.temps            = temps      ;                     this.mettreAJourQuestions();   }
	public void setDifficulte (int               difficulte)                   { this.difficulte       = difficulte ;                     this.mettreAJourQuestions();   }
	public void setComplements(ArrayList<String> complements)                  { this.listeComplements = complements;                                                    }
	public void setNotion     (Notion            notion)                       { this.notion           = notion     ;                     this.mettreAJourQuestions();   }




	/**
	 * +----------+
	 * | METHODES |
	 * +----------+
	 */

	public boolean ajouterOption(IOption opt)
	{
		if (opt == null)
			return false;


		for (IOption optionExistante : this.listeOptions)
		{
			if (optionsSontEgales(optionExistante, opt))
				return false;
		}

		this.listeOptions.add(opt);
		this.mettreAJourQuestions();

		return true;
	}



	public boolean supprimerOption(IOption opt)
	{
		this.listeOptions.remove(opt);
		this.mettreAJourQuestions();
		return true;
	}



	public void creerFichierQuestion() 
	{
		String chemin = "lib/ressources/" + notion.getRessource().getId () +
		                "_"               + notion.getRessource().getNom() +
						"/"               + notion.getNom      ()          +
						"/question"       + this.numQuestion               +
						"/question"       + this.numQuestion               +
						".rtf"                                               ;

		File fichier = new File(chemin);

		if (!fichier.exists()) 
		{
			ControleurFichier controleurFichier = new ControleurFichier(
				"lib/ressources/" + notion.getRessource().getId () +
				"_"               + notion.getRessource().getNom() +
				"/"               + notion.getNom      ()          +
				"/question"       + this.numQuestion
			);

			controleurFichier.ajouterFichier(""                                           );
			controleurFichier.ajouterRtf    ("/question" + this.numQuestion + ".rtf"      );
			controleurFichier.ecrireQuestion("/question" + this.numQuestion + ".rtf", this);
		}
	}



	private boolean optionsSontEgales(IOption opt1, IOption opt2)
	{
		if (opt1.getType  ().equals(opt2.getType  ()) &&
		    opt1.getEnonce().equals(opt2.getEnonce())    )
		{
			if (opt1 instanceof OptionAssociation               &&
			    opt2 instanceof OptionAssociation               &&
			    ((OptionAssociation) opt1).getAssocie() != null &&
			    ((OptionAssociation) opt2).getAssocie() != null    )
			{
				return ((OptionAssociation) opt1).getAssocie().equals(((OptionAssociation) opt2).getAssocie());
			}
			else if (opt1 instanceof OptionElimination && opt2 instanceof OptionElimination) 
			{
				OptionElimination optionE1 = (OptionElimination) opt1;
				OptionElimination optionE2 = (OptionElimination) opt2;

				return optionE1.getEstReponse   () == optionE2.getEstReponse   () &&
				       optionE1.getOrdre        () == optionE2.getOrdre        () &&
				       optionE1.getNbPointsMoins() == optionE2.getNbPointsMoins()    ;
			}
			else if (opt1 instanceof Option && opt2 instanceof Option)
			{
				return ((Option) opt1).getEstReponse() == ((Option) opt2).getEstReponse();
			}
			return true;
		}
		return false;
	}



	public void mettreAJourQuestions()
	{
		String emplacement = "lib/ressources/" + notion.getRessource().getId () +
		                     "_"               + notion.getRessource().getNom() +
							 "/"               + notion.getNom      ()          +
							 "/question"       + this.numQuestion                 ;
		File fichier = new File(emplacement);
	
		if (fichier.exists())
		{
			ControleurFichier controleurFichier = new ControleurFichier(
				"lib/ressources/" + notion.getRessource().getId () +
				"_"               + notion.getRessource().getNom() +
				"/"               + notion.getNom      ()          +
				"/"
			);

			controleurFichier.modifierQuestion("question" + this.numQuestion + "/question" + this.numQuestion, this);
		}
		else
			System.err.println("Erreur : Le fichier n'existe pas et ne peut donc pas être modifié.");
	}



	public boolean ajouterComplement(String emplacement)
	{
		if (this.listeComplements.contains(emplacement))
			return false;
		
		return this.listeComplements.add(emplacement);
	}
}
	
package controleur;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import modele.Notion;
import modele.Question;
import modele.Ressource;
import modele.option.IOption;
import modele.option.Option;
import modele.option.OptionAssociation;
import modele.option.OptionElimination;

public class ControleurFichier
{
	/*
	 * +------------+
	 * | ATTRIBUTS  |
	 * +------------+
	 */
	private String chemin;

	/*
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */
	public ControleurFichier(String chemin)
	{
		this.chemin = chemin;
	}

	/*
	 * +----------+
	 * | METHODES |
	 * +----------+
	 */
	public void ajouterRtf(String nomFichier)
	{
		if (!nomFichier.endsWith(".rtf"))
		{
			nomFichier += ".rtf";
		}
		nomFichier = this.chemin + nomFichier;

		try
		{
			File fichier = new File(nomFichier);

			if (fichier.createNewFile())
			{
				// System.out.println("Fichier créé : " + fichier.getAbsolutePath());
			}
			else
			{
				System.out.println("Le fichier existe déjà.");
			}
		}
		catch (IOException e)
		{
			System.out.println("Une erreur s'est produite lors de la création du fichier.");
			e.printStackTrace();
		}
	}

	public void ajouterFichier(String nomFichier)
	{
		nomFichier = this.chemin + nomFichier;
		File fichier = new File(nomFichier);

		// Vérification et création du répertoire
		if (!fichier.exists())
		{
			fichier.mkdirs();
		}
	}

	public void supprimerRtf(String nomFichier)
	{
		if (!nomFichier.endsWith(".rtf"))
		{
			nomFichier += ".rtf";
		}
		this.supprimerFichier(nomFichier);
	}

	public void supprimerFichier(String nomFichier)
	{
		nomFichier = this.chemin + nomFichier;
		File fichier = new File(nomFichier);

		if (fichier.exists())
		{
			fichier.delete();
		}
		else
		{
			System.out.println("Le fichier spécifié n'existe pas : " + nomFichier);
		}
	}

	public void ecrireQuestion(String chemin, Question qst)
	{
		if (!chemin.endsWith(".rtf"))
		{
			chemin += ".rtf";
		}
		chemin = this.chemin + chemin;
		try
		{
			PrintWriter pw = new PrintWriter(new FileOutputStream(chemin, false));
			pw.println(qst.getType() + ";" + qst.getEnonce() + ";" + qst.getNbPoints() + ";" + qst.getTemps() + ";" + qst.getDifficulte() + ";" + qst.getNotion().getNom());
			pw.close();
			System.out.println("Fichier RTF créé : " + chemin);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public boolean exists(String nom)
	{
		nom = this.chemin + nom;
		File fichier = new File(nom);
		return fichier.exists();
	}

	public void modifierQuestion(String chemin, Question qst)
	{
		if (!chemin.endsWith(".rtf")) {
			chemin += ".rtf";
		}
		chemin = this.chemin + chemin;

		File fichier = new File(chemin);

		// Vérifie si le fichier existe
		if (!fichier.exists()) {
			return;
		}

		String ligneEntiere = qst.getType() + ";" + qst.getEnonce() + ";" + qst.getNbPoints() + ";" + qst.getTemps() + ";" + qst.getDifficulte() + ";" + qst.getNotion().getNom();
		if (qst.getEnsOptions() != null)
		{
			for (IOption option : qst.getEnsOptions())
			{
				ligneEntiere += ";" + option.getType() + ";" + option.getEnonce() + ";" + option.getId();
				if (option instanceof OptionElimination) {
					OptionElimination optionE = (OptionElimination) option;
					ligneEntiere += ";" + optionE.getEstReponse() + ";" + optionE.getOrdre() + ";" + optionE.getNbPointsMoins();
				}
				if (option instanceof Option) {
					Option optionO = (Option) option;
					ligneEntiere += ";" + optionO.getEstReponse();
				}
				ligneEntiere += "|";
			}
			
		}
		
		try {
			// Lire toutes les lignes du fichier
			List<String> lignes = Files.readAllLines(Paths.get(chemin));

			if (!lignes.isEmpty()) {
				lignes.set(0, ligneEntiere);
			}

			// Réécrire le contenu modifié dans le fichier
			Files.write(Paths.get(chemin), lignes, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			System.err.println("Une erreur s'est produite lors de la modification du fichier : " + e.getMessage());
		}
	}



	public void modifierReponse(String chemin, IOption opt)
	{
		if (!chemin.endsWith(".rtf"))
		{
			chemin += ".rtf";
		}
		chemin = this.chemin + chemin;
		File fichier = new File(chemin);

		// Vérifie si le fichier existe
		if (!fichier.exists())
		{
			System.out.println("Le fichier n'existe pas.");
			return;
		}

		String texte ="";

		if(opt instanceof Option){texte = this.stringOption((Option)opt);}
		if(opt instanceof OptionAssociation){texte = this.stringOptionAssociation((OptionAssociation)opt);}
		if(opt instanceof OptionElimination){texte = this.stringOptionElimination((OptionElimination) opt);}

		try
		{
			// Lire toutes les lignes du fichier
			List<String> lignes = Files.readAllLines(Paths.get(chemin));

			// Identifier et modifier la ligne correspondante
			for (int i = 0; i < lignes.size(); i++)
			{
				String ligne = lignes.get(i);
				if (ligne.startsWith(opt.getId() + ";"))
				{
					lignes.set(i, texte);
					break;
				}
			}

			// Réécrire le contenu modifié dans le fichier
			Files.write(Paths.get(chemin), lignes, StandardOpenOption.TRUNCATE_EXISTING);
		}
		catch (IOException e)
		{
			System.err.println("Une erreur s'est produite lors de la modification du fichier : " + e.getMessage());
		}
	}

	private String stringOption(Option opt)
	{
		return opt.getId() + ";" + opt.getType() + ";" + opt.getEnonce() + ";" + opt.getEstReponse();
	}

	private String stringOptionAssociation(OptionAssociation opt)
	{
		return opt.getId() + ";" + opt.getType() + ";" + opt.getEnonce()  + "; question" + opt.getQuestion().getNumQuestion();
	}

	private String stringOptionElimination(OptionElimination opt)
	{
		return opt.getId() + ";" + opt.getType() + ";" + opt.getEnonce() + ";" +  opt.getOrdre()+ ";" + opt.getNbPointsMoins()  + ";" + opt.getEstReponse() + ";" + opt.getQuestion();
	}

	// Méthode pour écrire toutes les ressources dans un fichie
	public static void ecrireRessources(List<Ressource> ressources)
	{
		try (FileWriter writer = new FileWriter("Ressources.csv"))
		{
			for (Ressource ressource : ressources)
			{
				boolean aNotion = (ressource.getNbNotion()>0);
				
				writer.write("id:" + ressource.getId() + ";nom:" + ressource.getNom());

				if (aNotion)
				{
					writer.write(";notions:");
					aNotion = false;
					
					for (Notion notion : ressource.getEnsNotions())
					{
						writer.write((aNotion?",":"") + notion.getNom());

						aNotion = true;
					}
				}
				
				writer.write("\n");
			}
		}
		catch(IOException e)
		{
			System.out.println("Erreur lors de l'écriture des ressources : " + e.getMessage());
		}
	}

	// Méthode pour écrire toutes les questions dans un fichier
	public static void ecrireQuestions(List<Question> questions)
	{
		try (FileWriter writer = new FileWriter("Questions.csv"))
		{
			for (Question question : questions)
			{
				writer.write("nom:" + question.getEnonce() + ";nbPoints:" + question.getNbPoints() + ";temps:" + question.getTemps() + ";difficulte:" + question.getDifficulte() + ";type:" + question.getType() + ";notion :" + question.getNotion().getNom() + ";options :" );

				for (IOption option : question.getEnsOptions())
				{
					writer.write(option.getType() + ";" + option.getEnonce() + ";" + option.getId());

					if (option instanceof OptionAssociation) {
						writer.write(";" + ((OptionAssociation) option).getAssocie().getId());
					}
					if (option instanceof OptionElimination)
					{
						OptionElimination optionE = (OptionElimination) option;
						writer.write(";" + optionE.getEstReponse() + ";" + optionE.getOrdre() + ";" + optionE.getNbPointsMoins());
					}
					if (option instanceof Option)
					{
						Option optionO = (Option) option;
						writer.write(";" + optionO.getEstReponse());
					}
					writer.write(" | ");
				}
				writer.write("\n");
			}
		}
		catch (IOException e)
		{
			System.out.println("Erreur lors de l'écriture des questions : " + e.getMessage());
		}
	}

}

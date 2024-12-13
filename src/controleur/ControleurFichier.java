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
            pw.println(qst.getType() + "\t" + qst.getEnonce() + "\t" + qst.getNbPoints() + "\t" + qst.getTemps() + "\t" + qst.getDifficulte() + "\t" + qst.getNotion().getNom());
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

    public void ecrireReponse(String chemin, IOption opt)
    {
        if (!chemin.endsWith(".rtf"))
		{
			chemin += ".rtf";
		}
		chemin = this.chemin + chemin;

		String texte ="";

		if(opt instanceof Option)			{texte = this.stringOption((Option)opt);}
		if(opt instanceof OptionAssociation){texte = this.stringOptionAssociation((OptionAssociation)opt);}
		if(opt instanceof OptionElimination){texte = this.stringOptionElimination((OptionElimination) opt);}

		File fichier = new File(chemin);

		// Vérifie si le fichier existe
		if (!fichier.exists())
		{
			System.out.println("Le fichier n'existe pas : " + chemin);
			return;
		}

		// Écrit les données de l'objet Option dans le fichier
		try (PrintWriter writer = new PrintWriter(new FileOutputStream(fichier, true)))
		{
			writer.println(texte);
		}
		catch (IOException e)
		{
			System.err.println("Une erreur s'est produite lors de l'écriture dans le fichier : " + e.getMessage());
		}
    }

    public void modifierQuestion(String chemin, Question qst)
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

        String ligneEntiere = qst.getType() + "\t" + qst.getEnonce() + "\t" + qst.getNbPoints() + "\t" + qst.getTemps() + "\t" + qst.getDifficulte() + "\t" + qst.getNotion().getNom();
        try
        {
            // Lire toutes les lignes du fichier
            List<String> lignes = Files.readAllLines(Paths.get(chemin));

            // Modifier la première ligne
            if (!lignes.isEmpty())
            {
                lignes.set(0, ligneEntiere);
            }

            // Réécrire le contenu modifié dans le fichier
            Files.write(Paths.get(chemin), lignes, StandardOpenOption.TRUNCATE_EXISTING);
        }
        catch (IOException e)
        {
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
				if (ligne.startsWith(opt.getId() + "\t"))
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
		return opt.getId() + "\t" + opt.getType() + "\t" + opt.getIntitule() + "\t" + opt.getEstReponse();
	}

	private String stringOptionAssociation(OptionAssociation opt)
	{
		return opt.getId() + "\t" + opt.getType() + "\t" + opt.getIntitule()  + "\t question" + opt.getQuestion().getNumQuestion();
	}

	private String stringOptionElimination(OptionElimination opt)
	{
		return opt.getId() + "\t" + opt.getType() + "\t" + opt.getIntitule() + "\t" +  opt.getOrdre()+ "\t" + opt.getNbPointsMoins()  + "\t" + opt.getEstReponse() + "\t" + opt.getQuestion();
	}

    // Méthode pour écrire toutes les ressources dans un fichier
    public static void ecrireRessources(List<Ressource> ressources)
    {
        try (FileWriter writer = new FileWriter("Ressources.csv")) {
            for (Ressource ressource : ressources) {
                writer.write("nom:" + ressource.getNom() + "    notions:");
                for (String notion : ressource.getNomsNotions())
                {
                    writer.write("," + notion);
                }
                writer.write("\n");
                System.out.println("Ecriture de la ressource : " + ressource.getNom());
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture des ressources : " + e.getMessage());
        }
    }

    // Méthode pour écrire toutes les notions dans un fichier
    public static void ecrireNotions(List<Notion> notions)
    {
        try (FileWriter writer = new FileWriter("Notions.csv")) {
            for (Notion notion : notions) {
                writer.write("nom:" + notion.getNom() + "   ressource:" + notion.getRessource().getNom() + "\n");
                System.out.println("Ecriture de la notion : " + notion.getNom());
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture des notions : " + e.getMessage());
        }
    }

    // Méthode pour écrire toutes les questions dans un fichier
    public static void ecrireQuestions(List<Question> questions)
    {
        try (FileWriter writer = new FileWriter("Questions.csv")) {
            for (Question question : questions) {
                writer.write(question.getEnonce() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture des questions : " + e.getMessage());
        }
    }
}

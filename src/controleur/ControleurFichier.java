package controleur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
	private String emplacement;

	/*
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */
	public ControleurFichier(String emplacement)
	{
		this.emplacement = emplacement;
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
		nomFichier = this.emplacement + nomFichier;

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
		nomFichier = this.emplacement + nomFichier;
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
		nomFichier = this.emplacement + nomFichier;

		File fichier = new File(nomFichier);

		if (fichier.exists())
		{
			File parent = fichier.getParentFile();

			fichier.delete();
			parent .delete();
		}
		else
		{
			System.out.println("Le fichier spécifié n'existe pas : " + nomFichier);
		}
	}

	public void ecrireQuestion(String emplacement, Question qst)
	{
		

		if (!emplacement.endsWith(".rtf")) 
		{
			emplacement += ".rtf";
		}
		emplacement = this.emplacement + emplacement;

		File fichier = new File(emplacement);

		// Vérifie si le fichier existe
		if (!fichier.exists()) 
		{
			return;
		}


		String enonce;

		enonce = qst.getEnonce();


		String ligneEntiere = qst.getType() + ";" + enonce + ";" + qst.getNbPoints() + ";" + qst.getTemps() + ";" + qst.getDifficulte() + ";" + qst.getNotion().getNom() + ";" + qst.getFeedback() + ";";
		
		if (qst.getEnsOptions() != null)
		{
			for (IOption option : qst.getEnsOptions())
			{
				enonce = option.getEnonce();
				/*if (aImage(option.getEnonce(), emplacement))
				{
					int indexDernierSlash = emplacement.lastIndexOf("/");
					new File(emplacement.substring(0, indexDernierSlash + 1) + File.separator + "complements").mkdirs();

					List<String> listeComplements = getImages(enonce);
					for (String image : listeComplements)
					{
						File fichierSource = new File(image);
						copierImage(fichierSource, emplacement.substring(0, indexDernierSlash + 1) + "complements/" + fichierSource.getName());
						enonce = gererPiecesJointes(enonce, emplacement.substring(0, indexDernierSlash + 1) + "complements/");
					}
				}*/

				ligneEntiere += option.getType() + "~" + enonce + "~" + option.getId();
				if (option instanceof OptionElimination)
				{
					OptionElimination optionE = (OptionElimination) option;
					ligneEntiere += "~" + optionE.getEstReponse() + "~" + optionE.getOrdre() + "~" + optionE.getNbPointsMoins();
				}
				
				if (option instanceof Option) 
				{
					Option optionO = (Option) option;
					ligneEntiere += "~" + optionO.getEstReponse();
				}
				ligneEntiere += "|";
			}

			try {
				PrintWriter pw = new PrintWriter(new FileOutputStream(emplacement, false));
				pw.println(ligneEntiere);
				pw.close();
			} catch (Exception e) {
				System.err.println("Erreur dans la création du rtf");
			}
			
		}
	}

	public boolean exists(String nom)
	{
		nom = this.emplacement + nom;
		File fichier = new File(nom);
		return fichier.exists();
	}

	public void modifierQuestion(String emplacement, Question qst)
	{
		if (!emplacement.endsWith(".rtf")) {
			emplacement += ".rtf";
		}
		emplacement = this.emplacement + emplacement;

		File fichier = new File(emplacement);

		// Vérifie si le fichier existe
		if (!fichier.exists()) {
			return;
		}


		String enonce;

		enonce = qst.getEnonce();
		if (aImage(qst.getEnonce(), emplacement))
		{
			int indexDernierSlash = emplacement.lastIndexOf("/");
			new File(emplacement.substring(0, indexDernierSlash + 1) + File.separator + "complements").mkdirs();

			List<String> listeImages = getImages(enonce);
			for (String image : listeImages)
			{
				File fichierSource = new File(image);
				copierFichier(fichierSource, emplacement.substring(0, indexDernierSlash + 1) + "complements/" + fichierSource.getName());
				qst.ajouterComplement(emplacement.substring(0, indexDernierSlash + 1) + "complements/" + fichierSource.getName());
				enonce = gererImage(enonce, emplacement.substring(0, indexDernierSlash + 1) + "complements/" + fichierSource.getName(), fichierSource.getName());
			}
		}

		if (aPieceJointe(qst.getEnonce(), emplacement))
		{
			int indexDernierSlash = emplacement.lastIndexOf("/");
			new File(emplacement.substring(0, indexDernierSlash + 1) + File.separator + "complements").mkdirs();

			List<String> listePiecesJointes = getPiecesJointes(enonce);
			for (String pieceJointe : listePiecesJointes)
			{
				File fichierSource = new File(pieceJointe);
				copierFichier(fichierSource, emplacement.substring(0, indexDernierSlash + 1) + "complements/" + fichierSource.getName());
				qst.ajouterComplement(emplacement.substring(0, indexDernierSlash + 1) + "complements/" + fichierSource.getName());
				enonce = gererPiecesJointe(enonce, emplacement.substring(0, indexDernierSlash + 1) + "complements/" + fichierSource.getName(), fichierSource.getName());
			}
		}

		qst.setEnonce(enonce, false);

		String ligneEntiere = qst.getType() + ";" + qst.getEnonce() + ";" + qst.getNbPoints() + ";" + qst.getTemps() + ";" + qst.getDifficulte() + ";" + qst.getNotion().getNom() + ";" + qst.getFeedback() + ";";
		if (qst.getEnsOptions() != null)
		{
			for (IOption option : qst.getEnsOptions())
			{
				enonce = option.getEnonce();
				if (aImage(option.getEnonce(), emplacement))
				{
					int indexDernierSlash = emplacement.lastIndexOf("/");
					new File(emplacement.substring(0, indexDernierSlash + 1) + File.separator + "complements").mkdirs();

					List<String> listeImages = getImages(enonce);
					for (String image : listeImages)
					{
						File fichierSource = new File(image);
						copierFichier(fichierSource, emplacement.substring(0, indexDernierSlash + 1) + "complements/" + fichierSource.getName());
						qst.ajouterComplement(emplacement.substring(0, indexDernierSlash + 1) + "complements/" + fichierSource.getName());
						enonce = gererImage(enonce, emplacement.substring(0, indexDernierSlash + 1) + "complements/" + fichierSource.getName(), fichierSource.getName());
					}
				}

				option.setEnonce(enonce);

				ligneEntiere += option.getType() + "~" + option.getEnonce() + "~" + option.getId();
				if (option instanceof OptionElimination)
				{
					OptionElimination optionE = (OptionElimination) option;
					ligneEntiere += "~" + optionE.getEstReponse() + "~" + optionE.getOrdre() + "~" + optionE.getNbPointsMoins();
				}
				if (option instanceof Option) 
				{
					Option optionO = (Option) option;
					ligneEntiere += "~" + optionO.getEstReponse();
				}
				ligneEntiere += "|";
			}
		}
		
		try {
			// Lire toutes les lignes du fichier
			List<String> lignes = Files.readAllLines(Paths.get(emplacement));

			if (!lignes.isEmpty()) 
			{
				lignes.set(0, ligneEntiere);
			}

			// Réécrire le contenu modifié dans le fichier
			Files.write(Paths.get(emplacement), lignes, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			System.err.println("Une erreur s'est produite lors de la modification du fichier : " + e.getMessage());
		}
	}


	private boolean aImage(String contenu, String emplacement)
	{
		String  regex   = "<img\\s+[^>]*src\\s*=\\s*\"[^\"]+\"";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(contenu);

		return matcher.find();
	}


	private boolean aPieceJointe(String contenu, String emplacement)
	{
		String  regex   = "<a\\s+[^>]*href\\s*=\\s*\"file:///[^\"\\s]+\"";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(contenu);

		return matcher.find();
	}


	private static List<String> getImages(String contenu) {
        // Liste pour stocker les chemins des images trouvés
        List<String> imagePaths = new ArrayList<>();

        // Expression régulière pour trouver les balises <img> avec un attribut src
        String regex = "<img[^>]*src=\\\"([^\\\"]*)\\\"";

        // Compiler l'expression régulière
        Pattern pattern = Pattern.compile(regex);

        // Matcher pour rechercher les correspondances dans le contenu
        Matcher matcher = pattern.matcher(contenu);

        // Parcourir toutes les correspondances trouvées
        while (matcher.find()) {
            // Ajouter la valeur de l'attribut src à la liste
            imagePaths.add(matcher.group(1));
        }

        return imagePaths;
    }


	private static List<String> getPiecesJointes(String contenu) {
		// Liste pour stocker les chemins des pièces jointes trouvées
		List<String> pieceJointePaths = new ArrayList<>();
	
		// Expression régulière pour trouver les balises <a> avec un attribut href
		String regex = "<a[^>]*href=\\\"([^\\\"]*)\\\"";
	
		// Compiler l'expression régulière
		Pattern pattern = Pattern.compile(regex);
	
		// Matcher pour rechercher les correspondances dans le contenu
		Matcher matcher = pattern.matcher(contenu);
	
		// Parcourir toutes les correspondances trouvées
		while (matcher.find()) {
			// Récupérer la valeur de l'attribut href
			String chemin = matcher.group(1);
	
			// Supprimer le préfixe "file:" s'il existe
			if (chemin.startsWith("file:")) {
				chemin = chemin.replace("file:", "");
			}
	
			// Ajouter le chemin nettoyé à la liste
			pieceJointePaths.add(chemin);
		}
	
		return pieceJointePaths;
	}
	

	public static void copierFichier(File fichierSource, String emplacementCible) {
        File fichierCible = new File(emplacementCible);

        // Vérifie si le fichier source existe
        if (!fichierSource.exists()) {
            System.out.println("Le fichier source n'existe pas : " + fichierSource.getName());
            return;
        }

        // Tente de copier le fichier
        try (FileInputStream inputStream = new FileInputStream(fichierSource);
             FileOutputStream outputStream = new FileOutputStream(fichierCible)) {

            byte[] buffer = new byte[1024];
            int longueur;

            // Lit et écrit les données en utilisant un buffer
            while ((longueur = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, longueur);
            }

        } catch (IOException e) {
            System.out.println("Erreur lors de la copie de l'image : " + e.getMessage());
        }
    }



	public static String gererImage(String enonce, String cheminAppli, String nomFichierAppli) {
		String regex = "(<img\\s+[^>]*src\\s*=\\s*\"([^\"]+)\"[^>]*>)(<!--" + Pattern.quote(cheminAppli) + "-->)?";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(enonce);
		StringBuffer resultat = new StringBuffer();
	
		while (matcher.find()) {
			String baliseImg = matcher.group(1);
			String cheminOrigine = matcher.group(2);
			String commentaireExistant = matcher.group(3);
	
			String nomFichier = cheminOrigine.substring(
				Math.max(cheminOrigine.lastIndexOf("/"), cheminOrigine.lastIndexOf("\\")) + 1
			);
	
			String replacement = baliseImg;
			if (nomFichier.equals(nomFichierAppli)) {
				String nouveauChemin = "complements/" + nomFichier;
				replacement = baliseImg.replace(cheminOrigine, nouveauChemin);
				
				if (commentaireExistant == null) {
					replacement += "<!--" + cheminAppli + "-->";
				} else {
					replacement += commentaireExistant;
				}
			} else if (commentaireExistant != null) {
				replacement += commentaireExistant;
			}
			
			matcher.appendReplacement(resultat, Matcher.quoteReplacement(replacement));
		}
	
		matcher.appendTail(resultat);
		return resultat.toString();
	}
	
	
	
	
	


	public static String gererPiecesJointe(String enonce, String cheminAppli, String nomFichierAppli) {
		String regex = "(<a\\s+[^>]*href\\s*=\\s*\"([^\"]+)\"[^>]*>)(<!--" + Pattern.quote(cheminAppli) + "-->)?";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(enonce);
		StringBuffer resultat = new StringBuffer();
	
		while (matcher.find()) {
			String baliseA = matcher.group(1);
			String cheminOrigine = matcher.group(2);
			String commentaireExistant = matcher.group(3);
	
			String nomFichier = cheminOrigine.substring(
				Math.max(cheminOrigine.lastIndexOf("/"), cheminOrigine.lastIndexOf("\\")) + 1
			);
	
			String replacement = baliseA;
			if (nomFichier.equals(nomFichierAppli)) {
				String nouveauChemin = "complements/" + nomFichier;
				replacement = baliseA.replace(cheminOrigine, nouveauChemin);
				
				if (commentaireExistant == null) {
					replacement += "<!--" + cheminAppli + "-->";
				} else {
					replacement += commentaireExistant;
				}
			} else if (commentaireExistant != null) {
				replacement += commentaireExistant;
			}
			
			matcher.appendReplacement(resultat, Matcher.quoteReplacement(replacement));
		}
	
		matcher.appendTail(resultat);
		return resultat.toString();
	}
	
	
	
	
	
	
	




	public void modifierReponse(String emplacement, IOption opt)
	{
		if (!emplacement.endsWith(".rtf"))
		{
			emplacement += ".rtf";
		}
		emplacement = this.emplacement + emplacement;
		File fichier = new File(emplacement);

		// Vérifie si le fichier existe
		if (!fichier.exists())
		{
			System.out.println("Le fichier n'existe pas. :" + emplacement);
			return;
		}

		System.out.println("rere ça passe ici");

		String texte ="";

		if(opt instanceof Option){texte = this.stringOption((Option)opt);}
		if(opt instanceof OptionAssociation){texte = this.stringOptionAssociation((OptionAssociation)opt);}
		if(opt instanceof OptionElimination){texte = this.stringOptionElimination((OptionElimination) opt);}

		try
		{
			// Lire toutes les lignes du fichier
			List<String> lignes = Files.readAllLines(Paths.get(emplacement));

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
			Files.write(Paths.get(emplacement), lignes, StandardOpenOption.TRUNCATE_EXISTING);
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
		try (FileWriter writer = new FileWriter("RessourceEtNotion.csv"))
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
}

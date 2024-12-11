package modele;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import modele.option.*;

public class Fichier{

	private String chemin;
	public Fichier(String chemin)
	{
		this.chemin = chemin;
	}

	public void ajouterRtf(String nomFichier)
	{
		if (!nomFichier.endsWith(".rtf")) {
			nomFichier += ".rtf";
		}
		nomFichier = this.chemin + nomFichier;

		try {
			File fichier = new File(nomFichier);

			if (fichier.createNewFile()) {
				//System.out.println("Fichier créé : " + fichier.getAbsolutePath());
			} else {
				System.out.println("Le fichier existe déjà.");
			}

		} catch (IOException e) {
			System.out.println("Une erreur s'est produite lors de la création du fichier.");
			e.printStackTrace();
		}
	}

	public void ajouterFichier(String nomFichier)
	{
		nomFichier = this.chemin+nomFichier;
		File fichier = new File(nomFichier);

		// Vérification et création du répertoire
		if (!fichier.exists()) {
			boolean estCree = fichier.mkdirs();
		}
	}

	public void supprimerRtf(String nomFichier)
	{
		if (!nomFichier.endsWith(".rtf")) {
			nomFichier += ".rtf";
		}
		this.supprimerFichier(nomFichier);
	}

	public void supprimerFichier(String nomFichier)
	{
		nomFichier = this.chemin+nomFichier;
		File fichier = new File(nomFichier);

		if (fichier.exists()) {
			fichier.delete();
		} else {
			System.out.println("Le fichier spécifié n'existe pas : " + nomFichier);
		}
	}

	public void ecrireQuestion(String chemin, Question qst)
	{
		if (!chemin.endsWith(".rtf")) {
			chemin += ".rtf";
		}
		chemin = this.chemin+chemin;
		try
		{
			PrintWriter pw = new PrintWriter(new FileOutputStream (chemin, false));
			pw.println(qst.getType() + "\t" + qst.getIntitule() + "\t" + qst.getNbPoints() + "\t" + qst.getTemps() + "\t" + qst.getDifficulte() + "\t" + qst.getNotion().getNom());
			pw.close();
			System.out.println("creer rtf");
			System.out.println(chemin);
		}
		catch (Exception e){e.printStackTrace();}
	}

	
	public boolean exists(String nom) {
		nom = this.chemin + nom;
		File fichier = new File(nom);
		return fichier.exists();
	}

	public void ecrireReponse(String chemin, Option opt)
	{
		if (!chemin.endsWith(".rtf")) {
			chemin += ".rtf";
		}
		chemin = this.chemin+chemin;


		File fichier = new File(chemin);

		// Vérifie si le fichier existe
		if (!fichier.exists()) {
			System.out.println("Le fichier n'existe pas.");
			System.out.println(chemin);
			return;
		}

		// Écrit les données de l'objet Option dans le fichier
		try (PrintWriter writer = new PrintWriter(new FileOutputStream(fichier, true))) {
			writer.println(opt.getId()+"\t"+opt.getType() + "\t" + opt.getIntitule() + "\t" + opt.getEstReponse()+"\t");
		} catch (IOException e) {
			System.err.println("Une erreur s'est produite lors de l'écriture dans le fichier : " + e.getMessage());
		}

	}

	public void modifierQuestion(String chemin, Question qst)
	{
		if (!chemin.endsWith(".rtf")) {
			chemin += ".rtf";
		}
		chemin = this.chemin+chemin;

		File fichier = new File(chemin);

		// Vérifie si le fichier existe
		if (!fichier.exists()) {
			System.out.println("Le fichier n'existe pas.");
			return;
		}

		String ligneEntiere = qst.getType() + "\t" + qst.getIntitule() + "\t" + qst.getNbPoints() + "\t" + qst.getTemps() + "\t" + qst.getDifficulte() + "\t" + qst.getNotion().getNom();
		try {
			// Lire toutes les lignes du fichier
			List<String> lignes = Files.readAllLines(Paths.get(chemin));

			// Modifier la première ligne
			if (!lignes.isEmpty()) {
				lignes.set(0,ligneEntiere);
			}

			// Réécrire le contenu modifié dans le fichier
			Files.write(Paths.get(chemin), lignes, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			System.err.println("Une erreur s'est produite lors de la modification du fichier : " + e.getMessage());
		}
	}

	public void modifierReponse(String chemin, Option opt)
	{
		if (!chemin.endsWith(".rtf")) {
			chemin += ".rtf";
		}
		chemin = this.chemin+chemin;
		File fichier = new File(chemin);

        // Vérifie si le fichier existe
        if (!fichier.exists()) {
            System.out.println("Le fichier n'existe pas.");
            return;
        }

        try {
            // Lire toutes les lignes du fichier
            List<String> lignes = Files.readAllLines(Paths.get(chemin));

            // Identifier et modifier la ligne correspondante
            for (int i = 0; i < lignes.size(); i++) {
                String ligne = lignes.get(i);
                if (ligne.startsWith(opt.getId() + "\t")) {
                    lignes.set(i, opt.getId()+"\t"+opt.getType() + "\t" + opt.getIntitule() + "\t" + opt.getEstReponse());
                    break;
                }
            }

            // Réécrire le contenu modifié dans le fichier
            Files.write(Paths.get(chemin), lignes, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Une erreur s'est produite lors de la modification du fichier : " + e.getMessage());
        }
	}
}

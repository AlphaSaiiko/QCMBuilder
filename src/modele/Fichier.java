package modele;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
			PrintWriter pw = new PrintWriter(new FileOutputStream (chemin, true));
			pw.println(qst.getType() + "\t" + qst.getIntitule() + "\t" + qst.getNbPoints() + "\t" + qst.getTemps() + "\t" + qst.getDifficulte() + "\t" + qst.getNotion().getNom());
			pw.close();
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
			System.out.println("1");
			writer.println(opt.getType() + "\t" + opt.getIntitule() + "\t" + opt.getEstReponse());
			System.out.println("2");
		} catch (IOException e) {
			System.err.println("Une erreur s'est produite lors de l'écriture dans le fichier : " + e.getMessage());
		}

	}


}

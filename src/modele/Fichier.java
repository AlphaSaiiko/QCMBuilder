package modele;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

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
			PrintWriter pw = new PrintWriter(new FileOutputStream (chemin));
			pw.println(qst.getType() + "\t" + qst.getIntitule() + "\t" + qst.getNbPoints() + "\t" + qst.getTemps() + "\t" + qst.getDifficulte() + "\t" + qst.getNotion().getNom());
			pw.close();
		}
		catch (Exception e){e.printStackTrace();}
	}


}

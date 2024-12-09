package modele;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import modele.option.*;

public class Fichier{

	private String chemin;
	public Fichier(String chemin)
	{
		this.chemin = chemin;
	}

	public void ajouterTxt(String nomFichier)
	{
		if (!nomFichier.endsWith(".txt")) {
			nomFichier += ".txt";
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


}

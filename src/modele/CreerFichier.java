package modele;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import modele.option.*;

public class CreerFichier
{
	private ArrayList<Ressource> lstRessource;
	public CreerFichier(ArrayList<Ressource> lstRessource)
	{
		this.lstRessource = new ArrayList<>(lstRessource);
	}

	public void creerRessource()
	{
		for (Ressource ressource : this.lstRessource) {

			this.creer("lib/ressources/"+ressource.getNom());
			for (Notion notion : ressource.getEnsNotions())
			{
				this.creer("lib/ressources/"+ressource.getNom()+"/"+notion.getNom());
				int i =1;
				for (Question question : notion.getEnsQuestions())
				{
					this.creer("lib/ressources/"+ressource.getNom()+"/"+notion.getNom()+"/question"+i);
					int j=0;
					for (IOption opt : question.getEnsOptions())
					{
						this.creerFichierTxt("lib/ressources/"+ressource.getNom()+"/"+notion.getNom()+"/question"+i+"/question"+j);
						j++;
					}
					i++;
				}
			}
		}
		System.out.println("les fichiers ont été créé");
	}

	public void creer(String nom)
	{
			// Création d'un objet File représentant le répertoire
			File fichier = new File(nom);

			// Vérification et création du répertoire
			if (!fichier.exists()) {
				boolean estCree = fichier.mkdirs();
			}
	}

	public void creerFichierTxt(String nomFichier) {
		if (!nomFichier.endsWith(".txt")) {
			nomFichier += ".txt";
		}

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


}

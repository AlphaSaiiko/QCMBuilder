package controleur;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import modele.Evaluation;
import modele.Notion;
import modele.Ressource;
import vue.Accueil;

public class Controleur
{
	private List<Ressource> listRessource = new ArrayList<>();
	private static Accueil acc;
	private static List<Evaluation> listEval;

	public Controleur()
	{
		Controleur.listEval = new ArrayList<>();
	}

    public static void chargerRessourcesEtNotion()
	{
        String resourcesPath = "lib/ressources";
        try (Stream<Path> paths = Files.walk(Paths.get(resourcesPath)))
		{
            paths.filter(Files::isDirectory).forEach(path ->
			{
                File dir = path.toFile();
                File parent = dir.getParentFile();

                // Si le répertoire actuel est une ressource
                if (parent != null && parent.getName().equals("ressources"))
				{
                    System.out.println("Ressource trouvée : " + dir.getName());
                    Ressource.creerRessource(dir.getName());
                }
                // Si le répertoire parent est une ressource
                else if (parent != null && parent.getParentFile() != null && parent.getParentFile().getName().equals("ressources"))
				{
                    Ressource ressource = Ressource.creerRessource(parent.getName());
                    if (ressource == null)
					{
                        System.out.println("Ressource non trouvée pour la notion, création d'une nouvelle ressource : " + parent.getName());
                        Ressource.creerRessource(parent.getName());
                        ressource = Ressource.creerRessource(parent.getName());
                    }

                    // Création de la notion
                    Notion notion = new Notion(dir.getName(), ressource);
                    System.out.println("Notion créée : " + notion.getNom() + " pour la ressource : " + ressource.getNom());

                    // Ajout de la notion à la ressource
                    ressource.ajouterNotion(notion);
                    System.out.println("Notion ajoutée à la ressource : " + ressource.getNom());
                }
            });
        }
		catch (IOException e)
		{
            System.err.println("Erreur lors du chargement des ressources et des notions : " + e.getMessage());
        }
    }
	public static void main(String[] args)
	{
		Controleur controleur = new Controleur();
		Controleur.acc      = new Accueil();
		controleur.chargerRessourcesEtNotion();
	}
}
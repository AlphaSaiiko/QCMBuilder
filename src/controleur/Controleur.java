package controleur;

import java.util.ArrayList;
import java.util.List;

import modele.Evaluation;
import modele.Notion;
import modele.Ressource;
import vue.Accueil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Controleur
{
	private static List<Evaluation> listEval;
	private static Accueil    acc ;

	public Controleur()
	{
		Controleur.acc      = new Accueil();
		Controleur.listEval = new ArrayList<Evaluation>();
	}

	
	public void chargerRessourcesEtNotion() {
		String resourcesPath = "lib/ressources";
		try (Stream<Path> paths = Files.walk(Paths.get(resourcesPath))) {
			paths.filter(Files::isDirectory)
		 .forEach(path -> {
			 File dir = path.toFile();
			 File parent1 = dir.getParentFile();
			 File parent2 = (parent1 != null) ? parent1.getParentFile() : null;
			 File parent3 = (parent2 != null) ? parent2.getParentFile() : null;
			 if (parent3 != null && parent3.getName().startsWith("ressource")) {
				 System.out.println("Question: " + dir.getName());
			 } else if (parent2 != null && parent2.getName().startsWith("ressource")) {
				 System.out.println("Notion: " + dir.getName());
				 Notion notion = new Notion(dir.getName(), new Ressource(parent1.getName()));
			 } else if (parent1 != null && parent1.getName().startsWith("ressource")) {
				 System.out.println("Ressource: " + dir.getName());
				 Ressource ressource = new Ressource(dir.getName());
			 }
		 });
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Controleur controleur = new Controleur();
		controleur.chargerRessourcesEtNotion();
	}
}
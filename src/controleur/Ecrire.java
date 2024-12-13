package controleur;

import modele.Ressource;
import modele.Notion;
import modele.Question;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Ecrire
{
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

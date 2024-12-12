package controleur;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;
import modele.*;
import vue.*;
import vue.Evaluation;

public class Controleur 
{
    /*
     * +------------+
     * | PARAMÈTRES |
     * +------------+
     */
    private static List<Ressource> listeRessources = new ArrayList<>();
    private static Accueil accueil;
    private static List<Evaluation> listeEvaluations;

    /*
     * +--------------+
     * | CONSTRUCTEUR |
     * +--------------+
     */
    public Controleur() 
    {
        Controleur.listeEvaluations = new ArrayList<>();
    }

    /*
     * +-------------------------------+
     * | CHARGER RESSOURCES ET NOTIONS |
     * +-------------------------------+
     */
    public static void chargerRessourcesEtNotions() 
    {
        String cheminRessources = "lib/ressources";
        try (Stream<Path> chemins = Files.walk(Paths.get(cheminRessources))) 
        {
            chemins.filter(Files::isDirectory).forEach(chemin -> 
            {
                File dossier = chemin.toFile();
                File parent = dossier.getParentFile();

                // Si le répertoire actuel est une ressource
                if (parent != null && parent.getName().equals("ressources")) 
                {
                    System.out.println("Ressource trouvée : " + dossier.getName());
                    Ressource.creerRessource(dossier.getName());
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
                    Notion notion = Notion.creerNotion(dossier.getName(), ressource);
                    System.out.println("Notion créée : " + notion.getNom() + " pour la ressource : " + ressource.getNom());

                    Controleur.chargerQuestions(notion, dossier);

                    // Ajout de la notion à la ressource
                    System.out.println("Notion ajoutée à la ressource : " + ressource.getNom());
                }
            });
        } 
        catch (IOException e) 
        {
            System.err.println("Erreur lors du chargement des ressources et des notions : " + e.getMessage());
        }
    }

    /*
     * +--------------------+
     * | CHARGER QUESTIONS  |
     * +--------------------+
     */
    public static void chargerQuestions(Notion notion, File dossier) 
    {
        int compteur = 0;
        // Boucle pour chaque question
        for (File sousDossier : dossier.listFiles()) 
        {
            File fichierRTF;
            if (sousDossier.listFiles() != null) 
            {
                fichierRTF = new File(sousDossier, sousDossier.getName() + ".rtf");
            } 
            else 
            {
                return;
            }

            try 
            {
                Scanner scanner = new Scanner(new FileInputStream(fichierRTF));
                String ligneQuestion = scanner.nextLine();
                Object[] ligne = ligneQuestion.split("\t");

                String type = String.valueOf(ligne[0]);
                String intitule = String.valueOf(ligne[1]);
                int nbPoints = Integer.valueOf(String.valueOf(ligne[2]));
                int temps = Integer.valueOf(String.valueOf(ligne[3]));
                int difficulte = Integer.valueOf(String.valueOf(ligne[4]));

                Question question = Question.creerQuestion(nbPoints, temps, notion, difficulte, type);

                notion.ajouterQuestion(question);
                scanner.close();
            } 
            catch (FileNotFoundException e) 
            {
                e.printStackTrace();
            }
            compteur++;
        }
    }

    /*
     * +-------------------+
     * | MÉTHODES D'ACTION |
     * +-------------------+
     */
    public static void creerEvaluation() 
    {
        new CreerEvaluation();
    }

    public static void creerQuestion() 
    {
        new CreerQuestion();
    }

    public static void ouvrirParametres() 
    {
        new Parametre();
    }

    public static void ouvrirAccueil() 
    {
        new Accueil();
    }

    public static void creerNotion(String titreNotion, Ressource ressource) 
    {
        Notion.creerNotion(titreNotion, ressource);
    }

    public static void creerQuestion(int nbPoints, int tempsReponse, Notion notion, int difficulte, String type) 
    {
        Question question = Question.creerQuestion(nbPoints, tempsReponse, notion, difficulte, type);
        switch (type) 
        {
            case "QCMRU":
                new QuestionReponseUnique(question);
                break;
            case "QCMRM":
                new QuestionReponseMultiple(question);
                break;
            case "QAE":
                new QuestionLiaison(question);
                break;
            case "QAEPR":
                new QuestionAvecElimination(question);
                break;
        }
    }

    public static void creerRessource(String titreRessource) 
    {
        Ressource.creerRessource(titreRessource);
    }

    public static void supprimerRessource(String nomRessource) 
    {
        Ressource ressource = Metier.trouverRessourceParNom(nomRessource);
        if (ressource != null) 
        {
            File dossierRessource = new File("./lib/ressources/" + ressource.getNom());
            supprimerDossier(dossierRessource);
            Metier.getListRessource().remove(ressource);
        }
    }

    public static void modifierRessource(String nomRessource) 
    {
        Ressource ressource = Metier.trouverRessourceParNom(nomRessource);
        if (ressource != null) 
        {
            new ModifierRessource(ressource).setVisible(true);
        }
    }

    public static void supprimerNotion(String nomRessource, String nomNotion) 
    {
        Ressource ressource = Metier.trouverRessourceParNom(nomRessource);
        if (ressource != null) 
        {
            Notion notion = ressource.getNotion(nomNotion);
            if (notion != null) 
            {
                File dossierNotion = new File("./lib/ressources/" + notion.getRessource().getNom() + "/" + notion.getNom());
                supprimerDossier(dossierNotion);
                ressource.getEnsNotions().remove(notion);
            }
        }
    }

    public static void modifierNotion(String nomRessource, String nomNotion) 
    {
        Ressource ressource = Metier.trouverRessourceParNom(nomRessource);
        if (ressource != null) 
        {
            Notion notion = ressource.getNotion(nomNotion);
            if (notion != null) 
            {
                new ModifierNotion(ressource, notion).setVisible(true);
            }
        }
    }

    public static List<Ressource> getListeRessources() 
    {
        return Metier.getListRessource();
    }

    public static Ressource trouverRessourceParNom(String nomRessource)
    {
        for (Ressource ressource : Metier.getListRessource())
        {
            if (ressource.getNom().equalsIgnoreCase(nomRessource))
            {
                return ressource;
            }
        }
        return null;
    }

    private static void supprimerDossier(File dossier) 
    {
        if (dossier.isDirectory()) 
        {
            for (File fichier : dossier.listFiles()) 
            {
                supprimerDossier(fichier);
            }
        }
        dossier.delete();
    }

    public static void main(String[] args) 
    {
        new Controleur();
        Controleur.chargerRessourcesEtNotions();
        Controleur.ouvrirAccueil();
    }
}

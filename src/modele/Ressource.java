package modele;

import java.util.ArrayList;
import java.util.List;

public class Ressource
{
    /*
     * +------------+
     * | PARAMETRES |
     * +------------+
     */
    private static final List<Ressource> listRessource = new ArrayList<>();
    private List<Notion> ensNotions = new ArrayList<>();
    private String nom;

    /*
     * +--------------+
     * | CONSTRUCTEUR |
     * +--------------+
     */
    public static Ressource creerRessource(String nom)
    {
        Ressource ressource = Ressource.trouverRessourceParNom(nom);
        if (ressource == null)
        {
            ressource = new Ressource(nom);
            System.out.println("Nouvelle ressource créée avec le titre: " + nom);
        }
        else
        {
            System.out.println("La ressource existe déjà.");
        }
        return ressource;
    }

    private Ressource(String nom)
    {
        this.nom = nom;
        Ressource.listRessource.add(this);
        this.creerFichierRessource();
    }

    /*
     * +---------+
     * | GETTERS |
     * +---------+
     */
    public String getNom()
    {
        return this.nom;
    }

    public int getNbNotion()
    {
        return this.ensNotions.size();
    }

    public List<Notion> getEnsNotions()
    {
        return this.ensNotions;
    }

    public Notion getNotion(String nom)
    {
        for (Notion notion : this.ensNotions)
        {
            if (notion.getNom().equalsIgnoreCase(nom))
            {
                return notion;
            }
        }
        return null;
    }

    public static List<Ressource> getListRessource()
    {
        return Ressource.listRessource;
    }

    public String[] getNomsNotions()
    {
        String[] nomsNotions = new String[this.ensNotions.size()];
        for (int i = 0; i < this.ensNotions.size(); i++)
        {
            nomsNotions[i] = this.ensNotions.get(i).getNom();
        }
        return nomsNotions;
    }

    /*
     * +---------+
     * | SETTERS |
     * +---------+
     */
    public void setNom(String nom)
    {
        this.nom = nom;
    }

    /*
     * +----------+
     * | METHODES |
     * +----------+
     */
    public void creerFichierRessource()
    {
        Fichier tmp = new Fichier("lib/ressources/");
        tmp.ajouterFichier(this.getNom());
    }

    public void ajouterNotion(Notion notion)
    {
        if (notion != null)
        {
            this.ensNotions.add(notion);
        }
    }

    public void supprimerNotion(Notion notion)
    {
        this.ensNotions.removeIf(not -> notion.equals(not));
    }

    public static String[] getNomsRessources()
    {
        if (Ressource.listRessource.isEmpty())
        {
            return null;
        }
        String[] nomsRessources = new String[Ressource.listRessource.size()];
        for (int i = 0; i < Ressource.listRessource.size(); i++)
        {
            nomsRessources[i] = Ressource.listRessource.get(i).getNom();
        }
        return nomsRessources;
    }

    public static Ressource trouverRessourceParNom(String nom)
    {
        for (Ressource ressource : Ressource.listRessource)
        {
            if (ressource.getNom().equalsIgnoreCase(nom))
            {
                return ressource;
            }
        }
        return null;
    }
}

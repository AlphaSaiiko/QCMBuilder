package modele;

import controleur.ControleurFichier;
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
    private ControleurFichier fichierControleur;

    /*
     * +--------------+
     * | CONSTRUCTEUR |
     * +--------------+
     */
    public static Ressource creerRessource(String nom)
    {
        Ressource ressource = Metier.trouverRessourceParNom(nom);
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
        this.fichierControleur = new ControleurFichier("lib/ressources/");
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
        this.fichierControleur.ajouterFichier(this.nom);
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
}

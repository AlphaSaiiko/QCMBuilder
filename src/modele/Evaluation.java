package modele;

import java.util.ArrayList;
import java.util.List;

public class Evaluation
{
    /*
     * +------------+
     * | ATTRIBUTS  |
     * +------------+
     */
    private Ressource ressource;
    private Notion notion;
    private List<Question> listeQuestions;
    private String lienEval; // Lien de l'évaluation à mettre sur l'intranet

    /*
     * +--------------+
     * | CONSTRUCTEUR |
     * +--------------+
     */
    public Evaluation(Ressource ressource, Notion notion)
    {
        this.ressource = ressource;
        this.notion = notion;
        this.listeQuestions = new ArrayList<>();
    }

    /*
     * +---------+
     * | GETTERS |
     * +---------+
     */
    public Ressource getRessource()
    {
        return ressource;
    }

    public Notion getNotion()
    {
        return notion;
    }

    public List<Question> getListeQuestions()
    {
        return listeQuestions;
    }

    public String getLienEval()
    {
        return lienEval;
    }

    /*
     * +---------+
     * | SETTERS |
     * +---------+
     */
    public void setRessource(Ressource ressource)
    {
        this.ressource = ressource;
    }

    public void setNotion(Notion notion)
    {
        this.notion = notion;
    }

    public void setLienEval(String lienEval)
    {
        this.lienEval = lienEval;
    }

    /*
     * +----------+
     * | METHODES |
     * +----------+
     */
    public boolean ajouterQuestion(Question qt)
    {
        if (this.listeQuestions != null && qt != null)
        {
            this.listeQuestions.add(qt);
            return true;
        }
        return false;
    }
}

package modele;

import java.util.ArrayList;
import java.util.List;

import controleur.Controleur;

public class Metier 
{
	
    /*
     * +------------+
     * | Attributs  |
     * +------------+
     */

	private Controleur controleur;

	private static List<Evaluation> listEval;

    private static List<Ressource>  listRessource;


    /*
     * +--------------+
     * | Constructeur |
     * +--------------+
     */

	public Metier(Controleur controleur)
	{
		this.controleur = controleur;
		Metier.listEval      = new ArrayList<Evaluation>();
		Metier.listRessource = new ArrayList<Ressource>();
	}

    

}

package controleur;

import java.util.ArrayList;
import java.util.List;

import modele.Evaluation;
import vue.Accueil;

public class Controleur
{

	private static List<Evaluation> listEval;
	private static Accueil    acc ;

	public Controleur()
	{
		Controleur.acc      = new Accueil();
		Controleur.listEval = new ArrayList<Evaluation>();
	}
}
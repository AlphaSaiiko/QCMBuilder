package modele;

import modele.option.*;
import controleur.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

public class CreationHTML {

	private StringBuilder htmlContent = new StringBuilder();
	private ControleurFichier ctrl = new ControleurFichier("");
	private Evaluation evaluation;
	private CreationQuestionHTML questionHTML;


	public CreationHTML(Evaluation evaluation){

		this.ctrl.ajouterFichier("html");

		this.ecrireCSS();

		this.questionHTML = new CreationQuestionHTML(evaluation);

		this.evaluation = evaluation;

		htmlContent.append("<!DOCTYPE html>\n");
		htmlContent.append("<html lang=\"fr\">\n");
		htmlContent.append("<head>\n");
		htmlContent.append("<meta charset=\"UTF-8\">\n");
		htmlContent.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
		if (evaluation.getChrono()){htmlContent.append("<title>Evaluation Chronométrée</title>\n");}
		else {htmlContent.append("<title>Evaluation</title>\n");}
		htmlContent.append("<link rel=\"stylesheet\" href=\"style.css\">");
		htmlContent.append("</head>\n");
		htmlContent.append("<body>\n");

		if(evaluation.getChrono())
		{
			int duree = 0;
			for (Question question : evaluation.getListeQuestions())
			{
				duree += question.getTemps();
			}
			this.pageAccueil(evaluation.getRessource().getNom(), evaluation.getListeNotions(), evaluation.getListeQuestions().size(), duree);
		}
		else
		{
			this.pageAccueil(evaluation.getRessource().getNom(), evaluation.getListeNotions(), evaluation.getListeQuestions().size(), -1);
		}


		htmlContent.append("</body>\n");
		htmlContent.append("</html>\n");

		// Sauvegarde dans un fichier HTML
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/titres.html"))) {
			writer.write(htmlContent.toString());
			System.out.println("Le fichier HTML a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}



		htmlContent = new StringBuilder();
		questionHTML.pageQuestionElimination();
	}

	public void ecrireCSS()
	{
		StringBuilder cssContent = new StringBuilder();

		cssContent.append("/* Réinitialisation générale */\n");
		cssContent.append("* {\n");
		cssContent.append("    margin: 0;\n");
		cssContent.append("    padding: 0;\n");
		cssContent.append("    box-sizing: border-box;\n");
		cssContent.append("}\n\n");
		
		cssContent.append("/* Corps de la page */\n");
		cssContent.append("body {\n");
		cssContent.append("    font-family: Arial, sans-serif;\n");
		cssContent.append("    background-color: #f8f9fa;\n");
		cssContent.append("    display: flex;\n");
		cssContent.append("    flex-direction: column;\n");
		cssContent.append("    align-items: center;\n");
		cssContent.append("    justify-content: center;\n");
		cssContent.append("    min-height: 100vh;\n");
		cssContent.append("    text-align: center;\n");
		cssContent.append("}\n\n");
		
		cssContent.append("/* Titres et paragraphes */\n");
		cssContent.append("h2 {\n");
		cssContent.append("    margin-bottom: 15px;\n");
		cssContent.append("}\n\n");
		
		cssContent.append("p {\n");
		cssContent.append("    margin-bottom: 20px;\n");
		cssContent.append("}\n\n");
		
		cssContent.append("/* Conteneur des colonnes */\n");
		cssContent.append(".association-entitees {\n");
		cssContent.append("    display: flex;\n");
		cssContent.append("    justify-content: center;\n");
		cssContent.append("    gap: 150px;\n");
		cssContent.append("    margin-bottom: 30px;\n");
		cssContent.append("}\n\n");
		
		cssContent.append(".proposition-qcm {\n");
		cssContent.append("    display: flex;\n");
		cssContent.append("    justify-content: center;\n");
		cssContent.append("    gap: 50px;\n");
		cssContent.append("    margin-bottom: 30px;\n");
		cssContent.append("}\n\n");
		
		cssContent.append("/* Colonne et listes */\n");
		cssContent.append(".colonne {\n");
		cssContent.append("    text-align: left;\n");
		cssContent.append("}\n\n");
		
		cssContent.append(".terms,\n");
		cssContent.append(".definitions {\n");
		cssContent.append("    list-style: none;\n");
		cssContent.append("    padding: 0;\n");
		cssContent.append("}\n\n");
		
		cssContent.append(".terms li,\n");
		cssContent.append(".definitions li {\n");
		cssContent.append("    border: 1px solid #ccc;\n");
		cssContent.append("    padding: 10px;\n");
		cssContent.append("    margin: 10px 0;\n");
		cssContent.append("    background-color: #fff;\n");
		cssContent.append("    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\n");
		cssContent.append("}\n\n");
		
		cssContent.append("/* Conteneur des boutons */\n");
		cssContent.append(".button-container button {\n");
		cssContent.append("    padding: 10px 20px;\n");
		cssContent.append("    margin: 5px;\n");
		cssContent.append("    font-size: 16px;\n");
		cssContent.append("    border: none;\n");
		cssContent.append("    background-color: #007BFF;\n");
		cssContent.append("    color: #ffffff;\n");
		cssContent.append("    cursor: pointer;\n");
		cssContent.append("    border-radius: 5px;\n");
		cssContent.append("    transition: background-color 0.3s ease;\n");
		cssContent.append("}\n\n");
		
		cssContent.append(".button-container button:hover {\n");
		cssContent.append("    background-color: #0056b3;\n");
		cssContent.append("}\n\n");
		
		cssContent.append("/* Réinitialisation générale */\n");
		cssContent.append("* {\n");
		cssContent.append("    margin: 0;\n");
		cssContent.append("    padding: 0;\n");
		cssContent.append("    box-sizing: border-box;\n");
		cssContent.append("}\n\n");
		
		cssContent.append("/* Style général du corps */\n");
		cssContent.append("body {\n");
		cssContent.append("    font-family: Arial, sans-serif;\n");
		cssContent.append("    background-color: #f4f4f9;\n");
		cssContent.append("    display: flex;\n");
		cssContent.append("    justify-content: center;\n");
		cssContent.append("    align-items: center;\n");
		cssContent.append("    min-height: 100vh;\n");
		cssContent.append("    text-align: center;\n");
		cssContent.append("}\n");

		cssContent.append("/* Conteneur principal */\n");
		cssContent.append(".container {\n");
		cssContent.append("    background-color: #ffffff;\n");
		cssContent.append("    border: 1px solid #ddd;\n");
		cssContent.append("    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);\n");
		cssContent.append("    border-radius: 10px;\n");
		cssContent.append("    padding: 30px 50px;\n");
		cssContent.append("    max-width: 500px;\n");
		cssContent.append("    width: 90%;\n");
		cssContent.append("}\n\n");

		cssContent.append("/* Titre principal */\n");
		cssContent.append("h1 {\n");
		cssContent.append("    margin-bottom: 20px;\n");
		cssContent.append("    font-size: 28px;\n");
		cssContent.append("    color: #333;\n");
		cssContent.append("}\n\n");

		cssContent.append("/* Paragraphes */\n");
		cssContent.append("p {\n");
		cssContent.append("    margin-bottom: 10px;\n");
		cssContent.append("    font-size: 18px;\n");
		cssContent.append("    color: #555;\n");
		cssContent.append("}\n\n");

		cssContent.append("/* Bouton */\n");
		cssContent.append("button {\n");
		cssContent.append("    margin-top: 20px;\n");
		cssContent.append("    padding: 12px 20px;\n");
		cssContent.append("    font-size: 18px;\n");
		cssContent.append("    background-color: #007BFF;\n");
		cssContent.append("    color: white;\n");
		cssContent.append("    border: none;\n");
		cssContent.append("    border-radius: 5px;\n");
		cssContent.append("    cursor: pointer;\n");
		cssContent.append("    transition: background-color 0.3s ease;\n");
		cssContent.append("}\n\n");

		cssContent.append("button:hover {\n");
		cssContent.append("    background-color: #0056b3;\n");
		cssContent.append("}\n\n");

		cssContent.append("/* Style général */\n");
		cssContent.append("body {\n");
		cssContent.append("    font-family: Arial, sans-serif;\n");
		cssContent.append("}\n\n");

		cssContent.append("h2 {\n");
		cssContent.append("    text-align: center;\n");
		cssContent.append("}\n\n");

		cssContent.append(".association-entitees {\n");
		cssContent.append("    display: flex;\n");
		cssContent.append("    justify-content: center;\n");
		cssContent.append("    position: relative;\n");
		cssContent.append("}\n\n");

		cssContent.append(".colonne {\n");
		cssContent.append("    margin: 0 20px;\n");
		cssContent.append("}\n\n");

		cssContent.append(".terms, .definitions {\n");
		cssContent.append("    list-style-type: none;\n");
		cssContent.append("    padding: 0;\n");
		cssContent.append("}\n\n");

		cssContent.append("li {\n");
		cssContent.append("    padding: 10px;\n");
		cssContent.append("    border: 1px solid #ddd;\n");
		cssContent.append("    cursor: pointer;\n");
		cssContent.append("}\n\n");

		cssContent.append("li.selected {\n");
		cssContent.append("    background-color: #d1e7dd; /* Vert clair */\n");
		cssContent.append("    border-color: #0f5132;\n");
		cssContent.append("}\n\n");

		cssContent.append("li.matched {\n");
		cssContent.append("    background-color: #cfe2ff; /* Bleu clair pour les éléments appariés */\n");
		cssContent.append("    pointer-events: none;\n");
		cssContent.append("}\n\n");

		cssContent.append("#connection-lines {\n");
		cssContent.append("    position: absolute;\n");
		cssContent.append("    top: 0;\n");
		cssContent.append("    left: 0;\n");
		cssContent.append("    width: 100%;\n");
		cssContent.append("    height: 100%;\n");
		cssContent.append("    pointer-events: none; /* Empêche les clics sur le SVG */\n");
		cssContent.append("}\n");

		


		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/style.css"))) {
			writer.write(cssContent.toString());
			System.out.println("Le fichier CSS a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void pageAccueil(String ressource, List<Notion> notion, int nbQuestion, int duree)
	{
		if (this.evaluation.getChrono())
		{
			htmlContent.append("\t<div class=\"container\">");
			htmlContent.append("\t\t<h1>Évaluation chronométrée</h1>");
		}
		else
		{
			htmlContent.append("\t<div class=\"container\">");
			htmlContent.append("\t\t<h1>Évaluation</h1>");
		}
		htmlContent.append("\t\t<p><strong>Ressource :</strong> "+ressource+"</p>");
		for (Notion n : notion)
		{
			htmlContent.append("\t\t<p><strong>Notion(s) :</strong> "+n.getNom()+"</p>");
		}
		htmlContent.append("\t\t<p><strong>Nombre de questions :</strong> 21 dont 11 <b>F</b> et 10 <b>M</b></p>");
		if (duree >= 0){
			if (duree>60)
			{
				int min = duree/60;
				int sec = duree%60;
				htmlContent.append("\t\t<p><strong>Durée totale prévue :</strong> "+min+"min "+sec+"sec </p>");
			}
			else
			{
				htmlContent.append("\t\t<p><strong>Durée totale prévue :</strong> "+duree+"sec </p>");
			}
		}
		htmlContent.append("\t<button onclick=\"location.href='page1.html';\">Commencer l'évaluation</button>");
		htmlContent.append("\t</div>");
	}
}

package modele;

import modele.option.*;
import controleur.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

public class CreationQuestionHTML {

	private Evaluation evaluation;

	public CreationQuestionHTML(Evaluation evaluation){this.evaluation = evaluation;}

	public void pageQuestionElimination()
	{

		StringBuilder htmlContent = new StringBuilder();

		htmlContent.append("<!DOCTYPE html>\n");
		htmlContent.append("<html lang=\"fr\">\n");
		htmlContent.append("<head>\n");
		htmlContent.append("<meta charset=\"UTF-8\">\n");
		htmlContent.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
		htmlContent.append("<title>Question 1</title>\n");
		htmlContent.append("<link rel=\"stylesheet\" href=\"style.css\">");
		htmlContent.append("</head>\n");
		htmlContent.append("<body>\n");

		htmlContent.append("\t<h2>Question 1/21 <b>F</b> Bases de l'économie</h2>");
		htmlContent.append("\t<p id=\"timer\"></p>");
		htmlContent.append("\t<script src=\"timer.js\"></script>");
		htmlContent.append("\t<p>Associez la bonne définition au mot :</p>");

		htmlContent.append("\t<div class=\"propositon-qcm\">");
		htmlContent.append("\t\t<div class=\"colonne\">");
		htmlContent.append("\t\t\t<ul class=\"terms\">");
		htmlContent.append("\t\t\t\t<li>Microéconomie</li>");
		htmlContent.append("\t\t\t\t<li>Macroéconomie</li>");
		htmlContent.append("\t\t\t</ul>");
		htmlContent.append("\t\t</div>");
		htmlContent.append("\t\t<div class=\"colonne\">");
		htmlContent.append("\t\t\t<ul class=\"definitions\">");
		htmlContent.append("\t\t\t\t<li>S'intéresse aux interactions entre vendeurs et acheteurs</li>");
		htmlContent.append("\t\t\t\t<li>S'attache aux principaux indicateurs économiques</li>");
		htmlContent.append("\t\t\t</ul>");
		htmlContent.append("\t\t</div>");
		htmlContent.append("\t</div>");

		htmlContent.append("\t<div class=\"button-container\">");
		htmlContent.append("\t\t<button>Précédent</button>");
		htmlContent.append("\t\t<button>Feedback</button>");
		htmlContent.append("\t\t<button>Valider</button>");
		htmlContent.append("\t\t<button>Suivant</button>");
		htmlContent.append("\t</div>");

		htmlContent.append("</body>\n");
		htmlContent.append("</html>\n");

		// Sauvegarde dans un fichier HTML
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/page1.html"))) {
			writer.write(htmlContent.toString());
			System.out.println("Le fichier HTML a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	private void ecrireTimer(int duree)
	{
		StringBuilder jsContent = new StringBuilder();
		jsContent.append("let temps = "+duree+";");
		jsContent.append("");
		jsContent.append("const timerElement = document.getElementById(\"timer\");");
		jsContent.append("");
		jsContent.append("setInterval(() => {");
		jsContent.append("  let minutes = parseInt(temps / 60, 10);");
		jsContent.append("  let secondes = parseInt(temps % 60, 10);");
		jsContent.append("");
		jsContent.append("  minutes = minutes < 10 ? \"0\" + minutes : minutes;");
		jsContent.append("  secondes = secondes < 10 ? \"0\" + secondes : secondes;");
		jsContent.append("");
		jsContent.append("  timerElement.innerText = `Compte à rebours : ${minutes}:${secondes}`;");
		jsContent.append("  temps = temps <= 0 ? 0 : temps - 1;");
		jsContent.append("}, 1000);");

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/timer.js"))) {
			writer.write(jsContent.toString());
			System.out.println("Le fichier js a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
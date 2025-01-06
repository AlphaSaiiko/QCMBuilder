package modele;

import controleur.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CreationHTML {

	private StringBuilder htmlContent = new StringBuilder();
	private ControleurFichier ctrl = new ControleurFichier("");
	private Evaluation evaluation;
	private CreationQuestionHTML questionHTML;
	private int numQuestion;


	public CreationHTML(Evaluation evaluation){

		this.ctrl.ajouterFichier("html");

		this.ecrireCSS();

		this.questionHTML = new CreationQuestionHTML(evaluation);

		this.evaluation = evaluation;
		this.numQuestion = 1;

		this.questionHTML.ecrireCSSAssociation();
		this.questionHTML.ecrireCSSElimination();
		this.questionHTML.ecrireCSSReponseUnique();

		this.questionHTML.ecrireJsAssociation();
		this.questionHTML.ecrireJsElimination();
		this.questionHTML.ecrireJsReponseMultiple();
		this.questionHTML.ecrireJsReponseUnique();
		this.questionHTML.ecrireJsTimer();

		System.out.println("liste question : " + this.evaluation.getListeQuestions()) ;

		htmlContent.append("<!DOCTYPE html>\n");
		htmlContent.append("<html lang=\"fr\">\n");
		htmlContent.append("<head>\n");
		htmlContent.append("<meta charset=\"UTF-8\">\n");
		htmlContent.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
		if (evaluation.getChrono()){htmlContent.append("<title>Evaluation Chronométrée</title>\n");}
		else {htmlContent.append("<title>Evaluation</title>\n");}
		htmlContent.append("<link rel=\"stylesheet\" href=\"style.css\">");
		htmlContent.append("<script>\n");
		htmlContent.append("    window.onload = function() {\n");
		htmlContent.append("        // Réinitialiser toutes les sauvegardes du localStorage\n");
		htmlContent.append("        localStorage.clear();\n");
		htmlContent.append("    };\n");
		htmlContent.append("</script>\n");
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
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/pageDAccueil.html"))) {
			writer.write(htmlContent.toString());
			System.out.println("Le fichier HTML a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Question question : this.evaluation.getListeQuestions())
		{
			if (question.getType().equals("QAEPR")){this.questionHTML.pageQuestionElimination(question, this.numQuestion); this.numQuestion++;}
			if (question.getType().equals("QCMRU")){this.questionHTML.pageQuestionUnique(question, this.numQuestion); this.numQuestion++;}
			if (question.getType().equals("QCMRM")){this.questionHTML.pageQuestionMultiple(question, this.numQuestion); this.numQuestion++;}
			if (question.getType().equals("QAE")){this.questionHTML.pageQuestionChoixAssociation(question, this.numQuestion); this.numQuestion++;}
		}

		this.pageDeFin();


	}

	public void ecrireCSS()
	{
		StringBuilder cssContent = new StringBuilder();

		cssContent.append("body {").append("\n");
		cssContent.append("    font-family: Arial, sans-serif;").append("\n");
		cssContent.append("    background-color: #f4f4f9;").append("\n");
		cssContent.append("    margin: 0;").append("\n");
		cssContent.append("    padding: 0;").append("\n");
		cssContent.append("    display: flex;").append("\n");
		cssContent.append("    justify-content: center;").append("\n");
		cssContent.append("    align-items: center;").append("\n");
		cssContent.append("    height: 100vh;").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".container {").append("\n");
		cssContent.append("    background-color: #fff;").append("\n");
		cssContent.append("    padding: 20px;").append("\n");
		cssContent.append("    border-radius: 10px;").append("\n");
		cssContent.append("    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);").append("\n");
		cssContent.append("    width: 80%;").append("\n");
		cssContent.append("    max-width: 1000px;").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append("h1 {").append("\n");
		cssContent.append("    color: #333;").append("\n");
		cssContent.append("    margin-bottom: 10px;").append("\n");
		cssContent.append("    display: flex;").append("\n");
		cssContent.append("    align-items: center;").append("\n");
		cssContent.append("    justify-content: center;").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".qcm-type {").append("\n");
		cssContent.append("    color: #666;").append("\n");
		cssContent.append("    font-size: 14px;").append("\n");
		cssContent.append("    margin-bottom: 20px;").append("\n");
		cssContent.append("    display: flex;").append("\n");
		cssContent.append("    justify-content: center;").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".content {").append("\n");
		cssContent.append("    display: flex;").append("\n");
		cssContent.append("    justify-content: space-between;").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".sujet,").append("\n");
		cssContent.append(".proposition {").append("\n");
		cssContent.append("    width: 48%;").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".rectangle {").append("\n");
		cssContent.append("    width: 100%;").append("\n");
		cssContent.append("    height: 50px;").append("\n");
		cssContent.append("    background-color: grey;").append("\n");
		cssContent.append("    margin: 10px 0;").append("\n");
		cssContent.append("    cursor: pointer;").append("\n");
		cssContent.append("    display: flex;").append("\n");
		cssContent.append("    align-items: center;").append("\n");
		cssContent.append("    justify-content: center;").append("\n");
		cssContent.append("    text-align: center;").append("\n");
		cssContent.append("    border-radius: 10px;").append("\n");
		cssContent.append("    transition: background-color 0.3s, transform 0.3s;").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".rectangle:hover {").append("\n");
		cssContent.append("    transform: scale(1.05);").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".buttons-container {").append("\n");
		cssContent.append("    display: flex;").append("\n");
		cssContent.append("    justify-content: center;").append("\n");
		cssContent.append("    margin-top: 20px;").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".buttons-container button {").append("\n");
		cssContent.append("    padding: 10px 20px;").append("\n");
		cssContent.append("    font-size: 16px;").append("\n");
		cssContent.append("    cursor: pointer;").append("\n");
		cssContent.append("    border: none;").append("\n");
		cssContent.append("    border-radius: 5px;").append("\n");
		cssContent.append("    background-color: #007bff;").append("\n");
		cssContent.append("    color: white;").append("\n");
		cssContent.append("    transition: background-color 0.3s, transform 0.3s;").append("\n");
		cssContent.append("    margin: 0 10px;").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".buttons-container button:hover {").append("\n");
		cssContent.append("    background-color: #0056b3;").append("\n");
		cssContent.append("    transform: scale(1.05);").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".buttons-container button:active {").append("\n");
		cssContent.append("    background-color: #004494;").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".tresfacile {").append("\n");
		cssContent.append("    box-shadow: 0 4px 8px rgba(0, 255, 0, 0.5);").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".facile {").append("\n");
		cssContent.append("    box-shadow: 0 4px 8px rgba(255, 255, 0, 0.5);").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".moyen {").append("\n");
		cssContent.append("    box-shadow: 0 4px 8px rgba(255, 0, 0, 0.5);").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".difficile {").append("\n");
		cssContent.append("    box-shadow: grey;").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".corp-titre {").append("\n");
		cssContent.append("    background-color: #fff;").append("\n");
		cssContent.append("    padding: 20px;").append("\n");
		cssContent.append("    border-radius: 10px;").append("\n");
		cssContent.append("    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);").append("\n");
		cssContent.append("    width: 80%;").append("\n");
		cssContent.append("    max-width: 500px;").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".corp-titre p {").append("\n");
		cssContent.append("    display: flex;").append("\n");
		cssContent.append("    align-items: center;").append("\n");
		cssContent.append("    justify-content: center;").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".corp-titre span {").append("\n");
		cssContent.append("    font-weight: bold;").append("\n");
		cssContent.append("    display: flex;").append("\n");
		cssContent.append("    padding: 10px;").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".cercleFacile {").append("\n");
		cssContent.append("    position: relative;").append("\n");
		cssContent.append("    display: inline-block;").append("\n");
		cssContent.append("    background-color: blue;").append("\n");
		cssContent.append("    width: .5em;").append("\n");
		cssContent.append("    height: .5em;").append("\n");
		cssContent.append("    border-radius: 100%;").append("\n");
		cssContent.append("    display: flex;").append("\n");
		cssContent.append("    align-items: center;").append("\n");
		cssContent.append("    margin: 0 5px;").append("\n");
		cssContent.append("    justify-content: center;").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".cercleMoyen {").append("\n");
		cssContent.append("    position: relative;").append("\n");
		cssContent.append("    display: inline-block;").append("\n");
		cssContent.append("    background-color: red;").append("\n");
		cssContent.append("    width: .5em;").append("\n");
		cssContent.append("    height: .5em;").append("\n");
		cssContent.append("    border-radius: 100%;").append("\n");
		cssContent.append("    display: flex;").append("\n");
		cssContent.append("    align-items: center;").append("\n");
		cssContent.append("    margin: 0 5px;").append("\n");
		cssContent.append("    justify-content: center;").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".cercleTresFacile {").append("\n");
		cssContent.append("    position: relative;").append("\n");
		cssContent.append("    display: inline-block;").append("\n");
		cssContent.append("    background-color: green;").append("\n");
		cssContent.append("    width: .5em;").append("\n");
		cssContent.append("    height: .5em;").append("\n");
		cssContent.append("    border-radius: 100%;").append("\n");
		cssContent.append("    display: flex;").append("\n");
		cssContent.append("    align-items: center;").append("\n");
		cssContent.append("    margin: 0 5px;").append("\n");
		cssContent.append("    justify-content: center;").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".cercleDifficile {").append("\n");
		cssContent.append("    position: relative;").append("\n");
		cssContent.append("    display: inline-block;").append("\n");
		cssContent.append("    background-color: grey;").append("\n");
		cssContent.append("    width: .5em;").append("\n");
		cssContent.append("    height: .5em;").append("\n");
		cssContent.append("    border-radius: 100%;").append("\n");
		cssContent.append("    display: flex;").append("\n");
		cssContent.append("    align-items: center;").append("\n");
		cssContent.append("    margin: 0 5px;").append("\n");
		cssContent.append("    justify-content: center;").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".boutonDiv {").append("\n");
		cssContent.append("    display: flex;").append("\n");
		cssContent.append("    justify-content: center;").append("\n");
		cssContent.append("    align-items: center;").append("\n");
		cssContent.append("}").append("\n");

		cssContent.append(".corp-titre button {").append("\n");
		cssContent.append("    padding: 10px 20px;").append("\n");
		cssContent.append("    font-size: 16px;").append("\n");
		cssContent.append("    cursor: pointer;").append("\n");
		cssContent.append("    border: none;").append("\n");
		cssContent.append("    border-radius: 5px;").append("\n");
		cssContent.append("    background-color: #007bff;").append("\n");
		cssContent.append("    color: white;").append("\n");
		cssContent.append("    transition: background-color 0.3s, transform 0.3s;").append("\n");
		cssContent.append("    margin: 0 10px;").append("\n");
		cssContent.append("}").append("\n");
		


		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/style.css"))) {
			writer.write(cssContent.toString());
			System.out.println("Le fichier CSS a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void pageAccueil(String ressource, List<Notion> notion, int nbQuestion, int duree)
	{
		int tf, f, d, m;

		tf = f = d =m = 0;

		tf = this.evaluation.getQuestionTresFacile();
		f = this.evaluation.getQuestionFacile();
		m = this.evaluation.getQuestionMoyen();
		d = this.evaluation.getQuestionDifficile();


		if (this.evaluation.getChrono())
		{
			htmlContent.append("\t<div class=\"container\">").append("\n");
			htmlContent.append("\t\t<h1>Évaluation chronométrée</h1>").append("\n");
		}
		else
		{
			htmlContent.append("\t<div class=\"container\">").append("\n");
			htmlContent.append("\t\t<h1>Évaluation</h1>").append("\n");
		}
		htmlContent.append("\t\t<p><strong>Ressource :</strong> "+ressource+"</p>").append("\n");
		for (Notion n : notion)
		{
			htmlContent.append("\t\t<p><strong>Notion(s) :</strong> "+n.getNom()+"</p>").append("\n");
		}
		htmlContent.append("\t\t<p><strong>Nombre de questions :</strong> "+(tf+f+m+d)+" dont ");
		if (tf>0){htmlContent.append("\t\t "+tf+" Très Facile  ");}
		if (f>0){htmlContent.append("\t\t  "+f+" Facile  ");}
		if (m>0){htmlContent.append("\t\t  "+m+" Moyenne  ");}
		if (d>0){htmlContent.append("\t\t  "+d+" Difficile  ");}
		htmlContent.append("</p>");
		if (duree >= 0){
			if (duree>60)
			{
				int min = duree/60;
				int sec = duree%60;
				htmlContent.append("\t\t<p><strong>Durée totale prévue :</strong> "+min+"min "+sec+"sec </p>").append("\n");
			}
			else
			{
				htmlContent.append("\t\t<p><strong>Durée totale prévue :</strong> "+duree+"sec </p>").append("\n");
			}
		}
		htmlContent.append("\t<button onclick=\"location.href='page1.html';\">Commencer l'évaluation</button>").append("\n");
		htmlContent.append("\t</div>").append("\n");
	}

	public void pageDeFin()
	{
		StringBuilder htmlContent = new StringBuilder();

		int tf, f, d, m;

		tf = f = d =m = 0;

		tf = this.evaluation.getQuestionTresFacile();
		f = this.evaluation.getQuestionFacile();
		m = this.evaluation.getQuestionMoyen();
		d = this.evaluation.getQuestionDifficile();



		htmlContent.append("<!DOCTYPE html>").append("\n");
		htmlContent.append("<html lang=\"fr\">").append("\n");
		htmlContent.append("<head>").append("\n");
		htmlContent.append("    <meta charset=\"UTF-8\">").append("\n");
		htmlContent.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">").append("\n");
		htmlContent.append("    <title>Evaluation</title>").append("\n");
		htmlContent.append("    <link rel=\"stylesheet\" href=\"style.css\">").append("\n");
		htmlContent.append("</head>").append("\n");
		htmlContent.append("<body>").append("\n");
		htmlContent.append("    <div class=\"corp-titre\">").append("\n");
		htmlContent.append("        <h1>Évaluation Terminée ! </h1>").append("\n");
		htmlContent.append("        <p> <span class=\"bold-texte\"> Ressource : </span> "+ this.evaluation.getRessource().getNom() + "</p>").append("\n");

		for (Notion n : this.evaluation.getListeNotions())
		{
			htmlContent.append("        <p><span class=\"bold-texte\"> Notion(s) : </span> "+n.getNom()+"</p>").append("\n");
		}
		htmlContent.append("        <p> <span class=\"bold-texte\"> Nombre de questions : </span> "+this.evaluation.getNbQuestion()+" dont ");
		if (tf>0){htmlContent.append(tf+"<span class=\"cercleTresFacile\">TF</span> ");}
		if (f>0){htmlContent.append(f+"<span class=\"cercleFacile\">F</span> ");}
		if (m>0){htmlContent.append(m+"<span class=\"cercleMoyen\">M</span> ");}
		if (d>0){htmlContent.append(d+"<span class=\"cercleDifficile\">D</span> ");}
		htmlContent.append("</p> \n");
		htmlContent.append("        <p> <span class=\"bold-texte\">Score global : </span> <span id=\"score\"></span>/"+this.evaluation.getNbPoints()+"</p>").append("\n");
		htmlContent.append("    </div>").append("\n");
		htmlContent.append("</body>").append("\n");
		htmlContent.append("<script>").append("\n");
		htmlContent.append("    // Fonction pour mettre à jour le score global").append("\n");
		htmlContent.append("    function updateScore() {").append("\n");
		htmlContent.append("        const totalPoints = localStorage.getItem('points') ? parseFloat(localStorage.getItem('points')) : 0;").append("\n");
		htmlContent.append("        document.getElementById('score').textContent = totalPoints.toFixed(2);").append("\n"); // Affiche jusqu'à 2 chiffres après la virgule
		htmlContent.append("    }").append("\n");
		htmlContent.append("    // Appeler la fonction pour mettre à jour le score lorsque la page se charge").append("\n");
		htmlContent.append("    document.addEventListener('DOMContentLoaded', updateScore);").append("\n");
		htmlContent.append("</script>").append("\n");
		htmlContent.append("</html>").append("\n");



		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/pageDeFin.html"))) {
			writer.write(htmlContent.toString());
			System.out.println("Le fichier HTML a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package modele;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import modele.option.*;

public class CreationQuestionHTML {

	private Evaluation evaluation;
	private int nbQuestion;

	public CreationQuestionHTML(Evaluation evaluation){
		this.evaluation = evaluation;
	}


    public void pageQuestionChoixAssociation(Question question, int numQuestion) {
		StringBuilder htmlContent = new StringBuilder();

		String pageSuivante;
		if (numQuestion < this.evaluation.getNbQuestion()){pageSuivante = "page"+(numQuestion+1)+"";}
		else {pageSuivante = "pageDeFin";}
	
		htmlContent.append("<!DOCTYPE html>").append("\n");
		htmlContent.append("<html lang=\"fr\">").append("\n");
		htmlContent.append("<head>").append("\n");
		htmlContent.append("    <meta charset=\"UTF-8\">").append("\n");
		htmlContent.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">").append("\n");
		htmlContent.append("    <title>Association de Mots</title>").append("\n");
		htmlContent.append("    <link rel=\"stylesheet\" href=\"styleAssociation.css\">").append("\n");
		htmlContent.append("</head>").append("\n");
		htmlContent.append("<body>").append("\n");
		htmlContent.append("    <div class=\"container\">").append("\n");
		htmlContent.append("        <h1>").append(question.getEnonce()).append("</h1>").append("\n");
		htmlContent.append("        <div class=\"columns\">").append("\n");
	
		List<IOption> banqueDeReponses = question.getEnsOptions();
		int id = 1; // Initialiser l'ID à 1 pour les mots et les définitions
	
		htmlContent.append("            <div class=\"words\" id=\"words\">\n");
		for (IOption reponse : banqueDeReponses) {
			if (reponse.getId() % 2 == 1) { // Vérifie si l'ID est impair (mot)
				htmlContent.append("                <div class=\"word\" data-id=\"").append(id).append("\">")
						  .append(reponse.getEnonce()).append("</div>\n");
				id++; // Incrémenter l'ID après chaque ajout
			}
		}
		htmlContent.append("            </div>\n");
		htmlContent.append("            <svg id=\"svg-container\"></svg>\n");
		
		id = 1; // Réinitialiser l'ID à 1 pour les définitions
		htmlContent.append("            <div class=\"definitions\" id=\"definitions\">\n");
		for (IOption reponse : banqueDeReponses) {
			if (reponse.getId() % 2 == 0) { // Vérifie si l'ID est pair (définition)
				htmlContent.append("                <div class=\"definition\" data-id=\"").append(id).append("\">")
						  .append(reponse.getEnonce()).append("</div>\n");
				id++; // Incrémenter l'ID après chaque ajout
			}
		}
		htmlContent.append("            </div>\n");
		htmlContent.append("        </div>\n");
		htmlContent.append("        <button onclick=\"validate()\">Valider</button>").append("\n");
		htmlContent.append("        <button onclick=\"location.href='"+pageSuivante+".html';\">Suivant</button>").append("\n");
		htmlContent.append("    </div>").append("\n");
		htmlContent.append("    <script src=\"scriptAssociation.js\"></script>").append("\n");
		htmlContent.append("</body>").append("\n");
		htmlContent.append("</html>").append("\n");
	
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/page" + numQuestion + ".html"))) {
			writer.write(htmlContent.toString());
			System.out.println("Le fichier HTML a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	public void pageQuestionElimination(Question question, int numQuestion)
	{
		StringBuilder htmlContent = new StringBuilder();

		String pageSuivante;
		if (numQuestion < this.evaluation.getNbQuestion()){pageSuivante = "page"+(numQuestion+1)+"";}
		else {pageSuivante = "pageDeFin";}

		htmlContent.append("<!DOCTYPE html>").append("\n");
		htmlContent.append("<html lang=\"fr\">").append("\n");
		htmlContent.append("<head>").append("\n");
		htmlContent.append("    <meta charset=\"UTF-8\">").append("\n");
		htmlContent.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">").append("\n");
		htmlContent.append("    <title>Question à Élimination</title>").append("\n");
		htmlContent.append("    <link rel=\"stylesheet\" href=\"styleElimination.css\">").append("\n");
		htmlContent.append("</head>").append("\n");
		htmlContent.append("<body>").append("\n");
		htmlContent.append("    <div class=\"container\">").append("\n");
		htmlContent.append("        <h1>").append(question.getEnonce()).append("</h1>").append("\n");
		htmlContent.append("        <div class=\"question\" id=\"question\">").append("\n");
		for (IOption reponse : question.getEnsOptions())
		{
			if (reponse instanceof OptionElimination)
			{
				OptionElimination opt = (OptionElimination) reponse;
				if (opt.getOrdre() != -1)
				{
					htmlContent.append("            <div class=\"reponse mauvaise-reponse\" data-order=\"").append(opt.getOrdre()).append("\">").append(opt.getEnonce()).append("</div>").append("\n");
				}
				else if (opt.getEstReponse())
				{
					htmlContent.append("            <div class=\"reponse bonne-reponse\">").append(opt.getEnonce()).append("</div>").append("\n");
				}
				else
				{
					htmlContent.append("            <div class=\"reponse mauvaise-reponse\">").append(opt.getEnonce()).append("</div>").append("\n");
				}
			}
		}
		htmlContent.append("        </div>").append("\n");
		htmlContent.append("        <button id=\"eliminar\">Éliminer</button>").append("\n");
		htmlContent.append("        <button id=\"valider\">Valider</button>").append("\n");
		htmlContent.append("        <button onclick=\"location.href='"+pageSuivante+".html';\">Suivant</button>").append("\n");
		htmlContent.append("    </div>").append("\n");
		htmlContent.append("    <script src=\"scriptElimination.js\"></script>").append("\n");
		htmlContent.append("</body>").append("\n");
		htmlContent.append("</html>").append("\n");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/page" + numQuestion + ".html"))) {
			writer.write(htmlContent.toString());
			System.out.println("Le fichier js a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void pageQuestionUnique (Question question, int numQuestion)
	{
		StringBuilder htmlContent = new StringBuilder();

		String pageSuivante;
		if (numQuestion < this.evaluation.getNbQuestion()){pageSuivante = "page"+(numQuestion+1)+"";}
		else {pageSuivante = "pageDeFin";}

		htmlContent.append("<!DOCTYPE html>").append("\n");
		htmlContent.append("<html lang=\"fr\">").append("\n");
		htmlContent.append("<head>").append("\n");
		htmlContent.append("    <meta charset=\"UTF-8\">").append("\n");
		htmlContent.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">").append("\n");
		htmlContent.append("    <title>Question à Réponse Unique</title>").append("\n");
		htmlContent.append("    <link rel=\"stylesheet\" href=\"styleReponseUnique.css\">").append("\n");
		htmlContent.append("</head>").append("\n");
		htmlContent.append("<body>").append("\n");
		htmlContent.append("    <div class=\"container\">").append("\n");
		htmlContent.append("        <h1>").append(question.getEnonce()).append("</h1>").append("\n");
		htmlContent.append("        <div class=\"question\" id=\"question\">").append("\n");
		for (IOption reponse : question.getEnsOptions()) {
			if (reponse instanceof Option)
			{
				Option opt = (Option) reponse;
				if (opt.getEstReponse())
				{
					htmlContent.append("            <div class=\"reponse bonne-reponse\">").append(opt.getEnonce()).append("</div>").append("\n");
				}
				else
				{
					htmlContent.append("            <div class=\"reponse mauvaise-reponse\">").append(opt.getEnonce()).append("</div>").append("\n");
				}
			}
			
		}
		htmlContent.append("        </div>").append("\n");
		htmlContent.append("        <button id=\"valider\">Valider</button>").append("\n");
		htmlContent.append("        <button onclick=\"location.href='"+pageSuivante+".html';\">Suivant</button>").append("\n");
		htmlContent.append("    </div>").append("\n");
		htmlContent.append("    <script src=\"scriptReponseUnique.js\"></script>").append("\n");
		htmlContent.append("</body>").append("\n");
		htmlContent.append("</html>").append("\n");

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/page" + numQuestion + ".html"))) {
			writer.write(htmlContent.toString());
			System.out.println("Le fichier js a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void pageQuestionMultiple(Question question, int numQuestion)
	{
		StringBuilder htmlContent = new StringBuilder();

		String pageSuivante;
		if (numQuestion < this.evaluation.getNbQuestion()){pageSuivante = "page"+(numQuestion+1)+"";}
		else {pageSuivante = "pageDeFin";}

		htmlContent.append("<!DOCTYPE html>").append("\n");
		htmlContent.append("<html lang=\"fr\">").append("\n");
		htmlContent.append("<head>").append("\n");
		htmlContent.append("    <meta charset=\"UTF-8\">").append("\n");
		htmlContent.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">").append("\n");
		htmlContent.append("    <title>Question à Réponses Multiples</title>").append("\n");
		htmlContent.append("    <link rel=\"stylesheet\" href=\"styleReponseUnique.css\">").append("\n");
		htmlContent.append("</head>").append("\n");
		htmlContent.append("<body>").append("\n");
		htmlContent.append("    <div class=\"container\">").append("\n");
		htmlContent.append("        <h1>").append(question.getEnonce()).append("</h1>").append("\n");
		htmlContent.append("        <div class=\"question\" id=\"question\">").append("\n");
		for (IOption reponse : question.getEnsOptions())
		{
			if (reponse instanceof Option)
			{
				Option opt = (Option) reponse;
				if (opt.getEstReponse())
				{
					htmlContent.append("            <div class=\"reponse bonne-reponse\">").append(opt.getEnonce()).append("</div>").append("\n");
				}
				else
				{
					htmlContent.append("            <div class=\"reponse mauvaise-reponse\">").append(opt.getEnonce()).append("</div>").append("\n");
				}
			}
		}
		htmlContent.append("        </div>").append("\n");
		htmlContent.append("        <button id=\"valider\">Valider</button>").append("\n");
		htmlContent.append("        <button onclick=\"location.href='"+pageSuivante+".html';\">Suivant</button>").append("\n");
		htmlContent.append("    </div>").append("\n");
		htmlContent.append("    <script src=\"scriptReponseMultiple.js\"></script>").append("\n");
		htmlContent.append("</body>").append("\n");
		htmlContent.append("</html>").append("\n");

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/page" + numQuestion + ".html"))) {
			writer.write(htmlContent.toString());
			System.out.println("Le fichier html a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void ecrireJsReponseMultiple()
	{
		StringBuilder jsContent = new StringBuilder();

		jsContent.append("document.addEventListener('DOMContentLoaded', () => {").append("\n");
		jsContent.append("    // Mélanger les réponses (au cas où)").append("\n");
		jsContent.append("    randomizeOrder('.reponse', '#question');").append("\n");
		jsContent.append("\n");
		jsContent.append("    // Gérer la sélection des réponses").append("\n");
		jsContent.append("    let selectedAnswers = new Set();").append("\n");
		jsContent.append("    document.querySelectorAll('.reponse').forEach(reponse => {").append("\n");
		jsContent.append("        reponse.addEventListener('click', () => {").append("\n");
		jsContent.append("            if (selectedAnswers.has(reponse)) {").append("\n");
		jsContent.append("                reponse.classList.remove('selected');").append("\n");
		jsContent.append("                selectedAnswers.delete(reponse);").append("\n");
		jsContent.append("            } else {").append("\n");
		jsContent.append("                reponse.classList.add('selected');").append("\n");
		jsContent.append("                selectedAnswers.add(reponse);").append("\n");
		jsContent.append("            }").append("\n");
		jsContent.append("        });").append("\n");
		jsContent.append("    });").append("\n");
		jsContent.append("\n");
		jsContent.append("    // Valider les réponses sélectionnées").append("\n");
		jsContent.append("    document.getElementById('valider').addEventListener('click', () => {").append("\n");
		jsContent.append("        const bonnesReponses = document.querySelectorAll('.bonne-reponse');").append("\n");
		jsContent.append("        const mauvaisesReponses = document.querySelectorAll('.mauvaise-reponse');").append("\n");
		jsContent.append("\n");
		jsContent.append("        let allCorrect = true;").append("\n");
		jsContent.append("\n");
		jsContent.append("        // Vérifie si toutes les bonnes réponses sont sélectionnées").append("\n");
		jsContent.append("        bonnesReponses.forEach(reponse => {").append("\n");
		jsContent.append("            if (!selectedAnswers.has(reponse)) {").append("\n");
		jsContent.append("                allCorrect = false;").append("\n");
		jsContent.append("            }").append("\n");
		jsContent.append("        });").append("\n");
		jsContent.append("\n");
		jsContent.append("        // Vérifie qu'aucune mauvaise réponse n'est sélectionnée").append("\n");
		jsContent.append("        mauvaisesReponses.forEach(reponse => {").append("\n");
		jsContent.append("            if (selectedAnswers.has(reponse)) {").append("\n");
		jsContent.append("                allCorrect = false;").append("\n");
		jsContent.append("            }").append("\n");
		jsContent.append("        });").append("\n");
		jsContent.append("\n");
		jsContent.append("        if (allCorrect) {").append("\n");
		jsContent.append("            alert('Bravo! Vous avez sélectionné toutes les bonnes réponses.');").append("\n");
		jsContent.append("        } else {").append("\n");
		jsContent.append("            alert('Désolé, certaines réponses sélectionnées sont incorrectes.');").append("\n");
		jsContent.append("        }").append("\n");
		jsContent.append("    });").append("\n");
		jsContent.append("});").append("\n");
		jsContent.append("\n");
		jsContent.append("function randomizeOrder(selector, parentSelector) {").append("\n");
		jsContent.append("    const items = document.querySelectorAll(selector);").append("\n");
		jsContent.append("    const parent = document.querySelector(parentSelector);").append("\n");
		jsContent.append("    const shuffledItems = Array.from(items).sort(() => Math.random() - 0.5);").append("\n");
		jsContent.append("\n");
		jsContent.append("    shuffledItems.forEach(item => parent.appendChild(item));").append("\n");
		jsContent.append("}").append("\n");

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/scriptReponseMultiple.js"))) {
			writer.write(jsContent.toString());
			System.out.println("Le fichier html a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ecrireJsReponseUnique()
	{
		StringBuilder jsContent = new StringBuilder();
		jsContent.append("document.addEventListener('DOMContentLoaded', () => {").append("\n");
		jsContent.append("    // Mélanger les réponses (au cas où)").append("\n");
		jsContent.append("    randomizeOrder('.reponse', '#question');").append("\n");
		jsContent.append("\n");
		jsContent.append("    // Gérer la sélection des réponses").append("\n");
		jsContent.append("    let selectedAnswer = null;").append("\n");
		jsContent.append("    document.querySelectorAll('.reponse').forEach(reponse => {").append("\n");
		jsContent.append("        reponse.addEventListener('click', () => {").append("\n");
		jsContent.append("            if (selectedAnswer) {").append("\n");
		jsContent.append("                selectedAnswer.classList.remove('selected');").append("\n");
		jsContent.append("            }").append("\n");
		jsContent.append("            reponse.classList.add('selected');").append("\n");
		jsContent.append("            selectedAnswer = reponse;").append("\n");
		jsContent.append("        });").append("\n");
		jsContent.append("    });").append("\n");
		jsContent.append("\n");
		jsContent.append("    // Valider la réponse sélectionnée").append("\n");
		jsContent.append("    document.getElementById('valider').addEventListener('click', () => {").append("\n");
		jsContent.append("        if (selectedAnswer) {").append("\n");
		jsContent.append("            if (selectedAnswer.classList.contains('bonne-reponse')) {").append("\n");
		jsContent.append("                alert('Bravo! Vous avez trouvé la bonne réponse.');").append("\n");
		jsContent.append("            } else {").append("\n");
		jsContent.append("                alert('Désolé, la réponse sélectionnée est incorrecte.');").append("\n");
		jsContent.append("            }").append("\n");
		jsContent.append("        } else {").append("\n");
		jsContent.append("            alert('Veuillez sélectionner une réponse.');").append("\n");
		jsContent.append("        }").append("\n");
		jsContent.append("    });").append("\n");
		jsContent.append("});").append("\n");
		jsContent.append("\n");
		jsContent.append("function randomizeOrder(selector, parentSelector) {").append("\n");
		jsContent.append("    const items = document.querySelectorAll(selector);").append("\n");
		jsContent.append("    const parent = document.querySelector(parentSelector);").append("\n");
		jsContent.append("    const shuffledItems = Array.from(items).sort(() => Math.random() - 0.5);").append("\n");
		jsContent.append("\n");
		jsContent.append("    shuffledItems.forEach(item => parent.appendChild(item));").append("\n");
		jsContent.append("}").append("\n");

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/scriptReponseUnique.js"))) {
			writer.write(jsContent.toString());
			System.out.println("Le fichier js a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public void ecrireJsElimination()
	{
		StringBuilder jsContent = new StringBuilder();

		jsContent.append("document.addEventListener('DOMContentLoaded', () => {").append("\n");
		jsContent.append("    // Élimer les mauvaises réponses dans l'ordre spécifié").append("\n");
		jsContent.append("    const eliminationOrder = Array.from(document.querySelectorAll('.mauvaise-reponse[data-order]'))").append("\n");
		jsContent.append("        .sort((a, b) => a.getAttribute('data-order') - b.getAttribute('data-order'));").append("\n\n");
		jsContent.append("    let currentIndex = 0;").append("\n\n");
		jsContent.append("    document.getElementById('eliminar').addEventListener('click', () => {").append("\n");
		jsContent.append("        if (currentIndex < eliminationOrder.length) {").append("\n");
		jsContent.append("            const toEliminate = eliminationOrder[currentIndex];").append("\n");
		jsContent.append("            toEliminate.style.display = 'none';").append("\n");
		jsContent.append("            currentIndex++;").append("\n");
		jsContent.append("        }").append("\n");
		jsContent.append("    });").append("\n\n");
		jsContent.append("    // Gérer la sélection des réponses").append("\n");
		jsContent.append("    let selectedAnswer = null;").append("\n");
		jsContent.append("    document.querySelectorAll('.reponse').forEach(reponse => {").append("\n");
		jsContent.append("        reponse.addEventListener('click', () => {").append("\n");
		jsContent.append("            if (selectedAnswer) {").append("\n");
		jsContent.append("                selectedAnswer.classList.remove('selected');").append("\n");
		jsContent.append("            }").append("\n");
		jsContent.append("            reponse.classList.add('selected');").append("\n");
		jsContent.append("            selectedAnswer = reponse;").append("\n");
		jsContent.append("        });").append("\n");
		jsContent.append("    });").append("\n\n");
		jsContent.append("    // Valider la réponse sélectionnée").append("\n");
		jsContent.append("    document.getElementById('valider').addEventListener('click', () => {").append("\n");
		jsContent.append("        if (selectedAnswer) {").append("\n");
		jsContent.append("            if (selectedAnswer.classList.contains('bonne-reponse')) {").append("\n");
		jsContent.append("                alert('Bravo! Vous avez trouvé la bonne réponse.');").append("\n");
		jsContent.append("            } else {").append("\n");
		jsContent.append("                alert('Désolé, la réponse sélectionnée est incorrecte.');").append("\n");
		jsContent.append("            }").append("\n");
		jsContent.append("        } else {").append("\n");
		jsContent.append("            alert('Veuillez sélectionner une réponse.');").append("\n");
		jsContent.append("        }").append("\n");
		jsContent.append("    });").append("\n");
		jsContent.append("});").append("\n\n");
		jsContent.append("function randomizeOrder(selector, parentSelector) {").append("\n");
		jsContent.append("    const items = document.querySelectorAll(selector);").append("\n");
		jsContent.append("    const parent = document.querySelector(parentSelector);").append("\n");
		jsContent.append("    const shuffledItems = Array.from(items).sort(() => Math.random() - 0.5);").append("\n\n");
		jsContent.append("    shuffledItems.forEach(item => parent.appendChild(item));").append("\n");
		jsContent.append("}").append("\n");

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/scriptElimination.js"))) {
			writer.write(jsContent.toString());
			System.out.println("Le fichier js a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void ecrireJsAssociation()
	{
		StringBuilder jsContent = new StringBuilder();


		jsContent.append("document.addEventListener('DOMContentLoaded', () => {").append("\n");
		jsContent.append("    randomizeOrder('.word', '#words');").append("\n");
		jsContent.append("    randomizeOrder('.definition', '#definitions');").append("\n");
		jsContent.append("});").append("\n\n");
		jsContent.append("function randomizeOrder(selector, parentSelector) {").append("\n");
		jsContent.append("    const items = document.querySelectorAll(selector);").append("\n");
		jsContent.append("    const parent = document.querySelector(parentSelector);").append("\n");
		jsContent.append("    const shuffledItems = Array.from(items).sort(() => Math.random() - 0.5);").append("\n\n");
		jsContent.append("    shuffledItems.forEach(item => parent.appendChild(item));").append("\n");
		jsContent.append("}").append("\n\n");
		jsContent.append("let motSelectionne = null;").append("\n");
		jsContent.append("let connexions = {};").append("\n");
		jsContent.append("let reverseConnexions = {};").append("\n\n");
		jsContent.append("// Ajouter un événement de clic à chaque mot").append("\n");
		jsContent.append("document.querySelectorAll('.word').forEach(mot => {").append("\n");
		jsContent.append("    mot.addEventListener('click', () => {").append("\n");
		jsContent.append("        motSelectionne = mot;").append("\n");
		jsContent.append("        clearSelection();").append("\n");
		jsContent.append("        mot.style.backgroundColor = '#d3d3d3'; // Mettre en surbrillance le mot sélectionné").append("\n");
		jsContent.append("    });").append("\n");
		jsContent.append("});").append("\n\n");
		jsContent.append("// Ajouter un événement de clic à chaque définition").append("\n");
		jsContent.append("document.querySelectorAll('.definition').forEach(definition => {").append("\n");
		jsContent.append("    definition.addEventListener('click', () => {").append("\n");
		jsContent.append("        if (motSelectionne) {").append("\n");
		jsContent.append("            let motId = motSelectionne.getAttribute('data-id');").append("\n");
		jsContent.append("            let defId = definition.getAttribute('data-id');").append("\n\n");
		jsContent.append("            if (connexions[motId]) {").append("\n");
		jsContent.append("                // Effacer l'ancienne ligne si elle existe").append("\n");
		jsContent.append("                let ancienDefId = connexions[motId];").append("\n");
		jsContent.append("                delete reverseConnexions[ancienDefId];").append("\n");
		jsContent.append("                delete connexions[motId];").append("\n");
		jsContent.append("                removeLine(motSelectionne, document.querySelector(`.definition[data-id='${ancienDefId}']`));").append("\n");
		jsContent.append("            }").append("\n\n");
		jsContent.append("            if (reverseConnexions[defId]) {").append("\n");
		jsContent.append("                // Effacer l'ancienne ligne si elle existe").append("\n");
		jsContent.append("                let ancienMotId = reverseConnexions[defId];").append("\n");
		jsContent.append("                delete connexions[ancienMotId];").append("\n");
		jsContent.append("                delete reverseConnexions[defId];").append("\n");
		jsContent.append("                removeLine(document.querySelector(`.word[data-id='${ancienMotId}']`), definition);").append("\n");
		jsContent.append("            }").append("\n\n");
		jsContent.append("            connexions[motId] = defId;").append("\n");
		jsContent.append("            reverseConnexions[defId] = motId;").append("\n\n");
		jsContent.append("            drawLine(motSelectionne, definition);").append("\n");
		jsContent.append("            clearSelection();").append("\n");
		jsContent.append("        }").append("\n");
		jsContent.append("    });").append("\n");
		jsContent.append("});").append("\n\n");
		jsContent.append("function drawLine(sujet, proposition) {").append("\n");
		jsContent.append("    const svg = document.getElementById('svg-container');").append("\n");
		jsContent.append("    const sujetRect = sujet.getBoundingClientRect();").append("\n");
		jsContent.append("    const propositionRect = proposition.getBoundingClientRect();").append("\n");
		jsContent.append("    const containerRect = svg.getBoundingClientRect();").append("\n\n");
		jsContent.append("    const x1 = sujetRect.right - containerRect.left;").append("\n");
		jsContent.append("    const y1 = sujetRect.top + sujetRect.height / 2 - containerRect.top;").append("\n");
		jsContent.append("    const x2 = propositionRect.left - containerRect.left;").append("\n");
		jsContent.append("    const y2 = propositionRect.top + propositionRect.height / 2 - containerRect.top;").append("\n\n");
		jsContent.append("    const path = document.createElementNS('http://www.w3.org/2000/svg', 'path');").append("\n");
		jsContent.append("    const d = `M ${x1},${y1} C ${(x1 + x2) / 2},${y1} ${(x1 + x2) / 2},${y2} ${x2},${y2}`;").append("\n");
		jsContent.append("    path.setAttribute('d', d);").append("\n");
		jsContent.append("    path.setAttribute('stroke', 'black');").append("\n");
		jsContent.append("    path.setAttribute('stroke-width', '2');").append("\n");
		jsContent.append("    path.setAttribute('fill', 'none');").append("\n");
		jsContent.append("    path.setAttribute('class', `line-${sujet.getAttribute('data-id')}-${proposition.getAttribute('data-id')}`);").append("\n\n");
		jsContent.append("    svg.appendChild(path);").append("\n");
		jsContent.append("}").append("\n\n");
		jsContent.append("function removeLine(sujet, proposition) {").append("\n");
		jsContent.append("    const svg = document.getElementById('svg-container');").append("\n");
		jsContent.append("    const line = svg.querySelector(`.line-${sujet.getAttribute('data-id')}-${proposition.getAttribute('data-id')}`);").append("\n");
		jsContent.append("    if (line) {").append("\n");
		jsContent.append("        svg.removeChild(line);").append("\n");
		jsContent.append("    }").append("\n");
		jsContent.append("}").append("\n\n");
		jsContent.append("// Fonction pour effacer la sélection").append("\n");
		jsContent.append("function clearSelection() {").append("\n");
		jsContent.append("    document.querySelectorAll('.word').forEach(mot => {").append("\n");
		jsContent.append("        mot.style.backgroundColor = '#e3e3e3'; // Réinitialiser la couleur de fond").append("\n");
		jsContent.append("    });").append("\n");
		jsContent.append("}").append("\n\n");
		jsContent.append("// Fonction pour valider les connexions").append("\n");
		jsContent.append("function validate() {").append("\n");
		jsContent.append("    let correct = true;").append("\n");
		jsContent.append("    let totalAssociations = Object.keys(connexions).length;").append("\n");
		jsContent.append("    let mots = document.querySelectorAll('.word').length;").append("\n\n");
		jsContent.append("    if (totalAssociations < mots) {").append("\n");
		jsContent.append("        alert(\"Il manque des associations.\");").append("\n");
		jsContent.append("        return;").append("\n");
		jsContent.append("    }").append("\n\n");
		jsContent.append("    for (let motId in connexions) {").append("\n");
		jsContent.append("        if (connexions[motId] != motId) {").append("\n");
		jsContent.append("            correct = false;").append("\n");
		jsContent.append("            break;").append("\n");
		jsContent.append("        }").append("\n");
		jsContent.append("    }").append("\n\n");
		jsContent.append("    if (correct) {").append("\n");
		jsContent.append("        alert(\"Bravo! Toutes les associations sont correctes.\");").append("\n");
		jsContent.append("    } else {").append("\n");
		jsContent.append("        alert(\"Désolé, certaines associations sont incorrectes. Essayez encore.\");").append("\n");
		jsContent.append("    }").append("\n");
		jsContent.append("}").append("\n");

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/scriptAssociation.js"))) {
			writer.write(jsContent.toString());
			System.out.println("Le fichier js a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ecrireCSSAssociation()
	{
		StringBuilder cssContent = new StringBuilder();

		cssContent.append("body {").append("\n");
		cssContent.append("    font-family: Arial, sans-serif;").append("\n");
		cssContent.append("    display: flex;").append("\n");
		cssContent.append("    justify-content: center;").append("\n");
		cssContent.append("    align-items: center;").append("\n");
		cssContent.append("    height: 100vh;").append("\n");
		cssContent.append("    margin: 0;").append("\n");
		cssContent.append("    background-color: #f7f7f7;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");
		cssContent.append(".container {").append("\n");
		cssContent.append("    text-align: center;").append("\n");
		cssContent.append("    position: relative;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");
		cssContent.append(".columns {").append("\n");
		cssContent.append("    display: flex;").append("\n");
		cssContent.append("    justify-content: space-between;").append("\n");
		cssContent.append("    align-items: center;").append("\n");
		cssContent.append("    margin-bottom: 20px;").append("\n");
		cssContent.append("    position: relative;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");
		cssContent.append(".words, .definitions {").append("\n");
		cssContent.append("    width: 45%;").append("\n");
		cssContent.append("    padding: 20px;").append("\n");
		cssContent.append("    border: 1px solid #ccc;").append("\n");
		cssContent.append("    border-radius: 5px;").append("\n");
		cssContent.append("    background-color: #fff;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");
		cssContent.append(".word, .definition {").append("\n");
		cssContent.append("    margin: 10px 0;").append("\n");
		cssContent.append("    padding: 10px;").append("\n");
		cssContent.append("    background-color: #e3e3e3;").append("\n");
		cssContent.append("    border: 1px solid #ccc;").append("\n");
		cssContent.append("    border-radius: 5px;").append("\n");
		cssContent.append("    cursor: pointer;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");
		cssContent.append("svg {").append("\n");
		cssContent.append("    position: absolute;").append("\n");
		cssContent.append("    width: 100%;").append("\n");
		cssContent.append("    height: 100%;").append("\n");
		cssContent.append("    pointer-events: none;").append("\n");
		cssContent.append("}").append("\n");

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/styleAssociation.css"))) {
			writer.write(cssContent.toString());
			System.out.println("Le fichier js a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ecrireCSSElimination()
	{
		StringBuilder cssContent = new StringBuilder();

		cssContent.append("body {").append("\n");
		cssContent.append("    font-family: Arial, sans-serif;").append("\n");
		cssContent.append("    display: flex;").append("\n");
		cssContent.append("    justify-content: center;").append("\n");
		cssContent.append("    align-items: center;").append("\n");
		cssContent.append("    height: 100vh;").append("\n");
		cssContent.append("    margin: 0;").append("\n");
		cssContent.append("    background-color: #f7f7f7;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");

		cssContent.append(".container {").append("\n");
		cssContent.append("    text-align: center;").append("\n");
		cssContent.append("    position: relative;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");

		cssContent.append(".question {").append("\n");
		cssContent.append("    display: flex;").append("\n");
		cssContent.append("    flex-direction: column;").append("\n");
		cssContent.append("    justify-content: center;").append("\n");
		cssContent.append("    align-items: center;").append("\n");
		cssContent.append("    width: 50%;").append("\n");
		cssContent.append("    margin: 0 auto;").append("\n");
		cssContent.append("    padding: 20px;").append("\n");
		cssContent.append("    border: 1px solid #ccc;").append("\n");
		cssContent.append("    border-radius: 5px;").append("\n");
		cssContent.append("    background-color: #fff;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");

		cssContent.append(".reponse {").append("\n");
		cssContent.append("    margin: 10px 0;").append("\n");
		cssContent.append("    padding: 10px;").append("\n");
		cssContent.append("    background-color: #e3e3e3;").append("\n");
		cssContent.append("    border: 1px solid #ccc;").append("\n");
		cssContent.append("    border-radius: 5px;").append("\n");
		cssContent.append("    cursor: pointer;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");

		cssContent.append(".reponse.selected {").append("\n");
		cssContent.append("    background-color: #b3d9ff; /* Highlight selected answer */").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");

		cssContent.append("button {").append("\n");
		cssContent.append("    margin: 10px 5px;").append("\n");
		cssContent.append("    padding: 10px 20px;").append("\n");
		cssContent.append("    background-color: #4CAF50;").append("\n");
		cssContent.append("    color: white;").append("\n");
		cssContent.append("    border: none;").append("\n");
		cssContent.append("    border-radius: 5px;").append("\n");
		cssContent.append("    cursor: pointer;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");

		cssContent.append("button:hover {").append("\n");
		cssContent.append("    background-color: #45a049;").append("\n");
		cssContent.append("}").append("\n");

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/styleElimination.css"))) {
			writer.write(cssContent.toString());
			System.out.println("Le fichier js a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ecrireCSSReponseUnique()
	{
		StringBuilder cssContent = new StringBuilder();

		cssContent.append("body {").append("\n");
		cssContent.append("    font-family: Arial, sans-serif;").append("\n");
		cssContent.append("    display: flex;").append("\n");
		cssContent.append("    justify-content: center;").append("\n");
		cssContent.append("    align-items: center;").append("\n");
		cssContent.append("    height: 100vh;").append("\n");
		cssContent.append("    margin: 0;").append("\n");
		cssContent.append("    background-color: #f7f7f7;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");

		cssContent.append(".container {").append("\n");
		cssContent.append("    text-align: center;").append("\n");
		cssContent.append("    position: relative;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");

		cssContent.append(".question {").append("\n");
		cssContent.append("    display: flex;").append("\n");
		cssContent.append("    flex-direction: column;").append("\n");
		cssContent.append("    justify-content: center;").append("\n");
		cssContent.append("    align-items: center;").append("\n");
		cssContent.append("    width: 50%;").append("\n");
		cssContent.append("    margin: 0 auto;").append("\n");
		cssContent.append("    padding: 20px;").append("\n");
		cssContent.append("    border: 1px solid #ccc;").append("\n");
		cssContent.append("    border-radius: 5px;").append("\n");
		cssContent.append("    background-color: #fff;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");

		cssContent.append(".reponse {").append("\n");
		cssContent.append("    margin: 10px 0;").append("\n");
		cssContent.append("    padding: 10px;").append("\n");
		cssContent.append("    background-color: #e3e3e3;").append("\n");
		cssContent.append("    border: 1px solid #ccc;").append("\n");
		cssContent.append("    border-radius: 5px;").append("\n");
		cssContent.append("    cursor: pointer;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");

		cssContent.append(".reponse.selected {").append("\n");
		cssContent.append("    background-color: #b3d9ff; /* Highlight selected answer */").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");

		cssContent.append("button {").append("\n");
		cssContent.append("    margin: 10px 5px;").append("\n");
		cssContent.append("    padding: 10px 20px;").append("\n");
		cssContent.append("    background-color: #4CAF50;").append("\n");
		cssContent.append("    color: white;").append("\n");
		cssContent.append("    border: none;").append("\n");
		cssContent.append("    border-radius: 5px;").append("\n");
		cssContent.append("    cursor: pointer;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");

		cssContent.append("button:hover {").append("\n");
		cssContent.append("    background-color: #45a049;").append("\n");
		cssContent.append("}").append("\n");

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/styleReponseUnique.css"))) {
			writer.write(cssContent.toString());
			System.out.println("Le fichier js a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	}
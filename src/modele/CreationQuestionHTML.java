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

	public void pageQuestionChoixAssociation(Question question, int numQuestion)
	{
		StringBuilder htmlContent = new StringBuilder();
	
		String pageSuivante;
		String pagePrecedente;
		if (numQuestion < this.evaluation.getNbQuestion()) {
			pageSuivante = "page" + (numQuestion + 1);
		} else {
			pageSuivante = "pageDeFin";
		}
	
		if (numQuestion > 1) {
			pagePrecedente = "page" + (numQuestion - 1);
		} else {
			pagePrecedente = "pageDAccueil";
		}
	
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
		htmlContent.append("<p>Question " + numQuestion + "/" + this.evaluation.getNbQuestion() + "</p>");
		if (this.evaluation.getChrono())
		{
			htmlContent.append("<p>Temps restant : <span id=\"timer\">"+question.getTemps()+"</span> secondes</p>");
		}
		htmlContent.append("        <div id=\"points\">Points : 0</div>").append("\n");
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
		if(!this.evaluation.getChrono())
		{
			htmlContent.append("        <button class=\"btn\" onclick=\"location.href='").append(pagePrecedente).append(".html';\">Précédent</button>").append("\n");
		}
		htmlContent.append("        <button class=\"btn\" onclick=\"validate(").append(numQuestion).append(")\">Valider</button>").append("\n");
		htmlContent.append("        <button class=\"btn\" onclick=\"location.href='").append(pageSuivante).append(".html';\">Suivant</button>").append("\n");
		htmlContent.append("    </div>\n");
		htmlContent.append("    <div id=\"popup\" class=\"popup\">").append("\n");
		htmlContent.append("        <div class=\"popup-content\">").append("\n");
		htmlContent.append("            <span class=\"close\" id=\"popup-close\">&times;</span>").append("\n");
		htmlContent.append("            <p id=\"popup-text\">").append(question.getFeedback()).append("</p>").append("\n");
		htmlContent.append("        </div>").append("\n");
		htmlContent.append("    </div>").append("\n");
		htmlContent.append("    <script src=\"scriptAssociation.js\"></script>").append("\n");
		htmlContent.append("    <script src=\"timer.js\"></script>").append("\n");
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
		String pagePrecedente;
		if (numQuestion < this.evaluation.getNbQuestion()) {
			pageSuivante = "page" + (numQuestion + 1);
		} else {
			pageSuivante = "pageDeFin";
		}
	
		if (numQuestion > 1) {
			pagePrecedente = "page" + (numQuestion - 1);
		} else {
			pagePrecedente = "pageDAccueil";
		}
	
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
		htmlContent.append("<p>Question " + numQuestion + "/" + this.evaluation.getNbQuestion() + "</p>");
		if (this.evaluation.getChrono())
		{
			htmlContent.append("<p>Temps restant : <span id=\"timer\">"+question.getTemps()+"</span> secondes</p>");
		}
		htmlContent.append("        <div id=\"points\">Points : 0</div>").append("\n");
		htmlContent.append("        <div class=\"question\" id=\"question-").append(numQuestion).append("\">").append("\n");
	
		for (IOption reponse : question.getEnsOptions()) {
			if (reponse instanceof OptionElimination) {
				OptionElimination opt = (OptionElimination) reponse;
				String reponseIdStr = "reponse-" + opt.getId();
				if (opt.getOrdre() != -1) {
					htmlContent.append("            <div class=\"reponse mauvaise-reponse\" id=\"").append(reponseIdStr).append("\" data-order=\"")
							   .append(opt.getOrdre()).append("\" ptselim=\"").append(opt.getNbPointsMoins()).append("\">")
							   .append(opt.getEnonce()).append("</div>").append("\n");
				} else if (opt.getEstReponse()) {
					htmlContent.append("            <div class=\"reponse bonne-reponse\" id=\"").append(reponseIdStr).append("\">")
							   .append(opt.getEnonce()).append("</div>").append("\n");
				} else {
					htmlContent.append("            <div class=\"reponse mauvaise-reponse\" id=\"").append(reponseIdStr).append("\">")
							   .append(opt.getEnonce()).append("</div>").append("\n");
				}
			}
		}
	
		htmlContent.append("        </div>").append("\n");
		if(!this.evaluation.getChrono())
		{
			htmlContent.append("        <button class=\"btn\" onclick=\"location.href='").append(pagePrecedente).append(".html';\">Précédent</button>").append("\n");
		}
		htmlContent.append("        <button id=\"eliminar\">Éliminer</button>").append("\n");
		htmlContent.append("        <button id=\"valider\">Valider</button>").append("\n");
		htmlContent.append("        <button onclick=\"location.href='").append(pageSuivante).append(".html';\">Suivant</button>").append("\n");
		htmlContent.append("    </div>").append("\n");
		htmlContent.append("    <div id=\"popup\" class=\"popup\">").append("\n");
		htmlContent.append("        <div class=\"popup-content\">").append("\n");
		htmlContent.append("            <span class=\"close\" id=\"popup-close\">&times;</span>").append("\n");
		htmlContent.append("            <p id=\"popup-text\">").append(question.getFeedback()).append("</p>").append("\n");
		htmlContent.append("        </div>").append("\n");
		htmlContent.append("    </div>").append("\n");
		htmlContent.append("    <script src=\"scriptElimination.js\"></script>").append("\n");
		htmlContent.append("    <script src=\"timer.js\"></script>").append("\n");
		htmlContent.append("</body>").append("\n");
		htmlContent.append("</html>").append("\n");
	
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/page" + numQuestion + ".html"))) {
			writer.write(htmlContent.toString());
			System.out.println("Le fichier HTML a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	

	public void pageQuestionUnique(Question question, int numQuestion) {
		StringBuilder htmlContent = new StringBuilder();
	
		String pageSuivante;
		String pagePrecedente;
		if (numQuestion < this.evaluation.getNbQuestion()) {
			pageSuivante = "page" + (numQuestion + 1);
		} else {
			pageSuivante = "pageDeFin";
		}
	
		if (numQuestion > 1) {
			pagePrecedente = "page" + (numQuestion - 1);
		} else {
			pagePrecedente = "pageDAccueil";
		}
	
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
		htmlContent.append("<p>Question " + numQuestion + "/" + this.evaluation.getNbQuestion() + "</p>");
		if (this.evaluation.getChrono())
		{
			htmlContent.append("<p>Temps restant : <span id=\"timer\">"+question.getTemps()+"</span> secondes</p>");
		}
		htmlContent.append("        <div id=\"points\">Points : 0</div>").append("\n");
		htmlContent.append("        <div class=\"question\" id=\"question\" data-points=\"").append(question.getNbPoints()).append("\" data-question-id=\"question-").append(numQuestion).append("\">").append("\n");
		for (IOption reponse : question.getEnsOptions()) {
			if (reponse instanceof Option) {
				Option opt = (Option) reponse;
				if (opt.getEstReponse()) {
					htmlContent.append("            <div class=\"reponse bonne-reponse\">").append(opt.getEnonce()).append("</div>").append("\n");
				} else {
					htmlContent.append("            <div class=\"reponse mauvaise-reponse\">").append(opt.getEnonce()).append("</div>").append("\n");
				}
			}
		}
		htmlContent.append("        </div>").append("\n");
		if(!this.evaluation.getChrono())
		{
			htmlContent.append("        <button class=\"btn\" onclick=\"location.href='").append(pagePrecedente).append(".html';\">Précédent</button>").append("\n");
		}
		htmlContent.append("        <button id=\"valider\">Valider</button>").append("\n");
		htmlContent.append("        <button onclick=\"location.href='").append(pageSuivante).append(".html';\">Suivant</button>").append("\n");
		htmlContent.append("    </div>").append("\n");
		htmlContent.append("    <div id=\"popup\" class=\"popup\">").append("\n");
		htmlContent.append("        <div class=\"popup-content\">").append("\n");
		htmlContent.append("            <span class=\"close\" id=\"popup-close\">&times;</span>").append("\n");
		htmlContent.append("            <p id=\"popup-text\"></p>").append("\n");
		htmlContent.append("            <p>").append(question.getFeedback()).append("</p>").append("\n");
		htmlContent.append("        </div>").append("\n");
		htmlContent.append("    </div>").append("\n");
		htmlContent.append("    <script src=\"scriptReponseUnique.js\"></script>").append("\n");
		htmlContent.append("    <script src=\"timer.js\"></script>").append("\n");
		htmlContent.append("</body>").append("\n");
		htmlContent.append("</html>").append("\n");
	
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/page" + numQuestion + ".html"))) {
			writer.write(htmlContent.toString());
			System.out.println("Le fichier HTML a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	


	public void pageQuestionMultiple(Question question, int numQuestion)
	{
		StringBuilder htmlContent = new StringBuilder();
	
		String pageSuivante;
		String pagePrecedente;
		if (numQuestion < this.evaluation.getNbQuestion()) {
			pageSuivante = "page" + (numQuestion + 1);
		} else {
			pageSuivante = "pageDeFin";
		}
	
		if (numQuestion > 1) {
			pagePrecedente = "page" + (numQuestion - 1);
		} else {
			pagePrecedente = "pageDAccueil";
		}
	
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
		htmlContent.append("<p>Question " + numQuestion + "/" + this.evaluation.getNbQuestion() + "</p>");
		if (this.evaluation.getChrono())
		{
			htmlContent.append("<p>Temps restant : <span id=\"timer\">"+question.getTemps()+"</span> secondes</p>");
		}
		htmlContent.append("        <div id=\"points\">Points : 0</div>").append("\n");
		htmlContent.append("        <div class=\"question\" data-points=\"").append(question.getNbPoints()).append("\" data-question-id=\"question-").append(numQuestion).append("\">").append("\n");
		
		for (IOption reponse : question.getEnsOptions()) {
			if (reponse instanceof Option) {
				Option opt = (Option) reponse;
				String reponseId = "reponse-" + opt.getId();
				if (opt.getEstReponse()) {
					htmlContent.append("            <div class=\"reponse bonne-reponse\" id=\"").append(reponseId).append("\">").append(opt.getEnonce()).append("</div>").append("\n");
				} else {
					htmlContent.append("            <div class=\"reponse mauvaise-reponse\" id=\"").append(reponseId).append("\">").append(opt.getEnonce()).append("</div>").append("\n");
				}
			}
		}
		
		htmlContent.append("        </div>").append("\n");
		if(!this.evaluation.getChrono())
		{
			htmlContent.append("        <button class=\"btn\" onclick=\"location.href='").append(pagePrecedente).append(".html';\">Précédent</button>").append("\n");
		}
		htmlContent.append("        <button id=\"valider\">Valider</button>").append("\n");
		htmlContent.append("        <button onclick=\"location.href='").append(pageSuivante).append(".html';\">Suivant</button>").append("\n");
		htmlContent.append("    </div>").append("\n");
		htmlContent.append("    <div id=\"popup\" class=\"popup\">").append("\n");
		htmlContent.append("        <div class=\"popup-content\">").append("\n");
		htmlContent.append("            <span class=\"close\" id=\"popup-close\">&times;</span>").append("\n");
		htmlContent.append("            <p id=\"popup-text\"></p>").append("\n");
		htmlContent.append("            <p>").append(question.getFeedback()).append("</p>").append("\n");
		htmlContent.append("        </div>").append("\n");
		htmlContent.append("    </div>").append("\n");
		htmlContent.append("    <script src=\"scriptReponseMultiple.js\"></script>").append("\n");
		htmlContent.append("    <script src=\"timer.js\"></script>").append("\n");
		htmlContent.append("</body>").append("\n");
		htmlContent.append("</html>").append("\n");
	
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/page" + numQuestion + ".html"))) {
			writer.write(htmlContent.toString());
			System.out.println("Le fichier HTML a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ecrireJsReponseMultiple() {
		StringBuilder jsContent = new StringBuilder();
	
		jsContent.append("document.addEventListener('DOMContentLoaded', () => {").append("\n");
		jsContent.append("    const questionId = document.querySelector('.question').getAttribute('data-question-id');").append("\n");
		jsContent.append("\n");
		jsContent.append("    // Mélanger les réponses").append("\n");
		jsContent.append("    randomizeOrder('.reponse', '.question');").append("\n");
		jsContent.append("\n");
		jsContent.append("    // Initialiser les points à partir du localStorage").append("\n");
		jsContent.append("    let totalPoints = localStorage.getItem('points') ? parseFloat(localStorage.getItem('points')) : 0;").append("\n");
		jsContent.append("    document.getElementById('points').textContent = `Points : ${totalPoints}`;").append("\n");
		jsContent.append("\n");
		jsContent.append("    // Gérer la sélection des réponses").append("\n");
		jsContent.append("    let selectedAnswers = new Set();").append("\n");
		jsContent.append("    let isValidationDone = localStorage.getItem(`isValidationDone-${questionId}`) === 'true';").append("\n");
		jsContent.append("    if (isValidationDone) {").append("\n");
		jsContent.append("        restoreState(questionId);").append("\n");
		jsContent.append("        transformButtonToFeedback();").append("\n");
		jsContent.append("    } else {").append("\n");
		jsContent.append("        enableAnswerSelection();").append("\n");
		jsContent.append("    }").append("\n");
		jsContent.append("\n");
		jsContent.append("    function enableAnswerSelection() {").append("\n");
		jsContent.append("        document.querySelectorAll('.reponse').forEach(reponse => {").append("\n");
		jsContent.append("            reponse.addEventListener('click', () => {").append("\n");
		jsContent.append("                if (!isValidationDone) {").append("\n");
		jsContent.append("                    if (selectedAnswers.has(reponse.id)) {").append("\n");
		jsContent.append("                        reponse.classList.remove('selected');").append("\n");
		jsContent.append("                        selectedAnswers.delete(reponse.id);").append("\n");
		jsContent.append("                    } else {").append("\n");
		jsContent.append("                        reponse.classList.add('selected');").append("\n");
		jsContent.append("                        selectedAnswers.add(reponse.id);").append("\n");
		jsContent.append("                    }").append("\n");
		jsContent.append("                }").append("\n");
		jsContent.append("            });").append("\n");
		jsContent.append("        });").append("\n");
		jsContent.append("    }").append("\n");
		jsContent.append("\n");
		jsContent.append("    // Valider la réponse sélectionnée").append("\n");
		jsContent.append("    function validate() {").append("\n");
		jsContent.append("        if (isValidationDone) return;  // Si la validation est déjà faite, ne pas continuer").append("\n");
		jsContent.append("\n");
		jsContent.append("        const popup = document.getElementById('popup');").append("\n");
		jsContent.append("        const popupText = document.getElementById('popup-text');").append("\n");
		jsContent.append("        const questionPoints = parseFloat(document.querySelector('.question').getAttribute('data-points'));").append("\n");
		jsContent.append("\n");
		jsContent.append("        if (selectedAnswers.size > 0) {").append("\n");
		jsContent.append("            let allCorrect = true;").append("\n");
		jsContent.append("\n");
		jsContent.append("            selectedAnswers.forEach(answerId => {").append("\n");
		jsContent.append("                const answerElement = document.getElementById(answerId);").append("\n");
		jsContent.append("                if (!answerElement.classList.contains('bonne-reponse')) {").append("\n");
		jsContent.append("                    allCorrect = false;").append("\n");
		jsContent.append("                }").append("\n");
		jsContent.append("            });").append("\n");
		jsContent.append("\n");
		jsContent.append("            // Vérifier que toutes les bonnes réponses sont sélectionnées").append("\n");
		jsContent.append("            const allGoodSelected = Array.from(document.querySelectorAll('.bonne-reponse')).every(goodAnswer => {").append("\n");
		jsContent.append("                return selectedAnswers.has(goodAnswer.id);").append("\n");
		jsContent.append("            });").append("\n");
		jsContent.append("\n");
		jsContent.append("            if (allCorrect && allGoodSelected) {").append("\n");
		jsContent.append("                popupText.innerHTML = '<span style=\"color: green;\">Bonne réponse!</span>';").append("\n");
		jsContent.append("                totalPoints += questionPoints;").append("\n");
		jsContent.append("                localStorage.setItem('points', totalPoints);").append("\n");
		jsContent.append("                document.getElementById('points').textContent = `Points : ${totalPoints}`;").append("\n");
		jsContent.append("            } else {").append("\n");
		jsContent.append("                popupText.innerHTML = '<span style=\"color: red;\">Mauvaise réponse!</span>';").append("\n");
		jsContent.append("            }").append("\n");
		jsContent.append("\n");
		jsContent.append("            // Mettre en évidence les bonnes réponses").append("\n");
		jsContent.append("            document.querySelectorAll('.bonne-reponse').forEach(goodAnswer => {").append("\n");
		jsContent.append("                goodAnswer.style.backgroundColor = 'green';").append("\n");
		jsContent.append("            });").append("\n");
		jsContent.append("\n");
		jsContent.append("            // Marquer les mauvaises réponses").append("\n");
		jsContent.append("            selectedAnswers.forEach(answerId => {").append("\n");
		jsContent.append("                const answerElement = document.getElementById(answerId);").append("\n");
		jsContent.append("                if (!answerElement.classList.contains('bonne-reponse')) {").append("\n");
		jsContent.append("                    answerElement.style.backgroundColor = 'red';").append("\n");
		jsContent.append("                }").append("\n");
		jsContent.append("            });").append("\n");
		jsContent.append("\n");
		jsContent.append("            // Sauvegarder l'état").append("\n");
		jsContent.append("            localStorage.setItem(`isValidationDone-${questionId}`, true);").append("\n");
		jsContent.append("            localStorage.setItem(`selectedAnswers-${questionId}`, JSON.stringify(Array.from(selectedAnswers)));").append("\n");
		jsContent.append("            localStorage.setItem(`popupText-${questionId}`, popupText.innerHTML);").append("\n");
		jsContent.append("\n");
		jsContent.append("            // Transformer le bouton 'Valider' en 'Feedback'").append("\n");
		jsContent.append("            transformButtonToFeedback();").append("\n");
		jsContent.append("\n");
		jsContent.append("            // Afficher le pop-up personnalisé").append("\n");
		jsContent.append("            popup.style.display = 'flex';").append("\n");
		jsContent.append("            isValidationDone = true;").append("\n");
		jsContent.append("        } else {").append("\n");
		jsContent.append("            alert('Veuillez sélectionner une réponse !');").append("\n");
		jsContent.append("        }").append("\n");
		jsContent.append("\n");
		jsContent.append("        // Réactiver la sélection des réponses après l'alerte").append("\n");
		jsContent.append("        document.querySelectorAll('.reponse').forEach(reponse => {").append("\n");
		jsContent.append("            reponse.style.pointerEvents = '';").append("\n");
		jsContent.append("        });").append("\n");
		jsContent.append("    }").append("\n");
		jsContent.append("\n");
		jsContent.append("    document.getElementById('valider').addEventListener('click', validate);").append("\n");
		jsContent.append("\n");
		jsContent.append("    function transformButtonToFeedback() {").append("\n");
		jsContent.append("        const validerButton = document.getElementById('valider');").append("\n");
		jsContent.append("        validerButton.textContent = 'Feedback';").append("\n");
		jsContent.append("        validerButton.removeEventListener('click', validate);").append("\n");
		jsContent.append("        validerButton.addEventListener('click', showFeedback);").append("\n");
		jsContent.append("    }").append("\n");
		jsContent.append("\n");
		jsContent.append("    function showFeedback() {").append("\n");
		jsContent.append("        const popup = document.getElementById('popup');").append("\n");
		jsContent.append("        const popupText = document.getElementById('popup-text');").append("\n");
		jsContent.append("        const savedPopupText = localStorage.getItem(`popupText-${questionId}`);").append("\n");
		jsContent.append("        if (savedPopupText) {").append("\n");
		jsContent.append("            popupText.innerHTML = savedPopupText;").append("\n");
		jsContent.append("        } else {").append("\n");
		jsContent.append("            popupText.innerHTML = '<span style=\"color: red;\">Pas de feedback disponible.</span>';").append("\n");
		jsContent.append("        }").append("\n");
		jsContent.append("        popup.style.display = 'flex';").append("\n");
		jsContent.append("    }").append("\n");
		jsContent.append("\n");
		jsContent.append("    // Fermer le pop-up").append("\n");
		jsContent.append("    document.getElementById('popup-close').addEventListener('click', () => {").append("\n");
		jsContent.append("        const popup = document.getElementById('popup');").append("\n");
		jsContent.append("        popup.style.display = 'none';").append("\n");
		jsContent.append("    });").append("\n");
		jsContent.append("});").append("\n");
		jsContent.append("\n");
		jsContent.append("function restoreState(questionId) {").append("\n");
		jsContent.append("    const selectedAnswers = JSON.parse(localStorage.getItem(`selectedAnswers-${questionId}`));").append("\n");
		jsContent.append("    if (selectedAnswers) {").append("\n");
		jsContent.append("        selectedAnswers.forEach(answerId => {").append("\n");
		jsContent.append("            const reponse = document.getElementById(answerId);").append("\n");
		jsContent.append("            if (reponse) {").append("\n");
		jsContent.append("                reponse.classList.add('selected');").append("\n");
		jsContent.append("                if (reponse.classList.contains('bonne-reponse')) {").append("\n");
		jsContent.append("                    reponse.style.backgroundColor = 'green';").append("\n");
		jsContent.append("                } else {").append("\n");
		jsContent.append("                    reponse.style.backgroundColor = 'red';").append("\n");
		jsContent.append("                }").append("\n");
		jsContent.append("            }").append("\n");
		jsContent.append("        });").append("\n");
		jsContent.append("    }").append("\n");
		jsContent.append("    const savedPopupText = localStorage.getItem(`popupText-${questionId}`);").append("\n");
		jsContent.append("    if (savedPopupText) {").append("\n");
		jsContent.append("        document.getElementById('popup-text').innerHTML = savedPopupText;").append("\n");
		jsContent.append("    }").append("\n");
		jsContent.append("    document.querySelectorAll('.bonne-reponse').forEach(goodAnswer => {").append("\n");
		jsContent.append("        goodAnswer.style.backgroundColor = 'green';").append("\n");
		jsContent.append("    });").append("\n");
		jsContent.append("}").append("\n");
		jsContent.append("\n");
		jsContent.append("function randomizeOrder(selector, parentSelector) {").append("\n");
		jsContent.append("    const items = document.querySelectorAll(selector);").append("\n");
		jsContent.append("    const parent = document.querySelector(parentSelector);").append("\n");
		jsContent.append("    const shuffledItems = Array.from(items).sort(() => Math.random() - 0.5);").append("\n");
		jsContent.append("    shuffledItems.forEach(item => parent.appendChild(item));").append("\n");
		jsContent.append("}").append("\n");

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/scriptReponseMultiple.js"))) {
			writer.write(jsContent.toString());
			System.out.println("Le fichier JS a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ecrireJsReponseUnique()
	{
		StringBuilder jsContent = new StringBuilder();
	
		jsContent.append("document.addEventListener('DOMContentLoaded', () => {").append("\n");
		jsContent.append("    const questionId = document.querySelector('.question').getAttribute('data-question-id');").append("\n");
		jsContent.append("\n");
		jsContent.append("    // Mélanger les réponses (au cas où)").append("\n");
		jsContent.append("    randomizeOrder('.reponse', '#question');").append("\n");
		jsContent.append("\n");
		jsContent.append("    // Initialiser les points à partir du localStorage").append("\n");
		jsContent.append("    let totalPoints = localStorage.getItem('points') ? parseFloat(localStorage.getItem('points')) : 0;").append("\n");
		jsContent.append("    document.getElementById('points').textContent = `Points : ${totalPoints}`;").append("\n");
		jsContent.append("\n");
		jsContent.append("    // Gérer la sélection des réponses").append("\n");
		jsContent.append("    let selectedAnswer = null;").append("\n");
		jsContent.append("    let isValidationDone = localStorage.getItem(`isValidationDone-${questionId}`) === 'true';").append("\n");
		jsContent.append("    if (isValidationDone) {").append("\n");
		jsContent.append("        restoreState(questionId);").append("\n");
		jsContent.append("        transformButtonToFeedback(); // Assurez-vous d'appeler la fonction ici").append("\n");
		jsContent.append("    }").append("\n");
		jsContent.append("\n");
		jsContent.append("    function enableAnswerSelection() {").append("\n");
		jsContent.append("        document.querySelectorAll('.reponse').forEach(reponse => {").append("\n");
		jsContent.append("            reponse.addEventListener('click', () => {").append("\n");
		jsContent.append("                if (!isValidationDone) {").append("\n");
		jsContent.append("                    if (selectedAnswer) {").append("\n");
		jsContent.append("                        selectedAnswer.classList.remove('selected');").append("\n");
		jsContent.append("                    }").append("\n");
		jsContent.append("                    reponse.classList.add('selected');").append("\n");
		jsContent.append("                    selectedAnswer = reponse;").append("\n");
		jsContent.append("                }").append("\n");
		jsContent.append("            });").append("\n");
		jsContent.append("        });").append("\n");
		jsContent.append("    }").append("\n");
		jsContent.append("\n");
		jsContent.append("    enableAnswerSelection();").append("\n");
		jsContent.append("\n");
		jsContent.append("    // Valider la réponse sélectionnée").append("\n");
		jsContent.append("    function validate() {").append("\n");
		jsContent.append("        const popup = document.getElementById('popup');").append("\n");
		jsContent.append("        const popupText = document.getElementById('popup-text');").append("\n");
		jsContent.append("        const questionPoints = parseFloat(document.querySelector('.question').getAttribute('data-points'));").append("\n");
		jsContent.append("\n");
		jsContent.append("        if (selectedAnswer) {").append("\n");
		jsContent.append("            if (selectedAnswer.classList.contains('bonne-reponse')) {").append("\n");
		jsContent.append("                popupText.innerHTML = '<span style=\"color: green;\">Bonne réponse!</span>';").append("\n");
		jsContent.append("                totalPoints += questionPoints;").append("\n");
		jsContent.append("                localStorage.setItem('points', totalPoints);").append("\n");
		jsContent.append("                document.getElementById('points').textContent = `Points : ${totalPoints}`;").append("\n");
		jsContent.append("            } else {").append("\n");
		jsContent.append("                popupText.innerHTML = '<span style=\"color: red;\">Mauvaise réponse!</span>';").append("\n");
		jsContent.append("                selectedAnswer.style.backgroundColor = 'red';").append("\n");
		jsContent.append("            }").append("\n");
		jsContent.append("            // Mettre en évidence les bonnes réponses").append("\n");
		jsContent.append("            document.querySelectorAll('.bonne-reponse').forEach(goodAnswer => {").append("\n");
		jsContent.append("                goodAnswer.style.backgroundColor = 'green';").append("\n");
		jsContent.append("            });").append("\n");
		jsContent.append("\n");
		jsContent.append("            // Sauvegarder l'état").append("\n");
		jsContent.append("            localStorage.setItem(`isValidationDone-${questionId}`, true);").append("\n");
		jsContent.append("            localStorage.setItem(`selectedAnswer-${questionId}`, selectedAnswer.innerHTML);").append("\n");
		jsContent.append("            localStorage.setItem(`popupText-${questionId}`, popupText.innerHTML);").append("\n");
		jsContent.append("\n");
		jsContent.append("            // Transformer le bouton 'Valider' en 'Feedback'").append("\n");
		jsContent.append("            transformButtonToFeedback();").append("\n");
		jsContent.append("\n");
		jsContent.append("            // Afficher le pop-up personnalisé").append("\n");
		jsContent.append("            popup.style.display = 'flex';").append("\n");
		jsContent.append("            isValidationDone = true; // Ne marquer comme \"validation terminée\" que si une réponse est sélectionnée").append("\n");
		jsContent.append("        } else if (!isValidationDone) {").append("\n");
		jsContent.append("            alert('Veuillez sélectionner une réponse !');").append("\n");
		jsContent.append("            isValidationDone = false;").append("\n");
		jsContent.append("        }").append("\n");
		jsContent.append("\n");
		jsContent.append("        enableAnswerSelection();").append("\n");
		jsContent.append("    }").append("\n");
		jsContent.append("\n");
		jsContent.append("    document.getElementById('valider').addEventListener('click', validate);").append("\n");
		jsContent.append("\n");
		jsContent.append("    function transformButtonToFeedback() {").append("\n");
		jsContent.append("        const validerButton = document.getElementById('valider');").append("\n");
		jsContent.append("        validerButton.textContent = 'Feedback';").append("\n");
		jsContent.append("        validerButton.removeEventListener('click', validate);").append("\n");
		jsContent.append("        validerButton.addEventListener('click', () => {").append("\n");
		jsContent.append("            const popupFeedback = document.getElementById('popup');").append("\n");
		jsContent.append("            const popupFeedbackText = document.getElementById('popup-text');").append("\n");
		jsContent.append("            const savedPopupText = localStorage.getItem(`popupText-${questionId}`);").append("\n");
		jsContent.append("            if (savedPopupText) {").append("\n");
		jsContent.append("                popupFeedbackText.innerHTML = savedPopupText;").append("\n");
		jsContent.append("            } else {").append("\n");
		jsContent.append("                if (selectedAnswer && selectedAnswer.classList.contains('bonne-reponse')) {").append("\n");
		jsContent.append("                    popupFeedbackText.innerHTML = '<span style=\"color: green;\">Bonne réponse!</span>';").append("\n");
		jsContent.append("                } else {").append("\n");
		jsContent.append("                    popupFeedbackText.innerHTML = '<span style=\"color: red;\">Mauvaise réponse!</span>';").append("\n");
		jsContent.append("                }").append("\n");
		jsContent.append("            }").append("\n");
		jsContent.append("            popupFeedback.style.display = 'flex';").append("\n");
		jsContent.append("        });").append("\n");
		jsContent.append("    }").append("\n");
		jsContent.append("\n");
		jsContent.append("    // Fermer le pop-up").append("\n");
		jsContent.append("    document.getElementById('popup-close').addEventListener('click', () => {").append("\n");
		jsContent.append("        const popup = document.getElementById('popup');").append("\n");
		jsContent.append("        popup.style.display = 'none';").append("\n");
		jsContent.append("    });").append("\n");
		jsContent.append("});").append("\n");
		jsContent.append("\n");
		jsContent.append("function restoreState(questionId) {").append("\n");
		jsContent.append("    const selectedAnswerText = localStorage.getItem(`selectedAnswer-${questionId}`);").append("\n");
		jsContent.append("    document.querySelectorAll('.reponse').forEach(reponse => {").append("\n");
		jsContent.append("        if (reponse.innerHTML === selectedAnswerText) {").append("\n");
		jsContent.append("            reponse.classList.add('selected');").append("\n");
		jsContent.append("            if (reponse.classList.contains('bonne-reponse')) {").append("\n");
		jsContent.append("                reponse.style.backgroundColor = 'green';").append("\n");
		jsContent.append("            } else {").append("\n");
		jsContent.append("                reponse.style.backgroundColor = 'red';").append("\n");
		jsContent.append("            }").append("\n");
		jsContent.append("        }").append("\n");
		jsContent.append("    });").append("\n");
		jsContent.append("    document.querySelectorAll('.bonne-reponse').forEach(goodAnswer => {").append("\n");
		jsContent.append("        goodAnswer.style.backgroundColor = 'green';").append("\n");
		jsContent.append("    });").append("\n");
		jsContent.append("    const savedPopupText = localStorage.getItem(`popupText-${questionId}`);").append("\n");
		jsContent.append("    if (savedPopupText) {").append("\n");
		jsContent.append("        document.getElementById('popup-text').innerHTML = savedPopupText;").append("\n");
		jsContent.append("    }").append("\n");
		jsContent.append("}").append("\n");
		jsContent.append("\n");
		jsContent.append("function randomizeOrder(selector, parentSelector) {").append("\n");
		jsContent.append("    const items = document.querySelectorAll(selector);").append("\n");
		jsContent.append("    const parent = document.querySelector(parentSelector);").append("\n");
		jsContent.append("    const shuffledItems = Array.from(items).sort(() => Math.random() - 0.5);").append("\n");
		jsContent.append("    shuffledItems.forEach(item => parent.appendChild(item));").append("\n");
		jsContent.append("}").append("\n");
	
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/scriptReponseUnique.js"))) {
			writer.write(jsContent.toString());
			System.out.println("Le fichier JS a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
				
	public void ecrireJsElimination() 
	{
		StringBuilder jsContent = new StringBuilder();
	
		jsContent.append("document.addEventListener('DOMContentLoaded', () => {").append("\n");
		jsContent.append("    const questionId = document.querySelector('.question').getAttribute('id');").append("\n\n");
		jsContent.append("    // Mélanger les réponses").append("\n");
		jsContent.append("    randomizeOrder('.reponse', '.question');").append("\n\n");
		jsContent.append("    // Initialiser les points à partir du localStorage").append("\n");
		jsContent.append("    let totalPoints = localStorage.getItem('points') ? parseFloat(localStorage.getItem('points')) : 0;").append("\n");
		jsContent.append("    document.getElementById('points').textContent = `Points : ${totalPoints}`;").append("\n\n");
		jsContent.append("    // Initialiser les points potentiels").append("\n");
		jsContent.append("    let potentialPoints = 1; // Suppose que chaque question vaut 1 point par défaut").append("\n\n");
		jsContent.append("    // Élimer les mauvaises réponses dans l'ordre spécifié").append("\n");
		jsContent.append("    const eliminationOrder = Array.from(document.querySelectorAll('.mauvaise-reponse[data-order]'))").append("\n");
		jsContent.append("        .sort((a, b) => a.getAttribute('data-order') - b.getAttribute('data-order'));").append("\n\n");
		jsContent.append("    let currentIndex = 0;").append("\n\n");
		jsContent.append("    document.getElementById('eliminar').addEventListener('click', () => {").append("\n");
		jsContent.append("        if (currentIndex < eliminationOrder.length) {").append("\n");
		jsContent.append("            const toEliminate = eliminationOrder[currentIndex];").append("\n");
		jsContent.append("            const ptselim = parseFloat(toEliminate.getAttribute('ptselim'));").append("\n");
		jsContent.append("            toEliminate.innerHTML = `<span style='color: red;'>${ptselim} pts</span>`;").append("\n");
		jsContent.append("            toEliminate.style.cursor = 'default';").append("\n");
		jsContent.append("            toEliminate.classList.add('eliminated');").append("\n");
		jsContent.append("            currentIndex++;").append("\n\n");
		jsContent.append("            // Mise à jour des points potentiels").append("\n");
		jsContent.append("            potentialPoints += ptselim;").append("\n\n");
		jsContent.append("            // Sauvegarder l'ordre d'élimination").append("\n");
		jsContent.append("            localStorage.setItem(`eliminationOrder-${questionId}`, currentIndex);").append("\n");
		jsContent.append("            localStorage.setItem(`potentialPoints-${questionId}`, potentialPoints);").append("\n");
		jsContent.append("        }").append("\n");
		jsContent.append("    });").append("\n\n");
		jsContent.append("    // Gérer la sélection des réponses").append("\n");
		jsContent.append("    let selectedAnswer = null;").append("\n");
		jsContent.append("    let isValidationDone = localStorage.getItem(`isValidationDone-${questionId}`) === 'true';").append("\n");
		jsContent.append("    if (isValidationDone) {").append("\n");
		jsContent.append("        restoreState();").append("\n");
		jsContent.append("        initializeToFeedback();").append("\n");
		jsContent.append("    }").append("\n\n");
		jsContent.append("    document.querySelectorAll('.reponse').forEach(reponse => {").append("\n");
		jsContent.append("        reponse.addEventListener('click', () => {").append("\n");
		jsContent.append("            if (!isValidationDone && !reponse.classList.contains('eliminated')) {").append("\n");
		jsContent.append("                if (selectedAnswer) {").append("\n");
		jsContent.append("                    selectedAnswer.classList.remove('selected');").append("\n");
		jsContent.append("                }").append("\n");
		jsContent.append("                reponse.classList.add('selected');").append("\n");
		jsContent.append("                selectedAnswer = reponse;").append("\n");
		jsContent.append("            }").append("\n");
		jsContent.append("        });").append("\n");
		jsContent.append("    });").append("\n\n");
		jsContent.append("    // Valider la réponse sélectionnée").append("\n");
		jsContent.append("    document.getElementById('valider').addEventListener('click', () => {").append("\n");
		jsContent.append("        if (!isValidationDone) {").append("\n");
		jsContent.append("            if (selectedAnswer) {").append("\n");
		jsContent.append("                const popup = document.getElementById('popup');").append("\n");
		jsContent.append("                const popupText = document.getElementById('popup-text');").append("\n");
		jsContent.append("                const feedbackText = popupText.innerText.trim();").append("\n\n");
		jsContent.append("                if (selectedAnswer.classList.contains('bonne-reponse')) {").append("\n");
		jsContent.append("                    popupText.innerHTML = '<span style=\"color: green;\">Bonne réponse !</span>';").append("\n");
		jsContent.append("                    selectedAnswer.style.backgroundColor = 'green';").append("\n");
		jsContent.append("                    totalPoints += potentialPoints; // Ajouter les points potentiels au score total").append("\n");
		jsContent.append("                    localStorage.setItem('points', totalPoints);").append("\n");
		jsContent.append("                    document.getElementById('points').textContent = `Points : ${totalPoints}`;").append("\n");
		jsContent.append("                } else {").append("\n");
		jsContent.append("                    popupText.innerHTML = '<span style=\"color: red;\">Mauvaise réponse !</span>';").append("\n");
		jsContent.append("                    selectedAnswer.style.backgroundColor = 'red';").append("\n");
		jsContent.append("                    document.querySelector('.bonne-reponse').style.backgroundColor = 'green';").append("\n");
		jsContent.append("                }").append("\n\n");
		jsContent.append("                if (feedbackText) {").append("\n");
		jsContent.append("                    popupText.innerHTML += `<p>${feedbackText}</p>`;").append("\n");
		jsContent.append("                }").append("\n\n");
		jsContent.append("                popup.style.display = 'flex';").append("\n");
		jsContent.append("                isValidationDone = true;").append("\n\n");
		jsContent.append("                // Transformer le bouton 'Valider' en 'Feedback'").append("\n");
		jsContent.append("                initializeToFeedback();").append("\n\n");
		jsContent.append("                // Sauvegarder l'état").append("\n");
		jsContent.append("                localStorage.setItem(`isValidationDone-${questionId}`, true);").append("\n");
		jsContent.append("                localStorage.setItem(`selectedAnswer-${questionId}`, selectedAnswer.getAttribute('id'));").append("\n");
		jsContent.append("                localStorage.setItem(`popupText-${questionId}`, popupText.innerHTML);").append("\n");
		jsContent.append("            } else {").append("\n");
		jsContent.append("                alert('Veuillez sélectionner une réponse.');").append("\n");
		jsContent.append("            }").append("\n");
		jsContent.append("        } else {").append("\n");
		jsContent.append("            const popup = document.getElementById('popup');").append("\n");
		jsContent.append("            popup.style.display = 'flex';").append("\n");
		jsContent.append("        }").append("\n");
		jsContent.append("    });").append("\n\n");
		jsContent.append("    function initializeToFeedback() {").append("\n");
		jsContent.append("        const validerButton = document.getElementById('valider');").append("\n");
		jsContent.append("        validerButton.textContent = 'Feedback';").append("\n");
		jsContent.append("        validerButton.removeEventListener('click', this);").append("\n");
		jsContent.append("        validerButton.addEventListener('click', () => {").append("\n");
		jsContent.append("            const popup = document.getElementById('popup');").append("\n");
		jsContent.append("            popup.style.display = 'flex';").append("\n");
		jsContent.append("        });").append("\n");
		jsContent.append("    }").append("\n\n");
		jsContent.append("    function restoreState() {").append("\n");
		jsContent.append("        const savedSelectedAnswerId = localStorage.getItem(`selectedAnswer-${questionId}`);").append("\n");
		jsContent.append("        if (savedSelectedAnswerId) {").append("\n");
		jsContent.append("            const savedSelectedAnswer = document.getElementById(savedSelectedAnswerId);").append("\n");
		jsContent.append("            if (savedSelectedAnswer) {").append("\n");
		jsContent.append("                savedSelectedAnswer.classList.add('selected');").append("\n");
		jsContent.append("                if (savedSelectedAnswer.classList.contains('bonne-reponse')) {").append("\n");
		jsContent.append("                    savedSelectedAnswer.style.backgroundColor = 'green';").append("\n");
		jsContent.append("                } else {").append("\n");
		jsContent.append("                    savedSelectedAnswer.style.backgroundColor = 'red';").append("\n");
		jsContent.append("                }").append("\n");
		jsContent.append("            }").append("\n");
		jsContent.append("        }").append("\n");
		jsContent.append("        const savedPopupText = localStorage.getItem(`popupText-${questionId}`);").append("\n");
		jsContent.append("        if (savedPopupText) {").append("\n");
		jsContent.append("            document.getElementById('popup-text').innerHTML = savedPopupText;").append("\n");
		jsContent.append("        }").append("\n");
		jsContent.append("        document.querySelectorAll('.bonne-reponse').forEach(goodAnswer => {").append("\n");
		jsContent.append("            goodAnswer.style.backgroundColor = 'green';").append("\n");
		jsContent.append("        });").append("\n\n");
		jsContent.append("        const savedEliminationOrder = parseInt(localStorage.getItem(`eliminationOrder-${questionId}`), 10);").append("\n");
		jsContent.append("        for (let i = 0; i < savedEliminationOrder; i++) {").append("\n");
		jsContent.append("            const toEliminate = eliminationOrder[i];").append("\n");
		jsContent.append("            const ptselim = parseFloat(toEliminate.getAttribute('ptselim'));").append("\n");
		jsContent.append("            toEliminate.innerHTML = `<span style='color: red;'>${ptselim} pts</span>`;").append("\n");
		jsContent.append("            toEliminate.style.cursor = 'default';").append("\n");
		jsContent.append("            toEliminate.classList.add('eliminated');").append("\n");
		jsContent.append("        }").append("\n");
		jsContent.append("    }").append("\n\n");
		jsContent.append("    // Fermer le pop-up").append("\n");
		jsContent.append("    document.getElementById('popup-close').addEventListener('click', () => {").append("\n");
		jsContent.append("        const popup = document.getElementById('popup');").append("\n");
		jsContent.append("        popup.style.display = 'none';").append("\n");
		jsContent.append("    });").append("\n");
		jsContent.append("});").append("\n\n");
		jsContent.append("function randomizeOrder(selector, parentSelector) {").append("\n");
		jsContent.append("    const items = document.querySelectorAll(selector);").append("\n");
		jsContent.append("    const parent = document.querySelector(parentSelector);").append("\n");
		jsContent.append("    const shuffledItems = Array.from(items).sort(() => Math.random() - 0.5);").append("\n");
		jsContent.append("    shuffledItems.forEach(item => parent.appendChild(item));").append("\n");
		jsContent.append("}").append("\n");
	
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/scriptElimination.js"))) {
			writer.write(jsContent.toString());
			System.out.println("Le fichier JS a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ecrireJsAssociation()
	{
		StringBuilder jsContent = new StringBuilder();
	
		jsContent.append("document.addEventListener('DOMContentLoaded', () => {").append("\n");
		jsContent.append("    const pointsElem = document.getElementById('points');").append("\n");
		jsContent.append("    let totalPoints = parseFloat(localStorage.getItem('points')) || 0;").append("\n");
		jsContent.append("    pointsElem.textContent = `Points : ${totalPoints}`;  // Mise à jour des points affichés").append("\n");
		jsContent.append("\n");
		jsContent.append("    randomizeOrder('.word', '#words');").append("\n");
		jsContent.append("    randomizeOrder('.definition', '#definitions');").append("\n");
		jsContent.append("    \n");
		jsContent.append("    const questionId = getQuestionId();").append("\n");
		jsContent.append("    loadPreviousState(questionId);").append("\n");
		jsContent.append("    addWordListeners();").append("\n");
		jsContent.append("    addDefinitionListeners();").append("\n");
		jsContent.append("});").append("\n");
		jsContent.append("\n");
		jsContent.append("function getQuestionId() {").append("\n");
		jsContent.append("    const url = window.location.pathname;").append("\n");
		jsContent.append("    const filename = url.substring(url.lastIndexOf('/') + 1);").append("\n");
		jsContent.append("    const questionId = filename.match(/\\d+/);").append("\n");
		jsContent.append("    return questionId ? parseInt(questionId[0], 10) : null;  // Retourne le numéro de la page").append("\n");
		jsContent.append("}").append("\n");
		jsContent.append("\n");
		jsContent.append("function randomizeOrder(selector, parentSelector) {").append("\n");
		jsContent.append("    const items = document.querySelectorAll(selector);").append("\n");
		jsContent.append("    const parent = document.querySelector(parentSelector);").append("\n");
		jsContent.append("    const shuffledItems = Array.from(items).sort(() => Math.random() - 0.5);").append("\n");
		jsContent.append("    shuffledItems.forEach(item => parent.appendChild(item));").append("\n");
		jsContent.append("}").append("\n");
		jsContent.append("\n");
		jsContent.append("let motSelectionne = null;").append("\n");
		jsContent.append("let connexions = {};").append("\n");
		jsContent.append("let reverseConnexions = {};").append("\n");
		jsContent.append("let feedbackClicked = false;").append("\n");
		jsContent.append("let isValidationDone = false;").append("\n");
		jsContent.append("\n");
		jsContent.append("function addWordListeners() {").append("\n");
		jsContent.append("    document.querySelectorAll('.word').forEach(mot => {").append("\n");
		jsContent.append("        mot.addEventListener('click', () => {").append("\n");
		jsContent.append("            if (!isValidationDone) {").append("\n");
		jsContent.append("                motSelectionne = mot;").append("\n");
		jsContent.append("                clearSelection();").append("\n");
		jsContent.append("                mot.style.backgroundColor = '#d3d3d3';").append("\n");
		jsContent.append("            }").append("\n");
		jsContent.append("        });").append("\n");
		jsContent.append("    });").append("\n");
		jsContent.append("}").append("\n");
		jsContent.append("\n");
		jsContent.append("function addDefinitionListeners() {").append("\n");
		jsContent.append("    document.querySelectorAll('.definition').forEach(definition => {").append("\n");
		jsContent.append("        definition.addEventListener('click', () => {").append("\n");
		jsContent.append("            if (motSelectionne && !isValidationDone) {").append("\n");
		jsContent.append("                let motId = motSelectionne.getAttribute('data-id');").append("\n");
		jsContent.append("                let defId = definition.getAttribute('data-id');").append("\n");
		jsContent.append("\n");
		jsContent.append("                if (connexions[motId]) {").append("\n");
		jsContent.append("                    let ancienDefId = connexions[motId];").append("\n");
		jsContent.append("                    delete reverseConnexions[ancienDefId];").append("\n");
		jsContent.append("                    delete connexions[motId];").append("\n");
		jsContent.append("                    removeLine(motSelectionne, document.querySelector(`.definition[data-id='${ancienDefId}']`));").append("\n");
		jsContent.append("                }").append("\n");
		jsContent.append("\n");
		jsContent.append("                if (reverseConnexions[defId]) {").append("\n");
		jsContent.append("                    let ancienMotId = reverseConnexions[defId];").append("\n");
		jsContent.append("                    delete connexions[ancienMotId];").append("\n");
		jsContent.append("                    delete reverseConnexions[defId];").append("\n");
		jsContent.append("                    removeLine(document.querySelector(`.word[data-id='${ancienMotId}']`), definition);").append("\n");
		jsContent.append("                }").append("\n");
		jsContent.append("\n");
		jsContent.append("                connexions[motId] = defId;").append("\n");
		jsContent.append("                reverseConnexions[defId] = motId;").append("\n");
		jsContent.append("\n");
		jsContent.append("                drawLine(motSelectionne, definition, 'black');").append("\n");
		jsContent.append("                clearSelection();").append("\n");
		jsContent.append("            }").append("\n");
		jsContent.append("        });").append("\n");
		jsContent.append("    });").append("\n");
		jsContent.append("}").append("\n");
		jsContent.append("\n");
		jsContent.append("function drawLine(sujet, proposition, color) {").append("\n");
		jsContent.append("    const svg = document.getElementById('svg-container');").append("\n");
		jsContent.append("    const sujetRect = sujet.getBoundingClientRect();").append("\n");
		jsContent.append("    const propositionRect = proposition.getBoundingClientRect();").append("\n");
		jsContent.append("    const containerRect = svg.getBoundingClientRect();").append("\n");
		jsContent.append("\n");
		jsContent.append("    const x1 = sujetRect.right - containerRect.left;").append("\n");
		jsContent.append("    const y1 = sujetRect.top + sujetRect.height / 2 - containerRect.top;").append("\n");
		jsContent.append("    const x2 = propositionRect.left - containerRect.left;").append("\n");
		jsContent.append("    const y2 = propositionRect.top + propositionRect.height / 2 - containerRect.top;").append("\n");
		jsContent.append("\n");
		jsContent.append("    const path = document.createElementNS('http://www.w3.org/2000/svg', 'path');").append("\n");
		jsContent.append("    const d = `M ${x1},${y1} C ${(x1 + x2) / 2},${y1} ${(x1 + x2) / 2},${y2} ${x2},${y2}`;").append("\n");
		jsContent.append("    path.setAttribute('d', d);").append("\n");
		jsContent.append("    path.setAttribute('stroke', color);").append("\n");
		jsContent.append("    path.setAttribute('stroke-width', '2');").append("\n");
		jsContent.append("    path.setAttribute('fill', 'none');").append("\n");
		jsContent.append("    path.setAttribute('class', `line-${sujet.getAttribute('data-id')}-${proposition.getAttribute('data-id')}`);").append("\n");
		jsContent.append("\n");
		jsContent.append("    svg.appendChild(path);").append("\n");
		jsContent.append("}").append("\n");
		jsContent.append("\n");
		jsContent.append("function removeLine(sujet, proposition) {").append("\n");
		jsContent.append("    const svg = document.getElementById('svg-container');").append("\n");
		jsContent.append("    const line = svg.querySelector(`.line-${sujet.getAttribute('data-id')}-${proposition.getAttribute('data-id')}`);").append("\n");
		jsContent.append("    if (line) {").append("\n");
		jsContent.append("        svg.removeChild(line);").append("\n");
		jsContent.append("    }").append("\n");
		jsContent.append("}").append("\n");
		jsContent.append("\n");
		jsContent.append("function clearSelection() {").append("\n");
		jsContent.append("    document.querySelectorAll('.word').forEach(mot => {").append("\n");
		jsContent.append("        mot.style.backgroundColor = '#e3e3e3';").append("\n");
		jsContent.append("    });").append("\n");
		jsContent.append("}").append("\n");
		jsContent.append("\n");
		jsContent.append("function validate(questionId) {").append("\n");
		jsContent.append("    let correct = true;").append("\n");
		jsContent.append("    let totalAssociations = Object.keys(connexions).length;").append("\n");
		jsContent.append("    let mots = document.querySelectorAll('.word').length;").append("\n");
		jsContent.append("\n");
		jsContent.append("    if (totalAssociations < mots && !isValidationDone) {").append("\n");
		jsContent.append("        alert('Il manque des associations.'); // Utiliser une alerte").append("\n");
		jsContent.append("        return;").append("\n");
		jsContent.append("    }").append("\n");
		jsContent.append("\n");
		jsContent.append("    for (let motId in connexions) {").append("\n");
		jsContent.append("        const isCorrect = connexions[motId] == motId;").append("\n");
		jsContent.append("        drawLine(").append("\n");
		jsContent.append("            document.querySelector(`.word[data-id='${motId}']`),").append("\n");
		jsContent.append("            document.querySelector(`.definition[data-id='${connexions[motId]}']`),").append("\n");
		jsContent.append("            isCorrect ? 'green' : 'red'").append("\n");
		jsContent.append("        );").append("\n");
		jsContent.append("        if (!isCorrect) {").append("\n");
		jsContent.append("            correct = false;").append("\n");
		jsContent.append("        }").append("\n");
		jsContent.append("    }").append("\n");
		jsContent.append("\n");
		jsContent.append("    let popupMessage;").append("\n");
		jsContent.append("    if (correct) {").append("\n");
		jsContent.append("        popupMessage = '<span style=\"color: green;\">Bravo! Toutes les associations sont correctes.</span>';").append("\n");
		jsContent.append("        if (!feedbackClicked && !isValidationDone) {").append("\n");
		jsContent.append("            updatePoints(1);  // Ajouter des points pour les réponses correctes").append("\n");
		jsContent.append("        }").append("\n");
		jsContent.append("    } else {").append("\n");
		jsContent.append("        popupMessage = '<span style=\"color: red;\">Désolé, certaines associations sont incorrectes.</span>';").append("\n");
		jsContent.append("    }").append("\n");
		jsContent.append("\n");
		jsContent.append("    showPopup(popupMessage);").append("\n");
		jsContent.append("\n");
		jsContent.append("    isValidationDone = true;").append("\n");
		jsContent.append("    feedbackClicked = true;").append("\n");
		jsContent.append("    localStorage.setItem(`isValidationDone-${questionId}`, 'true');").append("\n");
		jsContent.append("    localStorage.setItem(`connexions-${questionId}`, JSON.stringify(connexions));").append("\n");
		jsContent.append("    localStorage.setItem(`popupMessage-${questionId}`, popupMessage);").append("\n");
		jsContent.append("\n");
		jsContent.append("    document.querySelector(`.btn[onclick^=\"validate(${questionId})\"]`).textContent = 'Feedback';").append("\n");
		jsContent.append("}").append("\n");
		jsContent.append("\n");
		jsContent.append("function updatePoints(points) {").append("\n");
		jsContent.append("    let totalPoints = parseFloat(localStorage.getItem('points')) || 0;").append("\n");
		jsContent.append("    totalPoints += points;").append("\n");
		jsContent.append("    localStorage.setItem('points', totalPoints);").append("\n");
		jsContent.append("    document.getElementById('points').textContent = `Points : ${totalPoints}`;").append("\n");
		jsContent.append("}").append("\n");
		jsContent.append("\n");
		jsContent.append("function showPopup(message) {").append("\n");
		jsContent.append("    const popup = document.getElementById('popup');").append("\n");
		jsContent.append("    const popupText = document.getElementById('popup-text');").append("\n");
		jsContent.append("    popupText.innerHTML = message;").append("\n");
		jsContent.append("    popup.style.display = 'flex';").append("\n");
		jsContent.append("}").append("\n");
		jsContent.append("\n");
		jsContent.append("document.getElementById('popup-close').addEventListener('click', () => {").append("\n");
		jsContent.append("    const popup = document.getElementById('popup');").append("\n");
		jsContent.append("    popup.style.display = 'none';").append("\n");
		jsContent.append("});").append("\n");
		jsContent.append("\n");
		jsContent.append("function loadPreviousState(questionId) {").append("\n");
		jsContent.append("    if (localStorage.getItem(`isValidationDone-${questionId}`) === 'true') {").append("\n");
		jsContent.append("        isValidationDone = true;").append("\n");
		jsContent.append("        const savedConnexions = JSON.parse(localStorage.getItem(`connexions-${questionId}`));").append("\n");
		jsContent.append("        let correct = true;").append("\n");
		jsContent.append("        for (let motId in savedConnexions) {").append("\n");
		jsContent.append("            const defId = savedConnexions[motId];").append("\n");
		jsContent.append("            const mot = document.querySelector(`.word[data-id='${motId}']`);").append("\n");
		jsContent.append("            const definition = document.querySelector(`.definition[data-id='${defId}']`);").append("\n");
		jsContent.append("            const isCorrect = motId == defId;").append("\n");
		jsContent.append("            drawLine(mot, definition, isCorrect ? 'green' : 'red');").append("\n");
		jsContent.append("            connexions[motId] = defId;").append("\n");
		jsContent.append("            reverseConnexions[defId] = motId;").append("\n");
		jsContent.append("            if (!isCorrect) {").append("\n");
		jsContent.append("                correct = false;").append("\n");
		jsContent.append("            }").append("\n");
		jsContent.append("        }").append("\n");
		jsContent.append("\n");
		jsContent.append("        const savedPopupMessage = localStorage.getItem(`popupMessage-${questionId}`);").append("\n");
		jsContent.append("        if (savedPopupMessage) {").append("\n");
		jsContent.append("            document.querySelector(`.btn[onclick^=\"validate(${questionId})\"]`).addEventListener('click', () => {").append("\n");
		jsContent.append("                showPopup(savedPopupMessage);").append("\n");
		jsContent.append("            });").append("\n");
		jsContent.append("        }").append("\n");
		jsContent.append("        document.querySelector(`.btn[onclick^=\"validate(${questionId})\"]`).textContent = 'Feedback';").append("\n");
		jsContent.append("    }").append("\n");
		jsContent.append("}").append("\n");
	
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/scriptAssociation.js"))) {
			writer.write(jsContent.toString());
			System.out.println("Le fichier JS a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ecrireJsTimer()
	{
		StringBuilder jsContent = new StringBuilder();

		jsContent.append("const timerElement = document.getElementById('timer');").append("\n");
		jsContent.append("").append("\n");
		jsContent.append("let timeRemaining = parseInt(timerElement.textContent, 10);").append("\n");
		jsContent.append("").append("\n");
		jsContent.append("function formatTime(seconds) {").append("\n");
		jsContent.append("    const minutes = Math.floor(seconds / 60);").append("\n");
		jsContent.append("    const secs = seconds % 60;").append("\n");
		jsContent.append("    return `${String(minutes).padStart(2, '0')}:${String(secs).padStart(2, '0')}`;").append("\n");
		jsContent.append("}").append("\n");
		jsContent.append("").append("\n");
		jsContent.append("timerElement.textContent = formatTime(timeRemaining);").append("\n");
		jsContent.append("").append("\n");
		jsContent.append("function countdown() {").append("\n");
		jsContent.append("    if (timeRemaining > 0) {").append("\n");
		jsContent.append("        timeRemaining--;").append("\n");
		jsContent.append("        timerElement.textContent = formatTime(timeRemaining);").append("\n");
		jsContent.append("    } else {").append("\n");
		jsContent.append("        clearInterval(timerInterval);").append("\n");
		jsContent.append("        alert(\"Le temps est écoulé !\");").append("\n");
		jsContent.append("    }").append("\n");
		jsContent.append("}").append("\n");
		jsContent.append("").append("\n");
		jsContent.append("const timerInterval = setInterval(countdown, 1000);").append("\n");

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/timer.js"))) {
			writer.write(jsContent.toString());
			System.out.println("Le fichier JS a été généré avec succès !");
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
		cssContent.append("    transition: background-color 0.3s ease;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");
		cssContent.append(".word:hover, .definition:hover {").append("\n");
		cssContent.append("    background-color: #b3d9ff;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");
		cssContent.append(".btn {").append("\n");
		cssContent.append("    margin: 10px 5px;").append("\n");
		cssContent.append("    padding: 10px 20px;").append("\n");
		cssContent.append("    background-color: #4CAF50;").append("\n");
		cssContent.append("    color: white;").append("\n");
		cssContent.append("    border: none;").append("\n");
		cssContent.append("    border-radius: 5px;").append("\n");
		cssContent.append("    cursor: pointer;").append("\n");
		cssContent.append("    transition: background-color 0.3s ease, transform 0.3s ease;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");
		cssContent.append(".btn:hover {").append("\n");
		cssContent.append("    background-color: #45a049;").append("\n");
		cssContent.append("    transform: translateY(-2px);").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");
		cssContent.append("svg {").append("\n");
		cssContent.append("    position: absolute;").append("\n");
		cssContent.append("    width: 100%;").append("\n");
		cssContent.append("    height: 100%;").append("\n");
		cssContent.append("    pointer-events: none;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");
		cssContent.append(".popup {").append("\n");
		cssContent.append("    display: none;").append("\n");
		cssContent.append("    position: fixed;").append("\n");
		cssContent.append("    top: 0;").append("\n");
		cssContent.append("    left: 0;").append("\n");
		cssContent.append("    width: 100%;").append("\n");
		cssContent.append("    height: 100%;").append("\n");
		cssContent.append("    background-color: rgba(0, 0, 0, 0.5);").append("\n");
		cssContent.append("    justify-content: center;").append("\n");
		cssContent.append("    align-items: center;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");
		cssContent.append(".popup-content {").append("\n");
		cssContent.append("    background-color: #fff;").append("\n");
		cssContent.append("    padding: 20px;").append("\n");
		cssContent.append("    border-radius: 5px;").append("\n");
		cssContent.append("    text-align: center;").append("\n");
		cssContent.append("    position: relative;").append("\n");
		cssContent.append("    width: 50%;").append("\n"); // Ajout pour centrer et ajuster la taille
		cssContent.append("    margin: 0 auto;").append("\n"); // Ajout pour centrer et ajuster la taille
		cssContent.append("}").append("\n");
		cssContent.append("\n");
		cssContent.append(".close {").append("\n");
		cssContent.append("    position: absolute;").append("\n");
		cssContent.append("    top: 10px;").append("\n");
		cssContent.append("    right: 10px;").append("\n");
		cssContent.append("    font-size: 20px;").append("\n"); // Ajusté pour être plus petit
		cssContent.append("    cursor: pointer;").append("\n");
		cssContent.append("    color: #333;").append("\n"); // Couleur du texte
		cssContent.append("}").append("\n");
	
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/styleAssociation.css"))) {
			writer.write(cssContent.toString());
			System.out.println("Le fichier CSS a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	public void ecrireCSSElimination() {
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
		cssContent.append("    transition: background-color 0.3s ease;").append("\n");
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
		cssContent.append("    transition: background-color 0.3s ease;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");
	
		cssContent.append("button:hover {").append("\n");
		cssContent.append("    background-color: #45a049;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");
	
		cssContent.append(".popup {").append("\n");
		cssContent.append("    display: none;").append("\n");
		cssContent.append("    position: fixed;").append("\n");
		cssContent.append("    top: 0;").append("\n");
		cssContent.append("    left: 0;").append("\n");
		cssContent.append("    width: 100%;").append("\n");
		cssContent.append("    height: 100%;").append("\n");
		cssContent.append("    background-color: rgba(0,0,0,0.5);").append("\n");
		cssContent.append("    justify-content: center;").append("\n");
		cssContent.append("    align-items: center;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");
	
		cssContent.append(".popup-content {").append("\n");
		cssContent.append("    background-color: #fff;").append("\n");
		cssContent.append("    padding: 20px;").append("\n");
		cssContent.append("    border-radius: 5px;").append("\n");
		cssContent.append("    text-align: center;").append("\n");
		cssContent.append("    position: relative;").append("\n");
		cssContent.append("    width: 50%;").append("\n"); // Ajout pour centrer et ajuster la taille
		cssContent.append("    margin: 0 auto;").append("\n"); // Ajout pour centrer et ajuster la taille
		cssContent.append("}").append("\n");
		cssContent.append("\n");
	
		cssContent.append(".close {").append("\n");
		cssContent.append("    position: absolute;").append("\n");
		cssContent.append("    top: 10px;").append("\n");
		cssContent.append("    right: 10px;").append("\n");
		cssContent.append("    font-size: 20px;").append("\n"); // Ajusté pour être plus petit
		cssContent.append("    cursor: pointer;").append("\n");
		cssContent.append("    color: #333;").append("\n"); // Couleur du texte
		cssContent.append("}").append("\n");
	
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/styleElimination.css"))) {
			writer.write(cssContent.toString());
			System.out.println("Le fichier CSS a été généré avec succès !");
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
		cssContent.append("\n");

		cssContent.append(".popup {").append("\n");
		cssContent.append("    display: none;").append("\n");
		cssContent.append("    position: fixed;").append("\n");
		cssContent.append("    top: 0;").append("\n");
		cssContent.append("    left: 0;").append("\n");
		cssContent.append("    width: 100%;").append("\n");
		cssContent.append("    height: 100%;").append("\n");
		cssContent.append("    background-color: rgba(0, 0, 0, 0.5);").append("\n");
		cssContent.append("    justify-content: center;").append("\n");
		cssContent.append("    align-items: center;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");

		cssContent.append(".popup-content {").append("\n");
		cssContent.append("    background-color: #fff;").append("\n");
		cssContent.append("    padding: 20px;").append("\n");
		cssContent.append("    border-radius: 5px;").append("\n");
		cssContent.append("    text-align: center;").append("\n");
		cssContent.append("    position: relative;").append("\n");
		cssContent.append("    width: 50%;").append("\n");
		cssContent.append("    margin: 0 auto;").append("\n");
		cssContent.append("}").append("\n");
		cssContent.append("\n");

		cssContent.append(".close {").append("\n");
		cssContent.append("    position: absolute;").append("\n");
		cssContent.append("    top: 10px;").append("\n");
		cssContent.append("    right: 10px;").append("\n");
		cssContent.append("    font-size: 20px;").append("\n");
		cssContent.append("    cursor: pointer;").append("\n");
		cssContent.append("    color: #333;").append("\n");
		cssContent.append("}").append("\n");

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/styleReponseUnique.css"))) {
			writer.write(cssContent.toString());
			System.out.println("Le fichier CSS a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
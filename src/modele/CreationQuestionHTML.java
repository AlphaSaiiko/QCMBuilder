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

	public void pageQuestionElimination(Question question)
	{
		StringBuilder htmlContent = new StringBuilder();
				htmlContent.append("<!DOCTYPE html>").append("\n");
				htmlContent.append("<html lang=\"fr\">").append("\n");
				htmlContent.append("\n");
				htmlContent.append("<head>").append("\n");
				htmlContent.append("    <meta charset=\"UTF-8\">").append("\n");
				htmlContent.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">").append("\n");
				htmlContent.append("    <title>QCM Elimination</title>").append("\n");
				htmlContent.append("    <link rel=\"stylesheet\" href=\"style.css\">").append("\n");
				htmlContent.append("</head>").append("\n");
				htmlContent.append("\n");
				htmlContent.append("<body>").append("\n");
				htmlContent.append("    <div class=\"container\">").append("\n");
				htmlContent.append("        <h1>"+question.getEnonce()+"</h1>").append("\n");
				htmlContent.append("        <p class=\"qcm-type\">une seul réponse possible</p>").append("\n");
				htmlContent.append("        <div class=\"question\">").append("\n");
                for (IOption opt : question.getEnsOptions())
                {
                    htmlContent.append("            <div class=\"rectangle\" onclick=\"changeCouleur(this)\">"+opt.getEnonce()+"</div>").append("\n");
                }

				htmlContent.append("        </div>").append("\n");
				htmlContent.append("        <div class=\"buttons-container\">").append("\n");
				htmlContent.append("            <button onclick=\"precedent()\">Précédent</button>").append("\n");
				htmlContent.append("            <button onclick=\"feedback()\">Feedback</button>").append("\n");
				htmlContent.append("            <button onclick=\"valider()\">Valider</button>").append("\n");
				htmlContent.append("            <button onclick=\"window.location.href = 'fin.html';\">Suivant</button>").append("\n");
				htmlContent.append("            <button onclick=\"Eliminer()\">Eliminer</button>").append("\n");
				htmlContent.append("        </div>").append("\n");
				htmlContent.append("    </div>").append("\n");
				htmlContent.append("    <script src=\"script.js\"></script>").append("\n");
				htmlContent.append("    <script>").append("\n");
				htmlContent.append("        setDifficulte('facile');").append("\n");
				htmlContent.append("    </script>").append("\n");
				htmlContent.append("</body>").append("\n");
				htmlContent.append("\n");
				htmlContent.append("</html>").append("\n");

		// Sauvegarde dans un fichier HTML
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/page1.html"))) {
			writer.write(htmlContent.toString());
			System.out.println("Le fichier HTML a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public void ecrireJs()
	{
		StringBuilder jsContent = new StringBuilder();
		jsContent.append("let selectedElements = [];").append("\n");
        jsContent.append("let selectedElement = null;").append("\n");
        jsContent.append("let selectedSujet = null;").append("\n");
        jsContent.append("let selectedProposition = null;").append("\n");
        jsContent.append("let associations = {};").append("\n");
        jsContent.append("let reverseAssociations = {};").append("\n");
        jsContent.append("\n");
        jsContent.append("function Association(element) {").append("\n");
        jsContent.append("    if (element.parentElement.classList.contains('sujet')) {").append("\n");
        jsContent.append("        if (selectedSujet) {").append("\n");
        jsContent.append("            selectedSujet.style.border = 'none';").append("\n");
        jsContent.append("        }").append("\n");
        jsContent.append("        selectedSujet = element;").append("\n");
        jsContent.append("        selectedSujet.style.border = '2px solid blue';").append("\n");
        jsContent.append("    } else if (element.parentElement.classList.contains('proposition')) {").append("\n");
        jsContent.append("        if (selectedProposition) {").append("\n");
        jsContent.append("            selectedProposition.style.border = 'none';").append("\n");
        jsContent.append("        }").append("\n");
        jsContent.append("        selectedProposition = element;").append("\n");
        jsContent.append("        selectedProposition.style.border = '2px solid blue';").append("\n");
        jsContent.append("    }").append("\n");
        jsContent.append("\n");
        jsContent.append("    if (selectedSujet && selectedProposition) {").append("\n");
        jsContent.append("        if (associations[selectedSujet.innerText] || reverseAssociations[selectedProposition.innerText]) {").append("\n");
        jsContent.append("            alert(\"Chaque sujet et chaque proposition ne peuvent avoir qu'une seule liaison.\");").append("\n");
        jsContent.append("            selectedSujet.style.border = 'none';").append("\n");
        jsContent.append("            selectedProposition.style.border = 'none';").append("\n");
        jsContent.append("            selectedSujet = null;").append("\n");
        jsContent.append("            selectedProposition = null;").append("\n");
        jsContent.append("            return;").append("\n");
        jsContent.append("        }").append("\n");
        jsContent.append("\n");
        jsContent.append("        associations[selectedSujet.innerText] = selectedProposition.innerText;").append("\n");
        jsContent.append("        reverseAssociations[selectedProposition.innerText] = selectedSujet.innerText;").append("\n");
        jsContent.append("        selectedSujet.style.border = 'none';").append("\n");
        jsContent.append("        selectedProposition.style.border = 'none';").append("\n");
        jsContent.append("        selectedSujet = null;").append("\n");
        jsContent.append("        selectedProposition = null;").append("\n");
        jsContent.append("        redrawLines();").append("\n");
        jsContent.append("    }").append("\n");
        jsContent.append("}").append("\n");
        jsContent.append("\n");
        jsContent.append("function drawLine(sujet, proposition) {").append("\n");
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
        jsContent.append("    path.setAttribute('stroke', 'black');").append("\n");
        jsContent.append("    path.setAttribute('stroke-width', '2');").append("\n");
        jsContent.append("    path.setAttribute('fill', 'none');").append("\n");
        jsContent.append("\n");
        jsContent.append("    svg.appendChild(path);").append("\n");
        jsContent.append("}").append("\n");
        jsContent.append("\n");
        jsContent.append("function redrawLines() {").append("\n");
        jsContent.append("    const svg = document.getElementById('svg-container');").append("\n");
        jsContent.append("    svg.innerHTML = ''; // Clear existing lines").append("\n");
        jsContent.append("    for (const [sujetText, propositionText] of Object.entries(associations)) {").append("\n");
        jsContent.append("        const sujet = [...document.querySelectorAll('.sujet .rectangle')].find(el => el.innerText === sujetText);").append("\n");
        jsContent.append("        const proposition = [...document.querySelectorAll('.proposition .rectangle')].find(el => el.innerText === propositionText);").append("\n");
        jsContent.append("        if (sujet && proposition) {").append("\n");
        jsContent.append("            drawLine(sujet, proposition);").append("\n");
        jsContent.append("        }").append("\n");
        jsContent.append("    }").append("\n");
        jsContent.append("}").append("\n");
        jsContent.append("\n");
        jsContent.append("// Call redrawLines on window resize to ensure lines are correctly positioned").append("\n");
        jsContent.append("window.addEventListener('resize', redrawLines);").append("\n");
        jsContent.append("\n");
        jsContent.append("// Set a timer to redraw lines every 2 seconds").append("\n");
        jsContent.append("setInterval(redrawLines, 2000);").append("\n");
        jsContent.append("\n");
        jsContent.append("function changeCouleur(element) {").append("\n");
        jsContent.append("    if (selectedElement === element) {").append("\n");
        jsContent.append("        element.style.backgroundColor = 'grey';").append("\n");
        jsContent.append("        selectedElement = null;").append("\n");
        jsContent.append("    } else {").append("\n");
        jsContent.append("        if (selectedElement) {").append("\n");
        jsContent.append("            selectedElement.style.backgroundColor = 'grey';").append("\n");
        jsContent.append("        }").append("\n");
        jsContent.append("        element.style.backgroundColor = 'green';").append("\n");
        jsContent.append("        selectedElement = element;").append("\n");
        jsContent.append("    }").append("\n");
        jsContent.append("}").append("\n");
        jsContent.append("\n");
        jsContent.append("function changeCouleurMultiple(element) {").append("\n");
        jsContent.append("    const index = selectedElements.indexOf(element);").append("\n");
        jsContent.append("    if (index > -1) {").append("\n");
		jsContent.append("// Si l'élément est déjà sélectionné, le désélectionner").append("\n");
        jsContent.append("        element.style.backgroundColor = 'grey';").append("\n");
        jsContent.append("        selectedElements.splice(index, 1);").append("\n");
        jsContent.append("    } else {").append("\n");
        jsContent.append("        element.style.backgroundColor = 'green';").append("\n");
        jsContent.append("        selectedElements.push(element);").append("\n");
        jsContent.append("    }").append("\n");
        jsContent.append("}").append("\n\n");

        jsContent.append("function precedent() {").append("\n");
        jsContent.append("    history.go(-1);").append("\n");
        jsContent.append("}").append("\n\n");

        jsContent.append("function feedback() {").append("\n");
        jsContent.append("    window.location.href = 'feedbackQ1.html';").append("\n");
        jsContent.append("}").append("\n\n");

        jsContent.append("function valider() {").append("\n");
        jsContent.append("    if (selectedElements.length > 0) {").append("\n");
        jsContent.append("        window.location.href = 'QcmRéponseMultiple.html';").append("\n");
        jsContent.append("    } else {").append("\n");
        jsContent.append("        alert(\"Veuillez sélectionner au moins une réponse avant de valider.\");").append("\n");
        jsContent.append("    }").append("\n");
        jsContent.append("}").append("\n\n");

        jsContent.append("let bonnerep = \"Extends\";").append("\n\n");

        jsContent.append("function Eliminer() {").append("\n");
        jsContent.append("    const propositions = document.querySelectorAll('.question .rectangle');").append("\n");
        jsContent.append("    const mauvaisesReponses = Array.from(propositions).filter(proposition => {").append("\n");
        jsContent.append("        return proposition.innerText !== bonnerep;").append("\n");
        jsContent.append("    });").append("\n\n");
        jsContent.append("    if (mauvaisesReponses.length > 0) {").append("\n");
        jsContent.append("        const mauvaiseReponse = mauvaisesReponses[Math.floor(Math.random() * mauvaisesReponses.length)];").append("\n");
        jsContent.append("        mauvaiseReponse.style.display = 'none';").append("\n");
        jsContent.append("    } else {").append("\n");
        jsContent.append("        alert(\"Toutes les mauvaises réponses ont déjà été éliminées.\");").append("\n");
        jsContent.append("    }").append("\n");
        jsContent.append("}").append("\n\n");

        jsContent.append("function setDifficulte(difficulte) {").append("\n");
        jsContent.append("    const container = document.querySelector('.container');").append("\n");
        jsContent.append("    container.classList.remove('tresfacile', 'facile', 'moyen', 'difficile');").append("\n");
        jsContent.append("    if (difficulte === 'tresfacile') {").append("\n");
        jsContent.append("        container.classList.add('tresfacile');").append("\n");
        jsContent.append("    } else if (difficulte === 'facile') {").append("\n");
        jsContent.append("        container.classList.add('facile');").append("\n");
        jsContent.append("    } else if (difficulte === 'moyen') {").append("\n");
        jsContent.append("        container.classList.add('moyen');").append("\n");
        jsContent.append("    } else if (difficulte === 'difficile') {").append("\n");
        jsContent.append("        container.classList.add('difficile');").append("\n");
        jsContent.append("    }").append("\n");
        jsContent.append("}").append("\n\n");

        jsContent.append("function changeShadowColor() {").append("\n");
        jsContent.append("    const difficulties = ['tresfacile', 'facile', 'moyen', 'difficile'];").append("\n");
        jsContent.append("    let currentIndex = 0;").append("\n\n");
        jsContent.append("    setInterval(() => {").append("\n");
        jsContent.append("        setDifficulte(difficulties[currentIndex]);").append("\n");
        jsContent.append("        currentIndex = (currentIndex + 1) % difficulties.length;").append("\n");
        jsContent.append("    }, 3000); // Change every 3 seconds").append("\n");
        jsContent.append("}").append("\n");

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/script.js"))) {
			writer.write(jsContent.toString());
			System.out.println("Le fichier js a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public void pageQuestionChoixUnique(Question question) {
        StringBuilder htmlContent = new StringBuilder();
        
        htmlContent.append("<!DOCTYPE html>\n");
        htmlContent.append("<html lang=\"fr\">\n");
        htmlContent.append("<head>\n");
        htmlContent.append("    <meta charset=\"UTF-8\">\n");
        htmlContent.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        htmlContent.append("    <title>QCMBuilder : Question à choix multiple : Réponse Unique</title>\n");
        htmlContent.append("    <link rel=\"stylesheet\" href=\"style.css\">\n");
        htmlContent.append("</head>\n");
        htmlContent.append("<body>\n");
        htmlContent.append("    <div class=\"container\">\n");
        htmlContent.append("        <h1>").append(question.getEnonce()).append("</h1>\n");
        htmlContent.append("        <p class=\"qcm-type\">Une seule réponse possible</p>\n");
        htmlContent.append("        <div class=\"rectangles-container\">\n");
        
        for (IOption option : question.getEnsOptions())
        {
        htmlContent.append("            <div class=\"rectangle\" onclick=\"changeCouleur(this)\">").append(option.getEnonce()).append("</div>\n");
        }
        
        htmlContent.append("        </div>\n");
        htmlContent.append("        <div class=\"buttons-container\">\n");
        htmlContent.append("            <button onclick=\"precedent()\">Précédent</button>\n");
        htmlContent.append("            <button onclick=\"feedback()\">Feedback</button>\n");
        htmlContent.append("            <button onclick=\"valider()\">Valider</button>\n");
        htmlContent.append("            <button onclick=\"window.location.href = 'QcmRéponseMultiple.html';\">Suivant</button>\n");
        htmlContent.append("        </div>\n");
        htmlContent.append("    </div>\n");
        htmlContent.append("    <script src=\"script.js\"></script>\n");
        htmlContent.append("    <script>\n");
        htmlContent.append("        setDifficulte('tresfacile')\n");
        htmlContent.append("    </script>\n");
        htmlContent.append("</body>\n");
        htmlContent.append("</html>");
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("html/page2.html"))) {
			writer.write(htmlContent.toString());
			System.out.println("Le fichier js a été généré avec succès !");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	}
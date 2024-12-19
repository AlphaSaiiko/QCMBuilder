let selectedElements = [];
let selectedElement = null;
let selectedSujet = null;
let selectedProposition = null;
let associations = {};
let reverseAssociations = {};

function Association(element) {
	if (element.parentElement.classList.contains('sujet')) {
		if (selectedSujet) {
			selectedSujet.style.border = 'none';
		}
		selectedSujet = element;
		selectedSujet.style.border = '2px solid blue';
	} else if (element.parentElement.classList.contains('proposition')) {
		if (selectedProposition) {
			selectedProposition.style.border = 'none';
		}
		selectedProposition = element;
		selectedProposition.style.border = '2px solid blue';
	}

	if (selectedSujet && selectedProposition) {
		if (associations[selectedSujet.innerText] || reverseAssociations[selectedProposition.innerText]) {
			alert("Chaque sujet et chaque proposition ne peuvent avoir qu'une seule liaison.");
			selectedSujet.style.border = 'none';
			selectedProposition.style.border = 'none';
			selectedSujet = null;
			selectedProposition = null;
			return;
		}

		associations[selectedSujet.innerText] = selectedProposition.innerText;
		reverseAssociations[selectedProposition.innerText] = selectedSujet.innerText;
		selectedSujet.style.border = 'none';
		selectedProposition.style.border = 'none';
		selectedSujet = null;
		selectedProposition = null;
		redrawLines();
	}
}

function drawLine(sujet, proposition) {
	const svg = document.getElementById('svg-container');
	const sujetRect = sujet.getBoundingClientRect();
	const propositionRect = proposition.getBoundingClientRect();
	const containerRect = svg.getBoundingClientRect();

	const x1 = sujetRect.right - containerRect.left;
	const y1 = sujetRect.top + sujetRect.height / 2 - containerRect.top;
	const x2 = propositionRect.left - containerRect.left;
	const y2 = propositionRect.top + propositionRect.height / 2 - containerRect.top;

	const path = document.createElementNS('http://www.w3.org/2000/svg', 'path');
	const d = `M ${x1},${y1} C ${(x1 + x2) / 2},${y1} ${(x1 + x2) / 2},${y2} ${x2},${y2}`;
	path.setAttribute('d', d);
	path.setAttribute('stroke', 'black');
	path.setAttribute('stroke-width', '2');
	path.setAttribute('fill', 'none');

	svg.appendChild(path);
}

function redrawLines() {
	const svg = document.getElementById('svg-container');
	svg.innerHTML = ''; // Clear existing lines
	for (const [sujetText, propositionText] of Object.entries(associations)) {
		const sujet = [...document.querySelectorAll('.sujet .rectangle')].find(el => el.innerText === sujetText);
		const proposition = [...document.querySelectorAll('.proposition .rectangle')].find(el => el.innerText === propositionText);
		if (sujet && proposition) {
			drawLine(sujet, proposition);
		}
	}
}

// Call redrawLines on window resize to ensure lines are correctly positioned
window.addEventListener('resize', redrawLines);

// Set a timer to redraw lines every 2 seconds
setInterval(redrawLines, 2000);


function changeCouleur(element) {
	if (selectedElement === element) {
		element.style.backgroundColor = 'grey';
		selectedElement = null;
	} else {
		if (selectedElement) {
			selectedElement.style.backgroundColor = 'grey';
		}
		element.style.backgroundColor = 'green';
		selectedElement = element;
	}
}

function changeCouleurMultiple(element) {
	const index = selectedElements.indexOf(element);
	if (index > -1) {
		// Si l'élément est déjà sélectionné, le désélectionner
		element.style.backgroundColor = 'grey';
		selectedElements.splice(index, 1);
	} else {
		// Sinon, le sélectionner
		element.style.backgroundColor = 'green';
		selectedElements.push(element);
	}
}


function precedent() {
	history.go(-1);
}

function feedback() {
	window.location.href = 'feedbackQ1.html';
}



function valider() {
	if (selectedElements.length > 0) {
		window.location.href = 'QcmRéponseMultiple.html';
	} else {
		alert("Veuillez sélectionner au moins une réponse avant de valider.");
	}
}

let bonnerep = "Extends";

function Eliminer() {
	const propositions = document.querySelectorAll('.question .rectangle');
	const mauvaisesReponses = Array.from(propositions).filter(proposition => {
		return proposition.innerText !== bonnerep;
	});

	if (mauvaisesReponses.length > 0) {
		const mauvaiseReponse = mauvaisesReponses[Math.floor(Math.random() * mauvaisesReponses.length)];
		mauvaiseReponse.style.display = 'none';
	} else {
		alert("Toutes les mauvaises réponses ont déjà été éliminées.");
	}
}

function setDifficulte(difficulte) {
	const container = document.querySelector('.container');
	container.classList.remove('tresfacile', 'facile', 'moyen','difficile');
	if (difficulte === 'tresfacile') {
		container.classList.add('tresfacile');
	} else if (difficulte === 'facile') {
		container.classList.add('facile');
	} else if (difficulte=== 'moyen') {
		container.classList.add('moyen');
	}else if (difficulte === 'difficile') {
		container.classList.add('difficile');
	}
}

function changeShadowColor() {
	const difficulties = ['tresfacile', 'facile', 'moyen', 'difficile'];
	let currentIndex = 0;

	setInterval(() => {
		setDifficulty(difficulties[currentIndex]);
		currentIndex = (currentIndex + 1) % difficulties.length;
	}, 3000); // Change every 3 seconds
}
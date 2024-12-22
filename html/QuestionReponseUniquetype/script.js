document.addEventListener('DOMContentLoaded', () => {
	// Mélanger les réponses (au cas où)
	randomizeOrder('.reponse', '#question');

	// Gérer la sélection des réponses
	let selectedAnswer = null;
	document.querySelectorAll('.reponse').forEach(reponse => {
		reponse.addEventListener('click', () => {
			if (selectedAnswer) {
				selectedAnswer.classList.remove('selected');
			}
			reponse.classList.add('selected');
			selectedAnswer = reponse;
		});
	});

	// Valider la réponse sélectionnée
	document.getElementById('valider').addEventListener('click', () => {
		if (selectedAnswer) {
			if (selectedAnswer.classList.contains('bonne-reponse')) {
				alert('Bravo! Vous avez trouvé la bonne réponse.');
			} else {
				alert('Désolé, la réponse sélectionnée est incorrecte.');
			}
		} else {
			alert('Veuillez sélectionner une réponse.');
		}
	});
});

function randomizeOrder(selector, parentSelector) {
	const items = document.querySelectorAll(selector);
	const parent = document.querySelector(parentSelector);
	const shuffledItems = Array.from(items).sort(() => Math.random() - 0.5);

	shuffledItems.forEach(item => parent.appendChild(item));
}


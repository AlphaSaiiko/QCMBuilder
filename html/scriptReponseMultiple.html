document.addEventListener('DOMContentLoaded', () => {
    // Mélanger les réponses (au cas où)
    randomizeOrder('.reponse', '#question');

    // Gérer la sélection des réponses
    let selectedAnswers = new Set();
    document.querySelectorAll('.reponse').forEach(reponse => {
        reponse.addEventListener('click', () => {
            if (selectedAnswers.has(reponse)) {
                reponse.classList.remove('selected');
                selectedAnswers.delete(reponse);
            } else {
                reponse.classList.add('selected');
                selectedAnswers.add(reponse);
            }
        });
    });

    // Valider les réponses sélectionnées
    document.getElementById('valider').addEventListener('click', () => {
        const bonnesReponses = document.querySelectorAll('.bonne-reponse');
        const mauvaisesReponses = document.querySelectorAll('.mauvaise-reponse');

        let allCorrect = true;

        // Vérifie si toutes les bonnes réponses sont sélectionnées
        bonnesReponses.forEach(reponse => {
            if (!selectedAnswers.has(reponse)) {
                allCorrect = false;
            }
        });

        // Vérifie qu'aucune mauvaise réponse n'est sélectionnée
        mauvaisesReponses.forEach(reponse => {
            if (selectedAnswers.has(reponse)) {
                allCorrect = false;
            }
        });

        if (allCorrect) {
            alert('Bravo! Vous avez sélectionné toutes les bonnes réponses.');
        } else {
            alert('Désolé, certaines réponses sélectionnées sont incorrectes.');
        }
    });
});

function randomizeOrder(selector, parentSelector) {
    const items = document.querySelectorAll(selector);
    const parent = document.querySelector(parentSelector);
    const shuffledItems = Array.from(items).sort(() => Math.random() - 0.5);

    shuffledItems.forEach(item => parent.appendChild(item));
}

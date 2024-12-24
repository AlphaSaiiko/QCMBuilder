document.addEventListener('DOMContentLoaded', () => {
    // Mélanger les réponses (au cas où)
    randomizeOrder('.reponse', '#question');

    // Gérer la sélection des réponses
    let selectedAnswers = new Set();
    let isValidationDone = false;
    document.querySelectorAll('.reponse').forEach(reponse => {
        reponse.addEventListener('click', () => {
            if (!isValidationDone) {
                if (selectedAnswers.has(reponse)) {
                    reponse.classList.remove('selected');
                    selectedAnswers.delete(reponse);
                } else {
                    reponse.classList.add('selected');
                    selectedAnswers.add(reponse);
                }
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
            bonnesReponses.forEach(reponse => {
                reponse.style.backgroundColor = 'green'; // Mettre en vert les bonnes réponses
            });
        } else {
            document.querySelectorAll('.reponse').forEach(reponse => {
                if (reponse.classList.contains('bonne-reponse')) {
                    reponse.style.backgroundColor = 'green'; // Mettre en vert les bonnes réponses
                } else {
                    reponse.style.backgroundColor = 'red'; // Mettre en rouge les mauvaises réponses
                }
            });
        }

        const popup = document.getElementById('popup');
        const popupText = document.getElementById('popup-text');

        if (allCorrect) {
            popupText.innerHTML = '<span style="color: green;">Bonne réponse!</span>';
        } else {
            popupText.innerHTML = '<span style="color: red;">Mauvaise réponse!</span>';
        }
        popup.style.display = 'flex';
        isValidationDone = true;

        // Transformer le bouton 'Valider' en 'Feedback'
        const validerButton = document.getElementById('valider');
        validerButton.textContent = 'Feedback';
        validerButton.removeEventListener('click', this);
        validerButton.addEventListener('click', () => {
            popup.style.display = 'flex';
        });
    });

    // Fermer le pop-up
    document.getElementById('popup-close').addEventListener('click', () => {
        const popup = document.getElementById('popup');
        popup.style.display = 'none';
    });
});

function randomizeOrder(selector, parentSelector) {
    const items = document.querySelectorAll(selector);
    const parent = document.querySelector(parentSelector);
    const shuffledItems = Array.from(items).sort(() => Math.random() - 0.5);
    shuffledItems.forEach(item => parent.appendChild(item));
}

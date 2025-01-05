document.addEventListener('DOMContentLoaded', () => {
    const questionId = document.querySelector('.question').getAttribute('data-question-id');

    // Mélanger les réponses
    randomizeOrder('.reponse', '.question');

    // Initialiser les points à partir du localStorage
    let totalPoints = localStorage.getItem('points') ? parseFloat(localStorage.getItem('points')) : 0;
    document.getElementById('points').textContent = `Points : ${totalPoints}`;

    // Gérer la sélection des réponses
    let selectedAnswers = new Set();
    let isValidationDone = localStorage.getItem(`isValidationDone-${questionId}`) === 'true';
    if (isValidationDone) {
        restoreState(questionId);
        transformButtonToFeedback();
    } else {
        enableAnswerSelection();
    }

    function enableAnswerSelection() {
        document.querySelectorAll('.reponse').forEach(reponse => {
            reponse.addEventListener('click', () => {
                if (!isValidationDone) {
                    if (selectedAnswers.has(reponse.id)) {
                        reponse.classList.remove('selected');
                        selectedAnswers.delete(reponse.id);
                    } else {
                        reponse.classList.add('selected');
                        selectedAnswers.add(reponse.id);
                    }
                }
            });
        });
    }

    // Valider la réponse sélectionnée
    function validate() {
        if (isValidationDone) return;  // Si la validation est déjà faite, ne pas continuer

        const popup = document.getElementById('popup');
        const popupText = document.getElementById('popup-text');
        const questionPoints = parseFloat(document.querySelector('.question').getAttribute('data-points'));

        if (selectedAnswers.size > 0) {
            let allCorrect = true;

            selectedAnswers.forEach(answerId => {
                const answerElement = document.getElementById(answerId);
                if (!answerElement.classList.contains('bonne-reponse')) {
                    allCorrect = false;
                }
            });

            if (allCorrect) {
                popupText.innerHTML = '<span style="color: green;">Bonne réponse!</span>';
                totalPoints += questionPoints;
                localStorage.setItem('points', totalPoints);
                document.getElementById('points').textContent = `Points : ${totalPoints}`;
            } else {
                popupText.innerHTML = '<span style="color: red;">Mauvaise réponse!</span>';
            }

            // Mettre en évidence les bonnes réponses
            document.querySelectorAll('.bonne-reponse').forEach(goodAnswer => {
                goodAnswer.style.backgroundColor = 'green';
            });

            // Marquer les mauvaises réponses
            selectedAnswers.forEach(answerId => {
                const answerElement = document.getElementById(answerId);
                if (!answerElement.classList.contains('bonne-reponse')) {
                    answerElement.style.backgroundColor = 'red';
                }
            });

            // Sauvegarder l'état
            localStorage.setItem(`isValidationDone-${questionId}`, true);
            localStorage.setItem(`selectedAnswers-${questionId}`, JSON.stringify(Array.from(selectedAnswers)));
            localStorage.setItem(`popupText-${questionId}`, popupText.innerHTML);

            // Transformer le bouton 'Valider' en 'Feedback'
            transformButtonToFeedback();

            // Afficher le pop-up personnalisé
            popup.style.display = 'flex';
            isValidationDone = true;
        } else {
            alert('Veuillez sélectionner une réponse !');
        }

        // Réactiver la sélection des réponses après l'alerte
        document.querySelectorAll('.reponse').forEach(reponse => {
            reponse.style.pointerEvents = '';
        });
    }

    document.getElementById('valider').addEventListener('click', validate);

    function transformButtonToFeedback() {
        const validerButton = document.getElementById('valider');
        validerButton.textContent = 'Feedback';
        validerButton.removeEventListener('click', validate);
        validerButton.addEventListener('click', showFeedback);
    }

    function showFeedback() {
        const popup = document.getElementById('popup');
        const popupText = document.getElementById('popup-text');
        const savedPopupText = localStorage.getItem(`popupText-${questionId}`);
        if (savedPopupText) {
            popupText.innerHTML = savedPopupText;
        } else {
            popupText.innerHTML = '<span style="color: red;">Pas de feedback disponible.</span>';
        }
        popup.style.display = 'flex';
    }

    // Fermer le pop-up
    document.getElementById('popup-close').addEventListener('click', () => {
        const popup = document.getElementById('popup');
        popup.style.display = 'none';
    });
});

function restoreState(questionId) {
    const selectedAnswers = JSON.parse(localStorage.getItem(`selectedAnswers-${questionId}`));
    if (selectedAnswers) {
        selectedAnswers.forEach(answerId => {
            const reponse = document.getElementById(answerId);
            if (reponse) {
                reponse.classList.add('selected');
                if (reponse.classList.contains('bonne-reponse')) {
                    reponse.style.backgroundColor = 'green';
                } else {
                    reponse.style.backgroundColor = 'red';
                }
            }
        });
    }
    const savedPopupText = localStorage.getItem(`popupText-${questionId}`);
    if (savedPopupText) {
        document.getElementById('popup-text').innerHTML = savedPopupText;
    }
    document.querySelectorAll('.bonne-reponse').forEach(goodAnswer => {
        goodAnswer.style.backgroundColor = 'green';
    });
}

function randomizeOrder(selector, parentSelector) {
    const items = document.querySelectorAll(selector);
    const parent = document.querySelector(parentSelector);
    const shuffledItems = Array.from(items).sort(() => Math.random() - 0.5);
    shuffledItems.forEach(item => parent.appendChild(item));
}

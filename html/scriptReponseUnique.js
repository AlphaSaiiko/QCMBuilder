document.addEventListener('DOMContentLoaded', () => {
    const questionId = document.querySelector('.question').getAttribute('data-question-id');

    // Mélanger les réponses (au cas où)
    randomizeOrder('.reponse', '#question');

    // Initialiser les points à partir du localStorage
    let totalPoints = localStorage.getItem('points') ? parseFloat(localStorage.getItem('points')) : 0;
    document.getElementById('points').textContent = `Points : ${totalPoints}`;

    // Gérer la sélection des réponses
    let selectedAnswer = null;
    let isValidationDone = localStorage.getItem(`isValidationDone-${questionId}`) === 'true';
    if (isValidationDone) {
        restoreState(questionId);
    }

    function enableAnswerSelection() {
        document.querySelectorAll('.reponse').forEach(reponse => {
            reponse.addEventListener('click', () => {
                if (!isValidationDone) {
                    if (selectedAnswer) {
                        selectedAnswer.classList.remove('selected');
                    }
                    reponse.classList.add('selected');
                    selectedAnswer = reponse;
                }
            });
        });
    }
    
    enableAnswerSelection();

    // Valider la réponse sélectionnée
    document.getElementById('valider').addEventListener('click', function validate() {
        const popup = document.getElementById('popup');
        const popupText = document.getElementById('popup-text');
        const questionPoints = parseFloat(document.querySelector('.question').getAttribute('data-points'));

        if (selectedAnswer) {
            if (selectedAnswer.classList.contains('bonne-reponse')) {
                popupText.innerHTML = '<span style="color: green;">Bonne réponse!</span>';
                totalPoints += questionPoints;
                localStorage.setItem('points', totalPoints);
                document.getElementById('points').textContent = `Points : ${totalPoints}`;
            } else {
                popupText.innerHTML = '<span style="color: red;">Mauvaise réponse!</span>';
                selectedAnswer.style.backgroundColor = 'red';
            }
            // Mettre en évidence les bonnes réponses
            document.querySelectorAll('.bonne-reponse').forEach(goodAnswer => {
                goodAnswer.style.backgroundColor = 'green';
            });

            // Sauvegarder l'état
            localStorage.setItem(`isValidationDone-${questionId}`, true);
            localStorage.setItem(`selectedAnswer-${questionId}`, selectedAnswer.innerHTML);

            // Transformer le bouton 'Valider' en 'Feedback'
            const validerButton = document.getElementById('valider');
            validerButton.textContent = 'Feedback';
            validerButton.removeEventListener('click', validate);
            validerButton.addEventListener('click', () => {
                const popupFeedback = document.getElementById('popup');
                const popupFeedbackText = document.getElementById('popup-text');
                if (selectedAnswer.classList.contains('bonne-reponse')) {
                    popupFeedbackText.innerHTML = '<span style="color: green;">Bonne réponse!</span>';
                } else {
                    popupFeedbackText.innerHTML = '<span style="color: red;">Mauvaise réponse!</span>';
                }
                popupFeedback.style.display = 'flex';
            });

            // Afficher le pop-up personnalisé
            popup.style.display = 'flex';
            isValidationDone = true; // Ne marquer comme "validation terminée" que si une réponse est sélectionnée
        } else {
            alert('Veuillez sélectionner une réponse !');
            isValidationDone = false;
        }

        enableAnswerSelection();
    });

    // Fermer le pop-up
    document.getElementById('popup-close').addEventListener('click', () => {
        const popup = document.getElementById('popup');
        popup.style.display = 'none';
    });
});

function restoreState(questionId) {
    const selectedAnswerText = localStorage.getItem(`selectedAnswer-${questionId}`);
    document.querySelectorAll('.reponse').forEach(reponse => {
        if (reponse.innerHTML === selectedAnswerText) {
            reponse.classList.add('selected');
            if (reponse.classList.contains('bonne-reponse')) {
                reponse.style.backgroundColor = 'green';
            } else {
                reponse.style.backgroundColor = 'red';
            }
        }
    });
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

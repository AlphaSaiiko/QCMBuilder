document.addEventListener('DOMContentLoaded', () => {
    const questionId = document.querySelector('.question').getAttribute('id');

    // Mélanger les réponses
    randomizeOrder('.reponse', '.question');

    // Initialiser les points à partir du sessionStorage
    let totalPoints = sessionStorage.getItem('points') ? parseFloat(sessionStorage.getItem('points')) : 0;
    document.getElementById('points').textContent = `Points : ${totalPoints}`;

    // Initialiser les points potentiels
    let potentialPoints = 1; // Suppose que chaque question vaut 1 point par défaut

    // Élimer les mauvaises réponses dans l'ordre spécifié
    const eliminationOrder = Array.from(document.querySelectorAll('.mauvaise-reponse[data-order]'))
        .sort((a, b) => a.getAttribute('data-order') - b.getAttribute('data-order'));

    let currentIndex = 0;

    document.getElementById('eliminar').addEventListener('click', () => {
        if (currentIndex < eliminationOrder.length) {
            const toEliminate = eliminationOrder[currentIndex];
            const ptselim = parseFloat(toEliminate.getAttribute('ptselim'));
            toEliminate.innerHTML = `<span style='color: red;'>${ptselim} pts</span>`;
            toEliminate.style.cursor = 'default';
            toEliminate.classList.add('eliminated');
            currentIndex++;

            // Mise à jour des points potentiels
            potentialPoints += ptselim;

            // Sauvegarder l'ordre d'élimination
            sessionStorage.setItem(`eliminationOrder-${questionId}`, currentIndex);
            sessionStorage.setItem(`potentialPoints-${questionId}`, potentialPoints);
        }
    });

    // Gérer la sélection des réponses
    let selectedAnswer = null;
    window.reponseSelectionnee = [];
    let isValidationDone = sessionStorage.getItem(`isValidationDone-${questionId}`) === 'true';
    if (isValidationDone) {
        restoreState();
        initializeToFeedback();
    }

    document.querySelectorAll('.reponse').forEach(reponse => {
        reponse.addEventListener('click', () => {
            if (!isValidationDone && !reponse.classList.contains('eliminated')) {
                if (selectedAnswer) {
                    selectedAnswer.classList.remove('selected');
                }
                reponse.classList.add('selected');
                selectedAnswer = reponse;
                window.reponseSelectionnee = [reponse]; // Mettre à jour window.reponseSelectionnee
            }
        });
    });

    // Valider la réponse sélectionnée
    function valider() {
        if (!isValidationDone) {
            if (selectedAnswer) {
                const popup = document.getElementById('popup');
                const popupText = document.getElementById('popup-text');
                const feedbackText = popupText.innerText.trim();

                if (selectedAnswer.classList.contains('bonne-reponse')) {
                    popupText.innerHTML = '<span style="color: green;">Bonne réponse !</span>';
                    selectedAnswer.style.backgroundColor = 'green';
                    totalPoints += potentialPoints; // Ajouter les points potentiels au score total
                    sessionStorage.setItem('points', totalPoints);
                    document.getElementById('points').textContent = `Points : ${totalPoints}`;
                } else {
                    popupText.innerHTML = '<span style="color: red;">Mauvaise réponse !</span>';
                    selectedAnswer.style.backgroundColor = 'red';
                    document.querySelector('.bonne-reponse').style.backgroundColor = 'green';
                }

                if (feedbackText) {
                    popupText.innerHTML += `<p>${feedbackText}</p>`;
                }

                popup.style.display = 'flex';
                isValidationDone = true;

                // Transformer le bouton 'Valider' en 'Feedback'
                initializeToFeedback();

                // Sauvegarder l'état
                sessionStorage.setItem(`isValidationDone-${questionId}`, true);
                sessionStorage.setItem(`selectedAnswer-${questionId}`, selectedAnswer.getAttribute('id'));
                sessionStorage.setItem(`popupText-${questionId}`, popupText.innerHTML);
                // Arrêter le minuteur
                if (typeof arreterMinuteur === 'function') {
                    arreterMinuteur();
                }
            } else {
                alert('Veuillez sélectionner une réponse.');
            }
        } else {
            const popup = document.getElementById('popup');
            popup.style.display = 'flex';
        }
    }

    document.getElementById('valider').addEventListener('click', valider);

    function initializeToFeedback() {
        const validerButton = document.getElementById('valider');
        validerButton.textContent = 'Feedback';
        validerButton.removeEventListener('click', this);
        validerButton.addEventListener('click', () => {
            const popup = document.getElementById('popup');
            popup.style.display = 'flex';
        });
    }

    function restoreState() {
        const savedSelectedAnswerId = sessionStorage.getItem(`selectedAnswer-${questionId}`);
        if (savedSelectedAnswerId) {
            const savedSelectedAnswer = document.getElementById(savedSelectedAnswerId);
            if (savedSelectedAnswer) {
                savedSelectedAnswer.classList.add('selected');
                if (savedSelectedAnswer.classList.contains('bonne-reponse')) {
                    savedSelectedAnswer.style.backgroundColor = 'green';
                } else {
                    savedSelectedAnswer.style.backgroundColor = 'red';
                }
                window.reponseSelectionnee = [savedSelectedAnswer]; // Mettre à jour window.reponseSelectionnee
            }
        }
        const savedPopupText = sessionStorage.getItem(`popupText-${questionId}`);
        if (savedPopupText) {
            document.getElementById('popup-text').innerHTML = savedPopupText;
        }
        document.querySelectorAll('.bonne-reponse').forEach(goodAnswer => {
            goodAnswer.style.backgroundColor = 'green';
        });

        const savedEliminationOrder = parseInt(sessionStorage.getItem(`eliminationOrder-${questionId}`), 10);
        for (let i = 0; i < savedEliminationOrder; i++) {
            const toEliminate = eliminationOrder[i];
            const ptselim = parseFloat(toEliminate.getAttribute('ptselim'));
            toEliminate.innerHTML = `<span style='color: red;'>${ptselim} pts</span>`;
            toEliminate.style.cursor = 'default';
            toEliminate.classList.add('eliminated');
        }
    }

    // Fermer le pop-up
    document.getElementById('popup-close').addEventListener('click', () => {
        const popup = document.getElementById('popup');
        popup.style.display = 'none';
    });

    // Fonction finMinuteur appelée par le timer
    function finMinuteur() {
        const popup = document.getElementById('popup');
        const popupText = document.getElementById('popup-text');

        if (!isValidationDone && window.reponseSelectionnee.length === 0) {
            // Mettre en évidence les bonnes réponses
            document.querySelectorAll('.bonne-reponse').forEach(bonneReponse => {
                bonneReponse.style.backgroundColor = 'green';
            });

            popupText.innerHTML = '<span style="color: red;">Veuillez sélectionner une réponse. Temps écoulé !</span>';
            popup.style.display = 'flex';

            // Sauvegarder l'état
            sessionStorage.setItem(`isValidationDone-${questionId}`, true);
            sessionStorage.setItem(`popupText-${questionId}`, popupText.innerHTML);
            initializeToFeedback();

            // Arrêter le minuteur
            if (typeof arreterMinuteur === 'function') {
                arreterMinuteur();
            }
        }
    }

    // Attacher les fonctions finMinuteur et valider à l'objet window pour les rendre globales
    window.finMinuteur = finMinuteur;
    window.valider = valider;
});

function randomizeOrder(selector, parentSelector) {
    const items = document.querySelectorAll(selector);
    const parent = document.querySelector(parentSelector);
    const shuffledItems = Array.from(items).sort(() => Math.random() - 0.5);
    shuffledItems.forEach(item => parent.appendChild(item));
}

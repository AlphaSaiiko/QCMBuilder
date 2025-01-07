document.addEventListener('DOMContentLoaded', () => {
    const questionId = document.querySelector('.question').getAttribute('data-question-id');

    // Mélanger les réponses (au cas où)
    melangerOrdre('.reponse', '.question');

    // Initialiser les points à partir du sessionStorage
    let totalPoints = sessionStorage.getItem('points') ? parseFloat(sessionStorage.getItem('points')) : 0;
    document.getElementById('points').textContent = `Points : ${totalPoints}`;

    // Gérer la sélection des réponses
    window.reponseSelectionnee = [];
    let isValidationDone = sessionStorage.getItem(`isValidationDone-${questionId}`) === 'true';
    if (isValidationDone) {
        restaurerEtat(questionId);
        transformerBoutonEnFeedback(); // Assurez-vous d'appeler la fonction ici
    }

    function activerSelectionReponse() {
        document.querySelectorAll('.reponse').forEach(reponse => {
            reponse.addEventListener('click', () => {
                if (!isValidationDone) {
                    reponse.classList.toggle('selected');
                    if (reponse.classList.contains('selected')) {
                        window.reponseSelectionnee.push(reponse);
                    } else {
                        window.reponseSelectionnee = window.reponseSelectionnee.filter(r => r !== reponse);
                    }
                }
            });
        });
    }

    activerSelectionReponse();

    // Valider les réponses sélectionnées
    function valider() {
        const popup = document.getElementById('popup');
        const popupTexte = document.getElementById('popup-text');
        const questionPoints = parseFloat(document.querySelector('.question').getAttribute('data-points'));

        if (window.reponseSelectionnee.length > 0) {
            const allCorrect = window.reponseSelectionnee.every(reponse => reponse.classList.contains('bonne-reponse'));
            const selectedCorrect = document.querySelectorAll('.bonne-reponse.selected').length;
            const totalCorrect = document.querySelectorAll('.bonne-reponse').length;

            if (allCorrect && selectedCorrect === totalCorrect) {
                popupTexte.innerHTML = '<span style="color: green;">Bonne réponse!</span>';
                totalPoints += questionPoints;
                sessionStorage.setItem('points', totalPoints);
                document.getElementById('points').textContent = `Points : ${totalPoints}`;
            } else {
                popupTexte.innerHTML = '<span style="color: red;">Mauvaise réponse!</span>';
                window.reponseSelectionnee.forEach(reponse => {
                    if (!reponse.classList.contains('bonne-reponse')) {
                        reponse.style.backgroundColor = 'red';
                    }
                });
            }

            // Mettre en évidence les bonnes réponses
            document.querySelectorAll('.bonne-reponse').forEach(bonneReponse => {
                bonneReponse.style.backgroundColor = 'green';
            });

            // Sauvegarder l'état
            sessionStorage.setItem(`isValidationDone-${questionId}`, true);
            sessionStorage.setItem(`reponseSelectionnee-${questionId}`, JSON.stringify(window.reponseSelectionnee.map(r => r.innerHTML)));
            sessionStorage.setItem(`popupTexte-${questionId}`, popupTexte.innerHTML);

            // Transformer le bouton 'Valider' en 'Feedback'
            transformerBoutonEnFeedback();

            // Afficher le pop-up personnalisé
            popup.style.display = 'flex';
            isValidationDone = true;
            // Arrêter le minuteur
            arreterMinuteur();
        } else {
            alert('Veuillez sélectionner au moins une réponse avant de valider.'); // Appeler finMinuteur si aucune réponse n'est sélectionnée manuellement
        }
        activerSelectionReponse();
    }

    document.getElementById('valider').addEventListener('click', valider);

    function transformerBoutonEnFeedback() {
        const validerButton = document.getElementById('valider');
        validerButton.textContent = 'Feedback';
        validerButton.removeEventListener('click', valider);
        validerButton.addEventListener('click', () => {
            const popupFeedback = document.getElementById('popup');
            const popupFeedbackTexte = document.getElementById('popup-text');
            const savedPopupTexte = sessionStorage.getItem(`popupTexte-${questionId}`);
            if (savedPopupTexte) {
                popupFeedbackTexte.innerHTML = savedPopupTexte;
            } else {
                if (window.reponseSelectionnee.every(r => r.classList.contains('bonne-reponse'))) {
                    popupFeedbackTexte.innerHTML = '<span style="color: green;">Bonne réponse!</span>';
                } else {
                    popupFeedbackTexte.innerHTML = '<span style="color: red;">Mauvaise réponse!</span>';
                }
            }
            popupFeedback.style.display = 'flex';
        });
    }

    function finMinuteur() {
        if (window.reponseSelectionnee.length === 0) {
            // Mettre en évidence les bonnes réponses en vert
            document.querySelectorAll('.bonne-reponse').forEach(bonneReponse => {
                bonneReponse.style.backgroundColor = 'green';
            });

            // Afficher le pop-up avec le message de fin de temps
            const popup = document.getElementById('popup');
            const popupTexte = document.getElementById('popup-text');
            popupTexte.innerHTML = '<span style="color: red;">Vous n\'avez plus de temps!</span>';
            popup.style.display = 'flex';

            // Sauvegarder l'état dans le sessionStorage
            sessionStorage.setItem(`isValidationDone-${questionId}`, true);
            sessionStorage.setItem(`popupTexte-${questionId}`, popupTexte.innerHTML);

            // Marquer la validation comme terminée
            isValidationDone = true;

            // Transformer le bouton 'Valider' en 'Feedback'
            transformerBoutonEnFeedback();
        }
    }

    // Attacher les fonctions finMinuteur et valider à l'objet window pour les rendre globales
    window.finMinuteur = finMinuteur;
    window.valider = valider;

    // Fermer le pop-up
    document.getElementById('popup-close').addEventListener('click', () => {
        const popup = document.getElementById('popup');
        popup.style.display = 'none';
    });
});

function restaurerEtat(questionId) {
    const reponseSelectionneeTexte = JSON.parse(sessionStorage.getItem(`reponseSelectionnee-${questionId}`));
    document.querySelectorAll('.reponse').forEach(reponse => {
        if (reponseSelectionneeTexte.includes(reponse.innerHTML)) {
            reponse.classList.add('selected');
            if (reponse.classList.contains('bonne-reponse')) {
                reponse.style.backgroundColor = 'green';
            } else {
                reponse.style.backgroundColor = 'red';
            }
        }
    });

    document.querySelectorAll('.bonne-reponse').forEach(bonneReponse => {
        bonneReponse.style.backgroundColor = 'green';
    });

    const savedPopupTexte = sessionStorage.getItem(`popupTexte-${questionId}`);
    if (savedPopupTexte) {
        document.getElementById('popup-text').innerHTML = savedPopupTexte;
    }
}

function melangerOrdre(selecteur, selecteurParent) {
    const elements = document.querySelectorAll(selecteur);
    const parent = document.querySelector(selecteurParent);
    const elementsMelanges = Array.from(elements).sort(() => Math.random() - 0.5);
    elementsMelanges.forEach(element => parent.appendChild(element));
}

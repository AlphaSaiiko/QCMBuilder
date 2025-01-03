document.addEventListener('DOMContentLoaded', () => {
    // Mélanger les réponses (au cas où)
    randomizeOrder('.reponse', '#question');

    // Élimer les mauvaises réponses dans l'ordre spécifié
    const eliminationOrder = Array.from(document.querySelectorAll('.mauvaise-reponse[data-order]'))
        .sort((a, b) => a.getAttribute('data-order') - b.getAttribute('data-order'));

    let currentIndex = 0;

    document.getElementById('eliminar').addEventListener('click', () => {
        if (currentIndex < eliminationOrder.length) {
            const toEliminate = eliminationOrder[currentIndex];
            const ptselim = toEliminate.getAttribute('ptselim');
            toEliminate.innerHTML = `<span style='color: red;'>${ptselim} pts</span>`;
            toEliminate.style.cursor = 'default';
            toEliminate.classList.add('eliminated');
            currentIndex++;
        }
    });

    // Gérer la sélection des réponses
    let selectedAnswer = null;
    let isValidationDone = false;
    document.querySelectorAll('.reponse').forEach(reponse => {
        reponse.addEventListener('click', () => {
            if (!isValidationDone && !reponse.classList.contains('eliminated')) {
                if (selectedAnswer) {
                    selectedAnswer.classList.remove('selected');
                }
                reponse.classList.add('selected');
                selectedAnswer = reponse;
            }
        });
    });

    // Valider la réponse sélectionnée
    document.getElementById('valider').addEventListener('click', () => {
        if (selectedAnswer) {
            const popup = document.getElementById('popup');
            const popupText = document.getElementById('popup-text');

            if (selectedAnswer.classList.contains('bonne-reponse')) {
                popupText.innerHTML = '<span style="color: green;">Bravo! Vous avez trouvé la bonne réponse.</span>';
                selectedAnswer.style.backgroundColor = 'green';
            } else {
                popupText.innerHTML = '<span style="color: red;">Désolé, la réponse sélectionnée est incorrecte.</span>';
                selectedAnswer.style.backgroundColor = 'red';
                document.querySelector('.bonne-reponse').style.backgroundColor = 'green';
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
        } else {
            alert('Veuillez sélectionner une réponse.');
        }
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

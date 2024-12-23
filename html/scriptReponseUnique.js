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
        const popup = document.getElementById('popup');
        const popupText = document.getElementById('popup-text');
        if (selectedAnswer) {
            if (selectedAnswer.classList.contains('bonne-reponse')) {
                popupText.innerHTML = '<span style="color: green;">Bonne réponse!</span>';
            } else {
                popupText.innerHTML = '<span style="color: red;">Mauvaise réponse!</span>';
            }
        } else {
            popupText.innerHTML = '<span style="color: red;">Mauvaise réponse!</span>';
        }
        popup.style.display = 'flex';

        // Transformer le bouton 'Valider' en 'Feedback'
        const validerButton = document.getElementById('valider');
        validerButton.textContent = 'Feedback';
        validerButton.removeEventListener('click', this);
        validerButton.addEventListener('click', () => {
            popup.style.display = 'flex';
        });
    });

    // Redirection vers la page suivante
    document.getElementById('suivant').addEventListener('click', () => {
        location.href = 'page3.html';
    });

    // Fermer le pop-up
    document.getElementById('popup-close').addEventListener('click', () => {
        document.getElementById('popup').style.display = 'none';
    });
});

function randomizeOrder(selector, parentSelector) {
    const items = document.querySelectorAll(selector);
    const parent = document.querySelector(parentSelector);
    const shuffledItems = Array.from(items).sort(() => Math.random() - 0.5);

    shuffledItems.forEach(item => parent.appendChild(item));
}

document.addEventListener('DOMContentLoaded', () => {
    // Élimer les mauvaises réponses dans l'ordre spécifié
    const eliminationOrder = Array.from(document.querySelectorAll('.mauvaise-reponse[data-order]'))
        .sort((a, b) => a.getAttribute('data-order') - b.getAttribute('data-order'));

    let currentIndex = 0;

    document.getElementById('eliminar').addEventListener('click', () => {
        if (currentIndex < eliminationOrder.length) {
            const toEliminate = eliminationOrder[currentIndex];
            toEliminate.style.display = 'none';
            currentIndex++;
        }
    });

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

document.addEventListener('DOMContentLoaded', () => {
    randomizeOrder('.word', '#words');
    randomizeOrder('.definition', '#definitions');
});

function randomizeOrder(selector, parentSelector) {
    const items = document.querySelectorAll(selector);
    const parent = document.querySelector(parentSelector);
    const shuffledItems = Array.from(items).sort(() => Math.random() - 0.5);

    shuffledItems.forEach(item => parent.appendChild(item));
}

let motSelectionne = null;
let connexions = {};
let reverseConnexions = {};
let isValidationDone = false;

// Ajouter un événement de clic à chaque mot
document.querySelectorAll('.word').forEach(mot => {
    mot.addEventListener('click', () => {
        if (!isValidationDone) {
            motSelectionne = mot;
            clearSelection();
            mot.style.backgroundColor = '#d3d3d3'; // Mettre en surbrillance le mot sélectionné
        }
    });
});

// Ajouter un événement de clic à chaque définition
document.querySelectorAll('.definition').forEach(definition => {
    definition.addEventListener('click', () => {
        if (motSelectionne && !isValidationDone) {
            let motId = motSelectionne.getAttribute('data-id');
            let defId = definition.getAttribute('data-id');

            if (connexions[motId]) {
                // Effacer l'ancienne ligne si elle existe
                let ancienDefId = connexions[motId];
                delete reverseConnexions[ancienDefId];
                delete connexions[motId];
                removeLine(motSelectionne, document.querySelector(`.definition[data-id='${ancienDefId}']`));
            }

            if (reverseConnexions[defId]) {
                // Effacer l'ancienne ligne si elle existe
                let ancienMotId = reverseConnexions[defId];
                delete connexions[ancienMotId];
                delete reverseConnexions[defId];
                removeLine(document.querySelector(`.word[data-id='${ancienMotId}']`), definition);
            }

            connexions[motId] = defId;
            reverseConnexions[defId] = motId;

            drawLine(motSelectionne, definition);
            clearSelection();
        }
    });
});

function drawLine(sujet, proposition) {
    const svg = document.getElementById('svg-container');
    const sujetRect = sujet.getBoundingClientRect();
    const propositionRect = proposition.getBoundingClientRect();
    const containerRect = svg.getBoundingClientRect();

    const x1 = sujetRect.right - containerRect.left;
    const y1 = sujetRect.top + sujetRect.height / 2 - containerRect.top;
    const x2 = propositionRect.left - containerRect.left;
    const y2 = propositionRect.top + propositionRect.height / 2 - containerRect.top;

    const path = document.createElementNS('http://www.w3.org/2000/svg', 'path');
    const d = `M ${x1},${y1} C ${(x1 + x2) / 2},${y1} ${(x1 + x2) / 2},${y2} ${x2},${y2}`;
    path.setAttribute('d', d);
    path.setAttribute('stroke', 'black');
    path.setAttribute('stroke-width', '2');
    path.setAttribute('fill', 'none');
    path.setAttribute('class', `line-${sujet.getAttribute('data-id')}-${proposition.getAttribute('data-id')}`);

    svg.appendChild(path);
}

function removeLine(sujet, proposition) {
    const svg = document.getElementById('svg-container');
    const line = svg.querySelector(`.line-${sujet.getAttribute('data-id')}-${proposition.getAttribute('data-id')}`);
    if (line) {
        svg.removeChild(line);
    }
}

// Fonction pour effacer la sélection
function clearSelection() {
    document.querySelectorAll('.word').forEach(mot => {
        mot.style.backgroundColor = '#e3e3e3'; // Réinitialiser la couleur de fond
    });
}

// Fonction pour valider les connexions
function validate() {
    let correct = true;
    let totalAssociations = Object.keys(connexions).length;
    let mots = document.querySelectorAll('.word').length;

    if (totalAssociations < mots) {
        showPopup('<span style="color: red;">Il manque des associations.</span>');
        return;
    }

    for (let motId in connexions) {
        if (connexions[motId] != motId) {
            correct = false;
            break;
        }
    }

    if (correct) {
        showPopup('<span style="color: green;">Bravo! Toutes les associations sont correctes.</span>');
    } else {
        showPopup('<span style="color: red;">Désolé, certaines associations sont incorrectes. Essayez encore.</span>');
    }
    isValidationDone = true; // Désactiver les modifications après validation
    const validerButton = document.querySelector('.btn[onclick="validate()"]');
    validerButton.textContent = 'Feedback';
}

// Fonction pour afficher le pop-up avec un message
function showPopup(message) {
    const popup = document.getElementById('popup');
    const popupText = document.getElementById('popup-text');
    popupText.innerHTML = message;
    popup.style.display = 'flex';
}

// Fermer le pop-up
document.getElementById('popup-close').addEventListener('click', () => {
    const popup = document.getElementById('popup');
    popup.style.display = 'none';
});

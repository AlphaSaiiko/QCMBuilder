window.type = 'association';
document.addEventListener('DOMContentLoaded', () => {
    const pointsElem = document.getElementById('points');
    let totalPoints = parseFloat(sessionStorage.getItem('points')) || 0;
    pointsElem.textContent = `Points : ${totalPoints}`;  // Mise à jour des points affichés

    randomizeOrder('.word', '#words');
    randomizeOrder('.definition', '#definitions');

    const questionId = getQuestionId();
    loadPreviousState(questionId);
    addWordListeners();
    addDefinitionListeners();

    // Initialisation des variables globales
    window.reponseSelectionnee = [];
    window.associationsFaites = 0;
    window.associationsPossibles = document.querySelectorAll('.word').length;
});

function getQuestionId() {
    const url = window.location.pathname;
    const filename = url.substring(url.lastIndexOf('/') + 1);
    const questionId = filename.match(/\d+/);
    return questionId ? parseInt(questionId[0], 10) : null;  // Retourne le numéro de la page
}

function randomizeOrder(selector, parentSelector) {
    const items = document.querySelectorAll(selector);
    const parent = document.querySelector(parentSelector);
    const shuffledItems = Array.from(items).sort(() => Math.random() - 0.5);
    shuffledItems.forEach(item => parent.appendChild(item));
}

let motSelectionne = null;
let connexions = {};
let reverseConnexions = {};
let feedbackClic = false;
let validationFaite = false;

function addWordListeners() {
    document.querySelectorAll('.word').forEach(mot => {
        mot.addEventListener('click', () => {
            if (!validationFaite) {
                motSelectionne = mot;
                effacerSelection();
                mot.style.backgroundColor = '#d3d3d3';
            }
        });
    });
}

function addDefinitionListeners() {
    document.querySelectorAll('.definition').forEach(definition => {
        definition.addEventListener('click', () => {
            if (motSelectionne && !validationFaite) {
                let motId = motSelectionne.getAttribute('data-id');
                let defId = definition.getAttribute('data-id');

                if (connexions[motId]) {
                    let ancienDefId = connexions[motId];
                    delete reverseConnexions[ancienDefId];
                    delete connexions[motId];
                    retirerLigne(motSelectionne, document.querySelector(`.definition[data-id='${ancienDefId}']`));
                }

                if (reverseConnexions[defId]) {
                    let ancienMotId = reverseConnexions[defId];
                    delete connexions[ancienMotId];
                    delete reverseConnexions[defId];
                    retirerLigne(document.querySelector(`.word[data-id='${ancienMotId}']`), definition);
                }

                connexions[motId] = defId;
                reverseConnexions[defId] = motId;

                dessinerLigne(motSelectionne, definition, 'black');
                effacerSelection();
                window.associationsFaites = Object.keys(connexions).length; // Met à jour les associations faites
            }
        });
    });
}

function dessinerLigne(sujet, proposition, couleur) {
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
    path.setAttribute('stroke', couleur);
    path.setAttribute('stroke-width', '2');
    path.setAttribute('fill', 'none');
    path.setAttribute('class', `line-${sujet.getAttribute('data-id')}-${proposition.getAttribute('data-id')}`);

    svg.appendChild(path);
}

function retirerLigne(sujet, proposition) {
    const svg = document.getElementById('svg-container');
    const line = svg.querySelector(`.line-${sujet.getAttribute('data-id')}-${proposition.getAttribute('data-id')}`);
    if (line) {
        svg.removeChild(line);
    }
}

function effacerSelection() {
    document.querySelectorAll('.word').forEach(mot => {
        mot.style.backgroundColor = '#e3e3e3';
    });
}

function valider(questionId) {
    let correct = true;
    let totalAssociations = Object.keys(connexions).length;
    let mots = document.querySelectorAll('.word').length;

    if (totalAssociations < mots && !validationFaite) {
        alert('Il manque des associations.');
        return;
    }

    for (let motId in connexions) {
        const isCorrect = connexions[motId] == motId;
        dessinerLigne(
            document.querySelector(`.word[data-id='${motId}']`),
            document.querySelector(`.definition[data-id='${connexions[motId]}']`),
            isCorrect ? 'green' : 'red'
        );
        if (!isCorrect) {
            correct = false;
        }
    }

    let popupMessage;
    if (correct) {
        popupMessage = '<span style="color: green;">Bravo! Toutes les associations sont correctes.</span>';
        if (!feedbackClic && !validationFaite) {
            updatePoints(1);  // Ajouter des points pour les réponses correctes
        }
    } else {
        popupMessage = '<span style="color: red;">Désolé, certaines associations sont incorrectes.</span>';
    }

    showPopup(popupMessage);

    validationFaite = true;
    feedbackClic = true;
    sessionStorage.setItem(`validationFaite-${questionId}`, 'true');
    sessionStorage.setItem(`connexions-${questionId}`, JSON.stringify(connexions));
    sessionStorage.setItem(`popupMessage-${questionId}`, popupMessage);

    document.querySelector(`.btn[onclick^="valider(${questionId})"]`).textContent = 'Feedback';
}

function updatePoints(points) {
    let totalPoints = parseFloat(sessionStorage.getItem('points')) || 0;
    totalPoints += points;
    sessionStorage.setItem('points', totalPoints);
    document.getElementById('points').textContent = `Points : ${totalPoints}`;
}

function showPopup(message) {
    const popup = document.getElementById('popup');
    const popupText = document.getElementById('popup-text');
    popupText.innerHTML = message;
    popup.style.display = 'flex';
}

document.getElementById('popup-close').addEventListener('click', () => {
    const popup = document.getElementById('popup');
    popup.style.display = 'none';
});

function loadPreviousState(questionId) {
    if (sessionStorage.getItem(`validationFaite-${questionId}`) === 'true') {
        validationFaite = true;
        const savedConnexions = JSON.parse(sessionStorage.getItem(`connexions-${questionId}`));
        let correct = true;
        for (let motId in savedConnexions) {
            const defId = savedConnexions[motId];
            const mot = document.querySelector(`.word[data-id='${motId}']`);
            const definition = document.querySelector(`.definition[data-id='${defId}']`);
            const isCorrect = motId == defId;
            dessinerLigne(mot, definition, isCorrect ? 'green' : 'red');
            connexions[motId] = defId;
            reverseConnexions[defId] = motId;
            if (!isCorrect) {
                correct = false;
            }
        }

        const messagePopupEnregistre = sessionStorage.getItem(`popupMessage-${questionId}`);
        if (messagePopupEnregistre) {
            document.querySelector(`.btn[onclick^="valider(${questionId})"]`).addEventListener('click', () => {
                showPopup(messagePopupEnregistre);
            });
        }
        document.querySelector(`.btn[onclick^="valider(${questionId})"]`).textContent = 'Feedback';
    }
}

function finMinuteur() {
    // Enlever les associations dessinées
    document.querySelectorAll('path').forEach(ligne => {
        ligne.remove();
    });

    // Afficher le popup "Vous n'avez plus de temps"
    showPopup('<span style="color: red;">Vous n\'avez plus de temps.</span>');

    // Afficher les associations correctes
    document.querySelectorAll('.word').forEach(mot => {
        const motId = mot.getAttribute('data-id');
        const definition = document.querySelector(`.definition[data-id='${motId}']`);
        dessinerLigne(mot, definition, 'green');
    });

    // Désactiver la création d'associations
    validationFaite = true;
}


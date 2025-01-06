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
let feedbackClicked = false;
let isValidationDone = false;

function addWordListeners() {
    document.querySelectorAll('.word').forEach(mot => {
        mot.addEventListener('click', () => {
            if (!isValidationDone) {
                motSelectionne = mot;
                clearSelection();
                mot.style.backgroundColor = '#d3d3d3';
            }
        });
    });
}

function addDefinitionListeners() {
    document.querySelectorAll('.definition').forEach(definition => {
        definition.addEventListener('click', () => {
            if (motSelectionne && !isValidationDone) {
                let motId = motSelectionne.getAttribute('data-id');
                let defId = definition.getAttribute('data-id');

                if (connexions[motId]) {
                    let ancienDefId = connexions[motId];
                    delete reverseConnexions[ancienDefId];
                    delete connexions[motId];
                    removeLine(motSelectionne, document.querySelector(`.definition[data-id='${ancienDefId}']`));
                }

                if (reverseConnexions[defId]) {
                    let ancienMotId = reverseConnexions[defId];
                    delete connexions[ancienMotId];
                    delete reverseConnexions[defId];
                    removeLine(document.querySelector(`.word[data-id='${ancienMotId}']`), definition);
                }

                connexions[motId] = defId;
                reverseConnexions[defId] = motId;

                drawLine(motSelectionne, definition, 'black');
                clearSelection();
            }
        });
    });
}

function drawLine(sujet, proposition, color) {
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
    path.setAttribute('stroke', color);
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

function clearSelection() {
    document.querySelectorAll('.word').forEach(mot => {
        mot.style.backgroundColor = '#e3e3e3';
    });
}

function validate(questionId) {
    let correct = true;
    let totalAssociations = Object.keys(connexions).length;
    let mots = document.querySelectorAll('.word').length;

    if (totalAssociations < mots && !isValidationDone) {
        alert('Il manque des associations.'); // Utiliser une alerte
        return;
    }

    for (let motId in connexions) {
        const isCorrect = connexions[motId] == motId;
        drawLine(
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
        if (!feedbackClicked && !isValidationDone) {
            updatePoints(1);  // Ajouter des points pour les réponses correctes
        }
    } else {
        popupMessage = '<span style="color: red;">Désolé, certaines associations sont incorrectes.</span>';
    }

    showPopup(popupMessage);

    isValidationDone = true;
    feedbackClicked = true;
    sessionStorage.setItem(`isValidationDone-${questionId}`, 'true');
    sessionStorage.setItem(`connexions-${questionId}`, JSON.stringify(connexions));
    sessionStorage.setItem(`popupMessage-${questionId}`, popupMessage);

    document.querySelector(`.btn[onclick^="validate(${questionId})"]`).textContent = 'Feedback';
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
    if (sessionStorage.getItem(`isValidationDone-${questionId}`) === 'true') {
        isValidationDone = true;
        const savedConnexions = JSON.parse(sessionStorage.getItem(`connexions-${questionId}`));
        let correct = true;
        for (let motId in savedConnexions) {
            const defId = savedConnexions[motId];
            const mot = document.querySelector(`.word[data-id='${motId}']`);
            const definition = document.querySelector(`.definition[data-id='${defId}']`);
            const isCorrect = motId == defId;
            drawLine(mot, definition, isCorrect ? 'green' : 'red');
            connexions[motId] = defId;
            reverseConnexions[defId] = motId;
            if (!isCorrect) {
                correct = false;
            }
        }

        const savedPopupMessage = sessionStorage.getItem(`popupMessage-${questionId}`);
        if (savedPopupMessage) {
            document.querySelector(`.btn[onclick^="validate(${questionId})"]`).addEventListener('click', () => {
                showPopup(savedPopupMessage);
            });
        }
        document.querySelector(`.btn[onclick^="validate(${questionId})"]`).textContent = 'Feedback';
    }
}

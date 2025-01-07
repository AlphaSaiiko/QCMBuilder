const elementMinuteur = document.getElementById('timer');
let tempsRestant = parseInt(elementMinuteur.textContent, 10);
let intervalleMinuteur;

function formaterTemps(secondes) {
    const minutes = Math.floor(secondes / 60);
    const secs = secondes % 60;
    return `${String(minutes).padStart(2, '0')}:${String(secs).padStart(2, '0')}`;
}

elementMinuteur.textContent = formaterTemps(tempsRestant);

function decompte() {
    if (tempsRestant > 0) {
        tempsRestant--;
        elementMinuteur.textContent = formaterTemps(tempsRestant);
    } else {
        clearInterval(intervalleMinuteur);
        if (window.reponseSelectionnee.length > 0) {
            window.valider(); // Valider automatiquement si une réponse est sélectionnée
        } else {
            window.finMinuteur(); // Appeler finMinuteur si aucune réponse n'est sélectionnée
        }
    }
}

function demarrerMinuteur() {
    intervalleMinuteur = setInterval(decompte, 1000);
}

function arreterMinuteur() {
    clearInterval(intervalleMinuteur);
}

// Commencer le minuteur
demarrerMinuteur();

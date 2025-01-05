window.onload = function() {
    // Réinitialiser les points
    localStorage.setItem('points', 0);

    // Réinitialiser l'état de toutes les questions
    const totalQuestions = 21; // Nombre total de questions
    for (let i = 1; i <= totalQuestions; i++) {
        localStorage.removeItem(`isValidationDone-question-${i}`);
        localStorage.removeItem(`selectedAnswer-question-${i}`);
        localStorage.removeItem(`popupText-question-${i}`);
    }

    console.log("Réinitialisation effectuée !");
};

package vue;

import javax.swing.*;
import java.awt.*;
import java.io.File;


public class GenerationDEvaluation 
{

	public GenerationDEvaluation()
	{
		/*
		 * +------------+ 
		 * | PARAMETRES | 
		 * +------------+
		 */

		JFrame fenetre;

		/*
		 * +--------------+ 
		 * | CONSTRUCTEUR |
		 * +--------------+
		 */

		 // Créer la fenêtre principale
		fenetre = new JFrame("Génération d'évaluation");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setSize(500, 300);
		fenetre.setLocationRelativeTo(null);
		fenetre.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		// Panel pour regrouper les composants sur une seule ligne
		JPanel linePanel = new JPanel(new GridBagLayout());
		GridBagConstraints lineGbc = new GridBagConstraints();
		lineGbc.insets = new Insets(10, 10, 10, 10);

		// Champ de texte pour la ressource
		lineGbc.gridx = 0;
		lineGbc.gridy = 0;
		lineGbc.anchor = GridBagConstraints.WEST;
		linePanel.add(new JLabel("Ressource"), lineGbc);

		lineGbc.gridx = 1;
		lineGbc.gridy = 0;
		lineGbc.anchor = GridBagConstraints.CENTER;
		JComboBox<String> ressources = new JComboBox<>(
				new String[] { "R1.05 Intro Bd", "R1.05 Intro Bd", "R1.05 Intro Bd", "R1.05 Intro Bd" });
		linePanel.add(ressources, lineGbc);

		// Ajout du chronomètre
		lineGbc.gridx = 2;
		lineGbc.gridy = 0;
		lineGbc.anchor = GridBagConstraints.CENTER;
		String imagePath = "QCMBuilder/lib/icones/timer.png";
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            ImageIcon timerIcon = new ImageIcon(imagePath);
            JLabel timerLabel = new JLabel(timerIcon);
            linePanel.add(timerLabel, lineGbc);
        } else {
            JLabel errorLabel = new JLabel("Image non trouvée : " + imagePath);
            errorLabel.setForeground(Color.RED);
            linePanel.add(errorLabel, lineGbc);
        }

		// Ajout des boutons radio
		lineGbc.gridx = 3;
		lineGbc.gridy = 0;
		lineGbc.anchor = GridBagConstraints.CENTER;
		JRadioButton ouiButton = new JRadioButton("Oui");
		JRadioButton nonButton = new JRadioButton("Non");

		ButtonGroup group = new ButtonGroup();
		group.add(ouiButton);
		group.add(nonButton);

		JPanel radioPanel = new JPanel(new GridBagLayout());
		GridBagConstraints radioGbc = new GridBagConstraints();
		radioGbc.gridx = 0;
		radioGbc.gridy = GridBagConstraints.RELATIVE;
		radioGbc.insets = new Insets(5, 5, 5, 5);
		radioGbc.anchor = GridBagConstraints.CENTER;


		radioPanel.add(ouiButton, radioGbc);
		radioPanel.add(nonButton, radioGbc);

		linePanel.add(radioPanel, lineGbc);

		// Ajouter le panel regroupant les composants à la fenêtre principale
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		fenetre.add(linePanel, gbc);

		// Rendre la fenêtre visible
		fenetre.setVisible(true);
	}

	public static void main(String[] args)
	{
		new GenerationDEvaluation();
	}
}
package vue;

import javax.swing.*;
import java.awt.*;


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
		fenetre.setSize(800, 600);
		fenetre.setLocationRelativeTo(null);
		fenetre.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		// Champ de texte pour la ressource
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		fenetre.add(new JLabel("Ressource"), gbc);

		gbc.gridy = 1;
		JTextField textField = new JTextField(15);
		fenetre.add(textField, gbc);

		// Bouton déroulant (combo box)
		gbc.gridx = 2;
		gbc.gridwidth = 1;
		String[] options = { "Option 1", "Option 2", "Option 3" };
		JComboBox<String> comboBox = new JComboBox<>(options);
		fenetre.add(comboBox, gbc);

		// Ajout du chronomètre et des boutons radio
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		JLabel timerLabel = new JLabel(new ImageIcon("src/lib/icones/timer.png")); 
		fenetre.add(timerLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		JRadioButton ouiButton = new JRadioButton("Oui");
		JRadioButton nonButton = new JRadioButton("Non");

		// Groupement des boutons radio
		ButtonGroup group = new ButtonGroup();
		group.add(ouiButton);
		group.add(nonButton);

		JPanel radioPanel = new JPanel();
		radioPanel.add(ouiButton);
		radioPanel.add(nonButton);

		fenetre.add(radioPanel, gbc);

		// Afficher la fenêtre
		fenetre.setVisible(true);


	}

	public void main(String[] args)
	{
		new GenerationDEvaluation();
	}
}
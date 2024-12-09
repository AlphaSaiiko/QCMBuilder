package vue;

import java.awt.*;
import javax.swing.*;

import modele.Ressource;

public class CreerNotion extends JFrame
{
	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
	public CreerNotion() {

		setTitle("Créer une ressource");

		JPanel panelprincipal = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5); // Marges entre les composants

		// JComboBox pour choisir le type de ressource
		String[] ressources = Ressource.getAllRessources();
		JComboBox<String> choixRessource = new JComboBox<>(ressources);
		choixRessource.setBorder(BorderFactory.createTitledBorder("Ressource"));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.3;
		panelprincipal.add(choixRessource, gbc);

		// JTextArea pour le titre de la ressource
		JTextArea titre = new JTextArea(1, 20); // Taille réduite
		titre.setBorder(BorderFactory.createTitledBorder("Titre de la ressource"));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		panelprincipal.add(titre, gbc);

		// JButton pour ajouter une ressource à côté du titre
		JButton ajouter = new JButton("Ajouter");
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.2;
		panelprincipal.add(ajouter, gbc);

		this.add(panelprincipal);
		this.setSize(600, 100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	// Création des boutons ronds pour les niveaux de difficultés
	public static RoundButton creerButtonRond(Color couleur, String texte)
	{
		RoundButton button = new RoundButton(texte);
		button.setPreferredSize(new Dimension(45, 45));
		button.setBackground(couleur);
		button.setOpaque(false);
		button.setBorderPainted(false);
		return button;
	}

	public static void main(String[] args)
	{
		new CreerNotion();
	}
}

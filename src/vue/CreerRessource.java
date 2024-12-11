package vue;

import java.awt.*;
import javax.swing.*;
import modele.Ressource;

public class CreerRessource extends JFrame
{
	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
	public CreerRessource() {

		setTitle("Créer une ressource");

		JPanel panelprincipal = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5); // Marges entre les composants

		// JTextArea pour le titre de la ressource
		JTextArea titre = new JTextArea(1, 20); // Taille réduite
		titre.setBorder(BorderFactory.createTitledBorder("Titre de la ressource"));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.8;
		gbc.weighty = 0;
		panelprincipal.add(titre, gbc);

		// JButton pour ajouter une ressource à côté du titre
		JButton ajouter = new JButton("Ajouter");
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.2;
		gbc.weighty = 0;
		panelprincipal.add(ajouter, gbc);

		// Récupérer le contenu du JTextArea et créer une nouvelle ressource
		ajouter.addActionListener(e -> {
			String titreRessource = titre.getText();
			if (!titreRessource.trim().isEmpty()) {
				Ressource ressource = new Ressource(titreRessource);
				System.out.println("Nouvelle ressource créée avec le titre: " + titreRessource);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Le titre de la ressource ne peut pas être vide.", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		});

		this.add(panelprincipal);
		this.setSize(500, 150);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		new CreerRessource();
	}
}

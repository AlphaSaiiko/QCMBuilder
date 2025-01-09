package vue.notionetressource;

import controleur.Controleur;
import java.awt.*;
import javax.swing.*;
import modele.Ressource;

public class CreerNotion extends JFrame
{
	/**
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */

	public CreerNotion(Ressource ressource)
	{
		setTitle("Créer une notion");


		//Panel principal
		JPanel panelPrincipal = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);


		// JTextArea pour le titre de la ressource
		JTextArea texteTitre = new JTextArea(1, 20);
		texteTitre.setBorder(BorderFactory.createTitledBorder("Titre de la notion"));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.8;
		panelPrincipal.add(texteTitre, gbc);


		// JButton pour ajouter une ressource à côté du titre
		JButton boutonAjouter = new JButton("Ajouter");
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.2;
		panelPrincipal.add(boutonAjouter, gbc);

		
		// Récupérer le contenu du JTextArea et créer une nouvelle notion
		boutonAjouter.addActionListener(e -> {
			String titreNotion = texteTitre.getText();
			if (!titreNotion.trim().isEmpty())
			{
				Controleur.creerNotion(titreNotion, ressource);
				this.dispose();
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Le titre de la notion ne peut pas être vide.", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		});


		// Ajout du panel principal à la frame et configuration de cette dernière
		this.add(panelPrincipal);
		this.setSize(600, 100);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}

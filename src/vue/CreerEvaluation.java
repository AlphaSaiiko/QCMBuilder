package vue;

import java.awt.*;
import java.io.File;
import javax.swing.*;

import controleur.Controleur;
import modele.Ressource;

public class CreerEvaluation extends JFrame
{
	/**
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */

	public CreerEvaluation()
	{
		setTitle("Génération d'évaluation");

		
		//Panel principal
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BorderLayout());


		// Bouton "Menu principal"
		String imageMenu = "./lib/icones/home.png";
		ImageIcon icon = new ImageIcon(imageMenu);
		Image img = icon.getImage();
		Image newImg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newImg);

		JButton btnMenu = new JButton(icon);
		btnMenu.addActionListener(e -> {
			Controleur.ouvrirAccueil();
			dispose();
		});

		JPanel panelMenu = new JPanel();
		panelMenu.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelMenu.add(btnMenu);


		// Panel pour regrouper les composants sur une seule ligne
		JPanel panelLigne = new JPanel(new GridBagLayout());
		GridBagConstraints gbcLigne = new GridBagConstraints();
		gbcLigne.insets = new Insets(10, 10, 10, 10);


		// Champ de texte pour la ressource
		gbcLigne.gridx = 0;
		gbcLigne.gridy = 0;
		gbcLigne.anchor = GridBagConstraints.WEST;
		panelLigne.add(new JLabel("Ressource"), gbcLigne);

		gbcLigne.gridx = 1;
		gbcLigne.gridy = 0;
		gbcLigne.anchor = GridBagConstraints.CENTER;
		JComboBox<String> ressources = new JComboBox<>(Controleur.getNomsRessources());
		panelLigne.add(ressources, gbcLigne);

		
		// Ajout du chronomètre
		gbcLigne.gridx = 2;
		gbcLigne.gridy = 0;
		gbcLigne.anchor = GridBagConstraints.CENTER;
		String cheminImage = "./lib/icones/timer.png";
		File fichierImage = new File(cheminImage);
		if (fichierImage.exists())
		{
			ImageIcon timerIcon = new ImageIcon(cheminImage);
			JLabel timerLabel = new JLabel(timerIcon);
			panelLigne.add(timerLabel, gbcLigne);
		}
		else
		{
			JLabel errorLabel = new JLabel("Image non trouvée : " + cheminImage);
			errorLabel.setForeground(Color.RED);
			panelLigne.add(errorLabel, gbcLigne);
		}


		// Ajout des boutons radio
		gbcLigne.gridx = 3;
		gbcLigne.gridy = 0;
		gbcLigne.anchor = GridBagConstraints.CENTER;
		JRadioButton boutonOui = new JRadioButton("Oui");
		JRadioButton boutonNon = new JRadioButton("Non");

		ButtonGroup groupe = new ButtonGroup();
		groupe.add(boutonOui);
		groupe.add(boutonNon);

		JPanel panelBoutons = new JPanel(new GridBagLayout());
		GridBagConstraints gbcBoutons = new GridBagConstraints();
		gbcBoutons.gridx = 0;
		gbcBoutons.gridy = GridBagConstraints.RELATIVE;
		gbcBoutons.insets = new Insets(5, 5, 5, 5);
		gbcBoutons.anchor = GridBagConstraints.CENTER;

		panelBoutons.add(boutonOui, gbcBoutons);
		panelBoutons.add(boutonNon, gbcBoutons);

		panelLigne.add(panelBoutons, gbcLigne);


		// Ajouter le panel regroupant les composants à la fenêtre principale
		panelPrincipal.add(panelMenu, BorderLayout.NORTH);
		panelPrincipal.add(panelLigne, BorderLayout.CENTER);


		// Ajouter un bouton "Créer une nouvelle évaluation"
		JButton boutonCreer = new JButton("Créer une nouvelle évaluation");
		JPanel panelCreer = new JPanel();
		panelCreer.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelCreer.add(boutonCreer);

		boutonCreer.addActionListener (e -> {
			String nomRessource = String.valueOf(ressources.getSelectedItem());
			Ressource ressource = Controleur.trouverRessourceParNom(nomRessource);

			String erreur = "";

			if (ressource.getEnsNotions().isEmpty())                erreur += "Cette ressource n'a pas de notions !\n";

			if (! boutonOui.isSelected() && ! boutonNon.isSelected()) erreur += "Veuillez décider si vous souhaitez donner un temps limite à l'évaluation !\n";


			if (erreur.isEmpty())
			{
				Controleur.creerEvaluation();
				TabEvaluation tabEval = new TabEvaluation(ressource);
				dispose();
			}
			else
			{
				JOptionPane.showMessageDialog(null, erreur, "Erreur : ", JOptionPane.ERROR_MESSAGE);
			}
			
		});

		panelPrincipal.add(panelCreer, BorderLayout.SOUTH);

		
		// Ajout du panel principal à la frame et configuration de cette dernière
		this.add(panelPrincipal);
		this.setSize(500, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static void main(String[] args)
	{
		new CreerEvaluation();
	}
}

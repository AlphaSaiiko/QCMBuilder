package vue;

import controleur.Controleur;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Accueil extends JFrame
{
	/*
		* +--------------+
		* | CONSTRUCTEUR |
		* +--------------+
		*/
	public Accueil()
	{
		setTitle("Accueil");


		//Panel principal
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new EmptyBorder(25, 0, 0, 0));
		panelPrincipal.setLayout(new BorderLayout());


		//Panel titre
		JPanel panelTitre = new JPanel();
		panelTitre.setLayout(new BorderLayout());
		JLabel lblTitre = new JLabel("Bienvenue dans l'Application QCM Builder");
		lblTitre.setFont(new Font("Arial", Font.BOLD, 24));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitre.add(lblTitre, BorderLayout.CENTER);


		// Panel contenant les boutons "Générer une Evaluation", "Créer une question" et "Paramètres"
		JPanel panelBoutons = new JPanel();
		panelBoutons.setLayout(new GridBagLayout());
		GridBagConstraints gbcBoutons = new GridBagConstraints();
		gbcBoutons.insets = new Insets(10, 10, 10, 10);
		gbcBoutons.fill = GridBagConstraints.HORIZONTAL;
		gbcBoutons.gridx = 0;
		gbcBoutons.gridy = GridBagConstraints.RELATIVE;


		// Création des boutons "Générer une Evaluation", "Créer une question" et "Paramètres"
		JButton btnGenererEval = new JButton("Générer une Evaluation");
		btnGenererEval.setFont(new Font("Arial", Font.PLAIN, 18));

		JButton btnCreerQuestion = new JButton("Créer une question");
		btnCreerQuestion.setFont(new Font("Arial", Font.PLAIN, 18));

		JButton btnParametres = new JButton("Paramètres");
		btnParametres.setFont(new Font("Arial", Font.PLAIN, 18));


		// Configuration de la taille des boutons
		Dimension tailleBoutons = new Dimension(300, 50);
		btnGenererEval.setPreferredSize(tailleBoutons);
		btnCreerQuestion.setPreferredSize(tailleBoutons);
		btnParametres.setPreferredSize(tailleBoutons);


		// Ajout des ActionListener
		btnGenererEval.addActionListener(e -> {
			if (Controleur.getListRessource().isEmpty())
			{
				JOptionPane.showMessageDialog(this, "Aucune ressource n'existe. Veuillez ajouter une ressource.", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				Controleur.creerEvaluation();
				this.dispose();
			}
		});

		btnCreerQuestion.addActionListener(e -> {
			if (Controleur.getListRessource().isEmpty())
			{
				JOptionPane.showMessageDialog(this, "Aucune ressource n'existe. Veuillez ajouter une ressource.", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				Controleur.creerQuestion();
				this.dispose();
			}
		});

		btnParametres.addActionListener(e -> {
			Controleur.ouvrirParametres();
			this.dispose();
		});


		// Ajout des boutons au panel des boutons
		panelBoutons.add(btnParametres, gbcBoutons);
		panelBoutons.add(btnCreerQuestion, gbcBoutons);
		panelBoutons.add(btnGenererEval, gbcBoutons);


		// Ajout des panels secondaires au panel principal
		panelPrincipal.add(panelTitre, BorderLayout.NORTH);
		panelPrincipal.add(panelBoutons, BorderLayout.CENTER);


		// Ajout du panel principal à la frame et configuration de cette dernière
		this.add(panelPrincipal);
		this.setSize(600, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);


		// Vérification de la liste des ressources après l'initialisation de l'interface graphique
		SwingUtilities.invokeLater(() -> {
			if (Controleur.getListRessource().isEmpty())
			{
				btnGenererEval.setEnabled(false);
				btnCreerQuestion.setEnabled(false);
			}
		});
	}
}

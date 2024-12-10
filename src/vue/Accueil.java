package vue;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Accueil extends JFrame
{
	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
	public Accueil() 
	{
		setTitle("Accueil");

		// Configuration du panel principal + espace de 25 au nord
		JPanel panelPrincipal = new JPanel();        
		panelPrincipal.setBorder(new EmptyBorder(25, 0, 0, 0));
		panelPrincipal.setLayout(new BorderLayout());

		// Configuration du titre
		JPanel panelTitre = new JPanel();
		panelTitre.setLayout(new BorderLayout());
		JLabel lblTitre = new JLabel("Bienvenue dans l'Application QCM Builder");
		lblTitre.setFont(new Font("Arial", Font.BOLD, 24));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitre.add(lblTitre, BorderLayout.CENTER);

		// Configuration du panel des boutons de l'accueil
		JPanel panelBoutons = new JPanel();
		panelBoutons.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;

		// Création des boutons
		JButton btnCreerQCM = new JButton("Créer un QCM");
		JButton btnCreerQuestion = new JButton("Créer une question");
		JButton btnParametres = new JButton("Paramètres");

		// Configuration police des boutons
		btnCreerQCM.setFont(new Font("Arial", Font.PLAIN, 18));
		btnCreerQuestion.setFont(new Font("Arial", Font.PLAIN, 18));
		btnParametres.setFont(new Font("Arial", Font.PLAIN, 18));

		// Configuration de la taille des boutons
		Dimension buttonSize = new Dimension(300, 50);
		btnCreerQCM.setPreferredSize(buttonSize);
		btnCreerQuestion.setPreferredSize(buttonSize);
		btnParametres.setPreferredSize(buttonSize);

		// Ajout des interactions des boutons
		btnCreerQCM.addActionListener(e -> {
			new CreerEvaluation();
			this.dispose();
		});
		
		btnCreerQuestion.addActionListener(e -> {
			new CreerQuestion();
			this.dispose();
		});

		btnParametres.addActionListener(e ->{
			new Parametre();
			this.dispose();
		});

		// Ajout des boutons au panel des boutons
		panelBoutons.add(btnCreerQCM, gbc);
		panelBoutons.add(btnCreerQuestion, gbc);
		panelBoutons.add(btnParametres, gbc);

		// Ajout des panels secondaires au panel principal
		panelPrincipal.add(panelTitre, BorderLayout.NORTH);
		panelPrincipal.add(panelBoutons, BorderLayout.CENTER);

		// Ajout du panel principal et configuration de la frame
		this.add(panelPrincipal);
		this.setSize(600, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Accueil();
	}
}

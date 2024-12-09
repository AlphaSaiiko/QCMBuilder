package vue;

import java.awt.*;
import javax.swing.*;

public class Accueil extends JFrame
{
	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
	public Accueil() {
		setTitle("Accueil");

		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BorderLayout());

		JPanel panelTitre = new JPanel();
		panelTitre.setLayout(new BorderLayout());
		JLabel lblTitre = new JLabel("Bienvenue dans l'Application QCM Builder");
		lblTitre.setFont(new Font("Arial", Font.BOLD, 24));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitre.add(lblTitre, BorderLayout.CENTER);

		JPanel panelBoutons = new JPanel();
		panelBoutons.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;

		JButton btnCreerQCM = new JButton("Créer un QCM");
		JButton btnCreerQuestion = new JButton("Créer une question");
		JButton btnParametres = new JButton("Paramètres");

		btnCreerQCM.setFont(new Font("Arial", Font.PLAIN, 18));
		btnCreerQuestion.setFont(new Font("Arial", Font.PLAIN, 18));
		btnParametres.setFont(new Font("Arial", Font.PLAIN, 18));

		Dimension buttonSize = new Dimension(300, 50);
		btnCreerQCM.setPreferredSize(buttonSize);
		btnCreerQuestion.setPreferredSize(buttonSize);
		btnParametres.setPreferredSize(buttonSize);

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

		panelBoutons.add(btnCreerQCM, gbc);
		panelBoutons.add(btnCreerQuestion, gbc);
		panelBoutons.add(btnParametres, gbc);

		panelPrincipal.add(panelTitre, BorderLayout.NORTH);
		panelPrincipal.add(panelBoutons, BorderLayout.CENTER);

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

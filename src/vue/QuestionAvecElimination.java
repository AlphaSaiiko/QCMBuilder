package vue;

import controleur.Controleur;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import modele.Question;

public class QuestionAvecElimination extends JFrame
{
	private JPanel panelQuestion;
	private JTextArea textareaQuestion;
	private Question question;

	public QuestionAvecElimination(Question question)
	{
		this.question = question;
		// Initialiser le conteneur principal
		JPanel panelPrincipal = new JPanel(new BorderLayout());

		// Initialiser le panel des questions
		panelQuestion = new JPanel();
		panelQuestion.setLayout(new BoxLayout(panelQuestion, BoxLayout.Y_AXIS));

		// Ajouter un champ de texte pour écrire la question
		textareaQuestion = new JTextArea(5, 60);
		textareaQuestion.setLineWrap(true);
		textareaQuestion.setWrapStyleWord(true);
		textareaQuestion.setFont(new Font("Arial", Font.PLAIN, 18));
		textareaQuestion.setMaximumSize(new Dimension(Integer.MAX_VALUE, textareaQuestion.getPreferredSize().height));
		panelQuestion.add(new JScrollPane(textareaQuestion));

		// Ajouter un panel pour les boutons "Ajouter", "Explication" et "Enregistrer"
		JPanel panelBoutons = new JPanel();
		panelBoutons.setLayout(new FlowLayout(FlowLayout.LEFT));

		// Ajouter un bouton pour ajouter un nouveau panel trash avec une icône
		ImageIcon iconeAjouter = new ImageIcon(
				new ImageIcon("./lib/icones/add.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton boutonAjouter = new JButton(iconeAjouter);
		boutonAjouter.setPreferredSize(new Dimension(40, 40));
		boutonAjouter.setBorderPainted(false);
		boutonAjouter.setContentAreaFilled(false);
		boutonAjouter.setFocusPainted(false);
		boutonAjouter.setOpaque(false);
		panelBoutons.add(boutonAjouter);

		// Ajouter un bouton "Explication" avec une icône
		ImageIcon iconeExplication = new ImageIcon(
				new ImageIcon("./lib/icones/edit.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton boutonExplication = new JButton(iconeExplication);
		boutonExplication.setPreferredSize(new Dimension(40, 40));
		boutonExplication.setBorderPainted(false);
		boutonExplication.setContentAreaFilled(false);
		boutonExplication.setFocusPainted(false);
		boutonExplication.setOpaque(false);
		panelBoutons.add(boutonExplication);

		// Ajouter un bouton "Enregistrer"
		JButton boutonEnregistrer = new JButton("Enregistrer");
		panelBoutons.add(boutonEnregistrer);

		// Ajouter le panel des boutons au panel des questions
		panelQuestion.add(panelBoutons);

		// Ajouter un ActionListener au bouton "Ajouter"
		boutonAjouter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// Créer un nouveau panel trash
				JPanel newTrashPanel = new JPanel(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.insets = new Insets(5, 5, 5, 5);
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.anchor = GridBagConstraints.WEST;

				// Ajouter un label intitulé "Réponse:"
				JLabel labelReponse = new JLabel("Réponse:");
				labelReponse.setFont(new Font("Arial", Font.PLAIN, 18));
				newTrashPanel.add(labelReponse, gbc);

				// Augmenter la taille du JTextField
				JTextField newTextField = new JTextField();
				newTextField.setFont(new Font("Arial", Font.PLAIN, 18));
				newTextField.setPreferredSize(new Dimension(400, 30));
				gbc.gridx = 1;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.weightx = 1.0;
				newTrashPanel.add(newTextField, gbc);

				gbc.gridx = 2;
				gbc.fill = GridBagConstraints.NONE;
				gbc.weightx = 0;

				// Ajouter un label pour l'ordre
				JLabel labelOrdre = new JLabel("Ordre:");
				labelOrdre.setFont(new Font("Arial", Font.PLAIN, 18));
				gbc.gridx = 3;
				newTrashPanel.add(labelOrdre, gbc);

				// Ajouter le JTextField pour l'ordre
				JTextField texteOrdre = new JTextField();
				texteOrdre.setFont(new Font("Arial", Font.PLAIN, 18));
				texteOrdre.setPreferredSize(new Dimension(50, 30));
				gbc.gridx = 4;
				newTrashPanel.add(texteOrdre, gbc);

				// Ajouter un label pour les points
				JLabel labelPoints = new JLabel("Points:");
				labelPoints.setFont(new Font("Arial", Font.PLAIN, 18));
				gbc.gridx = 5;
				newTrashPanel.add(labelPoints, gbc);

				// Ajouter le JTextField pour les points
				JTextField textePoints = new JTextField();
				textePoints.setFont(new Font("Arial", Font.PLAIN, 18));
				textePoints.setPreferredSize(new Dimension(50, 30));
				gbc.gridx = 6;
				newTrashPanel.add(textePoints, gbc);

				// Ajouter un bouton radio
				JRadioButton newRadioButton = new JRadioButton();
				newRadioButton.setPreferredSize(new Dimension(30, 30));
				gbc.gridx = 7;
				newTrashPanel.add(newRadioButton, gbc);

				// Redimensionner l'icône de poubelle
				ImageIcon newTrashIcon = new ImageIcon(new ImageIcon("./lib/icones/delete.png").getImage()
						.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
				JButton newTrashButton = new JButton(newTrashIcon);
				newTrashButton.setPreferredSize(new Dimension(40, 40));
				newTrashButton.setBorderPainted(false);
				newTrashButton.setContentAreaFilled(false);
				newTrashButton.setFocusPainted(false);
				newTrashButton.setOpaque(false);
				gbc.gridx = 8;
				newTrashPanel.add(newTrashButton, gbc);

				// Ajouter un ActionListener au bouton de poubelle
				newTrashButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						// Supprimer le panel parent lorsque l'icône de poubelle est cliquée
						panelQuestion.remove(newTrashPanel);
						panelQuestion.revalidate();
						panelQuestion.repaint();
					}
				});

				// Ajouter le nouveau panel trash au conteneur principal
				System.out.println(panelQuestion.getComponentCount() - 1);
				System.out.println(panelQuestion.getComponentCount());
				panelQuestion.add(newTrashPanel, panelQuestion.getComponentCount() - 1);

				// Rafraîchir l'interface utilisateur
				panelQuestion.revalidate();
				panelQuestion.repaint();
			}
		});

		// Ajouter un ActionListener au bouton "Explication"
		boutonExplication.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// Créer et afficher une nouvelle fenêtre pour écrire l'explication
				JFrame explicationFrame = new JFrame("Explication");
				explicationFrame.setSize(400, 300);
				explicationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				JTextArea explicationArea = new JTextArea(10, 30);
				explicationArea.setLineWrap(true);
				explicationArea.setWrapStyleWord(true);
				explicationArea.setFont(new Font("Arial", Font.PLAIN, 18));

				explicationFrame.add(new JScrollPane(explicationArea));
				explicationFrame.setVisible(true);
			}
		});

		// Ajouter un ActionListener au bouton "Enregistrer"
		boutonEnregistrer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// Enregistrer la question dans un fichier		
				question.setEnonce(textareaQuestion.getText());

				//Enregistrer les réponses
				for (int i = 1; i < panelQuestion.getComponentCount() - 1; i++)
				{
					JPanel panel = (JPanel) panelQuestion.getComponent(i);

					JTextField textField = (JTextField) panel.getComponent(1);
					JTextField ordre = (JTextField) panel.getComponent(3);
					JTextField points = (JTextField) panel.getComponent(5);
					JRadioButton radioButton = (JRadioButton) panel.getComponent(6);

					Controleur.creerReponseElimination(textField.getText(), Integer.parseInt(ordre.getText()), Double.parseDouble(points.getText()), radioButton.isSelected(), question);

					/*
					JTextField textField = (JTextField) panel.getComponent(1);
					JTextField ordre = (JTextField) panel.getComponent(4);
					JTextField points = (JTextField) panel.getComponent(6);
					JRadioButton radioButton = (JRadioButton) panel.getComponent(7);

					question.ajouterReponse(textField.getText(), Integer.parseInt(ordre.getText()), Integer.parseInt(points.getText()), radioButton.isSelected());
				*/}
			}
		});

		panelPrincipal.add(panelQuestion, BorderLayout.NORTH);

		// Ajouter tout dans le conteneur principal
		add(panelPrincipal, BorderLayout.CENTER);

		// Afficher la fenêtre
		setTitle("Question Réponse Unique");
		setSize(1000, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

}

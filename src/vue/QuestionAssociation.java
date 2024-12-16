package vue;

import controleur.Controleur;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import modele.Question;
import modele.option.OptionAssociation;

public class QuestionAssociation extends JFrame
{
	private JPanel questionPanel;
	private JTextArea questionArea;
	private JButton ajouterBouton;
	private JButton explicationBouton;
	private JButton enregistrerBouton;
	private Question question;

	public QuestionAssociation(Question question)
	{
		this.question = question;
		// Initialiser le conteneur principal
		JPanel mainPanel = new JPanel(new BorderLayout());

		// Initialiser le panel des questions
		questionPanel = new JPanel();
		questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));

		// Ajouter un champ de texte pour écrire la question
		questionArea = new JTextArea(10, 80);
		questionArea.setLineWrap(true);
		questionArea.setWrapStyleWord(true);
		questionArea.setFont(new Font("Arial", Font.PLAIN, 18));
		questionArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, questionArea.getPreferredSize().height));
		questionPanel.add(questionArea);

		// Ajouter un panel pour les boutons "Ajouter", "Explication" et
		// "Enregistrer"
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		// Initialiser le bouton "ajouter"
		ajouterBouton = new JButton("Ajouter");
		ajouterBouton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ajouterReponses();
			}
		});

		// Initialiser le bouton "Explication"
		explicationBouton = new JButton("Explication");
		explicationBouton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
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

		// Initialiser le bouton "Enregistrer"
		enregistrerBouton = new JButton("Enregistrer");
		enregistrerBouton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// Sauvegarder la question
				question.setEnonce(questionArea.getText());

				// Sauvegarder les réponses
				for (int i = 0; i < questionPanel.getComponentCount(); i++)
				{
					Component comp = questionPanel.getComponent(i);
					if (comp instanceof JPanel) {
						JPanel rowPanel = (JPanel) comp;
						JTextField textField1 = (JTextField) ((JPanel) rowPanel.getComponent(0)).getComponent(1);
						JTextField textField2 = (JTextField) ((JPanel) rowPanel.getComponent(1)).getComponent(0);

						System.out.println("Réponse 1 : " + textField1.getText());
						System.out.println("Réponse 2 : " + textField2.getText());

						// Si les champs de texte sont vides, les remplir avec des valeurs par défaut
						if (textField1.getText().isEmpty() || textField2.getText().isEmpty())
						{
							JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs de texte.", "Erreur", JOptionPane.ERROR_MESSAGE);
						}

						OptionAssociation o1 = Controleur.creerReponseAssociation(textField1.getText(), question);
						OptionAssociation o2 = Controleur.creerReponseAssociation(textField2.getText(), question);

						o1.setAssocie(o2);
						o2.setAssocie(o1);

						question.ajouterOption(o1);
						question.ajouterOption(o2);
					}
				}
			}
		});
		// Ajouter les boutons au panel des boutons
		buttonPanel.add(ajouterBouton);
		buttonPanel.add(explicationBouton);
		buttonPanel.add(enregistrerBouton);

		// Ajouter le panel des boutons au conteneur principal
		mainPanel.add(questionPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		// Ajouter le conteneur principal au frame
		add(mainPanel, BorderLayout.CENTER);

		// Afficher la fenêtre
		setTitle("Question Réponse Association");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	// Méthode pour ajouter deux TrashPanel en miroir sur la même ligne
	private void ajouterReponses()
	{
		JPanel rowPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);

		// TrashPanel
		JPanel newTrashPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.anchor = GridBagConstraints.WEST;

		// Redimensionner l'icône de poubelle
		ImageIcon newTrashIcon = new ImageIcon(
				new ImageIcon("QCMBuilder/lib/icones/delete.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton newTrashButton = new JButton(newTrashIcon);
		newTrashButton.setPreferredSize(new Dimension(40, 40));
		newTrashButton.setBorderPainted(false);
		newTrashButton.setContentAreaFilled(false);
		newTrashButton.setFocusPainted(false);
		newTrashButton.setOpaque(false);
		gbc1.gridx = 0;
		newTrashPanel.add(newTrashButton, gbc1);

		// Ajouter un JTextField
		JTextField newTextField = new JTextField();
		newTextField.setFont(new Font("Arial", Font.PLAIN, 18));
		newTextField.setPreferredSize(new Dimension(200, 30));
		gbc1.gridx = 1;
		gbc1.fill = GridBagConstraints.HORIZONTAL;
		gbc1.weightx = 1.0;
		newTrashPanel.add(newTextField, gbc1);

		// Ajouter un ActionListener au bouton de poubelle
		newTrashButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				questionPanel.remove(rowPanel);
				questionPanel.revalidate();
				questionPanel.repaint();
			}
		});

		// Ajouter le TrashPanel au rowPanel
		gbc.gridx = 0;
		rowPanel.add(newTrashPanel, gbc);

		// Ajouter le rowPanel au questionPanel
		questionPanel.add(rowPanel);
		questionPanel.revalidate();
		questionPanel.repaint();

		// Deuxième TrashPanel (miroir)
		JPanel newTrashPanel2 = new JPanel(new GridBagLayout());
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		gbc2.anchor = GridBagConstraints.WEST;

		// Augmenter la taille du JTextField
		JTextField newTextField2 = new JTextField();
		newTextField2.setFont(new Font("Arial", Font.PLAIN, 18));
		newTextField2.setPreferredSize(new Dimension(200, 30));
		gbc2.gridx = 0;
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		gbc2.weightx = 1.0;
		newTrashPanel2.add(newTextField2, gbc2);

		gbc2.gridx = 1;
		gbc2.fill = GridBagConstraints.NONE;
		gbc2.weightx = 0;

		// Redimensionner l'icône de poubelle
		ImageIcon newTrashIcon2 = new ImageIcon(new ImageIcon("QCMBuilder/lib/icones/delete.png").getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton newTrashButton2 = new JButton(newTrashIcon2);
		newTrashButton2.setPreferredSize(new Dimension(40, 40));
		newTrashButton2.setBorderPainted(false);
		newTrashButton2.setContentAreaFilled(false);
		newTrashButton2.setFocusPainted(false);
		newTrashButton2.setOpaque(false);
		newTrashPanel2.add(newTrashButton2, gbc2);

		// Ajouter un ActionListener au bouton de poubelle
		newTrashButton2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				questionPanel.remove(rowPanel);
				questionPanel.revalidate();
				questionPanel.repaint();
			}
		});

		// Ajouter les deux TrashPanel au rowPanel
		gbc.gridx = 0;
		rowPanel.add(newTrashPanel, gbc);
		gbc.gridx = 1;
		rowPanel.add(newTrashPanel2, gbc);

		// Ajouter le rowPanel au questionPanel
		questionPanel.add(rowPanel);
		questionPanel.revalidate();
		questionPanel.repaint();
	}
}
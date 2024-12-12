package vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import modele.*;

public class QuestionReponseUnique extends JFrame
{
	private JPanel questionPanel;
	private ButtonGroup buttonGroup;
	private JTextArea questionArea;
	private Question question;

	public QuestionReponseUnique(Question question)
	{
		this.question = question;
		// Initialiser le conteneur principal
		JPanel mainPanel = new JPanel(new BorderLayout());

		// Initialiser le panel des questions
		questionPanel = new JPanel();
		questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));

		// Initialiser le groupe de boutons radio
		buttonGroup = new ButtonGroup();

		// Ajouter un champ de texte pour écrire la question
		this.questionArea = new JTextArea(10, 80);
		this.questionArea.setLineWrap(true);
		this.questionArea.setWrapStyleWord(true);
		this.questionArea.setFont(new Font("Arial", Font.PLAIN, 18));
		this.questionArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, questionArea.getPreferredSize().height));
		questionPanel.add(questionArea);

		// Ajouter un panel pour les boutons "Ajouter", "Explication" et
		// "Enregistrer"
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		// Ajouter un bouton pour ajouter un nouveau panel trash avec une icône
		ImageIcon addIcon = new ImageIcon(
				new ImageIcon("./lib/icones/add.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton addButton = new JButton(addIcon);
		addButton.setPreferredSize(new Dimension(40, 40));
		addButton.setBorderPainted(false);
		addButton.setContentAreaFilled(false);
		addButton.setFocusPainted(false);
		addButton.setOpaque(false);
		buttonPanel.add(addButton);

		// Ajouter un bouton "Explication" avec une icône
		ImageIcon explicationIcon = new ImageIcon(
				new ImageIcon("./lib/icones/edit.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton explicationButton = new JButton(explicationIcon);
		explicationButton.setPreferredSize(new Dimension(40, 40));
		explicationButton.setBorderPainted(false);
		explicationButton.setContentAreaFilled(false);
		explicationButton.setFocusPainted(false);
		explicationButton.setOpaque(false);
		buttonPanel.add(explicationButton);

		// Ajouter un bouton "Enregistrer"
		JButton saveBtn = new JButton("Enregistrer");
		buttonPanel.add(saveBtn);

		// Ajouter le panel des boutons au panel des questions
		questionPanel.add(buttonPanel);

		// Ajouter un ActionListener au bouton "Ajouter"
		addButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// Créer un nouveau panel trash
				JPanel poubellePnl = new JPanel(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.insets = new Insets(5, 5, 5, 5);
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.anchor = GridBagConstraints.WEST;

				// Redimensionner l'icône de poubelle
				ImageIcon poubelleIcon = new ImageIcon(new ImageIcon("./lib/icones/delete.png").getImage()
						.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
				JButton poubelleBtn = new JButton(poubelleIcon);
				poubelleBtn.setPreferredSize(new Dimension(40, 40));
				poubelleBtn.setBorderPainted(false);
				poubelleBtn.setContentAreaFilled(false);
				poubelleBtn.setFocusPainted(false);
				poubelleBtn.setOpaque(false);
				poubellePnl.add(poubelleBtn, gbc);

				// Ajouter un ActionListener au bouton de poubelle
				poubelleBtn.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						// Supprimer le panel parent lorsque l'icône de poubelle
						// est cliquée
						questionPanel.remove(poubellePnl);
						questionPanel.revalidate();
						questionPanel.repaint();
					}
				});

				// Augmenter la taille du JTextField
				JTextField newTextField = new JTextField();
				newTextField.setFont(new Font("Arial", Font.PLAIN, 18));
				newTextField.setPreferredSize(new Dimension(400, 30));
				gbc.gridx = 1;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.weightx = 1.0;
				poubellePnl.add(newTextField, gbc);

				gbc.gridx = 2;
				gbc.fill = GridBagConstraints.NONE;
				gbc.weightx = 0;

				// Ajouter un bouton radio
				JRadioButton newRadioButton = new JRadioButton();
				newRadioButton.setPreferredSize(new Dimension(30, 30));
				buttonGroup.add(newRadioButton);
				poubellePnl.add(newRadioButton, gbc);

				// Ajouter le nouveau panel trash au conteneur principal
				questionPanel.add(poubellePnl, questionPanel.getComponentCount() - 1);

				// Rafraîchir l'interface utilisateur
				questionPanel.revalidate();
				questionPanel.repaint();
			}
		});

		// Ajouter un ActionListener au bouton "Explication"
		explicationButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// Créer et afficher une nouvelle fenêtre pour écrire
				// l'explication
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
		saveBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// Enregistrer la question dans un fichier
				/*String questionText = questionArea.getText();
				try (FileWriter writer = new FileWriter("question.txt"))
				{
					writer.write(questionText);
					JOptionPane.showMessageDialog(null, "Question enregistrée avec succès !");
				} catch (IOException ex)
				{
					JOptionPane.showMessageDialog(null, "Erreur lors de l'enregistrement de la question.");
				}*/
				question.setIntitule(questionArea.getText());
			}
		});

		mainPanel.add(questionPanel, BorderLayout.NORTH);

		// Ajouter tout dans le conteneur principal
		add(mainPanel, BorderLayout.CENTER);

		// Afficher la fenêtre
		setTitle("Question Réponse Unique");
		setSize(800, 600);
        setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

}
package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionReponseUnique extends JFrame
{
	private JPanel questionPanel;
	private ButtonGroup buttonGroup;

	public QuestionReponseUnique()
	{
		// Initialiser le conteneur principal
		JPanel mainPanel = new JPanel(new BorderLayout());

		// Initialiser le panel des questions
		questionPanel = new JPanel();
		questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));

		// Initialiser le groupe de boutons radio
		buttonGroup = new ButtonGroup();

		// Ajouter un champ de texte pour écrire la question
		JTextArea questionArea = new JTextArea(10, 80);
		questionArea.setLineWrap(true);
		questionArea.setWrapStyleWord(true);
		questionArea.setFont(new Font("Arial", Font.PLAIN, 18));
		questionArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, questionArea.getPreferredSize().height));
		questionPanel.add(questionArea);

		// Ajouter un bouton pour ajouter un nouveau panel trash
		JButton addButton = new JButton("Ajouter");
		questionPanel.add(addButton);

		// Ajouter un ActionListener au bouton
		addButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// Créer un nouveau panel trash
				JPanel newTrashPanel = new JPanel(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.insets = new Insets(5, 5, 5, 5);
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.anchor = GridBagConstraints.WEST;

				// Redimensionner l'icône de poubelle
				ImageIcon newTrashIcon = new ImageIcon(new ImageIcon("QCMBuilder/lib/icones/delete.png").getImage()
						.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
				JButton newTrashButton = new JButton(newTrashIcon);
				newTrashButton.setPreferredSize(new Dimension(40, 40));
				newTrashButton.setBorderPainted(false);
				newTrashButton.setContentAreaFilled(false);
				newTrashButton.setFocusPainted(false);
				newTrashButton.setOpaque(false);
				newTrashPanel.add(newTrashButton, gbc);

				// Ajouter un ActionListener au bouton de poubelle
				newTrashButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						// Supprimer le panel parent lorsque l'icône de poubelle
						// est cliquée
						questionPanel.remove(newTrashPanel);
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
				newTrashPanel.add(newTextField, gbc);

				gbc.gridx = 2;
				gbc.fill = GridBagConstraints.NONE;
				gbc.weightx = 0;

				// Ajouter un bouton radio
				JRadioButton newRadioButton = new JRadioButton();
				newRadioButton.setPreferredSize(new Dimension(30, 30));
				buttonGroup.add(newRadioButton);
				newTrashPanel.add(newRadioButton, gbc);

				// Ajouter le nouveau panel trash au conteneur principal
				questionPanel.add(newTrashPanel, questionPanel.getComponentCount() - 1);

				// Rafraîchir l'interface utilisateur
				questionPanel.revalidate();
				questionPanel.repaint();
			}
		});

		mainPanel.add(questionPanel, BorderLayout.NORTH);

		// Ajouter tout dans le conteneur principal
		add(mainPanel, BorderLayout.CENTER);

		// Afficher la fenêtre
		setTitle("Question Réponse Unique");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(QuestionReponseUnique::new);
	}
}
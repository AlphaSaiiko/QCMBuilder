package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionLiaison extends JFrame
{
	private JPanel questionPanel;
	private JTextArea questionArea;
	private JButton addButton;
	private JButton explicationButton;
	private JButton saveButton;
	private JButton lierButton;
	private JComboBox<String> questionComboBox;
	private JComboBox<String> reponseComboBox;
	private List<String> questions;
	private List<String> reponses;
	private Map<String, String> questionReponseMap;

	public QuestionLiaison()
	{
		questions = new ArrayList<>();
		reponses = new ArrayList<>();
		questionReponseMap = new HashMap<>();

		// Initialiser le conteneur principal
		JPanel mainPanel = new JPanel(new BorderLayout());

		// Initialiser le panel des questions
		questionPanel = new JPanel();
		questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));

		// Ajouter un champ de texte pour écrire la question
		questionArea = new JTextArea(5, 40);
		questionArea.setLineWrap(true);
		questionArea.setWrapStyleWord(true);
		questionArea.setFont(new Font("Arial", Font.PLAIN, 14));
		Dimension fixedSize = new Dimension(400, 100);
		questionArea.setPreferredSize(fixedSize);
		questionArea.setMaximumSize(fixedSize);
		questionArea.setMinimumSize(fixedSize);
		questionPanel.add(new JScrollPane(questionArea));

		// Ajouter un panel pour les boutons "Ajouter", "Explication",
		// "Enregistrer" et "Lier Q&R"
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		// Initialiser le bouton "Ajouter"
		addButton = new JButton("Ajouter");
		addButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				addTrashPanels();
			}
		});
		buttonPanel.add(addButton);

		// Initialiser le bouton "Explication"
		explicationButton = new JButton("Explication");
		explicationButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFrame explicationFrame = new JFrame("Ajouter Explication");
				explicationFrame.setSize(300, 200);
				explicationFrame.setLocationRelativeTo(null);
				JTextArea explicationArea = new JTextArea(5, 20);
				explicationFrame.add(new JScrollPane(explicationArea), BorderLayout.CENTER);
				JButton saveExplicationButton = new JButton("Enregistrer Explication");
				saveExplicationButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						String explication = explicationArea.getText();
						System.out.println("Explication enregistrée: " + explication);
						explicationFrame.dispose();
					}
				});
				explicationFrame.add(saveExplicationButton, BorderLayout.SOUTH);
				explicationFrame.setVisible(true);
			}
		});
		buttonPanel.add(explicationButton);

		// Initialiser le bouton "Enregistrer"
		saveButton = new JButton("Enregistrer");
		saveButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try (FileWriter writer = new FileWriter("questions_et_reponses.txt", true))
				{
					for (String question : questions)
					{
						writer.write("Question: " + question + "\n");
						String reponse = questionReponseMap.get(question);
						if (reponse != null)
						{
							writer.write("Réponse: " + reponse + "\n");
						}
					}
					questionArea.setText("");
				} catch (IOException ex)
				{
					ex.printStackTrace();
				}
			}
		});
		buttonPanel.add(saveButton);

		// Initialiser les JComboBox pour les questions et les réponses
		questionComboBox = new JComboBox<>();
		reponseComboBox = new JComboBox<>();
		buttonPanel.add(new JLabel("Question:"));
		buttonPanel.add(questionComboBox);
		buttonPanel.add(new JLabel("Réponse:"));
		buttonPanel.add(reponseComboBox);

		// Initialiser le bouton "Lier Q&R"
		lierButton = new JButton("Lier Q&R");
		lierButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String question = (String) questionComboBox.getSelectedItem();
				String reponse = (String) reponseComboBox.getSelectedItem();
				lierQuestionsAvecReponses(question, reponse);
			}
		});
		buttonPanel.add(lierButton);

		// Ajouter le panel des boutons au panel principal
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		mainPanel.add(new JScrollPane(questionPanel), BorderLayout.CENTER);

		// Ajouter le panel principal au frame
		add(mainPanel);

		// Configurer le frame
		setTitle("Question Liaison");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void addTrashPanels()
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
		ImageIcon newTrashIcon = new ImageIcon(new ImageIcon("QCMBuilder/lib/icones/delete.png").getImage()
				.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		JButton newTrashButton = new JButton(newTrashIcon);
		newTrashButton.setPreferredSize(new Dimension(20, 20));
		newTrashButton.setBorderPainted(false);
		newTrashButton.setContentAreaFilled(false);
		newTrashButton.setFocusPainted(false);
		newTrashButton.setOpaque(false);
		gbc1.gridx = 0;
		newTrashPanel.add(newTrashButton, gbc1);

		// Ajouter un JTextField pour la question
		JTextField newTextField1 = new JTextField();
		newTextField1.setFont(new Font("Arial", Font.PLAIN, 14));
		newTextField1.setPreferredSize(new Dimension(100, 20));
		gbc1.gridx = 1;
		gbc1.fill = GridBagConstraints.HORIZONTAL;
		gbc1.weightx = 1.0;
		newTrashPanel.add(newTextField1, gbc1);

		// Ajouter un JTextField pour la réponse
		JTextField newTextField2 = new JTextField();
		newTextField2.setFont(new Font("Arial", Font.PLAIN, 14));
		newTextField2.setPreferredSize(new Dimension(100, 20));
		gbc1.gridx = 2;
		newTrashPanel.add(newTextField2, gbc1);

		// Ajouter un bouton pour ajouter les questions et les réponses aux
		// JComboBox
		JButton addButton = new JButton("Ajouter");
		addButton.setFont(new Font("Arial", Font.PLAIN, 12));
		addButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String question = newTextField1.getText();
				String reponse = newTextField2.getText();
				if (!question.isEmpty() && !comboBoxContains(questionComboBox, question))
				{
					questionComboBox.addItem(question);
					questions.add(question);
				}
				if (!reponse.isEmpty() && !comboBoxContains(reponseComboBox, reponse))
				{
					reponseComboBox.addItem(reponse);
					reponses.add(reponse);
				}
			}
		});
		gbc1.gridx = 3;
		newTrashPanel.add(addButton, gbc1);

		// Ajouter un ActionListener au bouton de poubelle
		newTrashButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				questionPanel.remove(rowPanel);
				questionPanel.revalidate();
				questionPanel.repaint();
			}
		});

		// Ajouter le TrashPanel au rowPanel
		rowPanel.add(newTrashPanel, gbc);

		// Ajouter le rowPanel au questionPanel
		questionPanel.add(rowPanel);
		questionPanel.revalidate();
		questionPanel.repaint();
	}

	// Méthode pour vérifier si un JComboBox contient un élément
	private boolean comboBoxContains(JComboBox<String> comboBox, String item)
	{
		for (int i = 0; i < comboBox.getItemCount(); i++)
		{
			if (comboBox.getItemAt(i).equals(item))
			{
				return true;
			}
		}
		return false;
	}

	// Méthode pour lier les questions avec les réponses
	private void lierQuestionsAvecReponses(String question, String reponse)
	{
		if (question != null && reponse != null)
		{
			questionReponseMap.put(question, reponse);
			System.out.println("Question: " + question + " - Réponse: " + reponse);
		}
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(QuestionLiaison::new);
	}
}
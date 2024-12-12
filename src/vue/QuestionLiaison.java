package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class QuestionLiaison extends JFrame
{
	private JPanel mainPanel;
	private JPanel questionPanel;
	private JPanel buttonPanel;
	private JButton enregistrerButton;
	private JButton ajouterButton;
	private JTextArea sujetTextArea;

	public QuestionLiaison()
	{
		mainPanel = new JPanel(new BorderLayout());
		questionPanel = new JPanel();
		questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
		buttonPanel = new JPanel();

		// Ajouter le bouton "Enregistrer"
		enregistrerButton = new JButton("Enregistrer");
		buttonPanel.add(enregistrerButton);

		// Ajouter le bouton "Ajouter Question/Réponse"
		ajouterButton = new JButton("Ajouter Question/Réponse");
		buttonPanel.add(ajouterButton);

		// Ajouter le panel des boutons au panel principal
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		mainPanel.add(new JScrollPane(questionPanel), BorderLayout.CENTER);

		// Ajouter le panel principal au frame
		add(mainPanel);

		// Ajouter le label "Question" et le JTextArea pour la question du sujet
		JPanel sujetPanel = new JPanel(new BorderLayout());
		JLabel sujetLabel = new JLabel("Question");
		sujetTextArea = new JTextArea(3, 50);
		sujetTextArea.setLineWrap(true);
		sujetTextArea.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(sujetTextArea);
		sujetPanel.add(sujetLabel, BorderLayout.NORTH);
		sujetPanel.add(scrollPane, BorderLayout.CENTER);

		// Ajouter le sujetPanel au-dessus du questionPanel
		mainPanel.add(sujetPanel, BorderLayout.NORTH);

		// Configurer le frame
		setTitle("Question Liaison");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null);
		setVisible(true);

		// Ajouter le gestionnaire d'événements pour le bouton "Enregistrer"
		enregistrerButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				enregistrerQuestionsEtReponses();
			}
		});

		// Ajouter le gestionnaire d'événements pour le bouton "Ajouter
		// Question/Réponse"
		ajouterButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				addTrashPanel();
			}
		});
	}

	private void addTrashPanel()
	{
		JPanel rowPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);

		// Redimensionner et ajouter une icône au début du panel
		ImageIcon startIcon = new ImageIcon(new ImageIcon("/home/saiiko/IUT/test/QCMBuilder/lib/icones/delete.png")
				.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		JLabel startLabel = new JLabel(startIcon);
		startLabel.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				questionPanel.remove(rowPanel);
				questionPanel.revalidate();
				questionPanel.repaint();
			}
		});
		gbc.gridx = 0;
		gbc.gridy = 0;
		rowPanel.add(startLabel, gbc);

		// Champ de texte pour la question
		JTextField questionField = new JTextField(20);
		gbc.gridx = 1;
		gbc.gridy = 0;
		rowPanel.add(questionField, gbc);

		// Champ de texte pour la réponse
		JTextField reponseField = new JTextField(20);
		gbc.gridx = 2;
		gbc.gridy = 0;
		rowPanel.add(reponseField, gbc);

		// Redimensionner et ajouter une icône à la fin du panel
		ImageIcon endIcon = new ImageIcon(new ImageIcon("/home/saiiko/IUT/test/QCMBuilder/lib/icones/delete.png")
				.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		JLabel endLabel = new JLabel(endIcon);
		endLabel.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				questionPanel.remove(rowPanel);
				questionPanel.revalidate();
				questionPanel.repaint();
			}
		});
		gbc.gridx = 3;
		gbc.gridy = 0;
		rowPanel.add(endLabel, gbc);

		questionPanel.add(rowPanel);
		questionPanel.revalidate();
		questionPanel.repaint();
	}

	private void enregistrerQuestionsEtReponses()
	{
		// Logique pour enregistrer les questions et les réponses
		String sujet = sujetTextArea.getText();
		System.out.println("Sujet: " + sujet);

		for (Component component : questionPanel.getComponents())
		{
			if (component instanceof JPanel)
			{
				JPanel panel = (JPanel) component;
				JTextField questionField = (JTextField) panel.getComponent(1);
				JTextField reponseField = (JTextField) panel.getComponent(2);
				String question = questionField.getText();
				String reponse = reponseField.getText();
				// Enregistrer la question et la réponse
				System.out.println("Question: " + question + ", Réponse: " + reponse);
			}
		}
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				new QuestionLiaison();
			}
		});
	}
}
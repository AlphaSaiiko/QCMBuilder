package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionLiaison extends JFrame
{
	private JPanel questionPanel;
	private JTextArea questionArea;
	private JButton addButton;
	private ButtonGroup buttonGroup;

	public QuestionLiaison()
	{
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

		// Initialiser le bouton "add"
		addButton = new JButton("Add");

		// Initialiser le ButtonGroup pour les boutons radio
		buttonGroup = new ButtonGroup();

		// Ajouter un ActionListener au bouton "add"
		addButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				addTrashPanels();
			}
		});

		// Ajouter le bouton "add" au panel des boutons
		buttonPanel.add(addButton);

		// Ajouter le panel des boutons au conteneur principal
		mainPanel.add(buttonPanel, BorderLayout.NORTH);
		mainPanel.add(questionPanel, BorderLayout.CENTER);

		// Ajouter le conteneur principal au frame
		add(mainPanel, BorderLayout.CENTER);

		// Afficher la fenêtre
		setTitle("Question Réponse Unique");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	// Méthode pour ajouter deux TrashPanel en miroir sur la même ligne
	private void addTrashPanels()
	{
		JPanel rowPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);

		// Premier TrashPanel
		JPanel newTrashPanel1 = new JPanel(new GridBagLayout());
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.anchor = GridBagConstraints.WEST;

		// Redimensionner l'icône de poubelle
		ImageIcon newTrashIcon1 = new ImageIcon(
				new ImageIcon("./lib/icones/delete.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JButton newTrashButton1 = new JButton(newTrashIcon1);
		newTrashButton1.setPreferredSize(new Dimension(40, 40));
		newTrashButton1.setBorderPainted(false);
		newTrashButton1.setContentAreaFilled(false);
		newTrashButton1.setFocusPainted(false);
		newTrashButton1.setOpaque(false);
		newTrashPanel1.add(newTrashButton1, gbc1);

		// Ajouter un ActionListener au bouton de poubelle
		newTrashButton1.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				questionPanel.remove(rowPanel);
				questionPanel.revalidate();
				questionPanel.repaint();
			}
		});

		// Augmenter la taille du JTextField
		JTextField newTextField1 = new JTextField();
		newTextField1.setFont(new Font("Arial", Font.PLAIN, 18));
		newTextField1.setPreferredSize(new Dimension(200, 30));
		gbc1.gridx = 1;
		gbc1.fill = GridBagConstraints.HORIZONTAL;
		gbc1.weightx = 1.0;
		newTrashPanel1.add(newTextField1, gbc1);

		gbc1.gridx = 2;
		gbc1.fill = GridBagConstraints.NONE;
		gbc1.weightx = 0;

		// Ajouter un bouton radio
		JRadioButton newRadioButton1 = new JRadioButton();
		newRadioButton1.setPreferredSize(new Dimension(30, 30));
		newTrashPanel1.add(newRadioButton1, gbc1);

		// Ajouter le bouton radio au ButtonGroup
		buttonGroup.add(newRadioButton1);

		// Deuxième TrashPanel (miroir)
		JPanel newTrashPanel2 = new JPanel(new GridBagLayout());
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		gbc2.anchor = GridBagConstraints.WEST;

		// Redimensionner l'icône de poubelle
		ImageIcon newTrashIcon2 = new ImageIcon(
				new ImageIcon("./lib/icones/delete.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
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
			@Override
			public void actionPerformed(ActionEvent e)
			{
				questionPanel.remove(rowPanel);
				questionPanel.revalidate();
				questionPanel.repaint();
			}
		});

		// Augmenter la taille du JTextField
		JTextField newTextField2 = new JTextField();
		newTextField2.setFont(new Font("Arial", Font.PLAIN, 18));
		newTextField2.setPreferredSize(new Dimension(200, 30));
		gbc2.gridx = 1;
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		gbc2.weightx = 1.0;
		newTrashPanel2.add(newTextField2, gbc2);

		gbc2.gridx = 2;
		gbc2.fill = GridBagConstraints.NONE;
		gbc2.weightx = 0;

		// Ajouter un bouton radio
		JRadioButton newRadioButton2 = new JRadioButton();
		newRadioButton2.setPreferredSize(new Dimension(30, 30));
		newTrashPanel2.add(newRadioButton2, gbc2);

		// Ajouter le bouton radio au ButtonGroup
		buttonGroup.add(newRadioButton2);

		// Ajouter les deux TrashPanel au rowPanel
		gbc.gridx = 0;
		rowPanel.add(newTrashPanel1, gbc);
		gbc.gridx = 1;
		rowPanel.add(newTrashPanel2, gbc);

		// Ajouter le rowPanel au questionPanel
		questionPanel.add(rowPanel);
		questionPanel.revalidate();
		questionPanel.repaint();
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(QuestionLiaison::new);
	}
}